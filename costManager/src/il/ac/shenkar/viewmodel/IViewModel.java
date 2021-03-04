package il.ac.shenkar.viewmodel;

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
    public void addCostItem(il.ac.shenkar.model.CostTransaction item);
    public void addCategoryItem(String category);
    public void getCostByDate(String fDate, String lDate);
    public void displayPieChart(String fDate, String lDate);
    public void getAllCosts();
    public void getAllCategories();
    public void deleteCostByIndex(int index);


}
