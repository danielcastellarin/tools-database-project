package gui.data;

import gui.SQLController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class OwnedUserTools {

    private List<Integer> tids;
    private List<Integer> salePrices;
    private List<String> toolNames;
    private List<Boolean> lendable;
    private List<String> categories;

    /**
     * Constructs a new Owned User Tools Object
     *
     * @param uid The ID of the user
     */
    public OwnedUserTools(int uid) {
        this.tids = new ArrayList<>();
        this.salePrices = new ArrayList<>();
        this.toolNames = new ArrayList<>();
        this.lendable = new ArrayList<>();
        this.categories = new ArrayList<>();
        SQLController.getUserTools(uid, tids, salePrices, toolNames, lendable, categories);
    }

    /**
     * Returns the list of Tools ID's
     *
     * @return a list of Tool ID's
     */
    public List<Integer> getTids() {
        return tids;
    }

    /**
     * Returns the list of Tool prices
     *
     * @return a list of Tool prices
     */
    public List<Integer> getSalePrices() {
        return salePrices;
    }

    /**
     * Returns the list of Tool names
     *
     * @return a list of Tool names
     */
    public List<String> getToolNames() {
        return toolNames;
    }

    /**
     * Returns a Tool ID based on the index given
     *
     * @param index an index that we're using to access the list of Tool ID's
     * @return a Tool ID
     */
    public Integer getTidFromToolName(int index) {
        return tids.get(index);
    }

    /**
     * Returns a list of booleans that state whether a Tool is available
     *
     * @return the Tool availability list
     */
    public List<Boolean> getLendable() {
        return lendable;
    }

    /**
     * Returns a list of the Tool categories
     *
     * @return the Tool category list
     */
    public List<String> getCategories() {
        return categories;
    }
}
