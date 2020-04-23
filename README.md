# SimpleVoicePlayerView ![Release](https://jitpack.io/v/mutasemhajhasan/SimpleVoicePlayerView.svg)

A simple voice player widget for android (like in whatsapp)

# Usage
## gradle
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
	    implementation 'com.github.mutasemhajhasan:SimpleVoicePlayerView:1.0.0'
}
```

## activity.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp"
    tools:context=".MainActivity">

    <me.mutasem.simplevoiceplayer.PlayerView
        android:id="@+id/player"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        app:audioRes="@raw/bird"
        app:pauseIcon="@drawable/pause"
        app:playIcon="@drawable/play" />
</LinearLayout>
```
## activity.java
```java
 PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player);
//        playerView.setAudio(R.raw.bird);
    }

    @Override
    protected void onDestroy() {
        playerView.release();
        super.onDestroy();
    }
```
## License
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
