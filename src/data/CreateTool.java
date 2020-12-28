package data;

import gui.SQLController;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

/**
 * Data structure used for creating a new Tool and adding it to the database.
 */
class CreateTool {

    private Random random;

    private ArrayList<Integer> userOrder;

    /**
     * Creates a new Tool and adds it to the database. It then performs actions
     * on the Tool in the database from its creation date to the present day.
     *
     * @param users a map of User data
     */
    CreateTool(HashMap<Integer, User> users) {
        this.random = new Random();

        // Setup Date information
        int initialDay = (int) LocalDate.of(2019, 11, 13).toEpochDay();
        int finalDay = (int) LocalDate.of(2020, 11, 13).toEpochDay();
        LocalDate datePurchased = createDate(initialDay, finalDay);
        LocalDate dateSold = null;
        LocalDate dueDate = null;

        // Generate Tool name, its Categories, and price
        Pair<String, List<String>> pair = generateName();
        String toolName = pair.getKey();
        List<String> categories = pair.getValue();
        int salePrice = (random.nextInt(8) + 1) * 5;

        // Setup Owner/Borrower information
        getUserPreferenceOrder(categories, users);
        int currentOwner = findNewUser(-1);
        int currentBorrower = -1;
        boolean isLent = false;

        // Add Tool to database and retrieve its tid
        String query = "SELECT addTool(" + currentOwner + ", '" + toolName + "', '" + datePurchased + "', " + salePrice + ", VARIADIC ARRAY[";
        for (int i = 0; i < categories.size(); i++) {
            query += "'" + categories.get(i) + (i + 1 < categories.size() ? "', " : "'])");
        }
        int tid = SQLController.readInt(query);

        System.out.println("Order for tid " + tid + ": " + userOrder.toString());
        System.out.println("Owns(" + currentOwner + ", " + tid + ", " + datePurchased + ", " + dateSold + ", " + salePrice + ")");

        // Initialize User preference variables
        int ownerLendProb;
        int ownerSellProb;
        int ownerPriceProb;
        double borrowerReturnMod;

        // Main loop for the tool's lifespan (from date added to the present)
        for (LocalDate currentDate = datePurchased; currentDate.isBefore(LocalDate.ofEpochDay(finalDay)); currentDate =
                currentDate.plusDays(1) ) {
            System.out.println("----------------------");
            System.out.println("Current Owner: " + currentOwner);
            System.out.println(currentDate);
            int actionVar = random.nextInt(101);// + 1;
            if (isLent) {
                borrowerReturnMod = users.get(currentBorrower).getReturnMod();
                double daysUntilDue = (double) (currentDate.toEpochDay() - dueDate.toEpochDay());
                if (isToolReturned(daysUntilDue, borrowerReturnMod, actionVar)) {   // Update Record in Borrows
                    System.out.println( currentBorrower + " Return Tool to " + currentOwner);
                    currentBorrower = -1;
                    isLent = false;
                    SQLController.returnTool(tid, currentDate);
                } else {
                    System.out.println("Currently lent to " + currentBorrower);
                }
            } else {
                // Retrieve User action probabilities
                ownerLendProb = users.get(currentOwner).getLendProb();
                ownerSellProb = users.get(currentOwner).getSellProb();
                ownerPriceProb = users.get(currentOwner).getPriceProb();
                if (actionVar < ownerPriceProb) {             // Update Tool Price in Owns                   OG 2
                    int userMod = users.get(currentOwner).getPriceModifier();
                    int priceChange;
                    do {
                        int offset = random.nextInt(21) - 10;
                        priceChange = (userMod + offset) * 5;
                    } while (priceChange + salePrice <= 0);     // ensure price is positive
                    System.out.println("Change Price from " + salePrice + " to " + (salePrice + priceChange));
                    salePrice += priceChange;
                    SQLController.performUpdate("UPDATE \"Owns\" SET sale_price = " +
                            salePrice + " WHERE date_sold IS NULL AND tid = " + tid);
                } else if (actionVar < ownerSellProb) {     // Insert into Owns, Update Date_Sold           OG 8
                    // TODO: Consider organizing ties by user's buyProb
                    int newOwner = findNewUser(currentOwner);
                    String sellQuery = "SELECT sellTool(" + tid + ", " +
                            newOwner + ", " + currentOwner + ", '" + currentDate + "')";
                    if (!SQLController.sellToolFunc(sellQuery)) continue;
                    System.out.println(currentOwner + " Sell Tool To: " + newOwner);
                    System.out.println("Date Purchased: " + currentDate);
                    System.out.println("Date Sold: Null");
                    currentOwner = newOwner;
                } else if (actionVar < ownerLendProb) {    // Insert into Borrows                          OG 20
                    // TODO: Consider using a different skew for Borrows, slightly more normal
                    //      Also consider organizing ties by user's borrowProb
                    currentBorrower = findNewUser(currentOwner);
                    dueDate = createDate((int)currentDate.toEpochDay() + 7,
                            (int)currentDate.toEpochDay() + 21);
                    System.out.println(currentOwner + " Lend Tool To: " + currentBorrower);
                    System.out.println("Lend Date: " + currentDate);
                    System.out.println("Due Date: " + dueDate);
                    System.out.println("Return Date: Null");
                    SQLController.lendTool(currentBorrower, tid, dueDate, currentDate);
                    isLent = true;
                }
            }
            System.out.println("----------------------");
        }
    }

