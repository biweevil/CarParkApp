package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private SplitPane AppSplitBar;

    @FXML
    private AnchorPane LogInBG;

    @FXML
    private TextField UserNameTextbox;

    @FXML
    private PasswordField PasswordTextbox;

    @FXML
    private Button LoginButton;

    @FXML
    private Label LogInFeeback;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField UserNameSignupTextbox1;

    @FXML
    private PasswordField PasswordSignupTextbox1;

    @FXML
    private AnchorPane AppMain;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button TopUpButton;

    @FXML
    private Button VerificationButton;

    @FXML
    private Button SecureVehicleButton;

    @FXML
    private Label signedinas;

    @FXML
    private Label balanceSign;

    @FXML
    private Button DayPassButton;

    @FXML
    private Button WeekPassButton;

    @FXML
    private Button SeasonPassButton;

    @FXML
    private Label DayPassDate;

    @FXML
    private Label WeekPassDate;

    @FXML
    private Label SeasonPassDate;

    @FXML
    void initialize() {
        assert AppSplitBar != null : "fx:id=\"AppSplitBar\" was not injected: check your FXML file 'App.fxml'.";
        assert LogInBG != null : "fx:id=\"LogInBG\" was not injected: check your FXML file 'App.fxml'.";
        assert UserNameTextbox != null : "fx:id=\"UserNameTextbox\" was not injected: check your FXML file 'App.fxml'.";
        assert PasswordTextbox != null : "fx:id=\"PasswordTextbox\" was not injected: check your FXML file 'App.fxml'.";
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'App.fxml'.";
        assert LogInFeeback != null : "fx:id=\"LogInFeeback\" was not injected: check your FXML file 'App.fxml'.";
        assert SignUpButton != null : "fx:id=\"SignUpButton\" was not injected: check your FXML file 'App.fxml'.";
        assert UserNameSignupTextbox1 != null : "fx:id=\"UserNameSignupTextbox1\" was not injected: check your FXML file 'App.fxml'.";
        assert PasswordSignupTextbox1 != null : "fx:id=\"PasswordSignupTextbox1\" was not injected: check your FXML file 'App.fxml'.";
        assert AppMain != null : "fx:id=\"AppMain\" was not injected: check your FXML file 'App.fxml'.";
        assert LogoutButton != null : "fx:id=\"LogoutButton\" was not injected: check your FXML file 'App.fxml'.";
        assert TopUpButton != null : "fx:id=\"TopUpButton\" was not injected: check your FXML file 'App.fxml'.";
        assert VerificationButton != null : "fx:id=\"VerificationButton\" was not injected: check your FXML file 'App.fxml'.";
        assert SecureVehicleButton != null : "fx:id=\"SecureVehicleButton\" was not injected: check your FXML file 'App.fxml'.";
        assert signedinas != null : "fx:id=\"signedinas\" was not injected: check your FXML file 'App.fxml'.";
        assert balanceSign != null : "fx:id=\"balanceSign\" was not injected: check your FXML file 'App.fxml'.";
        AppCallBacks();
    }

    private AccountInfo accountInfo;

    private void AppCallBacks()
    {
        AppMain.setVisible(false);
        new File(System.getProperty("user.home")+"/Accounts").mkdirs();
        SignUpButton.setOnAction((ActionEvent event) ->
        {

            if ((UserNameSignupTextbox1.getText().isEmpty()) || (PasswordSignupTextbox1.getText().isEmpty()))
            {
                LogInFeeback.setText("You need to enter the Username and Password");
            } else
            {
                if (new File(System.getProperty("user.home")+"/Accounts/" + UserNameSignupTextbox1.getText() + ".txt").exists())
                {
                    LogInFeeback.setText("Username already Exists");
                    UserNameSignupTextbox1.setText("");
                    PasswordSignupTextbox1.setText("");
                } else
                {
                    File accountFile;
                    accountFile = new File(System.getProperty("user.home")+"/Accounts/" + UserNameSignupTextbox1.getText() + ".txt");

                    try
                    {
                        accountFile.createNewFile();
                        accountInfo = AccountInfo.newAccount(accountFile, PasswordSignupTextbox1.getText());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    signedinas.setText("Signed in as: " + UserNameSignupTextbox1.getText());
                    try {
                        balanceSign.setText("Balance: £" + new DecimalFormat("###,###.00").parse(String.valueOf(accountInfo.getBalance())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    UserNameSignupTextbox1.setText("");
                    PasswordSignupTextbox1.setText("");
                    AppSplitBar.setDividerPosition(0, 0.0);
                    AppMain.setVisible(true);
                    LogInFeeback.setText("Login Successful!");
                }
            }


        });

        LoginButton.setOnAction((event ->
        {
            File currentAccount = new File(System.getProperty("user.home")+"/Accounts/" + UserNameTextbox.getText() + ".txt");
            if (currentAccount.exists())
            {
                try
                {
                    Scanner scanner = new Scanner(new FileInputStream(currentAccount));
                    if (scanner.nextLine().equals(PasswordTextbox.getText()))
                    {
                        LogInFeeback.setText("Login Successful!");
                        signedinas.setText("Signed in as: " + UserNameTextbox.getText());
                        UserNameTextbox.setText("");
                        PasswordTextbox.setText("");
                        AppSplitBar.setDividerPosition(0, 0.0);
                        AppMain.setVisible(true);
                        scanner.close();
                        accountInfo = new AccountInfo(currentAccount);
                        balanceSign.setText("Balance: £" + new DecimalFormat("###,###.00").parse(String.valueOf(accountInfo.getBalance())));
                    } else
                    {
                        LogInFeeback.setText("Incorrect Password");
                        UserNameTextbox.setText("");
                        PasswordTextbox.setText("");
                    }
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                LogInFeeback.setText("User Doesn't exist");
                UserNameTextbox.setText("");
                PasswordTextbox.setText("");
            }

        }));
        LogoutButton.setOnAction(event ->
        {

            AppMain.setVisible(false);
            AppSplitBar.setDividerPosition(0, 0.2764);
            LogInFeeback.setText("Succesfully Signed Out!");
            accountInfo.update();
            accountInfo = null;
        });

        TopUpButton.setOnAction((event) ->
        {
            TextInputDialog dialog = new TextInputDialog("0.0");
            dialog.setTitle("Account Top Up");
            dialog.setHeaderText("Top up");
            dialog.setContentText("Please enter top up amount:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent((string) ->
            {
                double topUpAmount;
                try
                {
                    topUpAmount = Double.valueOf(string);
                } catch (NumberFormatException e)
                {
                    topUpAmount = 0;
                }
                if (topUpAmount > 0)
                {
                    accountInfo.setBalance(accountInfo.getBalance() + topUpAmount);
                    accountInfo.update();
                }
                try
                {
                    balanceSign.setText("Balance: £" + new DecimalFormat("###,###.00").parse(String.valueOf(accountInfo.getBalance())));
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            });
        });

        VerificationButton.setOnAction(event ->
        {

            if (Objects.equals(accountInfo.getPhoneNumber(), ""))
            {
                boolean validNumber = false;
                TextInputDialog numberdialog = new TextInputDialog("44");
                numberdialog.setTitle("Verify Number");
                numberdialog.setHeaderText("Verify Number");
                numberdialog.setContentText("Please enter mobile number:");
                Optional <String> result = numberdialog.showAndWait();
                if (result.isPresent())
                {
                    try
                    {
                        new PhoneNumber(result.get());
                        validNumber = true;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                if(validNumber){
                    StringBuilder validationBuilder = new StringBuilder();
                    for (int i = 0; i < 5; i++){
                        validationBuilder.append((int)Math.floor(Math.random()*10));
                    }
                    String validationCode = validationBuilder.toString();
                    String validationMessage = "Please enter the following 5 digit code into the app: "+validationCode;
                    MessageApi Messenger = new MessageApi();
                    Messenger.sendMessage(result.get(),validationMessage);

                    boolean valid = false;
                    while (!valid)
                    {
                        TextInputDialog codedialog = new TextInputDialog();
                        codedialog.setTitle("Verify Number");
                        codedialog.setHeaderText("Enter 5 digit number in the text message.");
                        codedialog.setContentText("Please enter 5 digit code:");
                        Optional <String> result2 = codedialog.showAndWait();
                        if (result2.isPresent())
                        {
                            if (result2.get().equals(validationCode))
                            {
                                valid = true;
                                accountInfo.setPhoneNumber(result.get());
                                accountInfo.update();
                                new Alert(Alert.AlertType.INFORMATION, "Number Verified").showAndWait();
                            }
                            else
                            {
                                new Alert(Alert.AlertType.ERROR, "Code not recognised").showAndWait();
                            }

                        }
                    }
                }
            }else{
                TextInputDialog numberdialog = new TextInputDialog();
                numberdialog.setTitle("Login");
                numberdialog.setHeaderText("Login Code");
                numberdialog.setContentText("Please enter 6 digit number displayed on car park machine");
                Optional <String> result = numberdialog.showAndWait();
                if (result.isPresent()){
                    try
                    {
                        System.out.println(result.get());
                        //if(!result.get().contains("[0-9]+"))
                        //   throw new NumberFormatException();
                        String verifiyString = accountInfo.Verify(result.get());
                        Alert feedback1 = new Alert(Alert.AlertType.INFORMATION,"Please enter the following security code in to the car park machine: "+verifiyString);
                        feedback1.showAndWait();
                        System.out.println(verifiyString);
                    }catch (NumberFormatException e){
                        Alert feedback2 = new Alert(Alert.AlertType.ERROR,"Invalid number, 6 digits in length, displayed on car park's machine");
                        feedback2.showAndWait();
                    }
                }
            }
        });

        DayPassButton.setOnAction(event -> {
            double price = 5;
            if(accountInfo.getBalance() >= price){
                accountInfo.setBalance(accountInfo.getBalance()-price);
                updateBalance();
                accountInfo.setDayPass(Instant.now());
            }
            updateDates();
        });

        WeekPassButton.setOnAction(event -> {
            double price = 20;
            if(accountInfo.getBalance() >= price){
                accountInfo.setBalance(accountInfo.getBalance()-price);
                updateBalance();
                Calendar cal = Calendar.getInstance();
                cal.setTime(Date.from(Instant.now()));
                cal.add(Calendar.DATE,7);
                WeekPassDate.setText("");
                accountInfo.setWeekPass(cal.toInstant());
            }
            updateDates();
        });

    }

    private void updateBalance(){
        try
        {
            balanceSign.setText("Balance: £" + new DecimalFormat("###,###.00").parse(String.valueOf(accountInfo.getBalance())));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    private void updateDates(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        Date day = Date.from(accountInfo.getDayPass());
        DayPassDate.setText(formatter.format(day));
        Date week = Date.from(accountInfo.getWeekPass());
        DayPassDate.setText(formatter.format(day));
        Date season = Date.from(accountInfo.getSeasonPass());
        DayPassDate.setText(formatter.format(day));
    }
}
