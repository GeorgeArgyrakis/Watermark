package info.gargy.screenWatermark;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Used to overlay an activity with a watermark.
 * Call {@link #add()} onCreate or {@link #update()}
 * Call {@link #dispose()} onDestroy
 */
public class ScreenWatermark extends ContextWrapper {

    private int mTextSize = 12;
    private int mBackgroundColor = Color.argb(0, 0, 0, 0);
    private int mTextColor = Color.argb(255, 255, 0, 0);
    private int mGravity = Gravity.CENTER;
    private String mMessage = "";
    private TextView tvOverlay;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT);

    public ScreenWatermark(Context context) {
        this(context, null);
    }

    public ScreenWatermark(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.OverlayMessageDefaultStyle);
    }

    public ScreenWatermark(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyleAttr) {
        tvOverlay = new TextView(this);

        final TypedArray a = obtainStyledAttributes(attrs, R.styleable.ScreenWatermark, R.attr.ScreenOverlayStyle, defStyleAttr);
        mTextSize = a.getInteger(R.styleable.ScreenWatermark_TextSize, mTextSize);
        mTextColor = a.getColor(R.styleable.ScreenWatermark_TextColor, mTextColor);
        mBackgroundColor = a.getColor(R.styleable.ScreenWatermark_BackgroundColor, mBackgroundColor);
        mGravity = a.getInteger(R.styleable.ScreenWatermark_android_gravity, mGravity);

        a.recycle();
    }

    /**
     * Sets text size in DP
     * @param value
     * @return
     */
    public ScreenWatermark textSize(int value) {
        this.mTextSize = value;
        return this;
    }

    /**
     * Sets background color
     * @param value
     * @return
     */
    public ScreenWatermark backgroundColor(int value) {
        this.mBackgroundColor = value;
        return this;
    }

    /**
     * Sets text color
     * @param value
     * @return
     */
    public ScreenWatermark textColor(int value) {
        this.mTextColor = value;
        return this;
    }

    /**
     * Sets position of message in screen
     * @param gravity
     * @return
     */
    public ScreenWatermark gravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }
    /**
     * Sets the message to be displayed
     * @param message
     * @return
     */
    public ScreenWatermark message(String message) {
        this.mMessage = message;
        return this;
    }
    /**
     * Sets shows display the watermark on screen
     * @return
     */
    private void add() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(tvOverlay, params);
    }

    private void update() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.updateViewLayout(tvOverlay, params);
    }

    /**
     * Shows or updates the watermark on screen
     * @return
     */
    public ScreenWatermark show() {
        setProperties();
        if (tvOverlay.getWindowToken() == null) {
            add();
        } else {
            update();
        }
        return this;
    }

    private void setProperties() {
        tvOverlay.setGravity(Gravity.CENTER);
        tvOverlay.setText(mMessage);
        tvOverlay.setTextColor(mTextColor);
        tvOverlay.setBackgroundColor(mBackgroundColor);
        tvOverlay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        params.gravity = mGravity;
    }
    /**
     * Removes displayed message. Always call it on destroy of activity to avoid memory leaks
     */
    public void dispose() {
        if (tvOverlay != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            //remove immediate because with simple remove textview still leaks window. ???Not fast enough???
            wm.removeViewImmediate(tvOverlay);
            tvOverlay = null;
        }
    }
}


