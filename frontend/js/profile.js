const allCommunities = [
  "Review", "AMA", "News", "Article", "Spoilers",
  "Poster", "Recommendation", "Trailer", "Bonk!",
  "Official Discussion", "Without Tag", "Weekly Box Office",
  "Discussion", "Media", "Question"
];
var userTags=[]

document.addEventListener("DOMContentLoaded", function () {

    let button= document.getElementById('affinityButton')
    console.log(button)
    if(button!=null){
   
  button.addEventListener('click', function() {
        // Fetch the usernames
        const urlParams = new URLSearchParams(window.location.search);
        const username1 = urlParams.get('username1');
        const username2 = urlParams.get('username');
        // Make the GET request using fetch
        fetch('http://localhost:8080/api/community/affinity/' + username1 + '/' + username2)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(result => {
                // Display the result
                document.getElementById('affinityResult').innerHTML = '<p> ' + result + '</p>';
            })
            .catch(error => {
                // Handle errors
                console.error('Fetch error:', error);
                document.getElementById('affinityResult').innerHTML = '<p>Error calculating affinity.</p>';
            });
    }
  )};
});

  // Ottieni l'username dalla sessione PHP
  var username = document.getElementById("user_id").textContent;
  fetchTotalRuntime(username)
console.log(username)
  // Se l'username è presente, esegui la richiesta Fetch
  if (username) {
    fetchWatchedMovies(username);
    function fetchWatchedMovies(username) {
      var requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
      };
    
      fetch("http://localhost:8080/api/user/watchedMovies/" + encodeURIComponent(username), requestOptions)
        .then(response => response.json())
        .then(watchedMovies => {
          displayWatchedMovies(watchedMovies);
        })
        .catch(error => console.error('Errore nella richiesta Fetch:', error));
    }
   

    var requestOptions = {
  
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        // Includi eventuali altri header necessari
      },
    
    };

    // Esegui la richiesta Fetch
    fetch("http://localhost:8080/api/user/info/" + encodeURIComponent(username), requestOptions)
      .then(response => {
        if (!response.ok) {
          throw new Error("Errore nella richiesta AJAX");
        }
        return response.json();
      })
      .then(data => {
        // Manipola i dati ottenuti dal server e visualizzali nel DOM
        displayUserInfo(data);
        displayTags(data.tags, username)
        userTags=data.tags;
        console.log(userTags);
        populateCommunityDropdown(username);
      })
      .catch(error => {
        
        console.error("Errore nella richiesta Fetch:", error);
        console.log("Dettagli della risposta:", error.response);
      });
  }

;

function displayUserInfo(userInfo) {
  // Manipola e visualizza le informazioni dell'utente nel DOM
  // Esempio: Aggiorna il testo di un elemento con l'id "user-info"
  var userInfoElement = document.getElementById("user-info");
  userInfoElement.innerHTML = "<h2>" + userInfo.display_name + "</h2><p>Age: " + userInfo.age + "</p><p>City: " + userInfo.city + "</p>";

  // Puoi aggiungere ulteriori manipolazioni del DOM per visualizzare altre informazioni dell'utente
}
function displayTags(tags,username) {
  const tagsContainer = document.getElementById('user-communities');
  tagsContainer.innerHTML = ''; // Pulisci i tags esistenti

  if (tags.length > 0) {
    const tagsParagraph = document.createElement('p');
    tagsParagraph.className = 'user-tags';

    // Crea un array di elementi 'a' (link) per ciascun tag
    const tagLinks = tags.map(tag => {
      const tagLink = document.createElement('a');
      tagLink.href = 'community.php?tag=' + tag+'&username=' + username;; // Aggiungi il tag come parametro nella URL
      tagLink.textContent = tag;
      tagLink.className = 'tag-link';
      return tagLink;
    });
    tagsParagraph.appendChild(document.createTextNode("Community: "));

    // Aggiungi i link dei tag al paragrafo separati da virgole
    for (let i = 0; i < tagLinks.length; i++) {
      
      if (i > 0) {
        
        tagsParagraph.appendChild(document.createTextNode(', '));
      }
      tagsParagraph.appendChild(tagLinks[i]);
    }

    tagsContainer.appendChild(tagsParagraph);
  } else {
    tagsContainer.innerHTML = '<p>No communities</p>';
  }
}

// Esempio di utilizzo:




