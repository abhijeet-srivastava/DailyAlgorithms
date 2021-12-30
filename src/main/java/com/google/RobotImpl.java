package com.google;

public class RobotImpl implements RobotII {

    public static void main(String[] args) {
        RobotII robo = new RobotImpl(20, 13);
        robo.move(12);
        robo.move(21);
        int[] pos = robo.getPos();
        String dir = robo.getDir();
        robo.move(2);
        robo.move(1);
        robo.move(4);
        pos = robo.getPos();
        dir = robo.getDir();
    }

    private final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private final String[] DIR_NAMES =  {"East", "North", "West", "South"};
    int height;
    int width;

    int x;
    int y;

    int direction;

    public RobotImpl(int width, int height) {
        this.height = height;
        this.width = width;
        this.x = 0;
        this.y = 0;
        this.direction  = 0;
    }

    @Override
    public void move(int num) {
        int[] dir = DIRECTIONS[direction];
        while(num > 0) {
            int rm = this.x + dir[0]*num;
            int cm = this.y + dir[1]*num;
            if(rm >= 0 && rm < width & cm >= 0 && cm < height) {
                num = 0;
                this.x = rm;
                this.y = cm;
                if((dir[0] > 0  && this.x == width-1)
                        || (dir[0] < 0 && this.x ==  0)
                        || (dir[1] > 0 && this.y == height-1)
                        || (dir[1] < 0  && this.y == 0)) {
                    this.direction = (this.direction + 1) % 4;
                }
                continue;
            } else {
                if (this.direction == 0) {
                    num -= (this.width - this.x - 1);
                    this.x = this.width - 1;
                } else if (this.direction == 1) {
                    num -= (this.height - this.y - 1);
                    this.y = this.height - 1;
                } else if (this.direction == 2) {
                    num -= this.x;
                    this.x = 0;
                } else {
                    num -= this.y;
                    this.y = 0;
                }
            }
            this.direction = (this.direction + 1) % 4;
            dir = DIRECTIONS[direction];

        }

    }

    @Override
    public int[] getPos() {
        return new int[]{x, y};
    }

    @Override
    public String getDir() {
        return DIR_NAMES[this.direction];
    }
}
