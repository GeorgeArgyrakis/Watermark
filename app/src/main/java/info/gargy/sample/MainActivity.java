package info.gargy.sample;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.gargy.screenWatermark.ScreenWatermark;

public class MainActivity extends AppCompatActivity {

    private ScreenWatermark Watermark1;
    private ScreenWatermark Watermark2;
    private ScreenWatermark Watermark3;
    private CountDownTimer newtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Watermark1 = new ScreenWatermark(this)
                            .backgroundColor(Color.argb(100,0,0,255))
                            .textColor(Color.argb(150,255,186,102))
                            .textSize(22)
                            .gravity(Gravity.TOP | Gravity.RIGHT);
        newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                Watermark1.message("Watermark: "+(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()))).show();
            }
            public void onFinish() {

            }
        };
        newtimer.start();
        Watermark2 = new ScreenWatermark(this)
                .backgroundColor(Color.argb(50,255,255,0))
                .message("Watermark: "
                       + new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())
                        )
                .show();
        ;
        Watermark3 = new ScreenWatermark(this, null, R.style.ScreenWatermark)
                .message("Watermark:"
                        + BuildConfig.BUILD_TYPE
                        + " "
                        + BuildConfig.VERSION_NAME
                         )
                .show()
                ;


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Watermark1 != null) {
            Watermark1.dispose();
        }
        if (Watermark2 != null) {
            Watermark2.dispose();
        }
        if (Watermark3 != null) {
            Watermark3.dispose();
        }
        newtimer.cancel();
    }
}
