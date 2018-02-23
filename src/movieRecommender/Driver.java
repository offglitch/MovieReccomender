package movieRecommender;

/** A driver class for the MovieRecommender. In the main method, we
 * create a movie recommender, load movie data from files and compute
 * recommendations and anti-recommendations for a particular user.
 */
public class Driver {
    public static void main(String[] args) {

        MovieRatingsList list =	new	MovieRatingsList();

        list.getMiddleNode();
        list.print();

        //MovieRecommender recommender = new MovieRecommender();

        // movies.csv and ratings.csv should be in the project folder
        //recommender.loadData("movies.csv","ratings.csv");
        //System.out.println("Loaded movie data...");

        //recommender.findRecommendations(3, 15, "recommendations");
        //System.out.println();
        //recommender.findAntiRecommendations(3, 15, "antiRecommendations");

    }
}
