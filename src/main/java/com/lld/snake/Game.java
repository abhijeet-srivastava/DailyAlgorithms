package com.lld.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private int width;
    private int height;

    private Board board;
    private Snake snake;

    private GameState state;

    private AtomicInteger moveCounts;

    private Integer incrAfter;

    private static final Map<Character, MoveCommand> ALLOWED_MOVES = Map.of(
            'U', MoveCommand.UP,
            'D', MoveCommand.DOWN,
            'L', MoveCommand.LEFT,
            'R', MoveCommand.RIGHT
    );

    public Game(int width, int height, List<int[]> initSnake, int incrAfter) {
        this.width = width;
        this.height = height;
        this.board = new Board(width,height);
        this.moveCounts = new AtomicInteger(0);
        this.incrAfter = incrAfter;
        List<Cell> snakeCells = new ArrayList<>();
        for(int[] body: initSnake) {
            snakeCells.add(board.getCell(body[0], body[1]));
        }
        this.snake = new Snake(snakeCells);
        this.state = GameState.GAME_STARTED;
    }

    public void makeMovement(Character move) {
        if(!ALLOWED_MOVES.containsKey(move)) {
            throw new AssertionError("Invalid move");
        }
        MoveCommand command = ALLOWED_MOVES.get(move);
        Cell currHead = this.snake.getHead();
        int nextRow = (currHead.getRow() + command.getDeltaX() + height)%height;
        int nextCol = (currHead.getCol() + command.getDeltaY() + width)%width;
        Cell nextHead = this.board.getCell(nextRow, nextCol);
        nextHead.setVal(command.getHead());
        this.snake.makeMovement(nextHead, this.moveCounts.incrementAndGet()%incrAfter == 0);
        board.printBoard();
    }
}
