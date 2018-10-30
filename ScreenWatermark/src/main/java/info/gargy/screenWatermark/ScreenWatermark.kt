package info.gargy.screenWatermark

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView


/**
 * Used to overlay an activity with a watermark.
 * Call {@link #add()} onCreate or {@link #update()}
 * Call {@link #dispose()} onDestroy
 */
class ScreenWatermark constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.style.OverlayMessageDefaultStyle) : ContextWrapper(context) {

    constructor(context: Context) : this(context, null)

    private var mTextSize = 12
    private var mBackgroundColor = Color.argb(0, 0, 0, 0)
    private var mTextColor = Color.argb(255, 255, 0, 0)
    private var mGravity = Gravity.CENTER
    private var mMessage = ""
    private var tvOverlay: TextView? = null
    private val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)

    init {
        tvOverlay = TextView(this)

        with(obtainStyledAttributes(attrs, R.styleable.ScreenWatermark, R.attr.ScreenOverlayStyle, defStyleAttr)) {
            mTextSize = getInteger(R.styleable.ScreenWatermark_TextSize, mTextSize)
            mTextColor = getColor(R.styleable.ScreenWatermark_TextColor, mTextColor)
            mBackgroundColor = getColor(R.styleable.ScreenWatermark_BackgroundColor, mBackgroundColor)
            mGravity = getInteger(R.styleable.ScreenWatermark_android_gravity, mGravity)

            recycle()
        }
    }

    /**
     * Sets text size in DP
     * @param value
     * @return
     */
    fun textSize(value: Int): ScreenWatermark {
        this.mTextSize = value
        return this
    }

    /**
     * Sets background color
     * @param value
     * @return
     */
    fun backgroundColor(value: Int): ScreenWatermark {
        this.mBackgroundColor = value
        return this
    }

    /**
     * Sets text color
     * @param value
     * @return
     */
    fun textColor(value: Int): ScreenWatermark {
        this.mTextColor = value
        return this
    }

    /**
     * Sets position of message in screen
     * @param gravity
     * @return
     */
    fun gravity(gravity: Int): ScreenWatermark {
        this.mGravity = gravity
        return this
    }

    /**
     * Sets the message to be displayed
     * @param message
     * @return
     */
    fun message(message: String): ScreenWatermark {
        this.mMessage = message
        return this
    }

    /**
     * Sets shows display the watermark on screen
     * @return
     */
    private fun add() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.addView(tvOverlay, params)
    }

    private fun update() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.updateViewLayout(tvOverlay, params)
    }

    /**
     * Shows or updates the watermark on screen
     * @return
     */
    fun show(): ScreenWatermark {
        setProperties()

        if (tvOverlay!!.windowToken == null) {
            add()
        } else {
            update()
        }

        return this
    }

    private fun setProperties() {
        tvOverlay?.apply {
           gravity = Gravity.CENTER
           text = mMessage
           setTextColor(mTextColor)
           setBackgroundColor(mBackgroundColor)
           setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize.toFloat())
        }

        params.gravity = mGravity
    }

    /**
     * Removes displayed message. Always call it on destroy of activity to avoid memory leaks
     */
    fun dispose() {
        if (tvOverlay != null) {
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            //remove immediate because with simple remove textview still leaks window. ???Not fast enough???
            wm.removeViewImmediate(tvOverlay)
            tvOverlay = null
        }
    }
}


