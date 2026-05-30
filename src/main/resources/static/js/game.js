import { getGameState, playCard, declareCombo, readyForPlay, showCombos } from './api.js';
import { getSession } from './storage.js';
import { startPolling } from './polling.js';
import { SUIT_SYMBOLS, SUIT_COLORS, RANK_LABELS } from './cards.js';

const { nickname, roomId } = getSession();
if (!nickname || !roomId) window.location.href = 'index.html';

const scoreA          = document.getElementById('scoreA');
const trumpInfo       = document.getElementById('trumpInfo');
const scoreB          = document.getElementById('scoreB');
const pTop            = document.getElementById('pTop');
const pLeft           = document.getElementById('pLeft');
const pRight          = document.getElementById('pRight');
const pBottom         = document.getElementById('pBottom');
const trickArea       = document.getElementById('trickArea');
const actionsDiv      = document.getElementById('actions');
const handDiv         = document.getElementById('hand');
const errorDiv        = document.getElementById('error');
const comboOverlay    = document.getElementById('comboOverlay');
const comboHandDiv    = document.getElementById('comboHand');
const comboButtons    = document.getElementById('comboButtons');
const comboListDiv    = document.getElementById('comboList');
const readyBtn        = document.getElementById('readyBtn');
const comboError      = document.getElementById('comboError');
const roundEndOverlay = document.getElementById('roundEndOverlay');
const roundEndContent = document.getElementById('roundEndContent');
const gameOverOverlay = document.getElementById('gameOverOverlay');
const gameOverContent = document.getElementById('gameOverContent');

const SLOTS           = { 0: pBottom, 1: pLeft, 2: pTop, 3: pRight };
const RANK_ORDER      = ['SHVIDI', 'RVA', 'CXRA', 'ATI', 'VALETI', 'DAMA', 'KAROLI', 'TUZI'];
const TRUMP_RANK_ORDER = ['SHVIDI', 'RVA', 'ATI', 'DAMA', 'KAROLI', 'TUZI', 'CXRA', 'VALETI'];

// combo phase state (reset on each new phase entry)
let comboReady = false;
let selectedIdx = new Set();
let lockedIdx = new Set();
let pendingCombos = []; // [{ combo, indices: Set }]

// belote state
let pendingBeloteCard = null;

// round-end display
let prevFaza = null;
let roundEndUntil = 0;

// ─── SHARED HELPERS ─────────────────────────────────────────────────────────

function cardEl(suit, rank) {
  const el = document.createElement('span');
  el.className = 'playing-card';
  el.style.color = SUIT_COLORS[suit];
  el.textContent = `${SUIT_SYMBOLS[suit]} ${RANK_LABELS[rank]}`;
  return el;
}

function renderTopBar(state) {
  scoreA.textContent = `A: ${state.qula.gundiAQula}`;
  scoreB.textContent = `B: ${state.qula.gundiBQula}`;
  let html = '';
  if (state.koziriCveti) {
    html = `<span style="color:${SUIT_COLORS[state.koziriCveti]}">${SUIT_SYMBOLS[state.koziriCveti]}</span>`;
    if (state.mokozire) html += ` ${state.mokozire.zedmetsaxeli} (${state.mokozire.gundi})`;
  }
  if (state.qula.gayinuliQula > 0) html += ` · Frozen: ${state.qula.gayinuliQula}`;
  trumpInfo.innerHTML = html;
}

function renderPlayers(state) {
  const me = state.motamasheebi.find(p => p.zedmetsaxeli === nickname);
  if (!me) return;
  for (const p of state.motamasheebi) {
    const diff = (p.pozicia - me.pozicia + 4) % 4;
    const el = SLOTS[diff];
    if (!el) continue;
    el.textContent = `${p.zedmetsaxeli} (${p.gundi})`;
    el.className = 'player-slot' + (p.zedmetsaxeli === state.mimdinareMotamashisZedmetsaxeli ? ' active' : '');
    el.style.color = 'rgba(255,255,255,0.9)';
  }
}

