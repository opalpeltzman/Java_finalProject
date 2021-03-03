package il.ac.shenkar.model;

/**
 * Data Access Object Interface,
 * transactionDao is an interface that has all the actions\methods the user
 * can perform on his bank account data base.
 */
public enum Currency {
    ILS, USD, EURO, GBP;

    /**
     * enum method that returns the enum string representation.
     */
    public String getString() {
        return this.toString();
    }

}
