
package financialmanagement.ui;

import financialmanagement.dao.SQLExpenseDao;
import financialmanagement.dao.SQLIncomeDao;
import financialmanagement.dao.SQLUserDao;
import financialmanagement.domain.Expense;
import financialmanagement.domain.FinancialManagementService;
import financialmanagement.domain.Income;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
        
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FinancialManagementUi extends Application {
      
    private Scene newUserScene;
    private Scene loginScene;
    private Scene mainScene;
    private Scene newIncomeScene;
    private Scene newExpenseScene;
    private Scene listResentTenScene;
    private Scene listExpensesBetweenScene;
    
    private Label menuLabel;
   
    private FinancialManagementService financialManagementService;
    
    
    @Override
    public void init() throws Exception {
       Properties properties = new Properties();
       InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
       properties.load(is);
       
       String database = properties.getProperty("String");
       
       SQLUserDao userDao = new SQLUserDao(database);
       SQLIncomeDao incomeDao = new SQLIncomeDao(database);
       SQLExpenseDao expenseDao = new SQLExpenseDao(database); 
       menuLabel = new Label();
       financialManagementService = new FinancialManagementService(userDao, incomeDao, expenseDao);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label notUser = new Label("If you don't have any username, create new account.");
        notUser.setTextFill(Color.FORESTGREEN);
        //login scene
        VBox loginPane = new VBox(10);
        loginPane.setSpacing(30);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(20));
        Label loginLabel = new Label("Username:");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label(" ");
        
        Button loginButton = new Button("login");
        loginButton.setPadding(new Insets(10, 10, 10, 10));
        Button createButton = new Button("Create new user");
        createButton.setPadding(new Insets(10, 10, 10, 10));
        
        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            menuLabel.setText(username + " is logged in.");            
            try {
                if (financialManagementService.login(username) == true) {
                    loginMessage.setText("");
                    primaryStage.setScene(mainScene);
                    usernameInput.setText("");
                } else {
                    loginMessage.setText("User does not exist!");
                    loginMessage.setTextFill(Color.RED);
                    primaryStage.setScene(loginScene);
                }
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        });

        createButton.setOnAction(e-> {
            usernameInput.setText(""); 
            primaryStage.setScene(newUserScene);
        });
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, notUser,createButton);
        loginScene = new Scene(loginPane, 900, 700);
        
        // createNewUserScene
        VBox newUserPane = new VBox(10);
        newUserPane.setSpacing(20);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setSpacing(20);
        
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField();
        
        Label newUsernameLabel = new Label("Username:");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
        Label userCreationMessage = new Label();
        
        Button backToLogin = new Button("Back to login!");
        backToLogin.setPadding(new Insets(10, 10, 10, 10));
        Button createNewUserButton = new Button("Create user");
        createNewUserButton.setPadding(new Insets(10, 10, 10, 10));
        
        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            newUsernameInput.setText("");
            if (username.length() < 3 || username.length() >= 100){
                userCreationMessage.setText("Username must be between 3 and 99 characters long");
                userCreationMessage.setTextFill(Color.RED);
            } else if(!username.matches("[a-z0-9A-Z]*")) {
                userCreationMessage.setText("Use characters a-z, A-Z, 0-9");
                userCreationMessage.setTextFill(Color.RED);                
            } else try {
                if (financialManagementService.createUser(username)) {
                    userCreationMessage.setText("");
                    loginMessage.setText("New user created");
                    loginMessage.setTextFill(Color.GREEN);
                    primaryStage.setScene(loginScene);
                } else {
                    userCreationMessage.setText("Username has to be unique.");
                    userCreationMessage.setTextFill(Color.RED);
                }
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        backToLogin.setOnAction(e->{
            primaryStage.setScene(loginScene);
        });
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, createNewUserButton, backToLogin);
        newUserScene = new Scene(newUserPane, 900, 700);
        
        // main scene
        BorderPane mainPane = new BorderPane();
        GridPane expensesBetween = new GridPane();
        
        HBox menuPane = new HBox(10);
        menuPane.setPadding(new Insets(20,20,20,20));
        menuPane.setSpacing(10);
        
        VBox selectButtons = new VBox(10);
        selectButtons.setPadding(new Insets(20,20,100,20));
        selectButtons.setSpacing(10);
        
        Button logoutButton = new Button("logout");
        logoutButton.setPadding(new Insets(10, 10, 10, 10));
        Button searchBetween = new Button("Search");
        searchBetween.setPadding(new Insets(10, 10, 10, 10));
        
        // Select buttons in the main scene
        Button addIncome = new Button("New income");
        addIncome.setPadding(new Insets(10, 10, 10, 10));
        Button addExpense = new Button("New expense");
        addExpense.setPadding(new Insets(10, 10, 10, 10));
        Button listLastTenAdds = new Button("List last 10 expenses and incomes");
        listLastTenAdds.setPadding(new Insets(10, 10, 10, 10));
        Button categoriesExpenses = new Button("Expenses per category");
        categoriesExpenses.setPadding(new Insets(10));
        Button categoriesIncome = new Button("Incomes per category");
        categoriesIncome.setPadding(new Insets(10));
        
        // Expenses between selection
        final ComboBox yearFrom = new ComboBox(createYears());
        yearFrom.setValue("2018");
        final ComboBox yearTo = new ComboBox(createYears());
        yearTo.setValue("2018");

        final ComboBox monthFrom = new ComboBox(createMonths());
        final ComboBox monthTo = new ComboBox(createMonths());
        monthFrom.setValue("01");
        monthTo.setValue("01");
        
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        expensesBetween.setVgap(20);
        expensesBetween.setHgap(20);
        expensesBetween.setPadding(new Insets(15, 15, 15, 15));
        expensesBetween.add(new Label("Show all expenses in specific period of time. End date must be after begin date."), 0, 0);
        expensesBetween.add(new Label("Select begin of the search:"), 0, 1);
        expensesBetween.add(yearFrom,1, 1);
        expensesBetween.add(monthFrom, 2, 1);
        expensesBetween.add(new Label("Select end of the search:"), 0, 2);
        expensesBetween.add(yearTo, 1, 2);
        expensesBetween.add(monthTo, 2, 2);
        expensesBetween.add(searchBetween, 1, 3);
        expensesBetween.add(errorMessage, 0, 4);

        selectButtons.getChildren().addAll(addIncome, addExpense, listLastTenAdds, categoriesExpenses, categoriesIncome);
        menuPane.getChildren().addAll(menuLabel, logoutButton);
       
        mainPane.setTop(menuPane);
        mainPane.setCenter(expensesBetween);
        mainPane.setBottom(selectButtons);
        
        // Buttons on action
        searchBetween.setOnAction(e->{
            Date dateFrom = Date.valueOf(yearFrom.getValue().toString() + "-" + monthFrom.getValue().toString() + "-01");
            Date dateTo = Date.valueOf(yearTo.getValue().toString() + "-" + monthTo.getValue().toString() + "-01");
            if (dateFrom.after(dateTo) || dateFrom.equals(dateTo)) {
                errorMessage.setText("Begin date must be before end date.");
            } else {
                errorMessage.setText("");
                listExpensesBetweenScene = new Scene(listExpensesBetween(primaryStage, dateFrom, dateTo), 900, 700);
                primaryStage.setScene(listExpensesBetweenScene);
            }    
        });
           
        logoutButton.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        
        addIncome.setOnAction(e->{
            primaryStage.setScene(newIncomeScene);
        });
        
        addExpense.setOnAction(e->{
            primaryStage.setScene(newExpenseScene);
        });
        
        listLastTenAdds.setOnAction(e->{             
            listResentTenScene = new Scene(listLastTenIncomesAndOutcomes(primaryStage), 900, 700);
            primaryStage.setScene(listResentTenScene);
        });
        
        categoriesExpenses.setOnAction(e->{
            try {
                Scene showCategoriesExpense = new Scene(overviewCategoriesExpenses(primaryStage), 900, 700);
                primaryStage.setScene(showCategoriesExpense);
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        categoriesIncome.setOnAction(e->{
            try {
                Scene showCategoriesIncome = new Scene(overviewCategoriesIncome(primaryStage), 900, 700);
                primaryStage.setScene(showCategoriesIncome);
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mainScene = new Scene(mainPane, 900, 700);
    
        //new income scene
        newIncomeScene = new Scene(addNewIncome(primaryStage), 900, 700); 
        
        // new expense scene 
        newExpenseScene = new Scene(addNewExpense(primaryStage), 900, 700);
        
        // setup primary stage 
        primaryStage.setTitle("Financial Management");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {
            if(financialManagementService.getLoggedUser()!= null){
                financialManagementService.logout();
            }    
        });
    }
    @Override
    public void stop() {
    }
    
    public static void main(String[] args) {
        launch(args);        
    } 
    
    // Expenses and their percentage from total for each category.
    
    public BorderPane overviewCategoriesExpenses(Stage primaryStage) throws Exception {
        BorderPane organizePane = new BorderPane();
        
        VBox categoriesList = new VBox();
        categoriesList.setPadding(new Insets(10));
        categoriesList.setSpacing(10);

        BarChart<String, Number> expensesChart = createBarChart("Expenses per category");       

        Button backToMain = new Button("Back to main");
        backToMain.setPadding(new Insets(10));
        
        backToMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        
        HashMap<String, ArrayList<Double>> expenseCategories = financialManagementService.overviewExpenses(financialManagementService.getLoggedUser().getId());
       
        XYChart.Series categories = new XYChart.Series<>();
        for (String category: expenseCategories.keySet()) {
            if (expenseCategories.get(category).isEmpty()) {
                categories.getData().add(new XYChart.Data<>(category, 0.0));
                categoriesList.getChildren().add(new Label(category + ":   0.0 €"));
            } else {
                categories.getData().add(new XYChart.Data<>(category, expenseCategories.get(category).get(1), expenseCategories.get(category).get(1)));
                categoriesList.getChildren().add(new Label(category + ":   " + expenseCategories.get(category).get(0) + " €"));
            }    
        }
        expensesChart.getData().add(categories);
        organizePane.setPadding(new Insets(10));
        organizePane.setTop(backToMain);
        organizePane.setCenter(expensesChart);
        organizePane.setBottom(categoriesList);
        return organizePane;
    }
    
    // Incomes and their percentage from total for each category.
    
    public BorderPane overviewCategoriesIncome(Stage primaryStage) throws Exception {
        BorderPane organizePane = new BorderPane();

        BarChart<String, Number> expensesChart = createBarChart("Incomes per category");
        
        VBox categoriesList = new VBox();
        categoriesList.setPadding(new Insets(10));
        categoriesList.setSpacing(10);
       
        Button backToMain = new Button("Back to main");
        backToMain.setPadding(new Insets(10));
        
        backToMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        
        HashMap<String, ArrayList<Double>> expenseCategories = financialManagementService.overviewIncomes(financialManagementService.getLoggedUser().getId());
       
        XYChart.Series categories = new XYChart.Series<>();
        for (String category: expenseCategories.keySet()) {
            if (expenseCategories.get(category).isEmpty()) {
                categories.getData().add(new XYChart.Data<>(category, 0.0));
                categoriesList.getChildren().add(new Label(category + ":   0.0 €"));
            } else {
                categories.getData().add(new XYChart.Data<>(category, expenseCategories.get(category).get(1), expenseCategories.get(category).get(1)));
                categoriesList.getChildren().add(new Label(category + ":   " + expenseCategories.get(category).get(0) + " €"));
            }    
        }
        expensesChart.getData().add(categories);
        organizePane.setPadding(new Insets(10));
        organizePane.setTop(backToMain);
        organizePane.setCenter(expensesChart);
        organizePane.setBottom(categoriesList);
        return organizePane;
    }
    
    // List all expenses between given Period
    
    public BorderPane listExpensesBetween(Stage primaryStage, Date dateFrom, Date dateTo) {
        BorderPane organizePane = new BorderPane();
        
        List<Expense> expenses = financialManagementService.listExpensesBetween(financialManagementService.getLoggedUser().getId(), dateFrom, dateTo);
        
        ObservableList<String> data = FXCollections.observableArrayList();
        ListView expensesView = new ListView(data);
        expensesView.setPrefSize(200, 250);
        expensesView.setEditable(true);
        data.add("Date        Category        Amount");
        for (int i = 0; i < expenses.size(); i++) {
            String date = expenses.get(i).getDate().toString();
            data.add( date + "  " + expenses.get(i).getCategory() + "  " +String.valueOf(expenses.get(i).getAmount()) + " €");
        }
        expensesView.setItems(data);
        HBox menuBox = new HBox();
        menuBox.setPadding(new Insets(10, 10, 10, 10));
        menuBox.setSpacing(30);
        
        
        Button backToMain = new Button("Back to overview");
        Button logout = new Button("Logout");
        Label label = new Label("Expenses from " + dateFrom.toString() + " to " + dateTo.toString() + " (end date is not included)");
        
        menuBox.getChildren().addAll(label, backToMain, logout);
        
        backToMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        
        logout.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        
        organizePane.setTop(menuBox);
        organizePane.setCenter(expensesView);
        return organizePane;
    }
    
    // List last ten newest incomes and outcomes.  
    public BorderPane listLastTenIncomesAndOutcomes(Stage primaryStage) {
        ScrollPane listPane = new ScrollPane();
        BorderPane organizePane = new BorderPane(listPane);
        GridPane incomesPane = new GridPane();
        incomesPane.setPadding(new Insets(20,50,20,20));
        incomesPane.setHgap(20);
        incomesPane.setVgap(10);
        
        GridPane expensesPane= new GridPane();
        expensesPane.setPadding(new Insets(20,20,20,50));
        expensesPane.setHgap(20);
        expensesPane.setVgap(10); 
        HBox menu = new HBox(10);
        menu.setSpacing(20);
        menu.setPadding(new Insets(10,10,10,10));
        
        Button backtoMain = new Button("Back to overview");
        backtoMain.setPadding(new Insets(10, 10, 10, 10));
        Button logout = new Button("Logout");
        logout.setPadding(new Insets(10, 10, 10, 10));
        
        menu.getChildren().addAll(new Label("Last 10 added incomes and expenses"), backtoMain, logout);
        int account_id = 0;
        if(financialManagementService.getLoggedUser() != null) {
            account_id = financialManagementService.getLoggedUser().getId();
        }

        List<Expense> expenses = financialManagementService.listExpenses(account_id);
        List<Income> incomes = financialManagementService.listIncomes(account_id);
       
        incomesPane.add(new Label("Incomes:"), 0, 0);
        incomesPane.add(new Label("Date:"), 0, 1);
        incomesPane.add(new Label("Category:"), 1, 1);
        incomesPane.add(new Label("Amount:"), 2, 1);
        for (int i = 0; i < incomes.size(); i++) {
            String date = incomes.get(i).getDatetime().toString();
            incomesPane.add(new Label(date), 0, i + 2);
            incomesPane.add(new Label(incomes.get(i).getCategory()), 1, i + 2);
            incomesPane.add(new Label(String.valueOf(incomes.get(i).getAmount())), 2, i + 2);

        }
        expensesPane.add(new Label("Expenses:"), 0, 0);
        expensesPane.add(new Label("Date:"), 0, 1);
        expensesPane.add(new Label("Category:"), 1, 1);
        expensesPane.add(new Label("Price:"), 2, 1);
        for (int i = 0; i < expenses.size(); i++) {
            String date = expenses.get(i).getDate().toString();
            expensesPane.add(new Label(date), 0, i + 2);
            expensesPane.add(new Label(expenses.get(i).getCategory()), 1, i + 2);
            expensesPane.add(new Label(String.valueOf(expenses.get(i).getAmount())), 2, i + 2);
        }
     

        organizePane.setTop(menu);
        organizePane.setLeft(incomesPane);
        organizePane.setCenter(expensesPane);
       
        backtoMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        
        logout.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        
        return organizePane;
    }
    
    // creates add new income scene
    
    public BorderPane addNewIncome(Stage primaryStage) {
        Label errormessageIncome = new Label ();
        GridPane newIncomePane = new GridPane();
        BorderPane organizePane = new BorderPane();
        organizePane.setPadding(new Insets(10, 10, 10, 10));
        
        HBox menuBox = new HBox();
        menuBox.setPadding(new Insets(10, 10, 10, 10));
        menuBox.setSpacing(30);
        
        Label headerLabel = new Label("Add new income");
        TextField setAmount = new TextField("0.00");
        Button backtoMain = new Button("Back to overview");
        backtoMain.setPadding(new Insets(10,10,10,10));
        Button newIncome = new Button("Add income");
        newIncome.setPadding(new Insets(10,10,10,10));
        Button logout = new Button("logout");
        logout.setPadding(new Insets(10,10,10,10));
        
        ComboBox date = createdays();

        final ComboBox setCategory = new ComboBox(createCategories());
        setCategory.setValue("Other");
        
        Label notAnumberError = new Label();
        newIncomePane.setHgap(10);
        newIncomePane.setVgap(10);

        newIncomePane.add(notAnumberError, 2, 3);
        newIncomePane.add(new Label("Give date here:"), 0, 2);
        newIncomePane.add(date, 1, 2);
        newIncomePane.add( new Label("Give amount here (xxx.xx):"), 0, 3);
        newIncomePane.add(setAmount, 1, 3);
        newIncomePane.add(new Label("Choose category:"), 0, 4);
        newIncomePane.add(setCategory, 1, 4);
        newIncomePane.add(newIncome, 1, 5);
        newIncomePane.add(errormessageIncome, 2, 5);

        menuBox.getChildren().addAll(headerLabel, backtoMain, logout);
        setAmount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?") || newValue.isEmpty()) {
                notAnumberError.setText("Invalid number form");
                notAnumberError.setTextFill(Color.RED);
            } else {
                notAnumberError.setText("");
            }
        });

        backtoMain.setOnAction(e -> {
            errormessageIncome.setText("");
            primaryStage.setScene(mainScene);
        });
        
        logout.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        
        newIncome.setOnAction(e->{
            double amount = Double.valueOf(setAmount.getText());
            String selectedDate = date.getValue().toString();
            Date datetime = Date.valueOf(selectedDate);
            
            try {
                if (financialManagementService.createIncome(datetime, amount, setCategory.getValue().toString(), financialManagementService.getLoggedUser().getId()) == false) {
                    errormessageIncome.setText("Income exists already");
                    errormessageIncome.setTextFill(Color.RED);
                    date.setValue(date.getItems().get(0).toString());
                    
                } else {
                    errormessageIncome.setText("Income is added");
                    errormessageIncome.setTextFill(Color.GREEN);    
                }
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });               
        organizePane.setTop(menuBox);
        organizePane.setCenter(newIncomePane);
        return organizePane;
    }
    
    // Add new expense scene
    
    public BorderPane addNewExpense(Stage primaryStage) {
        Label errormessageExpense = new Label ();
        GridPane expensePane = new GridPane();
        BorderPane organizePane = new BorderPane();
        organizePane.setPadding(new Insets(10, 10, 10, 10));
        HBox menuBox = new HBox();
        menuBox.setPadding(new Insets(10, 10, 10, 10));
        menuBox.setSpacing(30);
        
        Label headerLabel = new Label("Add new expense");
        
        TextField setAmount = new TextField("0.00");
        Button backtoMain = new Button("Back to overview");
        backtoMain.setPadding(new Insets(10,10,10,10));
        Button newExpense = new Button("Add expense");
        newExpense.setPadding(new Insets(10, 10, 10, 10));
        Button logout = new Button("Logout");
        logout.setPadding(new Insets(10, 10, 10, 10));
                
        ComboBox setDate = createdays();
        final ComboBox setCategory = new ComboBox(createExpenseCategories());
        setCategory.setValue("Other");
        
        menuBox.getChildren().addAll(headerLabel, backtoMain, logout);
        
        Label notAnumberError = new Label();
        expensePane.setHgap(10);
        expensePane.setVgap(10);

        expensePane.add(notAnumberError, 1, 4);
        expensePane.add(new Label("Give date here:"), 0, 1);
        expensePane.add(setDate, 1, 1);
        expensePane.add( new Label("Give amount here (xxx.xx)"), 0, 2);
        expensePane.add(setAmount, 1, 2);
        expensePane.add(new Label("Choose category"), 0, 3);
        expensePane.add(setCategory, 1, 3);
        expensePane.add(newExpense, 1,6);
        expensePane.add(errormessageExpense, 2,4);

        setAmount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?") || newValue.isEmpty()) {
                notAnumberError.setText("Invalid number! Give number value between 0.0 and 9999999.99");
                notAnumberError.setTextFill(Color.RED);
            } else {
                notAnumberError.setText("");
            }
        });

        backtoMain.setOnAction(e -> {
            errormessageExpense.setText("");
            primaryStage.setScene(mainScene);
        });

        logout.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        
        newExpense.setOnAction((ActionEvent e)->{
            double price = Double.valueOf(setAmount.getText());
            String date = setDate.getValue().toString();
            Date datetime = Date.valueOf(date);
            
            try {
                if (financialManagementService.createExpense(datetime, price, setCategory.getValue().toString(), financialManagementService.getLoggedUser().getId()) == false) {
                    errormessageExpense.setText("Expense exists already");
                    errormessageExpense.setTextFill(Color.RED);
                    setDate.setValue(setDate.getItems().get(0).toString());
                    
                } else {
                    errormessageExpense.setText("Expense added");
                    errormessageExpense.setTextFill(Color.GREEN);    
                }
            } catch (Exception ex) {
                Logger.getLogger(FinancialManagementUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });     
        organizePane.setTop(menuBox);
        organizePane.setCenter(expensePane);
        return organizePane;
    }    
    
    private ObservableList createYears() {
                ObservableList<String> years = 
                FXCollections.observableArrayList(
                        "2018",
                        "2019",
                        "2020",
                        "2021",
                        "2022",
                        "2023",
                        "2024",
                        "2025",
                        "2026",
                        "2027",
                        "2028"
                );
        return years;
    }

    private ObservableList createMonths() {
                ObservableList<String> months = 
                FXCollections.observableArrayList(
                        "01",
                        "02",
                        "03",
                        "04",
                        "05",
                        "06",
                        "07",
                        "08",
                        "09",
                        "10",
                        "11",
                        "12"
                );
        return months;        
    }

    private ObservableList createCategories() {
        ObservableList<String> categories = 
                FXCollections.observableArrayList(
                        "Salary",
                        "Present",
                        "Other"
                );
        return categories;
    }
    
    private ObservableList createExpenseCategories() {
        ObservableList<String> categories = 
                FXCollections.observableArrayList(
                        "Other",
                        "Home",
                        "Food",
                        "Car",
                        "Hobbies",
                        "Restaurants",
                        "Education",
                        "Travelling",
                        "Insurances"                         
                );
        return categories;
    }

    private BarChart<String, Number> createBarChart(String text) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("% from total");
        
        BarChart<String, Number> newChart = new BarChart<>(xAxis, yAxis);
        newChart.setTitle(text);
        newChart.setLegendVisible(false);

        return newChart;
    }

    private ComboBox createdays() {
        ComboBox date = new ComboBox();
        for (int i = 0; i < 92; i++) {
            date.getItems().add(LocalDate.now().minusDays(i));
        }
        date.setValue(date.getItems().get(0).toString());
        return date;
    }

}
