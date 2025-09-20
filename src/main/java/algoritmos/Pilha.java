package algoritmos;

/**
 * Classe que implementa uma pilha.
 */
public class Pilha {

    private Ponto[] dados;

    private int topo;

    private int capacidade;

    /**
     * Construtor da pilha.
     *
     * @param capacidade Quantidade máxima de elementos que a pilha pode armazenar
     */
    public Pilha(int capacidade){
        this.capacidade = capacidade;
        this.dados = new Ponto[capacidade];
        this.topo = -1; // Pilha inicia vazia
    }

    /**
     * Adiciona um ponto no topo da pilha (empilhar).
     *
     * @param p Ponto a ser adicionado
     */
    public void empilhar(Ponto p) {
        if (!estaCheia()) {
            topo++;
            dados[topo] = p;
        } else {
            System.out.println("A pilha está cheia, não foi possível empilhar!");
        }
    }

    /**
     * Remove e retorna o ponto do topo da pilha (desempilhar).
     *
     * @return O ponto removido do topo, ou null se a pilha estiver vazia
     */
    public Ponto desempilhar() {
        if (!estaVazia()) {
            Ponto pontoDoTopo = dados[topo];
            topo--;
            return pontoDoTopo;
        }
        return null;
    }

    /**
     * Verifica se a pilha está vazia.
     *
     * @return true se não houver elementos, false caso contrário
     */
    public boolean estaVazia() {
        return topo == -1;
    }

    /**
     * Verifica se a pilha está cheia.
     *
     * @return true se a pilha atingiu sua capacidade máxima, false caso contrário
     */
    public boolean estaCheia() {
        return topo == capacidade - 1;
    }
}
