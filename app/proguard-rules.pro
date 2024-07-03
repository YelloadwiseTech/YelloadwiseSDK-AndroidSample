##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------End: proguard configuration for Gson  ----------


##---------------Begin: proguard configuration for Retrofit2  ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

##---------------End: proguard configuration for Retrofit2  ----------

##---------------Begin: proguard configuration for okhttp3  ----------

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

##---------------End: proguard configuration for okhttp3  ----------

##---------------Begin: proguard configuration for Yelloadwise Sdk  ----------

# Application classes that will be serialized/deserialized over Gson
-keepclassmembers enum * { *; }
-keep interface ir.yelloadwise.app.NoProguard

-keep interface ir.yelloadwise.app.NoNameProguard
-keep class * implements ir.yelloadwise.app.NoProguard { *; }

-keep enum * implements ir.yelloadwise.app.NoProguard { *; }

-keepnames class * implements ir.yelloadwise.app.NoNameProguard { *; }
-keep class ir.yelloadwise.app.nativeads.YelloadwiseNativeVideoAdLoader$Builder {*;}
-keep class ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerAdLoader$Builder {*;}
-keep interface com.android.vending.billing.IInAppBillingService
-keep class * implements com.android.vending.billing.IInAppBillingService {*;}

-keep class ir.yelloadwise.app.models.** { *; }

-keep class ir.yelloadwise.app.sentry.model.** {*;}

# To Remove Logger Class (Todo: Replace Logger Class with LogUtils)
-assumenosideeffects class ir.yelloadwise.app.logger.Logger {
    public * ;
    public static *** LogDebug(...);
    public static *** LogError(...);
    public static *** LogInfo(...);
    public static *** LogVerbose(...);
    public static *** LogWarn(...);
}

-keep public class com.bumptech.glide.**
