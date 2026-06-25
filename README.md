# أسئلة بصراحة

لعبة أندرويد جماعية باللغة العربية — تُلعب بين الأصدقاء في نفس المكان بدون إنترنت.

## المتطلبات

- Android Studio Hedgehog (2023.1.1) أو أحدث
- JDK 17
- Android SDK 35

## فتح المشروع

1. افتح Android Studio
2. اختر **Open** وحدد مجلد المشروع
3. انتظر حتى ينتهي Gradle Sync

## بناء APK

### من Android Studio

**Build → Build Bundle(s) / APK(s) → Build APK(s)**

الملف سيكون في:
`app/build/outputs/apk/debug/app-debug.apk`

### من سطر الأوامر

```bash
# Windows
gradlew.bat assembleDebug

# Linux / macOS
./gradlew assembleDebug
```

APK للإصدار النهائي:
```bash
gradlew.bat assembleRelease
```

## الميزات

- واجهة عربية كاملة (RTL)
- Material Design 3
- أكثر من 500 سؤال افتراضي
- إضافة / تعديل / حذف أسئلة مخصصة (Room Database)
- الوضع الليلي
- أصوات واهتزاز
- يعمل بدون إنترنت

## طريقة اللعب

1. اضغط **ابدأ اللعبة**
2. اختر عدد اللاعبين (2–20) واكتب الأسماء
3. اضغط **ابدأ**
4. في كل جولة يظهر لاعب عشوائي وسؤال عشوائي
5. يجيب اللاعب أمام الجميع ثم اضغط **التالي**

## التقنيات

- Kotlin
- Jetpack Compose
- Room Database
- DataStore
- Navigation Compose
- Min SDK 24 / Target SDK 35
