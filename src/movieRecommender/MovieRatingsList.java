package movieRecommender;

/**
 * MovieRatingsList.
 * A class that stores movie ratings for a user in a custom singly linked list of
 * MovieRatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail! no size!). The list should be sorted by
 * rating (from highest to smallest).
 * Fill in code in the methods of this class.
 * Do not modify signatures of methods. Not all methods are needed to compute recommendations,
 * but all methods are required for the assignment.
 */

import java.util.Iterator;

public class MovieRatingsList implements Iterable<MovieRatingNode> {

	private MovieRatingNode head;
	// Note: you are not allowed to store the tail or the size of this list


	/**
	 * Changes the rating for a given movie to newRating. The position of the
	 * node within the list should be changed accordingly, so that the list
	 * remains sorted by rating (from largest to smallest).
	 *
	 * @param movieId id of the movie
	 * @param newRating new rating of this movie
	 */
	public void setRating(int movieId, double newRating) {
		// FILL IN CODE

	}

    /**
     * Return the rating for a given movie. If the movie is not in the list,
     * returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
	public double getRating(int movieId) {
		// FILL IN CODE
		return -1; // don't forget to change it

	}


    /**
     * Insert a new node (with a given movie id and a given rating) into the list.
     * Insert it in the right place based on the value of the rating. Assume
     * the list is sorted by the value of ratings, from highest to smallest. The
     * list should remain sorted after this insert operation.
     *
     * @param movieId id of the movie
     * @param rating rating of the movie
     */
	public void insertByRating(int movieId, double rating) {
		// FILL IN CODE. Make sure to test this method thoroughly


	}

    /**
     * Computes similarity between two lists of ratings using Pearson correlation.
	 * https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
	 * Note: You are allowed to use a HashMap for this method.
     *
     * @param otherList another MovieRatingList
     * @return similarity computed using Pearson correlation
     */
    public double computeSimilarity(MovieRatingsList otherList) {
		double similarity = 0;
        // FILL IN CODE


        return similarity;

    }
    /**
     * Returns a sublist of this list where the rating values are in the range
     * from begRating to endRating, inclusive.
     *
     * @param begRating lower bound for ratings in the resulting list
     * @param endRating upper bound for ratings in the resulting list
     * @return sublist of the MovieRatingsList that contains only nodes with
     * rating in the given interval
     */
	public MovieRatingsList sublist(int begRating, int endRating) {
		MovieRatingsList res = new MovieRatingsList();

		// FILL IN CODE
		return res;
	}

	/** Traverses the list and prints the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public void print() {
		// FILL IN CODE
	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), and should be done in one pass
     * using slow & fast pointers (as described in class).
	 *
	 * @return the middle MovieRatingNode
	 */
	public MovieRatingNode getMiddleNode() {

		// FILL IN CODE
		return null; // don't forget to change it
	}

    /**
     * Returns the median rating (the number that is halfway into the sorted
     * list). To compute it, find the middle node and return it's rating. If the
     * middle node is null, return -1.
     *
     * @return rating stored in the node in the middle of the list
     */
	public double getMedianRating() {
		// FILL IN CODE

		return -1; // don't forget to change it
	}

    /**
     * Returns a RatingsList that contains n best rated movies. These are
     * essentially first n movies from the beginning of the list. If the list is
     * shorter than size n, it will return the whole list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 5
     */
	public MovieRatingsList getNBestRankedMovies(int n) {
		// FILL IN CODE

		return null; // don't forget to change
	}

    /**
     * * Returns a RatingsList that contains n worst rated movies for this user.
     * Essentially, these are the last n movies from the end of the list. You are required to
	 * use slow & fast pointers to find the n-th node from the end (as discussed in class).
	 * Note: This method should compute the result in one pass. Do not use size variable
	 * Do NOT use reverse(). Do not destroy the list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 1
     */
	public MovieRatingsList getNWorstRankedMovies(int n) {

		// FILL IN CODE
		return null; // don't forget to change
	}

    /**
     * Return a new list that is the reverse of the original list. The returned
     * list is sorted from lowest ranked movies to the highest rated movies.
     * Use only one additional MovieRatingsList (the one you return) and constant amount
     * of memory. You may NOT use arrays, ArrayList and other built-in Java Collections classes.
     * Read description carefully for requirements regarding implementation of this method.
	 *
     * @param h head of the MovieRatingList to reverse
     * @return reversed list
     */
	public MovieRatingsList reverse(MovieRatingNode h) {
		MovieRatingsList r = new MovieRatingsList();
		// FILL IN CODE

		return r;
	}

	public Iterator<MovieRatingNode> iterator() {

		return new MovieRatingsListIterator(0);
	}

	/**
	 * Inner class, MovieRatingsListIterator
	 * The iterator for the ratings list. Allows iterating over the MovieRatingNode-s of
	 * the list.
	 * FILL IN CODE
	 */
	private class MovieRatingsListIterator implements Iterator<MovieRatingNode> {

		MovieRatingNode curr = null;

		public MovieRatingsListIterator(int index) {
			// FILL IN CODE

		}

		@Override
		public boolean hasNext() {
			// FILL IN CODE

			return true; // don't forget to change
		}

		@Override
		public MovieRatingNode next() {
			// FILL IN CODE
			return null; // don't forget to change
		}

	}

}
