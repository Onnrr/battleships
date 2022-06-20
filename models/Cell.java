package models;

import javafx.scene.control.Button;

public class Cell extends Button {
    int row;
    int column;
    boolean occupied;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        occupied = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setOccupied(boolean b) {
        occupied = b;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
