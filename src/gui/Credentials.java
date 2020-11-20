package gui;

public class Credentials {

    private static String url = "jdbc:postgresql://reddwarf.cs.rit.edu:5432/p320_19";
    private static String username = "p320_19";
    private static String password = "shagiPh8vier5ooxeing";

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
