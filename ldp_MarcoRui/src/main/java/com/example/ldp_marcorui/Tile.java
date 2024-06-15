package com.example.ldp_marcorui;

/**
 * Representa um único tile no jogo
 */
public class Tile {
    /**
     * O ID do tile.
     * A coordenada X do tile.
     * A coordenada Y do tile.
     */
    private int id, x, y;
    /** A escada associada a este tile. */
    private EndTile snake = null;
    /** A cobra associada a este tile. */
    private EndTile ladder = null;

    /**
     * Cria um novo tile com o ID e as coordenadas especificadas.
     *
     * @param id O ID do tile.
     * @param x A coordenada X do tile.
     * @param y A coordenada Y do tile.
     */
    public Tile(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Define a cobra associada a este tile.
     *
     * @param snake A cobra associada a este tile.
     */
    public void setSnake(EndTile snake){
        this.snake = snake;
    }
    /**
     * Obtém a cobra associada a este tile.
     *
     * @return A cobra associada a este tile, ou null se não houver nenhuma.
     */
    public EndTile getSnake(){
        return snake;
    }

    /**
     * Define a escada associada a este tile.
     *
     * @param ladder A escada associada a este tile.
     */
    public void setLadder(EndTile ladder){
        this.ladder = ladder;
    }
    /**
     * Obtém a escada associada a este tile.
     *
     * @return A escada associada a este tile, ou null se não houver nenhuma.
     */
    public EndTile getLadder(){
        return ladder;
    }

    /**
     * Obtém o ID do tile.
     *
     * @return O ID do tile.
     */
    public int getId(){
        return id;
    }
    /**
     * Obtém a coordenada X do tile.
     *
     * @return A coordenada X do tile.
     */
    public int getX(){
        return x;
    }
    /**
     * Obtém a coordenada Y do tile.
     *
     * @return A coordenada Y do tile.
     */
    public int getY(){
        return y;
    }

    /**
     * Retorna uma representação em formato de string do tile, de forma a listar o ID, coordenada X e coordenada Y.
     *
     * @return Uma representação em formato de string do tile.
     */
    public String toString(){
        return "Tile { id: " + id + ", x: " + x + ", y: " + y + " }";
    }

}
