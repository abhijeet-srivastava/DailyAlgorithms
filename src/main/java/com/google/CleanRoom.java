package com.google;

import java.util.HashSet;
import java.util.Set;

public class CleanRoom {

    int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};// Turn Left

    public void clean(Robot robot) {
        Set<String> visited = new HashSet<>();
        cleanRoom(robot, 0, 0, 0, visited);
    }

    private void cleanRoom(Robot robot, int row, int col, int dir, Set<String> visited) {
        robot.clean();
        String key = String.valueOf(row).concat("_").concat(String.valueOf(col));
        visited.add(key);
        for(int i = 0; i < 4; i++) {
            int nDir = (dir + 1) % 4;
            int nRow = row + DIRS[nDir][0];
            int nCol = col + DIRS[nDir][1];
            String nKey = String.valueOf(nRow).concat("_").concat(String.valueOf(nCol));
            if(!visited.contains(nKey) && robot.move()) {
                cleanRoom(robot, nRow, nCol, nDir, visited);
                goBack(robot);
            }
            robot.turnRight();
        }
    }

    private void goBack(Robot robot) {
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }
}
