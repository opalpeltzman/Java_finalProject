package il.ac.shenkar.viewmodel;

import il.ac.shenkar.model.CostTransaction;
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
    public void addCostItem(CostTransaction item) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                if (!checkForNull()) {
                    try {
                        model.addCostTran(item);
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
                    List<CostTransaction> dateTransactions = new ArrayList<CostTransaction>(model.getTransByDate(fDate, lDate));
                    view.showItems(dateTransactions);

                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }

//    @Override
//    public void displayPieChart(String fDate, String lDate) {
//        exec.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    HashMap<String, Double> categoryCostsMAp = new HashMap<>();
////                    String[] categories = null;
////                    double [] costs = null;
//                    ArrayList< ArrayList<Object>> result = new ArrayList<>(model.getCostsPieChart(fDate, lDate));
//                    for(ArrayList<Object> categoryCost : result) {
//
//                        boolean isKeyPresent = categoryCostsMAp.containsKey(categoryCost.get(0));
//                        if (isKeyPresent){
//                            categoryCostsMAp.put((String) categoryCost.get(0), Double.sum(categoryCostsMAp.get(categoryCost.get(0)), (Double) categoryCost.get(1)));
//                        }
//                        else
//                            categoryCostsMAp.put((String) categoryCost.get(0), (Double) categoryCost.get(1));
//                    }
//                    System.out.println("categoryCostsMAp: " + categoryCostsMAp);
//                    view.displayPieChart(categoryCostsMAp);
//
//                }catch (CostManagerException e) {
//                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
//                }
//            }
//        });
//    }

    /**
     * adding new category to DB
     */
    @Override
    public void addCtegoryItem(String category) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.checkForCate(category);
                    view.showMessage(String.format("category added successfully"));
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
                    List<Object> result = model.getAllTransactions();
                    view.showAllCosts(result);

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
                    view.showAllCategories(myCategories);

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
                }catch (CostManagerException e) {
                    view.showMessage(String.format("ERROR!: " + e.getMessage()));
                }
            }
        });
    }
}


