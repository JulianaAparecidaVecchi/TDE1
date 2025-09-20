package aplicacao;

import algoritmos.AlgoritmoPreenchimento;
import algoritmos.Ponto;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonType;


public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Label tituloPrograma = new Label("Flood Fill");
        tituloPrograma.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        Image imgCoracao = carregarImagem("/image/coracao.png");

        VBox colunaFila = criarColuna("Versão Fila (BFS)", imgCoracao);

        VBox colunaPilha = criarColuna("Versão Pilha (DFS)", imgCoracao);

        colunaFila.setVisible(true);
        colunaPilha.setVisible(true);

        Button btnPreencher = criarBotaoPreenchimento();

        HBox imagens = new HBox(40, colunaFila, colunaPilha);
        imagens.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, tituloPrograma, imagens, btnPreencher);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F5F5;"); // Fundo cinza claro elegante

        Scene scene = new Scene(root, 950, 700);
        stage.setTitle("Flood Fill Moderno");
        stage.setScene(scene);
        stage.show();

        btnPreencher.setOnAction(e -> acaoBotao(btnPreencher, colunaFila, colunaPilha, imgCoracao));
    }

    /**
     * Cria uma coluna de imagem estilizada com título.
     *
     * @param titulo   Título da coluna.
     * @param imagem   Imagem a ser exibida.
     * @return VBox estilizado contendo o título e a imagem.
     */
    private VBox criarColuna(String titulo, Image imagem) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");

        ImageView imgView = new ImageView(imagem);
        imgView.setFitWidth(300);
        imgView.setPreserveRatio(true);

        VBox coluna = new VBox(15, lblTitulo, imgView);
        coluna.setAlignment(Pos.CENTER);
        coluna.setPadding(new Insets(20));
        coluna.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10,0,0,4);");
        return coluna;
    }

    /**
     * Cria um botão de preenchimento estilizado em rosa.
     *
     * @return Botão estilizado.
     */
    private Button criarBotaoPreenchimento() {
        Button btn = new Button("Preencher Imagem");
        btn.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #EC407A; -fx-text-fill: white; " +
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; " +
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10px;"));
        return btn;
    }

    /**
     * Ação executada quando o botão de preenchimento é pressionado.
     *
     * @param btn           Botão clicado.
     * @param colunaFila    Coluna da BFS.
     * @param colunaPilha   Coluna da DFS.
     * @param imgCoracao    Imagem base.
     */
    private void acaoBotao(Button btn, VBox colunaFila, VBox colunaPilha, Image imgCoracao) {
        List<String> opcoes = Arrays.asList("Fila (BFS)", "Pilha (DFS)", "Ambos");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Fila (BFS)", opcoes);
        dialog.setTitle("Escolha o algoritmo");
        dialog.setHeaderText("Selecione o algoritmo de preenchimento");
        dialog.setContentText("Algoritmo:");

        dialog.getDialogPane().setStyle("-fx-font-size: 16px; -fx-background-color: #FFFFFF; " +
                "-fx-border-color: #E91E63; -fx-border-width: 2px; -fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #BDBDBD; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        Optional<String> escolhaOpt = dialog.showAndWait();
        if (escolhaOpt.isPresent()) {
            String escolha = escolhaOpt.get();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try (var stream1 = getClass().getResourceAsStream("/image/coracao.png");
                         var stream2 = getClass().getResourceAsStream("/image/coracao.png")) {

                        if (stream1 == null || stream2 == null)
                            throw new RuntimeException("Imagem não encontrada no classpath: /image/coracao.png");

                        BufferedImage imgBuffFila = ImageIO.read(stream1);
                        BufferedImage imgBuffPilha = ImageIO.read(stream2);

                        AlgoritmoPreenchimento floodFill = new AlgoritmoPreenchimento();

                        File outputDir = new File("src/output");
                        if (!outputDir.exists()) outputDir.mkdirs();

                        switch (escolha) {
                            case "Fila (BFS)":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(true);
                                    colunaPilha.setVisible(false);
                                });
                                floodFill.preencherComFilaAnimado(imgBuffFila,
                                        new Ponto(imgBuffFila.getWidth() / 2, imgBuffFila.getHeight() / 2),
                                        Color.BLUE, (ImageView) colunaFila.getChildren().get(1));
                                ImageIO.write(imgBuffFila, "png", new File(outputDir, "coracao_preenchido_fila.png"));
                                break;

                            case "Pilha (DFS)":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(false);
                                    colunaPilha.setVisible(true);
                                });
                                floodFill.preencherComPilhaAnimado(imgBuffPilha,
                                        new Ponto(imgBuffPilha.getWidth() / 2, imgBuffPilha.getHeight() / 2),
                                        Color.RED, (ImageView) colunaPilha.getChildren().get(1));
                                ImageIO.write(imgBuffPilha, "png", new File(outputDir, "coracao_preenchido_pilha.png"));
                                break;

                            case "Ambos":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(true);
                                    colunaPilha.setVisible(true);
                                });

                                Thread threadFila = new Thread(() -> {
                                    try {
                                        floodFill.preencherComFilaAnimado(imgBuffFila,
                                                new Ponto(imgBuffFila.getWidth() / 2, imgBuffFila.getHeight() / 2),
                                                Color.BLUE, (ImageView) colunaFila.getChildren().get(1));
                                        ImageIO.write(imgBuffFila, "png", new File(outputDir, "coracao_preenchido_fila.png"));
                                    } catch (InterruptedException | IOException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                });

                                Thread threadPilha = new Thread(() -> {
                                    try {
                                        floodFill.preencherComPilhaAnimado(imgBuffPilha,
                                                new Ponto(imgBuffPilha.getWidth() / 2, imgBuffPilha.getHeight() / 2),
                                                Color.RED, (ImageView) colunaPilha.getChildren().get(1));
                                        ImageIO.write(imgBuffPilha, "png", new File(outputDir, "coracao_preenchido_pilha.png"));
                                    } catch (InterruptedException | IOException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                });

                                threadFila.start();
                                threadPilha.start();
                                threadFila.join();
                                threadPilha.join();
                                break;
                        }
                    }
                    return null;
                }
            };

            new Thread(task).start();
        }
    }

    /**
     * Carrega uma imagem do classpath.
     *
     * @param caminho Caminho da imagem dentro do resources.
     * @return Imagem carregada.
     */
    private Image carregarImagem(String caminho) {
        URL url = getClass().getResource(caminho);
        if (url == null)
            throw new RuntimeException("Imagem não encontrada no classpath: " + caminho);
        return new Image(url.toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
