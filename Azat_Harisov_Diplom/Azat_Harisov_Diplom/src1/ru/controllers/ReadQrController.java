package ru.controllers;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import ru.ApplicationMain;

public class ReadQrController extends AnchorPane {
    private Desktop desktop = Desktop.getDesktop();
    @FXML
    private Button nextButton;
    @FXML
    private Button selectButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label label;

    public ReadQrController() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/ReadQr.fxml"));
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
        String[] pathToFile = new String[1];
        this.selectButton.setOnAction((actionEvent) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(ApplicationMain.mainStage);
            if (file != null) {
                Path path = Path.of(file.getPath());
                pathToFile[0] = path.toString();
                Image image = new Image(file.toURI().toString());
                this.imageView.setImage(image);
            }

        });
        this.nextButton.setOnAction((actionEvent) -> {
            HashMap hashMap = new HashMap();

            try {
                MainPageController.setSubSceneRoot(new WebController(readQRCode(pathToFile[0], "UTF-8,", hashMap)));
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (NotFoundException var5) {
                var5.printStackTrace();
            }

        });
    }

    public static String readQRCode(String filePath, String charset, Map hintMap) throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = (new MultiFormatReader()).decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }
}
