package me.mutasem.voiceplayerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import me.mutasem.simplevoiceplayer.PlayerView;

public class MainActivity extends AppCompatActivity {
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player);
        playerView.setAudio(R.raw.bird);
    }

    @Override
    protected void onDestroy() {
        playerView.release();
        super.onDestroy();
    }
}
