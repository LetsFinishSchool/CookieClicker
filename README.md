# CookieClicker

Enjoy playing!

How to build from source:

Clone git repo:

``` git clone https://github.com/LetsFinishSchool/CookieClicker.git ```

Open Cookie Clicker directory:

``` cd CookieClicker ```

If you want to build for android, you need to install an android sdk and point the project to the directory. To do that, create a file called 'locale.properties' and
add the line 'sdk.dir=REPLACE_WITH_PATH_TO_SDK'

after that you can run

``` ./gradlew --stacktrace --info android:assembleRelease ```

you can find the unsigned apk in 'CookieClicker/android/build/outputs/apk/release'. Before you can install that on your device, you need to digitally sign the file

if you want to build for desktop you can either generate a jar file or a native system installer with bundeled jdk (only for your current os)

Jar:

``` ./gradlew --stacktrace --info desktop:dist```

output: 'CookieClicker/desktop/build/lib'

Installer:

``` ./gradlew --stacktrace --info desktop:jpackage```

output: 'CookieClicker/desktop/build/jpackage'

If you want to compile to html5, run:

``` ./gradlew --stacktrace --info html:dist ```

output is in 'CookieClicker/html/build/dist'
