package game2;

import javalib.worldimages.*;
import tester.Tester;

public class Testing implements Constants {

    // examples of the Dodger class
    Dodger d1 = new Dodger(new Posn(300, 300), dRADIUS, dCOLOR);
    Dodger d1Left = new Dodger(new Posn(260, 300), dRADIUS, dCOLOR);
    Dodger d1Right = new Dodger(new Posn(340, 300), dRADIUS, dCOLOR);
    Dodger d1Up = new Dodger(new Posn(300, 260), dRADIUS, dCOLOR);
    Dodger d1Down = new Dodger(new Posn(300, 340), dRADIUS, dCOLOR);
    Dodger d1LeftUp = new Dodger(new Posn(260, 260), dRADIUS, dCOLOR);
    Dodger d1UpRight = new Dodger(new Posn(340, 260), dRADIUS, dCOLOR);
    Dodger d1RightDown = new Dodger(new Posn(340, 340), dRADIUS, dCOLOR);
    Dodger d1DownLeft = new Dodger(new Posn(260, 340), dRADIUS, dCOLOR);

    Dodger d2 = new Dodger(new Posn(-20, 300), dRADIUS, dCOLOR);
    Dodger d3 = new Dodger(new Posn(620, 300), dRADIUS, dCOLOR);
    Dodger d4 = new Dodger(new Posn(300, -20), dRADIUS, dCOLOR);
    Dodger d5 = new Dodger(new Posn(300, 620), dRADIUS, dCOLOR);

    Dodger d0 = new Dodger(new Posn(20, 20), dRADIUS, dCOLOR);

    // examples of the Thing class
    Thing t1 = new Thing(new Posn(300, 300), tRADIUS, 1, 0, tCOLOR);
    Thing t1Right = new Thing(new Posn(340, 300), tRADIUS, 1, 0, tCOLOR);
    Thing t1Right2 = new Thing(new Posn(380, 300), tRADIUS, 1, 0, tCOLOR);
    Thing t2 = new Thing(new Posn(300, 300), tRADIUS, 0, 1, tCOLOR);
    Thing t2Down = new Thing(new Posn(300, 340), tRADIUS, 0, 1, tCOLOR);
    Thing t2Down2 = new Thing(new Posn(300, 380), tRADIUS, 0, 1, tCOLOR);

    Thing t3 = new Thing(new Posn(620, 300), tRADIUS, 1, 0, tCOLOR);
    Thing t4 = new Thing(new Posn(300, 620), tRADIUS, 0, 1, tCOLOR);

    // examples of the Explosion class
    Explosion e0 = new Explosion(new Posn(-60, -60), eRADIUS, eCOLOR);
    Explosion e1 = new Explosion(new Posn(300, 300), eRADIUS, eCOLOR);

    // examples of the DodgeWorld class
    DodgeWorld dw1 = new DodgeWorld(d1, t1, t2, e0, 0, 0);
    DodgeWorld dw1Left = new DodgeWorld(d1Left, t1, t2, e0, 0, 0);
    DodgeWorld dw1Right = new DodgeWorld(d1Right, t1, t2, e0, 0, 0);
    DodgeWorld dw1Up = new DodgeWorld(d1Up, t1, t2, e0, 0, 0);
    DodgeWorld dw1Down = new DodgeWorld(d1Down, t1, t2, e0, 0, 0);

    DodgeWorld dw2 = new DodgeWorld(d2, t1, t2, e0, 0, 0);
//    DodgeWorld dw3 = new DodgeWorld(d1, t3, t4, e0, 0, 0);
//    DodgeWorld dw3Tick = new DodgeWorld(d1, t5, t6, e0, 1, 0);
    DodgeWorld dw4 = new DodgeWorld(d1, t1, t2Down, e0, 0, 0);
    DodgeWorld dw5 = new DodgeWorld(d1, t1Right, t2, e0, 0, 0);
    DodgeWorld dw6 = new DodgeWorld(d1Left, t1Right, t2Down, e0, 0, 0);
    DodgeWorld dw6Tick = new DodgeWorld(d1Left, t1Right2, t2Down2, e0, 0, 0);
    DodgeWorld dw7 = new DodgeWorld(d0, t1, t2, e0, 0, 0);
    DodgeWorld dw7Tick = new DodgeWorld(d0, t1Right, t2Down, e1, 0, 0);
    DodgeWorld dw8 = new DodgeWorld(d1, t1, t2, e1, 0, 0);

    // Test method moveDodger in Dodger class
    boolean testMoveDodger(Tester t) {
        return t.checkExpect(this.d1.moveDodger("left"),
                this.d1Left, "test moveDodger - Left " + "\n")
                && t.checkExpect(this.d1.moveDodger("right"),
                        this.d1Right, "test moveDodger - Right " + "\n")
                && t.checkExpect(this.d1.moveDodger("up"),
                        this.d1Up, "test moveDodger - Up " + "\n")
                && t.checkExpect(this.d1.moveDodger("down"),
                        this.d1Down, "test moveDodger - Down " + "\n");
    }

    // Test method moveThing in Thing class
    boolean testMoveThing(Tester t) {
        return t.checkExpect(this.t1.moveThing(),
                this.t1Right, "test moveThing - X Move " + "\n")
                && t.checkExpect(this.t2.moveThing(),
                        this.t2Down, "test moveThing - Y Move " + "\n");
    }

