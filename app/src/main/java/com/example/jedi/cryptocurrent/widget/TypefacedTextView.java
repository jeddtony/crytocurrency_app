package com.example.jedi.cryptocurrent.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.jedi.cryptocurrent.R;

/**
 * Created by jedi on 12/31/2017.
 */

public class TypefacedTextView extends AppCompatTextView {
    public TypefacedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }
}
