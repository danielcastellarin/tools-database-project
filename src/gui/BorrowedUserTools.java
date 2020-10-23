package gui;

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

    public List<Integer> getTids() {
        return tids;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public List<String> getOwnerNames() {
        return ownerNames;
    }

    public List<String> getToolNames() {
        return toolNames;
    }

    public Integer getTidFromToolName(int index) {
        return tids.get(index);
    }

    public List<String> getLendDates() {
        return lendDates;
    }

    public List<String> getDueDates() {
        return dueDates;
    }

    public List<String> getCategories() {
        return categories;
    }
}
