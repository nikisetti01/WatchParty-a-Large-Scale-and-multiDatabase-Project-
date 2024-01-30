var movieId = movie_id;
var authorUsername = username;
var movie_title=movie_title;
console.log(movie_title)

document.addEventListener("DOMContentLoaded", function() {
    fetchReviews(movieId);

    document.getElementById("review-form").addEventListener("submit", function(event) {
        event.preventDefault();
        submitReview(movieId,movie_title);
    });
});

function fetchReviews(movieId) {
    const url = `http://localhost:8080/api/review/findbyfilm/${encodeURIComponent(movieId)}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify({ movieId: movieId })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(reviews => {
        const reviewList = document.getElementById('review-list');
        reviewList.innerHTML = ''; // Pulisce la lista esistente
        reviews.forEach(review => {
            const reviewElement = document.createElement('div');
            reviewElement.innerHTML = `<strong>${review.authorusername}</strong>: ${review.rating_val}/10 - ${review.review ? review.review : ''}`;
            reviewList.appendChild(reviewElement);
        });
    })
    .catch(error => {
        console.error('Errore nella richiesta Fetch:', error);
    });
}

function submitReview(movieId,movie_title) {
    // Ottieni i valori dai campi del form
    const rating = document.getElementById('rating').value;
    const reviewText = document.getElementById('review-text').value;
    const authorUsername = username;

    console.log(movie_title);

    // Crea l'oggetto dati della recensione
    const reviewData = {
        authorusername: authorUsername,
        rating_val: rating,
        review: reviewText,
        movie_id: movieId,
        movie_title:movie_title
    };

    // Invia la richiesta al server
    fetch('http://localhost:8080/api/review/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reviewData)
    })
    .then(response => {
        if(response.ok) {
            return response.text();
        } else {
            return response.text().then(error => Promise.reject(error));
        }
    })
    .then(data => {
        console.log('Success:', data);

        // Aggiungi la nuova recensione all'elenco delle recensioni
        const reviewList = document.getElementById('review-list');
        const reviewElement = document.createElement('div');
        reviewElement.innerHTML = `<strong>${authorUsername}</strong>: ${rating}/10 - ${reviewText}`;
        reviewList.appendChild(reviewElement);

        // Pulisci i campi del modulo dopo aver inviato la recensione
        document.getElementById('rating').value = '';
        document.getElementById('review-text').value = '';
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}
