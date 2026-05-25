const savedNickname = localStorage.getItem("zedmetsaxeli");
const savedRoomId = localStorage.getItem("otaxisId");

if (savedNickname && savedRoomId) {
    async function checkSession() {
        const res = await fetch(`/api/tamashi/${savedRoomId}/mdgomareoba?zedmetsaxeli=${savedNickname}`);
        const data = await res.json();
        document.getElementById('session-info').textContent = 'Active session in room ' + savedRoomId + ' as ' + savedNickname;
        document.getElementById('session-banner').style.display = 'block';

        document.getElementById('btn-rejoin').addEventListener('click', () => {
            if (data.faza === 'LODINI') {
                window.location.href = 'waiting.html';
            } else {
                window.location.href = 'game.html';
            }
        });

        document.getElementById('btn-clear').addEventListener('click', () => {
            localStorage.removeItem('zedmetsaxeli');
            localStorage.removeItem('otaxisId');
            document.getElementById('session-banner').style.display = 'none';
        });
    }
    checkSession();
} else {
    console.log('no session found');
}

