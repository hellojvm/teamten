// Copyright 2011 Lawrence Kesteloot

package com.teamten.mario;

import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

/**
 * The environment (walls, etc.) that our character lives in.
 */
public class Env {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 100;
    private final List<Floor> mFloorList = new ArrayList<Floor>();

    public static Env makeEnv() {
        Env env = new Env();

        env.addFloor(new Floor(0, WIDTH, HEIGHT - Floor.HEIGHT));
        env.addFloor(new Floor(WIDTH/5, WIDTH/5,
                    HEIGHT - Floor.HEIGHT - Player.HEIGHT*3/2 - Floor.HEIGHT));
        env.addFloor(new Floor(WIDTH/5*2, WIDTH/5,
                    HEIGHT - Floor.HEIGHT - Player.HEIGHT*3/2*2 - Floor.HEIGHT*2));

        return env;
    }

    private void addFloor(Floor floor) {
        mFloorList.add(floor);
    }

    public boolean isTouchingFloor(Player player) {
        int playerBottom = player.getY() + Player.HEIGHT - 1;

        for (Floor floor : mFloorList) {
            if (playerFloorHorizontalOverlap(player.getX(), floor)) {
                if (playerBottom == floor.getTop() - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public Integer getPushBack(Player player, int x, int y, int vx, int vy) {
        for (Floor floor : mFloorList) {
            if (playerFloorHorizontalOverlap(x, floor)) {
                if (playerFloorVerticalOverlap(y, floor)) {
                    if (vy > 0) {
                        // Going down.
                        return y + Player.HEIGHT - floor.getTop();
                    } else {
                        return y - (floor.getTop() + Floor.HEIGHT);
                    }
                }
            }
        }

        return null;
    }

    private static boolean playerFloorHorizontalOverlap(int playerX, Floor floor) {
        return playerX + Player.WIDTH - 1 >= floor.getLeft()
            && playerX < floor.getLeft() + floor.getWidth() - 1;
    }

    private static boolean playerFloorVerticalOverlap(int playerY, Floor floor) {
        return playerY + Player.HEIGHT - 1 >= floor.getTop()
            && playerY < floor.getTop() + Floor.HEIGHT - 1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Floor floor : mFloorList) {
            floor.draw(g);
        }
    }
}
