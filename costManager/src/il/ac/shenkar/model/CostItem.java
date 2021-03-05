package il.ac.shenkar.model;

import java.sql.Date;

/**
 * value object,
 * costTransaction represents a user's cost transaction
 * that will be added to his bank account data base.
 * the bank account database holds all user's transactions
 * listed by dates and categories.
 */
public class CostItem {

    private Category category;
    private double cost;
    private Currency currency;
    private String date;
    private String description;
    private int id;

    public CostItem( int id, String category, double cost, String currency, String date, String description) throws CostManagerException {

//        //check if date is according to the format yyyy/mm/dd
//        try {
//            checkDate(date);
//        } catch (CostManagerException e) {
//            e.printStackTrace();
//            throw e;
//        }

        this.id = id;
        this.category = new Category(category);
        this.cost = cost;
        this.setCurrency(currency);
        this.date = date;
        this.description = description;
    }

    public CostItem(String category, double cost, String currency, String date, String description) throws CostManagerException {
        //check if date is according to the format yyyy/mm/dd
//        try {
//            checkDate(date);
//        } catch (CostManagerException e) {
//            e.printStackTrace();
//            throw e;
//        }
        this.id = -1;
        this.category = new Category(category);
        this.cost = cost;
        this.setCurrency(currency);
        this.date = date;
        this.description = description;
    }


    /**
     * object representation by String.
     */
    @Override
    public String toString() {
        return "CostItem: " +
                "category='" + category + ' ' +
                ", cost=" + cost + ' ' +
                ", currency=" + currency + ' ' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ' ';
    }

    /**
     * Overriding equals() to compare two costTransaction objects
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of costTransaction or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof CostItem)) {
            return false;
        }

        // typecast o to costTransaction so that we can compare data members
        CostItem c = (CostItem) o;

        // Compare the data members and return accordingly
        return Double.compare(cost, c.cost) == 0                         // cost
                && category.equals(c.category)                           // category
                && currency.getString().equals(c.currency.getString())   // currency
                && date.equals(c.date)                                   // date
                && description.equals(c.description);                    // description
    }

    // get costTransaction methods:

    public Category getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getDate() {
        return  java.sql.Date.valueOf(date);
    }

    public String getDescription() {
        return description;
    }

    /**
     * check if the currency is correct,
     * if correct -> update the object currency
     * if not -> throws exception.
     */
    public void setCurrency(String currency) throws CostManagerException {
        switch (currency) {
            case "EURO":
                this.currency = Currency.EURO;
                break;
            case "GBP":
                this.currency = Currency.GBP;
                break;
            case "ILS":
                this.currency = Currency.ILS;
                break;
            case "USD":
                this.currency = Currency.USD;
                break;
            default: throw new CostManagerException("wrong currency option");
        }
    }
}
