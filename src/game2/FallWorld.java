package game2;

import javalib.funworld.*;
import javalib.worldimages.*;
import java.util.Random;
import static java.lang.Math.*;

public class FallWorld extends World implements Constants {

    int width = wWIDTH;
    int height = wHEIGHT;
    Dodger dodger;
    Thing thing1;
    Thing thing2;
    Thing thing3;
    int score;
    int lives;
    int rounds;

    // Generates random integers used in placing Things
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    // Constructor for the world
    public FallWorld(Dodger dodger, Thing thing1, Thing thing2, Thing thing3,
            int score, int lives, int rounds) {
        super();
        this.dodger = dodger;
        this.thing1 = thing1;
        this.thing2 = thing2;
        this.thing3 = thing3;
        this.score = score;
        this.lives = lives;
        this.rounds = rounds;
    }

    public int getScore(FallWorld fw) {
        return fw.score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    //Move Dodger on key presses
    public World onKeyEvent(String key) {
        if (key.equals("up") || key.equals("down")) {
            return new FallWorld(this.dodger, this.thing1, this.thing2,
                    this.thing3, this.score, this.lives, this.rounds);
        } else {
            return new FallWorld(this.dodger.moveDodger(key), this.thing1,
                    this.thing2, this.thing3, this.score, this.lives, this.rounds);
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
        // If the things are out of bounds: 
        // - 'Reset' all things to initial Y with random X
        // - Increment score
        if (this.thing3.atBounds(this.width, this.height)) {
            int closeX = this.dodger.center.x;
            this.thing1 = new Thing(new Posn(closeX + randInt(-2, -1) * dRADIUS * 2,
                    -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            this.thing2 = new Thing(new Posn(closeX, -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            this.thing3 = new Thing(new Posn(closeX + randInt(1, 2) * dRADIUS * 2,
                    -dRADIUS),
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
                    this.score = this.score + 1, this.lives, this.rounds);
        }
        // If Dodger hit any of the things, check lives left:
        //  - If 1 or more lives, decrement lives and continue world
        //  - If 0 lives, end the world
        if (this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2)) {
            if (this.lives >= 1) {
                return new FallWorld(this.dodger, this.thing1.moveThing(),
                        this.thing2.moveThing(), this.thing3.moveThing(),
                        this.score, this.lives = this.lives - 1, this.rounds);
            } else {
                return this.endOfWorld("DodgerThingCollision");
            }
        } // Otherwise move the things.
        else {
            return new FallWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.thing3.moveThing(),
                    this.score, this.lives, this.rounds);
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
                                "SCORE: " + this.score
                                + "          LIVES: " + this.lives,
                                bCOLOR),
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
        if (this.rounds == 0) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "Round 5 Over! Your final score is: " + this.score, sCOLOR)));
        } else {
            if (this.dodger.outOfBounds(this.width, this.height)) {
                DodgeWorld w1 = new DodgeWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT / 2), dRADIUS, dCOLOR),
                        new Thing(new Posn(tRADIUS, tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2),
                                tRADIUS, 1, 0, tCOLOR),
                        new Thing(new Posn(tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                                tRADIUS, 0, 1, tCOLOR),
                        new Explosion(new Posn(-eRADIUS, -eRADIUS), eRADIUS, eCOLOR), 
                        this.score, max(this.score / 5, 1), this.rounds - 1);
                w1.bigBang(wWIDTH, wHEIGHT, 0.07);
                return new WorldEnd(true, new OverlayImages(this.makeImage(),
                        new TextImage(new Posn(this.width / 2, this.height / 2),
                                "Out of Bounds!", sCOLOR)));
            }
            if ((this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2)
                    || this.dodger.didCollide(thing3)) && this.lives == 1) {
                DodgeWorld w1 = new DodgeWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT / 2), dRADIUS, dCOLOR),
                        new Thing(new Posn(tRADIUS, tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2),
                                tRADIUS, 1, 0, tCOLOR),
                        new Thing(new Posn(tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                                tRADIUS, 0, 1, tCOLOR),
                        new Explosion(new Posn(-eRADIUS, -eRADIUS), eRADIUS, eCOLOR), this.score, max(this.score / 5, 1), this.rounds - 1);
                w1.bigBang(wWIDTH, wHEIGHT, 0.07);
                return new WorldEnd(true, new OverlayImages(this.makeImage(),
                        new TextImage(new Posn(this.width / 2, this.height / 2),
                                "You didn't 'Dodge the Thing'!", sCOLOR)));
            } else {
                return new WorldEnd(false, this.makeImage());
            }
        }
    }
}
