package il.ac.shenkar.viewmodel;

import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.model.IModel;

/**
 * holds a reference to a il.ac.shenkar.model object
 * holds a reference to a il.ac.shenkar.view object
 */
public interface IViewModel {

    //enable the connection to the il.ac.shenkar.view
    public void setView(il.ac.shenkar.view.IView view);
    //enable the connection to the il.ac.shenkar.model
    public void setModel(IModel model);

    //methods:
    public void disconnectDB();
    public void addCostItem(CostItem item);
    public void addCategoryItem(String category);
    public void getCostByDate(String fDate, String lDate);
    public void displayPieChart(String fDate, String lDate);
    public void getAllCosts();
    public void getAllCategories();
    public void deleteCostByIndex(int index);
    //getReport method use for passing cost items by date range from the model to the view
    public void getReport(String startDate, String endDate);
    //generateReport method will display the cost items list in range of the input date
    public void generateReport(String startDate, String endDate);


}
