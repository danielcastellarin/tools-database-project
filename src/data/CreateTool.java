package data;

import gui.SQLController;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

public class CreateTool {

    private Random random;
    private int initialDay;
    private int finalDay;

    private ArrayList<Integer> userOrder;

    public CreateTool(HashMap<Integer, User> users) {
        this.random = new Random();
        this.initialDay = (int) LocalDate.of(2019, 11, 13).toEpochDay();
        this.finalDay = (int) LocalDate.of(2020, 11, 13).toEpochDay();
        createDataForTool(users);
    }

    /*
    min - the minimum skewed value possible
    max - the maximum skewed value possible
    skew - the degree to which the values cluster around the mode of the distribution; higher values mean tighter clustering
    bias - the tendency of the mode to approach the min, max or midpoint value; positive values bias toward max, negative values toward min
     */
    public double nextSkewedBoundedDouble(double min, double max, double skew, double bias) {
        Random random = new Random();
        double range = max - min;
        double mid = min + range / 2.0;
        double unitGaussian = random.nextGaussian();
        double biasFactor = Math.exp(bias);
        double retval = mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5));
        return retval;
    }

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

    private int findNewUser(int currentOwner) {
        int orderID;
        do {
            orderID = (int) nextSkewedBoundedDouble(1, userOrder.size(), 1, 3);
        } while (userOrder.get(orderID) == currentOwner);

        return userOrder.get(orderID);
    }

    private double determineReturnProbability(double x) {
        return 1 / (1.1 + Math.exp(-((5 * x) / 6 + .8)));
    }

    public Pair<String, List<String>> generateName() {
        String catChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder toolName = new StringBuilder("Tool");
        int numCategories = random.nextInt(4) + 3;
        List<String> categories = new ArrayList<>();
        Set<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < numCategories; i++) {
            int catIndex = random.nextInt(26);
            if (seen.contains(catIndex)) continue;

            toolName.append(catChars.charAt(catIndex));
            seen.add(catIndex);
            categories.add(String.valueOf(catChars.charAt(catIndex)));
        }
        return new Pair<>(toolName.toString(), categories);
    }

    private LocalDate createDate(int initialDay, int finalDay) {
        long randomDay = initialDay + random.nextInt(finalDay - initialDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private void createDataForTool(HashMap<Integer, User> users) {
        LocalDate datePurchased = createDate(initialDay, finalDay);
        int salePrice = (random.nextInt(8) + 1) * 5;
        LocalDate dateSold = null;
        LocalDate dueDate = null;

        Pair<String, List<String>> pair = generateName();
        String toolName = pair.getKey();
        List<String> categories = pair.getValue();

        getUserPreferenceOrder(categories, users);
        int currentOwner = findNewUser(-1);
        int currentBorrower = -1;
        boolean isLent = false;

        // Run query to for database and retrieve its tid
        String query = "SELECT addTool(" + currentOwner + ", '" + toolName + "', '" + datePurchased + "', " + salePrice + ", VARIADIC ARRAY[";
        for (int i = 0; i < categories.size(); i++) {
            query += "'" + categories.get(i) + (i + 1 < categories.size() ? "', " : "'])");
        }
        int tid = SQLController.readInt(query);

        System.out.println("Order for tid " + tid + ": " + userOrder.toString());
        System.out.println("Owns(" + currentOwner + ", " + tid + ", " + datePurchased + ", " + dateSold + ", " + salePrice + ")");

        for (LocalDate currentDate = datePurchased; currentDate.isBefore(LocalDate.ofEpochDay(finalDay)); currentDate =
                currentDate.plusDays(1) ) {
            System.out.println("----------------------");
            System.out.println("Current Owner: " + currentOwner);
            System.out.println(currentDate);
            int actionVar = random.nextInt(101) + 1;
            if (isLent) {                       // Update Borrows Entry
                if (actionVar < determineReturnProbability((int) (currentDate.toEpochDay() - dueDate.toEpochDay())) * 100) {
                    System.out.println( currentBorrower + " Return Tool to " + currentOwner);
                    currentBorrower = -1;
                    isLent = false;
                    SQLController.returnTool(tid, currentDate);
                }
            } else {
                if(actionVar < 2) {             // Update Tool Price in Owns
                    int userMod = users.get(currentOwner).getPriceModifier();
                    int priceChange;
                    do {
                        int offset = random.nextInt(21) - 10;
                        priceChange = (userMod + offset) * 5;
                    } while (priceChange + salePrice <= 0);
                    System.out.println("Change Price from " + salePrice + " to " + (salePrice + priceChange));
                    salePrice += priceChange;
                    SQLController.performUpdate("UPDATE \"Owns\" SET sale_price = " +
                            salePrice + " WHERE date_sold IS NULL AND tid = " + tid);
                } else if (actionVar < 8) {     // Insert into Owns, Update Date_Sold
                    // TODO: Consider organizing ties by user's buyProb
                    int newOwner = findNewUser(currentOwner);
                    String sellQuery = "SELECT sellTool(" + tid + ", " +
                            newOwner + ", " + currentOwner + ", '" + currentDate + "')";
                    if (!SQLController.sellToolFunc(sellQuery)) continue;
                    System.out.println(currentOwner + " Sell Tool To: " + newOwner);
                    System.out.println("Date Purchased: " + currentDate);
                    System.out.println("Date Sold: Null");
                    currentOwner = newOwner;
                } else if (actionVar < 20) {    // Insert into Borrows
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
}
