package com.example.ldp_marcorui;

import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

/**
 * Representa um jogador no jogo
 */
public class Player extends Circle{
    /** O nome do jogador. */
    private String name;
    /** A cor do jogador. */
    private ImageView color;
    /** O tile atual onde o jogador está posicionado. */
    private Tile tilePlayer;
    private boolean canAdvance;

    /**
     * Cria um novo jogador com o nome e a cor especificados.
     *
     * @param name O campo de texto que contém o nome do jogador.
     * @param color A ImageView que representa a cor do jogador.
     */
    public Player(TextField name, ImageView color){
        this.name = String.valueOf(name);
        this.color = color;
        this.canAdvance = false;
    }

    /**
     * Define o nome do jogador.
     *
     * @param name O nome do jogador.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Obtém o nome do jogador.
     *
     * @return O nome do jogador.
     */
    public String getName(){
        return name;
    }

    /**
     * Obtém a cor do jogador.
     *
     * @return A ImageView que representa a cor do jogador.
     */
    public ImageView getColor(){
        return color;
    }

    /**
     * Define o tile onde o jogador está posicionado.
     *
     * @param tilePlayer O tile onde o jogador está posicionado.
     */
    public void setTilePlayer(Tile tilePlayer){
        this.tilePlayer = tilePlayer;
    }

    /**
     * Obtém o tile onde o jogador está posicionado.
     *
     * @return O tile onde o jogador está posicionado.
     */
    public Tile getTilePlayer(){
        return tilePlayer;
    }

    public boolean getCanAdvance() {
        return canAdvance;
    }

    public void setCanAdvance(boolean canAdvance) {
        this.canAdvance = canAdvance;
    }
}

