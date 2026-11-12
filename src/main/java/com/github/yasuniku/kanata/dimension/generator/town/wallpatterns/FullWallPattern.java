package com.github.yasuniku.kanata.dimension.generator.town.wallpatterns;

@SuppressWarnings("unused")
public class FullWallPattern implements WallPattern {
    @Override
    public boolean[][] getBaseWallShape() {
        boolean[][] BaseWall = new boolean[2][16];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                BaseWall[i][j] = true;
            }
        }
        return BaseWall;
    }
}
