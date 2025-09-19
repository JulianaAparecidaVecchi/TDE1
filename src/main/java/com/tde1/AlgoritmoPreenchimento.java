package com.tde1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlgoritmoPreenchimento {

    private static final int PIXELS_POR_FRAME = 1000; // Atualiza a cada 1000 pixels

    // Método para preenchimento com pilha (DFS) animado
    public void preencherComPilhaAnimado(BufferedImage imagem, Ponto pontoInicial, Color novaCor, ImageView imgView) throws InterruptedException, IOException {
        int corOriginal = imagem.getRGB(pontoInicial.x, pontoInicial.y);
        if (corOriginal == novaCor.getRGB()) return;

        int capacidade = imagem.getWidth() * imagem.getHeight();
        Pilha pilha = new Pilha(capacidade);
        pilha.empilhar(pontoInicial);

        int contador = 0;
        int frame = 0;

        while (!pilha.estaVazia()) {
            Ponto p = pilha.desempilhar();
            if (p.x >= 0 && p.x < imagem.getWidth() && p.y >= 0 && p.y < imagem.getHeight()) {
                if (imagem.getRGB(p.x, p.y) == corOriginal) {
                    // a. Pinte o pixel
                    imagem.setRGB(p.x, p.y, novaCor.getRGB());
                    contador++;

                    // b. Adicione os 4 vizinhos na pilha
                    pilha.empilhar(new Ponto(p.x + 1, p.y)); // Direita
                    pilha.empilhar(new Ponto(p.x - 1, p.y)); // Esquerda
                    pilha.empilhar(new Ponto(p.x, p.y + 1)); // Baixo
                    pilha.empilhar(new Ponto(p.x, p.y - 1)); // Cima

                    // c. Atualiza frame a cada PIXELS_POR_FRAME
                    if (contador % PIXELS_POR_FRAME == 0) {
                        salvarEAtualizar(imgView, imagem, "src/main/java/com/tde1/img/pilha_frame_" + frame + ".png");
                        frame++;
                        Thread.sleep(20);
                    }
                }
            }
        }

        // Salva a imagem final
        salvarEAtualizar(imgView, imagem, "src/main/java/com/tde1/img/coracao_preenchido_pilha.png");
    }

    // Método para preenchimento com fila (BFS) animado
    public void preencherComFilaAnimado(BufferedImage imagem, Ponto pontoInicial, Color novaCor, ImageView imgView) throws InterruptedException, IOException {
        int corOriginal = imagem.getRGB(pontoInicial.x, pontoInicial.y);
        if (corOriginal == novaCor.getRGB()) return;

        int capacidade = imagem.getWidth() * imagem.getHeight();
        Fila fila = new Fila(capacidade);
        fila.enfileirar(pontoInicial);

        int contador = 0;
        int frame = 0;

        while (!fila.estaVazia()) {
            Ponto p = fila.desenfileirar();
            if (p != null && p.x >= 0 && p.x < imagem.getWidth() && p.y >= 0 && p.y < imagem.getHeight()) {
                if (imagem.getRGB(p.x, p.y) == corOriginal) {
                    // a. Pinte o pixel
                    imagem.setRGB(p.x, p.y, novaCor.getRGB());
                    contador++;

                    // b. Adicione os 4 vizinhos na fila
                    fila.enfileirar(new Ponto(p.x + 1, p.y)); // Direita
                    fila.enfileirar(new Ponto(p.x - 1, p.y)); // Esquerda
                    fila.enfileirar(new Ponto(p.x, p.y + 1)); // Baixo
                    fila.enfileirar(new Ponto(p.x, p.y - 1)); // Cima

                    // c. Atualiza frame a cada PIXELS_POR_FRAME
                    if (contador % PIXELS_POR_FRAME == 0) {
                        salvarEAtualizar(imgView, imagem, "src/main/java/com/tde1/img/fila_frame_" + frame + ".png");
                        frame++;
                        Thread.sleep(20);
                    }
                }
            }
        }

        // Salva a imagem final
        salvarEAtualizar(imgView, imagem, "src/main/java/com/tde1/img/coracao_preenchido_fila.png");
    }

    // Método auxiliar para salvar e atualizar ImageView
    private void salvarEAtualizar(ImageView imgView, BufferedImage imagem, String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        ImageIO.write(imagem, "png", arquivo);
        Platform.runLater(() -> imgView.setImage(new Image("file:" + caminhoArquivo)));
    }
}
