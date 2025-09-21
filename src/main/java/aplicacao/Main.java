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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.text.Text;
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

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Título do programa
        Text tituloPrograma = new Text("Flood Fill");
        tituloPrograma.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: black;");

        // Carregar imagem para ser usada nas colunas
        Image imgCoracao = carregarImagem("/image/coracao.png");

        // Criar as colunas para mostrar as versões de BFS e DFS
        VBox colunaFila = criarColuna("Versão Fila (BFS)", imgCoracao);
        VBox colunaPilha = criarColuna("Versão Pilha (DFS)", imgCoracao);

        // Inicialmente, ambas as colunas estão visíveis
        colunaFila.setVisible(true);
        colunaPilha.setVisible(true);

        // Criação do seletor de cor
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(javafx.scene.paint.Color.BLACK);  // Cor inicial é preta

        // Botão para acionar o preenchimento
        Button btnPreencher = criarBotaoPreenchimento();

        // Layout horizontal para as colunas de BFS e DFS
        HBox imagens = new HBox(40, colunaFila, colunaPilha);
        imagens.setAlignment(Pos.CENTER);

        // Layout vertical que combina o título, o color picker, as imagens e o botão
        VBox root = new VBox(30, tituloPrograma, colorPicker, imagens, btnPreencher);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F5F5;"); // Fundo de cor cinza claro

        Scene scene = new Scene(root, 950, 700);
        stage.setTitle("Flood Fill Moderno");
        stage.setScene(scene);
        stage.show();

        // Ação para o botão de preencher
        btnPreencher.setOnAction(e -> acaoBotao(btnPreencher, colunaFila, colunaPilha, imgCoracao, colorPicker));
    }

    /**
     * Cria uma coluna com título e imagem para exibição.
     *
     * @param titulo   O título a ser exibido no topo da coluna.
     * @param imagem   A imagem que será exibida na coluna.
     * @return VBox estilizado contendo o título e a imagem.
     */
    private VBox criarColuna(String titulo, Image imagem) {
        Text lblTitulo = new Text(titulo);
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: black;");

        // Visualização da imagem
        ImageView imgView = new ImageView(imagem);
        imgView.setFitWidth(300);
        imgView.setPreserveRatio(true);

        VBox coluna = new VBox(15);
        coluna.setAlignment(Pos.CENTER);
        coluna.setPadding(new Insets(20));
        coluna.getChildren().addAll(lblTitulo, imgView);

        // Estilo da coluna
        coluna.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10,0,0,4);");
        return coluna;
    }

    /**
     * Cria o botão estilizado que dispara a ação de preenchimento.
     *
     * @return O botão estilizado.
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
     * Permite que o usuário escolha entre os algoritmos BFS, DFS ou ambos,
     * e então preenche a imagem escolhida com a cor selecionada no ColorPicker.
     *
     * @param btn           O botão clicado.
     * @param colunaFila    Coluna para a versão BFS.
     * @param colunaPilha   Coluna para a versão DFS.
     * @param imgCoracao    A imagem base a ser preenchida.
     * @param colorPicker   O seletor de cor.
     */
    private void acaoBotao(Button btn, VBox colunaFila, VBox colunaPilha, Image imgCoracao, ColorPicker colorPicker) {
        // Opções do algoritmo para o usuário escolher
        List<String> opcoes = Arrays.asList("Fila (BFS)", "Pilha (DFS)", "Ambos");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Fila (BFS)", opcoes);
        dialog.setTitle("Escolha o algoritmo");
        dialog.setHeaderText("Selecione o algoritmo de preenchimento");
        dialog.setContentText("Algoritmo:");

        // Estilizando o diálogo
        dialog.getDialogPane().setStyle("-fx-font-size: 16px; -fx-background-color: #FFFFFF; " +
                "-fx-border-color: #E91E63; -fx-border-width: 2px; -fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;");

        // Personalizando botões OK e Cancelar
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #BDBDBD; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        // Escolha do algoritmo
        Optional<String> escolhaOpt = dialog.showAndWait();
        if (escolhaOpt.isPresent()) {
            String escolha = escolhaOpt.get();

            // Executar tarefa assíncrona para preencher a imagem
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

                        // Obtendo a cor selecionada no ColorPicker
                        Color corEscolhida = new Color((float) colorPicker.getValue().getRed(),
                                (float) colorPicker.getValue().getGreen(),
                                (float) colorPicker.getValue().getBlue());

                        // Ação de preenchimento com base na escolha do algoritmo
                        switch (escolha) {
                            case "Fila (BFS)":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(true);
                                    colunaPilha.setVisible(false);
                                });
                                floodFill.preencherComFilaAnimado(imgBuffFila,
                                        new Ponto(imgBuffFila.getWidth() / 2, imgBuffFila.getHeight() / 2),
                                        corEscolhida, (ImageView) colunaFila.getChildren().get(1));
                                ImageIO.write(imgBuffFila, "png", new File(outputDir, "coracao_preenchido_fila.png"));
                                break;

                            case "Pilha (DFS)":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(false);
                                    colunaPilha.setVisible(true);
                                });
                                floodFill.preencherComPilhaAnimado(imgBuffPilha,
                                        new Ponto(imgBuffPilha.getWidth() / 2, imgBuffPilha.getHeight() / 2),
                                        corEscolhida, (ImageView) colunaPilha.getChildren().get(1));
                                ImageIO.write(imgBuffPilha, "png", new File(outputDir, "coracao_preenchido_pilha.png"));
                                break;

                            case "Ambos":
                                Platform.runLater(() -> {
                                    colunaFila.setVisible(true);
                                    colunaPilha.setVisible(true);
                                });

                                // Threads para rodar BFS e DFS simultaneamente
                                Thread threadFila = new Thread(() -> {
                                    try {
                                        floodFill.preencherComFilaAnimado(imgBuffFila,
                                                new Ponto(imgBuffFila.getWidth() / 2, imgBuffFila.getHeight() / 2),
                                                corEscolhida, (ImageView) colunaFila.getChildren().get(1));
                                        ImageIO.write(imgBuffFila, "png", new File(outputDir, "coracao_preenchido_fila.png"));
                                    } catch (InterruptedException | IOException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                });

                                Thread threadPilha = new Thread(() -> {
                                    try {
                                        floodFill.preencherComPilhaAnimado(imgBuffPilha,
                                                new Ponto(imgBuffPilha.getWidth() / 2, imgBuffPilha.getHeight() / 2),
                                                corEscolhida, (ImageView) colunaPilha.getChildren().get(1));
                                        ImageIO.write(imgBuffPilha, "png", new File(outputDir, "coracao_preenchido_pilha.png"));
                                    } catch (InterruptedException | IOException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                });

                                threadFila.start();
                                threadPilha.start();
                                break;
                        }
                    }
                    return null;
                }
            };

            // Iniciar a tarefa em uma nova thread
            new Thread(task).start();
        }
    }

    /**
     * Carrega uma imagem a partir do arquivo de recursos.
     *
     * @param caminho Caminho para a imagem a ser carregada.
     * @return A imagem carregada.
     */
    private Image carregarImagem(String caminho) {
        URL url = getClass().getResource(caminho);
        if (url == null) {
            throw new RuntimeException("Imagem não encontrada no caminho: " + caminho);
        }
        return new Image(url.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
