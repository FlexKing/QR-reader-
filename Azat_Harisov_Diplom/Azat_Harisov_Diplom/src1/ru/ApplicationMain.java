package ru;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.entity.UserEntity;

public class ApplicationMain extends Application {
    public static Stage mainStage;
    public static Scene mainScene;
    public static UserEntity currentUser;

    public ApplicationMain() {
    }

    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("views/LoginPage.fxml"));
        mainStage.setResizable(false);
        mainStage.setTitle("AzatQR");
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(this.getClass().getResource("styles/Style.css").toExternalForm());
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/azat?setServerTimeZone=Europe/Moscow&characterEncoding=utf8", "root", "12345678");
    }

    public static void main(String[] args) {
        launch(args);
    }
}