package ru.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import ru.ApplicationMain;

public class DialogUtil {
    private static final Alert alert;
    private static boolean ownerInitFlag;

    public DialogUtil() {
    }

    public static void showError(String message) {
        checkInit();
        alert.setAlertType(AlertType.ERROR);
        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(okButton);
        alert.setTitle("Ошибка!");
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirm(String message) {
        checkInit();
        alert.setAlertType(AlertType.CONFIRMATION);
        ButtonType okButton = new ButtonType("Да", ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Нет", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(new ButtonType[]{okButton, cancelButton});
        alert.setTitle("Подтверждение");
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        return optionalButtonType.isPresent() && optionalButtonType.get() == okButton;
    }

    public static ButtonType showConceptConfirm(String message) {
        checkInit();
        alert.setAlertType(AlertType.CONFIRMATION);
        ButtonType yesButton = new ButtonType("Да", ButtonData.YES);
        ButtonType noButton = new ButtonType("Нет", ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(new ButtonType[]{yesButton, noButton, cancelButton});
        alert.setTitle("Подтверждение");
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        return (ButtonType)optionalButtonType.orElse((ButtonType) null);
    }

    public static void showInfo(String message) {
        checkInit();
        alert.setAlertType(AlertType.INFORMATION);
        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(okButton);
        alert.setTitle("Сообщение");
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void checkInit() {
        if (!ownerInitFlag) {
            alert.initOwner(ApplicationMain.mainStage);
            ownerInitFlag = true;
        }

    }

    static {
        alert = new Alert(AlertType.NONE);
        ownerInitFlag = false;
    }
}

