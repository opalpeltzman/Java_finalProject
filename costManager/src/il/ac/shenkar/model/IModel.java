package il.ac.shenkar.model;
import java.util.HashMap;
import java.util.List;

/**
 * Data Access Object Interface,
 * transactionDao is an interface that has all the actions\methods the user
 * can perform on his bank account data base.
 */
public interface IModel {
    public void buildBankAccountTable() throws CostManagerException;
    public void dropTable(String name) throws CostManagerException;
    public void shutDown() throws CostManagerException;
    public List<CostItem> getAllTransactions() throws CostManagerException;
    public CostItem getTransByIndex(int index) throws CostManagerException;
    public List<CostItem> getTransByDate(String fDate, String lDate) throws CostManagerException;
    public List<CostItem> getTransByCate(String category) throws CostManagerException;
    public List<String> getAllCategories() throws CostManagerException;
    public HashMap<String, Double> getCostsPieChart(String fDate, String lDate) throws CostManagerException;
    public void addCostTran(CostItem trans) throws CostManagerException;
    public void addCategory(Category category) throws CostManagerException;
    public void checkForCate(String category) throws CostManagerException;
    public void deleteCostTrans(int index) throws CostManagerException;
    public void deleteTable(String tableName) throws CostManagerException;
    public void exit() throws CostManagerException;
}
