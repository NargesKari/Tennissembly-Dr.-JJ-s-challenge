#include <jni.h>
#include "model_library_MyNativeLibrary.h"
#include <math.h>
#include <stdlib.h>
#include <windows.h>

JNIEXPORT jdoubleArray JNICALL Java_model_library_MyNativeLibrary_updateTrailValues(JNIEnv *env, jobject obj, jdoubleArray trailsValues, jdouble value) {
    jdouble *values = (*env)->GetDoubleArrayElements(env, trailsValues, 0);
    for (int i = 3; i > 0; i--) {
        values[i] += 0.1 * (values[i - 1] - values[i]);
    }
    values[0] += 0.1 * (value - values[0]);

    (*env)->ReleaseDoubleArrayElements(env, trailsValues, values, 0);
    return trailsValues;
}

JNIEXPORT jdoubleArray JNICALL Java_model_library_MyNativeLibrary_calculateParabola(
    JNIEnv *env, jobject obj, jdouble time, jdouble a, jdouble b, jdouble c, jdouble speed) {
    jdouble result[4] __attribute__((aligned(32)));  
    const double indices[4] __attribute__((aligned(32))) = {0.0, 1.0, 2.0, 3.0};  // ثابت‌های مورد نیاز
    const double scale = 0.001; // مقدار ثابت 0.001 برای ضرب نهایی
    __asm__ volatile (
        // Load initial time into all 4 lanes of YMM0
        "vbroadcastsd %1, %%ymm0\n\t"
        // Create time increments: time, time+speed, time+2*speed, time+3*speed
        "vbroadcastsd %5, %%ymm1\n\t"   // Load speed
        "vmovupd %7, %%ymm2\n\t"        // Load {0,1,2,3} from memory
        "vmulpd %%ymm1, %%ymm2, %%ymm2\n\t"  // Multiply speed with {0,1,2,3}
        "vaddpd %%ymm2, %%ymm0, %%ymm0\n\t"  // Add to initial time
        // Multiply each time value by a
        "vbroadcastsd %2, %%ymm3\n\t"   // Load 'a'
        "vmulpd %%ymm0, %%ymm3, %%ymm3\n\t"
        // Add b to each lane
        "vbroadcastsd %3, %%ymm4\n\t"   // Load 'b'
        "vaddpd %%ymm4, %%ymm3, %%ymm3\n\t"
        // Multiply time by (time * a + b)
        "vmulpd %%ymm0, %%ymm3, %%ymm3\n\t"
        // Add c
        "vbroadcastsd %4, %%ymm5\n\t"   // Load 'c'
        "vaddpd %%ymm5, %%ymm3, %%ymm3\n\t"
        // Final multiplication by 0.001
        "vbroadcastsd %6, %%ymm6\n\t"   // Load 0.001 from memory
        "vmulpd %%ymm6, %%ymm3, %%ymm3\n\t"
        // Store results
        "vmovupd %%ymm3, (%0)\n\t"
        :
        : "r" (result),        // Output array
          "m" (time),          // Initial time
          "m" (a),             // Coefficient a
          "m" (b),             // Coefficient b
          "m" (c),             // Coefficient c
          "m" (speed),         // Speed increment
          "m" (scale),         // Constant 0.001
          "m" (indices)        // Indices {0,1,2,3}
        : "ymm0", "ymm1", "ymm2", "ymm3", "ymm4", "ymm5", "ymm6", "memory"
    );
    // Create Java double array
    jdoubleArray jResult = (*env)->NewDoubleArray(env, 4);
    (*env)->SetDoubleArrayRegion(env, jResult, 0, 4, result);
    return jResult; 
}


