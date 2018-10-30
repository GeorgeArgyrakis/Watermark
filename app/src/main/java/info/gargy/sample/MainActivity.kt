package info.gargy.sample

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import info.gargy.screenWatermark.ScreenWatermark
import java.text.SimpleDateFormat
import java.util.*

internal class MainActivity : AppCompatActivity() {

    private lateinit var watermark1: ScreenWatermark
    private lateinit var watermark2: ScreenWatermark
    private lateinit var watermark3: ScreenWatermark
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        watermark1 = ScreenWatermark(this).apply {
            backgroundColor(Color.argb(100, 0, 0, 255))
            textColor(Color.argb(150, 255, 186, 102))
            textSize(22)
            gravity(Gravity.TOP or Gravity.END)
        }

        timer = object : CountDownTimer(1000000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                watermark1.message("Watermark: ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}").show()
            }
            override fun onFinish() {}
        }.start()

        watermark2 = ScreenWatermark(this).apply {
            backgroundColor(Color.argb(50, 255, 255, 0))
            message("Watermark: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())}")
            show()
        }

        watermark3 = ScreenWatermark(this, null, R.style.ScreenWatermark).apply {
            message("Watermark:${BuildConfig.BUILD_TYPE} ${BuildConfig.VERSION_NAME}")
            show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        watermark1.dispose()
        watermark2.dispose()
        watermark3.dispose()
        timer.cancel()
    }
}
