# ScreenWatermark
A simple way to add watermark on activities

![Alt text](screenshot.png?raw=true "FloatingKeaboardView Demo")

## Usage Example
``` 
@Override
protected void onCreate(Bundle savedInstanceState) {
...
    Watermark = new ScreenWatermark(this)
                     .backgroundColor(Color.argb(100,0,0,255))
                     .textColor(Color.argb(150,255,186,102))
                     .textSize(22)
                     .gravity(Gravity.TOP | Gravity.RIGHT)
                     .message("Watermark")
                     .show();'
...
}

@Override
protected void onDestroy() {
    super.onDestroy();
    // Always call this on destroy of activity to avoid memory leaks
    if (Watermark != null) {
         Watermark.dispose();
    }
}
    
```                        
