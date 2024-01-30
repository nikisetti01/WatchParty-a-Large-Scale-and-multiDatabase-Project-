<?php
session_start();

// Verifica se il parametro 'userId' o 'username' è presente nella richiesta GET
$userId = isset($_GET['username']) ? $_GET['username'] : '';
$username = isset($_GET['displayName']) ? $_GET['displayName'] : '';

// Carica i dati dell'utente dal server o dal database utilizzando $userId o $username
// ... (logica per caricare i dati dell'utente)

// Se non ci sono dati disponibili, reindirizza alla pagina di errore o gestisci come preferisci


?>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/profile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
      #user-communities a {
  pointer-events: none;
}
      </style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Includi il tuo file JavaScript personale dopo jQuery -->
  </head>
  <body>
    <header>
    <h1 id="user_id"><?php echo htmlspecialchars($_GET['username']);?></h1>
      <nav>
        <ul>
          <li><a href="search_user.php">Find Users</a></li>
          <li id="user-communities"></li>
        </ul>
      </nav>
    </header>
    <main>
      <div>
      <section id="user-info">
        <?php echo("<h2>" . $_GET['displayName'] . "</h2>") ?>
        <p>Information: Example information</p>
      </section>
      <h2>Statistics</h2>
        <ul>
        <button id="affinityButton">Calculate Affinity</button>
    <div id="affinityResult"></div>
    <li id="film-watched">Number of films watched: 10</li>
         
         <li id="total-runtime">Total watch time: 20 hours</li>
      
         <canvas id="myChart" width="100" height="100"></canvas>

              
        
      </section>
      </div>
      <section id="watchlist">
        <h2>My Watchlists</h2>
        <div class="watchlist-section">
          <h3>Watched Movies</h3>
          <ul id="watchedMovieList">
            <!-- Qui puoi inserire i film già visti tramite PHP o JavaScript -->
          </ul>
        



        </div>

        <div class="watchlist-section">
          <h3>Movies to Watch</h3>
          <ul id="toWatchMovieList">
            <!-- Qui puoi inserire i film da vedere tramite PHP o JavaScript -->
          </ul>
         

        </div>
      </section>
      <section id="stats">
     
    </main>
    <script src="js/profile.js"></script>
  </body>
</html>