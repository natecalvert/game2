package game2;

import javalib.worldimages.*;
import tester.Tester;

public class Game2 implements Constants {

    public static void main(String[] args) {

        Testing test = new Testing();
        Tester.runReport(test, false, false);

        DodgeWorld w1 = new DodgeWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT / 2), dRADIUS, dCOLOR),
                new Thing(new Posn(tRADIUS, tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2),
                        tRADIUS, 1, 0, tCOLOR),
                new Thing(new Posn(tRADIUS + DodgeWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                        tRADIUS, 0, 1, tCOLOR),
                new Explosion(new Posn(-eRADIUS, -eRADIUS), eRADIUS, eCOLOR), 0, 3);
        w1.bigBang(wWIDTH, wHEIGHT, 0.07);
        
        FallWorld w2 = new FallWorld(new Dodger(new Posn(wWIDTH / 2, wHEIGHT - 20), dRADIUS, dCOLOR),
                new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                        tRADIUS, 0, 1, tCOLOR),
                new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                        tRADIUS, 0, 1, tCOLOR),
                new Thing(new Posn(tRADIUS + FallWorld.randInt(0, 14) * dRADIUS * 2, tRADIUS),
                        tRADIUS, 0, 1, tCOLOR), 0, 1);
        w2.bigBang(wWIDTH, wHEIGHT, 0.04);
    }
}
