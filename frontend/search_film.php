<?php
session_start();

// Imposta variabili di sessione o gestisci la logica qui
$username = isset($_SESSION['username']) ? $_SESSION['username'] : 'Guest';
$displayName = isset($_SESSION['displayName']) ? $_SESSION['displayName'] : 'Guest User';
?>
<!DOCTYPE html>
<html>
<head>
    <title>Search Film</title>
    <link rel="stylesheet" type="text/css" href="css/search_film.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
          var username="<?php echo htmlspecialchars($username);?>"
        </script>
</head>
<body>
<div class="container mt-4">
    <h1 class="text-center mb-4" id="user">
        <?php if (isset($username) && $username !== "Guest"): ?>
            <!-- Se l'utente è loggato, mostra il link al profilo -->
            <a href="profile.php" class="user"><?php echo htmlspecialchars($username);?></a>
        <?php else: ?>
            <!-- Se l'utente non è loggato o è 'Guest', mostra solo il testo senza il link -->
            <?php echo htmlspecialchars($username); ?>
        <?php endif; ?>
    </h1>
    <h1> Welcome !</h1>
</div>
        <form id="search-form" class="mb-4">
            <div class="input-group">
                <input type="text" id="search-input" class="form-control" placeholder="Enter film title or director">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
        <div id="search-results" class="row">
            <!-- I risultati della ricerca verranno inseriti qui -->
        </div>
    </div>
    <script src="js/search_film.js"></script>
</body>
</html>
