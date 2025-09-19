package com.tde1;//o que o ponto/pixel da imagem precisa ter? quais seus atributos?
//ele precisa apenas de coordenadas, para sabermos sua localizaçõa;
//o ponto x, e o ponto y
//então ao invez de dizer que a pilha ira guardar inteiros, ela ira guardar pixels;

public class Ponto {

    int x;
    int y;

    public Ponto(int x, int y){
        this.x = x;
        this.y = y;
    }

}
