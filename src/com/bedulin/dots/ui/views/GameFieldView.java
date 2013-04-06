package com.bedulin.dots.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.bedulin.dots.R;
import com.bedulin.dots.ui.constants.GameConstants;

/**
 * @author Alexandr Bedulin
 */
public class GameFieldView extends View {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final String LOG = "T_" + GameFieldView.class.getName();
    public final int POINT_RADIUS;
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;

    // ===========================================================
    // Fields
    // ===========================================================
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint, mBitmapPaint;
    private SharedPreferences mSharedPreference;

    // ===========================================================
    // Constructors
    // ===========================================================
    public GameFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        Log.d(LOG, "H:" + dm.heightPixels + " W:" + SCREEN_WIDTH);

        mBitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        //определяем параметры кисти, которой будем рисовать сетку и атомы
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setColor(getResources().getColor(R.color.light_blue));
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        //            TODO cells
//        final int CELLS_IN_H = Integer.parseInt(mSharedPreference.getString(MenuAndPrefsConstants.PREFERENCE_FIELD_CELLS_IN_HEIGHT, "2"));
//        final int CELLS_IN_W = Integer.parseInt(mSharedPreference.getString(MenuAndPrefsConstants.PREFERENCE_FIELD_CELLS_IN_WIDTH, "1"));

        Log.d(LOG, "cells_in_h: " + GameConstants.CELLS_IN_HEIGHT + "cells_in_w: " + GameConstants.CELLS_IN_WIDTH);

        final int CELL_SIZE = Math.min(SCREEN_HEIGHT, SCREEN_WIDTH) / Math.max(GameConstants.CELLS_IN_HEIGHT, GameConstants.CELLS_IN_WIDTH);
        for (int x = 0; x < GameConstants.CELLS_IN_WIDTH + 1; x++)
            mCanvas.drawLine(
                    (float) x * CELL_SIZE, 0,
                    (float) x * CELL_SIZE, SCREEN_HEIGHT,
                    mPaint);
        for (int y = 0; y < GameConstants.CELLS_IN_WIDTH + 1; y++)
            mCanvas.drawLine(
                    0, (float) y * CELL_SIZE,
                    SCREEN_WIDTH, (float) y * CELL_SIZE,
                    mPaint);

        POINT_RADIUS = CELL_SIZE / 7;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            final float X = event.getX();
            final float Y = event.getY();
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(X, Y, POINT_RADIUS, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawCircle(X, Y, POINT_RADIUS * 3, mPaint);
        return super.onTouchEvent(event);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    //переводим dp в пиксели
    public float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
