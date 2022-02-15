package com.thanguit.tiushop.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundCornerImage extends androidx.appcompat.widget.AppCompatImageView {
    private static float radius = 10f;

    public RoundCornerImage(@NonNull Context context) {
        super(context);
    }

    public RoundCornerImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundCornerImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) { // https://stackoverflow.com/a/40150715
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        Path path = new Path();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
