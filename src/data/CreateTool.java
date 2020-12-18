package data;

import gui.SQLController;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.*;

public class CreateTool {

    private Random random;
    private int initialDay;
    private int finalDay;

    // private HashMap(Int, User)

    public CreateTool() {
        this.random = new Random();
        this.initialDay = (int) LocalDate.of(2019, 11, 13).toEpochDay();
        this.finalDay = (int) LocalDate.of(2020, 11, 13).toEpochDay();
        int numUsers = 53;
        int uid = random.nextInt(numUsers) + 1;
        createDataForTool(uid, numUsers);
        // createDataForTool(User)
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

    private void createDataForTool(int uid, int numUsers) {
        LocalDate datePurchased = createDate(initialDay, finalDay);
        int salePrice = (random.nextInt(8) + 1) * 5;
        LocalDate dateSold = null;

        Pair<String, List<String>> pair = generateName();
        // Pair<String, List<String>> pair = generateName(User); passed in thru createDataForTool
        String toolName = pair.getKey();
        List<String> categories = pair.getValue();

        String query = "SELECT addTool(" + uid + ", '" + toolName + "', '" + datePurchased + "', " + salePrice + ", VARIADIC ARRAY[";
        for (int i = 0; i < categories.size(); i++) {
            query += "'" + categories.get(i) + (i + 1 < categories.size() ? "', " : "'])");
        }
        int tid = SQLController.readInt(query);
        System.out.println("Owns(" + uid + ", " + tid + ", " + datePurchased + ", " + dateSold + ", " + salePrice + ")");

        LocalDate dueDate = null;
        int currentOwner = uid;
        int currentBorrower = -1;

        boolean isLent = false;

        for (LocalDate currentDate = datePurchased; currentDate.isBefore(LocalDate.ofEpochDay(finalDay)); currentDate =
                currentDate.plusDays(1) ) {
            System.out.println("----------------------");
            System.out.println("Current Owner: " + currentOwner);
            System.out.println(currentDate);
            int actionVar = random.nextInt(101) + 1;
            if (isLent) {
                // Update Borrows Entry
                if (actionVar < determineReturnProbability((int) (currentDate.toEpochDay() - dueDate.toEpochDay())) * 100) {
                    System.out.println( currentBorrower + " Return Tool to " + currentOwner);
                    currentBorrower = -1;
                    isLent = false;
                    SQLController.returnTool(tid, currentDate);
                }
            } else {
                // Update Owns
                if(actionVar < 2) {
                    System.out.print("Change Price From " + salePrice + " ");
                    salePrice = (random.nextInt(8) + 1) * 5;
                    System.out.println("to " + salePrice);
                    SQLController.performUpdate("UPDATE \"Owns\" SET sale_price = " +
                            salePrice + " WHERE date_sold IS NULL AND tid = " + tid);
                // Insert into Owns, Update Date_Sold
                } else if (actionVar < 8) {
                    int newOwner = random.nextInt(numUsers) + 1;
                    String sellQuery = "SELECT sellTool(" + tid + ", " +
                            newOwner + ", " + currentOwner + ", '" + currentDate + "')";
                    boolean success = SQLController.sellToolFunc(sellQuery);
                    if (newOwner == currentOwner || !success) continue;
                    System.out.println(currentOwner + " Sell Tool To: " + newOwner);
                    System.out.println("Date Purchased: " + currentDate);
                    System.out.println("Date Sold: Null");
                    currentOwner = newOwner;
                // Insert into Borrows
                } else if (actionVar < 20) {
                    currentBorrower = random.nextInt(numUsers) + 1;
                    if (currentBorrower == currentOwner) continue;
                    System.out.println(currentOwner + " Lend Tool To: " + currentBorrower);
                    dueDate = createDate((int)currentDate.toEpochDay() + 7,
                            (int)currentDate.toEpochDay() + 21);
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
