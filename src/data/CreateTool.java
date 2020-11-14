package data;

import java.time.LocalDate;
import java.util.Random;

public class CreateTool {

    private Random random;
    private int initialDay;
    private int finalDay;

    public CreateTool() {
        this.random = new Random();
        this.initialDay = (int) LocalDate.of(2019, 11, 13).toEpochDay();
        this.finalDay = (int) LocalDate.of(2020, 11, 13).toEpochDay();
    }

    //
    private double determineReturnProbability(double x) {
        return 1 / (1.1 + Math.exp(-((5 * x) / 6 + .8)));
    }

    private LocalDate createDate(int initialDay, int finalDay) {

        long randomDay = initialDay + random.nextInt(finalDay - initialDay);
        LocalDate purchaseDate = LocalDate.ofEpochDay(randomDay);
        return purchaseDate;
    }

    private void createDataForTool(int tid, int uid, int numUsers) {

        LocalDate datePurchased = createDate(initialDay, finalDay);
        int salePrice = (random.nextInt(8) + 1) * 5;
        LocalDate dateSold = null;
        System.out.println("Owns(" + uid + ", " + tid + ", " + datePurchased + ", " + dateSold + ", " + salePrice + ")");


//        int lendDateStart = 0;
        LocalDate dueDate = null;
        int currentOwner = uid;
        int currentBorrower = -1;
//        int lastSold = 0;

        boolean isLent = false;

        for (LocalDate currentDate = datePurchased; currentDate.isBefore(LocalDate.ofEpochDay(finalDay)); currentDate =
                currentDate.plusDays(1) ) {
            System.out.println("----------------------");
            System.out.println("Current Owner: " + currentOwner);
            System.out.println(currentDate);
            int actionVar = random.nextInt(101) + 1;
            if (isLent) {
                // Update Borrows Entry
                if (actionVar < determineReturnProbability((int) (currentDate.toEpochDay() - dueDate.toEpochDay())) * 100){
                    System.out.println( currentBorrower + " Return Tool to " + currentOwner);
                    currentBorrower = -1;
                    isLent = false;
                }

            } else {
                // Update Owns
                if(actionVar < 2) {
                    System.out.print("Change Price From " + salePrice + " ");
                    salePrice = (random.nextInt(8) + 1) * 5;
                    System.out.println("to " + salePrice);
                // Insert into Owns, Update Date_Sold
                } else if (actionVar < 8) {
                    int newOwner = random.nextInt(numUsers + 1) + 1;
                    System.out.println("Update Date Sold to: " + currentDate);
                    System.out.println(currentOwner + " Sell Tool To: " + newOwner);
                    System.out.println("Date Purchased: " + currentDate);
                    System.out.println("Date Sold: Null");
                    currentOwner = newOwner;
                // Insert into Borrows
                } else if (actionVar < 20) {
                    currentBorrower = random.nextInt(numUsers + 1) + 1;
                    System.out.println(currentOwner + " Lend Tool To: " + currentBorrower);
                    dueDate = createDate((int)currentDate.toEpochDay() + 7,
                            (int)currentDate.toEpochDay() + 21);
                    System.out.println("Lend Date: " + currentDate);
                    System.out.println("Due Date: " + dueDate);
                    System.out.println("Return Date: Null");
                    isLent = true;
                }
            }
            System.out.println("----------------------");
        }
    }

    public static void main(String[] args) {
        CreateTool createTool = new CreateTool();
        createTool.createDataForTool(1, 1, 20);
    }

}
