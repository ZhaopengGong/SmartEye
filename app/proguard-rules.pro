# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
#Gson混淆配置
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.** { *; }
-keep class com.xx.duqian_cloud.JavaScriptInterface { *; }#webview js


#忽略 libiary 混淆
-keep class io.vov.vitamio.** { *; }

-keep class com.lidroid.xutils.** { *; }
-keep public class * extends com.lidroid.xutils.**
-keepattributes Signature
-keepattributes *Annotation*
-keep public interface com.lidroid.xutils.** {*;}
-keep class * extends java.lang.annotation.Annotation { *; }
-dontwarn com.lidroid.xutils.**

-keepclasseswithmembernames class ** {
    native <methods>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-dontoptimize
-dontwarn InnerClasses

-dontwarn org.apache.http.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.
-keep class com.tencent.** { *; }
-keep class com.tencent.mm.** { *; }
-keep class com.umeng.socialize.** { *; }
-keep class com.android.volley.** { *; }
-keep class com.sina.** { *; }
-keep class com.desarrollodroide.libraryfragmenttransactionextended.** { *; }
-keep class com.rt.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.zxing.** { *; }
-keep class net.sourceforge.zbar.** { *; }
-keep class com.nineoldandroids.** { *; }
-keep class com.lidroid.** { *; }
-keep class com.android.volley.**{*;}
-keep class cn.edu.bjut.sipl.smarteye.data.** { *; } #实体类不参与混淆
-keep class * extends java.lang.annotation.Annotation { *; }
-keep class android.support.v4.view.**{ *;}
-keep class lecho.lib.hellocharts.**{ *;}
-dontwarn android.support.v4.**
-dontwarn android.support.v4.view.**
-dontwarn com.alibaba.fastjson.support.spring.**
-dontwarn com.sina.weibo.sdk.net.**
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-dontwarn com.tencent.stat.**
-dontwarn com.alibaba.fastjson.serializer.**
-dontwarn com.alibaba.fastjson.support.spring.**
-dontwarn com.alibaba.fastjson.util.**
-dontwarn com.tencent.connect.avatar.c
-dontwarn com.android.volley.toolbox.**
-dontwarn com.desarrollodroide.libraryfragmenttransactionextended.**
-dontwarn com.sina.weibo.sdk.component.**
-dontwarn com.sina.weibo.sdk.net.**
-dontwarn com.tencent.open.**
-dontwarn com.tencent.stat.**
-dontwarn com.tencent.utils.**
-dontwarn com.tencent.weiyun.**
-dontwarn com.umeng.socialize.**
-keepattributes Signature #范型

# 避免影响升级功能，需要keep住support包的类
-keep class android.support.**{*;}


#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
