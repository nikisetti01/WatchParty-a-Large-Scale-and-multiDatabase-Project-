<?php
session_start();
$username = isset($_SESSION['username']) ? $_SESSION['username'] : 'Guest';
$movie_id = isset($_GET['movie_id']) ? $_GET['movie_id'] : '';
$movie_title = isset($_GET['movie_title']) ? $_GET['movie_title'] : '';

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movie Reviews</title>
    <script type="text/javascript">
              var movie_id="<?php echo htmlspecialchars($movie_id);?>"
              var username="<?php echo htmlspecialchars($username);?>"
              var movie_title="<?php echo htmlspecialchars($movie_title);?>"

                </script>
    <link rel="stylesheet" type="text/css" href="css/reviews.css">
</head>
<body>
    <header>
        <h1>Movie Reviews</h1>
    </header>
    <main>
        <section id="review-list">
            <h2>Reviews</h2>
         
            <!-- Le recensioni saranno inserite qui tramite JavaScript -->
        </section>

        <section id="add-review">
            <h2>Add a Review to <?php echo htmlspecialchars($movie_title);?></h2>
            <form id="review-form">
                <input type="hidden" id="author-username" value="<?php echo htmlspecialchars($username); ?>">
                <label for="rating">Rating:</label>
                <input type="number" id="rating" min="1" max="10" required>
                <label for="review-text">Review (Optional):</label>
                <textarea id="review-text"></textarea>
                <button type="submit">Submit Review</button>
            </form>
        </section>
    </main>
    <script src="js/reviews.js"></script>
</body>
</html>
