package models;

public class Player {
    final int TABLE_SIZE = 10;
    final int SHIPS = 20;
    final char[] LETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
    String name;
    int[][] myTable;
    int[][] opponentTable;
    int numberOfCorrectGuesses;
    boolean isHost;

    int remaining;
    /*
     * 0 -> unknown
     * 1 -> has ship
     * 2 -> empty
     * 3 -> sunk ship
     * 4 -> opponent's sunk ship
     */

    public Player(String name, boolean isHost) {
        myTable = new int[TABLE_SIZE][TABLE_SIZE];
        opponentTable = new int[TABLE_SIZE][TABLE_SIZE];
        numberOfCorrectGuesses = 0;
        remaining = SHIPS;
        this.name = name;
    }

    public int getNumberOfCorrectGuesses() {
        return numberOfCorrectGuesses;
    }

    public void incrementCorrectGuess() {
        numberOfCorrectGuesses++;
    }

    public boolean correctGuess(String guess) {
        char row = guess.charAt(0);
        char column = guess.charAt(1);
        return myTable[findIndex(row)][Character.getNumericValue(column)] == 1;
    }

    private int findIndex(char a) {
        for (int i = 0; i < LETTERS.length; i++) {
            if (i == LETTERS[i]) {
                return i;
            }
        }
        return -1;
    }

    public int[][] getMyTable() {
        return myTable;
    }

    public int[][] getOpponentTable() {
        return opponentTable;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getOppRemaining() {
        return SHIPS - numberOfCorrectGuesses;
    }

    public String getName() {
        return name;
    }
}
