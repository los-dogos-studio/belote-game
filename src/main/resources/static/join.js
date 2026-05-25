const params = new URLSearchParams(window.location.search);

const otaxisId = params.get('otaxisId');

if (otaxisId) {
    document.getElementById('room-id').value = otaxisId;
}


document.getElementById('join-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const nickname = document.getElementById('nickname').value.trim();
    const roomId = document.getElementById('room-id').value.trim();
    const team = document.getElementById('team').value;
    const res = await fetch('/api/tamashi/shesvla', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({zedmetsaxeli: nickname, otaxisId: roomId, gundi: team})
    });

    if (!res.ok) {
        document.getElementById('error-msg').textContent = await res.text();
        return;
    }

    localStorage.setItem('zedmetsaxeli', nickname);
    localStorage.setItem('otaxisId', roomId);
    window.location.href = 'waiting.html';
});
