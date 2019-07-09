/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_example_yy_computes_Algorithm */

#ifndef _Included_com_example_yy_computes_Algorithm
#define _Included_com_example_yy_computes_Algorithm
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_yy_computes_Algorithm
 * Method:    FFT_int_JNI
 * Signature: (I[I[F)[I
 */
JNIEXPORT void JNICALL Java_com_example_yy_computes_Algorithm_FFT_1int_1JNI
  (JNIEnv *, jclass, jint, jintArray, jintArray);

/*
 * Class:     com_example_yy_computes_Algorithm
 * Method:    FFT_float_JNI
 * Signature: (I[F[F)[F
 */
JNIEXPORT void JNICALL Java_com_example_yy_computes_Algorithm_FFT_1float_1JNI
  (JNIEnv *, jclass, jint, jfloatArray, jfloatArray);

/*
 * Class:     com_example_yy_computes_Algorithm
 * Method:    mix_int_JNI
 * Signature: ([I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_example_yy_computes_Algorithm_mix_1int_1JNI
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_example_yy_computes_Algorithm
 * Method:    mix_float_JNI
 * Signature: ([F)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_example_yy_computes_Algorithm_mix_1float_1JNI
  (JNIEnv *, jclass, jfloatArray);

#ifdef __cplusplus
}
#endif
#endif