function hideAllOverlays() {
  comboOverlay.style.display = 'none';
  roundEndOverlay.style.display = 'none';
  gameOverOverlay.style.display = 'none';
}

// ─── KARTIS_DARIGEBA ────────────────────────────────────────────────────────

function renderDealing() {
  hideAllOverlays();
  trickArea.innerHTML = '';
  handDiv.innerHTML = '';
  actionsDiv.textContent = 'Dealing cards...';
}

// ─── KOMBINACIIS_DEKLARACIA ─────────────────────────────────────────────────

function renderComboPhase(state) {
  hideAllOverlays();
  comboOverlay.style.display = 'flex';
  if (comboReady) return;
  comboReady = true;

  selectedIdx.clear();
  lockedIdx.clear();
  pendingCombos = [];
  comboListDiv.innerHTML = '';
  comboError.textContent = '';
  comboButtons.style.display = 'none';

  renderComboHand(state.sheniKartebi, state.koziriCveti);

  readyBtn.onclick = async () => {
    readyBtn.disabled = true;
    comboError.textContent = '';
    try {
      if (pendingCombos.length > 0) {
        await declareCombo(roomId, nickname, pendingCombos.map(p => p.combo));
      }
      await readyForPlay(roomId, nickname);
      comboReady = false;
    } catch (err) {
      comboError.textContent = err.message;
      readyBtn.disabled = false;
    }
  };
}

function renderComboHand(cards, trumpSuit) {
  comboHandDiv.innerHTML = '';

  cards.forEach((card, i) => {
    const el = cardEl(card.cveti, card.ranki);
    el.style.margin = '0.2rem';

    if (lockedIdx.has(i)) {
      el.style.opacity = '0.3';
    } else {
      el.style.cursor = 'pointer';
      if (selectedIdx.has(i)) el.style.outline = '3px solid #f57c00';
      el.addEventListener('click', () => {
        if (selectedIdx.has(i)) selectedIdx.delete(i); else selectedIdx.add(i);
        renderComboHand(cards, trumpSuit);
        updateComboTypeButtons(cards, trumpSuit);
      });
    }

    comboHandDiv.appendChild(el);
  });
}

function updateComboTypeButtons(cards, trumpSuit) {
  comboButtons.innerHTML = '';
  if (selectedIdx.size === 0) { comboButtons.style.display = 'none'; return; }
  comboButtons.style.display = 'block';

  const selected = [...selectedIdx].map(i => cards[i]);

  [['Sequence (Miyoleba)', 'MIYOLEBA'], ['Four of a Kind (Ertnairebi)', 'ERTNAIREBI']].forEach(([label, type]) => {
    const btn = document.createElement('button');
    btn.textContent = label;
    btn.style.cssText = 'background:#1976d2; color:white; margin:0.2rem;';
    btn.addEventListener('click', () => {
      const rankOrder = (type === 'ERTNAIREBI' || selected[0].cveti !== trumpSuit) ? RANK_ORDER : TRUMP_RANK_ORDER;
      const umaghlesiRanki = selected.reduce((best, c) =>
        rankOrder.indexOf(c.ranki) > rankOrder.indexOf(best) ? c.ranki : best
      , selected[0].ranki);

      const combo = {
        tipi: type,
        cveti: type === 'ERTNAIREBI' ? null : selected[0].cveti,
        umaghlesiRanki,
        sigrdze: selected.length
      };

      pendingCombos.push({ combo, indices: new Set(selectedIdx) });
      selectedIdx.forEach(i => lockedIdx.add(i));
      selectedIdx.clear();
      comboButtons.style.display = 'none';
      renderComboHand(cards, trumpSuit);
      renderComboList(cards, trumpSuit);
    });
    comboButtons.appendChild(btn);
  });
}

