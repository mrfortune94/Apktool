-keep class brut.apktool.Main {
    public static void main(java.lang.String[]);
}
-keep class brut.apktool.MainActivity {
    *;
}
-keepclassmembers enum * {
    static **[] values();
    static ** valueOf(java.lang.String);
}

# Keep all Apktool classes
-keep class brut.** { *; }
-keep class org.xmlpull.** { *; }

# https://github.com/iBotPeaches/Apktool/pull/3670#issuecomment-2296326878
-dontwarn com.google.j2objc.annotations.Weak
-dontwarn com.google.j2objc.annotations.RetainedWith

# Android specific
-dontwarn java.awt.**
-dontwarn javax.swing.**
