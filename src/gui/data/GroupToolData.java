package gui.data;

import gui.SQLController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class GroupToolData {

    private List<Integer> tids;
    private List<String> toolNames;
    private List<Integer> owners;
    private List<String> ownerNames;
    private List<String> lendDates;
    private List<String> dueDates;
    private List<String> categories;
    private List<Integer> ownedTids;
    private List<Integer> salePrices;
    private List<String> ownedToolNames;
    private List<Boolean> lendable;
    private List<String> ownedCategories;

    public GroupToolData(int uid) {
        this.tids = new ArrayList<>();
        this.toolNames = new ArrayList<>();
        this.owners = new ArrayList<>();
        this.ownerNames = new ArrayList<>();
        this.lendDates = new ArrayList<>();
        this.dueDates = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.ownedTids = new ArrayList<>();
        this.salePrices = new ArrayList<>();
        this.ownedToolNames = new ArrayList<>();
        this.lendable = new ArrayList<>();
        this.ownedCategories = new ArrayList<>();
        SQLController.getBorrowedTools(uid, tids, toolNames, owners, ownerNames, lendDates, dueDates, categories);
        SQLController.getUserTools(uid, ownedTids, salePrices, ownedToolNames,
                lendable, ownedCategories);
    }


    public List<Integer> getTids() {
        return tids;
    }

    public void setTids(List<Integer> tids) {
        this.tids = tids;
    }

    public List<String> getToolNames() {
        return toolNames;
    }

    public void setToolNames(List<String> toolNames) {
        this.toolNames = toolNames;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public void setOwners(List<Integer> owners) {
        this.owners = owners;
    }

    public List<String> getOwnerNames() {
        return ownerNames;
    }

    public void setOwnerNames(List<String> ownerNames) {
        this.ownerNames = ownerNames;
    }

    public List<String> getLendDates() {
        return lendDates;
    }

    public void setLendDates(List<String> lendDates) {
        this.lendDates = lendDates;
    }

    public List<String> getDueDates() {
        return dueDates;
    }

    public void setDueDates(List<String> dueDates) {
        this.dueDates = dueDates;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Integer> getOwnedTids() {
        return ownedTids;
    }

    public void setOwnedTids(List<Integer> ownedTids) {
        this.ownedTids = ownedTids;
    }

    public List<Integer> getSalePrices() {
        return salePrices;
    }

    public void setSalePrices(List<Integer> salePrices) {
        this.salePrices = salePrices;
    }

    public List<String> getOwnedToolNames() {
        return ownedToolNames;
    }

    public void setOwnedToolNames(List<String> ownedToolNames) {
        this.ownedToolNames = ownedToolNames;
    }

    public List<Boolean> getLendable() {
        return lendable;
    }

    public void setLendable(List<Boolean> lendable) {
        this.lendable = lendable;
    }

    public List<String> getOwnedCategories() {
        return ownedCategories;
    }

    public void setOwnedCategories(List<String> ownedCategories) {
        this.ownedCategories = ownedCategories;
    }
}
