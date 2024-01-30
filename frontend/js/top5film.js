document.addEventListener('DOMContentLoaded', function() {
    fetchTopFilms();
});

function fetchTopFilms() {
    // Sostituire con il tuo endpoint API
    fetch('http://localhost:8080/api/movies/top5films')
        .then(response => response.json())
        .then(data => renderFilms(data))
        .catch(error => console.error('Error fetching top films:', error));
}

function renderFilms(films) {
    const container = document.getElementById('films-container');
    container.innerHTML = ''; // Pulisci il contenuto precedente

    films.forEach(film => {
        const filmDiv = document.createElement('div');
        filmDiv.className = 'film';

        // Aggiungi informazioni sul film
        const title = document.createElement('h2');
        title.textContent = `Film ID: ${film.movie_id} (Average Rating: ${film.avgRating.toFixed(2)}, Total Reviews: ${film.totalReviews})`;
        filmDiv.appendChild(title);

        // Aggiungi le migliori recensioni
        const reviewsList = document.createElement('div');
        
        film.topReviews.forEach(topReview => {
            topReview.randomReviews.forEach(review => {
                const reviewDiv = document.createElement('div');
                reviewDiv.className = 'review';
                reviewDiv.textContent = `${review.authorusername}: Rating: ${review.rating_val}`;
                reviewsList.appendChild(reviewDiv);
            });
        });

        filmDiv.appendChild(reviewsList);
        container.appendChild(filmDiv);
    });
}
