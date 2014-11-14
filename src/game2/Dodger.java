package game2;

import java.awt.Color;
import javalib.worldimages.*;

public class Dodger implements Constants{
    Posn center;
    int radius;
    Color color;

    // Constructor for Dodger
    Dodger(Posn center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    // Produce the image of the Dodger at it's curent location
    WorldImage dodgerImage() {
        return new DiskImage(this.center, this.radius, this.color);
    }

    // Moves the Dodger based on user inputs
    public Dodger moveDodger(String key) {
        switch (key) {
            case "right":
                return new Dodger(new Posn(this.center.x + dRADIUS * 2,
                        this.center.y), this.radius, this.color);
            case "left":
                return new Dodger(new Posn(this.center.x - dRADIUS * 2,
                        this.center.y), this.radius, this.color);
            case "up":
                return new Dodger(new Posn(this.center.x,
                        this.center.y - dRADIUS * 2), this.radius, this.color);
            case "down":
                return new Dodger(new Posn(this.center.x,
                        this.center.y + dRADIUS * 2), this.radius, this.color);
            default:
                return this;
        }
    }

    // Checks if Dodger is out of bounds.
    boolean outOfBounds(int width, int height) {
        return this.center.x < 0
                || this.center.x > width
                || this.center.y < 0
                || this.center.y > height;
    }

    // Checks if Dodger hit a Thing
    boolean didCollide(Thing thing) {
        return this.center.x == thing.center.x
                && this.center.y == thing.center.y;
    }

    // Checks if Dodger hit an Explosion
    boolean didCollide(Explosion explosion) {
        return this.center.x == explosion.center.x
                && this.center.y == explosion.center.y
                || this.center.x == explosion.center.x + eRADIUS * 2 / 3
                && this.center.y == explosion.center.y + eRADIUS * 2 / 3
                || this.center.x == explosion.center.x + eRADIUS * 2 / 3
                && this.center.y == explosion.center.y
                || this.center.x == explosion.center.x + eRADIUS * 2 / 3
                && this.center.y == explosion.center.y - eRADIUS * 2 / 3
                || this.center.x == explosion.center.x
                && this.center.y == explosion.center.y - eRADIUS * 2 / 3
                || this.center.x == explosion.center.x - eRADIUS * 2 / 3
                && this.center.y == explosion.center.y - eRADIUS * 2 / 3
                || this.center.x == explosion.center.x - eRADIUS * 2 / 3
                && this.center.y == explosion.center.y
                || this.center.x == explosion.center.x - eRADIUS * 2 / 3
                && this.center.y == explosion.center.y + eRADIUS * 2 / 3
                || this.center.x == explosion.center.x
                && this.center.y == explosion.center.y + eRADIUS * 2 / 3;
    }
}
