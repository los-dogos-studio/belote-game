const otaxisId = localStorage.getItem('otaxisId');

document.getElementById('room-id').textContent = 'Room ID: ' + otaxisId;
document.getElementById('share-link').textContent = window.location.origin + '/join.html?otaxisId=' + otaxisId;

async function poll() {
    const nickname = localStorage.getItem('zedmetsaxeli');
    const res = await fetch(`/api/tamashi/${otaxisId}/mdgomareoba?zedmetsaxeli=${nickname}`);
    const data = await res.json();

    const playerList = document.getElementById('player-list');
    playerList.innerHTML = '';
    data.motamasheebi.forEach(player => {
        const li = document.createElement('li');
        li.textContent = player.zedmetsaxeli + ' - Team ' + player.gundi;
        playerList.appendChild(li);
    });
    if (data.faza !== 'LODINI') {
        window.location.href = 'game.html';
    }
}

setInterval(poll, 1000);

