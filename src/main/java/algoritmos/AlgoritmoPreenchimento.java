package algoritmos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlgoritmoPreenchimento {

    private static final int PIXELS_POR_FRAME = 1000;

    /**
     * Preenche a imagem usando uma pilha (DFS) de forma animada.
     * Mostra no terminal as coordenadas de cada pixel pintado.
     *
     * @param imagem Imagem a ser preenchida
     * @param pontoInicial Coordenada inicial do preenchimento
     * @param novaCor Cor que será usada no preenchimento
     * @param imgView ImageView da interface para atualizar a animação
     * @throws InterruptedException
     * @throws IOException
     */
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
                    // Pinta o pixel
                    imagem.setRGB(p.x, p.y, novaCor.getRGB());
                    contador++;

                    System.out.println("DFS - Pintando pixel: (" + p.x + ", " + p.y + ")");

                    pilha.empilhar(new Ponto(p.x + 1, p.y));
                    pilha.empilhar(new Ponto(p.x - 1, p.y));
                    pilha.empilhar(new Ponto(p.x, p.y + 1));
                    pilha.empilhar(new Ponto(p.x, p.y - 1));

                    if (contador % PIXELS_POR_FRAME == 0) {
                        salvarEAtualizar(imgView, imagem, "output/pilha_frame_" + frame + ".png");
                        frame++;
                        Thread.sleep(20);
                    }
                }
            }
        }

        salvarEAtualizar(imgView, imagem, "output/heart_filled_stack.png");
    }

    /**
     * Preenche a imagem usando uma fila (BFS) de forma animada.
     * Mostra no terminal as coordenadas de cada pixel pintado.
     *
     * @param imagem Imagem a ser preenchida
     * @param pontoInicial Coordenada inicial do preenchimento
     * @param novaCor Cor que será usada no preenchimento
     * @param imgView ImageView da interface para atualizar a animação
     * @throws InterruptedException
     * @throws IOException
     */
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
                    imagem.setRGB(p.x, p.y, novaCor.getRGB());
                    contador++;

                    System.out.println("BFS - Pintando pixel: (" + p.x + ", " + p.y + ")");

                    fila.enfileirar(new Ponto(p.x + 1, p.y));
                    fila.enfileirar(new Ponto(p.x - 1, p.y));
                    fila.enfileirar(new Ponto(p.x, p.y + 1));
                    fila.enfileirar(new Ponto(p.x, p.y - 1));

                    if (contador % PIXELS_POR_FRAME == 0) {
                        salvarEAtualizar(imgView, imagem, "output/fila_frame_" + frame + ".png");
                        frame++;
                        Thread.sleep(20);
                    }
                }
            }
        }

        salvarEAtualizar(imgView, imagem, "output/heart_filled_queue.png");
    }

    /**
     * Método auxiliar para salvar a imagem em disco e atualizar a ImageView na UI.
     *
     * @param imgView ImageView a ser atualizada
     * @param imagem Imagem que será salva
     * @param caminhoArquivo Caminho completo do arquivo PNG
     * @throws IOException
     */
    private void salvarEAtualizar(ImageView imgView, BufferedImage imagem, String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        arquivo.getParentFile().mkdirs();
        ImageIO.write(imagem, "png", arquivo);

        Platform.runLater(() -> imgView.setImage(new Image("file:" + arquivo.getAbsolutePath())));
    }
}