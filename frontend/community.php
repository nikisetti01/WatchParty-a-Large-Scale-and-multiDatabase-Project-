<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Community Page</title>
    <link rel="stylesheet" type="text/css" href="css/community.css">
</head>
<body>
    <header>
        <h1>Community: <?php echo htmlspecialchars($_GET['tag']); ?></h1>
       
    <!-- ... esistenti elementi dell'header ... -->
    <div id="recommended-community-section">
        <button id="recommended-community-btn" class="recommended-btn">Loading...</button>
        <p id="suggested-by">Suggested by: Loading...</p>
   


        <!-- Puoi aggiungere altri elementi dell'header qui -->
    </header>
    <main>
        <section id="posts-container">
            <!-- I post verranno caricati qui tramite JavaScript -->
        </section>
        <section id="create-post">
            <input type="text" id="post-title" placeholder="Enter post title here..."> <!-- Campo di input per il titolo -->
            <textarea id="post-content" placeholder="Write your post here..."></textarea> <!-- Area di testo per il contenuto del post -->
            <button id="submit-post">Submit Post</button> <!-- Pulsante per inviare il post -->
        </section>
    </main>
    <script src="js/community.js"></script>
</body>
</html>

