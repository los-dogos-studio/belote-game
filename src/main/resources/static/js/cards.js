export const SUIT_SYMBOLS = { GULI: '♥', WKENTI: '♦', JVARI: '♣', YVAVI: '♠' };
export const SUIT_COLORS  = { GULI: '#d32f2f', WKENTI: '#d32f2f', JVARI: '#212121', YVAVI: '#212121' };
export const RANK_LABELS  = { SHVIDI: '7', RVA: '8', CXRA: '9', ATI: '10', VALETI: 'J', DAMA: 'Q', KAROLI: 'K', TUZI: 'A' };

export function parseCard(str) {
  const i = str.indexOf('_');
  return { suit: str.slice(0, i), rank: str.slice(i + 1) };
}

export function cardEl(suit, rank) {
  const el = document.createElement('div');
  el.className = 'playing-card';
  el.style.color = SUIT_COLORS[suit];
  const sym = SUIT_SYMBOLS[suit];
  const r = RANK_LABELS[rank];
  el.innerHTML = `<span class="card-corner">${r}<br>${sym}</span><span class="card-suit">${sym}</span><span class="card-corner">${r}<br>${sym}</span>`;
  return el;
}
