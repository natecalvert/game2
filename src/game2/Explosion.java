package game2;

import java.awt.Color;
import javalib.worldimages.*;

public class Explosion implements Constants {

    Posn center;
    int radius;
    Color color;

    // Constructor for Explosion
    Explosion(Posn center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    // Produce the image of the Explosion at it's current position
    WorldImage explosionImage() {
        return new RectangleImage(this.center, this.radius * 2,
                this.radius * 2, this.color);
    }
}
