

document.addEventListener('DOMContentLoaded', function() {
    fetchAndDisplayPosts();
    document.getElementById('submit-post').addEventListener('click', submitPost);
    fetchRecommendedCommunity()

});
function fetchRecommendedCommunity() {
    const username = getAuthorFromURL(); // Assicurati che questa funzione estragga correttamente l'username dall'URL
    console.log(username);
   
    fetch('http://localhost:8080/api/community/recommended/' + username)
        .then(response => response.json())
        .then(data => {
            const recommendedBtn = document.getElementById('recommended-community-btn');
            const suggestedByParagraph = document.getElementById('suggested-by');

            recommendedBtn.textContent = `Join Recommended Community: ${data.comname}`;
            suggestedByParagraph.textContent = `Suggested by: ${data.mostActiveUser}`;

            recommendedBtn.onclick = function() {
                joinRecommendedCommunity(data.comname, username);
            };
        })
        .catch(error => console.error('Error fetching recommended community:', error));
}

function joinRecommendedCommunity(communityName, username) {
    const postData = {
        community: communityName,
        username: username
    };

    fetch('http://localhost:8080/api/community/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(postData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        // Reindirizza l'utente alla pagina della community dopo aver joinato con successo
       joinCommunity(communityName,username)
    })
    .catch(error => {
        console.error('Error joining recommended community:', error);
    });
}

function getAuthorFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('username');
}



function fetchAndDisplayPosts() {
    const communityName = getCommunityTag();
    const savedPosts = localStorage.getItem(communityName);
    if (savedPosts) {
        // Se i post sono già salvati nel Local Storage, li visualizza senza chiamare il server
        const posts = JSON.parse(savedPosts);
        displayPosts(posts); // 
    }else{

    fetch('http://localhost:8080/api/community/show/' + encodeURIComponent(communityName))
        .then(response => response.json())
        .then(posts => {
            localStorage.setItem(communityName, JSON.stringify(posts))
            displayPosts(posts);
         
        })
        .catch(error => console.error('Error fetching posts:', error));
}
    }

function createCommentsSection(post) {
    const commentsDiv = document.createElement('div');
    commentsDiv.className = 'comments-container';

    const commentsHeader = document.createElement('h4');
    commentsHeader.textContent = 'Comments:';
    commentsDiv.appendChild(commentsHeader);

    const commentsList = document.createElement('ul');
    if(post.comment != null)
    post.comment.forEach(comment => {
        const commentLi = document.createElement('li');
        commentLi.className = 'comment';
        commentLi.innerHTML =`<strong>${comment.author}:</strong> ${comment.text}`; ;
        commentsList.appendChild(commentLi);
    });
    commentsDiv.appendChild(commentsList);

    return commentsDiv;
}

function submitComment(buttonElement, postId) {
    const commentInput = buttonElement.previousElementSibling;
    const commentText = commentInput.value;
    const postDiv = this.closest('.post');

    
    if (commentText) {
        // Implementa la logica per inviare il nuovo commento al server
        // Esempio: POST request a http://localhost:8080/api/community/add-comment/{postId}
        
        commentInput.value = ''; // Pulisci la casella di testo dopo l'invio
    } else {
        alert('Please write a comment before submitting.');
    }
}

function getCommunityTag() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('tag');
}
function submitComment(buttonElement) {
    const commentInput = buttonElement.previousElementSibling;
    const commentText = commentInput.value;
    
    // Trova il div del post più vicino e recupera il suo ID
    const postDiv = buttonElement.closest('.post');
    const postId = postDiv.id; // Usa l'ID del div del post come postId

    if (commentText) {
        const commentData = {
            text: commentText,
            author: getAuthorFromURL(), // Assumi che l'autore sia nell'URL o recuperarlo in altro modo
            // Aggiungi altri campi necessari per il tuo CommentRequest
        };

        fetch('http://localhost:8080/api/community/add-comment/' + encodeURIComponent(postId), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(commentData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json();
        })
        .then(comment => {
            // Aggiungi il commento appena creato al postDiv o gestisci la risposta
            const commentsContainer = postDiv.querySelector('.comments-container');
            const newCommentLi = document.createElement('li');
            newCommentLi.className = 'comment';
            newCommentLi.innerHTML = `<strong>${comment.author}:</strong> ${comment.text}`;
            commentsContainer.appendChild(newCommentLi);
            const communityName = getCommunityTag();
    const savedPosts = JSON.parse(localStorage.getItem(communityName) || '[]');
    const postIndex = savedPosts.findIndex(p => p.postId === postId);
    if (postIndex !== -1) {
        savedPosts[postIndex].comment = savedPosts[postIndex].comment || [];
        savedPosts[postIndex].comment.push(comment);
        localStorage.setItem(communityName, JSON.stringify(savedPosts));
    }

            commentInput.value = ''; // Pulisci la casella di testo dopo l'invio
        })
        .catch(error => console.error('Error submitting comment:', error));
    } else {
        alert('Please write a comment before submitting.');
    }
}

