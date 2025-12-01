-renamesourcefileattribute SourceFile
-repackageclasses
-optimizationpasses 3
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** d(...);
    public static *** e(...);
}
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class java.lang.invoke.** { *; }
-keep class com.onesignal.** { *; }
-dontwarn org.slf4j.impl.StaticLoggerBinder
