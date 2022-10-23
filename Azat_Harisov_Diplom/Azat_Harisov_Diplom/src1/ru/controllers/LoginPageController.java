package ru.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.ApplicationMain;
import ru.entity.UserEntity;
import ru.manager.UserEntityManager;
import ru.utils.CipherUtil;

public class LoginPageController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Pane leftBluePane;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView loginPicture;
    @FXML
    private Pane rightWhitePane;
    @FXML
    private Label enterLabel;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label noAccountLabel;
    @FXML
    private Button registerSuggestionButton;
    @FXML
    private Pane registerForChangePane;
    @FXML
    private Label registrationLabel;
    @FXML
    private TextField regEmailField;
    @FXML
    private TextField regNameField;
    @FXML
    private Button goNextButton;
    @FXML
    private Label haveAccountLabel;
    @FXML
    private Button loginSuggestionButton;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private PasswordField regPasswordConfirmField;
    @FXML
    private Button regBackButton;
    @FXML
    private Button registerButton;
    @FXML
    private Pane loginFieldsPane;
    @FXML
    private Pane firstRegPane;
    @FXML
    private Pane secondRegPane;
    @FXML
    private Label errorTextLog;
    @FXML
    private Label errorTextReg;

    public LoginPageController() {
    }

    @FXML
    private void initialize() {
        this.registerForChangePane.setVisible(false);
        this.secondRegPane.setVisible(false);
        this.mainPane.setOnMouseClicked((mouseEvent) -> {
            this.mainPane.requestFocus();
        });
        this.initSuggestionButtons();
        this.initFunctionalButtons();
        this.errorTextLog.setWrapText(true);
        this.errorTextReg.setWrapText(true);
    }

    private void initFunctionalButtons() {
        this.loginButton.setOnAction((actionEvent) -> {
            try {
                UserEntity potentialUser = UserEntityManager.getRowByEmail(this.emailField.getText());
                if (potentialUser != null) {
                    int iterations = Integer.parseInt(String.valueOf(potentialUser.getPassword().charAt(potentialUser.getPassword().length() - 1)));
                    byte[] enteredPassword = this.passwordField.getText().getBytes();
                    String hashedEnteredPassword = CipherUtil.hashDataSetIterations(enteredPassword, iterations);
                    if (potentialUser.getPassword().equals(hashedEnteredPassword)) {
                        ApplicationMain.currentUser = potentialUser;
                        ApplicationMain.mainScene.setRoot((Parent)FXMLLoader.load(this.getClass().getResource("../views/MainPage.fxml")));
                    } else {
                        this.errorTextLog.setText("Введенный пароль неверен");
                    }
                } else {
                    this.errorTextLog.setText("Пользователя с введенной почтой не существует");
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }

        });
        this.goNextButton.setOnAction((actionEvent) -> {
            if (this.regEmailField.getText().length() >= 1 && this.regNameField.getText().length() >= 1) {
                if (this.isValidDataFirstPage()) {
                    this.errorTextReg.setText("");
                    this.firstRegPane.setVisible(true);
                    this.secondRegPane.setVisible(true);
                    TranslateTransition toRegFirstTransition = new TranslateTransition();
                    toRegFirstTransition.setNode(this.firstRegPane);
                    toRegFirstTransition.setToX(-590.0D);
                    toRegFirstTransition.setDuration(new Duration(800.0D));
                    TranslateTransition toRegSecondTransition = new TranslateTransition();
                    toRegSecondTransition.setNode(this.secondRegPane);
                    toRegSecondTransition.setToX(-590.0D);
                    toRegSecondTransition.setDuration(new Duration(800.0D));
                    toRegFirstTransition.setOnFinished((actionEvent1) -> {
                        this.firstRegPane.setVisible(false);
                    });
                    toRegFirstTransition.play();
                    toRegSecondTransition.play();
                }
            } else {
                this.errorTextReg.setText("Поля не должны быть пустыми");
            }

        });
        this.registerButton.setOnAction((actionEvent) -> {
            if (this.regPasswordField.getText().length() < 8) {
                this.errorTextReg.setText("Пароль должен состоять не менее чем из 8 символов");
            } else if (!this.regPasswordField.getText().equals(this.regPasswordConfirmField.getText())) {
                this.errorTextReg.setText("Пароли не совпадают");
            } else if (!this.regPasswordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{8,}$")) {
                this.errorTextReg.setText("Пароль может содержать буквы английского алфавита и цифры от 0 до 9, не может содержать пробелов и спец. символов. При этом в пароле должна присутствовать как минимум одна цифра и заглавная буква");
            } else {
                try {
                    UserEntity userToRegister = new UserEntity(this.regEmailField.getText(), this.regNameField.getText(), CipherUtil.hashData(this.regPasswordField.getText().getBytes()));
                    UserEntityManager.addRow(userToRegister);
                    ApplicationMain.currentUser = userToRegister;
                    ApplicationMain.mainScene.setRoot((Parent)FXMLLoader.load(this.getClass().getResource("../views/MainPage.fxml")));
                } catch (SQLException | IOException | NoSuchAlgorithmException var3) {
                    var3.printStackTrace();
                }
            }

        });
    }

    private boolean isValidDataFirstPage() {
        StringBuilder errorLog = new StringBuilder();
        boolean troublesFound = false;
        if (!this.regEmailField.getText().matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            troublesFound = true;
            errorLog.append("Пожалуйста введите корректный email");
        }

        if (!this.regNameField.getText().matches("[a-zA-Zа-яА-Я \\-`']*")) {
            troublesFound = true;
            if (!errorLog.isEmpty()) {
                errorLog.append("\n");
            }

            errorLog.append("В имени можно использовать буквы русского и английского алфавита, пробел, дефис и апострофы");
        }

        if (troublesFound) {
            this.errorTextReg.setText(errorLog.toString());
            return false;
        } else {
            return true;
        }
    }

    private void initSuggestionButtons() {
        this.registerSuggestionButton.setOnAction((actionEvent) -> {
            this.rightWhitePane.setVisible(true);
            this.registerForChangePane.setVisible(true);
            TranslateTransition toRegFirstTransition = new TranslateTransition();
            toRegFirstTransition.setNode(this.rightWhitePane);
            toRegFirstTransition.setToY(-950.0D);
            toRegFirstTransition.setDuration(new Duration(800.0D));
            TranslateTransition toRegSecondTransition = new TranslateTransition();
            toRegSecondTransition.setNode(this.registerForChangePane);
            toRegSecondTransition.setToY(-950.0D);
            toRegSecondTransition.setDuration(new Duration(800.0D));
            toRegFirstTransition.setOnFinished((actionEvent1) -> {
                this.rightWhitePane.setVisible(false);
            });
            toRegFirstTransition.play();
            toRegSecondTransition.play();
        });
        this.loginSuggestionButton.setOnAction((actionEvent) -> {
            this.rightWhitePane.setVisible(true);
            this.registerForChangePane.setVisible(true);
            TranslateTransition toLogFirstTransition = new TranslateTransition();
            toLogFirstTransition.setNode(this.rightWhitePane);
            toLogFirstTransition.setToY(0.0D);
            toLogFirstTransition.setDuration(new Duration(800.0D));
            TranslateTransition toLogSecondTransition = new TranslateTransition();
            toLogSecondTransition.setNode(this.registerForChangePane);
            toLogSecondTransition.setToY(0.0D);
            toLogSecondTransition.setDuration(new Duration(800.0D));
            toLogSecondTransition.setOnFinished((actionEvent1) -> {
                this.registerForChangePane.setVisible(false);
            });
            toLogFirstTransition.play();
            toLogSecondTransition.play();
        });
        this.regBackButton.setOnAction((actionEvent) -> {
            this.errorTextReg.setText("");
            this.firstRegPane.setVisible(true);
            this.secondRegPane.setVisible(true);
            TranslateTransition toRegFirstTransition = new TranslateTransition();
            toRegFirstTransition.setNode(this.firstRegPane);
            toRegFirstTransition.setToX(0.0D);
            toRegFirstTransition.setDuration(new Duration(800.0D));
            TranslateTransition toRegSecondTransition = new TranslateTransition();
            toRegSecondTransition.setNode(this.secondRegPane);
            toRegSecondTransition.setToX(0.0D);
            toRegSecondTransition.setDuration(new Duration(800.0D));
            toRegFirstTransition.setOnFinished((actionEvent1) -> {
                this.secondRegPane.setVisible(false);
            });
            toRegFirstTransition.play();
            toRegSecondTransition.play();
        });
    }
}