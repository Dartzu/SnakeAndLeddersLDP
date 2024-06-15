package com.example.ldp_marcorui;

/**
 * Representa uma extremidade (cabeça de cobra ou topo da escada) no jogo.
 */
public class EndTile {
    /** O tile que representa a extremidade. */
    Tile endTile;

    /**
     * Cria uma nova extremidade associada ao tile especificado.
     *
     * @param endTile O tile que representa a extremidade.
     */
    public EndTile (Tile endTile){
        this.endTile = endTile;
    }
}
