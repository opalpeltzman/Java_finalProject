package il.ac.shenkar.model;

import java.sql.SQLException;

/**
 * CostManagerException is thrown to specific reasons connected
 * to transaction actions the user can perform.
 * message explains the cause of the exception.
 */
public class CostManagerException extends Exception{
    public CostManagerException(String message){super(message);}

    public CostManagerException(String message, SQLException e){
        super(message);
        printSQLException(e);
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     */
    public static void printSQLException(SQLException e) {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null) {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());

            e = e.getNextException();
        }
    }
}
