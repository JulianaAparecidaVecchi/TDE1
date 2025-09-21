package algoritmos;

/**
 * Classe que implementa uma fila.
 */
public class Fila {

    private Ponto[] dados;

    private int inicio;

    private int fim;

    private int tamanhoAtual;

    private int capacidade;

    /**
     * Construtor da fila.
     *
     * @param capacidade Quantidade máxima de elementos que a fila pode armazenar
     */
    public Fila(int capacidade){
        this.capacidade = capacidade;
        this.dados = new Ponto[capacidade];
        this.tamanhoAtual = 0;
        this.inicio = 0;
        this.fim = -1; // Inicialmente não há nenhum item na fila
    }

    /**
     * Adiciona um ponto ao final da fila (enfileirar).
     *
     * @param p Ponto a ser adicionado
     */
    public void enfileirar(Ponto p) {
        if (!estaCheia()) {
            fim = (fim + 1) % capacidade;
            dados[fim] = p;
            tamanhoAtual++;
        } else {
            System.out.println("A fila está cheia, não foi possível enfileirar!");
        }
    }

    /**
     * Remove e retorna o ponto do início da fila (desenfileirar).
     *
     * @return O ponto removido do início da fila, ou null se a fila estiver vazia
     */
    public Ponto desenfileirar() {
        if (!estaVazia()) {
            Ponto pontoDoInicio = dados[inicio];
            inicio = (inicio + 1) % capacidade;
            tamanhoAtual--;

            return pontoDoInicio;
        }
        return null;
    }

    /**
     * Verifica se a fila está vazia.
     *
     * @return true se não houver elementos, false caso contrário
     */
    public boolean estaVazia() {
        return tamanhoAtual == 0;
    }

    /**
     * Verifica se a fila está cheia.
     *
     * @return true se a fila atingiu sua capacidade máxima, false caso contrário
     */
    public boolean estaCheia() {
        return tamanhoAtual == capacidade;
    }
}