package com.github.yasuniku.kanata.dimension.generator.town.areapatterns.district;

import com.github.yasuniku.kanata.utils.CellPos;
import com.github.yasuniku.kanata.utils.Vector2DInt;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DistrictLayoutGenerator {
    public static List<District> generate(long seed) {
        Random r = new Random(seed);
        District[][] districts = new District[4][4];

        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 4; x++) {
                districts[z][x] = new District(new CellPos(1, 1), new Vector2DInt(3, 3), r.nextLong());
            }
        }

        for (int i = 0; i < 4; ++i) {
            var x = r.nextInt(4);
            districts[i][x].setSize(districts[i][x].getSize().withX(2));
            var z = r.nextInt(4);
            districts[z][i].setSize(districts[z][i].getSize().withY(2));
        }

        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 4; x++) {
                var district = districts[z][x];
                var newX = district.getStart().x();
                var newZ = district.getStart().z();
                if (x != 0) {
                    var left = districts[z][x - 1];
                    newX = left.getStart().x() + left.getWidth() + 1;
                }
                if (z != 0) {
                    var up = districts[z - 1][x];
                    newZ = up.getStart().z() + up.getHeight() + 1;
                }
                district.setStart(new CellPos(newX, newZ));

                Vector2DInt megaBuildingStart = null;
                if (district.getWidth() == 3 && district.getHeight() == 3) {
                    megaBuildingStart = new Vector2DInt(r.nextInt(2), r.nextInt(2));
                } else if (r.nextBoolean()) {
                    megaBuildingStart =
                        new Vector2DInt(r.nextInt(district.getWidth() - 1), r.nextInt(district.getHeight() - 1));
                }
                district.setMegaBuildingStart(megaBuildingStart);
            }
        }

        return Arrays.stream(districts).flatMap(Arrays::stream).toList();
    }
}
