package movieRecommender;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
//import java.io.FileNotFoundException;

/** MovieRecommender. A class that is responsible for:
    - Reading movie and ratings data from the file and loading it in the data structure UsersList.
 *  - Computing movie recommendations for a given user and printing them to a file.
 *  - Computing movie "anti-recommendations" for a given user and printing them to file.
 *  Fill in code in methods of this class.
 *  Do not modify signatures of methods.
 */
public class MovieRecommender {
    private UsersList usersData; // linked list of users
    private HashMap<Integer, String> movieMap; // maps each movieId to the movie title

    public MovieRecommender() {
         movieMap = new HashMap<>();
         usersData = new UsersList();
    }

    /**
     * Read user ratings from the file and save data for each user in this list.
     * For each user, the ratings list will be sorted by rating (from largest to
     * smallest).
     * @param movieFilename name of the file with movie info
     * @param ratingsFilename name of the file with ratings info
     */
    public void loadData(String movieFilename, String ratingsFilename) throws IOException {
        loadMovies(movieFilename);
        loadRatings(ratingsFilename);
    }

    /** Load information about movie ids and titles from the given file.
     *  Store information in a hashmap that maps each movie id to a movie title
     *
     * @param movieFilename csv file that contains movie information.
     *
     */
    private void loadMovies(String movieFilename) throws FileNotFoundException, IOException
    {
        String line = "";
        String comma = ",";
        int ID, firstCommaIndex = 0;
        String MovieName;
        char character;
        boolean isQuoted;

        FileReader reader = new FileReader(movieFilename);
        BufferedReader reader1 = new BufferedReader(reader);

        line = reader1.readLine();
        while((line = reader1.readLine()) != null)
        {
            firstCommaIndex = line.indexOf(comma);
            if (firstCommaIndex == 0) continue;
            ID = Integer.parseInt(line.substring(0, firstCommaIndex));

            character = line.charAt(firstCommaIndex);
            isQuoted = (character == '"' || character == '\'');
            if (isQuoted)
            {
                int firstQuoteIndex = firstCommaIndex + 1;
                String newString = line.substring(firstCommaIndex + 1);
                int secondQuoteIndex = newString.indexOf(character);
                if(secondQuoteIndex != -1 && firstQuoteIndex >= 0)
                {
                    MovieName = newString.substring(0, secondQuoteIndex);

                }
                else
                {
                    continue;
                }

            }
            else
            {
                String newString = line.substring(firstCommaIndex + 1);
                int secondCommaIndex = newString.indexOf(comma);
                if (firstCommaIndex >= 0 && secondCommaIndex < line.length())
                {
                    MovieName = newString.substring(0, secondCommaIndex);
                }
                else
                {
                    continue;
                }

            }

            movieMap.put(ID, MovieName);
        }

        //System.out.println(movieMap);

    }

    /**
     * Load users' movie ratings from the file into UsersList
     * @param ratingsFilename name of the file that contains ratings
     */
    private void loadRatings(String ratingsFilename) throws FileNotFoundException, IOException {
        String line;
        String comma = ",";
        int movieId, userId;
        double rating;
        String[] data;

        FileReader reader1 = new FileReader(ratingsFilename);
        BufferedReader reader2 = new BufferedReader(reader1);

        line = reader2.readLine();
        while ((line = reader2.readLine()) != null) {
            //We will read the userid, movie id , and rating
            data = line.split(comma);
            userId = Integer.parseInt(data[0]);
            movieId = Integer.parseInt(data[1]);
            rating = Double.valueOf(data[2]);
            usersData.insert(userId, movieId, rating);

        }
    }

    /**
     * * Computes up to num movie recommendations for the user with the given user
     * id and prints these movie titles to the given file. First calls
     * findMostSimilarUser and then getFavoriteMovies(num) method on the
     * "most similar user" to get up to num recommendations. Prints movies that
     * the user with the given userId has not seen yet.
     * @param userid id of the user
     * @param num max number of recommendations
     * @param filename name of the file where to output recommended movie titles
     *                 Format of the file: one movie title per each line
     */
        public void findRecommendations(int userid, int num, String filename) {

            PrintWriter pw = null;

            try
            {
                pw = new PrintWriter(new File(filename));

            }
            catch(FileNotFoundException e)
            {
                System.out.println("File " + filename + "not found");
                return;
            }

            UserNode myUserNode = usersData.get(userid);
            UserNode mostSimilarUser = usersData.findMostSimilarUser(userid);
            int[] MSUFavorites  = mostSimilarUser.getFavoriteMovies(num);

            HashSet<Integer> alreadyWatched = myUserNode.alreadyWatched();

            for (int movieId : MSUFavorites){
                if (movieId == 0) continue; // 0 is invalid movieId number
                if (!(alreadyWatched.contains(movieId))){
                    pw.print(movieId + " : " + movieMap.get(movieId) + "\n");
                }
            }
            pw.close();

        }

    /**
     * Computes up to num movie anti-recommendations for the user with the given
     * user id and prints these movie titles to the given file. These are the
     * movies the user should avoid. First calls findMostSimilarUser and then
     * getLeastFavoriteMovies(num) method on the "most similar user" to get up
     * to num movies the most similar user strongly disliked. Prints only
     * those movies to the file that the user with the given userid has not seen yet.
     * Format: one movie title per each line
     * @param userid id of the user
     * @param num max number of anti-recommendations
     * @param filename name of the file where to output anti-recommendations (movie titles)
     */
    public void findAntiRecommendations(int userid, int num, String filename) {

        PrintWriter pw = null;

        try
        {
            pw = new PrintWriter(new File(filename));

        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + filename + "not found");
            return;
        }

        UserNode myUserNode = usersData.get(userid);
        UserNode mostSimilarUser = usersData.findMostSimilarUser(userid);
        int[] MSUAntiFavorites  = mostSimilarUser.getLeastFavoriteMovies(num);

        HashSet<Integer> alreadyWatched = myUserNode.alreadyWatched();

        for (int movieId : MSUAntiFavorites){
            if (movieId == 0) continue; // 0 is invalid movieId number
            if (!(alreadyWatched.contains(movieId))){
                pw.write(movieId + " : " + movieMap.get(movieId) + "\n");
            }
        }
        pw.close();

    }

}
