package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    public BorrowedTool(String name, String owner, String lendDate,
                        String dueDate, String categories) {
        this.name = new SimpleStringProperty(name);
        this.owner = new SimpleStringProperty(owner);
        this.lendDate = new SimpleStringProperty(lendDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.categories = new SimpleStringProperty(categories);
    }

    public String getName() {
        return name.get();
    }

    public String getOwner() {
        return owner.get();
    }

    public String getLendDate() {
        return lendDate.get();
    }


    public String getDueDate() {
        return dueDate.get();
    }


    public String getCategories() {
        return categories.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public void setLendDate(String lendDate) {
        this.lendDate.set(lendDate);
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }

    public void setCategories(String categories) {
        this.categories.set(categories);
    }
}