function renderComboList(cards, trumpSuit) {
  comboListDiv.innerHTML = '';
  pendingCombos.forEach(({ combo }, idx) => {
    const row = document.createElement('div');
    row.style.cssText = 'display:flex; justify-content:space-between; padding:0.3rem 0; border-bottom:1px solid #eee;';

    const label = combo.tipi === 'ERTNAIREBI'
      ? `Four ${RANK_LABELS[combo.umaghlesiRanki]}s`
      : `Sequence of ${combo.sigrdze}: ${SUIT_SYMBOLS[combo.cveti]} up to ${RANK_LABELS[combo.umaghlesiRanki]}`;

    const removeBtn = document.createElement('button');
    removeBtn.textContent = '×';
    removeBtn.style.cssText = 'background:none; color:#d32f2f; font-weight:bold; padding:0 0.3rem;';
    removeBtn.addEventListener('click', () => {
      pendingCombos[idx].indices.forEach(i => lockedIdx.delete(i));
      pendingCombos.splice(idx, 1);
      renderComboHand(cards, trumpSuit);
      renderComboList(cards, trumpSuit);
    });

    row.innerHTML = `<span>${label}</span>`;
    row.appendChild(removeBtn);
    comboListDiv.appendChild(row);
  });
}

// ─── KRUGEBI ────────────────────────────────────────────────────────────────

function renderKrugebi(state) {
  hideAllOverlays();
  comboReady = false;

  trickArea.innerHTML = '';
  if (state.mimdinareKrugi) {
    state.mimdinareKrugi.forEach(played => {
      const div = document.createElement('div');
      div.style.textAlign = 'center';
      div.appendChild(cardEl(played.cveti, played.ranki));
      const name = document.createElement('div');
      name.textContent = played.zedmetsaxeli;
      name.style.cssText = 'font-size:0.7rem; color:rgba(255,255,255,0.7); margin-top:0.15rem;';
      div.appendChild(name);
      trickArea.appendChild(div);
    });
  }

  if (pendingBeloteCard) {
    renderBeloteActions(pendingBeloteCard);
  } else {
    renderKrugebiActions(state);
  }

  const isMyTurn = state.mimdinareMotamashisZedmetsaxeli === nickname && !pendingBeloteCard;
  handDiv.innerHTML = '';
  state.sheniKartebi.forEach(card => {
    const el = cardEl(card.cveti, card.ranki);
    el.style.margin = '0.2rem';
    if (isMyTurn) {
      el.style.cursor = 'pointer';
      el.addEventListener('click', () => handleCardClick(card, state));
    } else {
      el.style.opacity = '0.7';
    }
    handDiv.appendChild(el);
  });
}

function renderKrugebiActions(state) {
  actionsDiv.innerHTML = '';

  const showBtn = document.createElement('button');
  showBtn.textContent = 'Show Combos';
  showBtn.style.cssText = 'background:rgba(255,255,255,0.15); color:white; font-size:0.8rem; padding:0.4rem 0.8rem;';
  showBtn.addEventListener('click', async () => {
    errorDiv.textContent = '';
    try { await showCombos(roomId, nickname); } catch (err) { errorDiv.textContent = err.message; }
  });
  actionsDiv.appendChild(showBtn);

  if (state.kombinaciebRomblebicChaitvala?.length > 0) {
    const info = document.createElement('div');
    info.style.cssText = 'color:rgba(255,255,255,0.75); font-size:0.8rem; margin-top:0.3rem;';
    info.textContent = `Combos counted for Team ${state.romeliGundisKombinaciebiGadis}`;
    actionsDiv.appendChild(info);
  }
}

function handleCardClick(card, state) {
  const isTrumpKQ = card.cveti === state.koziriCveti &&
    (card.ranki === 'KAROLI' || card.ranki === 'DAMA');

  if (isTrumpKQ) {
    const hasK = state.sheniKartebi.some(c => c.cveti === state.koziriCveti && c.ranki === 'KAROLI');
    const hasQ = state.sheniKartebi.some(c => c.cveti === state.koziriCveti && c.ranki === 'DAMA');
    if (hasK && hasQ) {
      pendingBeloteCard = card;
      renderBeloteActions(card);
      return;
    }
  }

  doPlayCard(card, 'ARAA_NACXADEBI');
}

