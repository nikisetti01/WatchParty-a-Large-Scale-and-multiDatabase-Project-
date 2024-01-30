const searchForm = document.getElementById("search-form");
const searchInput = document.getElementById("search-input");
const searchResults = document.getElementById("search-results");

searchForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const query = searchInput.value.trim();

  if (query) {
    // Send a request to the server to search for movies by title
    fetch(`http://localhost:8080/api/movies/find/title/${encodeURIComponent(query)}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((movies) => {
        // Clear the existing search results
        searchResults.innerHTML = "";
        // Display the search results
        movies.forEach((movie) => {
          const movieResult = document.createElement("div");
          movieResult.classList.add("movie-result");

          // Funzione di utilità per creare un paragrafo solo se il valore è presente
          const createParagraph = (label, value) => {
            if (value) {
              const paragraph = document.createElement("p");
              paragraph.innerHTML = `${label}: ${value}`;
              movieResult.appendChild(paragraph);
            }
          };

          createParagraph('ID', movie.movie_id);
          createParagraph('Genres', movie.genres ? movie.genres.join(", ") : null);
          createParagraph('Original Language', movie.original_language);
          createParagraph('Overview', movie.overview);
          createParagraph('Production Countries', movie.production_countries ? movie.production_countries.join(", ") : null);
          createParagraph('Release Date', movie.release_date);
          createParagraph('Runtime', movie.runtime);
          createParagraph('Type', movie.type);
          createParagraph('Director', movie.director ? movie.director.join(", ") : null);
          createParagraph('Cast', movie.cast ? movie.cast.join(", ") : null);
          const filmElement = document.createElement('button');
          const addReview=document.createElement('button');
          addReview.textContent="Go to Review";
          filmElement.textContent = 'AddToWatch';
          addReview.onclick=function(){
            window.location.href="http://localhost/frontend/reviews_film.php?movie_id="+encodeURIComponent(movie.movie_id)+"&movie_title="+encodeURIComponent(searchInput.value);
          }
          filmElement.onclick = function() {
            console.log(movie.movie_id + "  "+  searchInput.value+ "  "+ movie.runtime)
            addToWatchlist(movie.movie_id,searchInput.value, movie.runtime);
          };
          movieResult.appendChild(filmElement);
          movieResult.appendChild(addReview);
          

          searchResults.appendChild(movieResult);
        });
      })
      .catch((error) => {
        console.error(error);
        alert("An error occurred while searching for movies.");
      });
    
  }
});


function addToWatchlist(filmId, movie_title,runtime) {
  // Impostazioni per la richiesta fetch
  
  console.log(username)
  const url = 'http://localhost:8080/api/user/addToWatchlisttoWatch/'+username; // Sostituisci con il tuo URL effettivo
  const data = {
      movie_id: filmId,
      movie_title:movie_title,
      runtime: runtime

     
  };

  fetch(url, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          // Aggiungi eventuali altri header richiesti, come l'autenticazione
      },
      body: JSON.stringify(data) // Trasforma l'oggetto dati in una stringa JSON
  })
  .then(response => {
      if(response.ok)
      {
        return response.text();
      } 
        else {
          return response.text().then(error => Promise.reject(error));
      }

  })
  .then(data => {
      console.log('Film aggiunto con successo:', data);
      // Qui puoi gestire la risposta positiva (es. aggiornare l'interfaccia utente)
  })
  .catch(error => {
      console.error('Errore durante aggiunta del film alla watchlist', error);
  });
}

