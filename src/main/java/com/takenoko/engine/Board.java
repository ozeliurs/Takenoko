package com.takenoko.engine;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.Weather;
import java.util.*;

/** Board class. The board contains the tiles. */
public class Board {
    private final TileLayer tileLayer;
    private final BambooLayer bambooLayer;
    private final Panda panda;
    private final Gardener gardener;
    private final GameAssets gameAssets;
    private Weather weather = null;

    /**
     * Constructor for the Board class.
     *
     * @param tileLayer the tile layer
     * @param bambooLayer the bamboo layer
     * @param panda the panda
     * @param gardener the gardener
     * @param gameAssets the game assets : dice
     */
    public Board(
            TileLayer tileLayer,
            BambooLayer bambooLayer,
            Panda panda,
            Gardener gardener,
            GameAssets gameAssets) {
        this.tileLayer = tileLayer;
        this.bambooLayer = bambooLayer;
        this.panda = panda;
        this.gardener = gardener;
        this.gameAssets = gameAssets;
    }

    public void rollWeather() {
        gameAssets.getWeatherDice().rollWeather();
    }

    public Weather peekWeather() {
        return gameAssets.getWeatherDice().peekWeather();
    }

    /** Constructor for the Board class. */
    public Board() {
        this(new TileLayer(), new BambooLayer(), new Panda(), new Gardener(), new GameAssets());
    }

    public Board(Board board) {
        this.tileLayer = board.tileLayer.copy();
        this.bambooLayer = board.bambooLayer.copy();
        this.panda = board.panda.copy();
        this.gardener = board.gardener.copy();
        this.gameAssets = board.gameAssets.copy();
        this.weather = board.weather;
    }

    /**
     * Return the list of available tiles.
     *
     * @return the list of available tiles
     */
    public List<Tile> getAvailableTiles() {
        return tileLayer.getAvailableTiles();
    }

    /**
     * Returns a copy of the hashmap of tiles.
     *
     * @return the map of tiles
     */
    public Map<PositionVector, Tile> getTiles() {
        return new HashMap<>(tileLayer.getTiles());
    }

    /**
     * Returns the available tile positions.
     *
     * @return the available tile positions
     */
    public List<PositionVector> getAvailableTilePositions() {
        return tileLayer.getAvailableTilePositions();
    }

    /**
     * Returns a copy of the hashmap of tiles without the pond.
     *
     * @return the map of tiles without the pond
     */
    public Map<PositionVector, Tile> getTilesWithoutPond() {
        return new HashMap<>(tileLayer.getTilesWithoutPond());
    }

    /**
     * Returns if the position is a tile.
     *
     * @param positionVector the position of the tile
     * @return if there is a tile at the position
     */
    public boolean isTile(PositionVector positionVector) {
        return tileLayer.isTile(positionVector);
    }

    /**
     * Returns the tile at the position.
     *
     * @param positionVector the position of the tile
     * @return the tile at the position
     */
    public Tile getTileAt(PositionVector positionVector) {
        return tileLayer.getTileAt(positionVector);
    }

    /**
     * Place a tile on the board. and update the available tiles and the available tile positions.
     *
     * @param tile the tile to add to the board
     * @param position the position of the tile
     * @return the LayerBambooStack at the position of the tile
     */
    public LayerBambooStack placeTile(Tile tile, PositionVector position) {
        return tileLayer.placeTile(tile, position, this);
    }

    /**
     * Get the bamboo stack at the position.
     *
     * @param positionVector the position of the tile
     */
    public LayerBambooStack getBambooAt(PositionVector positionVector) {
        return bambooLayer.getBambooAt(positionVector, this);
    }

    /**
     * Grow bamboo on a tile. By default, the number of bamboo is 1 if the tile is irrigated.
     *
     * @param positionVector the position of the tile
     */
    public void growBamboo(PositionVector positionVector) {
        bambooLayer.growBamboo(positionVector, this);
    }

    /**
     * Eat a bamboo from a tile.
     *
     * @param positionVector the position of the tile
     */
    public void eatBamboo(PositionVector positionVector) {
        bambooLayer.eatBamboo(positionVector, this);
    }

    /** Get the Position of the Panda */
    public PositionVector getPandaPosition() {
        return panda.getPosition();
    }

    /** Get the Position of the Gardener */
    public PositionVector getGardenerPosition() {
        return gardener.getPosition();
    }

    /**
     * Returns possible moves for the panda.
     *
     * @return the possible moves
     */
    public List<PositionVector> getPandaPossibleMoves() {
        return panda.getPossibleMoves(this);
    }

    /**
     * Returns possible moves for the gardener.
     *
     * @return the possible moves
     */
    public List<PositionVector> getGardenerPossibleMoves() {
        return gardener.getPossibleMoves(this);
    }

    /**
     * Move the panda with a vector.
     *
     * @param vector the vector to move the panda
     * @return bamboo stack of one if the panda ate bamboo
     */
    public LayerBambooStack movePanda(PositionVector vector) {
        return panda.move(vector, this);
    }

    /**
     * Move the gardener with a vector.
     *
     * @param vector the vector to move the gardener
     * @return bamboo stack of one if the gardener planted bamboo
     */
    public LayerBambooStack moveGardener(PositionVector vector) {
        return gardener.move(vector, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return tileLayer.equals(board.tileLayer)
                && bambooLayer.equals(board.bambooLayer)
                && panda.equals(board.panda)
                && gardener.equals((board.gardener))
                && gameAssets.equals(board.gameAssets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tileLayer, bambooLayer, panda, gardener, gameAssets);
    }

    public Board copy() {
        return new Board(this);
    }

    public Optional<Weather> getWeather() {
        return Optional.ofNullable(weather);
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void resetWeather() {
        weather = null;
    }
}