function renderBeloteActions(card) {
  actionsDiv.innerHTML = '';
  [['Belote', 'BELOTIA_NACXADEBI'], ['Rebelote', 'REBELOTIA_NACXADEBI'], ['No announcement', 'ARAA_NACXADEBI']].forEach(([label, val]) => {
    const btn = document.createElement('button');
    btn.textContent = label;
    btn.style.cssText = 'background:rgba(255,255,255,0.9); color:#333; margin:0.2rem;';
    btn.addEventListener('click', () => { pendingBeloteCard = null; doPlayCard(card, val); });
    actionsDiv.appendChild(btn);
  });
}

async function doPlayCard(card, belote) {
  errorDiv.textContent = '';
  handDiv.querySelectorAll('.playing-card').forEach(e => e.style.pointerEvents = 'none');
  try {
    await playCard(roomId, nickname, card.cveti, card.ranki, belote);
  } catch (err) {
    errorDiv.textContent = err.message;
    handDiv.querySelectorAll('.playing-card').forEach(e => e.style.pointerEvents = '');
  }
}

// ─── QULEBIS_DATVLA ─────────────────────────────────────────────────────────

function renderRoundEnd(state) {
  roundEndOverlay.style.display = 'flex';
  roundEndContent.innerHTML = `
    <p style="margin:0.5rem 0;">Team A: <strong>${state.qula.gundiAQula}</strong></p>
    <p style="margin:0.5rem 0;">Team B: <strong>${state.qula.gundiBQula}</strong></p>
    ${state.qula.gayinuliQula > 0 ? `<p style="margin:0.5rem 0; color:#f57c00;">Frozen: ${state.qula.gayinuliQula}</p>` : ''}
    <p style="margin-top:1rem; color:#777; font-size:0.85rem;">Next round starting...</p>
  `;
}

// ─── GAME OVER ──────────────────────────────────────────────────────────────

function renderGameOver(state) {
  gameOverOverlay.style.display = 'flex';
  gameOverContent.innerHTML = `
    <p style="font-size:1.5rem; font-weight:bold; margin-bottom:1rem;">Team ${state.gamarjvebuliGundi} wins!</p>
    <p>Team A: <strong>${state.qula.gundiAQula}</strong></p>
    <p>Team B: <strong>${state.qula.gundiBQula}</strong></p>
  `;
}

// ─── MAIN POLL ──────────────────────────────────────────────────────────────

const stopPolling = startPolling(async () => {
  try {
    const state = await getGameState(roomId, nickname);
    errorDiv.textContent = '';

    renderTopBar(state);
    renderPlayers(state);

    if (state.gamarjvebuliGundi) {
      stopPolling();
      renderGameOver(state);
      return;
    }

    if (state.faza === 'KOZIROBA') {
      stopPolling();
      window.location.href = 'bidding.html';
      return;
    }

    // Detect round end: faza just left KRUGEBI
    if (prevFaza === 'KRUGEBI' && state.faza !== 'KRUGEBI') {
      renderRoundEnd(state);
      roundEndUntil = Date.now() + 3000;
    }
    prevFaza = state.faza;

    // Hold rendering while round end overlay is showing
    if (Date.now() < roundEndUntil) return;

    if (state.faza !== 'KOMBINACIIS_DEKLARACIA') comboReady = false;

    switch (state.faza) {
      case 'KARTIS_DARIGEBA':        renderDealing();           break;
      case 'KOMBINACIIS_DEKLARACIA': renderComboPhase(state);   break;
      case 'KRUGEBI':                renderKrugebi(state);      break;
      case 'QULEBIS_DATVLA':         renderRoundEnd(state);     break;
    }
  } catch (err) {
    errorDiv.textContent = err.message;
  }
});
