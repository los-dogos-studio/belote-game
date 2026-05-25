# Guruli Belote — Complete Specification

## Game Rules

### 1. General
4 players, 2 teams. Partners sit opposite each other. 32-card deck: 7, 8, 9, 10, Jack, Queen, King, Ace. First team to 1001 points wins.

### 2. Seating
Players sit clockwise at positions 0, 1, 2, 3. Position 0 is first to act, position 3 is dealer. Teams: position 0 + position 2 vs position 1 + position 3. Dealer rotates clockwise each round.

### 3. Dealing — Stage 1
Dealer gives 5 cards to each player clockwise starting from position 0. One card is flipped face up as potential trump.

### 4. Trump Determination

**Jack rule:** If the flipped card itself is a Jack, that suit automatically becomes trump. First player (position 0) becomes declarer. Bidding is skipped.

**Round 1:** Starting from position 0, each player either accepts the flipped suit as trump or passes.

**Round 2:** If everyone passed in round 1, each player may name any suit except the flipped suit, or pass.

**Dealer obligation:** If everyone passes in round 2, dealer must name a suit and become declarer.

### 5. Dealing — Stage 2
Flipped card always goes to declarer. Then deal remaining cards clockwise from position 0 — 3 cards to each non-declarer, 2 to declarer. Everyone ends with 8 cards.

### 6. Combo Declaration Phase
After second deal, before tricks start, there is a 30 second declaration window (timer runs on frontend only). During this window:
- Players who have combos manually select and declare them
- Server validates declared combos actually exist in their hand
- Players press "ready" when done (with or without declaring)
- When all 4 press ready, server compares teams' declared combos and determines winning team
- Trick taking begins

### 7. Combos

**Belote:** Trump King + Trump Queen = 20 points. Announced during play — say "belote" when playing first card, "rebelote" when playing second. Any trick. If forgotten, doesn't count.

**Sequences:** Consecutive cards of same suit:
- 3 cards = 20 points
- 4 cards = 50 points
- 5 cards = 100 points

Multiple sequences allowed in one hand — longest found first, then from remaining cards. Ace cannot wrap before 7. Four of a kind takes priority — cards used in four of a kind cannot form sequences.

**Four of a kind:**
- Four Jacks = 200
- Four 9s = 150
- Four 10s/Queens/Kings/Aces = 100
- Four 7s or 8s = 0, don't count

### 8. Combo Comparison
Only one team's combos count — the stronger team's. Comparison rules:

Same length sequences:
1. Higher top card wins
2. Equal top card → trump suit wins
3. Still tied → earlier clockwise position wins

Four of a kind vs 5-card sequence:
- Trump 5-card sequence wins
- Otherwise → earlier clockwise position wins

Four of a kind vs four of a kind:
- Higher points wins
- Equal → earlier clockwise position wins

Winning team gets ALL their combos counted across both players.

### 9. Showing Combos
**Only declared combos that are actually shown count for scoring.**

- On trick 2: winning team must show their combos
- If winning team doesn't show on trick 2: losing team can show theirs on trick 3
- If at least one player from winning team shows on trick 2, whole team's combos count // MARK
- If nobody shows: no combos count

### 10. Card Power

Trump (strongest to weakest):
Jack → 9 → Ace → 10 → King → Queen → 8 → 7

Non-trump (strongest to weakest):
Ace → 10 → King → Queen → Jack → 9 → 8 → 7

### 11. Card Points

Trump: Jack=20, 9=14, Ace=11, 10=10, King=4, Queen=3, 8=0, 7=0

Non-trump: Ace=11, 10=10, King=4, Queen=3, Jack=2, 9=0, 8=0, 7=0

### 12. Play Rules
- Follow suit mandatory — if you have led suit you must play it
- Cut with trump mandatory — if no led suit, must play trump if you have it
- Overtrump mandatory — if trick already cut, must play higher trump than strongest on table if possible
- All rules apply when trump is led too

### 13. Winning a Trick
- Not cut: highest card of led suit wins
- Cut: highest trump wins
- Winner leads next trick

### 14. Scoring
Total card points per round = 162 (including 10 point bonus for last trick). Add combo points to winning combo team.

Rounding: remainder ≤5 → round down, remainder >5 → round up (exactly 5 rounds down).

**Fall:** If declarer team does not strictly beat opponents (tie = fall):
- Declarer gets −50
- Opponents get 160
- Declarer's combos transfer to opponents

**Capo:** One team takes all 8 tricks:
- Capo team gets 250
- Other team gets only their own combo points

**Tie:** Both teams equal points:
- Non-declaring team gets half immediately
- Remaining points frozen, carry to next round
- Next round winner claims frozen points too

### 15. Game End
First team to 1001 points wins.

---

# API Specification

## Base URL
`/api/tamashi`

## Enums

**Cveti (Suit):** `GULI, WKENTI, JVARI, YVAVI`

**Ranki (Rank):** `SHVIDI, RVA, CXRA, ATI, VALETI, DAMA, KAROLI, TUZI`

**RaundisFaza (Round Phase):** `KARTIS_DARIGEBA, KOZIROBA, KOMBINACIIS_DEKLARACIA, KRUGEBI, QULEBIS_DATVLA`

**KombinaciisTipi (Combo Type):** `BELOTI, MIYOLEBA, ERTNAIREBI`

**BelotisCxadeba (Belote Announcement):** `ARAA_NACXADEBI, BELOTIA_NACXADEBI, REBELOTIA_NACXADEBI`

---

## Endpoints

