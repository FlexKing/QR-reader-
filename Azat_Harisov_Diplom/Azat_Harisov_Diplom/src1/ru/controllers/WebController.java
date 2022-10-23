package ru.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebController extends AnchorPane {
    @FXML
    private Button backButton;
    @FXML
    private WebEngine webEngine;
    @FXML
    private WebView contentWebView;

    public WebController(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/WebPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        this.contentWebView.setMinSize(1165.0D, 861.0D);
        this.webEngine = this.contentWebView.getEngine();
        this.webEngine.load(path);
        this.initButtons();
    }

    public void initButtons() {
        this.backButton.setOnAction((actionEvent) -> {
            MainPageController.setSubSceneRoot(new ReadQrController());
        });
    }
}
