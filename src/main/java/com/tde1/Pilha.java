package com.tde1;

public class Pilha {

    private Ponto[] dados;
    private int topo;
    private int capacidade;

    public Pilha(int capacidade){
        this.capacidade = capacidade;
        this.dados = new Ponto[capacidade]; //cria um array com a capacidade total
        this.topo = -1; //iniciamos a pilha como vazia
    }

    // Adiciona um Ponto no topo da pilha
    public void empilhar(Ponto p) {
        if (!estaCheia()) { // Usando nosso método bônus, fica mais legível!
            topo++; // Se topo era -1, agora é 0. Se era 0, agora é 1...
            dados[topo] = p; // Colocamos o Ponto 'p' na posição 'topo' do array.
        } else {
            System.out.println("A pilha está cheia, não foi possível empilhar!");
        }
    }

    // Remove e retorna o Ponto do topo da pilha
    public Ponto desempilhar() {
        if (!estaVazia()) {
            Ponto pontoDoTopo = dados[topo]; // 1. Pega o item do topo.
            topo--; // 2. Move o ponteiro do topo para baixo.
            return pontoDoTopo; // 3. Retorna o item que estava no topo.
        }
        // Se a pilha estiver vazia, não há o que remover.
        return null; // Retornar null é uma forma de sinalizar que a operação falhou.
    }

    // Verifica se a pilha está vazia
    public boolean estaVazia() {
        // A pilha está vazia se o topo estiver na sua posição inicial, ou seja, -1.
        return topo == -1;
    }

    // (Bônus) Verifica se a pilha está cheia
    public boolean estaCheia() {
        // A pilha está cheia se o topo alcançou o último índice válido do array.
        //Se a capacidade é 10, os índices do array vão de 0 a 9. A pilha estará cheia quando o topo atingir o último índice possível, que é capacidade - 1.
        return topo == capacidade - 1;
    }


}
