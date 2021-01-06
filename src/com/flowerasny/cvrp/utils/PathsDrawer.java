package com.flowerasny.cvrp.utils;

import com.flowerasny.cvrp.model.City;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class PathsDrawer {

    private BufferedImage b;
    private Graphics2D g;

    public PathsDrawer() {
        b = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);
        g = b.createGraphics();
    }

    public void drawPath(List<City> cities, Color color) {
        for (int i = 1; i < cities.size(); i++) {
            City city1 = cities.get(i - 1);
            City city2 = cities.get(i);

            g.setColor(Color.white);
            g.drawString(city1.name, 90 * (float) city1.latitude - 1230, 700 - (90 * (float) city1.longitude - 4295));
            g.setColor(color);

            g.draw(new Line2D.Double(
                    90 * city1.latitude - 1200,
                    700 - (90 * city1.longitude - 4300),
                    90 * city2.latitude - 1200,
                    700 - (90 * city2.longitude - 4300))
            );
        }
    }

    public void saveImage() {
        try {
            ImageIO.write(b, "png", new File("best_solution.png"));
        } catch (Exception e) {
            System.out.println("Cannot print the best solution");
        }
    }

}
