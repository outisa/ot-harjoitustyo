
package financialmanagement.ui;
import financialmanagement.dao.SQLUserDao;
import financialmanagement.domain.FinancialManagementService;
        
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class FinancialManagementUi extends Application{
    private Scene newUserScene;
    private Scene loginScene;
    private Scene mainScene;
    
    private Label menuLabel = new Label();
    
  
    private FinancialManagementService financialManagementService;
    
    public static void main(String[] args){
        launch(args);
    }        
    
    @Override
    public void init() throws Exception {
       SQLUserDao userDao = new SQLUserDao();
       
       financialManagementService = new FinancialManagementService(userDao);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label errorMessage = new Label();
        //login scene
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));
        Label loginLabel = new Label("Username:");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("login");
        Button createButton = new Button("create new user");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            menuLabel.setText(username + " is logged in.");
            if(financialManagementService.login(username)){
                // not yet ready
                loginMessage.setText("");
                primaryStage.setScene(mainScene);
                usernameInput.setText("");
           } else {
                loginMessage.setText("User does not exist!");
                loginMessage.setTextFill(Color.RED);
            }
        });
        
        createButton.setOnAction(e->{
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);
        });
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);
        loginScene = new Scene(loginPane, 600, 500);
        
        // createNewUserScene
        VBox newUserPane = new VBox(10);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField();
        Label newUsernameLabel = new Label("Username:");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
        
        Label userCreationMessage = new Label();
        
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
        
        newUserPane.getChildren().addAll(errorMessage, userCreationMessage, newUsernamePane, createNewUserButton);
        newUserScene = new Scene(newUserPane, 600, 500);
        
        // main scene, not yet ready
        
        HBox menuPane = new HBox(10);
        
        Button logoutButton = new Button("logout");
        menuPane.getChildren().addAll(menuLabel, logoutButton , errorMessage);
        logoutButton.setOnAction(e->{ 
            financialManagementService.logout();
            primaryStage.setScene(loginScene);
        });
        mainScene = new Scene(menuPane, 600, 500);
        
        // setup primary stage
        
        primaryStage.setTitle("Financial Management");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
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
    
}
