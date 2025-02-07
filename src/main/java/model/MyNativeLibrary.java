package model;

import java.util.Random;

public class MyNativeLibrary {
    public static double[] updateTrailValues(double[] trailsValues, double value) {
        trailsValues[3] += 0.1 * (trailsValues[2] - trailsValues[3]);
        trailsValues[2] += 0.1 * (trailsValues[1] - trailsValues[2]);
        trailsValues[1] += 0.1 * (trailsValues[0] - trailsValues[1]);
        trailsValues[0] += 0.1 * (value - trailsValues[0]);
        return trailsValues;
    }

    public static double calculateParabola(double time, double a, double b, double c) {
        return (time * (0.01 * a * time + b) + c) * 0.001;
    }

    public static int divRoundAwayFromZero(double x, double bound) {
        return x < 0 ? (int) Math.floor(x / bound) : (int) Math.ceil(x / bound);
    }

    public static int asmAbs(int x) {
        return Math.abs(x);
    }

    public static double sin(double time, double a, double b, double c) {
        return a * Math.sin(b * time + c);
    }

    public static boolean isBetween(double p, double q, double r) {
        return (p >= q && p <= r);
    }

    public static double makeInBound(double m, double max, double min) {
        return Math.max(Math.min(m, min), max);
    }

    public static double myHypot(double x, double y) {
        return Math.hypot(x, y) * 3;
    }

    public static double makeRandomMovement(double direction, double frac) {
        Random random = new Random();
        return direction * frac * random.nextDouble() * Math.sqrt(random.nextDouble());
    }
}
