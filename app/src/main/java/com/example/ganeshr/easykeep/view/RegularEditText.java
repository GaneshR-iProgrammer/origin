package com.example.ganeshr.easykeep.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import com.example.ganeshr.easykeep.utils.TypefaceCache;

/**
 * Created by root on 8/9/17.
 */

public class RegularEditText extends android.widget.EditText {

    public RegularEditText(Context context) {
        super(context);
        setFont();

    }

    public RegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RegularEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont();
    }

    /**
     * This method is used to set the given font to the TextView.
     */
    private void setFont() {
        Typeface typeface = TypefaceCache.get(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        setTypeface(typeface);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}