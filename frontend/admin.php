<!-- admin.php -->

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" type="text/css" href="css/admin.css">
</head>
<body>
    <header>
        <h1 id="user_id">Admin</h1>
        <nav>
            <ul>
                <li><a href="search_user.php?username=Username">Find Users</a></li>
                <li><a href="top5films.php">Top Films</a></li>
                <li id="user-communities"></li>
            </ul>
        </nav>
    </header>

    <main>
        <section id="chart-section">
            <h2>Statistics</h2>
            <div id="chart-container">
                <canvas id="myChart" width="500" height="500"></canvas>
            </div>
        </section>

        <section id="user-info">
            <h2>Active Users by Community</h2>
            <div id="active-users-table">
            <div id="community-menu">
  <h2>Community Menu</h2>
  <div id="community-buttons">
    <!-- Genera i pulsanti delle community dinamicamente -->
  </div>
</div>

<div id="user-table-container">
  <table id="user-table">
    <!-- Qui verrà generata la tabella dei migliori utenti per la community selezionata -->
  </table>
</div>

                <!-- Qui verrà inserita la tabella degli utenti più attivi per ogni community -->
            </div>
        </section>

        <section id="delete-user-section">
            <h2>Delete User</h2>
            <div id="search-bar">
                <input type="text" placeholder="Search user">
                <button id="delete-user-button">Delete User</button>
            </div>
        </section>
        
        <section id="delete-movie-section">
    <h2>Delete Movie</h2>
    <div id="search-bar">
        <input type="text" placeholder="Search movie" id="movie-input">
        <button id="delete-movie-button" onclick="deleteMovie()">Delete Movie</button>
    </div>
</section>

    </main>

    <script src="js/admin.js"></script>
</body>
</html>
