package il.ac.shenkar.view;

import com.toedter.calendar.JDateChooser;
import il.ac.shenkar.Application;
import il.ac.shenkar.model.Category;
import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.model.CostManagerException;
import il.ac.shenkar.viewmodel.IViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

/**
 * View class display the application and the data it store for the user by his requests
 */

public class View implements IView {

    //vm is used for communication between the view and model by using the ViewModel methods
    private IViewModel vm;
    //ui is used for communication between the view and ApplicationUI
    private ApplicationUI ui;


    /**
     * Class constructor.
     */
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
     * setViewModel method set the input ViewModel as local variable for View class
     */
    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    /**
     * showMessage method display the input String on the message screen in the application
     */
    @Override
    public void showMessage(String text) {
        ui.showMessage(text);
    }

    /**
     * showItems method display the input costItems list to the costs screen
     */
    @Override
    public void showItems(List<CostItem> costItems) {
        ui.showItems(costItems);
    }

    /**
     * showReport method display the input costItems list to the report screen
     */
    @Override
    public void showReport(List<CostItem> report) {
        ui.showReport(report);
    }

    /**
     * showCategories method display the input Category list to the categories screen
     */
    @Override
    public void showCategories(List<String> categories) {
        ui.showCategories(categories);
    }

    /**
     * showPieChart method display the input costItems to pie chart display by counting the categories of all the items
     */
    @Override
    public void showPieChart(HashMap<String, Double> pie) {
        ui.showPieChart(pie);
    }

    /**
     * setItems method set the input costItems list as local variable for ApplicationUI class
     */
    @Override
    public void setItems(List<CostItem> costItems) {
        ui.setItems(costItems);
    }

    /**
     * setCategories method set the input Category list as local variable for ApplicationUI class
     */
    @Override
    public void setCategories(List<String> categories) {
        ui.setCategories(categories);
    }

    /**
     * setReport method set the input CostItem list as local variable for ApplicationUI class
     */

    @Override
    public void setReport(List<CostItem> report) {
        ui.setReport(report);
    }

    /**
     * ApplicationUI class display the first screen visible to the user
     */
    public class ApplicationUI {

        //create the frame
        private JFrame frame;
        //JPanel for the header
        private JPanel panelTop;
        //JPanel for the start button
        private JPanel panelLow;
        //JPanel for the logo
        private JPanel panelLogo;
        //main screen header
        private JLabel header;
        //label for the logo
        private JLabel logo;
        //create the image
        private Image icon;
        //create the button
        private JButton startAppBtn;
        //create the the main screen
        private MainScreen mainScreen;
        //create the list of items, categories and report
        private List<CostItem> items;
        private List<String> categories;
        private List<CostItem> report;

        /**
         * Class constructor.
         */
        public ApplicationUI() {
            //init the frame and setting the CostsManager title
            frame = new JFrame("CostsManager");
            //init the top panel
            panelTop = new JPanel();
            //init the lower panel
            panelLow = new JPanel();
            //init the logo panel
            panelLogo = new JPanel();
            //init the header of the main app screen
            header = new JLabel("COSTS MANAGER");
            //setting image into icon
            icon = new ImageIcon(Application.class.getResource("/resources/creditcard.png")).getImage().
                    getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            //create JLabel for the logo
            logo = new JLabel("");
            //init the start button and setting the start text in it
            startAppBtn = new JButton("START");

        }

        public void start() {

            //setting different color to the top panel
            panelTop.setBackground(new Color(12, 140, 20));
            //set border to the top panel
            panelTop.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            //setting different color to the low panel
            panelLow.setBackground(new Color(12, 140, 20));
            //set border to the low panel
            panelLow.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            //setting different color to the logo panel
            panelLogo.setBackground(new Color(12, 140, 20));

            //set font to the header label
            header.setFont(new Font("Tahoma", Font.BOLD, 28));
            //set the text color of the header
            header.setForeground(Color.BLACK);

            //set the logo image
            logo.setIcon(new ImageIcon(icon));

            //set the size and background color of the button
            startAppBtn.setSize(100, 100);
            startAppBtn.setBackground(Color.WHITE);
            //set the font and the color of the text in the button
            startAppBtn.setForeground(Color.BLACK);
            startAppBtn.setFont(new Font("Tahoma", Font.BOLD, 20));

            //listener for start button
            startAppBtn.addActionListener(e -> {
                //load the cost item from DB to the screen
                vm.getAllCosts();
                //load the Categories from DB to the category ComboBox
                vm.getAllCategories();
                //setting the local variable mainScreen of ApplicationUI
                ApplicationUI.this.mainScreen = new MainScreen();
                //start the main screen and display it
                ApplicationUI.this.mainScreen.start();
                //close the ApplicationUI screen
                frame.dispose();

            });

            //adding all the components to the JPanels
            panelTop.add(header);
            panelLogo.add(logo);
            panelLow.add(startAppBtn);

            //adding all the panels to the frame
            frame.add(panelTop, BorderLayout.NORTH);
            frame.add(panelLow, BorderLayout.SOUTH);
            frame.add(panelLogo, BorderLayout.CENTER);

            //setting the size of the screen
            frame.setSize(500, 400);
            //end the program when user close the screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //make the screen visible
            frame.setVisible(true);

        }


