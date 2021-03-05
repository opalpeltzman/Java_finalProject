package il.ac.shenkar.model;

/**
 * Represents the category for the CostTransaction item
 */
public class Category {
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // get Category methods:
    public String getCategoryName() {
        return categoryName;
    }

    // set Category methods:
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}


