package gui;

public class Credentials {

//    private static String url = "jdbc:postgresql://reddwarf.cs.rit.edu:5432/p320_19";
//    private static String username = "p320_19";
//    private static String password = "shagiPh8vier5ooxeing";
    private static String url = "jdbc:postgresql://ec2-23-23-88-216.compute-1.amazonaws.com:5432/d7fri40efn9meo";
    private static String username = "uymmlgoszhoocb";
    private static String password = "55be3f22c4312af1e5c2a6ce24f6fdc78e99def5e6ea84c652d752cdfa614d61";

    /**
     * Returns the server url
     *
     * @return url to the server
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Returns the username for postgres
     *
     * @return username for postgres
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Returns the password for postgres
     *
     * @return password for postgres
     */
    public static String getPassword() {
        return password;
    }
}
