package tests;

import java.util.ArrayList;
import java.util.Random;

import static model.library.MyNativeLibrary.*;
import static tests.PreviousLibrary.*;

public class TestTime {
    private static final int iterations = 1000;  // تعداد اجرا برای دقت بیشتر

    public static void main(String[] args) {
        checkABS();
        checkIsBetween();
        checkMakeRandom();
        checkMakeInBound();
        checkMyHypot();
        checkCalculateParabola();
        checkUpdateTrailValues();
        checkSin();
        checkDivRoundAwayFromZero();
    }

    private static void checkABS() {
        long total1 = 0, total2 = 0;
        ArrayList<Integer> randomInts = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            randomInts.add(rand.nextInt(iterations + 1) - 500); // عدد تصادفی بین -500 تا 500
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            asmAbs0(randomInts.get(i));
            asmAbs(randomInts.get(i));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            asmAbs0(randomInts.get(i));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            asmAbs(randomInts.get(i));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check ABS----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", (double) total2 / (double) total1);
    }

    private static void checkIsBetween() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 3 * iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            isBetween0(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            isBetween(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            isBetween(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            isBetween0(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check IS Between----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkMakeRandom() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
            randomDoubles.add(rand.nextDouble(iterations));
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            makeRandomMovement(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            makeRandomMovement0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            makeRandomMovement0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            makeRandomMovement(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Make Random Movement----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkMakeInBound() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 3 * iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            makeInBound0(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            makeInBound(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            makeInBound0(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            makeInBound(randomDoubles.get(3 * i), randomDoubles.get(3 * i + 1), randomDoubles.get(3 * i + 2));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Make In Bound----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkMyHypot() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            myHypot0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            myHypot(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            myHypot0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            myHypot(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check My Hypot----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkCalculateParabola() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 5 * iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            calculateParabola0(randomDoubles.get(5 * i), randomDoubles.get(5 * i + 1), randomDoubles.get(5 * i + 2), randomDoubles.get(5 * i + 3), randomDoubles.get(5 * i + 4));
            calculateParabola(randomDoubles.get(5 * i), randomDoubles.get(5 * i + 1), randomDoubles.get(5 * i + 2), randomDoubles.get(5 * i + 3), randomDoubles.get(5 * i + 4));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            calculateParabola(randomDoubles.get(5 * i), randomDoubles.get(5 * i + 1), randomDoubles.get(5 * i + 2), randomDoubles.get(5 * i + 3), randomDoubles.get(5 * i + 4));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            calculateParabola0(randomDoubles.get(5 * i), randomDoubles.get(5 * i + 1), randomDoubles.get(5 * i + 2), randomDoubles.get(5 * i + 3), randomDoubles.get(5 * i + 4));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Calculate Parabola----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkUpdateTrailValues() {
        long total1 = 0, total2 = 0;
        ArrayList<double[]> randomDoublesArray = new ArrayList<>();
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            randomDoublesArray.add(new double[]{rand.nextDouble(1000), rand.nextDouble(1000), rand.nextDouble(1000), rand.nextDouble(1000)});
            randomDoubles.add(rand.nextDouble(1));
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            updateTrailValues0(randomDoublesArray.get(i), randomDoubles.get(i));
            updateTrailValues(randomDoublesArray.get(i), randomDoubles.get(i));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            updateTrailValues0(randomDoublesArray.get(i), randomDoubles.get(i));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            updateTrailValues(randomDoublesArray.get(i), randomDoubles.get(i));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Trail Values----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

    private static void checkSin() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 4 * iterations; i++) {
            randomDoubles.add(rand.nextDouble(10) - 10);
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            sin0(randomDoubles.get(4 * i), randomDoubles.get(4 * i + 1), randomDoubles.get(4 * i + 2), randomDoubles.get(4 * i + 3));
            sin(randomDoubles.get(4 * i), randomDoubles.get(4 * i + 1), randomDoubles.get(4 * i + 2), randomDoubles.get(4 * i + 3));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            sin(randomDoubles.get(4 * i), randomDoubles.get(4 * i + 1), randomDoubles.get(4 * i + 2), randomDoubles.get(4 * i + 3));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            sin0(randomDoubles.get(4 * i), randomDoubles.get(4 * i + 1), randomDoubles.get(4 * i + 2), randomDoubles.get(4 * i + 3));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Sin----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - ((double) total2 / (double) total1));
    }

    private static void checkDivRoundAwayFromZero() {
        long total1 = 0, total2 = 0;
        ArrayList<Double> randomDoubles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            randomDoubles.add(rand.nextDouble(iterations + 1) - 500);
            randomDoubles.add(rand.nextDouble(iterations));
        }
// گرم کردن JVM
        for (int i = 0; i < 100; i++) {
            divRoundAwayFromZero(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            divRoundAwayFromZero0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
        }
// اندازه‌گیری تابع اول
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            divRoundAwayFromZero0(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total1 += (end - start);
        }
// اندازه‌گیری تابع دوم
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            divRoundAwayFromZero(randomDoubles.get(2 * i), randomDoubles.get(2 * i + 1));
            long end = System.nanoTime();
            total2 += (end - start);
        }
        System.out.println("----------Check Div Round Away From Zero----------");
        System.out.println("JAVA Avg: " + (total1 / iterations) + " ns");
        System.out.println("C+ASM Avg: " + (total2 / iterations) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) total2 / (double) total1);
    }

}

