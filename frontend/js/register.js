    const registerForm=document.getElementById("register-form");
    // Handle registration form submission
    registerForm.addEventListener('submit', (e) => {
        e.preventDefault();
    const username = document.getElementById('username').value;
    const displayName = document.getElementById('display-name').value;
    const password = document.getElementById('password').value;
    const city = document.getElementById('city').value;
    const age = document.getElementById('age').value;

    // Verifica che tutti i campi siano compilati
    if (!username || !displayName || !password || !city || !age) {
        alert('Tutti i campi sono obbligatori');
        return;
    }
        const formData = new FormData(registerForm);
        
        const registrationRequest = {
        
            username: formData.get('username'),
            display_name: formData.get('display-name'),
            password: formData.get('password'),
            city: formData.get('city'),
            age: parseInt(formData.get('age'))
        };

    
        fetch('http://localhost:8080/api/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registrationRequest),
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
            alert("User registered successfully");
            // Puoi reindirizzare l'utente a una pagina di login o fare altre azioni necessarie
        })
        .catch(error => {
            // Gestisci gli errori dalla risposta del server
            console.error(error);
            alert("Registration failed: " + error);
        });
    });

        // Send the user data to the server for registration
        // ...