function fetchHistogramData(username) {
  var requestOptions = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  };

  fetch("http://localhost:8080/api/user/histograminfo/" + encodeURIComponent(username), requestOptions)
    .then(response => response.json())
    .then(histogramData => {
      updateHistogramChart(histogramData);
    })
    .catch(error => console.error('Errore nella richiesta Fetch:', error));
}
function updateHistogramChart(histogramData) {
  function sommaArray(arr) {
    let somma = 0;
    for (let i = 0; i < arr.length; i++) {
      somma += arr[i];
    }
    return somma;
  }
  
  document.getElementById("film-watched").textContent="Total film watched:"+sommaArray(histogramData);
  myChart.data.datasets[0].data = histogramData; // Aggiorna i dati del grafico
  myChart.update(); // Aggiorna il grafico per mostrare i nuovi dati
}
function fetchWatchlist(username) {
  var requestOptions = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  };

  fetch("http://localhost:8080/api/user/watchlisttoWatch/" + encodeURIComponent(username), requestOptions)
    .then(response => response.json())
    .then(watchlistItems => {
      displayToWatchMovies(watchlistItems);
    })
    .catch(error => console.error('Errore nella richiesta Fetch:', error));
}
document.addEventListener("DOMContentLoaded", function () {
  var username = document.getElementById("user_id").textContent;
  if (username) {
    fetchWatchlist(username); // Aggiungi questa riga
  }
});
document.addEventListener("DOMContentLoaded", function () {
  var username = document.getElementById("user_id").textContent;
  if (username) {
    fetchWatchlist(username);
    fetchHistogramData(username); // Aggiungi questa riga per ottenere i dati dell'istogramma
  }
});

function displayWatchedMovies(watchedMovies) {
  var watchedMoviesList = document.getElementById("watchedMovieList"); // Assicurati che questo elemento esista nel tuo HTML
  console.log(watchedMoviesList);
  watchedMoviesList.innerHTML = ''; // Pulisce la lista esistente

  watchedMovies.forEach(movie => {
    var listItem = document.createElement("li");
    listItem.textContent = `${movie.movie_title} (${movie.runtime} min) (${movie.rating_val }/10)`;
    watchedMoviesList.appendChild(listItem);
  });
}

function displayToWatchMovies(watchlistItems) {
  var toWatchMovieList = document.getElementById("toWatchMovieList");
  toWatchMovieList.innerHTML = ''; // Pulisce la lista esistente

  watchlistItems.forEach(item => {
    var listItem = document.createElement("li");
    listItem.textContent = `${item.movie_title} (${item.runtime} min)`;
    toWatchMovieList.appendChild(listItem);
  });
  
}

const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'bar', // Tipo di grafico: barra
    data: {
        labels: ['Mark 1', 'Mark 2', 'Mark 3', 'Mark 4','Mark 5','Mark 6', 'Mark 7', 'Mark 8', 'Mark 9', 'Mark 10'], // Etichette asse X
        datasets: [{
            label: '# Film with mark', // Titolo del dataset
            data: [12, 19, 3, 5,3,4,7,8,8,10], // Dati (sostituire con dati reali)
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)', // Colori per ciascuna barra
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true // Assicura che l'asse Y inizi da zero
            }
      
            
        },
        
        
    }
    
});
document.getElementById('myChart').width = 200;
document.getElementById('myChart').height = 200;

function populateCommunityDropdown(username) {
  const container = document.getElementById('joinable-communities-container');
  container.innerHTML = '<h3>Joinable Communities</h3>'; // Pulisci la lista esistente e aggiungi un titolo
  const communitiesToShow = allCommunities.filter(community => !userTags.includes(community));

  communitiesToShow.forEach(community => {
      const communityElement = document.createElement('button');
      communityElement.textContent = community;
      communityElement.className = 'btn btn-primary m-1'; // Aggiungi classi Bootstrap per lo stile
      communityElement.onclick = function() { joinCommunity(community,username); };
      container.appendChild(communityElement);
  });
}
function joinCommunity(communityname,username) {
  console.log(communityname)

  // Prepara i dati da inviare al server
  const postData = {
      username: document.getElementById('user_id').textContent, // Assicurati che l'ID dell'utente sia corretto
      communityname: communityname
  };

  fetch('http://localhost:8080/api/community/join', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(postData)
  })
  .then(response => {
      if (!response.ok) {
          throw new Error('Network response was not ok.');
      }
      // Reindirizza l'utente alla pagina della community dopo aver aggiunto con successo
    window.location.href = `community.php?tag=${encodeURIComponent(communityname)}&username=${encodeURIComponent(username)}`;
  })
  .catch(error => {
      console.error('Error joining community:', error);
  });
}
function fetchTotalRuntime(username) {
  fetch(`http://localhost:8080/api/user/totalRuntime/${encodeURIComponent(username)}`)
      .then(response => response.json())
      .then(totalRuntime => {
          displayTotalRuntime(totalRuntime);
      })
      .catch(error => console.error('Errore nella richiesta Fetch:', error));
}
function displayTotalRuntime(totalRuntime) {
  // Aggiungi la logica per visualizzare il tempo totale di visione dei film
  document.getElementById('total-runtime').textContent = `Total Runtime: ${totalRuntime} mins`;
}


