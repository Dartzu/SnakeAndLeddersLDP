package com.example.ldp_marcorui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A classe principal que inicia a aplicação Snake and Ladders.
 */
public class Main extends Application {

    /**
     * Inicia a aplicação.
     *
     * @param stage O palco principal da aplicação.
     * @throws IOException Se houver um erro de entrada/saída ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SnakeAndLadders-view.fxml"));
        stage.setTitle("Snake and Ladders");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Método principal que inicia a aplicação.
     *
     * @param args Os argumentos da linha de comando (não utilizados neste programa).
     */
    public static void main(String[] args) {
        // Carrega as imagens dos lados do dado
        Dice.loadImage();
        // Inicia a aplicação JavaFX
        launch(args);
    }
}