package com.tde1;

public class Fila {

    private Ponto[] dados;
    private int inicio;
    private int fim;
    private int tamanhoAtual;
    private int capacidade;

    public Fila(int capacidade){
        this.capacidade = capacidade;
        this.dados = new Ponto[capacidade];
        this.tamanhoAtual = 0;
        this.inicio = 0;
        this.fim = -1; //aponta p ultimo item inserido
    }

    // Adiciona um Ponto no fim da fila
    public void enfileirar(Ponto p) {
        if (!estaCheia()) {
            // 1. Calcula a nova posição do fim.
            fim = (fim + 1) % capacidade;
            // 2. Coloca o ponto 'p' nessa nova posição.
            dados[fim] = p;
            // 3. Aumenta o contador de itens na fila.
            tamanhoAtual++;
        } else {
            System.out.println("A fila está cheia, não foi possível enfileirar!");
        }
    }

    // Remove e retorna o Ponto do início da fila
    public Ponto desenfileirar() {

        if (!estaVazia()) {
            // 1. Pega o item do início da fila.
            Ponto pontoDoInicio = dados[inicio];
            // 2. Move o ponteiro de início para o próximo.
            inicio = (inicio + 1) % capacidade;
            // 3. Diminui o contador de itens.
            tamanhoAtual--;
            // 4. Retorna o item que estava no início.
            return pontoDoInicio;
        }
        // Se a fila estiver vazia, retorna null.
        return null;

    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return tamanhoAtual == 0;
    }

    // Verifica se a fila está cheia
    public boolean estaCheia() {
        return tamanhoAtual == capacidade;
    }
}
