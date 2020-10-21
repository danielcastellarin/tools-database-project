package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewATool {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty price;
    private final SimpleBooleanProperty isLendable;
    private final SimpleBooleanProperty isPurchasable;
    private final SimpleStringProperty categories;

    public ViewATool(String name, int sale_price, boolean lendable, boolean purchasable, String categories) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(sale_price);
        this.isLendable = new SimpleBooleanProperty(lendable);
        this.isPurchasable = new SimpleBooleanProperty(purchasable);
        this.categories = new SimpleStringProperty(categories);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public boolean isLendable() {
        return isLendable.get();
    }

    public void setIsLendable(boolean isLendable) {
        this.isLendable.set(isLendable);
    }

    public boolean isPurchasable() {
        return isPurchasable.get();
    }

    public void setIsPurchasable(boolean isPurchasable) {
        this.isPurchasable.set(isPurchasable);
    }

    public String getCategories() {
        return categories.get();
    }

    public void setCategories(String categories) {
        this.categories.set(categories);
    }
}