function submitPost() {
    const postContent = document.getElementById('post-content').value;
    const postTitle = document.getElementById('post-title').value;
    const communityName = getCommunityTag();
    const author = getAuthorFromURL(); // Recupera l'autore dall'URL

    if (!postContent || !postTitle) {
        alert('Please write a title and content for your post.');
        return;
    }

    const postData = {
        author: author,
             // Genera un UUID univoco per il postId (implementa questa funzione o usa una libreria)
        title: postTitle,
        text: postContent,
        Comment: [] // Inizializza un array vuoto per i commenti
    };

    fetch('http://localhost:8080/api/community/add-post/' + encodeURIComponent(communityName), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(postData)
    })
    .then(response => response.json())
    .then(post => {
        const postsContainer = document.getElementById('posts-container');
        const postDiv = createPostDiv(post);
        postsContainer.appendChild(postDiv);
        const communityName = getCommunityTag();
        const savedPosts = JSON.parse(localStorage.getItem(communityName) || '[]');
        savedPosts.push(post);
        localStorage.setItem(communityName, JSON.stringify(savedPosts));

        document.getElementById('post-content').value = '';
        document.getElementById('post-title').value = '';
    })
    .catch(error => console.error('Error submitting post:', error));
}

function getAuthorFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('username');
}
function createPostDiv(post) {
    const postDiv = document.createElement('div');
    postDiv.className = 'post';
    postDiv.innerHTML = `
        <h3>${post.title} by ${post.author}</h3>
        <p>${post.text}</p>
    `;

    // Aggiungi un contenitore vuoto per i commenti se vuoi gestirli
    const commentsDiv = document.createElement('div');
    commentsDiv.className = 'comments-container';
    postDiv.appendChild(commentsDiv);

    return postDiv;
}
function displayPosts(posts){
    const postsContainer = document.getElementById('posts-container');
    postsContainer.innerHTML = '';

    posts.forEach(post => {
        const postDiv = document.createElement('div');
        postDiv.id=post.postId;
        postDiv.className = 'post';
        postDiv.innerHTML = `
            <h3>${post.title} by ${post.author}</h3>
            <p>${post.text}</p>
        `;

        // Aggiungi i commenti esistenti, se presenti
        const commentsDiv = createCommentsSection(post);
        postDiv.appendChild(commentsDiv);
        
        // Aggiungi la sezione per inserire un nuovo commento
        const newCommentDiv = document.createElement('div');
        newCommentDiv.className = 'new-comment';
        newCommentDiv.innerHTML = `
            <textarea class="comment-input" placeholder="Write your comment..."></textarea>
            <button class="submit-comment" onclick="submitComment(this, '${post.postId}')">Submit Comment</button>
        `;
        postDiv.appendChild(newCommentDiv);
        
        postsContainer.appendChild(postDiv);
    });
    
}
function joinCommunity(communityname,username) {
    console.log(communityname)
  
    // Prepara i dati da inviare al server
    const postData = {
        username: username, // Assicurati che l'ID dell'utente sia corretto
        communityname: communityname
    };
  
    fetch('http://localhost:8080/api/community/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(postData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        // Reindirizza l'utente alla pagina della community dopo aver aggiunto con successo
      window.location.href = `community.php?tag=${encodeURIComponent(communityname)}&username=${encodeURIComponent(username)}`;
    })
    .catch(error => {
        console.error('Error joining community:', error);
    });
  }
  
  