package data_creation;

import java.util.Random;

public class CreateTool {

    private void createDataForTool(int tid, int uid) {
        // Create initial own record with given tid, uid; random sale_price and purchase_date, date_sold is null
        Random boi = new Random();
        int purchase = 0; // Random         Rand(0, 365)
        int sale_price = 0; // Random           Rand(1, 8) * 5
        int currentday = 365;

        int lendDateStart = 0;
        int dueDate = 0;
        int currentUser = uid;

        int lastSold = 0;

        boolean isLent = false;

        int actionVar = 0; // Random number (1, 100)

        for (int day = purchase; day <= currentday; day++) {
            if (isLent) {
                if (actionVar > 70) {
//                   return tool
                    isLent = false;
                }
            } else {
                if(actionVar < 2) {
//                    changePrice
                } else if (actionVar < 8) {
//                    sell
                    currentUser = 10;   // Rand(1, numUsers)
                } else if (actionVar < 20) {
                    // lend
                    isLent = true;
                } else {
                    // do nothing
                }
            }
        }
    }

}
