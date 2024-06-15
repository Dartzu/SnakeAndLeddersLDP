package com.example.ldp_marcorui;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * Representa um dado usado no jogo.
 */
public class Dice {

    /** Lista de imagens dos lados do dado. */
    private static ArrayList<Image> diceImages = new ArrayList<Image>();

    /**
     * Simula o lançamento de um dado e retorna o valor.
     *
     * @return Um número inteiro que representa o resultado do lançamento do dado (entre 1 e 6).
     */
    public static int roll(){
        return (int)(Math.random() * 6 + 1);
    }

    /**
     * Carrega as imagens dos lados do dado.
     */
    public static void loadImage(){
        diceImages.add(new Image("/img/dice1.png"));
        for (int i = 1; i <= 6; i++){
            diceImages.add(new Image("/img/dice" + i +".png"));
        }
    }

    /**
     * Retorna a lista de imagens dos lados do dado.
     *
     * @return A lista de imagens dos lados do dado.
     */
    public static ArrayList<Image> getSides(){
        return diceImages;
    }
}
