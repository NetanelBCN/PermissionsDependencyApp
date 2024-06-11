package dev.netanelbcn.permissionsdependencyapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton btn;
    private MaterialTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViews();
        Intent intent=new Intent(MainActivity.this, NewActivity.class);
        btn.setOnClickListener(v -> {
             if (isConnectedToWifi(this) && isVolumeZero(this) && isBrightnessMax(this))
                   startActivity(intent);
        });
    }

    private void findViews() {
        btn = findViewById(R.id.main_btn);
        tv = findViewById(R.id.main_login);
    }


        public static boolean isConnectedToWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }

        return false;
    }

    public static boolean isVolumeZero(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager != null) {
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            return currentVolume == 0;
        }

        return false;
    }

    public static boolean isBrightnessMax(Context context) {
        try {
            int brightness = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
            return brightness == 255; // 255 is the maximum value for screen brightness
        } catch (Settings.SettingNotFoundException e) {
            Log.e("exception", "error occurred");
            return false;
        }
    }


}







