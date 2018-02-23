package movieRecommender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * A custom linked list that stores user info. Each node in the list is of type
 * UserNode.
 * FILL IN CODE. Also, add other methods as needed.
 *
 * @author okarpenko
 *
 */
public class UsersList {
    private UserNode head = null;
    private UserNode tail = null; // ok to store tail here, will be handy for appending


    /** Insert the rating for the given userId and given movieId.
     *
     * @param userId  id of the user
     * @param movieId id of the movie
     * @param rating  rating given by this user to this movie
     */
    public void insert(int userId, int movieId, double rating)
    {

       // Check if the node already exists

        if(get(userId) != null)
        {
            get(userId).insert(movieId, rating);


            return;
        }

        UserNode newUser = new UserNode(userId);
        newUser.insert(movieId, rating);
        append(newUser);
    }

    /**
     * Append a new node to the list
     * @param newNode a new node to append to the list
     */
    public void append(UserNode newNode)
    {
       if(head == null)
       {
           head = newNode;
           tail = newNode;

       }
       else
       {
           tail.setNext(newNode);
           tail = newNode;
       }
    }

    /** Return a UserNode given userId
     *
     * @param userId id of the user (as defined in ratings.csv)
     * @return UserNode for a given userId
     */
    public UserNode get(int userId)
    {
        UserNode current = head;
        while(current != null)
        {
            if(current.getId() == userId)
            {
                return current;
            }
            current = current.next();
        }
        return null;
    } // get method

    /**
     * The method computes the similarity between the user with the given userid
     * and all the other users. Finds the maximum similarity and returns the
     * "most similar user".
     * Calls computeSimilarity method in class MovieRatingsList/
     *
     * @param userid id of the user
     * @return the node that corresponds to the most similar user
     */
    public UserNode findMostSimilarUser(int userid) {

        int mostSimilarUserID = 0;
        UserNode current = head;
        UserNode myUserNode = this.get(userid);
        double maxSim = -1, similarity;
        while (current != null)
        {
            if (current.getId() == userid)
            {
                current = current.next();
                continue;

            }
            similarity = current.computeSimilarity(myUserNode);

            if (similarity > maxSim)
            {
                maxSim = similarity;
                mostSimilarUserID = current.getId();


            }
            current = current.next();
        }


        UserNode mostSimilarUser = this.get(mostSimilarUserID);

        return mostSimilarUser;

    }

    /** Print UsersList to a file  with the given name in the following format:
     (userid) movieId:rating; movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating; movieId:rating; movieId:rating;
     Info for each userid should be printed on a separate line
     * @param filename name of the file where to output UsersList info
     */
    public void print(String filename)
    {
        // print writer is used to write to a file
        PrintWriter pw = null;

        // try to open the file
        try
        {
            // open the file
            pw = new PrintWriter(new File(filename));
        }
        // if could not open file, catch the exception
        catch(FileNotFoundException e)
        {
            // print message that file not found
            System.out.println("File " + filename + " not found!");

            // return from this method
            return;
        }

        // else, print all users

        // reference to the head of the current list
        UserNode currentUser = head;

        // print all users
        // iterate the list till current is not null
        while( currentUser != null )
        {
            // print this user
            // the toString() method of the userNode class is called to print the contents as above
            pw.println(currentUser);

            // move to the next user
            currentUser = currentUser.next();
        }

        // close the pw writer
        pw.close();
    }
}