        /**
         * showMessage method display the input String on the message screen in the application
         */
        public void showMessage(String text) {

            if (SwingUtilities.isEventDispatchThread()) {
                //set the input String on the message screen of the application
                mainScreen.tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        //set the input String on the message screen of the application
                        mainScreen.tfMessage.setText(text);
                    }
                });

            }
        }


        /**
         * showItems method display the input costItems list to the costs screen
         */
        public void showItems(List<CostItem> items) {
            //clear all the existing data
            int rows = mainScreen.costsScreen.tableModel.getRowCount();
            for (int i = rows - 1; i >= 0; i--) {
                mainScreen.costsScreen.tableModel.removeRow(i);
            }
            //check items is not null
            if (items != null) {

                //fill the table
                for (CostItem item : items) {
                    String category = item.getCategory().getCategoryName();
                    String sum = String.valueOf(item.getCost());
                    String currency = item.getCurrency().name();
                    String desc = item.getDescription();
                    LocalDate date = item.getDate().toLocalDate();
                    String localDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String id = String.valueOf(item.getId());
                    //creating String array from the value of the CostItem
                    String[] data = {localDate, id, category, sum, currency, desc};

                    if (SwingUtilities.isEventDispatchThread()) {
                        //add the String array to the tableModel
                        mainScreen.costsScreen.tableModel.addRow(data);

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //add the String array to the tableModel
                                mainScreen.costsScreen.tableModel.addRow(data);

                            }
                        });

                    }

                }

            }

        }

        /**
         * showReport method display the input costItems list to the report screen
         */
        public void showReport(List<CostItem> report) {

            //clear all the existing data
            int rows = mainScreen.reportScreen.dtm.getRowCount();
            for (int i = rows - 1; i >= 0; i--) {
                mainScreen.reportScreen.dtm.removeRow(i);
            }
            //check report is not null
            if (report != null) {
                //fill the table
                for (CostItem item : report) {
                    String category = item.getCategory().getCategoryName();
                    String sum = String.valueOf(item.getCost());
                    String currency = item.getCurrency().name();
                    String desc = item.getDescription();
                    LocalDate date = item.getDate().toLocalDate();
                    String id = String.valueOf(item.getId());
                    String localDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    //creating String array from the value of the CostItem
                    String[] data = {localDate, id, category, sum, currency, desc};

                    if (SwingUtilities.isEventDispatchThread()) {
                        //add the String array to the tableModel
                        mainScreen.reportScreen.dtm.addRow(data);

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //add the String array to the tableModel
                                mainScreen.reportScreen.dtm.addRow(data);

                            }
                        });

                    }
                }

            }


        }

        /**
         * showCategories method display the input Category list to the categories screen
         */
        public void showCategories(List<String> categories) {

            //clear all the existing data
            int rows = mainScreen.categoryScreen.defaultTableModel.getRowCount();
            for (int i = rows - 1; i >= 0; i--) {
                mainScreen.categoryScreen.defaultTableModel.removeRow(i);
            }
            //check categories is not null
            if (categories != null) {

                //fill the table
                for (String category : categories) {
                    String catName = category;
                    //creating String array from the value of the Category
                    String[] data = {catName};

                    if (SwingUtilities.isEventDispatchThread()) {
                        //add the String array to the tableModel
                        mainScreen.categoryScreen.defaultTableModel.addRow(data);
                        //update the categories list in costs screen
                        mainScreen.costsScreen.setCategoriesList(categories);
                        //load the Category ComboBox
                        mainScreen.costsScreen.loadCategoryComboBox();

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //add the String array to the tableModel
                                mainScreen.categoryScreen.defaultTableModel.addRow(data);
                                //update the categories list in costs screen
                                mainScreen.costsScreen.setCategoriesList(categories);
                                //load the Category ComboBox
                                mainScreen.costsScreen.loadCategoryComboBox();

                            }
                        });

                    }
                }

            }

        }

        /**
         * showPieChart method display the input costItems to pie chart display by counting
         * the categories of all the items
         */
        public void showPieChart(HashMap<String, Double> pie) {
            class PieChart_AWT extends ApplicationFrame {
                public PieChart_AWT(String title) {
                    super(title);
                    setContentPane(createDemoPanel());
                }

                private PieDataset createDataset() {
                    DefaultPieDataset dataset = new DefaultPieDataset();
                    Iterator it = pie.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        dataset.setValue(pair.getKey().toString(), new Double((Double) pair.getValue()));
                    }
                    return dataset;
                }

                private JFreeChart createChart(PieDataset dataset) {
                    JFreeChart chart = ChartFactory.createPieChart(
                            "Costs by category",   // chart title
                            dataset,                    // data
                            true,               // include legend
                            true,
                            false);

                    return chart;
                }

                public JPanel createDemoPanel() {
                    JFreeChart chart = createChart(createDataset());
                    return new ChartPanel(chart);
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

            // PieChart
            PieChart_AWT demo = new PieChart_AWT( "Costs by Category" );
            demo.setSize( 560 , 367 );
            RefineryUtilities.centerFrameOnScreen( demo );
            demo.setVisible( true );

        }

        /**
         * setItems method set the input costItems list as local variable for ApplicationUI class
         */
        public void setItems(List<CostItem> items) {

            this.items = items;
        }

        /**
         * setCategories method set the input Category list as local variable for ApplicationUI class
         */

        public void setCategories(List<String> categories) {

            this.categories = categories;
        }

        /**
         * setReport method set the input CostItem list as local variable for ApplicationUI class
         */
        public void setReport(List<CostItem> report) {
            this.report = report;
        }

        /**
         * MainScreen class display the main screen that contain screen navigator on it's left side
         */

        public class MainScreen {

            //create the frame
            private JFrame frame;

            //create the application screens
            private CostsScreen costsScreen;
            private CategoryScreen categoryScreen;
            private ReportScreen reportScreen;
            private PieScreen pieScreen;

            //create the panels
            private JPanel costPanel;
            private JPanel categoryPanel;
            private JPanel reportPanel;
            private JPanel piePanel;
            private JPanel sidePanel;
            private JPanel panelTop;
            private JPanel mainPanel;


            private JPanel messagePanel;

            //create the labels
            private JLabel costLabel;
            private JLabel categoryLabel;
            private JLabel reportLabel;
            private JLabel pieLabel;
            private JLabel headline;
            private JLabel logo;

            //create the image
            private Image icon;

            //create the text field
            private JTextField tfMessage;

            /**
             * Class constructor.
             */
            public MainScreen() {

                //init the main frame and set background
                frame = new JFrame("CostsManager");

                //init the top panel
                panelTop = new JPanel();

                //init headline label
                headline = new JLabel("COSTS MANAGER");

                //init the main content panel of the app
                mainPanel = new JPanel();

                //init the message panel
                messagePanel = new JPanel();

                //init the side menu panel
                sidePanel = new JPanel();

                //init the cost panel
                costPanel = new JPanel();

                //add cost label to the cost panel
                costLabel = new JLabel("COSTS");

                //init the category panel
                categoryPanel = new JPanel();

                //add category label to category pane
                categoryLabel = new JLabel("CATEGORIES");

                //init the report panel
                reportPanel = new JPanel();

                //add report label to report panel
                reportLabel = new JLabel("REPORT");

                //init the pie chart panel
                piePanel = new JPanel();

                //add PIE label to pie panel
                pieLabel = new JLabel("PIE CHART");

                //set the size of the text field
                tfMessage = new JTextField(30);

                //create JLabel for the Logo
                logo = new JLabel("");

                //setting image into icon
                icon = new ImageIcon(Application.class.getResource("/resources/creditcard.png")).getImage().
                        getScaledInstance(100, 100, Image.SCALE_SMOOTH);


            }

            public void start() {

                //setting the window size
                frame.setSize(1500, 800);
                frame.getContentPane().setBackground(new Color(17, 150, 100));

                //handling window closing
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //setting a different color to the top panel
                panelTop.setBackground(new Color(17, 150, 100));

                //setting the border for the panel
                panelTop.setBorder(new EtchedBorder(EtchedBorder.RAISED));

                //setting a different color to the message panel
                messagePanel.setBackground(new Color(17, 150, 100));

                //setting different color and font for the label
                headline.setFont(new Font("Calibri", Font.BOLD, 30));
                headline.setForeground(Color.WHITE);

                //setting the main panel size
                mainPanel.setSize(800, 600);
                mainPanel.setBackground(new Color(225, 225, 225));

                //setting different background to the side panel
                sidePanel.setBackground(new Color(17, 150, 100));

                //setting GridLayout 5x1 as the LayoutManager for side panel
                sidePanel.setLayout(new GridLayout(5, 1));

                //setting the icon to logo label
                logo.setIcon(new ImageIcon(icon));
                logo.setHorizontalAlignment(SwingConstants.CENTER);


                //setting the text field to non editable
                tfMessage.setEditable(false);
                //setting the text color
                tfMessage.setForeground(Color.RED);

                //setting background to the cost panel
                costPanel.setBackground(new Color(17, 150, 100));
                //setting the size of the category panel
                costPanel.setBounds(0, 340, 249, 40);
                //setting the border for the panel
                costPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

                //setting the color of the label
                costLabel.setForeground(Color.WHITE);
                //setting the font of the label
                costLabel.setFont(new Font("Dialog", Font.BOLD, 20));
                //setting the size of the label
                costLabel.setBounds(76, 11, 163, 18);

                //setting background to the category panel
                categoryPanel.setBackground(new Color(17, 150, 100));
                //setting the size of the category panel
                categoryPanel.setBounds(0, 340, 249, 40);
                //setting the border for the panel
                categoryPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

                //setting the color of the label
                categoryLabel.setForeground(Color.WHITE);
                //setting the font of the label
                categoryLabel.setFont(new Font("Dialog", Font.BOLD, 20));
                //setting the size of the label
                categoryLabel.setBounds(76, 11, 163, 18);

                //setting background to the report panel
                reportPanel.setBackground(new Color(17, 150, 100));
                //setting the size of the report panel
                reportPanel.setBounds(0, 340, 249, 40);
                //setting the border for the panel
                reportPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

                //setting the color of the label
                reportLabel.setForeground(Color.WHITE);
                //setting the font of the label
                reportLabel.setFont(new Font("Dialog", Font.BOLD, 20));
                //setting the size of the label
                reportLabel.setBounds(76, 11, 163, 18);

                //setting background to the pie panel
                piePanel.setBackground(new Color(17, 150, 100));
                //setting the size of the pie panel
                piePanel.setBounds(0, 340, 249, 40);
                //setting the border for the panel
                piePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

                //setting the color of the label
                pieLabel.setForeground(Color.WHITE);
                //setting the font of the label
                pieLabel.setFont(new Font("Dialog", Font.BOLD, 20));
                //setting the size of the label
                pieLabel.setBounds(76, 11, 163, 18);

                //init the costs screen
                MainScreen.this.costsScreen = new CostsScreen();
                costsScreen.start();
                //init the category screen
                MainScreen.this.categoryScreen = new CategoryScreen();
                categoryScreen.start();
                //init the report screen
                MainScreen.this.reportScreen = new ReportScreen();
                reportScreen.start();
                //init the pie screen
                MainScreen.this.pieScreen = new PieScreen();
                pieScreen.start();


                //set mouse listener for the cost panel
                costPanel.addMouseListener(new PanelButtonMouseAdapter(costPanel) {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuClick(costsScreen);
                    }
                });

                //set mouse listener for the category panel
                categoryPanel.addMouseListener(new PanelButtonMouseAdapter(categoryPanel) {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuClick(categoryScreen);
                    }
                });

                //set mouse listener for the report panel
                reportPanel.addMouseListener(new PanelButtonMouseAdapter(reportPanel) {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuClick(reportScreen);
                    }
                });

                //set mouse listener for the pie panel
                piePanel.addMouseListener(new PanelButtonMouseAdapter(piePanel) {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        menuClick(pieScreen);
                    }
                });

                //adding the components to the top panel
                panelTop.add(headline);

                //adding the components to the cost panel
                costPanel.add(costLabel, Component.CENTER_ALIGNMENT);

                //adding the components to the category panel
                categoryPanel.add(categoryLabel, Component.CENTER_ALIGNMENT);

                //adding the components to the report panel
                reportPanel.add(reportLabel, Component.CENTER_ALIGNMENT);

                //adding the components to the pie panel
                piePanel.add(pieLabel, Component.CENTER_ALIGNMENT);

                //adding the logo to the side panel
                sidePanel.add(logo);

                //adding the components to the side panel
                sidePanel.add(costPanel, Component.CENTER_ALIGNMENT);
                sidePanel.add(categoryPanel, Component.CENTER_ALIGNMENT);
                sidePanel.add(reportPanel, Component.CENTER_ALIGNMENT);
                sidePanel.add(piePanel, Component.CENTER_ALIGNMENT);

                //adding all the screens to the main panel
                mainPanel.add(costsScreen);
                mainPanel.add(categoryScreen);
                mainPanel.add(reportScreen);
                mainPanel.add(pieScreen);

                messagePanel.add(tfMessage);

                //setting the cost window as default screen
                menuClick(costsScreen);

                //add the main panel to the frame
                frame.add(mainPanel, BorderLayout.CENTER);

                //add the top panel to the frame
                frame.add(panelTop, BorderLayout.NORTH);

                //add the side panel to the frame
                frame.add(sidePanel, BorderLayout.WEST);

                //add the message panel to the frame
                frame.add(messagePanel, BorderLayout.SOUTH);

                //displaying the window
                frame.setVisible(true);


            }

            /**
             * This function determines which screen to switch when we press a button
             */
            public void menuClick(JPanel panel) {

                costsScreen.setVisible(false);
                categoryScreen.setVisible(false);
                reportScreen.setVisible(false);
                pieScreen.setVisible(false);

                panel.setVisible(true);

            }


            /**
             * This function paints every panel you go through with the mouse
             */
            private class PanelButtonMouseAdapter extends MouseAdapter {

                JPanel panel;

                public PanelButtonMouseAdapter(JPanel panel) {
                    this.panel = panel;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    panel.setBackground(new Color(60, 179, 113));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    panel.setBackground(new Color(112, 128, 144));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(new Color(112, 128, 144));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(new Color(17, 150, 100));
                }
            }


            /**
             * CostsScreen class display costs screen, letting the user
             * to add or remove cost items and watch them
             */

            public class CostsScreen extends JPanel {

                //create the panels
                private JPanel inputsPanel;
                private JPanel buttonsPanel;

                //create the labels
                private JLabel categoryLabel;
                private JLabel headline;
                private JLabel sumLabel;
                private JLabel currencyLabel;
                private JLabel descriptionLabel;
                private JLabel dateLabel;

                //create the text fields
                private JTextField sumField;
                private JTextField descriptionField;

                //create the buttons
                private JButton addCostButton;
                private JButton deleteCostButton;
                private JButton newCatButton;

                //create the comboBox for categories and currency
                private JComboBox<String> catComboBox;
                private JComboBox<String> currencyComboBox;

                //create the table that contain the items
                private JTable table;

                //create the JScrollPane for the table
                private JScrollPane scrollPane;

                //create the JDateChooser
                private JDateChooser dateChooser;

                //create the DefaultTableModel
                private DefaultTableModel tableModel;

                //create the list of items and categories
                private List<CostItem> items;
                private List<String> categories;

                /**
                 * Class constructor.
                 */
                public CostsScreen() {

                    //setting the local items and categories list to the ApplicationUI local items and categories list
                    setItemsList(ApplicationUI.this.items);
                    setCategoriesList(categories = ApplicationUI.this.categories);

                    //create the text field panel
                    inputsPanel = new JPanel();

                    //create the buttons panel
                    buttonsPanel = new JPanel();

                    //load the cost items to the screen
                    loadCostsTable();

                    //init the JScrollPane for the table
                    scrollPane = new JScrollPane(table);

                    //init the label for the Headline
                    headline = new JLabel("COSTS");

                    //init the label for category
                    categoryLabel = new JLabel("Category");

                    //init the category combo box
                    catComboBox = new JComboBox<>();

                    //load the categories from DB to the category comboBox
                    loadCategoryComboBox();

                    //init the label for sum
                    sumLabel = new JLabel("Sum");

                    // init the field for sum
                    sumField = new JTextField();

                    //init the label for currency
                    currencyLabel = new JLabel("Currency");

                    //init comboBox for currency
                    currencyComboBox = new JComboBox<>();

                    //init the label for description
                    descriptionLabel = new JLabel("Description");

                    //init the field for description
                    descriptionField = new JTextField();

                    //init date label
                    dateLabel = new JLabel("Date");

                    //init field for date
                    dateChooser = new JDateChooser();

                    //init the add and delete cost buttons and add category button
                    addCostButton = new JButton("Add Cost");
                    deleteCostButton = new JButton("Delete Cost");
                    newCatButton = new JButton("New Category");

                }

                public void start() {
                    //set the size, layout and the background of the screen
                    setSize(800, 600);
                    setLayout(new BorderLayout(10, 10));
                    setBackground(new Color(225, 225, 225));

                    //set the inputsPanel and buttonsPanel background
                    inputsPanel.setBackground(new Color(225, 225, 225));
                    buttonsPanel.setBackground(new Color(225, 225, 225));

                    //set the buttonsPanel, inputsPanel and scrollPane borders
                    buttonsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                    inputsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                    scrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

                    //set the font,HorizontalAlignment,VerticalAlignment and color of the headline
                    headline.setFont(new Font("Arial", Font.BOLD, 28));
                    headline.setHorizontalAlignment(JLabel.CENTER);
                    headline.setVerticalAlignment(JLabel.CENTER);
                    headline.setForeground(Color.BLACK);

                    //set the color and the font of the categoryLabel
                    categoryLabel.setForeground(Color.BLACK);
                    categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    //set the color and the font of the sumLabel
                    sumLabel.setForeground(Color.BLACK);
                    sumLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    //set the color and the font of the currencyLabel
                    currencyLabel.setForeground(Color.BLACK);
                    currencyLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    //add the currency types to the currencyComboBox
                    currencyComboBox.addItem("ILS");
                    currencyComboBox.addItem("USD");
                    currencyComboBox.addItem("EURO");
                    currencyComboBox.addItem("GBP");

                    //set the color and the font of the descriptionLabel
                    descriptionLabel.setForeground(Color.BLACK);
                    descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    //set the color and the font of the dateLabel
                    dateLabel.setForeground(Color.BLACK);
                    dateLabel.setFont(new Font("Arial", Font.BOLD, 14));

                    //setting the date format for input date String
                    dateChooser.setDateFormatString("yyyy-MM-dd");

                    //set the size for sum field
                    sumField.setPreferredSize(new Dimension(150, 25));

                    //set the size for description field
                    descriptionField.setPreferredSize(new Dimension(150, 25));

                    //set the size for date field
                    dateChooser.setPreferredSize(new Dimension(150, 25));

                    //listener for add cost button
                    addCostButton.addActionListener(e -> {
                        try {
                            //get the category choice and convert it to String
                            String category = (String) catComboBox.getSelectedItem();
                            //get the description text input and convert it to String
                            String description = descriptionField.getText();
                            //check if the description field is empty and throw exception if empty
                            if (description == null || description.length() == 0) {
                                throw new CostManagerException("description cannot be empty");
                            }
                            //get the sum input and convert it to String
                            double sum = Double.parseDouble(sumField.getText());
                            //get the currency choice and convert it to String
                            String currencyStr = (String) currencyComboBox.getSelectedItem();

                            //check the date is not null and throw exception if yes
                            if (dateChooser.getDate() == null) {
                                throw new CostManagerException("date cannot be empty");
                            }
                            //parse the string to LocalDate Object
                            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dtf.format(dateChooser.getDate());

                            //create the cost item from the inputs and add it to the cost items table
                            CostItem item = new CostItem(category, sum, currencyStr, date, description);
                            vm.addCostItem(item);


                        } catch (NumberFormatException ex) {
                            View.this.showMessage("problem with entered sum... " + ex.getMessage());
                        } catch (CostManagerException ex) {
                            View.this.showMessage("problem with entered data... " + ex.getMessage());
                        }

                    });
                    //listener for delete cost button
                    deleteCostButton.addActionListener(e -> {

                        try {
                            //check the user select only one row to delete
                            if (table.getSelectedRowCount() == 1) {
                                //store the index of the selected row
                                int i = table.getSelectedRow();
                                //get the ID of the CostItem
                                String id = tableModel.getValueAt(i, 1).toString();
                                int userID = Integer.parseInt(id);

                                //delete the CostItem from the cost items table and remove it from the table model
                                vm.deleteCostByIndex(userID);
                                tableModel.removeRow(i);

                            } else {
                                if (table.getSelectedRowCount() == 0) {
                                    showMessage("Table is empty");
                                } else {
                                    showMessage("select single row to delete");
                                }
                            }

                        } catch (NumberFormatException ex) {
                            View.this.showMessage("problem with  sum... " + ex.getMessage());
                        }
                    });

                    //listener for new category button
                    newCatButton.addActionListener(e -> mainScreen.menuClick(mainScreen.categoryScreen));


                    //add all the components to the inputs Panel
                    inputsPanel.add(categoryLabel);
                    inputsPanel.add(catComboBox);
                    inputsPanel.add(sumLabel);
                    inputsPanel.add(sumField);
                    inputsPanel.add(currencyLabel);
                    inputsPanel.add(currencyComboBox);
                    inputsPanel.add(descriptionLabel);
                    inputsPanel.add(descriptionField);
                    inputsPanel.add(dateLabel);
                    inputsPanel.add(dateChooser);

                    //insert the add/delete cost button to the buttons Panel
                    buttonsPanel.add(addCostButton);
                    buttonsPanel.add(deleteCostButton);
                    buttonsPanel.add(newCatButton);

                    //add all the panels to the costs screen
                    add(headline, BorderLayout.NORTH);
                    add(inputsPanel, BorderLayout.CENTER);
                    add(scrollPane, BorderLayout.SOUTH);
                    add(buttonsPanel, BorderLayout.EAST);

                }

                //

                /**
                 * setting the local variable items
                 */
                public void setItemsList(List<CostItem> items) {
                    this.items = items;
                }

                /**
                 * setting the local variable categories
                 */
                public void setCategoriesList(List<String> categories) {
                    this.categories = categories;
                }

                /**
                 * loadCostsTable method load the table from the DB and display it
                 */
                public void loadCostsTable() {

                    //set default table model
                    tableModel = new DefaultTableModel();

                    //create the costs table
                    table = new JTable(tableModel);

                    //add the columns to the table
                    tableModel.addColumn("DATE");
                    tableModel.addColumn("ID");
                    tableModel.addColumn("CATEGORY");
                    tableModel.addColumn("SUM");
                    tableModel.addColumn("CURRENCY");
                    tableModel.addColumn("DESCRIPTION");

                    if (items != null) {

                        for (CostItem item : items) {
                            String category = item.getCategory().getCategoryName();
                            String sum = String.valueOf(item.getCost());
                            String currency = item.getCurrency().name();
                            String desc = item.getDescription();
                            LocalDate date = item.getDate().toLocalDate();
                            String localDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            String id = String.valueOf(item.getId());

                            String[] data = {localDate, id, category, sum, currency, desc};

                            if (SwingUtilities.isEventDispatchThread()) {
                                tableModel.addRow(data);

                            } else {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        tableModel.addRow(data);

                                    }
                                });

                            }
                        }
                    }
                    table.setRowHeight(25);
                    table.setFont(new Font("Arial", Font.PLAIN, 14));
                    table.setForeground(Color.BLUE);

                }

                /**
                 * loadCategoryComboBox show the list of categories in comboBox
                 */
                public void loadCategoryComboBox() {

                    //clear old list
                    if (catComboBox.getItemCount() > 0) {
                        catComboBox.removeAllItems();
                    }

                    if (categories != null) {
                        for (String category : categories) {
                            catComboBox.addItem(category);
                        }
                    }

                }

            }

            /**
             * CategoryScreen class display category screen,
             * letting the user to add categories and watch them
             */
            public class CategoryScreen extends JPanel {

                //create the labels
                private JLabel headline;
                private JLabel categoryLabel;

                //create the button
                private JButton addCatButton;

                //create the panel
                private JPanel inputsPanel;

                //create the text field
                private JTextField categoryField;

                //create the JScrollPane for the table
                private JScrollPane scrollPaneCat;

                //create the table that contain the categories
                private JTable catTable;

                //create the DefaultTableModel
                private DefaultTableModel defaultTableModel;

                //create the list of categories
                private List<String> categories;


                /**
                 * Class constructor.
                 */
                public CategoryScreen() {

                    //setting the local categories list to the ApplicationUI local list
                    categories = ApplicationUI.this.categories;

                    //create the fields panels
                    inputsPanel = new JPanel();

                    //load the categories to the category screen
                    loadCatTable();

                    //init the JScrollPane for the categories table
                    scrollPaneCat = new JScrollPane(catTable);

                    //create the add category button
                    addCatButton = new JButton("ADD CATEGORY");

                    //create the label for the Category
                    categoryLabel = new JLabel("Category");

                    //create the field for category
                    categoryField = new JTextField();

                    //create the label for the Headline
                    headline = new JLabel("CATEGORIES");

                }

                public void start() {

                    //set the size, layout and the background of the screen
                    setLayout(new BorderLayout(10, 10));
                    setSize(800, 600);
                    setBackground(new Color(225, 225, 225));

                    //setting different color to the inputs panel
                    inputsPanel.setBackground(new Color(225, 225, 225));

                    //setting font and text color to the category label
                    categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryLabel.setForeground(Color.BLACK);

                    //setting font and text color to the headline
                    headline.setFont(new Font("Arial", Font.BOLD, 28));
                    headline.setForeground(Color.BLACK);
                    headline.setHorizontalAlignment(JLabel.CENTER);
                    headline.setVerticalAlignment(JLabel.CENTER);

                    //set the size for category field
                    categoryField.setPreferredSize(new Dimension(250, 25));

                    //set action listener to the add cat button
                    addCatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            try {
                                String category = categoryField.getText();
                                if (category == null || category.length() == 0) {
                                    throw new CostManagerException("Category cannot be empty");
                                }

                                Category cat = new Category(category);
                                vm.addCategoryItem(cat.getCategoryName());

                            } catch (CostManagerException ex) {
                                View.this.showMessage("problem with entered data... " + ex.getMessage());
                            }
                        }
                    });

                    //set the input panel layout
                    inputsPanel.setLayout(new FlowLayout());

                    //add the components to the inputPanel
                    inputsPanel.add(categoryLabel);
                    inputsPanel.add(categoryField);
                    inputsPanel.add(addCatButton);


                    //add all the panels to the category screen
                    add(headline, BorderLayout.NORTH);
                    add(inputsPanel, BorderLayout.CENTER);
                    add(scrollPaneCat, BorderLayout.SOUTH);

                }


                public void loadCatTable() {

                    //set default table model
                    defaultTableModel = new DefaultTableModel();

                    //create the categories table
                    catTable = new JTable(defaultTableModel);

                    //add the columns to the table
                    defaultTableModel.addColumn("CATEGORY NAME");

                    if (categories != null) {

                        for (String category : categories) {
                            String catName = category;
                            String[] data = {catName};

                            if (SwingUtilities.isEventDispatchThread()) {
                                defaultTableModel.addRow(data);

                            } else {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        defaultTableModel.addRow(data);

                                    }
                                });

                            }
                        }
                    }
                    catTable.setRowHeight(40);
                    catTable.setFont(new Font("Dialog", Font.BOLD, 20));
                    catTable.setForeground(Color.BLACK);

                    //align text to center
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    catTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

                }

            }

            /**
             * ReportScreen class display report of cost items within date range selected by the user
             */
            public class ReportScreen extends JPanel {

                //create the labels
                private JLabel headline;
                private JLabel startDateLabel, endDateLabel;

                //create the start and end date chooser
                private JDateChooser startDateField;
                private JDateChooser endDateField;

                //create the input panel
                private JPanel inputsPanel;

                //create the show report button
                private JButton showReportButton;

                //create the JScrollPane for the table
                private JScrollPane scrollPane;

                //create the table that contain the cost items in the date range the user selected
                private JTable reportTable;

                //create the DefaultTableModel
                private DefaultTableModel dtm;

                /**
                 * Class constructor.
                 */
                public ReportScreen() {

                    //init the input panel
                    inputsPanel = new JPanel();

                    //init the headline label
                    headline = new JLabel("REPORT");

                    //create the table for the report screen
                    buildTable();

                    //init the show report button
                    showReportButton = new JButton("SHOW REPORT");

                    //init the scroll pane and set it size
                    scrollPane = new JScrollPane(reportTable);

                    //init the label for start date set it font and size
                    startDateLabel = new JLabel("Start Date");

                    //init the label for end date set it font and size
                    endDateLabel = new JLabel("End Date");

                    //init the field for start and end date and set its date format
                    startDateField = new JDateChooser();
                    endDateField = new JDateChooser();

                }

                public void start() {

                    //set the size, layout and the background of the report screen
                    setLayout(new BorderLayout(10, 10));
                    setSize(800, 600);
                    setBackground(new Color(225, 225, 225));

                    //set the background color
                    inputsPanel.setBackground(new Color(225, 225, 225));

                    //setting the size of the JScrollPane
                    scrollPane.setPreferredSize(new Dimension(800, 500));

                    //setting the font and the size of the start date label
                    startDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    startDateLabel.setForeground(Color.BLACK);

                    //setting the font and the size of the end date label
                    endDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    endDateLabel.setForeground(Color.BLACK);

                    //setting date format to dd/MM/yyyy
                    startDateField.setDateFormatString("yyyy-MM-dd");
                    endDateField.setDateFormatString("yyyy-MM-dd");

                    //setting the headline font, color and place
                    headline.setFont(new Font("Arial", Font.BOLD, 28));
                    headline.setForeground(Color.BLACK);
                    headline.setHorizontalAlignment(JLabel.CENTER);
                    headline.setVerticalAlignment(JLabel.CENTER);

                    //set the size for start and end date field
                    startDateField.setPreferredSize(new Dimension(100, 25));
                    endDateField.setPreferredSize(new Dimension(100, 25));

                    //add action listener to the show report button
                    showReportButton.addActionListener(e -> {
                        try {
                            if (startDateField.getDate() == null || endDateField.getDate() == null) {
                                throw new CostManagerException("Date cannot be empty");
                            }
                            if (startDateField.getDate().after(endDateField.getDate())) {
                                throw new CostManagerException("Date Range ERROR");
                            }

                            //parse the string to LocalDate Object
                            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                            String sDate = dtf.format(startDateField.getDate());
                            String eDate = dtf.format(endDateField.getDate());


                            //get the report of cost items within the date range
                            vm.generateReport(sDate, eDate);
                            vm.getReport(sDate, eDate);

                        } catch (CostManagerException ex) {
                            View.this.showMessage("problem with selected date... " + ex.getMessage());
                        }

                    });

                    //add the components to the inputs panel
                    inputsPanel.add(startDateLabel);
                    inputsPanel.add(startDateField);
                    inputsPanel.add(endDateLabel);
                    inputsPanel.add(endDateField);
                    inputsPanel.add(showReportButton);


                    //add the components to the main panel
                    add(headline, BorderLayout.NORTH);
                    add(inputsPanel, BorderLayout.CENTER);
                    add(scrollPane, BorderLayout.SOUTH);

                }

                /**
                 * buildTable used for create the table for the report
                 */
                public void buildTable() {

                    //set default table model
                    dtm = new DefaultTableModel();

                    //create the report table
                    reportTable = new JTable(dtm);

                    //add the columns to the table
                    dtm.addColumn("DATE");
                    dtm.addColumn("ID");
                    dtm.addColumn("CATEGORY");
                    dtm.addColumn("SUM");
                    dtm.addColumn("CURRENCY");
                    dtm.addColumn("DESCRIPTION");

                    reportTable.setRowHeight(25);
                    reportTable.setFont(new Font("Arial", Font.PLAIN, 14));
                    reportTable.setForeground(Color.BLACK);

                }

            }

            /**
             * PieScreen class display pie chart of cost items within date range selected by the user
             */
            public class PieScreen extends JPanel {

                //create a default pie
                private DefaultPieDataset pieDataset;

                //create the input panel
                private JPanel inputsPanel;

                //create the labels
                private JLabel headline;
                private JLabel startDateLabel, endDateLabel;

                //create the start and end date chooser
                private JDateChooser startDateField;
                private JDateChooser endDateField;

                //create the pie chart button
                private JButton pieChartBtn;

                /**
                 * Class constructor.
                 */
                public PieScreen() {

                    //set the size, layout and the background of the pie screen
                    setSize(800, 600);
                    setLayout(new BorderLayout(10, 10));
                    setBackground(new Color(225, 225, 225));

                    //init the input panel
                    inputsPanel = new JPanel();

                    //init the headline and set it font, color and place
                    headline = new JLabel("PIE");

                    //init the label for start date
                    startDateLabel = new JLabel("Start Date");

                    //init the label for end date
                    endDateLabel = new JLabel("End Date");

                    //init the date chooser for start date and end date
                    startDateField = new JDateChooser();
                    endDateField = new JDateChooser();

                    //init the pie chart button
                    pieChartBtn = new JButton("PIE CHART");
                }

                public void start() {

                    //set the size, layout and the background of the pie screen
                    setSize(800, 600);
                    setLayout(new BorderLayout(10, 10));
                    setBackground(new Color(225, 225, 225));

                    //setting different color to the inputs panel
                    inputsPanel.setBackground(new Color(225, 225, 225));

                    //init the head line and set it font, color and place
                    headline.setFont(new Font("Arial", Font.BOLD, 28));
                    headline.setForeground(Color.BLACK);
                    headline.setHorizontalAlignment(JLabel.CENTER);

                    //setting font and color to the start date label
                    startDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    startDateLabel.setForeground(Color.BLACK);

                    //setting font and color to the end date label
                    endDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    endDateLabel.setForeground(Color.BLACK);

                    //set the start date and end date format
                    startDateField.setDateFormatString("yyyy-MM-dd");
                    endDateField.setDateFormatString("yyyy-MM-dd");


                    //set the size for start date and end date
                    startDateField.setPreferredSize(new Dimension(100, 25));
                    endDateField.setPreferredSize(new Dimension(100, 25));

                    //adding action listener to the pie chart button
                    pieChartBtn.addActionListener(e -> {

                        try {
                            if (startDateField.getDate() == null || endDateField.getDate() == null) {
                                throw new CostManagerException("Date cannot be empty");
                            }

                            if (startDateField.getDate().after(endDateField.getDate())) {
                                throw new CostManagerException("Date Range ERROR");
                            }

                            //parse the string to LocalDate Object
                            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                            String sDate = dtf.format(startDateField.getDate());
                            String eDate = dtf.format(endDateField.getDate());

                            //display the pie chart
                            vm.displayPieChart(sDate, eDate);

                        } catch (CostManagerException ex) {
                            View.this.showMessage("problem with selected date... " + ex.getMessage());
                        }

                    });

                    //add the components to the inputs panel
                    inputsPanel.add(startDateLabel);
                    inputsPanel.add(startDateField);
                    inputsPanel.add(endDateLabel);
                    inputsPanel.add(endDateField);
                    inputsPanel.add(pieChartBtn);

                    //add the components to the main panel
                    add(inputsPanel, BorderLayout.CENTER);
                    add(headline, BorderLayout.NORTH);

                }

                /**
                 * createDataset method create pie dataset and return it
                 */
                public DefaultPieDataset createDataset(List<CostItem> pie) {

                    DefaultPieDataset dataset = new DefaultPieDataset();

                    //traverse the pie report and make a list of all the categories
                    List<String> list = new ArrayList<>();
                    for (CostItem item : pie) {
                        list.add(item.getCategory().getCategoryName());
                    }

                    //count is array list that count the frequency of each category
                    List<String> count = new ArrayList<>();
                    for (String cat : list) {
                        if (!(count.contains(cat))) {
                            int freq = Collections.frequency(list, cat);
                            count.add(cat);
                            dataset.setValue(cat, freq);
                        }
                    }

                    return dataset;

                }

            }

        }

    }

}









