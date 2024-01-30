<!DOCTYPE html>
<html>
<head>
    <title>Search User</title>
    <link rel="stylesheet" type="text/css" href="css/search_user.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="header">
            <h1 class="text-center mb-4">Search User</h1>
         
        </div>

        <form id="search-user-form" class="mb-4">
            <div class="input-group">
                <input type="text" id="search-user-input" class="form-control" placeholder="Enter username">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>

        <div id="search-user-results" class="row">
            <!-- I risultati della ricerca degli utenti verranno inseriti qui -->
        </div>
    </div>

    <script src="js/search_user.js"></script>
</body>
</html>
