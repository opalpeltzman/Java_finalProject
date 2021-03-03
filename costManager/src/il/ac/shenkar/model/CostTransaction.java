package il.ac.shenkar.model;

import java.sql.Date;

/**
 * value object,
 * costTransaction represents a user's cost transaction
 * that will be added to his bank account data base.
 * the bank account database holds all user's transactions
 * listed by dates and categories.
 */
public class CostTransaction {

    private String category;
    private double cost;
    private Currency currency;
    private String date;
    private String description;

    public CostTransaction(String category, double cost, String currency, String date, String description) throws CostManagerException {

        //check if date is according to the format yyyy/mm/dd
        try {
            checkDate(date);
        } catch (CostManagerException e) {
            e.printStackTrace();
            throw e;
        }

        this.category = category;
        this.cost = cost;
        this.setCurrency(currency);
        this.date = date;
        this.description = description;
    }

    /**
     * check if date is according to the format yyyy/mm/dd
     */
    private void checkDate(String date) throws CostManagerException{

        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) >= '0' && date.charAt(i) <= '9') {
                // charAt(i) is digit
            } else if (date.charAt(i) == '-') {
                // charAt(i) is /
            } else {
                // date is in the wrong format
                throw new CostManagerException("wrong date format");
            }
        }
    }

    /**
     * print the object.
     */
    public void printTransaction(){
        System.out.println("category : " + category
                + ", cost : " + cost + ", currency : " + currency + ", date : " + date
                + ", description : " + description);
    }

    /**
     * object representation by String.
     */
    @Override
    public String toString() {
        return "CostItem {" +
                "category='" + category + ' ' +
                ", cost=" + cost + ' ' +
                ", currency=" + currency + ' ' +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
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
        if (!(o instanceof CostTransaction)) {
            return false;
        }

        // typecast o to costTransaction so that we can compare data members
        CostTransaction c = (CostTransaction) o;

        // Compare the data members and return accordingly
        return Double.compare(cost, c.cost) == 0                         // cost
                && category.equals(c.category)                           // category
                && currency.getString().equals(c.currency.getString())   // currency
                && date.equals(c.date)                                   // date
                && description.equals(c.description);                    // description
    }

    // get costTransaction methods:

    public String getCategory() {
        return category;
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

    // set costTransaction methods:

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public void setDate(String date) throws CostManagerException {
        try {
            checkDate(date);
        } catch (CostManagerException e) {
            e.printStackTrace();
            throw e;
        }
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
