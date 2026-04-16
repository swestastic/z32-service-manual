# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep PDF viewer classes
-keep class com.github.barteksc.pdfviewer.** { *; }
-dontwarn com.github.barteksc.pdfviewer.**

# Keep application classes
-keep class com.z32.servicemanual.** { *; }
