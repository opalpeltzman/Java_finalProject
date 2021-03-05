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
    //addCostItem method will add the input item to the application and display the list of the items
    public void addCostItem(CostItem item);
    //addCategoryItem method will add the input category to the application and display the list of categories
    public void addCategoryItem(String category);
    //displayPieChart method will display the pie chart
    public void displayPieChart(String fDate, String lDate);
    //getAllCosts method use for passing cost items from the model to the view
    public void getAllCosts();
    //getAllCategories method use for passing Category items from the model to the view
    public void getAllCategories();
    //deleteCostByIndex method will delete the input item from the application and display the updated list of the items
    public void deleteCostByIndex(int index);
    //getReport method use for passing cost items by date range from the model to the view
    public void getReport(String startDate, String endDate);
    //generateReport method will display the cost items list in range of the input date
    public void generateReport(String startDate, String endDate);


}
