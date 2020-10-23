package gui;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class BorrowedTool {
    private final SimpleStringProperty name;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty lendDate;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty categories;

    /**
     * Constructs a new Borrowed Tool Object
     *
     * @param name       The name of the tool
     * @param owner      The tool's original owner
     * @param lendDate   The date the tool was lent
     * @param dueDate    The date the tool is due
     * @param categories The categories associated with the tool
     */
    public BorrowedTool(String name, String owner, String lendDate,
                        String dueDate, String categories) {
        this.name = new SimpleStringProperty(name);
        this.owner = new SimpleStringProperty(owner);
        this.lendDate = new SimpleStringProperty(lendDate);
        this.dueDate = new SimpleStringProperty(dueDate);
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
     * Returns the original owner of the tool
     *
     * @return A string of the tool's owner's username
     */
    public String getOwner() {
        return owner.get();
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
     * Returns the tool's due date
     *
     * @return A string of the tool's due date
     */
    public String getDueDate() {
        return dueDate.get();
    }

    /**
     * Returns the categories of the tool
     *
     * @return A string of the tool's categories
     */
    public String getCategories() {
        return categories.get();
    }
}