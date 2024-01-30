<?php
session_start();

if (!isset($_SESSION['username']) || !isset($_GET['type']) || !isset($_GET['viewType'])) {
    header('Location: login.php');
    exit();
}

$username = $_SESSION['username'];
$type = $_GET['type'];
$viewType = $_GET['viewType'];
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Watchlist - <?php echo $type === 'watched' ? 'Watched Movies' : 'Movies to Watch'; ?></title>
    <link rel="stylesheet" href="css/watchlist.css">
</head>
<body>
    <header>
        <h1><?php echo $type === 'watched' ? 'Watched Movies' : 'Movies to Watch'; ?></h1>
    </header>
    <main>
        <section id="watchlist">
            <ul id="movie-list">
                <!-- La lista dei film verrà popolata da JavaScript -->
            </ul>
        </section>
    </main>
    <script src="js/watchlist.js"></script> <!-- Assicurati che il percorso sia corretto -->
</body>
</html>
