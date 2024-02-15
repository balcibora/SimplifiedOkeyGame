public class Tile {
    
    int value;

    /*
     * creates a tile using the given value. False jokers are not included in this game.
     */
    public Tile(int value) {
        this.value = value;
    }

    /*
     * TODO: should check if the given tile t and this tile have the same value 
     * return true if they are matching, false otherwise
     * 
     * Gökdeniz Derelioğlu - done.
     */
    public boolean matchingTiles(Tile t) {
        return (this.value == t.value);
    }

    /*
     * TODO: should compare the order of these two tiles
     * return 1 if given tile has smaller in value
     * return 0 if they have the same value
     * return -1 if the given tile has higher value
     * 
     * Gökdeniz Derelioğlu - done.
     */
    public int compareTo(Tile t) {
        int output = 2; //terminal value

        if (t.value < this.value)
        {
            output = 1;
        }
        else if (t.value == this.value)
        {
            output = 0;
        }
        else
        {
            output = -1;
        }
       
        return output;
    }

    /*
     * TODO: should determine if this tile and given tile can form a chain together
     * this method should check the difference in values of the two tiles
     * should return true if the absoulute value of the difference is 1 (they can form a chain)
     * otherwise, it should return false (they cannot form a chain)
     * 
     * Gökdeniz Derelioğlu - done.
     */
    public boolean canFormChainWith(Tile t) {
        int difference = Math.abs(t.value - this.value);
        return (difference == 1);
    }

    public String toString() {
        return "" + value;
    }

    public int getValue() {
        return value;
    }

}