package ru.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.utils.DialogUtil;

public class EditElectronicSignatureController extends AnchorPane {
    @FXML
    private Button createButton;
    @FXML
    private TextField textField;
    @FXML
    private Label label;

    public EditElectronicSignatureController() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/EditElectronicSignature.fxml"));
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
            KeyPairGenerator keyPairGen = null;

            try {
                keyPairGen = KeyPairGenerator.getInstance("DSA");
            } catch (NoSuchAlgorithmException var15) {
                var15.printStackTrace();
            }

            assert keyPairGen != null;

            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            PrivateKey privKey = pair.getPrivate();
            Signature sign = null;

            try {
                sign = Signature.getInstance("SHA256withDSA");
            } catch (NoSuchAlgorithmException var14) {
                var14.printStackTrace();
            }

            try {
                assert sign != null;

                sign.initSign(privKey);
            } catch (InvalidKeyException var13) {
                var13.printStackTrace();
            }

            byte[] bytes = msg.getBytes();

            try {
                sign.update(bytes);
            } catch (SignatureException var12) {
                var12.printStackTrace();
            }

            byte[] signature = new byte[0];

            try {
                signature = sign.sign();
            } catch (SignatureException var11) {
                var11.printStackTrace();
            }

            try {
                String var10000 = new String(signature, "UTF8");
                DialogUtil.showInfo("Ваша электронная подпись: " + var10000 + " \n Сохранена");
            } catch (UnsupportedEncodingException var10) {
                var10.printStackTrace();
            }

        });
    }
}
