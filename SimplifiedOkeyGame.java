import java.util.Random;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     * 
     * Erkam Uysal - Done
     */
    public void distributeTilesToPlayers() {
        players[0].addTile(tiles[--tileCount]);
        for(int i = 0; i < 4 * 14; i++) {
            players[i / 14].addTile(tiles[--tileCount]);
        }
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     * 
     * Erkam Uysal - Done
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     * 
     * Erkam Uysal - Done
     */
    public String getTopTile() {
        players[currentPlayerIndex].addTile(tiles[--tileCount]);
        return tiles[tileCount].toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     * 
     * Erkam Uysal - Done
     */
    public void shuffleTiles() {
        Random rnd = new Random();
        for(int i = 103; i > 0; i--) {
            int index = rnd.nextInt(i);
            Tile tmp = tiles[i];
            tiles[i] = tiles[index];
            tiles[index] = tmp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     * 
     * Eser Tekin Tekeli - Done
     */
    public boolean didGameFinish() {
        if(players[currentPlayerIndex].checkWinning()){
            return true;
        }
        return false;
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     * 
     * Erkam Uysal - Done
     */
    public Player[] getPlayerWithHighestLongestChain() {

        int count = 1, maxChain = players[0].findLongestChain();
        for(int i = 1; i < 4; i++) {
            if(maxChain < players[i].findLongestChain()) {
                count = 1;
                maxChain = players[i].findLongestChain();
            }
            else if (maxChain == players[i].findLongestChain()) {
                count++;
            }
        }
        Player[] winners = new Player[count];
        for(int i = 0; i < 4; i++) {
            if(players[i].findLongestChain() == maxChain) {
                winners[--count] = players[i];
            }
        }
        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     * 
     *  Eser Tekin Tekeli - Done
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     * 
     * Eser Tekin Tekeli - Done
     */
    public void pickTileForComputer() {
        /*Find the longest chain ->
        * 1- If last discarded tile increases it) take it
        * 2- If last discarded tile doesnt increase it) take it from the top ->
        */
        int currentChain = 1, longestChain = 1, longestChainIndex = 0;
        Player currentPlayer = players[currentPlayerIndex];
        Tile[] currentPlayTiles = currentPlayer.getTiles();
        for(int i = 0; i < 13; i++){
            if(currentPlayTiles[i].canFormChainWith(currentPlayTiles[i + 1])){
                currentChain++;
            }
            else{
                currentChain = 1;
            }
            if(longestChain < currentChain){
                longestChain = currentChain;
                longestChainIndex = i + 1;
            }
        }
        boolean discardedCanForm = lastDiscardedTile.canFormChainWith(currentPlayTiles[longestChainIndex]) || lastDiscardedTile.canFormChainWith(currentPlayTiles[longestChainIndex + 1 - longestChain]);
        if(discardedCanForm){
            getLastDiscardedTile();
        } else{
            getTopTile();
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     * 
     * Eser Tekin Tekeli -Done
     */
    public void discardTileForComputer() {
        /*
         * least usefull card is the card that is the furthest away from it's neighboors
         * first and the last cards are assumed to have their missing neighboor can form chain
         */

        //give each card their value
        int[] cardValue = new int[15];
        Tile[] currentPlayerTiles = players[currentPlayerIndex].getTiles();
        cardValue[0] = currentPlayerTiles[1].value - currentPlayerTiles[0].value + 1; 
        cardValue[14] = currentPlayerTiles[14].value - currentPlayerTiles[14].value + 1;
        for(int i = 1; i < 14; i++){
            cardValue[i] = currentPlayerTiles[i + 1].value - currentPlayerTiles[i - 1].value;
        }

        //discard the card with the highest value
        int maxValue = 0, maxValueIndex = 0;
        for(int i = 0; i < 15; i++){
            if(maxValue < cardValue[i]){
                maxValue = cardValue[i];
                maxValueIndex = i;
            }
        }
        discardTile(maxValueIndex);
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     * 
     * Eser Tekin Tekeli -Done
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
