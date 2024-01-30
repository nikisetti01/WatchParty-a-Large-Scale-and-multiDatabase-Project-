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
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/profile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHjy8FZcZV2qkxTQT5Px4v3D9e6FNfT/9Fqf9W7/5O2Vz8+qvs3iDzB" crossorigin="anonymous"></script>

    <!-- Includi il tuo file JavaScript personale dopo jQuery -->
  </head>
  <body>
    <header>
    <button onclick="window.location.href='logout.php'" class="btn btn-logout">Logout</button>
    <h1 id="user_id"><?php echo htmlspecialchars($_SESSION['username']); ?></h1>
      <nav>
        <ul>
        <li><a href="search_user.php?username=<?php echo $_SESSION['username']; ?>">Find Users</a></li>
          <li><a href="top5films.php">Top Films</a></li>
          <li id="user-communities"></li>
        </ul>
      </nav>
    </header>
    <main>
      <div>
      
  <!-- ... (altri elementi) ... -->
  
      <section id="user-info">
        
        <?php echo("<h2>" . $_SESSION['username'] . "</h2>") ?>
        <p>Information: Example information</p>
      

      </section>
      <h2>Statistics</h2>
        <ul>
          <li id="film-watched">Number of films watched: 10</li>
         
          <li id="total-runtime">Total watch time: 20 hours</li>
       
          <canvas id="myChart" width="100" height="100"></canvas>

              
        
      </section>
      
      </div>
      <!-- ... codice esistente ... -->
<div class="watchlist-section">
  <h3>Watched Movies</h3>
  <ul id="watchedMovieList">
    <!-- Qui puoi inserire i film già visti tramite PHP o JavaScript -->
  </ul>
  <button onclick="window.location.href='search_film.php?username=<?php echo urlencode($_SESSION['username']); ?>'" class="btn btn-plus">+</button>
  <!-- Aggiungi il parametro viewType=0 per i film già visti -->
  <button onclick="window.location.href='watchlist.php?type=watched&viewType=1&username=<?php echo urlencode($_SESSION['username']); ?>'" class="btn btn-expand">Espandi</button>
</div>

<div class="watchlist-section">
  <h3>Movies to Watch</h3>
  <ul id="toWatchMovieList">
    <!-- Qui puoi inserire i film da vedere tramite PHP o JavaScript -->
  </ul>
  <button onclick="window.location.href='search_film.php?username=<?php echo urlencode($_SESSION['username']); ?>'" class="btn btn-plus">+</button>
  <!-- Aggiungi il parametro viewType=1 per i film da vedere -->
  <button onclick="window.location.href='watchlist.php?type=toWatch&viewType=0&username=<?php echo urlencode($_SESSION['username']); ?>'" class="btn btn-expand">Espandi</button>
</div>
<!-- ... codice esistente ... -->


        </div>

      <section id="stats">
        
      <div id="joinable-communities-container">
  <h3>Joinable Communities</h3>
  <!-- Le community joinabili saranno visualizzate qui -->
</div>

    </main>
   
    <script src="js/profile.js"></script>
  </body>
</html>