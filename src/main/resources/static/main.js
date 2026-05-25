const createForm = document.getElementById('create-form');
const errorMsg = document.getElementById('error-msg');

createForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const nickname = document.getElementById('nickname').value.trim();

    const res = await fetch('/api/tamashi/sheqmeni', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ zedmetsaxeli: nickname })
    });

    if (!res.ok){
        errorMsg.textContent = await res.text();
        return;
    }

    errorMsg.textContent = '';
    const data = await res.json();
    localStorage.setItem('zedmetsaxeli', nickname);
    localStorage.setItem('otaxisId', data.otaxisId);

    window.location.href = 'waiting.html';
});



