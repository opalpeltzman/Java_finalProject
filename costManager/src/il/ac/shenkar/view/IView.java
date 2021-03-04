package il.ac.shenkar.view;

import il.ac.shenkar.model.CostTransaction;

import java.util.HashMap;
import java.util.List;

/**
 * represent the GUI application
 * and contain methods that can run on il.ac.shenkar.view
 * will have a connection to a viewModel object reference  -> this object will connect with I-il.ac.shenkar.model
 */
public interface IView {

    public void setViewModel(il.ac.shenkar.viewmodel.IViewModel vm);
    public void showMessage(String text);
    public void showItems(List<CostTransaction> vec);
    public void showAllCosts(List<Object> vec);
    public void showAllCategories(List<String> vec);
    public void displayPieChart(HashMap<String, Double> categoryCosts);
    //public void updateCostTrans();

}
