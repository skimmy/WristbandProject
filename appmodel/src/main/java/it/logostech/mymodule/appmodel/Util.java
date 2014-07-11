package it.logostech.mymodule.appmodel;

import java.util.Random;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class Util {
    private static Random rand = new Random(System.currentTimeMillis());
    public static long getRandomId() {
        return rand.nextLong();
    }
}
