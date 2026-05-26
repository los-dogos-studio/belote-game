const zedmetsaxeliInput = document.querySelector('#zedmetsaxeli');
const otaxisIdInput = document.querySelector('#otaxisId');
const shesvlaButton = document.querySelector('#shesvlis-ghilaki');
const eroriSpani = document.querySelector('#eroriSpani');
const gundisArchevaSelecti = document.querySelector('#romel-gundshi-joindeba')

shesvlaButton.addEventListener('click', async (e) => {
    e.preventDefault();

    const zedmetsaxeli = zedmetsaxeliInput.value.trim();
    const otaxisId = otaxisIdInput.value.trim();
    const shercheuliGundi = gundisArchevaSelecti.value.trim();

    eroriSpani.textContent = '';

    try {
        const response = await fetch('/api/tamashi/shesvla', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ zedmetsaxeli, otaxisId, gundi: shercheuliGundi })
        });

        if (!response.ok) {
            const erorisText = await response.text();
            eroriSpani.textContent = erorisText;
            return;
        }

        localStorage.setItem('otaxisId', otaxisId);
        localStorage.setItem('zedmetsaxeli', zedmetsaxeli);

        window.location.href = '/lodini.html';

    } catch (err) {
        erorisSpani.textContent = 'ქსელის შეცდომა სცადეთ თავიდან';
    }

});




