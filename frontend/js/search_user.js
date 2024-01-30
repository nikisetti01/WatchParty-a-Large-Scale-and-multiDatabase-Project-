const searchUserForm = document.getElementById("search-user-form");
const searchUserInput = document.getElementById("search-user-input");
const searchUserResults = document.getElementById("search-user-results");


// Ottenere il valore di 'username' dalla query string




searchUserForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const username = searchUserInput.value.trim();
    console.log(username)
    if (username) {
        console.log(`http://localhost:8080/api/user/find/`);
        const currentUrl = new URL(window.location.href);
        const urlParams = new URLSearchParams(currentUrl.search);
        const username1 = urlParams.get('username');
        console.log(username1); 
      // Imposta le opzioni per la richiesta POST
// Imposta le opzioni per la richiesta POST
var requestOptions = {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
        // Aggiungi altri eventuali header necessari, come token di autenticazione
    },
    body: JSON.stringify({ username: username }) // Invia l'username nel corpo della richiesta
};

// Invia una richiesta al server per cercare gli utenti
fetch("http://localhost:8080/api/user/find", requestOptions)
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(user => {
        console.log( user.display_name);
        // Gestisci qui l'utente ricevuto
        // Reindirizza alla pagina view_profile.php con le informazioni dell'utente
        // Ottenere l'URL corrente
var url = window.location.href;

// Estrai i parametri dall'URL

        window.location.href = `view.php?username=${encodeURIComponent(username)}&displayName=${encodeURIComponent(user.display_name)}&username1=${encodeURIComponent(username1)}`;

    })
    .catch(error => {
        console.error("Errore nella richiesta Fetch:", error);
    });


    ;
    
     
  }
}
);
