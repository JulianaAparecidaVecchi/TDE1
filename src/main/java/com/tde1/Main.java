package com.tde1;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label tituloPrograma = new Label("Preenchimento com Flood Fill");

        // Coluna da Fila
        Label tituloFila = new Label("Versão com Fila");
        ImageView imgFila = new ImageView("file:src/main/java/com/tde1/img/coracao.png");
        imgFila.setFitWidth(350);
        imgFila.setPreserveRatio(true);
        VBox colunaFila = new VBox(10, tituloFila, imgFila);
        colunaFila.setAlignment(Pos.CENTER);

        // Coluna da Pilha
        Label tituloPilha = new Label("Versão com Pilha");
        ImageView imgPilha = new ImageView("file:src/main/java/com/tde1/img/coracao.png");
        imgPilha.setFitWidth(350);
        imgPilha.setPreserveRatio(true);
        VBox colunaPilha = new VBox(10, tituloPilha, imgPilha);
        colunaPilha.setAlignment(Pos.CENTER);

        // Botão para preencher
        Button btnPreencher = new Button("Preencher Imagem");
        btnPreencher.setOnAction(e -> {

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    BufferedImage imgFilaBuff = ImageIO.read(new File("src/main/java/com/tde1/img/coracao.png"));
                    BufferedImage imgPilhaBuff = ImageIO.read(new File("src/main/java/com/tde1/img/coracao.png"));

                    AlgoritmoPreenchimento floodFill = new AlgoritmoPreenchimento();

                    // Primeiro fila
                    floodFill.preencherComFilaAnimado(imgFilaBuff, new Ponto(imgFilaBuff.getWidth()/2, imgFilaBuff.getHeight()/2), Color.BLUE, imgFila);

                    // Depois pilha
                    floodFill.preencherComPilhaAnimado(imgPilhaBuff, new Ponto(imgPilhaBuff.getWidth()/2, imgPilhaBuff.getHeight()/2), Color.RED, imgPilha);

                    return null;
                }
            };

            new Thread(task).start(); // Roda em background
        });

        // Layout horizontal para as duas versões
        HBox imagens = new HBox(40, colunaFila, colunaPilha);
        imagens.setAlignment(Pos.CENTER);

        // Layout principal
        VBox root = new VBox(20, tituloPrograma, imagens, btnPreencher);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Flood Fill");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
