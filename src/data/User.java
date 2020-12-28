package data;

import java.util.*;

/**
 * Represents a single user of the application. Tendencies for performing
 * actions in the application are stored here.
 */
public class User {

    private Random random;

    private int lendProb;
    private int sellProb;
    private int priceProb;

    private double returnMod;
    private int priceMod;

    private String high;
    private String med;
    private String low;

    /**
     * Creates a new User with their own randomly generated preferences.
     * These values are organized into three types: probabilities, modifiers,
     * and category preferences.
     *
     * Probabilities are used to determine whether or not a User will perform
     * an action, specifically changing a tool's price, selling, or lending
     * a tool. These will only be accessed when a user owns the tool currently
     * being processed by CreateTool and it checks to see if an action
     * will be completed by the User.
     *
     * Modifiers are used to influence the result of an action. In particular,
     * they will determine how much the User tends to change the price of tools
     * and if the User likes to return tools early or late.
     *
     * Category preferences are used to determine what type of tools the User
     * likes to interact with.
     *
     * Overall, this data structure is intended to help the data represent the
     * realistic use of the application if it were to be used by actual users.
     */
    public User() {
        this.random = new Random();

        priceProb = random.nextInt(6);
        sellProb = random.nextInt(16 - priceProb) + priceProb;
        lendProb = random.nextInt(31 - sellProb) + sellProb;

        priceMod = random.nextInt(11) - 5;
        returnMod = (random.nextInt(31) - 15) * .1;

        high = ""; med = ""; low = "";
        determineCategoryPreference();
    }

    /**
     * Randomly generates the user's preference for certain categories
     * in a three-tier hierarchy. These preferences will be used when
     * determining how likely the user is to borrow/buy a certain tool.
     */
    private void determineCategoryPreference() {
        String catChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Set<Integer> seen = new HashSet<>();
        // FIXME something is up with this loop, when debugging, it adds the character to a string one iteration after it should
        //  still functional tho
        for (int i = 0; i < 11; i++) {
            int catIndex = random.nextInt(26);
            if (seen.contains(catIndex)) {
                i--;
                continue;
            }
            seen.add(catIndex);
            if(i < 3) {
                high += catChars.charAt(catIndex);
            } else if(i < 6) {
                med += catChars.charAt(catIndex);
            } else {
                low += catChars.charAt(catIndex);
            }
        }
    }

    /**
     * Determines the user's preference on acquiring a tool based on its categories.
     * Used to sort all users into a list for determining which users will be
     * more likely to borrow or buy a specific tool.
     *
     * @param categories the categories of the tool
     * @return a number quantifying the user's preference of the tool
     */
    int determineToolNeed(List<String> categories) {
        int need = 0;
        for (String c : categories) {
            need += (high.contains(c) ? 5 : (med.contains(c) ? 3 : (low.contains(c) ? 1 : 0)));
        }
        return need;
    }

    /**
     * Retrieves the increment the user usually changes the price of a tool by
     *
     * @return a number between -5 and 5
     */
    int getPriceModifier() {
        return priceMod;
    }

    /**
     * Retrieves the user's tendency to change a tool's price.
     *
     * @return a number between 0 and 5
     */
    int getPriceProb() {
        return priceProb;
    }

    /**
     * Retrieves the user's tendency to sell a tool.
     *
     * @return a number between the price probability and 15
     */
    int getSellProb() {
        return sellProb;
    }

    /**
     * Retrieves the user's tendency to lend a tool.
     *
     * @return a number between the sell probability and 30
     */
    int getLendProb() {
        return lendProb;
    }

    /**
     * Retrieves the user's tendency to return a tool on-time
     * (-1.5 is early, +1.5 is late).
     * See {@link CreateTool#isToolReturned(double, double, double)}
     * for more information on how the calculation works
     *
     * @return a number between -1.5 and 1.5
     */
    double getReturnMod() {
        return returnMod;
    }
}