JNIEXPORT jint JNICALL Java_model_library_MyNativeLibrary_divRoundAwayFromZero(JNIEnv *env, jobject obj, jdouble x, jdouble bound) {
    jint result;
    if (bound == 0) {
        // Handle division by zero error: If bound is zero, return 0 or throw exception
        return 0; // Alternatively, throw an exception (e.g., jniThrowException)
    }
    __asm__ __volatile__ (
        "movsd %1, %%xmm0\n\t"   // Load x into xmm0 (move x to xmm0 register)
        "movsd %2, %%xmm1\n\t"   // Load bound into xmm1 (move bound to xmm1 register)
        "movsd %3, %%xmm2\n\t"

        "xorpd %%xmm3, %%xmm3\n\t"  // Zero out xmm2 (set xmm2 to 0) using XOR operation (for comparison)
        "comisd %%xmm0, %%xmm3\n\t" // Compare the result (xmm0) with zero (xmm2)
        "ja negative_case\n\t"      // Jump to negative_case if the quotient is negative (below zero)

        // Positive case: rounding away from zero (rounding up)
        "subsd %%xmm2, %%xmm0\n\t"      // Subtract 1.0 from the quotient for rounding (rounding away from zero)
        "divsd %%xmm1, %%xmm0\n\t"  // Divide the result  (optional based on logic, might be redundant)
        "addsd %%xmm2, %%xmm0\n\t"      // Add 1.0 back to the quotient (final adjustment after division)
        "cvttsd2siq %%xmm0, %%rax\n\t" // Convert Scalar Double-Precision Floating-Point to Signed Integer (truncating)
        "movl %%eax, %0\n\t"        // Move the 32-bit integer result into output variable (result)
        "jmp done\n\t"              // Jump to done (skip negative case)

        "negative_case:\n\t"
        // Negative case: rounding away from zero (rounding down)
        "addsd %%xmm2, %%xmm0\n\t"      // Add 1.0 to the quotient (for rounding)
        "divsd %%xmm1, %%xmm0\n\t"  // Divide again (optional)
        "subsd %%xmm2, %%xmm0\n\t"      // Subtract 1.0 from the quotient (final adjustment after division)
        "cvttsd2siq %%xmm0, %%rax\n\t" // Convert the result to a signed integer
        "movl %%eax, %0\n\t"        // Move the result into the output variable (result)

        "done:\n\t" // Label marking the end of the assembly block (end of the calculation)

        : "=r" (result)              // Output: store result in the 'result' variable
        : "x" (x), "x" (bound), "x" (1.0) // Inputs: x, bound, and 1.0 for rounding (as a constant)
        : "xmm0", "xmm1", "xmm2", "xmm3", "rax" // Clobbered registers: xmm0, xmm1, xmm2, xmm3, rax
    );
    return result; // Return the final result to Java
}


JNIEXPORT jint JNICALL Java_model_library_MyNativeLibrary_asmAbs(JNIEnv *env, jobject obj, jint x) {
    jint result;
    __asm__ volatile (
        "movq %1, %%rax\n\t"      // مقدار x را در رجیستر 64 بیتی RAX قرار می‌دهد
        "testq %%rax, %%rax\n\t"  // بررسی می‌کند که عدد منفی است یا نه (اگر منفی باشد، بیت نشانه ۱ است)
        "jns positive\n\t"        // اگر عدد مثبت بود، به لیبل positive پرش می‌کند
        "negq %%rax\n"            // اگر منفی بود، مقدار را درجا منفی می‌کند
        "positive:\n\t"           // لیبلی برای اعداد مثبت یا تغییر یافته
        "movl %%eax, %0\n\t"      // مقدار 32 بیتی (eax) را در متغیر result ذخیره می‌کند
        : "=r" (result)            // خروجی: متغیر result
        : "r" ((long long)x)       // ورودی: مقدار x که به 64 بیت تبدیل شده
        : "%rax"                   // اعلام می‌کند که RAX در این عملیات تغییر می‌کند
    );
    return result;
}

JNIEXPORT jdouble JNICALL Java_model_library_MyNativeLibrary_sin(JNIEnv *env, jobject obj, jdouble time, jdouble a, jdouble b, jdouble c) {
    jdouble result;
    __asm__ volatile (
        "fldl %4\n\t"        // st0 = c FPU  *double-precision floating point
        "fldl %3\n\t"        // st0 = b, st1 = c
        "fldl %2\n\t"        // st0 = time, st1 = b, st2 = c
        "fmulp\n\t"          // st0 = b * time, st1 = c
        "faddp\n\t"          // st0 = b * time + c
        "fsin\n\t"           // st0 = sin(b * time + c)
        "fldl %1\n\t"        // st0 = a, st1 = sin(b * time + c)
        "fmulp\n\t"          // st0 = a * sin(b * time + c)
        "fstpl %0\n\t"       // ذخیره در result
        : "=m" (result)      // خروجی
        : "m" (a), "m" (time), "m" (b), "m" (c)  // ورودی‌ها
        : "%st"              // فقط st تغییر کرده است
    );
    return result;
}


JNIEXPORT jboolean JNICALL Java_model_library_MyNativeLibrary_isBetween(JNIEnv *env, jobject obj, jdouble p, jdouble q, jdouble r) {
    jboolean result = JNI_FALSE;
    __asm__ volatile (
        "movsd %1, %%xmm0\n\t"    // Move p to xmm0
        "movsd %2, %%xmm1\n\t"    // Move q to xmm1
        "movsd %3, %%xmm2\n\t"    // Move r to xmm2
        "ucomisd %%xmm1, %%xmm0\n\t"  // Compare p with q
        "setae %%al\n\t"          // Set AL if p >= q  "Set if Above or Equal"
        "ucomisd %%xmm2, %%xmm0\n\t"  // Compare p with r
        "setbe %%cl\n\t"          // Set CL if p <= r
        "andb %%al, %%cl\n\t"     // Logical AND of conditions
        "movb %%cl, %0\n\t"       // Move result to output
        : "+r" (result)            
        : "x" (p), "x" (q), "x" (r)
        : "%xmm0", "%xmm1", "%xmm2", "%al", "%cl"
    );
    return result;
}

