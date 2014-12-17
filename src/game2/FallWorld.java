package game2;

import javalib.funworld.*;
import javalib.worldimages.*;
import java.util.Random;

public class FallWorld extends World implements Constants {

    int width = wWIDTH;
    int height = wHEIGHT;
    Dodger dodger;
    Thing thing1;
    Thing thing2;
    Thing thing3;
    int score;

    // Generates random integers used in placing Things
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    // Constructor for the world
    public FallWorld(Dodger dodger, Thing thing1, Thing thing2, Thing thing3, int score) {
        super();
        this.dodger = dodger;
        this.thing1 = thing1;
        this.thing2 = thing2;
        this.thing3 = thing3;
        this.score = score;
    }
    
    public int getScore(FallWorld fw){
        return fw.score;
    }

    //Move Dodger on key presses
    public World onKeyEvent(String key) {
        if (key.equals("up") || key.equals("down")) {
            return new FallWorld(this.dodger, this.thing1, this.thing2,
                    this.thing3, this.score);
        } else {
            return new FallWorld(this.dodger.moveDodger(key), this.thing1,
                    this.thing2, this.thing3, this.score);
        }
    }

    // On tick check:
    // - If Dodger is out of bounds.
    // - If Dodger hit a Thing.
    // - If Thing is out of bounds.
    public World onTick() {
        // If Dodger is out of bounds, end the world.
        if (this.dodger.outOfBounds(this.width, this.height)) {
            return this.endOfWorld("OutOfBounds");
        }
        // If the thing3 is out of bounds: 
        // - 'Reset' all things to initial Y with random X
        // - Increment score
        if (this.thing3.atBounds(this.width, this.height)) {
            int closeX = this.dodger.center.x;
            this.thing1 = new Thing(new Posn(closeX + randInt(-2, -1) * dRADIUS * 2, -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            this.thing2 = new Thing(new Posn(closeX, -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            this.thing3 = new Thing(new Posn(closeX + randInt(1, 2) * dRADIUS * 2, -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            if (this.thing1.center.x < 0) {
                this.thing1 = new Thing(new Posn(0 + dRADIUS, -dRADIUS),
                        tRADIUS, 0, 1, tCOLOR);
            }
            if (this.thing3.center.x > width) {
                this.thing3 = new Thing(new Posn(width - dRADIUS, -dRADIUS),
                        tRADIUS, 0, 1, tCOLOR);
            }
            return new FallWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.thing3.moveThing(),
                    this.score = this.score + 1);
        }
        // If Dodger hit either thing1 or thing2, end the world
        if (this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2)) {
            return this.endOfWorld("DodgerThingCollision");
        } // Otherwise move thing1 and thing2.
        else {
            return new FallWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.thing3.moveThing(), this.score);
        }
    }

    // Background image for world 
    public WorldImage background
            = new RectangleImage(new Posn(this.width / 2, this.height / 2),
                    this.width, this.height, wCOLOR);

    // Produce image of world by adding Dodger and Thing to the background
    public WorldImage makeImage() {
        return new OverlayImages(this.background,
                new OverlayImages(new TextImage(new Posn(this.width / 2, dRADIUS),
                                "SCORE: " + this.score, bCOLOR),
                new OverlayImages(this.dodger.dodgerImage(),
                        new OverlayImages(this.thing1.thingImage(),
                                new OverlayImages(this.thing2.thingImage(),
                                        this.thing3.thingImage())))));
    }

    // Produce image of world by adding fail state explanation to background
    public WorldImage lastImage(String s) {
        return new OverlayImages(this.makeImage(),
                new TextImage(new Posn(this.width / 2, this.height / 2), s,
                        sCOLOR));
    }

    //Check if World should end:
    // - When Dodger is out of bounds
    // - If Dodger hit a Thing
    public WorldEnd worldEnds() {
        if (this.dodger.outOfBounds(this.width, this.height)) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "Out of Bounds!" + " SCORE: " + this.score, sCOLOR)));
        }
        if (this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2)
                || this.dodger.didCollide(thing3)) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "You didn't 'Dodge the Thing'!" + " SCORE: "
                            + this.score, sCOLOR)));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }
}
