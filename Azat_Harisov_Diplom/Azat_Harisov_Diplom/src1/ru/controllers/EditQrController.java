package ru.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ru.ApplicationMain;

public class EditQrController extends AnchorPane {
    @FXML
    private Button createButton;
    @FXML
    private TextField textField;
    @FXML
    private Label label;
    @FXML
    private ImageView imageView;
    Image image;

    public EditQrController() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/EditQr.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        this.initButtons();
    }

    private void initButtons() {
        this.createButton.setOnAction((actionEvent) -> {
            String msg = this.textField.getText();
            String path = "demo.png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap();
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            try {
                this.createQR(msg, path, charset, hashMap, 200, 200);
            } catch (IOException | WriterException var7) {
                var7.printStackTrace();
            }

        });
    }

    public void createQR(String data, String path, String charset, Map hashMap, int height, int width) throws WriterException, IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(ApplicationMain.mainStage);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        BitMatrix matrix = (new MultiFormatWriter()).encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf(46) + 1), file);
    }
}
