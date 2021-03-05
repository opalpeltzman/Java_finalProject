package il.ac.shenkar.view;

import il.ac.shenkar.model.CostTransaction;
import il.ac.shenkar.model.*;
import il.ac.shenkar.viewmodel.IViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class View implements IView {

    private IViewModel vm;
//    private ExecutorService pool;
    //main menu
    private ApplicationUI ui;
    //add cost item
    private AddCostTransactionUI costItem;
    //show cost by date
    private ShowCostByDateGUI costByDate;
    // add category
    private AddCategoryGUI category;
    //show all costs transactions
    private GetAllCostsGUI allCosts;
    //show all categories
    private GetAllCategoriesGUI allCate;
    //delete cost by index
    private DeleteCostByIndexGUI deleteCost;
    //show pie chat for costs
    private displayPieChartGUI showPieChart;


    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {
        ui.showMessage(text);
    }

    @Override
    public void showItems(List<CostTransaction> vec) {
        costByDate.showItems(vec);
    }

    @Override
    public void showAllCosts(List<Object> vec) {
        allCosts.showAllCosts(vec);
    }
    @Override
    public void showAllCategories(List<String> vec){
        allCate.showAllCategories(vec);
    }

    @Override
    public void displayPieChart(HashMap<String, Double> categoryCosts) {
        showPieChart.displayPieChart(categoryCosts);
    }

    public View() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View.this.ui = new ApplicationUI();
                View.this.ui.start();
            }
        });
    }

    /**
     * Main menu GUI
     */
    public class ApplicationUI{

        private JFrame frame;
        private JPanel panelMain;
        private JPanel panelMessage;
        private JButton showCostSpecificDate;
        private JButton addCostTransaction;
        private JButton addCategory;
        private JButton getAllCateList;
        private JButton getAllCostList;
        private JButton deleteCostTransactionByIndex;
        private JButton pieChart;
        private JButton exit;
        private JTextField tfMessage;
        private JLabel lbMessage;

        public ApplicationUI(){
            //creating the window
            frame = new JFrame("CostManager");
            //creating the panels
            panelMain = new JPanel();
            panelMessage = new JPanel();
            //creating the main ui components
            showCostSpecificDate = new JButton("Show Cost By Date");
            addCostTransaction = new JButton("Add Cost Item");
            addCategory = new JButton("Add Category Item");
            getAllCateList = new JButton("Get All Categories");
            getAllCostList = new JButton("Get All Costs");
            deleteCostTransactionByIndex = new JButton("Delete Cost By Index");
            pieChart = new JButton("Category Costs PieChart");
            exit = new JButton("Exit");
            //creating the messages ui components
            lbMessage = new JLabel("Message: ");
            tfMessage = new JTextField(30);
            Font fieldFont = new Font("Arial", Font.PLAIN, 15);
            tfMessage.setFont(fieldFont);

        }
        public void start(){

            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);
            panelMain.add(showCostSpecificDate);
            panelMain.add(addCostTransaction);
            panelMain.add(addCategory);
            panelMain.add(getAllCateList);
            panelMain.add(getAllCostList);
            panelMain.add(deleteCostTransactionByIndex);
            panelMain.add(pieChart);
            panelMain.add(exit);

            //adding the components to the messages panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);
            //setting a different color for the panel message
            panelMessage.setBackground(Color.pink);

            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });


            //handling exit button click
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    vm.disconnectDB();
                    System.exit(0);
                }

            });

            //handling cost item adding button click
            addCostTransaction.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.costItem = new AddCostTransactionUI();
                    View.this.costItem.start();
                }

            });

            //handling show cost by specific date button click
            showCostSpecificDate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.costByDate = new ShowCostByDateGUI();
                    View.this.costByDate.start();
                }

            });

            //handling add category button click
            addCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.category = new AddCategoryGUI();
                    View.this.category.start();
                }

            });

            //handling show all categories button click
            getAllCateList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.allCate = new GetAllCategoriesGUI();
                    View.this.allCate.start();
                }

            });

            //handling show all costs button click
            getAllCostList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.allCosts = new GetAllCostsGUI();
                    View.this.allCosts.start();
                }

            });

            //handling delete cost by index button click
            deleteCostTransactionByIndex.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.deleteCost = new DeleteCostByIndexGUI();
                    View.this.deleteCost.start();
                }

            });

            //handling PieChart
            pieChart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    View.this.showPieChart = new displayPieChartGUI();
                    View.this.showPieChart.start();
                }

            });

            //displaying the window
            frame.setSize(500, 300);
            frame.setVisible(true);
        }

        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tfMessage.setText(text);
                    }
                });

            }
        }
    }

    /**
     * show list of costs by specific date GUI
     */
    public class ShowCostByDateGUI{

        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JButton search;
        private JTextField tfFirstDate;
        private JLabel lbFirstDate;
        private JTextField tfLastDate;
        private JLabel lbLastDate;

        ShowCostByDateGUI(){
            //creating the window
            frame = new JFrame("Costs By Date");
            //creating the two panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            search = new JButton("Search");
            tfFirstDate = new JTextField(30);
            lbFirstDate = new JLabel("Enter First Date : ");
            tfLastDate =  new JTextField(30);
            lbLastDate = new JLabel("Enter Last Date : ");
        }
        public void start(){
            //adding the components to the top panel
            panelTop.add(lbFirstDate);
            panelTop.add(tfFirstDate);
            panelTop.add(lbLastDate);
            panelTop.add(tfLastDate);
            panelTop.add(search);

            //setting a different color for the panel top
            panelTop.setBackground(Color.GRAY);
            //setting BorderLayout as the LayoutManager for panelMain
            panelMain.setLayout(new GridLayout(1, 1));
            //adding the components to the main panel
            panelMain.add(scrollPane);
            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
//                    System.exit(0);
                    frame.setVisible(false);
                }
            });

            //handling search button click
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                    String firstDate = tfFirstDate.getText();
                    if(firstDate==null || firstDate.length()==0) {

                            throw new CostManagerException("date cannot be empty");
                        }

                    String lastDate = tfLastDate.getText();
                    if(lastDate==null || lastDate.length()==0) {

                            throw new CostManagerException("date cannot be empty");
                        }

                    vm.getCostByDate(firstDate, lastDate);

                    }catch (CostManagerException ex) {
                        View.this.showMessage("ERROR!: "+ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(1200, 600);
            frame.setVisible(true);
        }

        public void showItems(List<CostTransaction> items) {
            //check if no costs -?then print no costs
            StringBuilder sb = new StringBuilder();
            for(CostTransaction item : items) {
                sb.append(item.toString());
                sb.append("\n");
            }
            String text = sb.toString();

            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });
            }
        }
    }

    /**
     * show list of all categories GUI
     */
    public class GetAllCategoriesGUI{

        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JLabel lbItemHeadline;

        GetAllCategoriesGUI(){

            //creating the window
            frame = new JFrame("Get All Categories");
            //creating the two panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            //creating the top ui components
            lbItemHeadline = new JLabel("All Categories");
        }
        public void start(){

            //adding the components to the top panel
            panelTop.add(lbItemHeadline);
            //setting a different color for the panel top
            panelTop.setBackground(Color.GRAY);
            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //setting GridLayout 1x1 as the LayoutManager for panelMain
            panelMain.setLayout(new GridLayout(1, 1));
            //adding the components to the main panel
            panelMain.add(scrollPane);
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });


            vm.getAllCategories();

            //displaying the window
            frame.setSize(1200, 600);
            frame.setVisible(true);
        }

        public void showAllCategories(List<String> items) {
            StringBuilder sb = new StringBuilder();
            for(String item : items) {
                sb.append(item);
                sb.append("\n");
            }
            String text = sb.toString();

            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });
            }
        }

    }

    /**
     * show list of all costs GUI
     */
    public class GetAllCostsGUI{

        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JLabel lbItemHeadline;

        GetAllCostsGUI(){

            //creating the window
            frame = new JFrame("Get All Costs");
            //creating the two panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            //creating the top ui components
            lbItemHeadline = new JLabel("All Costs Transactions");
        }
        public void start(){

            //adding the components to the top panel
            panelTop.add(lbItemHeadline);
            //setting a different color for the panel top
            panelTop.setBackground(Color.GRAY);
            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //setting GridLayout 1x1 as the LayoutManager for panelMain
            panelMain.setLayout(new GridLayout(1, 1));
            //adding the components to the main panel
            panelMain.add(scrollPane);
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });

                vm.getAllCosts();

            //displaying the window
            frame.setSize(1200, 600);
            frame.setVisible(true);
        }

        public void showAllCosts(List<Object> items) {
            //check if no costs -?then print no costs
            StringBuilder sb = new StringBuilder();
            ArrayList<Object> transactionIndex = (ArrayList<Object>) items.get(0);
            List<CostTransaction> transactions = (List<CostTransaction>) items.get(1);
            int length = transactionIndex.size();
            for(int i = 0; i < length; i++) {

                sb.append("transaction number ");
                sb.append(transactionIndex.get(i).toString());
                sb.append(" ,");
                sb.append(transactions.get(i).toString());
                sb.append("\n");
            }
            String text = sb.toString();
            if(text.isEmpty())
                View.this.showMessage("No transactions yet!");
            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });
            }
        }
    }

    /**
     * delete cost transaction by index GUI
     */
    public class DeleteCostByIndexGUI{

        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JButton delete;
        private JTextField tfindex;
        private JLabel lbindex;
        private JLabel lbItemHeadline;


        DeleteCostByIndexGUI(){

            //creating the window
            frame = new JFrame("Delete Cost Transaction");
            //creating the two panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            delete = new JButton("Delete");
            tfindex = new JTextField(30);
            lbindex = new JLabel("Enter index : ");
            //creating the top ui components
            lbItemHeadline = new JLabel("Delete Cost Transaction");

        }
        public void start(){

            //adding the components to the top panel
            panelTop.add(lbItemHeadline);
            //setting a different color for the panel top
            panelTop.setBackground(Color.GRAY);
            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //adding the components to the main panel
            panelMain.add(lbindex);
            panelMain.add(tfindex);
            panelMain.add(delete);
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });

            //handling search button click
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    double index = Double.parseDouble(tfindex.getText());
                    int num = (int) index;
                    vm.deleteCostByIndex(num);

                }
            });

            //displaying the window
            frame.setSize(400, 300);
            frame.setVisible(true);
        }
    }

    /**
     * add category GUI
     */
    public class AddCategoryGUI{

        private JFrame CategoryFrame;
        private JLabel CategoryTitle;
        private JPanel panelTop;
        private JTextField NameCategory;
        private JButton CategoryButton;
        private JPanel MainPanelCategory;
        private JLabel CategorySubtitle;

        AddCategoryGUI(){

            CategoryFrame = new JFrame();
            CategoryTitle = new JLabel("Add new category");
            MainPanelCategory = new JPanel();
            panelTop = new JPanel();
            CategorySubtitle = new JLabel ("Enter Category Name:");
            NameCategory = new JTextField(10);
            CategoryButton = new JButton("Add");
        }
        public void start(){

            //adding the components to the top panel
            panelTop.add(CategoryTitle);
            //adding the components to the main panel
            MainPanelCategory.add(CategorySubtitle);
            MainPanelCategory.add(NameCategory);
            MainPanelCategory.add(CategoryButton);
            //handling category item adding button click

            //setting the window layout manager
            CategoryFrame.setLayout(new BorderLayout());
            //adding the main panel to the window
            CategoryFrame.add(MainPanelCategory, BorderLayout.CENTER);
            //adding top panel to the window
            CategoryFrame.add(panelTop, BorderLayout.NORTH);

            CategoryFrame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) { CategoryFrame.setVisible(false);
                }
            });
            CategoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String cate = NameCategory.getText();
                        if(cate==null || cate.length()==0) {
                            throw new CostManagerException("category cannot be empty");
                        }
                        vm.addCategoryItem(cate);
                    }catch (CostManagerException costManagerException) {
                        costManagerException.printStackTrace();
                    }
                }

            });

            CategoryFrame.setSize(400, 300);
            CategoryFrame.setVisible(true);
        }
    }

    /**
     * Add cost transaction GUI
     */
    public class AddCostTransactionUI{
        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelBottom;
        private JPanel panelMain;
        private JTextField tfItemCategory;
        private JTextField tfItemSum;
        private JTextField tfItemCurrency;
        private JTextField tfItemDate;
        private JTextField tfItemDescription;
        private JButton btAddCostItem;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JLabel lbItemCategory;
        private JLabel lbItemSum;
        private JLabel lbItemCurrency;
        private JLabel lbItemDate;
        private JLabel lbItemDescription;



        public AddCostTransactionUI() {
            //creating the window
            frame = new JFrame("AddCostTransaction");
            //creating the four panels
            panelMain = new JPanel();
            panelBottom = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            tfItemCategory = new JTextField(20);
            tfItemSum = new JTextField(8);
            tfItemCurrency = new JTextField(8);
            tfItemDate = new JTextField(20);
            tfItemDescription = new JTextField(20);
            btAddCostItem = new JButton("Add Cost Item");
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            lbItemCurrency = new JLabel("Item Currency:");
            lbItemDescription = new JLabel("Item Description:");
            lbItemSum = new JLabel("Item Sum:");
            lbItemCategory = new JLabel("Item Category");
            lbItemDate = new JLabel("Item Date");

        }

        public void start() {

            //adding the components to the top panel
            panelTop.add(lbItemCategory);
            panelTop.add(tfItemCategory);
            panelTop.add(lbItemSum);
            panelTop.add(tfItemSum);
            panelTop.add(lbItemCurrency);
            panelTop.add(tfItemCurrency);
            panelTop.add(lbItemDate);
            panelTop.add(tfItemDate);
            panelTop.add(lbItemDescription);
            panelTop.add(tfItemDescription);
            panelTop.add(btAddCostItem);

            //setting BorderLayout as the LayoutManager for panelMain
            panelMain.setLayout(new BorderLayout());

            //setting GridLayout 1x1 as the LayoutManager for panelBottom
            panelBottom.setLayout(new GridLayout(1, 1));

            //adding the components to the bottom panel
            panelBottom.add(scrollPane);

            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //adding the two panels to the main panel
            panelMain.add(panelBottom, BorderLayout.CENTER);

            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.CENTER);

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });

            //handling cost item adding button click
            btAddCostItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String description = tfItemDescription.getText();
                        if(description==null || description.length()==0) {
                            throw new CostManagerException("description cannot be empty");
                        }
                        String category = tfItemCategory.getText();
                        if(category==null || category.length()==0) {
                            throw new CostManagerException("category cannot be empty");
                        }
                        String date = tfItemDate.getText();
                        if(date==null || date.length()==0) {
                            throw new CostManagerException("date cannot be empty");
                        }
                        double sum = Double.parseDouble(tfItemSum.getText());
                        String currencyStr = tfItemCurrency.getText();

                        switch (currencyStr) {
                            case "EURO":
                                currencyStr = "EURO";
                                break;
                            case "ILS":
                                currencyStr = "ILS";
                                break;
                            case "USD":
                                currencyStr = "USD";
                                break;
                            case "GBP":
                                currencyStr = "GBP";
                                break;
                            default:
                                currencyStr = "USD";

                        }
                        CostTransaction item = new CostTransaction(category, sum, currencyStr, date, description);
                        vm.addCostItem(item);


                    } catch (NumberFormatException ex) {
                        View.this.showMessage("ERROR!: "+ex.getMessage());
                    } catch(CostManagerException ex){
                        View.this.showMessage("ERROR!: "+ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(500, 300);
            frame.setVisible(true);

        }

    }

    /**
     * Display PieChart
     */
    public class displayPieChartGUI {

        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JButton search;
        private JTextField tfFirstDate;
        private JLabel lbFirstDate;
        private JTextField tfLastDate;
        private JLabel lbLastDate;

        displayPieChartGUI(){
            //creating the window
            frame = new JFrame("PieChart");
            //creating the two panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            //creating the main ui components
            textArea = new JTextArea();
            scrollPane = new JScrollPane(textArea);
            search = new JButton("Search");
            tfFirstDate = new JTextField(30);
            lbFirstDate = new JLabel("Enter First Date : ");
            tfLastDate =  new JTextField(30);
            lbLastDate = new JLabel("Enter Last Date : ");

        }
        public void start(){
            //adding the components to the top panel
            panelTop.add(lbFirstDate);
            panelTop.add(tfFirstDate);
            panelTop.add(lbLastDate);
            panelTop.add(tfLastDate);
            panelTop.add(search);

            //setting a different color for the panel top
            panelTop.setBackground(Color.GRAY);
            //setting BorderLayout as the LayoutManager for panelMain
            panelMain.setLayout(new GridLayout(1, 1));
            //adding the components to the main panel
            panelMain.add(scrollPane);
            //setting the window layout manager
            frame.setLayout(new BorderLayout());
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });

            //handling search button click
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String firstDate = tfFirstDate.getText();
                        if(firstDate==null || firstDate.length()==0) {

                            throw new CostManagerException("date cannot be empty");
                        }

                        String lastDate = tfLastDate.getText();
                        if(lastDate==null || lastDate.length()==0) {

                            throw new CostManagerException("date cannot be empty");
                        }

                        vm.displayPieChart(firstDate, lastDate);

                    }catch (CostManagerException ex) {
                        View.this.showMessage("ERROR!: "+ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(1000, 400);
            frame.setVisible(true);
        }

        public void displayPieChart(HashMap<String, Double> categoryCosts){

            HashMap<String, Double> categoryCostsMAp = new HashMap<>(categoryCosts);
            class PieChart_AWT extends ApplicationFrame {

                public PieChart_AWT(String title) {
                    super(title);
                    setContentPane(createDemoPanel( ));
                }

                private PieDataset createDataset() {
                    DefaultPieDataset dataset = new DefaultPieDataset( );
                    Iterator it = categoryCosts.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        dataset.setValue( pair.getKey().toString() , new Double((Double) pair.getValue()));
            }
                    return dataset;
                }

                private JFreeChart createChart( PieDataset dataset ) {
                    JFreeChart chart = ChartFactory.createPieChart(
                            "Costs by category",   // chart title
                            dataset,                    // data
                            true,               // include legend
                            true,
                            false);

                    return chart;
                }

                public JPanel createDemoPanel( ) {
                    JFreeChart chart = createChart(createDataset( ) );
                    return new ChartPanel( chart );
                }

                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            }

            // text area
            StringBuilder sb = new StringBuilder();
            Iterator it = categoryCostsMAp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                sb.append("Category: ");
                sb.append(pair.getKey().toString());
                sb.append(" ,Sum: ");
                sb.append(pair.getValue().toString());
                sb.append("\n");
                it.remove(); // avoids a ConcurrentModificationException
            }

            String text = sb.toString();

            if (SwingUtilities.isEventDispatchThread()) {
                textArea.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });
            }

            // PieChart
            PieChart_AWT demo = new PieChart_AWT( "Costs by Category" );
            demo.setSize( 560 , 367 );
            RefineryUtilities.centerFrameOnScreen( demo );
            demo.setVisible( true );


        }
    }
}

