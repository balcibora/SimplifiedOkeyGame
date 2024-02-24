public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     * 
     * Ceyhun Deniz Keleş -done.
     */
    public boolean checkWinning ()
    {
        if (findLongestChain() >= 14)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     * 
     * Ceyhun Deniz Keleş -done.
     */
    public int findLongestChain () {
        int longestChainSoFar = 0;
        int currentChain = 0;

        for (int i = 0; i < numberOfTiles - 1; i++)
        {
            boolean canFormChain = playerTiles[i].canFormChainWith (playerTiles[i + 1]);
            boolean doesMatch = playerTiles[i].matchingTiles (playerTiles[i + 1]);

            // checks if the current tile can form a chain with the next tile
            if (canFormChain)
            {
                currentChain++;
            }

            // updates the longest chain when current chain is larger
            if (longestChainSoFar < currentChain)
            {
                longestChainSoFar = currentChain;
            }

            /* resets the chain if it cannot form a chain with the next tile while making sure the
            * next tile does not have the same value as the current one
            */
            if (!doesMatch && !canFormChain)
            {
                currentChain = 0;
            }    
        }

        return longestChainSoFar;
    }

    /*
     * TODO: removes and returns the tile in given index position
     * 
     * Ceyhun Deniz Keleş -done.
     */
    public Tile getAndRemoveTile (int index) 
    {
        Tile wantedTile = playerTiles[index];
        playerTiles[index] = null;
        numberOfTiles--;

        for (int i = index; i < playerTiles.length - 1; i++)
        {
            playerTiles[i] = playerTiles[i + 1];
        }

        return wantedTile;
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     * 
     * Ceyhun Deniz Keleş -done.
     */
    public void addTile (Tile t) 
    {
        int compareValue = 2; // terminal value
        int correctIndex = -1; // terminal value
        boolean indexNotFoundYet = true;

        for (int i = 0; i < numberOfTiles; i++)
        {
            compareValue = t.compareTo (playerTiles[i]);

            if (compareValue == -1)
            {
                correctIndex = i;
                indexNotFoundYet = false;
                break;
            }
        }

        /** If the new tile has a bigger value than the rest of the player tiles, it means that the loop above
         * was not able to find an index for the new tile in between the other ones. Therefore, the new tile is
         * added next to the last current index.
         */
        if (indexNotFoundYet)
        {
            correctIndex = numberOfTiles;
        }

        for (int i = playerTiles.length - 2; i >= correctIndex; i--)
        {
            playerTiles[i + 1] = playerTiles[i];
        }

        playerTiles[correctIndex] = t;
        numberOfTiles++;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
