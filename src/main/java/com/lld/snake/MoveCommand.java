package com.lld.snake;

public enum MoveCommand {

    UP(-1, 0, '^'),
    DOWN(1, 0, 'v'),
    LEFT(0, -1, '<'),
    RIGHT(0, 1, '>');

    private int deltaX;
    private int deltaY;

    private Character head;

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public Character getHead() {
        return head;
    }

    MoveCommand(int deltaX, int deltaY, char head) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.head = head;
    }

}
