package gui.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OwnedTool {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty price;
    private final SimpleBooleanProperty isLendable;
    private final SimpleStringProperty categories;


    /**
     * Constructs a new Owned Tool Object
     *
     * @param name       The name of the tool
     * @param sale_price The price of the tool
     * @param lendable   If the tool is available
     * @param categories The category the tool is is
     */
    public OwnedTool(String name, int sale_price, boolean lendable, String categories) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(sale_price);
        this.isLendable = new SimpleBooleanProperty(lendable);
        this.categories = new SimpleStringProperty(categories);
    }

    /**
     * Returns the name of the tool
     *
     * @return A string of the name of tool
     */
    public String getName() {
        return name.get();
    }

    /**
     * Set the name of the tool
     *
     * @param name A string name of the tool
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns the price of the tool
     *
     * @return The price of the tool (int)
     */
    public int getPrice() {
        return price.getValue();
    }

    /**
     * Sets the price of the tool
     *
     * @prama price The price of the tool (int)
     */
    public void setPrice(int price) {
        this.price.set(price);
    }

    /**
     * Returns if the tool is available
     *
     * @return If the tool is available (boolean)
     */
    public boolean isLendable() {
        return isLendable.get();
    }

    /**
     * Sets the availability of the tool
     *
     * @paramn isLendable If the tool is available (boolean)
     */
    public void setIsLendable(boolean isLendable) {
        this.isLendable.set(isLendable);
    }

    /**
     * Returns the categories of the tool
     *
     * @return A string of the tool's categories
     */
    public String getCategories() {
        return categories.get();
    }

    /**
     * Set the categories of the tool
     *
     * @param categories A string of the categories of the tool
     */
    public void setCategories(String categories) {
        this.categories.set(categories);
    }
}
