package game2;

import javalib.funworld.*;
import javalib.worldimages.*;
import java.util.Random;
import static java.lang.Math.*;

public class DodgeWorld extends World implements Constants {

    int width = wWIDTH;
    int height = wHEIGHT;
    Dodger dodger;
    Thing thing1;
    Thing thing2;
    Explosion explosion;
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
    public DodgeWorld(Dodger dodger, Thing thing1, Thing thing2,
            Explosion explosion, int score, int lives, int rounds) {
        super();
        this.dodger = dodger;
        this.thing1 = thing1;
        this.thing2 = thing2;
        this.explosion = explosion;
        this.score = score;
        this.lives = lives;
        this.rounds = rounds;
    }

    //Move Dodger on key presses
    public World onKeyEvent(String key) {
        return new DodgeWorld(this.dodger.moveDodger(key), this.thing1,
                this.thing2, this.explosion, this.score, this.lives, this.rounds);
    }

    // On tick check:
    // - If Dodger is out of bounds.
    // - If Dodger hit a Thing.
    // - If Thing is out of bounds.
    // - If thing1 hit thing2.
    // - If Dodger hit an Explosion.
    public World onTick() {
        // If Dodger is out of bounds, end the world.
        if (this.dodger.outOfBounds(this.width, this.height)) {
            return this.endOfWorld("OutOfBounds");
        }
        // If the thing1 is out of bounds: 
        // [Could also be thing2, as they move OoB @ same tick]
        // - 'Reset' both thing1 and thing2 to initial X or Y with random Y or X
        // - Increment score
        if (this.thing1.atBounds(this.width, this.height)) {
            int closeX = this.dodger.center.x;
            int closeY = this.dodger.center.y;
            this.thing1 = new Thing(new Posn(-dRADIUS,
                    closeY + randInt(0, 2) * dRADIUS * 2),
                    tRADIUS, 1, 0, tCOLOR);
            if (this.thing1.center.y > height) {
                this.thing1 = new Thing(new Posn(-dRADIUS, closeY),
                        tRADIUS, 1, 0, tCOLOR);
            }
            if (this.thing1.center.y < 0) {
                this.thing1 = new Thing(new Posn(-dRADIUS, closeY),
                        tRADIUS, 1, 0, tCOLOR);
            }
            this.thing2 = new Thing(new Posn(closeX + randInt(0, 2) * dRADIUS * 2,
                    -dRADIUS),
                    tRADIUS, 0, 1, tCOLOR);
            if (this.thing2.center.x > width) {
                this.thing2 = new Thing(new Posn(closeX, -dRADIUS),
                        tRADIUS, 0, 1, tCOLOR);
            }
            if (this.thing2.center.x < 0) {
                this.thing2 = new Thing(new Posn(closeX, -dRADIUS),
                        tRADIUS, 0, 1, tCOLOR);
            }
            return new DodgeWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.explosion,
                    this.score = this.score + 1, this.lives, this.rounds);
        }
        // If Dodger hit either thing1 or thing2, check lives left:
        //  - If 1 or more lives, decrement lives and continue world
        //  - If 0 lives, end the world
        if (this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2)) {
            if (this.lives >= 1) {
                return new DodgeWorld(this.dodger, this.thing1.moveThing(),
                        this.thing2.moveThing(), this.explosion,
                        this.score, this.lives = this.lives - 1, this.rounds);
            } else {
                return this.endOfWorld("DodgerThingCollision");
            }
        }
        // If thing1 and thing2 collide, create an Explosion
        if (this.thing1.hitThing(thing2) || this.thing2.hitThing(thing1)) {
            this.explosion = new Explosion(new Posn(this.thing1.center.x,
                    this.thing1.center.y), eRADIUS, eCOLOR);
            return new DodgeWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.explosion, this.score,
                    this.lives, this.rounds);
        }
        // If Dodger hit an explosion, check lives left:
        //  - If 1 or more lives, decrement lives and continue world
        //  - If 0 lives, end the world
        if (this.dodger.didCollide(explosion)) {
            if (this.lives >= 1) {
                return new DodgeWorld(this.dodger, this.thing1.moveThing(),
                        this.thing2.moveThing(), this.explosion,
                        this.score, this.lives = this.lives - 1, this.rounds);
            } else {
                return this.endOfWorld("DodgerBlewUp");
            }
        } // Otherwise move thing1 and thing2.
        else {
            return new DodgeWorld(this.dodger, this.thing1.moveThing(),
                    this.thing2.moveThing(), this.explosion, this.score,
                    this.lives, this.rounds);
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
                                new OverlayImages(this.explosion.explosionImage(),
                                        new OverlayImages(this.thing1.thingImage(),
                                                this.thing2.thingImage())))));
    }

    // Produce image of world by adding fail state explanation to background
    public WorldImage lastImage(String s) {
        return new OverlayImages(this.makeImage(),
                new TextImage(new Posn(this.width / 2, this.height / 2), s,
                        sCOLOR));
    }

    //Check if World should end:
    // - When Dodger is out of bounds
    // - If Dodger hit a Thing + no more lives
    // - If Dodger hit and Explosion + no more lives
    // - If round = 0, stop.
    public WorldEnd worldEnds() {
        if (this.dodger.outOfBounds(this.width, this.height)) {
            FallWorld w2 = new FallWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT - 20), dRADIUS, dCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR), this.score, max(this.score / 5, 1), this.rounds - 1);
            w2.bigBang(wWIDTH, wHEIGHT, 0.04);
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "Out of Bounds!", sCOLOR)));
        }
        if ((this.dodger.didCollide(thing1) || this.dodger.didCollide(thing2))
                && this.lives == 1) {
            FallWorld w2 = new FallWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT - 20), dRADIUS, dCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR), this.score, max(this.score / 5, 1), this.rounds - 1);
            w2.bigBang(wWIDTH, wHEIGHT, 0.04);
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "You didn't 'Dodge the Thing'!", sCOLOR)));
        }
        if (this.dodger.didCollide(explosion) && this.lives == 1) {
            FallWorld w2 = new FallWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT - 20), dRADIUS, dCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR),
                    new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                            tRADIUS, 0, 1, tCOLOR), this.score, max(this.score / 5, 1), this.rounds - 1);
            w2.bigBang(wWIDTH, wHEIGHT, 0.04);
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(this.width / 2, this.height / 2),
                            "You blew up!", sCOLOR)));

        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }
}
