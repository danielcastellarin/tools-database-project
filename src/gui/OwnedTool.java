package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OwnedTool {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty price;
    private final SimpleBooleanProperty isLendable;
    private final SimpleBooleanProperty isPurchasable;
    private final SimpleStringProperty categories;

    public OwnedTool(String name, int sale_price, boolean lendable, boolean purchasable, String categories) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(sale_price);
        this.isLendable = new SimpleBooleanProperty(lendable);
        this.isPurchasable = new SimpleBooleanProperty(purchasable);
        this.categories = new SimpleStringProperty(categories);
    }

    public String getName() {
        return name.get();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getCategories() {
        return categories.get();
    }

    public void setCategories(String categories) {
        this.categories.set(categories);
    }
}
