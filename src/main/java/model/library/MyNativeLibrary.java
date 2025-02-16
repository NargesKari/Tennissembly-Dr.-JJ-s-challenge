package model.library;
public class MyNativeLibrary {
    static {
        System.loadLibrary("MyNative"); // بارگذاری کتابخانه native
    }
    public native static double[] updateTrailValues(double[] trailsValues, double value);
    public native static double[] calculateParabola(double time, double a, double b, double c,double speed);
    public native static int divRoundAwayFromZero(double x, double bound);
    public native static int asmAbs(int x);
    public native static double sin(double time, double a, double b, double c);
    public native static boolean isBetween(double p, double q, double r);
    public native static double makeInBound(double m, double min, double max);
    public native static double myHypot(double x, double y);
    public native static double makeRandomMovement(double direction, double frac);
    public native static boolean isKeyPressed(int keyCode);
    public static void main(String[] args) {
    }

}