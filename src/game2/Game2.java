package game2;

import javalib.worldimages.*;
import tester.Tester;

public class Game2 implements Constants {

    public static void main(String[] args) {
        
        Testing test = new Testing();
        Tester.runReport(test, false, false);
        
        DodgeWorld w1 = new DodgeWorld(new Dodger(new Posn(300, 300), dRADIUS, dCOLOR),
                new Thing(new Posn(20, 20 + DodgeWorld.randInt(0, 14) * 40),
                        tRADIUS, 1, 0, tCOLOR),
                new Thing(new Posn(20 + DodgeWorld.randInt(0, 14) * 40, 20),
                        tRADIUS, 0, 1, tCOLOR),
                new Explosion(new Posn(-60, -60), eRADIUS, eCOLOR), 0);
        w1.bigBang(wWIDTH, wHEIGHT, 0.07);
    }
}
