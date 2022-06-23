package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.text.Text;

public class Player {
    final int MAX_ID = 9999;
    final int MIN_ID = 1000;
    final int TABLE_SIZE = 10;
    final int SHIPS = 20;
    int[][] myTable;
    int numberOfCorrectGuesses;
    boolean isHost;
    int gameID;

    boolean isOpponentConnected;

    InputStream input;
    BufferedReader reader;

    OutputStream output;
    PrintWriter writer;

    ServerSocket ss;
    Socket s;

    int remaining;

    public Player(boolean isHost) {
        myTable = new int[TABLE_SIZE][TABLE_SIZE];
        numberOfCorrectGuesses = 0;
        remaining = SHIPS;
        this.isHost = isHost;
        if (!isHost) {
            isOpponentConnected = true;
        } else {
            isOpponentConnected = false;
        }
    }

    public boolean isOpponentConnected() {
        return isOpponentConnected;
    }

    public void setOpponentConnected(boolean connected) {
        isOpponentConnected = connected;
    }

    public int getNumberOfCorrectGuesses() {
        return numberOfCorrectGuesses;
    }

    public void incrementCorrectGuess() {
        numberOfCorrectGuesses++;
    }

    public int[][] getMyTable() {
        return myTable;
    }

    public int getRemaining() {
        return remaining;
    }

    public void decrRemaining() {
        remaining--;
    }

    public int getOppRemaining() {
        return SHIPS - numberOfCorrectGuesses;
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
    public void connect(Text text) throws IOException {
        System.out.println("started waiting");
        gameID = generateGameID();

        ss = new ServerSocket(gameID);
        Socket s = ss.accept();
        System.out.println("Client connected");
        text.setText("Opponent Connected");

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

    public void resetTable() {
        myTable = new int[TABLE_SIZE][TABLE_SIZE];
    }

}
