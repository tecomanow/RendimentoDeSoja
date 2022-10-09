package br.com.matreis.rendimentodesoja.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import br.com.matreis.rendimentodesoja.R;


public class BorderImageView extends AppCompatImageView {

    private static final float DEFAULT_RADIUS = 0f;

    private float radius = 0f;
    private Path path;

    public BorderImageView(@NonNull Context context) {
        super(context);

        init(null);
    }

    public BorderImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BorderImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){
        if(attrs == null){
            return;
        }

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BorderImageView);
        radius = ta.getDimension(R.styleable.BorderImageView_borderRound, DEFAULT_RADIUS);
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        path.addRoundRect(rectF,radius,radius,Path.Direction.CW);
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
