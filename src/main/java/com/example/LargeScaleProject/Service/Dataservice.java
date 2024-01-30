package com.example.LargeScaleProject.Service;
import com.example.LargeScaleProject.ConsistencyCheck.WatchlistConsistencyChecker;
import com.example.LargeScaleProject.Model.MongoDB.*;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.AgeGroupMovieCountResult;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.UserActivity;
import com.example.LargeScaleProject.Model.httpRequest.Affinity;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.MovieRandomReviewsResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.RatingCountResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.ReviewAggregateResult;
import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Model.httpRequest.*;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbWatchlistservice;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommentService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Dataservice {
        @Autowired
        MongodbReviewService mongodbReviewService;
        @Autowired
        MongodbMovieService mongoDbMovieService;
        @Autowired
        MongodbUserService mongodbUserService;
        @Autowired
        MongodbWatchlistservice mongodbWatchlistservice;
        @Autowired
        Neo4jUserService neo4jUserService;
        @Autowired
        Neo4jPostService neo4jPostService;
        @Autowired
        Neo4jCommunityService neo4jCommunityService;
        @Autowired
        Neo4jCommentService neo4jCommentService;
        @Autowired
    WatchlistConsistencyChecker checker;
        private Map<String, List<Long>> cachedRatings = new ConcurrentHashMap<>();
    private Map<String, Integer> cachedTotalRuntime = new ConcurrentHashMap<>();

        public void registrazioneUser(RegisteredUser user) {
            mongodbUserService.addUser(user);
            User neo4juser = new User(user.getUsername(), user.getDisplay_name());
            neo4jUserService.createUser(neo4juser);
        }

        public RegisteredUser findbyUsername(String username) {
            return mongodbUserService.findByUserName(username).get();
        }

        public void cancellazioneUser(RegisteredUser user) {
            System.out.print(mongodbUserService.deleteUser(user.get_id()));
            Optional<User> neo4juser = neo4jUserService.getUserByUsername(user.getUsername());
            neo4jUserService.deleteUser(neo4juser.get().getId());
        }

        public List<Optional<Movie>> findmoviebytitle(String movie_title) {
            return mongoDbMovieService.findBytitle(movie_title);
        }

        public Optional<Movie> findmoviebyId(String movie_title) {
            return mongoDbMovieService.findMovieId(movie_title);
        }

        public boolean LoginControl(LoginRequest loginRequest) {
            Optional<RegisteredUser> loginuser = mongodbUserService.findByUserName(loginRequest.getUsername());
            return loginuser.isPresent() && (Objects.equals(loginRequest.getPassword(), loginuser.get().getPassword()));
        }
    public boolean AdminLoginControl(LoginRequest loginRequest) {
        Optional<Admin> loginuser = Optional.of(mongodbUserService.findAdmin(loginRequest.getUsername()));
        return Objects.equals(loginRequest.getPassword(), loginuser.get().getAdminpassword());
    }


    public InfoRequest getInfo(String username) {
            RegisteredUser user = mongodbUserService.findByUserName(username).get();
            String[] tags = user.getTags();
            if (tags != null)
                return new InfoRequest(user.getDisplay_name(), user.getPassword(), user.getAge(), user.getCity(), user.getTags());
            else
                return new InfoRequest(user.getDisplay_name(), user.getPassword(), user.getAge(), user.getCity(), new String[0] );

        }



        public boolean addWatchItem(String username, WatchlistAddRequest request) {
            WatchlistItem watchItem = new WatchlistItem(new Movie(request.getMovie_id(), request.getMovie_title(), request.getRuntime()), 0);
            mongodbWatchlistservice.addWatchlistItem(username, watchItem);
            return true;
        }

        public ArrayList<WatchlistAddRequest> toWatchMovie(String username) {
            ArrayList<WatchlistItem> list = mongodbWatchlistservice.findUnwatchedMovies(username).getWatchlist();
            System.out.println(list);
            ArrayList<WatchlistAddRequest> list1 = new ArrayList<>();
            if(list !=null)
             for (WatchlistItem item : list)
                list1.add(new WatchlistAddRequest(item.getMovie().getMovie_id(), item.getMovie().getMovie_title(), item.getMovie().getRuntime()));
            return list1;
        }

        public ArrayList<WatchlistAddRequest> expandWatchedMovie(String username) {

            ArrayList<WatchlistItem> list = mongodbUserService.expandWatchlistWatched(username).get().getWatchlist();
            System.out.println(list);
            ArrayList<WatchlistAddRequest> list1 = new ArrayList<>();
            if(list !=null)
                for (WatchlistItem item : list)
                    list1.add(new WatchlistAddRequest(item.getMovie().getMovie_id(), item.getMovie().getMovie_title(), item.getMovie().getRuntime(), item.getReview().getRating_val()));
           CompletableFuture.runAsync(() -> {
           boolean modified= checker.synchronizeWatchlistWithReviews(username);

               cachedTotalRuntime.put(username,null);
               cachedRatings.put(username,null);



            });
            return list1;

        }

        public ArrayList<WatchlistAddRequest> expandtoWatchMovie(String username) {
            ArrayList<WatchlistItem> list = mongodbUserService.expandWatchlistUnwatched(username).get().getWatchlist();
            System.out.println(list);
            ArrayList<WatchlistAddRequest> list1 = new ArrayList<>();
            if(list !=null)
                for (WatchlistItem item : list)
                    list1.add(new WatchlistAddRequest(item.getMovie().getMovie_id(), item.getMovie().getMovie_title(), item.getMovie().getRuntime()));
            return list1;
        }




        public ArrayList<WatchlistAddRequest> WatchedMovie(String username) {

            ArrayList<WatchlistItem> list = mongodbWatchlistservice.getFirst10WatchedMovies(username).getWatchlist();
            System.out.println(list);
            ArrayList<WatchlistAddRequest> list1 = new ArrayList<>();
            if(list !=null)
                for (WatchlistItem item : list)
                  list1.add(new WatchlistAddRequest(item.getMovie().getMovie_id(), item.getMovie().getMovie_title(), item.getMovie().getRuntime(), item.getReview().getRating_val()));
            return list1;

        }

        public List<ReviewRequest> ReviewbyFilm(String movie_id) {
            List<Review> list = mongodbReviewService.findByMovieId(movie_id);
            List<ReviewRequest> list1 = new ArrayList<>();
            for (Review elem : list)
                list1.add(new ReviewRequest(elem.getAuthorusername(), elem.getRating_val(), elem.getReview()));
            return list1;


        }

        public void AddReview(ReviewRequest reviewRequest) {
                System.out.println(reviewRequest);
            Movie movie = new Movie(reviewRequest.getMovie_id(), reviewRequest.getMovie_title());
            Review review;
            System.out.println(movie.getMovie_id());

            if (reviewRequest.getReview() != null) {
                // Se reviewRequest.getReview() non è nullo, usalo nel costruttore
                review = new Review(reviewRequest.getRating_val(), reviewRequest.getReview(), movie, reviewRequest.getAuthorusername());
            } else {
                // Se reviewRequest.getReview() è nullo, non impostare il campo review o impostalo su un valore di default
                review = new Review(reviewRequest.getRating_val(), "", movie, reviewRequest.getAuthorusername()); // Qui si può impostare una stringa vuota o un altro valore di default
            }

            mongodbReviewService.addReview(review);
            // I reload the Histogram after the add in async way
            CompletableFuture.runAsync(() -> {
                List<Long> ratingVector = ratingcount(reviewRequest.getAuthorusername());
                cachedRatings.put(review.getAuthorusername(), ratingVector);
                updateWatchlistAsync(review.getAuthorusername(), review);
                // I update in an  async way also the watchlist 1

            });
            CompletableFuture.runAsync(()->{
                Integer runtime=mongoDbMovieService.findMovieId(movie.get_id()).get().getRuntime();
                updateTotalRuntimeAsync(review.getAuthorusername(),runtime);



            });


        }
        public Integer getTotalRunTime(String username){
            if (cachedTotalRuntime.get(username)!=null)
             return  cachedTotalRuntime.get(username);
            else
                return mongodbWatchlistservice.calculateUserWatchTime(username);
        }
    private void updateTotalRuntimeAsync( String  username, Integer runtime) {
        CompletableFuture.runAsync(() -> {

            // Recupera totalRuntime dalla cache
            Integer totalRuntime = cachedTotalRuntime.getOrDefault(username, 0);
            if(totalRuntime==0)
                totalRuntime = mongodbWatchlistservice.calculateUserWatchTime(username);

            else// Aggiorna totalRuntime
                totalRuntime += runtime;

                // Salva il totalRuntime aggiornato nella cache
            cachedTotalRuntime.put(username, totalRuntime);


                // Log or other actions if needed
                System.out.println("Total runtime for " + username + " is now: " + totalRuntime);


        });
    }


    private void updateWatchlistAsync(String username, Review review) {
            CompletableFuture.runAsync(() -> {
                Movie moviewatch = mongoDbMovieService.findMovieId(review.getMovie().getMovie_id()).get();
                WatchlistItem watchlistItem = new WatchlistItem(new Movie(moviewatch.getMovie_id(), moviewatch.getMovie_title(), moviewatch.getRuntime()), new Review(review.getRating_val()), 1);
                System.out.println(watchlistItem);
                mongodbWatchlistservice.addWatchlistItem(username, watchlistItem);

            });
        }

        public List<Long> histograminfo(String username) {
            // if it is the first time in the session we calculate the array
            if (!cachedRatings.containsKey(username)) {
                // Se non è presente, calcolalo e aggiungilo alla cache
                List<Long> ratingVector = ratingcount(username);
                cachedRatings.put(username, ratingVector);
            }
            return cachedRatings.get(username);
        }


        public List<Long> ratingcount(String username) {
            List<RatingCountResult> list = mongodbReviewService.findRatingHistogramForUser(username);
            System.out.println(list);
            List<Long> list1 = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                list1.add(0L); // Aggiungi un valore long 0L
            }

            for (int i = 0; i < list.size(); i++) {

                int id = list.get(i).get_id() - 1;
                System.out.println(id);
                list1.set(id, list.get(i).getCount()); // Usa l'indice i per accedere agli elementi di 'list'
            }

            return list1;


        }

        public List<Topfilmrequest> getTopFilm() {
            List<Topfilmrequest> topfilms = new ArrayList<>();
            List<ReviewAggregateResult> reviewAggregateResults = mongodbReviewService.findTopMoviesByReviews();
            for (ReviewAggregateResult elem : reviewAggregateResults) {
                List<MovieRandomReviewsResult> reviews = mongodbReviewService.findRandomReviewsForMovie(elem.get_id());
                System.out.println(reviews);
                topfilms.add(new Topfilmrequest(elem.get_id(), elem.getAvgRating(), elem.getTotalReviews(), reviews));
            }
            return topfilms;

        }

        public List<PostRequest> getCommunityPost(String name) {
            List<Post> posts = neo4jCommunityService.getAllPost(name);


            List<PostRequest> requests = new ArrayList<>();
            for (Post elem : posts) {
                if (elem.getPostId() != null) {
                     List<Comment> comments = neo4jCommentService.Postcomments(elem.getPostId());
                    List<CommentRequest> commentRequests = convertecomments(comments, elem.getPostId());
                    commentRequests.sort(Comparator.comparingInt(CommentRequest::getOrder));
                    String author = null;
                    if (elem.getPostId() != null)
                        author = neo4jPostService.findAuthorbypost(elem.getPostId()).get();
                    requests.add(new PostRequest(author, elem.getPostId(), elem.getTitle(), elem.getText(), commentRequests));
                }
            }
            return requests;

        }

        public List<CommentRequest> convertecomments(List<Comment> comments,String postId){
            List<CommentRequest> commentRequests=new ArrayList<>();
            for(int i=0; i<comments.size(); i++)
                commentRequests.add(new CommentRequest(comments.get(i).getText(), comments.get(i).getOrder(),neo4jCommentService.findauthors(postId).get(i)));

            return commentRequests;
        }
        public Post addPost(PostRequest postRequest,String communityname){
          Post newpost=  new Post(null,postRequest.getText(),postRequest.getTitle());
            neo4jPostService.createPost(newpost);
            neo4jPostService.belongrelationship(newpost.getPostId(),communityname);
            neo4jUserService.postedrelation(postRequest.getAuthor(), newpost.getPostId());
            return  newpost;
        }
        public void addComment(CommentRequest commentRequest, String title){

            neo4jCommentService.createComment(new Comment(commentRequest.getText()), commentRequest.getAuthor(),title);
        }
        public  void joinCommunity(joinRequest joinRequest){
            neo4jUserService.joinCommunity(joinRequest.getUsername(),joinRequest.getCommunityname());
            CompletableFuture.runAsync(() -> {
             RegisteredUser user=   mongodbUserService.findByUserName(joinRequest.getUsername()).get();
             if(user.getTags()!=null) {
                 String[] oldTags = user.getTags();
                 // Calcola la nuova dimensione dell'array
                 int newLength = oldTags.length + 1;
                 // Crea un nuovo array con la nuova dimensione
                 String[] newTags = new String[newLength];
                 // Copia tutti gli elementi dall'array esistente al nuovo array
                 for (int i = 0; i < oldTags.length; i++) {
                     newTags[i] = oldTags[i];
                 }
                 // Aggiungi il nuovo elemento all'ultimo indice del nuovo array
                 newTags[newLength - 1] = joinRequest.getCommunityname();
                 // Assegna il nuovo array all'oggetto RegisteredUser
                 user.setTags(newTags);
             } else
             {String[] Tags=new String[1];
             Tags[0]=joinRequest.getCommunityname();
                user.setTags(Tags);}
                mongodbUserService.update(user);

            });
            }
            public RecommendedCommunity getRecommendedCommunity(String username){
            return neo4jUserService.recommendaComunity(username);
            }
    public String getAffinityMessage(Affinity affinity) {
        switch (affinity) {
            case SuperAffinity:
                return "You've SuperAffinity, let's discover his Watchlist";
            case GoodAffinity:
                return "GoodAffinity, you have some common taste";
            case BadAffinity:
                return "BadAffinity, not so compatible";
            default:
                return "Unknown Affinity";
        }

    }
    public String affinityneo4j(String username1, String username2){
       int aff=neo4jUserService.pathdistance(username1,username2);
       Affinity affinity=Affinity.calculateAffinity(aff);
        return getAffinityMessage(affinity);

    }
    public List <AgeGroupMovieCountResult> ageMovie(){

            return mongodbUserService.countMoviesByAgeGroup();
    }
    public List<UserActivity> getActivity(String name){
        return neo4jCommunityService.getMostActiveUsersInCommunity(name,5);

    }
    public void deleteUser(String username){
       RegisteredUser user=  mongodbUserService.findByUserName(username).get();
       mongodbUserService.deleteUser(user.get_id());
        CompletableFuture.runAsync(() -> {
            neo4jUserService.deleteUserbyusername(username);
        });
        }
    public void deleteMovie(String movie){
        Movie movie1=  mongoDbMovieService.findMovieId(movie).get();
        mongoDbMovieService.DeleteMovie(movie1.get_id());
        List <Review> reviews=mongodbReviewService.findByMovieId(movie);
        for(Review elem: reviews)
            mongodbReviewService.deleteReview(elem.get_id());

    }



    }








