document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('login-form');
    const adminLoginButton = document.getElementById('admin-login-button');

    // Gestisci l'invio del form di login
    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();
        performLogin(false);
    });

    // Gestisci il click sul bottone "Login Admin"
    adminLoginButton.addEventListener('click', function () {
       
        performLogin(true);
    });
});

function performLogin(isAdmin) {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Verifica che tutti i campi siano compilati
    if (!username || !password) {
        alert('Username and password are required');
        return;
    }

    const loginRequest = {
        username: username,
        password: password
    };

    // Scegli l'endpoint in base a se si tratta di un login admin o meno
    const loginUrl = isAdmin ? 'http://localhost:8080/api/admin/login' : 'http://localhost:8080/api/user/login';
    const adminPassword = isAdmin ? password : null; // Usa la password come adminpassword solo se isAdmin è true

    // Invia la richiesta di login al server
    fetch(loginUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(adminPassword ? { ...loginRequest, adminpassword: adminPassword } : loginRequest),
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            return response.text().then(error => Promise.reject(error));
        }
    })
    .then(responseText => {
        // Gestisci la risposta positiva dal server
        console.log(responseText);
        const queryParams = new URLSearchParams(loginRequest);

        // Redirigi l'utente alla pagina appropriata in base al tipo di login
        if (isAdmin) {
            window.location.href = 'admin.php?' + username;
        } else {
            window.location.href = 'profile.php?login=1&username=' + username;
        }
    })
    .catch(error => {
        // Gestisci gli errori dalla risposta del server
        console.error(error);
        alert("Login failed: " + error);
    });
}
