package com.bedulin.dots.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.bedulin.dots.Constants;
import com.bedulin.dots.R;

import java.util.ArrayList;

import static com.bedulin.dots.Constants.CELLS_IN_HEIGHT;
import static com.bedulin.dots.Constants.CELLS_IN_WIDTH;

/**
 * @author Alexandr Bedulin
 */
public class GameFieldView extends View {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG = GameFieldView.class.getSimpleName();

    public final int SCREEN_WIDTH;

    public final int SCREEN_HEIGHT;

    // ===========================================================
    // Fields
    // ===========================================================
    private Bitmap mBitmap;

    private Paint mPaint;

    private Paint mBitmapPaint;

    private SharedPreferences mSharedPreference;

    private float mCellSize;

    private float mPointRadius;

    private float mShiftX;

    private float mShiftY;

    private Canvas mCanvas;

    private ArrayList<PointF> mPointPlayerOne;

    private ArrayList<PointF> mPointPlayerTwo;

    // ===========================================================
    // Constructors
    // ===========================================================
    public GameFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        Log.d(LOG, "H:" + SCREEN_HEIGHT + " W:" + SCREEN_WIDTH);

        mCellSize = Math.min(SCREEN_HEIGHT, SCREEN_WIDTH) / Math.max(Constants.CELLS_IN_HEIGHT, CELLS_IN_WIDTH);
        mPointRadius = mCellSize / 7;

        mShiftX = (SCREEN_WIDTH - CELLS_IN_WIDTH * mCellSize) / 2;
        mShiftY = (SCREEN_HEIGHT - CELLS_IN_HEIGHT * mCellSize) / 2;

        mBitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setColor(getResources().getColor(R.color.light_blue));
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPointPlayerOne = new ArrayList<>();
        mPointPlayerTwo = new ArrayList<>();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        mCanvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        for (int x = 0; x < CELLS_IN_WIDTH + 1; x++)
            mCanvas.drawLine(
                    (float) x * mCellSize + mShiftX, 0,
                    (float) x * mCellSize + mShiftX, SCREEN_HEIGHT,
                    mPaint);
        for (int y = 0; y < CELLS_IN_WIDTH + 1; y++)
            mCanvas.drawLine(
                    0, (float) y * mCellSize + mShiftY,
                    SCREEN_WIDTH, (float) y * mCellSize + mShiftY,
                    mPaint);

        mCanvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        mPaint.setStyle(Paint.Style.FILL);
        int length = mPointPlayerOne.size();
        for (int i = 0; i < length; i++) {
            float x = mPointPlayerOne.get(i).x;
            float y = mPointPlayerOne.get(i).y;
            mCanvas.drawCircle(x, y, mPointRadius, mPaint);
//            mPaint.setStyle(Paint.Style.STROKE);
//            mCanvas.drawCircle(x, y, mPointRadius * 3, mPaint);
        }

        length = mPointPlayerTwo.size();
        for (int i = 0; i < length; i++) {
            float x = mPointPlayerOne.get(i).x;
            float y = mPointPlayerOne.get(i).y;
            mCanvas.drawCircle(x, y, mPointRadius, mPaint);
        }


        //drawing shadows

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(LOG, event.getAction() + "");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            invalidate();
        }
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
