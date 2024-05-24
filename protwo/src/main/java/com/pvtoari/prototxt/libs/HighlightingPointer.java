package com.pvtoari.prototxt.libs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;

import com.pvtoari.prototxt.libs.Ansi;

public class HighlightingPointer {
    private int pointingAtRow;
    private int pointingAtColumn;
    private final Ansi ANSI_HIGHLIGHT = new Ansi(Ansi.REVERSE_VIDEO);
    private ArrayList<String> charGrid;
    private boolean pointerUpdated;

    public HighlightingPointer(ArrayList<String> arrl) {
        this.charGrid = arrl;
        this.pointingAtRow = 0;
        this.pointingAtColumn = 0;
    }

    public boolean forwardRow(){
        movePointer(this.pointingAtRow++, this.pointingAtColumn);
        return true;
    }

    public boolean forwardColumn(){
        movePointer(this.pointingAtRow, this.pointingAtColumn++);
        return true;
    }

    public boolean backwardRow(){
        movePointer(this.pointingAtRow--, this.pointingAtColumn);
        return true;
    }

    public boolean backColumn(){
        movePointer(this.pointingAtRow, this.pointingAtColumn--);
        return true;
    }

    public void movePointer(int row, int column){
        if(checkPointer()) {
            this.pointingAtRow = row;
            this.pointingAtColumn = column;
            this.pointerUpdated = true;
        }
    }

    public boolean checkPointer() throws InputMismatchException{
        boolean res = false;

        if(this.pointingAtRow < 0 || this.pointingAtColumn < 0){
            throw new ArrayIndexOutOfBoundsException("Bad data for pointer arguments");
        } else if(this.pointingAtRow >= charGrid.size()) {
            throw new ArrayIndexOutOfBoundsException("Row pointer out of bounds");
        } else if(this.pointingAtColumn >= Listener.CHARACTER_PER_LINE_LIMIT) {
            throw new ArrayIndexOutOfBoundsException("Column pointer out of bounds");
        } else {
            res = true;
        }
        
        return res;
    }

    public void printHighlightedArrayList() {
        System.out.println();
        System.out.print("\r");
        for(int i = 0; i < charGrid.size(); i++) {
            for(int j = 0; j < charGrid.get(i).length(); j++) {
                // if the pointer is pointing at this position, highlight it
                if(i == pointingAtRow && j == pointingAtColumn) {
                    System.out.print(ANSI_HIGHLIGHT.colorize(String.valueOf(charGrid.get(i).charAt(j))));
                } else {
                    // if not, print it normally
                    System.out.print(charGrid.get(i).charAt(j));
                }
            }
            // add a new line after each line
            System.out.println();
        }
    }

    public boolean getPointerUpdated() {
        return this.pointerUpdated;
    }
}