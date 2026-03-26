# ============================================================
# Android framework — keep rules required by the OS
# ============================================================

# Keep Activity subclasses (referenced by name in AndroidManifest)
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep View constructors used by XML layouts
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep onClick methods referenced from XML layouts (android:onClick="...")
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable implementations
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep enums (used for Card.Rank and Card.Suit)
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============================================================
# AndroidX / Material
# ============================================================
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class com.google.android.material.** { *; }
-dontwarn androidx.**
-dontwarn com.google.android.material.**

# ============================================================
# App-specific — preserve game model classes
# ============================================================

# Card model: used via enum reflection and direct field access
-keep class com.apps.asritha.fivecards.Card { *; }
-keep class com.apps.asritha.fivecards.Card$Rank { *; }
-keep class com.apps.asritha.fivecards.Card$Suit { *; }

# ============================================================
# Obfuscation & optimisation settings
# ============================================================

# Rename packages to short names for stronger obfuscation
-repackageclasses 'o'

# Allow aggressive optimisation passes
-optimizationpasses 5
-allowaccessmodification

# Strip debug info from release build
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Remove all Log calls in release
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}
