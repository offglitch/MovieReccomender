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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
	public void setRating(int movieId, double newRating)
	{
		// Check if the movieId exists

		if(getRating(movieId) != -1)
		{
			// Iterate the list and update this rating
			// The ref to the head of this list

			MovieRatingNode current = head;

			// We iterate this as long as the movieId node is not found

			while(current != null)
			{
				// Check for the match of the movieId

				if(current.getMovieId() == movieId)
				{
					// If matches, update the rating and return
					current.setMovieRating(newRating);

					//********* Maintain sorted order, add method for this *********

					return;
				}

				// Move to the next movie
				current = current.next();
			}
		}
		else
		{
			// Add a new movie node with this rating
			// ********** SORTED ORDER TO BE MAINTAINED *************
			insertByRating(movieId, newRating);
		}
	}


    /**
     * Return the rating for a given movie. If the movie is not in the list,
     * returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
	public double getRating(int movieId)
	{
		// The ref to the head of this list

		MovieRatingNode current = head;

		// Iterate as long as current is not null
		while(current != null)
		{
			// Check for the match of the movieId

			if(current.getMovieId() == movieId)
			{
				// If matches, return the rating for this particular one

				return current.getMovieRating();
			}
			// Move to the next movie
			current = current.next();
		}
		// -1 is returned if the movieId doesn't exist in the list
		return -1;
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
	public void insertByRating(int movieId, double rating)
    {
        // if the head is null create a new head
        if(head == null)
        {
            head = new MovieRatingNode(movieId, rating);

            return;
        }

        // If this rating is greater than the rating at the head
        // insert it before the head

        else if(rating > head.getMovieRating())
        {
            // Create new node
            MovieRatingNode newNode = new MovieRatingNode(movieId, rating);

            // Add it before the head
            newNode.setNext(head);

            // Head is made point to the new node
            head = newNode;

        }

        // Else, insert it at the right position
        else
            {
            // current points to current node, prev to node before current
            MovieRatingNode current = head.next();
            MovieRatingNode prev = head;

            // We iterate this so long the rating is smaller than the rating of current

            while(current != null) {
                if(movieId > current.getMovieId())
                {
                    MovieRatingNode newNode = new MovieRatingNode(movieId, rating);

                    // After inserting: prev | newNode | current

                    newNode.setNext(current);

                    prev.setNext(newNode);

                    return;

                }

                // Moving

                prev = current;
                current = current.next();

            }

            // Movie rating is smaller than all ratings
            // Add to end of the list

            MovieRatingNode newNode = new MovieRatingNode(movieId, rating);

            prev.setNext(newNode);
        }
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

        // A hashmap to store the otherList ratings

        HashMap<Integer, Double> ratingsMapOther = new HashMap<>();

        // All ratings from other list into this HashMap

        for (MovieRatingNode node : otherList) {
            ratingsMapOther.put(node.getMovieId(), node.getMovieRating());
        }

        //HashMap storing this movie list's ratings

        HashMap<Integer, Double> ratingsMap = new HashMap<>();

        // All ratings into the HashMap
        for (MovieRatingNode node : this) {
            if (ratingsMapOther.containsKey(node.getMovieId()))
                ratingsMap.put(node.getMovieId(), node.getMovieRating());
        }

        for (Iterator<Integer> locate = ratingsMapOther.keySet().iterator(); locate.hasNext(); ) {
            Integer key = locate.next();
            // If the key (Movie) isn't present in list
            // Remove key from first HashMap
            if (!ratingsMap.containsKey(key)) ;
            {
                locate.remove();
            }
        }

        // Now both HashMaps have equal number of keys
        // Just the common movies

        int n = ratingsMapOther.size();
        double sx = 0;
        double sy = 0;
        double sxy = 0;
        double sx2 = 0;
        double sy2 = 0;

        // No common movies -> similarity is -1

        for(Integer key: ratingsMapOther.keySet())
        {
            sx += ratingsMapOther.get(key);
            sy += ratingsMap.get(key);
            sxy += ratingsMapOther.get(key)*ratingsMap.get(key);
            sx2 += ratingsMapOther.get(key)*ratingsMapOther.get(key);
            sy2 += ratingsMap.get(key)*ratingsMap.get(key);
        }


        double numerator = n * sxy - sx * sy;
        double denominator = Math.sqrt(n*sx2-sx*sx)*Math.sqrt(n*sy2-sy*sy);
        similarity = numerator/denominator;
        if (denominator == 0) return 0; // if denominator is 0, then return 0
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
	public MovieRatingsList sublist(int begRating, int endRating)
    {
        MovieRatingsList res = new MovieRatingsList();
        MovieRatingNode current = head;
        while(current != null)
        {
            if(current.getMovieRating() >= begRating && current.getMovieRating() <= endRating)
            {
                res.insertByRating( current.getMovieId(), current.getMovieRating() );
            }
            current = current.next();
        }
        return res;
    }


	/** Traverses the list and prints the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public void print()
	{
		// Refrence to the head node of this list
		MovieRatingNode current = head;

		// Add all ratings to be printed
		// Traverse as long as current is not null
		while(current != null)
        {
			System.out.print(current.getMovieId() + ":" + current.getMovieRating() + "; ");

			// Move to the next
			current = current.next();
		}
		System.out.println();

	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), and should be done in one pass
     * using slow & fast pointers (as described in class).
	 *
	 * @return the middle MovieRatingNode
	 * The idea is that the middle node will move once for every two moves of the fast node
	 * So by the time the fast node reaches the end of the list, the middle node will be in the middle
	 */
	public MovieRatingNode getMiddleNode()
	{
		MovieRatingNode middleNode = head;
		MovieRatingNode fastNode = head;

		// For every two moves of the fastNode, move the middleNode once
		// Counter to calculate the number of nodes passed by the fastNode

		int nodeCount = 0;

		// Until you reach the end of the list

		while(fastNode != null)
		{

			// Move the fast node to point to the next

			fastNode = fastNode.next();
			// increment the node count
			nodeCount++;
			// check if the nodeCount is a multiple of 2 (check if even)
			if(nodeCount%2 == 0 )
			{
				// if yes, update the middleNode

				middleNode = middleNode.next();
			}
		}

		return middleNode;
	}
    /**
     * Returns the median rating (the number that is halfway into the sorted
     * list). To compute it, find the middle node and return it's rating. If the
     * middle node is null, return -1.
     *
     * @return rating stored in the node in the middle of the list
     */
	public double getMedianRating()
	{
        // If the middle node is not null, return its value
        if( getMiddleNode() != null )
        {
            return getMiddleNode().getMovieRating();
        }

        // If middle node is null return -1

        return -1;

	}

    /**
     * Returns a RatingsList that contains n best rated movies. These are
     * essentially first n movies from the beginning of the list. If the list is
     * shorter than size n, it will return the whole list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 5
     */
	public MovieRatingsList getNBestRankedMovies(int n)
	{
		// The new list to be returned

		MovieRatingsList res = new MovieRatingsList();

		// The reference to the head node of this list

		MovieRatingNode current = head;

		// Add the first n ratings from the head of the list
		// Traverse as long as current is not null, and n ratings are not added

		while(current != null && n>0)
		{
			// Add this rating to the new list
			res.insertByRating(current.getMovieId(), current.getMovieRating());

			// Move to the next
			current = current.next();

			n--;
		}

		//Return resulting list

		return res;
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
	public MovieRatingsList getNWorstRankedMovies(int n)
	{
		// list to store the result
		MovieRatingsList result = new MovieRatingsList();
		//slow node
		MovieRatingNode slowNode = head;
		MovieRatingNode fastNode = head;
		// move the fast node n times, so that the number of nodes between fast and slow node are 'n'
		for(int i=0; i<n; i++)
		{
			if(fastNode != null)
			{
				fastNode = fastNode.next();
			}
			// If we aren't able to move the fast node at least n-1 nodes ahead, it means that the list
			// doesn't have n elements. So return null
			else{
				return null;
				}
			}
		// now, the slow node and the fast node are 'n' nodes apart
		// move the fast node and the slow node together until you reach the end of the list
		while(fastNode != null){
			fastNode = fastNode.next();
			slowNode = slowNode.next();
		}
		//At this point, the slow node is at the nth least rated movie. Traverse the list
		//from here and add the nodes to the list and return it
		while (slowNode != null)
		{
			result.insertByRating(slowNode.getMovieId(), slowNode.getMovieRating());
			slowNode = slowNode.next();
		}
		return result;

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
	public MovieRatingsList reverse(MovieRatingNode h)
	{
		MovieRatingsList r = new MovieRatingsList();

		MovieRatingNode temp;

		// Slow pointer starts at null

		MovieRatingNode slow = null;

		// Fast pointer starts at the head

		MovieRatingNode fast = h;

		while(fast != null)
		{
			temp = fast.next();
			fast.setNext(slow);
			slow = fast;
			fast = temp;
		}

		// Set the slow pointer to be the head of the new list

		r.head = slow;

		// Return the reversed list
		return r;
	}

	public Iterator<MovieRatingNode> iterator()
	{

		return new MovieRatingsListIterator(0);
	}

	/**
	 * Inner class, MovieRatingsListIterator
	 * The iterator for the ratings list. Allows iterating over the MovieRatingNode-s of
	 * the list.
	 *
	 */
	private class MovieRatingsListIterator implements Iterator<MovieRatingNode> {

			MovieRatingNode curr = null;

			//***********double check please***********

		public MovieRatingsListIterator(int index)
		{
			for(int i = 0; i < index; i++)
			{
				if(curr!=null)
					curr= curr.next();
			}


		}

		@Override
		public boolean hasNext()
		{
			// we have a next node if the current is not null, since the value at current is the next value to be returned
			return curr != null;
		}

		@Override
		public MovieRatingNode next()
		{
			// we return the value at current node

			// store the reference to the current node
			MovieRatingNode returnValue = curr;

			// move current to the next
			curr = curr.next();

			// return the value stored
			return returnValue;
		}

	}

}
