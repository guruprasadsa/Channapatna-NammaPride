# Jetpack Compose
-keepclassmembers class * extends androidx.compose.ui.node.RootForTest { *; }

# Firebase Firestore
-keep class com.google.firebase.firestore.** { *; }
-keep class com.google.firebase.common.** { *; }

# Keeping Data Models (MVVM)
-keep class com.example.channapatna_namma_pride.data.models.** { *; }

# Standard Proguard Rules
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes Signature
-keepattributes *Annotation*