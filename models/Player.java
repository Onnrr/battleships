package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Player {
    final int MAX_ID = 9999;
    final int MIN_ID = 1000;
    final int TABLE_SIZE = 10;
    final int SHIPS = 20;
    final char[] LETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
    String name;
    int[][] myTable;
    int[][] opponentTable;
    int numberOfCorrectGuesses;
    boolean isHost;
    int gameID;

    InputStream input;
    BufferedReader reader;

    OutputStream output;
    PrintWriter writer;

    ServerSocket ss;
    Socket s;

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
        this.isHost = isHost;
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

    public boolean isHost() {
        return isHost;
    }

    /**
     * For the host
     * Game id is created not taken as an argument
     * 
     * @throws IOException
     */
    public void connect() throws IOException {
        System.out.println("started waiting");
        gameID = generateGameID();

        ss = new ServerSocket(gameID);
        Socket s = ss.accept();
        System.out.println("Client connected");

        input = s.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));

        output = s.getOutputStream();
        writer = new PrintWriter(output, true);

    }

    /**
     * For the host
     * Game id is created not taken as an argument
     * 
     * @throws IOException
     */
    public void connect(int id) throws IOException {
        ss = null;
        System.out.println("bububub");
        s = new Socket("localhost", id);
        System.out.println("I connected");

        input = s.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));

        output = s.getOutputStream();
        writer = new PrintWriter(output, true);
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public String getMesssage() throws IOException {
        return reader.readLine();
    }

    public boolean hit(int row, int col) {
        return myTable[col][row] == 1;
    }

    public int getGameID() {
        return gameID;
    }

    private int generateGameID() {
        return (int) ((Math.random() * (MAX_ID - MIN_ID)) + MIN_ID);
    }

    public void setGameID(int id) {
        gameID = id;
    }

}
