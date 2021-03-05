package il.ac.shenkar.viewmodel;

import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.model.*;
import il.ac.shenkar.view.IView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ViewModel implements IViewModel{

    private IModel model = null;
    private IView view = null;
    int threads = Runtime.getRuntime().availableProcessors();
    ExecutorService exec = Executors.newFixedThreadPool(threads);

    public Boolean checkForNull(){
        if(view.equals(null) || model.equals(null)){
            return true;
        }
        return false;
    }

    /**
     * insert il.ac.shenkar.view object to il.ac.shenkar.view
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * insert il.ac.shenkar.model object to il.ac.shenkar.model
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    //methods:

    /**
     * insert a new cost transaction into the BankAccount table
     */
    @Override
    public void addCostItem(CostItem item) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                if (!checkForNull()) {
                    try {
                        model.addCostTran(item);
                        //display the cost items list
                        List<CostItem> items = model.getAllTransactions();
                        view.showItems(items);

                        view.showMessage(String.format("cost added successfully"));

                    } catch (CostManagerException e) {
                        view.showMessage(String.format("ERROR!: " + e.getMessage()));
                    }
                }
            }
        });
    }

    /**
     * exiting DB -
     * releasing sources and shutting down connections.
     */
    @Override
    public void disconnectDB(){
        exec.submit(new Runnable() {
            @Override
            public void run() {
                if (!checkForNull()) {

                    try {
                        model.exit();

                    } catch (CostManagerException e) {
                        System.out.println("error exiting app");
                    }
                }
            }
        });
    }

    /**
     * getting costs by specific date from DB
     */
    @Override
    public void getCostByDate(String fDate, String lDate) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> dateTransactions = new ArrayList<CostItem>(model.getTransByDate(fDate, lDate));
                    view.showItems(dateTransactions);

                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

    @Override
    public void displayPieChart(String fDate, String lDate) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Double> categoryCostsMAp = new HashMap<>(model.getCostsPieChart(fDate, lDate));
                    view.showPieChart(categoryCostsMAp);

                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

    /**
     * adding new category to DB
     */
    @Override
    public void addCategoryItem(String category) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.checkForCate(category);
                    view.showMessage(String.format("category added successfully"));

                    //display the categories list
                    List<String> myCategories = new ArrayList<String>(model.getAllCategories());
                    view.showCategories(myCategories);

                } catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));

                }
            }
        });
    }

    /**
     * getting all cost transactions from DB
     */
    @Override
    public void getAllCosts() {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> items = model.getAllTransactions();
                    view.setItems(items);

                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

    /**
     * getting all categories from DB
     */
    @Override
    public void getAllCategories() {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> myCategories = new ArrayList<String>(model.getAllCategories());
                    view.setCategories(myCategories);

                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

    /**
     * delete specific cost transaction from DB by index
     */
    @Override
    public void deleteCostByIndex(int index) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCostTrans(index);
                    view.showMessage(String.format("cost deleted successfully"));

                    //display the cost items list
                    List<CostItem> items = model.getAllTransactions();
                    view.showItems(items);
                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

    /**
     * getReport method use for passing cost items by date range from the model to the view
     */

    @Override
    public void getReport(String startDate, String endDate) {

        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //get the cost items list by date range and pass it to view
                    List<CostItem> report = model.getTransByDate(startDate, endDate);
                    view.setReport(report);
                } catch (CostManagerException e) {
                    view.showMessage("failed to set report list.." + e.getMessage());

                }
            }
        });


    }

    /**
     * generateReport method will display the cost items list in range of the input date
     */

    @Override
    public void generateReport(String startDate, String endDate) {


        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //get the cost items list in the input date range and display proper message
                    List<CostItem> report = model.getTransByDate(startDate, endDate);
                    view.showMessage("report was generate successfully");

                    //display the report
                    view.showReport(report);

                } catch (CostManagerException e) {
                    view.showMessage("failed to generate report.." + e.getMessage());

                }
            }
        });

    }
}


