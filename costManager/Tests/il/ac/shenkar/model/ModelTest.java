package il.ac.shenkar.model;

import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * BankDEmbeddedTest include test units for the cost transactions
 * actions\methods the user can perform on his bank account data base.
 */
public class ModelTest extends TestCase {

    public static IModel transaction;
    public static CostItem trans = null;
    public static CostItem trans1 = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("SETUP!");
        try {
            transaction = new Model();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        try {
            trans1 = new CostItem("shoes", 400, "ILS", "2019-12-16", "new transaction");
            trans = new CostItem("food", 10, "ILS", "2019-10-13", "new transaction");

        } catch (CostManagerException costManagerException) {
            costManagerException.printStackTrace();
        }
        // add transaction to table "myBankAccount"
        try {
            transaction.addCostTran(trans1);
            transaction.addCostTran(trans);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method tests the get transaction by
     * specific index.
     */
    @Test
    public void testGetTransByIndex(){
        System.out.println("TEST: testGetTransByIndex");
        try {
            int index = 1;
            CostItem tran = transaction.getTransByIndex(index);
            assertEquals("objects should be equal",trans1, tran);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the get transaction by
     * specific index that doesnt exist.
     */
    @Test
    public void testGetTransByNotExistIndex(){
        System.out.println("TEST: testGetTransByNotExistIndex");
        try {
            int index = 7;
            CostItem tran = transaction.getTransByIndex(index);
            assertNull("objects should be null, because the index doesnt exist", tran);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the get transaction by
     * specific category.
     */
    @Test
    public void testGetTransCate(){
        System.out.println("TEST: testGetTransCate");
        try {
            List<CostItem> catTransactions = new ArrayList<CostItem>(transaction.getTransByCate("shoes"));
            List<CostItem> catTransactionsExpected = new ArrayList<CostItem>();
            catTransactionsExpected.add(trans1);
            assertEquals("objects should be equal",catTransactionsExpected, catTransactions);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the get transaction by
     * specific date.
     */
    @Test
    public void testGetTransDate(){
        System.out.println("TEST: testGetTransDate");
        try {
            List<CostItem> dateTransactions = new ArrayList<CostItem>(transaction.getTransByDate("2018-10-01", "2020-10-30"));
            List<CostItem> dateTransactionsExpected = new ArrayList<CostItem>();
            dateTransactionsExpected.add(trans1);
            dateTransactionsExpected.add(trans);
            assertEquals("objects should be equal",dateTransactionsExpected, dateTransactions);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the get all transactions.
     */
    @Test
    public void testGetAllTrans(){
        System.out.println("TEST: testGetAllTrans");
        try {
            List<Object> result = Collections.singletonList(transaction.getAllTransactions());
            List<CostItem> Alltransactions = new ArrayList<CostItem>((Collection<? extends CostItem>) result.get(1));
            List<CostItem> AlltransactionsExpected = new ArrayList<CostItem>();
            AlltransactionsExpected.add(trans1);
            AlltransactionsExpected.add(trans);
            assertEquals("objects should be equal",AlltransactionsExpected, Alltransactions);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the get all categories.
     */
    @Test
    public void testGetAllCate(){
        System.out.println("TEST: testGetAllTrans");
        try {
            List<String> myCategories = new ArrayList<String>(transaction.getAllCategories());
            List<String> AllCategoriesExpected = new ArrayList<String>();
            AllCategoriesExpected.add("food");
            AllCategoriesExpected.add("clothes");
            AllCategoriesExpected.add("education");
            AllCategoriesExpected.add("healthcare");
            AllCategoriesExpected.add("maintenance");
            AllCategoriesExpected.add("shoes");
            assertEquals("objects should be equal",AllCategoriesExpected, myCategories);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method delete tables
     * and exiting DB -
     * releasing sources and shutting down connections.
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        // delete table
        System.out.println("TEARDOWN!");
        try {
            transaction.deleteTable("myBankAccount");
            transaction.deleteTable("categories");
        } catch (CostManagerException costManagerException) {
            costManagerException.printStackTrace();
        }

        // exit + realising DB
//        try {
//            transaction.exit();
//        } catch (CostManagerException e) {
//            e.printStackTrace();
//        }

        System.out.println(" ");
    }
}
