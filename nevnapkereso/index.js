const keresesGomb = document.getElementById('keresesGomb');
const eredmenyekDiv = document.getElementById('eredmenyek');

keresesGomb.addEventListener('click', () => {
  const nap = document.getElementById('nap').value;
  const nev = document.getElementById('nev').value;

  let url = 'http://localhost/api.php';
  if (nap) {
    url += '?nap=' + nap;
  } else if (nev) {
    url += '?nev=' + nev;
  }

  fetch(url)
    .then(response => response.json())
    .then(data => {
      if (data.error) {
        eredmenyekDiv.textContent = data.error;
      } else {
        eredmenyekDiv.textContent = JSON.stringify(data); // Vagy formázott megjelenítés
      }
    });
});