JNIEXPORT jdouble JNICALL Java_model_library_MyNativeLibrary_makeInBound(JNIEnv *env, jobject obj, jdouble m, jdouble min, jdouble max) {
    jdouble result;
    __asm__ volatile (
        "movsd %1, %%xmm0\n\t"      // Load `m` into XMM0
        "movsd %2, %%xmm1\n\t"      // Load `min` into XMM1
        "movsd %3, %%xmm2\n\t"      // Load `max` into XMM2
        // Compare and set minimum bound
        "maxsd %%xmm1, %%xmm0\n\t"  // Use MAXSD for m = max(m, min)
        // Compare and set maximum bound
        "minsd %%xmm2, %%xmm0\n\t"  // Use MINSD for m = min(m, max)
        "movsd %%xmm0, %0\n\t"      // Store final result 
        : "=x" (result)             // Output operand
        : "x" (m), "x" (min), "x" (max)  // Input operands
        : "xmm0", "xmm1", "xmm2"    // Clobbered registers
    );
    return result;
}



JNIEXPORT jdouble JNICALL Java_model_library_MyNativeLibrary_myHypot(JNIEnv *env, jobject obj, jdouble x, jdouble y) {
    jdouble result;    
    __asm__ volatile (
        // Load x and y into XMM registers
        "movsd %1, %%xmm0\n\t"     // Load x into xmm0
        "movsd %2, %%xmm1\n\t"     // Load y into xmm1        
        // Square x
        "mulsd %%xmm0, %%xmm0\n\t" // x * x     
        // Square y
        "mulsd %%xmm1, %%xmm1\n\t" // y * y       
        // Add squared values
        "addsd %%xmm1, %%xmm0\n\t" // x^2 + y^2      
        // Take square root
        "sqrtsd %%xmm0, %%xmm0\n\t" // sqrt(x^2 + y^2)        
        // Multiply by 3.0
        "movsd %3, %%xmm1\n\t"     // Load 3.0
        "mulsd %%xmm1, %%xmm0\n\t" // result * 3.0      
        // Store result
        "movsd %%xmm0, %0\n\t"       
        : "=x" (result)            // Output operand
        : "x" (x), "x" (y), "x" (3.0)  // Input operands
        : "xmm0", "xmm1"           // Clobbered registers
    );   
    return result;
}

JNIEXPORT jdouble JNICALL Java_model_library_MyNativeLibrary_makeRandomMovement(JNIEnv *env, jobject obj, jdouble direction, jdouble frac) {
    jdouble result = 0.0; // مقدار نتیجه اولیه را صفر قرار می‌دهیم
    
    __asm__ volatile (
        "call rand\n\t"             // تولید عدد تصادفی اول
        "cvtsi2sdq %%rax, %%xmm0\n\t"  // تبدیل مقدار صحیح 64 بیتی در RAX به مقدار double در رجیستر
        "divsd %1, %%xmm0\n\t"      // مقدار تصادفی اول را نرمال‌سازی می‌کنیم (بین 0 و 1)
        "mulsd %2, %%xmm0\n\t"      // ضرب مقدار تصادفی در direction
        "mulsd %3, %%xmm0\n\t"      // ضرب مقدار جدید در frac

        "call rand\n\t"             // تولید عدد تصادفی دوم
        "cvtsi2sdq %%rax, %%xmm1\n\t"
        "divsd %1, %%xmm1\n\t"      // مقدار تصادفی دوم را نرمال‌سازی می‌کنیم (بین 0 و 1)
        "mulsd %%xmm1, %%xmm1\n\t"  // گرفتن مربع مقدار تصادفی دوم
        "mulsd %%xmm1, %%xmm0\n\t"  // ضرب مقدار تصادفی قبلی در مربع مقدار جدید

        "movsd %%xmm0, %0\n\t"      // ذخیره‌ی مقدار نهایی در result
        : "+x" (result)  
        : "x" ((double)RAND_MAX), "x" (direction), "x" (frac)
        : "%xmm0", "%xmm1"
    );
    
    return result;
}


JNIEXPORT jboolean JNICALL Java_model_library_MyNativeLibrary_isKeyPressed(JNIEnv *env, jobject obj, jint keyCode) {
	return (GetAsyncKeyState(keyCode) & 0x8000) != 0;
}