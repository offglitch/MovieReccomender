package movieRecommender;

import java.util.HashSet;
import java.util.Iterator;

/** UserNode. The class represents a node in the UsersList.
 *  Stores a userId, a list of ratings of type MovieRatingsList,
 *  and a reference to the "next" user in the list.
 *  FILL IN CODE in methods getFavoriteMovies and getLeastFavoriteMovies
 *
 *  Do not modify signatures of methods.
 *  */
public class UserNode {
    private int userId;
    private MovieRatingsList movieRatings;
    private UserNode nextUser;

    /** A constructor for the UserNode.
     * @param id 	User id
     * */
    public UserNode(int id) {
        userId = id;
        movieRatings = new MovieRatingsList();
        nextUser = null;
    }

    /**
     * Getter for the next reference
     * @return the next node in the linked list of users
     */
    public UserNode next() {
        return nextUser;
    }

    /**
     * Setter for the next reference
     * @param anotherUserNode A user node
     */
    public void setNext(UserNode anotherUserNode) {
        this.nextUser = anotherUserNode;
    }

    /** Return a userId stored in this node */
    public int getId() {
        return userId;
    }

    /** Print info contained in this node:
     *  userId and a list of ratings.
     *  Expected format: (userid) movieId:rating; movieId:rating; movieId:rating; */
    public void print() {
        System.out.print("(" + userId + ") ");
        movieRatings.print();

    }


    /**
     * Add rating info for a given movie to the MovieRatingsList
     *  for this user node
     *
     * @param movieId id of the movie
     * @param rating  rating of the movie
     */
    public void insert(int movieId, double rating) {
        movieRatings.insertByRating(movieId, rating);

    }

    /**
     * Returns an array of user's favorite movies (up to n). These are the
     * movies that this user gave the rating of 5.
     *
     * @param n  the maximum number of movies to return
     * @return array containing movie ids of movies rated as 5 (by this user)
     */
    public int[] getFavoriteMovies(int n) {
        int[] lst = new int[n];
        int i = 0;
        Iterator<MovieRatingNode> iter = this.movieRatings.iterator();
        while (iter.hasNext() && i<n)
        {
            MovieRatingNode mr = iter.next();
            int movieId = mr.getMovieId();
            Double rating  =  mr.getMovieRating();
            if (rating==5){
                lst[i] = movieId;
                i+=1;
            }

        }

        return lst;
    }

    /**
     * Returns an array of movies the user likes the least (up to n). These
     * are the movies that this user gave the rating of 1.
     *
     * @param n the maximum number of movies to return
     * @return array of movie ids of movies rated as 1
     */
    public int[] getLeastFavoriteMovies(int n) {
        int[] lst = new int[n];
        int i = 0;
        Iterator<MovieRatingNode> iter = this.movieRatings.iterator();
        while (iter.hasNext() && i<n)
        {
            MovieRatingNode mr = iter.next();
            int movieId = mr.getMovieId();
            Double rating  =  mr.getMovieRating();
            if (rating==1){
                lst[i] = movieId;
                i+=1;
            }

        }

        return lst;
    }


    /**
     * Computes the similarity of this user with the given "other" user using
     * Pearson correlation - simply calls computeSimilarity method
     * from MovieRatingsList
     *
     * @param otherUser a user to compare the current user with
     * @return similarity score
     */
    public double computeSimilarity(UserNode otherUser) {
        return movieRatings.computeSimilarity(otherUser.movieRatings);
    }
    /**
     * Returns HashSet consisting of all the movie_id  watched by the user.
     */
    public HashSet<Integer> alreadyWatched(){

        Iterator<MovieRatingNode> iter = movieRatings.iterator();
        HashSet<Integer> set = new HashSet<>();
        while (iter.hasNext()){
            MovieRatingNode mr  = iter.next();
            set.add(mr.getMovieId());
        }

        return set;
    }

}