    // Test didCollide methods in Dodger class
    boolean testDidCollide(Tester t) {
        return t.checkExpect(this.d1.didCollide(t1),
                true, "test didCollide  - thing1 " + "\n")
                && t.checkExpect(this.d1.didCollide(t2),
                        true, "test didCollide - thing2 " + "\n")
                && t.checkExpect(this.d1Right.didCollide(t1Right),
                        true, "test didCollide - thing1Right " + "\n")
                && t.checkExpect(this.d1Down.didCollide(t2Down),
                        true, "test didCollide - thing2Down " + "\n")
                && t.checkExpect(this.d1.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1Left.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1Right.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1Up.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1Down.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1LeftUp.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1RightDown.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1UpRight.didCollide(e1),
                        true, "test didCollide - explosion1")
                && t.checkExpect(this.d1DownLeft.didCollide(e1),
                        true, "test didCollide - explosion1");
    }

    // Test method hitThing in Thing class
    boolean testHitThing(Tester t) {
        return t.checkExpect(this.t1.hitThing(t1),
                true, "test hitThing " + "\n")
                && t.checkExpect(this.t2.hitThing(t2),
                        true, "test hitThing " + "\n")
                && t.checkExpect(this.t1.hitThing(t2),
                        true, "test hitThing " + "\n");
    }

    // Test method outOfBounds in Dodger class
    boolean testOutOfBounds(Tester t) {
        return t.checkExpect(this.d2.outOfBounds(wWIDTH, wHEIGHT),
                true, "test outOfBounds - Left " + "\n")
                && t.checkExpect(this.d3.outOfBounds(wWIDTH, wHEIGHT),
                        true, "test outOfBounds - Right " + "\n")
                && t.checkExpect(this.d4.outOfBounds(wWIDTH, wHEIGHT),
                        true, "test outOfBounds - Top " + "\n")
                && t.checkExpect(this.d5.outOfBounds(wWIDTH, wHEIGHT),
                        true, "test outOfBounds - Bottom " + "\n");
    }

    // Test method atBounds in Thing class
    boolean testAtBounds(Tester t) {
        return t.checkExpect(this.t3.atBounds(wWIDTH, wHEIGHT),
                true, "test atBounds - Right " + "\n")
                && t.checkExpect(this.t4.atBounds(wWIDTH, wHEIGHT),
                        true, "test atBounds - Bottom " + "\n");
    }

    // Helper for testRandInt: boolean for if RandInt is in range given
    boolean checkRandInt() {
        int rand = DodgeWorld.randInt(0, 14);
        return (rand <= 14 && rand >= 0);
    }

    // Test method randInt in DodgeWorld class
    boolean testRandInt(Tester t) {
        return t.checkExpect(this.checkRandInt(),
                true, "test RandInt")
                && t.checkExpect(this.checkRandInt(),
                        true, "test RandInt")
                && t.checkExpect(this.checkRandInt(),
                        true, "test RandInt")
                && t.checkExpect(this.checkRandInt(),
                        true, "test RandInt")
                && t.checkExpect(this.checkRandInt(),
                        true, "test RandInt");
    }

    // Test method onKeyEvent in DodgeWorld class
    boolean testOnKeyEvent(Tester t) {
        return t.checkExpect(this.dw1.onKeyEvent("left"),
                this.dw1Left, "test onKeyEvent - Left " + "\n")
                && t.checkExpect(this.dw1.onKeyEvent("right"),
                        this.dw1Right, "test onKeyEvent - Right " + "\n")
                && t.checkExpect(this.dw1.onKeyEvent("up"),
                        this.dw1Up, "test onKeyEvent - Up " + "\n")
                && t.checkExpect(this.dw1.onKeyEvent("down"),
                        this.dw1Down, "test onKeyEvent - Down " + "\n");
    }

    // Test method onTick in DodgeWorld class
    boolean testOnTick(Tester t) {
        return t.checkExpect(this.dw2.onTick(),
                this.dw2.endOfWorld("OutOfBounds"),
                "test onTick - Dodger OoB " + "\n")
                && // An onTick call when things are OoB returns new things at random locations
                // thus that cannot tested in this way.
                //		t.checkExpect(this.dw3.onTick(), 
                //			this.dw3Tick, "test onTick - Things OoB " + "\n") &&
                t.checkExpect(this.dw4.onTick(),
                        this.dw4.endOfWorld("DodgerThingCollision"),
                        "test onTick - Dodger Thing1 Collide " + "\n")
                && t.checkExpect(this.dw5.onTick(),
                        this.dw5.endOfWorld("DodgerThingCollision"),
                        "test onTick - Dodger Thing2 Collide " + "\n")
                && t.checkExpect(this.dw6.onTick(),
                        this.dw6Tick,
                        "test onTick - Normal Move Things " + "\n")
                && t.checkExpect(this.dw7.onTick(),
                        this.dw7Tick,
                        "test onTick - Generate Explosion " + "\n")
                && t.checkExpect(this.dw8.onTick(),
                        this.dw8.endOfWorld("DodgerBlewUp"),
                        "test onTick - Dodger Explosion Collide " + "\n");
    }
}
