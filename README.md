Project setup:
1) Ensure that you have atleast android 8 or above(imp)
2) Download the APK From this link https://drive.google.com/file/d/1N8n85M-XBKj-tbI8T-2e6QT8v1lF4QSh
3) Enable 3rd party installations from (Developer Options->Enable 3rd party installations)
4) The apk hasn't been published yet, there may be chances that the android os may flag it as malicious , but its safe, continue with the installation 
5) after the installation , you may search for the stock/equity name directly (airbus, dassault etc etc) , no need to remember symbols
6) the Ui incorporates both the symbol and the stock name for more accessibility and ease of use.

Project demo video:
https://github.com/user-attachments/assets/47eb812a-e3d9-43ec-8299-18998efc6b53

Project Migration/Setup(for manually running the project):

1) Clone this project in a separate folder
2) Create a new project in android studio with an empty activity and compose
3) Import all the explicitly mentioned dependencies in the app.build.gradle.kts and sync the build
4) Get your api key from https://www.alphavantage.co/support/#api-key
5) in the apiOverview.kt file , replace BuildConfig.AlphaVantageKey with a string that encloses your api key
6) Enable Internet permissions in your new projects Manifest file(important)
7) proceed to build and run the project on an AVD or any other android device


   




