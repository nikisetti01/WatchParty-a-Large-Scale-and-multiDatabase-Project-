document.addEventListener('DOMContentLoaded', function() {
    const username = getUsername();
    const viewType = getViewType();
    console.log(viewType)
  
    fetchWatchlist(username, viewType);
});

function getUsername() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('username');
}

function getViewType() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('viewType');
}

function fetchWatchlist(username, viewType) {

    // Sostituisci con il tuo endpoint corrett
        let url;
        
        if (viewType === '1') {
            // Endpoint per i film già visti
            url = `http://localhost:8080/api/user/expandwatched/${encodeURIComponent(username)}`;
        } else if (viewType === '0') {
            // Endpoint per i film da vedere
            url = `http://localhost:8080/api/user/expandtowatch/${encodeURIComponent(username)}`;
        } else {
            console.error('Invalid viewType');
            return;
        }
    
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                
                return response.json();
            })
            .then(movies => {
           
                displayMovies(movies, viewType);
            })
            .catch(error => {
                console.error('Error fetching watchlist:', error);
            });
    }
    


    function displayMovies(movies, viewType) { // Aggiungi viewType come parametro
        const movieList = document.getElementById('movie-list');
        console.log(viewType)
        movieList.innerHTML = ''; // Pulisce la lista esistente
    
        movies.forEach(movie => {
            const listItem = document.createElement('li');
            if (viewType === '0') {
             
                // Mostra solo movie_title e runtime per i film già visti
                listItem.innerHTML = `
                    <span class="movie-title">${movie.movie_title}</span>
                    <span class="movie-runtime">${movie.runtime} mins</span>
                `;
            } else {
                // Mostra movie_title, runtime e rating_val per i film da vedere
                listItem.innerHTML = `
                    <span class="movie-title">${movie.movie_title}</span>
                    <span class="movie-runtime">${movie.runtime} mins</span>
                    <span class="movie-rating">${movie.rating_val}/10</span>
                `;
            }
            movieList.appendChild(listItem);
        });
    }