package il.ac.shenkar.view;

import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.viewmodel.IViewModel;

import java.util.HashMap;
import java.util.List;

/**
 * IView interface is take care of the UI
 */

public interface IView {

    //setViewModel method set the input ViewModel as local variable for View class
    public void setViewModel(IViewModel vm);

    //showMessage method display the input String on the message screen in the application
    public void showMessage(String text);

    //showItems method display the input costItems list to the costs screen
    public void showItems(List<CostItem> costItems);

    //showReport method display the input costItems list to the report screen
    public void showReport(List<CostItem> report);

    //showCategories method display the input Category list to the categories screen
    public void showCategories(List<String> categories);

    //showPieChart method display the input costItems to pie chart display by counting the categories of all the items
    public void showPieChart(HashMap<String, Double> pie);

    //setItems method set the input costItems list as local variable for ApplicationUI class
    public void setItems(List<CostItem> costItems);

    //setCategories method set the input Category list as local variable for ApplicationUI class
    public void setCategories(List<String> categories);

    //setReport method set the input CostItem list as local variable for ApplicationUI class
    public void setReport(List<CostItem> report);

}

