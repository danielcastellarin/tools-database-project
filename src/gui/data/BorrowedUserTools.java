package gui.data;

import gui.SQLController;

import java.util.ArrayList;
import java.util.List;

public class BorrowedUserTools {

    private List<Integer> tids;
    private List<String> toolNames;
    private List<Integer> owners;
    private List<String> ownerNames;
    private List<String> lendDates;
    private List<String> dueDates;
    private List<String> categories;

    /**
     * Constructs a new BorrowedUserTools Object
     *
     * @param uid The user id of the user
     */
    public BorrowedUserTools(int uid) {
        this.tids = new ArrayList<>();
        this.toolNames = new ArrayList<>();
        this.owners = new ArrayList<>();
        this.ownerNames = new ArrayList<>();
        this.lendDates = new ArrayList<>();
        this.dueDates = new ArrayList<>();
        this.categories = new ArrayList<>();
        SQLController.getBorrowedTools(uid, tids, toolNames, owners, ownerNames, lendDates, dueDates, categories);
    }

    /**
     * Returns a list of the TIDs the user has borrowed
     *
     * @return A list of integer TIDs
     */
    public List<Integer> getTids() {
        return tids;
    }

    /**
     * Returns a list of the usernames of owners borrowed from
     *
     * @return A list of Strings of usernames
     */
    public List<String> getOwnerNames() {
        return ownerNames;
    }

    /**
     * Returns a list of the tool names being borrowed
     *
     * @return A list of Strings of tool names
     */
    public List<String> getToolNames() {
        return toolNames;
    }

    /**
     * Returns a list of lend dates
     *
     * @return A list of Strings of lend dates
     */
    public List<String> getLendDates() {
        return lendDates;
    }

    /**
     * Returns a list of due dates
     *
     * @return A list of Strings of due dates
     */
    public List<String> getDueDates() {
        return dueDates;
    }

    /**
     * Returns a list of categories for each tool
     *
     * @return A list of Strings of categories
     */
    public List<String> getCategories() {
        return categories;
    }
}