    /**
     * Finds a date between two other dates. Used for determining when the Tool
     * is first added to the database and determining the Tool's due date.
     *
     * @param initialDay the first date in the range
     * @param finalDay the last date in the range
     * @return a date in between the two dates provided
     */
    private LocalDate createDate(int initialDay, int finalDay) {
        long randomDay = initialDay + random.nextInt(finalDay - initialDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    /**
     * Randomly creates the name and categories for the Tool.
     *
     * @return the name of the Tool and a List of its categories.
     */
    private Pair<String, List<String>> generateName() {
        String catChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder toolName = new StringBuilder("Tool");
        int numCategories = random.nextInt(4) + 3;
        List<String> categories = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < numCategories; i++) {
            int catIndex = random.nextInt(26);
            if (seen.contains(catIndex)) continue;

            toolName.append(catChars.charAt(catIndex));
            seen.add(catIndex);
            categories.add(String.valueOf(catChars.charAt(catIndex)));
        }
        return new Pair<>(toolName.toString(), categories);
    }

    /**
     * Determines the order of Users based on their preference for this
     * Tool's categories. A User with a high index in userOrder means they
     * have a high preference for this Tool, and will be more likely
     * to buy/borrow the tool.
     *
     * @param categories the Tool's categories
     * @param users the Users to be sorted
     */
    private void getUserPreferenceOrder(List<String> categories, HashMap<Integer, User> users) {
        userOrder = new ArrayList<>(users.size());
        ArrayList<Integer> userPreferences = new ArrayList<>(users.size());
        int uid, pos;
        User u;

        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            uid = entry.getKey();
            u = entry.getValue();

            pos = 0;
            int need = u.determineToolNeed(categories);
            for (; pos < userOrder.size(); pos++) {
                if (need < userPreferences.get(pos)) {
                    break;
                }
            }

            userOrder.add(pos, uid);
            userPreferences.add(pos, need);
        }
    }

    /**
     * Generates a random number between two values based on a certain skew.
     * In this case, the skew favors higher values. It is used when determining
     * the next owner/borrower of the Tool.
     *
     * @param min  the minimum possible value
     * @param max  the maximum possible value
     * @param skew the degree to which the values cluster around the mode
     *             of the distribution; higher values mean tighter clustering
     * @param bias the tendency of the mode to approach the min,
     *             max or midpoint value; positive values bias toward max,
     *             negative values toward min
     * @return the index to be chosen for the new owner/borrower of the tool
     */
    private double nextSkewedBoundedDouble(double min, double max, double skew, double bias) {
        double range = max - min;
        double mid = min + range / 2.0;
        double unitGaussian = random.nextGaussian();
        double biasFactor = Math.exp(bias);
        return mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5));
    }

    /**
     * Uses the list of Users ordered by their preference for this Tool
     * to determine who the new owner/borrower of the tool will be.
     *
     * @param currentOwner the uid of the current owner
     * @return the uid of the new owner/borrower
     */
    private int findNewUser(int currentOwner) {
        int orderID;
        do {
            orderID = (int) nextSkewedBoundedDouble(1, userOrder.size(), 1, 3);
        } while (userOrder.get(orderID) == currentOwner);

        return userOrder.get(orderID);
    }

    /**
     * Operates on a sigmoid function (S-curve) to determine whether or not
     * a borrower will return the Tool on a certain day.
     *
     * @param x days until the due date is reached (current - due)
     * @param userMod the tendency of a user to return a tool early or late
     * @param actionVar the number generated for this day (0-100)
     * @return true if the Tool will be successfully returned to the owner
     */
    boolean isToolReturned(double x, double userMod, double actionVar) {
        return actionVar < (1 / (1.2 + Math.exp(-.85 * x + .5 + userMod))) * 100;
    }
}
