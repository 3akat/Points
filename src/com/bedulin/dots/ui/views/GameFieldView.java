package com.bedulin.dots.ui.views;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.ScaleGestureDetector;
import android.view.View;
import com.bedulin.dots.Constants;
import com.bedulin.dots.R;
import com.bedulin.dots.temp.JPS;
import com.bedulin.dots.temp.Node;

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

    private static final int PLAYER_ONE_MOVE = 1;

    private static final int PLAYER_TWO_MOVE = 2;

    public final int SCREEN_WIDTH;

    public final int SCREEN_HEIGHT;

    private final int POINT_IN_WIDTH;

    private final int CELLS_COLOR;

    private final int PLAYER_ONE_COLOR;

    private final int PLAYER_TWO_COLOR;

    // ===========================================================
    // Fields
    // ===========================================================
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mCellSize;
    private float mPointRadius;
    private float mShiftX;
    private float mShiftY;

    private Node[][] mPossibleMoves;
    private ArrayList<Node> mPlayerOneMoves;
    private ArrayList<Node> mPlayerTwoMoves;
    private PointF mPlayerOneTempPoint;
    private PointF mPlayerTwoTempPoint;

    private int mNextMove;

    private ProgressDialog mProgressDialog;

    private ScaleGestureDetector mScaleGestureDirector;
    private ScaleGestureDetector.SimpleOnScaleGestureListener mScaleListener;

    private SharedPreferences mSharedPreference;

    private boolean isApprovingMoveNeed;

    // ===========================================================
    // Constructors
    // ===========================================================
    public GameFieldView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.loading));

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);

        //finding screen sizes
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        Log.d(LOG, "H:" + SCREEN_HEIGHT + " W:" + SCREEN_WIDTH);

        //init size for drawing (points, cells)
        mCellSize = Math.min(SCREEN_HEIGHT, SCREEN_WIDTH) / Math.max(Constants.CELLS_IN_HEIGHT, CELLS_IN_WIDTH);
        mPointRadius = mCellSize / 7;
        mShiftX = (SCREEN_WIDTH - CELLS_IN_WIDTH * mCellSize) / 2;
        mShiftY = (SCREEN_HEIGHT - CELLS_IN_HEIGHT * mCellSize) / 2;
        POINT_IN_WIDTH = CELLS_IN_WIDTH + 1;

        // creating drawing field
        mBitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);

        // init colors for drawing (cells, player one/two points)
        CELLS_COLOR = context.getResources().getColor(R.color.light_blue);
        PLAYER_ONE_COLOR = findColorByName(mSharedPreference.getString(Constants.PREFERENCE_FIRST_PLAYER_COLOR, Constants.PREFERENCE_DEFAULT_FIRST_PLAYER_COLOR));
        PLAYER_TWO_COLOR = findColorByName(mSharedPreference.getString(Constants.PREFERENCE_SECOND_PLAYER_COLOR, Constants.PREFERENCE_DEFAULT_SECOND_PLAYER_COLOR));

        // init drawing tools
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);


        mPossibleMoves = new Node[(CELLS_IN_HEIGHT + 1)][(CELLS_IN_WIDTH + 1)];
        findPossibleMoves();
        mPlayerOneMoves = new ArrayList<>();
        mPlayerTwoMoves = new ArrayList<>();

        mNextMove = PLAYER_ONE_MOVE;

        mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
