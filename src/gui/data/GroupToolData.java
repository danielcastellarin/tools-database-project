package gui.data;

import gui.Main;
import gui.SQLController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class GroupToolData {


    private List<String> ownerNames;
    private List<Integer> salePrices;
    private List<String> lendDates;
    private List<String> dueDates;
    private List<Boolean> lendable;

    private List<Integer> borrowedTids;
    private List<String> borrowedToolNames;
    private List<String> borrowedCategories;

    private List<Integer> ownedTids;
    private List<String> ownedToolNames;
    private List<String> ownedCategories;


    public GroupToolData() {
        updateBorrowedTools();
        updateOwnedTools();
    }

    public void updateBorrowedTools() {
        this.ownerNames = new ArrayList<>();
        this.lendDates = new ArrayList<>();
        this.dueDates = new ArrayList<>();
        this.borrowedTids = new ArrayList<>();
        this.borrowedToolNames = new ArrayList<>();
        this.borrowedCategories = new ArrayList<>();
        SQLController.getBorrowedTools(Main.getUID(), borrowedTids,
                borrowedToolNames,
                ownerNames, lendDates, dueDates, borrowedCategories);
    }

    public void updateOwnedTools() {
        this.ownedTids = new ArrayList<>();
        this.ownedToolNames = new ArrayList<>();
        this.ownedCategories = new ArrayList<>();
        this.salePrices = new ArrayList<>();
        this.lendable = new ArrayList<>();
        SQLController.getOwnedTools(Main.getUID(), ownedTids, ownedToolNames,
                salePrices
                , lendable, ownedCategories);
    }

    public List<String> getOwnerNames() {
        return ownerNames;
    }


    public List<Integer> getSalePrices() {
        return salePrices;
    }

    public List<String> getLendDates() {
        return lendDates;
    }


    public List<String> getDueDates() {
        return dueDates;
    }


    public List<Boolean> getLendable() {
        return lendable;
    }


    public List<Integer> getBorrowedTids() {
        return borrowedTids;
    }


    public List<String> getBorrowedToolNames() {
        return borrowedToolNames;
    }


    public List<String> getBorrowedCategories() {
        return borrowedCategories;
    }


    public List<Integer> getOwnedTids() {
        return ownedTids;
    }


    public List<String> getOwnedToolNames() {
        return ownedToolNames;
    }


    public List<String> getOwnedCategories() {
        return ownedCategories;
    }

}
