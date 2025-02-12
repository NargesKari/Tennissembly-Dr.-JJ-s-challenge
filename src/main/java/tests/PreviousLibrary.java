package tests;

import java.util.Random;

public class PreviousLibrary {
    public static double[] updateTrailValues0(double[] trailsValues, double value) {
        trailsValues[3] += 0.1 * (trailsValues[2] - trailsValues[3]);
        trailsValues[2] += 0.1 * (trailsValues[1] - trailsValues[2]);
        trailsValues[1] += 0.1 * (trailsValues[0] - trailsValues[1]);
        trailsValues[0] += 0.1 * (value - trailsValues[0]);
        return trailsValues;
    }

    public static double[] calculateParabola0(double time, double a, double b, double c, double speed) {
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (time * (a * time + b) + c) * 0.001;
            time += speed;
        }
        return result;
    }

    public static int divRoundAwayFromZero0(double x, double bound) {
        return x < 0 ? (int) Math.floor(x / bound) : (int) Math.ceil(x / bound);
    }

    public static int asmAbs0(int x) {
        return Math.abs(x);
    }

    public static double sin0(double time, double a, double b, double c) {
        return a * Math.sin(b * time + c);
    }

    public static boolean isBetween0(double p, double q, double r) {
        return (p >= q && p <= r);
    }

    public static double makeInBound0(double m, double min, double max) {
        return Math.min(Math.max(m, min), max);
    }

    public static double myHypot0(double x, double y) {
        return Math.hypot(x, y) * 3;
    }

    public static double makeRandomMovement0(double direction, double frac) {
        Random random = new Random();
        return direction * frac * random.nextDouble() * Math.sqrt(random.nextDouble());
    }
}

