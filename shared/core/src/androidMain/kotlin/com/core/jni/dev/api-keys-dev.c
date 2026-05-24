#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_core_data_utils_NativeHost_1androidKt_url(JNIEnv *env, jclass clazz) {
    // return (*env)->NewStringUTF(env, "http://10.0.2.2:8080/");

    return (*env)->NewStringUTF(env, "http://155.212.138.228:8080/");
}