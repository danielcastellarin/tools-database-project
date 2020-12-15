package gui.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class IndividualToolData {
    private final SimpleStringProperty name;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty lendDate;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty categories;
    private final SimpleIntegerProperty price;
    private final SimpleBooleanProperty isLendable;

    //Borrows
    public IndividualToolData(String name, String owner, String lendDate,
                              String dueDate, String categories) {
        this.name = new SimpleStringProperty(name);
        this.owner = new SimpleStringProperty(owner);
        this.lendDate = new SimpleStringProperty(lendDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.categories = new SimpleStringProperty(categories);
        this.price = null;
        this.isLendable = null;
    }

    //Owns
    public IndividualToolData(String name, int sale_price, boolean lendable, String categories) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(sale_price);
        this.isLendable = new SimpleBooleanProperty(lendable);
        this.categories = new SimpleStringProperty(categories);
        this.owner = null;
        this.lendDate = null;
        this.dueDate = null;
    }




    /**
     * Returns the original owner of the tool
     *
     * @return A string of the tool's owner's username
     */
    public String getOwner() {
        return owner.get();
    }

    /**
     * Set the original owner of the tool
     *
     * @param owner A string of the owner of the tool
     */
    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    /**
     * Returns the date the tool was lent
     *
     * @return A string of the date the tool was lent
     */
    public String getLendDate() {
        return lendDate.get();
    }

    /**
     * Set the lend date of the tool
     *
     * @param lendDate A string of the lend date of the tool
     */
    public void setLendDate(String lendDate) {
        this.lendDate.set(lendDate);
    }

    /**
     * Returns the tool's due date
     *
     * @return A string of the tool's due date
     */
    public String getDueDate() {
        return dueDate.get();
    }

    /**
     * Set the due date of the tool
     *
     * @param dueDate A string of the due date of the tool
     */
    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
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
