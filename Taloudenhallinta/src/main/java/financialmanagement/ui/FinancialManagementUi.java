
package financialmanagement.ui;
import financialmanagement.dao.SQLExpenseDao;
import financialmanagement.dao.SQLIncomeDao;
import financialmanagement.dao.SQLUserDao;
import financialmanagement.domain.Expense;
import financialmanagement.domain.FinancialManagementService;
import financialmanagement.domain.Income;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
        
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class FinancialManagementUi extends Application{
    private Scene newUserScene;
    private Scene loginScene;
    private Scene mainScene;
    private Scene newIncomeScene;
    private Scene newExpenseScene;
   
    private VBox incomeNodes;
    private VBox outcomeNodes;
    
    private Label menuLabel;
   
    private FinancialManagementService financialManagementService;
    
    public static void main(String[] args) {
        launch(args);
    }        
    private Scene listResentTenScene;
    
    @Override
    public void init() throws Exception {
       SQLUserDao userDao = new SQLUserDao();
       SQLIncomeDao incomeDao = new SQLIncomeDao();
       SQLExpenseDao expenseDao = new SQLExpenseDao(); 
       menuLabel = new Label();
       financialManagementService = new FinancialManagementService(userDao, incomeDao, expenseDao);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label errorMessage = new Label();
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
        Button createButton = new Button("create new user");

        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            menuLabel.setText(username + " is logged in.");
            if(financialManagementService.login(username) == true) {
                // not yet ready
                loginMessage.setText("");
                primaryStage.setScene(mainScene);
                usernameInput.setText("");
            } else {
                loginMessage.setText("User does not exist!");
                loginMessage.setTextFill(Color.RED);
                primaryStage.setScene(loginScene);
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
        Button createNewUserButton = new Button("create new user");
        createNewUserButton.setPadding(new Insets(10));
        
        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            newUsernameInput.setText("");
            if(username.length() < 3){
                userCreationMessage.setText("Username is too short, you need at least 3 characters");
                userCreationMessage.setTextFill(Color.RED);
            } else if(financialManagementService.createUser(username)){
                userCreationMessage.setText("");
                loginMessage.setText("New user created");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(loginScene);
            } else {
                userCreationMessage.setText("Username has to be unique.");
                userCreationMessage.setTextFill(Color.RED);
            }
        });
        
        backToLogin.setOnAction(e->{
            primaryStage.setScene(loginScene);
        });
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, createNewUserButton, backToLogin);
        newUserScene = new Scene(newUserPane, 900, 700);
        
        // main scene, ulkonäkö vaatii viimeistelyä
        BorderPane ground = new BorderPane();
        GridPane expensesBetween = new GridPane();
        
        HBox menuPane = new HBox(10);
        menuPane.setPadding(new Insets(20,20,20,20));
        menuPane.setSpacing(10);
        
        HBox selectButtons = new HBox(10);
        selectButtons.setPadding(new Insets(20,20,20,20));
        selectButtons.setSpacing(10);
        
        Button logoutButton = new Button("logout");
    
        final ComboBox yearFrom = new ComboBox(createYears());
        yearFrom.setValue("2018");
        final ComboBox yearTo = new ComboBox(createYears());
        yearTo.setValue("2018");

        final ComboBox monthFrom = new ComboBox(createMonths());
        final ComboBox monthTo = new ComboBox(createMonths());
        monthFrom.setValue("01");
        monthTo.setValue("01");
        
        expensesBetween.setVgap(4);
        expensesBetween.setHgap(10);
        expensesBetween.setPadding(new Insets(15, 15, 15, 15));
        expensesBetween.add(new Label("Show all expenses in specific period of time."), 0, 0);
        expensesBetween.add(new Label("Select begin of the search:"), 0, 1);
        expensesBetween.add(yearFrom,1, 1);
        expensesBetween.add(monthFrom, 2, 1);
        expensesBetween.add(new Label("Select end of the search:"), 0, 2);
        expensesBetween.add(yearTo, 1, 2);
        expensesBetween.add(monthTo, 2, 2);
        expensesBetween.add(new Button("Search"), 1, 3);
        
        Button addIncome = new Button("Add new income");
        Button addExpense = new Button("Add new expense");
        Button listAll = new Button("List last 10 expenses and 10 incomes");
   
        selectButtons.getChildren().addAll(addIncome, addExpense, listAll);
        menuPane.getChildren().addAll(menuLabel, logoutButton , errorMessage);
       
        ground.setTop(menuPane);
        ground.setCenter(expensesBetween);
        ground.setBottom(selectButtons);
           
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
        listAll.setOnAction(e->{ 
            listResentTenScene = new Scene(listLastTenIncomesAndOutcomes(primaryStage), 900, 700);
            primaryStage.setScene(listResentTenScene);
        });
        mainScene = new Scene(ground, 900, 700);
        
        //new income scene
        newIncomeScene = new Scene(addNewIncome(primaryStage), 900, 700); 
        
        // new expense scene 
        newExpenseScene = new Scene(addNewExpense(primaryStage), 900, 700);
        
        // list last last 10 incomes and expenses 
        
        listResentTenScene = new Scene(listLastTenIncomesAndOutcomes(primaryStage), 900, 700);
        
        // setup primary stage 
        primaryStage.setTitle("Financial Management");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {
            System.out.println("closing");
            System.out.println(financialManagementService.getLoggedUser());
            if(financialManagementService.getLoggedUser()!= null){
                errorMessage.setText("logout first");
                errorMessage.setTextFill(Color.FIREBRICK);
                e.consume();
            }    
        });
    }
    @Override
    public void stop(){
        System.out.println("Application is closing");
    }
    // When SQL in use change 1 to financialManagementService.getLoggedUser().getId()
    public BorderPane listLastTenIncomesAndOutcomes(Stage primaryStage) {
        ScrollPane listPane = new ScrollPane();
        BorderPane pane = new BorderPane(listPane);
        GridPane incomesPane = new GridPane();
        incomesPane.setPadding(new Insets(20,20,20,20));
        incomesPane.setHgap(20);
        incomesPane.setVgap(10);
        
        GridPane expensesPane= new GridPane();
        expensesPane.setPadding(new Insets(20,20,20,20));
        expensesPane.setHgap(20);
        expensesPane.setVgap(10)
                ;        
        HBox menu = new HBox(10);
        menu.setSpacing(20);
        menu.setPadding(new Insets(10,10,10,10));
        
        Button backtoMain = new Button("Back to overview");
        menu.getChildren().addAll(new Label("Last 10 added incomes and expenses"), backtoMain);

        List<Expense> expenses = financialManagementService.listExpenses(1);
        List<Income> incomes = financialManagementService.listIncomes(1);
       
        incomesPane.add(new Label("Date:"), 0, 0);
        incomesPane.add(new Label("Category:"), 1, 0);
        incomesPane.add(new Label("Amount:"), 2, 0);
        for (int i = 0; i < incomes.size(); i++) {
            String date = incomes.get(i).getDatetime().toString().substring(0, 10);
            incomesPane.add(new Label(date), 0, i + 1);
            incomesPane.add(new Label(incomes.get(i).getCategory()), 1, i + 1);
            incomesPane.add(new Label(String.valueOf(incomes.get(i).getAmount())), 2, i + 1);

        }
        expensesPane.add(new Label("Date:"), 0, 0);
        expensesPane.add(new Label("Category:"), 1, 0);
        expensesPane.add(new Label("Price:"), 2, 0);
        for (int i = 0; i < expenses.size(); i++) {
            String date = incomes.get(i).getDatetime().toString().substring(0, 10);
            expensesPane.add(new Label(date), 0, i + 1);
            expensesPane.add(new Label(expenses.get(i).getCategory()), 1, i + 1);
            expensesPane.add(new Label(String.valueOf(expenses.get(i).getAmount())), 2, i + 1);

        }
        Label income = new Label("Incomes:");
        Label expense = new Label("Expenses:");
        VBox listLayout = new VBox(10);
        listLayout.setPadding(new Insets(20,10,10,20));
        listLayout.setSpacing(20);
        listLayout.getChildren().addAll(income, incomesPane, expense, expensesPane);
        pane.setTop(menu);
        pane.setCenter(listLayout);
        backtoMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        
        return pane;
    }
    // loggedIn.getId must be added, when SQL in use
    public GridPane addNewIncome(Stage primaryStage) {
        Label errormessageIncome = new Label ();
        GridPane newIncomePane = new GridPane();
        
        TextField setAmount = new TextField("0.00");
        Button backtoMain = new Button("Back to overview");
        Button newIncome = new Button("create new income");
        
        final ComboBox setYear = new ComboBox(createYears());
        setYear.setValue("2018");
        final ComboBox setMonth = new ComboBox(createMonths());
        setMonth.setValue("01");
        final ComboBox setday = new ComboBox(createDays());
        setday.setValue("01");
        final ComboBox setCategory = new ComboBox(createCategories());
        setCategory.setValue("Other");
        
        backtoMain.setPadding(new Insets(10,10,10,10));
        Label notAnumberError = new Label();
        newIncomePane.setHgap(10);
        newIncomePane.setVgap(10);
        newIncomePane.add(new Label("year"), 1, 0);
        newIncomePane.add(new Label("month"), 2, 0);
        newIncomePane.add(new Label("day"), 3, 0);
        newIncomePane.add(notAnumberError, 1, 4);
        newIncomePane.add(new Label("Give date here:"), 0, 1);
        newIncomePane.add(setYear, 1, 1);
        newIncomePane.add(setMonth, 2, 1);
        newIncomePane.add(setday, 3, 1);
        newIncomePane.add( new Label("Give amount here (xxx.xx)"), 0, 2);
        newIncomePane.add(setAmount, 1, 2);
        newIncomePane.add(new Label("Choose category"), 0, 3);
        newIncomePane.add(setCategory, 1, 3);
        newIncomePane.add(newIncome, 1,6);
        newIncomePane.add(backtoMain, 1, 5);
        newIncomePane.add(errormessageIncome, 2,4);

        setAmount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?") || newValue.isEmpty()) {
                notAnumberError.setText("Invalid number form");
                notAnumberError.setTextFill(Color.RED);
            } else {
                notAnumberError.setText("");
            }
        });

        backtoMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });
        newIncome.setOnAction(e->{
            double price = Double.valueOf(setAmount.getText());
            String year = setYear.getValue().toString();
            String month = setMonth.getValue().toString();
            String day = setday.getValue().toString();
            
            LocalDateTime datetime = LocalDateTime.parse(year + "-" + month +
                    "-" + day + " " + "00:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            if (financialManagementService.createIncome(datetime, price, setCategory.getValue().toString(), 1) == false) {
                errormessageIncome.setText("Income exists already");
                errormessageIncome.setTextFill(Color.RED);
                setYear.setValue("2018");
                setMonth.setValue("01");
                setday.setValue("01");
                
            } else {
                errormessageIncome.setText("Income is added");
                errormessageIncome.setTextFill(Color.GREEN);
            }    
        });               
        return newIncomePane;
    }
    //loggedIn.getId must be addes when SQL in use
    public GridPane addNewExpense(Stage primaryStage) {
        Label errormessageExpense = new Label ();
        GridPane expensePane = new GridPane();
        
        TextField setAmount = new TextField("0.00");
        Button backtoMain = new Button("Back to overview");
        Button newExpense = new Button("add expense");
        
        final ComboBox setYear = new ComboBox(createYears());
        setYear.setValue("2018");
        final ComboBox setMonth = new ComboBox(createMonths());
        setMonth.setValue("01");
        final ComboBox setday = new ComboBox(createDays());
        setday.setValue("01");
        final ComboBox setCategory = new ComboBox(createExpenseCategories());
        setCategory.setValue("Other");
        
        backtoMain.setPadding(new Insets(10,10,10,10));
        Label notAnumberError = new Label();
        expensePane.setHgap(10);
        expensePane.setVgap(10);
        expensePane.add(new Label("year"), 1, 0);
        expensePane.add(new Label("month"), 2, 0);
        expensePane.add(new Label("day"), 3, 0);
        expensePane.add(notAnumberError, 1, 4);
        expensePane.add(new Label("Give date here:"), 0, 1);
        expensePane.add(setYear, 1, 1);
        expensePane.add(setMonth, 2, 1);
        expensePane.add(setday, 3, 1);
        expensePane.add( new Label("Give amount here (xxx.xx)"), 0, 2);
        expensePane.add(setAmount, 1, 2);
        expensePane.add(new Label("Choose category"), 0, 3);
        expensePane.add(setCategory, 1, 3);
        expensePane.add(newExpense, 1,6);
        expensePane.add(backtoMain, 1, 5);
        expensePane.add(errormessageExpense, 2,4);

        setAmount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?") || newValue.isEmpty()) {
                notAnumberError.setText("Invalid number form");
                notAnumberError.setTextFill(Color.RED);
            } else {
                notAnumberError.setText("");
            }
        });

        backtoMain.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });

        newExpense.setOnAction(e->{
            double price = Double.valueOf(setAmount.getText());
            String year = setYear.getValue().toString();
            String month = setMonth.getValue().toString();
            String day = setday.getValue().toString();
            
            LocalDateTime datetime = LocalDateTime.parse(year + "-" + month +
                    "-" + day + " " + "00:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            if (financialManagementService.createExpense(datetime, price, setCategory.getValue().toString(), 1) == false) {
                errormessageExpense.setText("Expense exists already");
                errormessageExpense.setTextFill(Color.RED);
                setYear.setValue("2018");
                setMonth.setValue("01");
                setday.setValue("01");
                
            } else {
                errormessageExpense.setText("Expense is added");
                errormessageExpense.setTextFill(Color.GREEN);
            }    
        });               
        return expensePane;
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
    private ObservableList createDays() {
                ObservableList<String> days = 
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
                        "12",
                        "13",
                        "14",
                        "15",
                        "16",
                        "17",
                        "18",
                        "19",
                        "20",
                        "21",
                        "22",
                        "23",
                        "24",
                        "25",
                        "26",
                        "27",
                        "28",
                        "29",
                        "30",
                        "31"
                );
        return days;        
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
                        "Houme",
                        "Food",
                        "Car",
                        "Hobbies",
                        "Restaurants",
                        "Education",
                        "Travelling"                        
                );
        return categories;
    }
}
