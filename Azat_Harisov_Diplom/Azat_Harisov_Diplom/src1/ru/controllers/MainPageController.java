package ru.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ru.ApplicationMain;
import ru.utils.DialogUtil;

public class MainPageController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Pane topPane;
    @FXML
    private ImageView topLogo;
    @FXML
    private Label userLabel;
    @FXML
    private Pane leftNavPane;
    @FXML
    private ToggleButton conceptsButton;
    @FXML
    private ToggleButton moviesButton;
    @FXML
    private ToggleButton genresButton;
    @FXML
    private ToggleButton certificatesButton;
    @FXML
    private ToggleButton connectButton;
    @FXML
    private ToggleGroup Menu;
    @FXML
    private Button exitButton;
    private static SubScene subSceneRoot;

    public MainPageController() {
    }

    @FXML
    private void initialize() {
        this.mainPane.setOnMouseClicked((mouseEvent) -> {
            this.mainPane.requestFocus();
        });
        this.Menu.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                oldToggle.setSelected(true);
            }

        });
        Label var10000 = this.userLabel;
        String var10001 = ApplicationMain.currentUser.getName();
        var10000.setText(var10001 + ", " + ApplicationMain.currentUser.getEmail());
        subSceneRoot = new SubScene(new ReadQrController(), 1165.0D, 860.0D);
        subSceneRoot.getStyleClass().add("sub-panels");
        AnchorPane.setTopAnchor(subSceneRoot, 89.0D);
        AnchorPane.setLeftAnchor(subSceneRoot, 275.0D);
        this.mainPane.getChildren().add(subSceneRoot);
        this.initNavButtons();
    }

    private void initNavButtons() {
        this.conceptsButton.setOnAction((actionEvent) -> {
            if (this.conceptsButton.isSelected()) {
                setSubSceneRoot(new EditQrController());
            }

        });
        this.moviesButton.setOnAction((actionEvent) -> {
            if (this.moviesButton.isSelected()) {
                setSubSceneRoot(new ReadQrController());
            }

        });
        this.genresButton.setOnAction((actionEvent) -> {
            if (this.genresButton.isSelected()) {
                setSubSceneRoot(new EditElectronicSignatureController());
            }

        });
        this.exitButton.setOnAction((actionEvent) -> {
            if (DialogUtil.showConfirm("Вы точно хотите выйти?\nВы будете возвращены на страницу авторизации.")) {
                ApplicationMain.currentUser = null;

                try {
                    ApplicationMain.mainScene.setRoot((Parent)FXMLLoader.load(this.getClass().getResource("../views/LoginPage.fxml")));
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }

        });
    }

    public static void setSubSceneRoot(Parent newRoot) {
        subSceneRoot.setRoot(newRoot);
    }
}