//                mCanvas.scale(detector.getScaleFactor(), detector.getScaleFactor());
//                invalidate();
                return true;
            }
        };

        mScaleGestureDirector = new ScaleGestureDetector(context, mScaleListener);

        isApprovingMoveNeed = mSharedPreference.getBoolean(Constants.PREFERENCE_IS_APPROVING_MOVE_NEED, true);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mPaint.setColor(CELLS_COLOR);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        for (int x = 0; x < CELLS_IN_WIDTH + 1; x++)
            canvas.drawLine(
                    (float) x * mCellSize + mShiftX, 0,
                    (float) x * mCellSize + mShiftX, SCREEN_HEIGHT,
                    mPaint);
        for (int y = 0; y < CELLS_IN_WIDTH + 1; y++)
            canvas.drawLine(
                    0, (float) y * mCellSize + mShiftY,
                    SCREEN_WIDTH, (float) y * mCellSize + mShiftY,
                    mPaint);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        //drawing player one points
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(PLAYER_ONE_COLOR);
        int length = mPlayerOneMoves.size();
        for (int i = 0; i < length; i++) {
            float x = mPlayerOneMoves.get(i).getX();
            float y = mPlayerOneMoves.get(i).getY();
            canvas.drawCircle(x, y, mPointRadius, mPaint);
        }

        //drawing player one temp point
        if (mPlayerOneTempPoint != null) {
            float x = mPlayerOneTempPoint.x;
            float y = mPlayerOneTempPoint.y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawCircle(x, y, mPointRadius * 3, mPaint);
        }

        //drawing player two points
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(PLAYER_TWO_COLOR);
        length = mPlayerTwoMoves.size();
        for (int i = 0; i < length; i++) {
            float x = mPlayerTwoMoves.get(i).getX();
            float y = mPlayerTwoMoves.get(i).getY();
            canvas.drawCircle(x, y, mPointRadius, mPaint);
        }

        //drawing player two temp point
        if (mPlayerTwoTempPoint != null) {
            float x = mPlayerTwoTempPoint.x;
            float y = mPlayerTwoTempPoint.y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawCircle(x, y, mPointRadius * 3, mPaint);
        }

        mCanvas = canvas;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDirector.onTouchEvent(event);
        if (!mScaleGestureDirector.isInProgress()) {  // if not scaling

            //trying to find path
            if (mPlayerOneMoves.size() > 3 || mPlayerTwoMoves.size() > 3) {
                float xMax = CELLS_IN_WIDTH * mCellSize + mShiftX;  //size of grid x direction
                float yMax = CELLS_IN_HEIGHT * mCellSize + mShiftY;  //size of the grid y direction
                float xIsland = 0; //islands along the x direction
                float yIsland = 0; //islands along the y direction
                Node startPoint = null;
                Node endPoint = null;
                if (mPlayerOneMoves.size() > 3 && mPlayerTwoMoves.size() > 3) {
                    switch (mNextMove) {
                        case PLAYER_ONE_MOVE:
                            startPoint = mPlayerOneMoves.get(0);
                            endPoint = mPlayerOneMoves.get(3);
                            break;
                        case PLAYER_TWO_MOVE:
                            startPoint = mPlayerTwoMoves.get(0);
                            endPoint = mPlayerTwoMoves.get(3);
                            break;
                    }
                } else if (mPlayerOneMoves.size() > 3) {
                    startPoint = mPlayerOneMoves.get(0);
                    endPoint = mPlayerOneMoves.get(3);
                } else if (mPlayerOneMoves.size() > 3) {
                    startPoint = mPlayerTwoMoves.get(0);
                    endPoint = mPlayerTwoMoves.get(3);
                }

                JPS jpsg = new JPS(xMax, yMax, xIsland, yIsland, mPossibleMoves, mCellSize, mShiftX, mShiftY, startPoint, endPoint);
                boolean thereIsPath = jpsg.search();
            }
            float x = event.getX();
            float y = event.getY();
            if (x > mShiftX &&
                    y > mShiftY &&
                    x < mShiftX + CELLS_IN_WIDTH * mCellSize &&
                    y < mShiftY + CELLS_IN_HEIGHT * mCellSize)     //checking cells field borders
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Node node = findNearestPoint(x, y);
                    switch (mNextMove) {
                        case PLAYER_ONE_MOVE:
                            if (!mPlayerTwoMoves.contains(node)) {// there is no other player point in this place
                                if (isApprovingMoveNeed)
                                    if (node.equals(mPlayerOneTempPoint)) {
                                        mPlayerOneMoves.add(node);
                                        mPlayerOneTempPoint = null;
                                        mNextMove = PLAYER_TWO_MOVE;
                                    } else {
                                        mPlayerOneTempPoint = node;
                                    }
                                else {
                                    mPlayerOneMoves.add(node);
                                    mNextMove = PLAYER_TWO_MOVE;
                                }
                            }
                            break;
                        case PLAYER_TWO_MOVE:
                            if (!mPlayerOneMoves.contains(node)) {// there is no other player point in this place
                                if (isApprovingMoveNeed)
                                    if (node.equals(mPlayerTwoTempPoint)) {
                                        mPlayerTwoMoves.add(node);
                                        mPlayerTwoTempPoint = null;
                                        mNextMove = PLAYER_ONE_MOVE;
                                    } else {
                                        mPlayerTwoTempPoint = node;
                                    }
                                else {
                                    mPlayerTwoMoves.add(node);
                                    mNextMove = PLAYER_ONE_MOVE;
                                }
                            }
                            break;
                    }
                    invalidate();
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private int findColorByName(String colorName) {
        int color = 0;

        switch (colorName) {
            case Constants.COLOR_BLACK:
                color = getContext().getResources().getColor(R.color.black);
                break;
            case Constants.COLOR_BLUE:
                color = getContext().getResources().getColor(R.color.blue);
                break;
            case Constants.COLOR_GREEN:
                color = getContext().getResources().getColor(R.color.green);
                break;
            case Constants.COLOR_RED:
                color = getContext().getResources().getColor(R.color.red);
                break;
            case Constants.COLOR_YELLOW:
                color = getContext().getResources().getColor(R.color.yellow);
                break;
            case Constants.COLOR_LIGHT_PURPLE:
                color = getContext().getResources().getColor(R.color.light_purple);
                break;
            case Constants.COLOR_LIGHT_BLUE:
                color = getContext().getResources().getColor(R.color.light_purple);
                break;
        }
        return color;
    }

    private void findPossibleMoves() {
        mProgressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < CELLS_IN_HEIGHT + 1; i++)
                    for (int j = 0; j < CELLS_IN_WIDTH + 1; j++) {
                        mPossibleMoves[j][i] = new Node((float) j * mCellSize + mShiftX, (float) i * mCellSize + mShiftY);
                    }
                mProgressDialog.dismiss();
            }
        }).start();
    }

    private Node findNearestPoint(float x, float y) {
        int posX = Math.round((x - mShiftX) / mCellSize);
        int posY = Math.round((y - mShiftY) / mCellSize);

        return mPossibleMoves[posX][posY];
    }

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
