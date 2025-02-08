#include <jni.h>
#include "model_MyNativeLibrary.h"
#include <math.h>
#include <stdlib.h>

JNIEXPORT jdoubleArray JNICALL Java_model_MyNativeLibrary_updateTrailValues(JNIEnv *env, jobject obj, jdoubleArray trailsValues, jdouble value) {
    jdouble *values = (*env)->GetDoubleArrayElements(env, trailsValues, 0);
    for (int i = 3; i > 0; i--) {
        values[i] += 0.1 * (values[i - 1] - values[i]);
    }
    values[0] += 0.1 * (value - values[0]);
    
    (*env)->ReleaseDoubleArrayElements(env, trailsValues, values, 0);
    return trailsValues;
}

JNIEXPORT jdouble JNICALL Java_model_MyNativeLibrary_calculateParabola(JNIEnv *env, jobject obj, jdouble time, jdouble a, jdouble b, jdouble c) {
    return (time * (0.01 * a * time + b) + c) * 0.001;
}

// ادامه پیاده‌سازی دیگر متدها در اینجا...

JNIEXPORT jint JNICALL Java_model_MyNativeLibrary_divRoundAwayFromZero(JNIEnv *env, jobject obj, jdouble x, jdouble bound) {
    return (x < 0) ? (int)floor(x / bound) : (int)ceil(x / bound);
}

JNIEXPORT jint JNICALL Java_model_MyNativeLibrary_asmAbs(JNIEnv *env, jobject obj, jint x) {
    return abs(x);
}

JNIEXPORT jdouble JNICALL Java_model_MyNativeLibrary_sin(JNIEnv *env, jobject obj, jdouble time, jdouble a, jdouble b, jdouble c) {
    return a * sin(b * time + c);
}

JNIEXPORT jboolean JNICALL Java_model_MyNativeLibrary_isBetween(JNIEnv *env, jobject obj, jdouble p, jdouble q, jdouble r) {
    return (p >= q && p <= r) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jdouble JNICALL Java_model_MyNativeLibrary_makeInBound(JNIEnv *env, jobject obj, jdouble m, jdouble max, jdouble min) {
    return fmax(fmin(m, min), max);
}

JNIEXPORT jdouble JNICALL Java_model_MyNativeLibrary_myHypot(JNIEnv *env, jobject obj, jdouble x, jdouble y) {
    return hypot(x, y) * 3.0;
}

JNIEXPORT jdouble JNICALL Java_model_MyNativeLibrary_makeRandomMovement(JNIEnv *env, jobject obj, jdouble direction, jdouble frac) {
    double randomValue = ((double) rand() / RAND_MAX);
    return direction * frac * randomValue * sqrt(((double) rand() / RAND_MAX));
}