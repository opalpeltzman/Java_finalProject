package il.ac.shenkar.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object Interface,
 * transactionDao is an interface that has all the actions\methods the user
 * can perform on his bank account data base.
 */
public interface IModel {
    public List<Object> getAllTransactions() throws CostManagerException;
    public CostTransaction getTransByIndex(int index) throws CostManagerException;
    public List<CostTransaction> getTransByDate(String fDate, String lDate) throws CostManagerException;
    public List<CostTransaction> getTransByCate(String category) throws CostManagerException;
    public List<String> getAllCategories() throws CostManagerException;
    public void addCostTran(CostTransaction trans) throws CostManagerException;
    public void addCategory(String category) throws CostManagerException;
//    public ArrayList<ArrayList<Object>> getCostsPieChart(String fDate, String lDate) throws CostManagerException;
    public void checkForCate(String category) throws CostManagerException;
    public void updateCostTrans(int index, CostTransaction trans) throws CostManagerException;
    public void deleteCostTrans(int index) throws CostManagerException;
    public void deleteTable(String tableName) throws CostManagerException;
    public void exit() throws CostManagerException;
}
