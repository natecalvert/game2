package game2;

import java.awt.Color;
import javalib.worldimages.*;

public class Thing implements Constants{
    Posn center;
    int radius;
    int dX;
    int dY;
    Color color;

    // Constructor for Thing
    Thing(Posn center, int radius, int dX, int dY, Color color) {
        this.center = center;
        this.radius = radius;
        this.dX = dX;
        this.dY = dY;
        this.color = color;
    }

    // Produce the image of the Thing at it's curent location
    WorldImage thingImage() {
        return new DiskImage(this.center, this.radius, this.color);
    }

    // Moves the Thing
    public Thing moveThing() {
        if (this.dX == 0) {
            return new Thing(new Posn(this.center.x, this.center.y + tRADIUS * 2),
                    this.radius, this.dX, this.dY, this.color);
        } else if (this.dY == 0) {
            return new Thing(new Posn(this.center.x + tRADIUS * 2, this.center.y),
                    this.radius, this.dX, this.dY, this.color);
        } else { //This should NEVER HAPPEN.
            return new Thing(new Posn(300, 300), 300, 1, 1, Color.MAGENTA);
        }
    }

    // Checks if Thing is out of bounds
    boolean atBounds(int width, int height) {
        return this.center.x > width || this.center.y > height;
    }

    // Checks if Thing hit a Thing
    boolean hitThing(Thing thing) {
        return this.center.x == thing.center.x
                && this.center.y == thing.center.y;
    }
}
