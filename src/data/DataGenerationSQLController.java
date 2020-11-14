package data;

import gui.SQLController;

import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class DataGenerationSQLController extends SQLController {

    public static void insertCategories(int tid, List<Integer> cids) {
        for (int cid : cids) {
            String query = "INSERT INTO \"Has\" (tid, cid)" +
                    " " + "VALUES(" + tid + "," + cid + ")";
            performUpdate(query);
        }
    }

    /**
     * Adds new tool
     *
     * @param uid the user id
     * @param toolName tool name
     * @param purchaseDate date of purchase
     * @param sale_price sale price
     * @param categories categories list
     */
    public static void addNewTool(int uid, String toolName,
                                  String purchaseDate, int sale_price,
                                  List<String> categories) {
        int tid = getNextAvailableTID();
        insertNewToolToTool(tid, toolName);
        insertToolToOwns(uid, tid, purchaseDate, sale_price);
        insertCategoriesToHas(tid, categories);
    }

    /**
     * Insert new record into borrow table
     *
     * @param tid tool id
     * @param dueDate date tool is due
     */
    public static void insertNewBorrowRecord(int uid, int tid, String dueDate
            , String currentDate) {
        String query1 = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', '" + currentDate + "')";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = false WHERE tid = " + tid;
        performUpdate(query2);
    }


}
