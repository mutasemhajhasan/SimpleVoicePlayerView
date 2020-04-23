package me.mutasem.simplevoiceplayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;


public class PlayerView extends RelativeLayout {
    private static final String TAG = "PlayerView";
    ImageButton actionBtn;
    SeekBar progress;
    MediaPlayer mediaPlayer;
    int audioResId = 0;
    private int playIconRes = R.drawable.ic_play;
    private int pauseIconRes = R.drawable.ic_pause;
    MediaListener mediaListener;
    private MediaPlayer.OnCompletionListener completeListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            playing = false;
//            if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
            actionBtn.setImageResource(playIconRes);
            progress.setProgress(0);
//            }
        }
    };
    Runnable handler = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                progress.setProgress(mediaPlayer.getCurrentPosition());
                postDelayed(this, 1000);
            }
        }
    };
    private boolean playing = false;
    private SeekBar.OnSeekBarChangeListener seekParchangeListner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            if (fromUser)
                if (mediaPlayer != null)
                    mediaPlayer.seekTo(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public PlayerView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        inflate(getContext(), R.layout.player_view, this);
        progress = findViewById(R.id.progress);
        progress.setOnSeekBarChangeListener(seekParchangeListner);
        actionBtn = findViewById(R.id.actionBtn);
        playing = false;
        actionBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playing) {
                    pause();
                } else
                    play();
            }
        });
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PlayerView,
                    defStyleAttr, 0);
            try {
                audioResId = a.getResourceId(R.styleable.PlayerView_audioRes, 0);
                playIconRes = a.getInt(R.styleable.PlayerView_playIcon, R.drawable.ic_play);
                pauseIconRes = a.getInt(R.styleable.PlayerView_pauseIcon, R.drawable.ic_pause);
                actionBtn.setImageResource(playIconRes);

                if (audioResId != 0)
                    mediaPlayer = MediaPlayer.create(context, audioResId);
                if (mediaPlayer == null)
                    actionBtn.setEnabled(false);
            } catch (Exception e) {
                Log.e(TAG, "init: ", e);
            } finally {
                a.recycle();
            }
        }
    }

    public void setAudio(String path) {
        try {
            stop();
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(path));
            mediaPlayer.setOnCompletionListener(completeListener);
            progress.setMax(mediaPlayer.getDuration());
            actionBtn.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAudio(Uri uri) {
        try {
            stop();
            mediaPlayer = MediaPlayer.create(getContext(), uri);
            mediaPlayer.setOnCompletionListener(completeListener);
            progress.setMax(mediaPlayer.getDuration());
            actionBtn.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAudio(int audioResId) {
        try {
            stop();
            this.audioResId = audioResId;
            mediaPlayer = MediaPlayer.create(getContext(), audioResId);
            mediaPlayer.setOnCompletionListener(completeListener);
            progress.setMax(mediaPlayer.getDuration());
            actionBtn.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            playing = false;
            actionBtn.setImageResource(playIconRes);
            progress.setProgress(0);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        try {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            playing = false;
            actionBtn.setImageResource(playIconRes);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void play() {
        if (mediaPlayer == null || mediaPlayer.isPlaying())
            return;
        mediaPlayer.start();
        post(handler);
        playing = true;
        actionBtn.setImageResource(pauseIconRes);
    }

    public void release() {
        if (mediaPlayer == null)
            return;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public MediaListener getMediaListener() {
        return mediaListener;
    }

    public void setMediaListener(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
    }

    public interface MediaListener {
        void onProgress(int progress);
    }
}
