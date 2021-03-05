package il.ac.shenkar.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * connection to the derbyDB = the user bank account DB.
 * Model implements all the user <-> DB methods.
 * each method starts the connection to the DB :
 * # define the Derby connection URL to use
 * # Create a connection to the database
 * and release all open resources to avoid unnecessary memory usage
 */
public class Model implements IModel {
    private final String framework = "embedded";
    private final String protocol = "jdbc:derby:";
    private final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String dbName = "bankAccount";
    private final String connectionURL = protocol + dbName + ";create=true";
    private PreparedStatement psInsertCategory;


    /**
     * Model constructor
     */
    public Model() throws CostManagerException {
        try {
            Class.forName(driver);
            buildBankAccountTable();
        } catch (ClassNotFoundException | CostManagerException e) {
            throw new CostManagerException("Error creating DB", (SQLException) e);
        }
    }

    /**
     * The buildBankAccountTable method creates the
     * user account table and the categories available table.
     */
    @Override
    public void buildBankAccountTable() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

            // Create 'categories' table :

            try {
                statement.execute("create table categories(Category_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Category_name varchar(25))");
                System.out.println("Created table- 'categories'");
                // insert default categories to table
                psInsertCategory = connection.prepareStatement(
                        "insert into categories(Category_name) values (?)");
                addCategory(new Category("food"));
                addCategory(new Category("clothes"));
                addCategory(new Category("education"));
                addCategory(new Category("healthcare"));
                addCategory(new Category("maintenance"));

            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    System.out.println("table- 'categories' already exist");
                } else {
                    System.out.println("Error Creating the 'categories' Table");
                    System.out.println("ERROR:" + e.getMessage());
                }
            } catch (CostManagerException e) {
                throw e;
            }
            // Create 'myBankAccount' table :

            statement.execute("create table myBankAccount(Exspense_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Category varchar(25), Cost double, Currency varchar(10), Exspense_date DATE , Description varchar(50))");
            System.out.println("Created table- 'myBankAccount'");

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("X0Y32")) {
                System.out.println("table- 'myBankAccount' already exist");
            } else {
                System.out.println("Error Creating the 'myBankAccount' Table");
                System.out.println("ERROR:" + ex.getMessage());
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
    }

    /**
     * delete table according to the table name sent.
     */
    @Override
    public void dropTable(String name) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        if (name == "myBankAccount") {
            try {
                statement.execute("drop table myBankAccount");

            } catch (SQLException throwables) {
                throw new CostManagerException("problem dropping table", throwables);
            } finally {
                exit();
            }
            System.out.println("Dropped table- 'myBankAccount'");
        } else if (name == "categories") {
            try {
                statement.execute("drop table categories");

            } catch (SQLException throwables) {
                throw new CostManagerException("problem dropping table", throwables);
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException sqle) {
                    throw new CostManagerException("problem releasing data", sqle);
                }
                System.out.println("disconnect from " + dbName);
            }
            System.out.println("Dropped table- 'categories'");
        }
    }

    /**
     * shuts down the Derby data base.
     */
    @Override
    public void shutDown() throws CostManagerException {
        if (framework.equals("embedded")) {
            try {
                // the shutdown=true attribute shuts down Derby
                DriverManager.getConnection("jdbc:derby:;shutdown=true");

            } catch (SQLException se) {
                if (((se.getErrorCode() == 50000)
                        && ("XJ015".equals(se.getSQLState())))) {
                    // we got the expected exception
                    System.out.println("Derby shut down normally");
                    // Note that for single database shutdown, the expected
                    // SQL state is "08006", and the error code is 45000.
                } else {
                    // if the error code or SQLState is different, we have
                    // an unexpected exception (shutdown failed)
                    System.err.println("Derby did not shut down normally");
                    throw new CostManagerException("problem shutting down", se);
                }
            }
        }
    }

    /**
     * return all cost transactions in the bank account DB table.
     */
    @Override
    public List<CostItem> getAllTransactions() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }

        List<CostItem> transactions = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT Exspense_ID, Category, Cost, Currency, Exspense_date, Description FROM myBankAccount " +
                            "ORDER BY Exspense_ID");
            while (resultSet.next()) {
                transactions.add(new CostItem(resultSet.getInt("Exspense_ID"), resultSet.getString("Category"), resultSet.getDouble("Cost"), resultSet.getString("Currency"), (resultSet.getDate("Exspense_date")).toString(), resultSet.getString("Description")));
            }

        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting all Transaction Costs", throwables);
        } catch (CostManagerException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

        return transactions;
    }

    /**
     * according to specific index in the bank account table
     * return the cost transaction=(only one) under that same index
     */
    @Override
    public CostItem getTransByIndex(int index) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        CostItem transaction = null;
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT Exspense_ID, Category, Cost, Currency, Exspense_date, Description FROM myBankAccount " +
                            "WHERE Exspense_ID = " + index + "ORDER BY Exspense_ID");
            while (resultSet.next()) {
                transaction = new CostItem(resultSet.getInt("Exspense_ID"), resultSet.getString("Category"), resultSet.getDouble("Cost"), resultSet.getString("Currency"), (resultSet.getDate("Exspense_date")).toString(), resultSet.getString("Description"));
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting Transaction Costs by index", throwables);
        } catch (CostManagerException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

        return transaction;
    }

    /**
     * according to specific date in the bank account table
     * return all the cost transaction under that same date
     */
    @Override
    public List<CostItem> getTransByDate(String fDate, String lDate) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        List<CostItem> transactions = new ArrayList<>();
        ResultSet resultSet = null;
        //converting string into sql date :
        java.sql.Date convertFDate = java.sql.Date.valueOf(fDate);
        java.sql.Date convertLDate = java.sql.Date.valueOf(lDate);

        String sql = "SELECT * FROM myBankAccount WHERE Exspense_date BETWEEN '" + convertFDate + "' AND '" + convertLDate + "' ORDER BY Exspense_ID";
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                transactions.add(new CostItem(resultSet.getInt("Exspense_ID"), resultSet.getString("Category"), resultSet.getDouble("Cost"), resultSet.getString("Currency"), (resultSet.getDate("Exspense_date")).toString(), resultSet.getString("Description")));
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting Transaction Costs by index", throwables);
        } catch (CostManagerException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

        return transactions;
    }

    /**
     * according to specific category in the bank account table
     * return all the cost transaction under that same category
     */
    @Override
    public List<CostItem> getTransByCate(String category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        List<CostItem> transactions = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT Exspense_ID, Category, Cost, Currency, Exspense_date, Description FROM myBankAccount " +
                            " WHERE Category = '" + category + "' ORDER BY Exspense_ID ");
            while (resultSet.next()) {
                transactions.add(new CostItem(resultSet.getInt("Exspense_ID"), resultSet.getString("Category"), resultSet.getDouble("Cost"), resultSet.getString("Currency"), (resultSet.getDate("Exspense_date")).toString(), resultSet.getString("Description")));
                System.out.println("transactions " + transactions);
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting Transaction Costs by index", throwables);
        } catch (CostManagerException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

        return transactions;
    }

    /**
     * returns all categories in the categories table.
     */
    @Override
    public List<String> getAllCategories() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        List<String> allCategories = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT Category_ID, Category_name FROM categories ORDER BY Category_ID");
            while (resultSet.next()) {
                allCategories.add(resultSet.getString("Category_name"));
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting all categories", throwables);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
        return allCategories;
    }

    /**
     * insert a new cost transaction into the BankAccount table
     */
    @Override
    public void addCostTran(CostItem trans) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }

        try {
            PreparedStatement psInsert;
            psInsert = connection.prepareStatement(
                    "insert into myBankAccount(Category, Cost, Currency, Exspense_date, Description) values (?, ?, ?, ?, ?)");
            psInsert.setString(1, trans.getCategory().getCategoryName());
            psInsert.setDouble(2, trans.getCost());
            psInsert.setString(3, trans.getCurrency().name());
            psInsert.setDate(4, trans.getDate());
            psInsert.setString(5, trans.getDescription());
            psInsert.executeUpdate();
            //commitChanges();
            System.out.println("Inserted new cost transaction to table- 'myBankAccount'");
        } catch (SQLException throwables) {
            throw new CostManagerException("problem adding transaction", throwables);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
    }

    /**
     * check if the category already exist int the categories table
     * if not -> add a new category to the categories table
     * note: only method checkForCate can call this method.
     */
    @Override
    public void addCategory(Category category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        try {
            psInsertCategory = connection.prepareStatement(
                    "insert into categories(Category_name) values (?)");
            psInsertCategory.setString(1, category.getCategoryName());
            psInsertCategory.executeUpdate();
            //commitChanges();
            System.out.println("Inserted new category = " + category + " to table- 'categories'");
        } catch (SQLException throwables) {
            throw new CostManagerException("problem adding category", throwables);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

    }

    /**
     * according to specific index in the bank account table
     * the user can delete a cost transaction row in the DB
     */
    @Override
    public void deleteCostTrans(int index) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        String sql = "SELECT * FROM myBankAccount";
        ResultSet resultSet = null;
        try {
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getInt("Exspense_ID") == index) {
                    resultSet.deleteRow();
                    System.out.println("deleted transaction at index = " + index);
                }
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem deleting transaction", throwables);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
    }

    /**
     * deleting table by name.
     */
    @Override
    public void deleteTable(String tableName) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        if (tableName == "myBankAccount" || tableName == "categories") {
            dropTable(tableName);
        } else {
            throw new CostManagerException("wrong table name");
        }
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqle) {
            throw new CostManagerException("problem releasing data", sqle);
        }
        System.out.println("disconnect from " + dbName);

    }

    /**
     * check if category is in categories:
     * if exist -> continue
     * if not -> add the new category to categories table
     */
    @Override
    public void checkForCate(String category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        String sql;
        int count = 0;
        sql = "select Category_name from categories ORDER BY Category_ID";
        try {
            ResultSet resultSet = null;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (category.equals(resultSet.getString("Category_name"))) {
                    count = count + 1;
                }
            }
            if (count == 0) {
                // category doesn't exist in categories table
                addCategory(new Category(category));
            }

        } catch (SQLException throwables) {
            throw new CostManagerException("problem checking category", throwables);
        } catch (CostManagerException e) {
            throw e;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }

    }

    @Override
    public HashMap<String, Double> getCostsPieChart(String fDate, String lDate) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        }
        HashMap<String, Double> categoryCostsMAp = new HashMap<>();
        ResultSet resultSet = null;

        //converting string into sql date :
        java.sql.Date convertFDate = java.sql.Date.valueOf(fDate);
        java.sql.Date convertLDate = java.sql.Date.valueOf(lDate);
        String sql = "SELECT Category, SUM(Cost) as Cost FROM myBankAccount WHERE Exspense_date BETWEEN '" + convertFDate + "' AND '" + convertLDate + "' GROUP BY Category";
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                categoryCostsMAp.put(resultSet.getString("Category"), resultSet.getDouble("Cost"));
            }
        } catch (SQLException throwables) {
            throw new CostManagerException("problem getting Transaction Costs by index", throwables);
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
        return categoryCostsMAp;
    }

    /**
     * exiting DB -
     * releasing sources and shutting down connections.
     */
    @Override
    public void exit() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        //connection to DB -
        try {
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
            statement = connection.createStatement();
            shutDown();

        } catch (SQLException throwables) {
            throw new CostManagerException("Error creating DB", throwables);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                throw new CostManagerException("problem releasing data", sqle);
            }
            System.out.println("disconnect from " + dbName);
        }
    }

}



