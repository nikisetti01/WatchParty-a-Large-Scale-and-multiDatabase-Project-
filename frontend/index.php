<?php

session_start();

// Verifica se il parametro 'login-successful' è presente nella richiesta GET
if (isset($_GET['login'])) {
    // Verifica se il login è avvenuto con successo
    $login_successful = $_GET['login'];

    if ($login_successful==1) {
        // Il login è riuscito, puoi procedere con l'autenticazione
        $_SESSION['username'] = isset($_GET['username']) ? $_GET['username'] : '';
        
      
    } else {
        $error_message = "Username or password incorrect.";
    }
}
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="container">
        <h1>Login</h1>
        <p>Welcome!</p>
        <form id="login-form" method="post" action="">
            <input type="hidden" id="login-successful" name="login-successful" value="false">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">Login</button>
            <button type="button" id="admin-login-button" class="button-admin">Login Admin</button> <!-- Nuovo bottone per login Admin -->
        </form>
        <p><?php echo isset($error_message) ? $error_message : ''; ?></p>
        <p>Don't have an account? <a href="register.html">Sign up</a></p>
        <div id="search-film-link">
        <a href="search_film.php">Search Film</a>
        <a href="search_user.php"> Search User </a>
    </div>
        
    </div>
    <script src="js/login.js"></script>
</body>
</html>
