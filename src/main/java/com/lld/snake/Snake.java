package com.lld.snake;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Snake {

    public Deque<Cell> snake;


    public Snake(List<Cell> snakeCells) {
        if(!isValid(snakeCells)) {
            throw new AssertionError("Invalid snake cells");
        }
        this.snake = new LinkedList<>();
        Cell head = snakeCells.get(0);
        head.setVal('>');
        this.snake.offerLast(head);
        for(int i = 1; i < snakeCells.size(); i++) {
            Cell snakeBody = snakeCells.get(i);
            snakeBody.setVal('~');
            this.snake.offerLast(snakeBody);
        }
    }
    public void makeMovement(Cell nextHead, boolean increment) {
        Cell currHead = snake.peekFirst();
        currHead.setVal('~');
        if(!increment) {
            Cell last = this.snake.removeLast();
            last.setVal(' ');
        }
        //Cell newHead = new Cell(nextRow, nextCol, MAX_HEIGHT, MAX_WIDTH, '>');
        if(selfCollision(nextHead)) {
            throw new RuntimeException("Snake collided to itself, Game Over");
        }
        this.snake.offerFirst(nextHead);
    }

    private boolean selfCollision(Cell newHead) {
        for(Cell snakeBody: this.snake) {
            if(snakeBody.getRow() == newHead.getRow() && snakeBody.getCol() == newHead.getCol()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(List<Cell> snakeCells) {
        if(snakeCells == null || snakeCells.size() == 0) {
            return false;
        } else if(snakeCells.size() == 1) {
            return true;
        } else if(snakeCells.size() == 2) {
            Cell first = snakeCells.get(0), last = snakeCells.get(1);
            return isLinear(first, last);
        }
        Cell prev = snakeCells.get(0), curr = snakeCells.get(1);
        if(!isLinear(prev, curr)) {
            return false;
        }
        int deltaR  = curr.getRow() - prev.getRow(), deltaC = curr.getCol() - prev.getCol();
        for(int i = 2; i < snakeCells.size(); i++) {
            prev = curr;
            curr =snakeCells.get(i);
            int currDeltaR = curr.getRow() - prev.getRow(), currDeltaC = curr.getCol() - prev.getCol();
            if(currDeltaR != deltaR && currDeltaC != deltaC) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLinear(Cell first, Cell last) {
        return (Math.abs(first.getRow() - last.getRow()) == 1 && first.getCol() == last.getCol())
                || Math.abs(first.getCol() - last.getCol()) == 1 && first.getRow() == last.getRow();
    }

    public Cell getHead() {
        return this.snake.peekFirst();
    }
}
