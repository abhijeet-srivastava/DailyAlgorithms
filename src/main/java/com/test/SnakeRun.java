package com.test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SnakeRun {
    /**
     * Grid,
     * snake - covers cells of grid
     * At a fix interval snake size is increasing
     * Snake start from particular cell - {0, 0} - R
     * Once snake reach boundry, it starts from beginning
     * End Game : snake hits to its own
     */
    private int width;
    private int height;

    private Deque<int[]> snake;

    private int[] delta;

    private int timestamp;

    private boolean isGameOver = false;

    private int updateSizeInterval;
    private Set<Integer> snakeCells;

    private long lastTimeStamp;

    private Character currDir;



    private  static final Map<Character, int[]>  DIRS = Map.of(
            'U' , new int[]{-1, 0},
            'D' , new int[]{1, 0},
            'L' , new int[]{0, -1},
            'R' , new int[]{0, 1}
    );

    public SnakeRun(int m, int n, int k) {
        this.width = n;
        this.height = m;
        this.snake =  new ArrayDeque<>();
        this.snake.offerFirst(new int[]{0, 0});
        this.timestamp = 0;
        this.updateSizeInterval = k;
        this.snakeCells = new HashSet<>();
        this.snakeCells.add(0);
        this.lastTimeStamp = System.currentTimeMillis();
        this.currDir = 'R';
    }

    public void moveByTs(char dir) {
        long currTimeStamp = System.currentTimeMillis();
        long timDelta = currTimeStamp - this.lastTimeStamp;
        int cellsEaten = (int)timDelta%1000;
        for(int i = 0; i < cellsEaten; i++) {
            this.move(this.currDir);
        }
        this.lastTimeStamp = currTimeStamp;
        this.currDir = dir;
    }

    //U, D, L, R -
    public void move(char dir) {
        this.delta = DIRS.get(dir);
        int[] currCell = this.snake.peekFirst();
        int newRow = (currCell[0] + delta[0] + this.height)%this.height, newCol = (currCell[1] + delta[1] + this.width)%this.width;
        int[] nextCell = new int[] {newRow, newCol};
        int nextCellVal = newRow*this.width+newCol;
        if(this.snakeCells.contains(nextCellVal)) {
            this.isGameOver = true;
            return;
        }
        this.timestamp += 1;
        if(this.timestamp % this.updateSizeInterval != 0) {
            int[] lastCell = this.snake.removeLast();
            this.snakeCells.remove(lastCell[0]*this.width + lastCell[1]);
        }
        this.snakeCells.add(nextCellVal);
        this.snake.offerFirst(nextCell);
    }

    public boolean isGameOver() {
        //To Know game is over or not
        return this.isGameOver;
    }


    public static void main(String[] args) {
        SnakeRun sr = new SnakeRun(10, 10, 3);
        for(int i = 0; i < 15; i++) {
            sr.move('R');
        }
        System.out.printf("1: Is Game over: %b\n", sr.isGameOver());
        sr.move('D');
        sr.move('L');
        sr.move('U');
        System.out.printf("2: Is Game over: %b\n", sr.isGameOver());
    }

}
