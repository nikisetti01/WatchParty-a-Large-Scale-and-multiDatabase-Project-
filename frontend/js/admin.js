const allCommunities = [
    "Review", "AMA", "News", "Article", "Spoilers",
    "Poster", "Recommendation", "Trailer", "Bonk!",
    "Official Discussion", "Without Tag", "Weekly Box Office",
    "Discussion", "Media", "Question"
  ];
  
// Esegui la richiesta HTTP quando la pagina è completamente caricata
document.addEventListener('DOMContentLoaded', function () {
        const communityButtonsContainer = document.getElementById('community-buttons');
        document.getElementById('delete-user-button').addEventListener('click', function() {
            deleteUser();
        });
      
        allCommunities.forEach(community => {
          const communityButton = document.createElement('button');
          communityButton.textContent = community;
          communityButton.className = 'community-button';
          
          // Aggiungi un gestore di eventi al pulsante per caricare la tabella dei migliori utenti
          communityButton.addEventListener('click', function() {
            loadTopUsersTable(community);
          });
      
          communityButtonsContainer.appendChild(communityButton);
        });
   
      

    fetch('http://localhost:8080/api/admin/ageMovie/')
      .then(response => {
        if (!response.ok) {
          throw new Error('Errore nella richiesta.');
        }
        return response.json();
      })
      .then(data => {
        // Qui puoi elaborare i dati ottenuti dal server e aggiornare il grafico.
        console.log(data); // Stampa i dati ottenuti dalla richiesta
  
        // Aggiungi un valore minimo per le fasce d'età mancanti
        var ageGroups = ["10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80+"];
        ageGroups.forEach(function (ageGroup) {
          var found = data.some(function (item) {
            return item.ageGroup === ageGroup;
          });
          if (!found) {
            data.push({
              "ageGroup": ageGroup,
              "movieCount": 50 // Imposta il valore minimo a 50 per le fasce mancanti
            });
          }
        });
  
        // Ordina i dati in base all'età
        data.sort(function (a, b) {
          return ageGroups.indexOf(a.ageGroup) - ageGroups.indexOf(b.ageGroup);
        });
  
        // Estrai le età e i conteggi dai dati
        var ageRanges = data.map(function (item) {
          return item.ageGroup;
        });
  
        var movieCounts = data.map(function (item) {
          return item.movieCount;
        });
  
        // Ottieni il riferimento al canvas del grafico
        var ctx = document.getElementById('myChart').getContext('2d');
  
        // Configura il grafico a barre
        var myChart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: ageRanges,
            datasets: [{
              label: 'Number of Films Watched',
              data: movieCounts,
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1
            }]
          },
          options: {
            scales: {
              x: {
                beginAtZero: true
              },
              y: {
                beginAtZero: true
              }
            }
          }
        });
      })
      .catch(error => {
        console.error('Errore:', error);
      });
  });
  function loadTopUsersTable(communityName) {
    // Effettua la richiesta GET al server
    fetch(`http://localhost:8080/api/admin/activity/${encodeURIComponent(communityName)}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(users => {
        // Chiamata di una funzione per visualizzare i dati nella tabella
        displayTopUsers(users);
      })
      .catch(error => {
        // Gestisci gli errori
        console.error('Fetch error:', error);
        // Puoi anche visualizzare un messaggio di errore nella tabella
        displayTopUsersError();
      });
  }
  function displayTopUsers(users) {
    const userTable = document.getElementById('user-table');
    userTable.innerHTML = ''; // Pulisci la tabella esistente
  
    // Creazione dell'intestazione della tabella
    const headerRow = userTable.createTHead().insertRow(0);
    headerRow.innerHTML = '<th>Username</th><th>Postings</th><th>Comments</th><th>Total Activity</th>';
  
    // Creazione delle righe della tabella
    users.forEach(user => {
      const row = userTable.insertRow();
      row.insertCell(0).textContent = user.username;
      row.insertCell(1).textContent = user.numPostings;
      row.insertCell(2).textContent = user.numComments;
      row.insertCell(3).textContent = user.totalActivity;
    });
  }
  
  function displayTopUsersError() {
    const userTable = document.getElementById('user-table');
    userTable.innerHTML = '<tr><td colspan="4">Error loading top users.</td></tr>';
  }
  function deleteUser() {
    const usernameInput = document.querySelector('#search-bar input');
    const username = usernameInput.value.trim();

    if (username) {
        // Effettua la richiesta DELETE al server
        fetch(`http://localhost:8080/api/admin/deleteuser/${encodeURIComponent(username)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.text();
        })
        .then(message => {
            // Visualizza un messaggio di successo o errore
            console.log(message);
            alert(message);
            // Pulisci il campo di input
            usernameInput.value = '';
        })
        .catch(error => {
            console.error('Error deleting user:', error);
            alert('Error deleting user: ' + error.message);
        });
    } else {
        alert('Please enter a username to delete.');
    }
}


function deleteMovie() {
  const movieInput = document.querySelector('#delete-movie-section #search-bar #movie-input');
  const movieTitle = movieInput.value.trim();

  if (movieTitle) {
    console.log(movieTitle);
      // Send the DELETE request to the server
      fetch(`http://localhost:8080/api/admin/deletemovie/${encodeURIComponent(movieTitle)}`, {
          method: 'GET', // Make sure this is a DELETE request
          headers: {
              'Content-Type': 'application/json',
          },
      })
      .then(response => {
          if (!response.ok) {
              throw new Error('Network response was not ok.');
          }
          return response.text();
      })
      .then(message => {
          // Display a success or error message
          console.log(message);
          alert(message);
          // Clear the input field
          movieInput.value = '';
      })
      .catch(error => {
          console.error('Error deleting movie:', error);
          alert('Error deleting movie: ' + error.message);
      });
  } else {
      alert('Please enter a movie title to delete.');
  }
}

