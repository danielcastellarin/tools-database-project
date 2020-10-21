package gui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class UserTools {


    private int uid;
    private List<Integer> tids;
    private List<Integer> salePrices;
    private List<String> toolNames;
    private List<Boolean> lendable;
    private List<Boolean> purchasable;
    private List<String> categories;

    public UserTools(int uid) {
        this.uid = uid;
        this.tids = new ArrayList<>();
        this.salePrices = new ArrayList<>();
        this.toolNames = new ArrayList<>();
        this.lendable = new ArrayList<>();
        this.purchasable = new ArrayList<>();
        this.categories = new ArrayList<>();
        SQLController.getUserTools(uid, tids, salePrices, toolNames, lendable
                , purchasable, categories);
    }

    public List<Integer> getTids() {
        return tids;
    }

    public List<Integer> getSalePrices() {
        return salePrices;
    }

    public List<String> getToolNames() {
        return toolNames;
    }

    public List<Boolean> getLendable() {
        return lendable;
    }

    public List<Boolean> getPurchasable() {
        return purchasable;
    }

    public List<String> getCategories() {
        return categories;
    }
}