### Create Game
`POST /api/tamashi/sheqmeni`

Request:
```json
{ "zedmetsaxeli": "giorgi" }
```

Response:
```json
{ "otaxisId": "1234" }
```

---

### Join Game
`POST /api/tamashi/shesvla`

Request:
```json
{ "zedmetsaxeli": "luka", "otaxisId": "1234", "gundi": "A" }
```

Response: `200 OK` (empty) or error string

---

### Get State
`GET /api/tamashi/{otaxisId}/mdgomareoba?zedmetsaxeli=giorgi`

Response:
```json
{
  "otaxisId": "1234",
  "faza": "KOZIROBA",
  "mimdinareMotamashisZedmetsaxeli": "giorgi",
  "sheniKartebi": [
    { "cveti": "GULI", "ranki": "TUZI" }
  ],
  "mimdinareKrugi": [
    { "zedmetsaxeli": "giorgi", "cveti": "GULI", "ranki": "TUZI" }
  ],
  "koziriCveti": "GULI",
  "amotrialebuliKarti": "GULI_CXRA",
  "qula": {
    "gundiAQula": 0,
    "gundiBQula": 0,
    "gayinuliQula": 0
  },
  "gamarjvebuliGundi": null,
  "kombinaciebRomblebicChaitvala": [
    { "tipi": "MIYOLEBA", "qula": 20, "cveti": "GULI", "umaghlesiRanki": "TUZI", "sigrdze": 3 }
  ],
  "romeliGundisKombinaciebiGadis": "A",
  "motamasheebi": [
    { "zedmetsaxeli": "giorgi", "gundi": "A" }
  ]
}
```

Notes:
- `sheniKartebi` — only requesting player's cards
- `mimdinareKrugi` — null during KOZIROBA and KOMBINACIIS_DEKLARACIA
- `koziriCveti` — available from KOMBINACIIS_DEKLARACIA onwards
- `amotrialebuliKarti` — only during KOZIROBA, format "CVETI_RANKI"
- `motamasheebi` — only populated during LODINI phase
- `kombinaciebRomblebicChaitvala` — null if no combos
- `romeliGundisKombinaciebiGadis` — "A" or "B" or null

---

### Bid — Accept in Round 1
`POST /api/tamashi/{otaxisId}/koziroba/pirvelshi`

Request:
```json
{ "zedmetsaxeli": "giorgi" }
```

---

### Bid — Pass
`POST /api/tamashi/{otaxisId}/koziroba/pasi`

Request:
```json
{ "zedmetsaxeli": "giorgi" }
```

---

### Bid — Name Trump in Round 2
`POST /api/tamashi/{otaxisId}/koziroba/meoreshi`

Request:
```json
{ "zedmetsaxeli": "giorgi", "cveti": "GULI" }
```

---

### Bid — Dealer Forced
`POST /api/tamashi/{otaxisId}/koziroba/vinujdeni`

Request:
```json
{ "zedmetsaxeli": "giorgi", "cveti": "GULI" }
```

---

### Declare Combos
`POST /api/tamashi/{otaxisId}/kombinacia/cxadeba`

Request:
```json
{
  "zedmetsaxeli": "giorgi",
  "kombinaciebi": [
    { "tipi": "MIYOLEBA", "cveti": "GULI", "umaghlesiRanki": "TUZI", "sigrdze": 3 },
    { "tipi": "ERTNAIREBI", "cveti": null, "umaghlesiRanki": "VALETI", "sigrdze": 4 }
  ]
}
```

Notes:
- `cveti` is null for four of a kind
- Server validates combos exist in player's hand

---

### Ready (Done Declaring)
`POST /api/tamashi/{otaxisId}/kombinacia/mzadyofna`

Request:
```json
{ "zedmetsaxeli": "giorgi" }
```

Notes:
- Must be called after declaring (or without declaring if nothing to declare)
- When all 4 players ready, phase transitions to KRUGEBI

---

### Show Combos
`POST /api/tamashi/{otaxisId}/kombinacia/chveneba`

Request:
```json
{ "zedmetsaxeli": "giorgi" }
```

Notes:
- Only valid on trick 2 (winning team) or trick 3 (losing team if winning team forgot)
- Only shown combos count for scoring

---

### Play Card
`POST /api/tamashi/{otaxisId}/kartis-chamosvla`

Request:
```json
{
  "zedmetsaxeli": "giorgi",
  "cveti": "GULI",
  "ranki": "TUZI",
  "belotisCxadeba": "ARAA_NACXADEBI"
}
```

Notes:
- `belotisCxadeba` options: `ARAA_NACXADEBI`, `BELOTIA_NACXADEBI`, `REBELOTIA_NACXADEBI`
- Player must remember to announce belote themselves — no prompting
- Server validates legal moves

---

## Game Flow

```
Create game → Join (3 more players) → 
KOZIROBA (bidding) → 
KOMBINACIIS_DEKLARACIA (30s frontend timer, declare + ready) → 
KRUGEBI (8 tricks) → 
QULEBIS_DATVLA (auto-scored, new round starts or game over)
```

## Frontend Notes
- Poll `GET /mdgomareoba` every 2 seconds
- Save `zedmetsaxeli` and `otaxisId` to localStorage for reconnection
- 30 second combo declaration timer runs on frontend only
- Belote announcement is player's responsibility — no prompting
- Each player only sees their own cards (`sheniKartebi`)
- Cards displayed using suit symbols: GULI=♥ WKENTI=♠ JVARI=♣ YVAVI=♦

