package com.bedulin.dots.ui.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.bedulin.dots.logic.search.Node;

import java.util.*;

import static com.bedulin.dots.Constants.CELLS_IN_HEIGHT;
import static com.bedulin.dots.Constants.CELLS_IN_WIDTH;

/**
 * @author Alexandr Bedulin
 */
public class GameFieldView extends View {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG_TAG = GameFieldView.class.getSimpleName();

    public static final int PLAYER_ONE_MOVE = 0;
    public static final int PLAYER_TWO_MOVE = 1;

    private static final int TRANSLATE_X_LEFT_MAX = -400;
    private static final int TRANSLATE_X_RIGHT_MAX = 400;
    private static final int TRANSLATE_Y_LEFT_MAX = -400;
    private static final int TRANSLATE_Y_RIGHT_MAX = 400;

    public final int SCREEN_WIDTH;

    public final int SCREEN_HEIGHT;

    public final float CELL_SIZE;

    public static final int POINT_IN_HEIGH;

    public static final int POINT_IN_WIDTH;

    private final int CELLS_COLOR;

    private final int PLAYER_ONE_COLOR;

    private final int PLAYER_TWO_COLOR;

    private static final float MIN_ZOOM = 1;

    private static final float MAX_ZOOM = 2;

    // ===========================================================
    // Fields
    // ===========================================================
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mPointRadius;
    private float mShiftX;
    private float mShiftY;

    private float mScaleCenterX;
    private float mScaleCenterY;
    private float mScaleFactor;
    private float mTranslateX;
    private float mTranslateY;
    private float mStartTranslateX;
    private float mStartTranslateY;

    private float mStartX;
    private float mStartY;

    private int mPrevAction;

    private Node[][] mPossibleMoves;
    private ArrayList<Node> mPlayerOneMoves;
    private ArrayList<Node> mPlayerTwoMoves;
    private PointF mPlayerOneTempPoint;
    private PointF mPlayerTwoTempPoint;
    private Set<List<Node>> mPlayerOnePaths;
    private Set<List<Node>> mPlayerTwoPaths;

    private int mNowMoves;

    private ProgressDialog mProgressDialog;

    private ScaleGestureDetector mScaleGestureDirector;
    private ScaleGestureDetector.SimpleOnScaleGestureListener mScaleListener;

    private SharedPreferences mSharedPreference;

    private boolean isApprovingMoveNeed;

    private boolean isFirstRender;

    // ===========================================================
    // Constructors
    // ===========================================================
    static {
        POINT_IN_HEIGH = CELLS_IN_HEIGHT + 1;
        POINT_IN_WIDTH = CELLS_IN_WIDTH + 1;
    }

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
        Log.d(LOG_TAG, "H:" + SCREEN_HEIGHT + " W:" + SCREEN_WIDTH);

        //init size for drawing (points, cells)
        CELL_SIZE = (float) Math.min(SCREEN_HEIGHT, SCREEN_WIDTH) / (float) Math.max(CELLS_IN_HEIGHT, CELLS_IN_WIDTH);
        mPointRadius = CELL_SIZE / 7;
        mShiftX = (SCREEN_WIDTH - CELLS_IN_WIDTH * CELL_SIZE) / 2;
        mShiftY = (SCREEN_HEIGHT - CELLS_IN_HEIGHT * CELL_SIZE) / 2;

        // creating drawing field
        mBitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);

        // init colors for drawing (cells, player one/two points)
        CELLS_COLOR = context.getResources().getColor(R.color.light_blue);
        PLAYER_ONE_COLOR = findColorByName(mSharedPreference.getString(Constants.PREFERENCE_FIRST_PLAYER_COLOR, Constants.PREFERENCE_DEFAULT_FIRST_PLAYER_COLOR));
        PLAYER_TWO_COLOR = findColorByName(mSharedPreference.getString(Constants.PREFERENCE_SECOND_PLAYER_COLOR, Constants.PREFERENCE_DEFAULT_SECOND_PLAYER_COLOR));

        // init drawing tools
        mPaint = new Paint(Paint.DITHER_FLAG);

        mPossibleMoves = new Node[POINT_IN_WIDTH][POINT_IN_HEIGH];
        findPossibleMoves();
        mPlayerOneMoves = new ArrayList<>();
        mPlayerTwoMoves = new ArrayList<>();
        mPlayerOnePaths = new HashSet<>();
        mPlayerTwoPaths = new HashSet<>();

        mNowMoves = PLAYER_ONE_MOVE;

        mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
                invalidate();
                return true;
            }
        };

        mScaleGestureDirector = new ScaleGestureDetector(context, mScaleListener);

        isApprovingMoveNeed = mSharedPreference.getBoolean(Constants.PREFERENCE_IS_APPROVING_MOVE_NEED, true);

        mScaleFactor = MIN_ZOOM;
        mScaleCenterX = SCREEN_WIDTH / 2;
        mScaleCenterY = SCREEN_HEIGHT / 2;

        isFirstRender = true;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public void setPath(List<Node> path) {
        switch (mNowMoves) {
            case PLAYER_ONE_MOVE:
                mPlayerOnePaths.add(path);
                break;
            case PLAYER_TWO_MOVE:
                mPlayerTwoPaths.add(path);
                break;
        }
    }

    public float getShiftX() {
        return mShiftX;
    }

    public float getShiftY() {
        return mShiftY;
    }

    public Node[][] getPossibleMoves() {
        return mPossibleMoves;
    }

    public ArrayList<Node> getPlayerOneMoves() {
        return mPlayerOneMoves;
    }

    public ArrayList<Node> getPlayerTwoMoves() {
        return mPlayerTwoMoves;
    }

    public int getNextMove() {
        return mNowMoves;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onDraw(Canvas canvas) {
        if (isFirstRender) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
            isFirstRender = false;
        }

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleCenterX, mScaleCenterY);
        canvas.translate(mTranslateX / mScaleFactor, mTranslateY / mScaleFactor);

        mPaint.setColor(CELLS_COLOR);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        for (int x = 0; x < CELLS_IN_WIDTH + 1; x++)
            canvas.drawLine(
                    (float) x * CELL_SIZE + mShiftX, mShiftY,
                    (float) x * CELL_SIZE + mShiftX, SCREEN_HEIGHT - mShiftY,
                    mPaint);
        for (int y = 0; y < CELLS_IN_WIDTH + 1; y++)
            canvas.drawLine(
                    mShiftX, (float) y * CELL_SIZE + mShiftY,
                    SCREEN_WIDTH - mShiftX, (float) y * CELL_SIZE + mShiftY,
                    mPaint);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        //drawing player one points
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(PLAYER_ONE_COLOR);
        int length = mPlayerOneMoves.size();
        for (int i = 0; i < length; i++) {
            float x = mPlayerOneMoves.get(i).x;
            float y = mPlayerOneMoves.get(i).y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
        }

        //drawing player one temp point
        if (mPlayerOneTempPoint != null) {
            float x = mPlayerOneTempPoint.x;
            float y = mPlayerOneTempPoint.y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x, y, mPointRadius * 3, mPaint);
        }

        //drawing player two points
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(PLAYER_TWO_COLOR);
        length = mPlayerTwoMoves.size();
        for (int i = 0; i < length; i++) {
            float x = mPlayerTwoMoves.get(i).x;
            float y = mPlayerTwoMoves.get(i).y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
        }

        //drawing player two temp point
        if (mPlayerTwoTempPoint != null) {
            float x = mPlayerTwoTempPoint.x;
            float y = mPlayerTwoTempPoint.y;
            canvas.drawCircle(x, y, mPointRadius, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x, y, mPointRadius * 3, mPaint);
        }

        //if there is path draw it
        if (mPlayerOnePaths.size() > 0) {
            mPaint.setColor(PLAYER_ONE_COLOR);
            mPaint.setStrokeWidth(mPointRadius);
            for (List<Node> path : mPlayerOnePaths) {
                int pathLength = path.size() - 1;
                for (int j = 0; j < pathLength; j++) {
                    Node thisNode = path.get(j);
                    Node nextNode = path.get(j + 1);
                    canvas.drawLine(thisNode.x, thisNode.y, nextNode.x, nextNode.y, mPaint);
                    if (j + 1 == pathLength) {
                        thisNode = nextNode;
                        nextNode = path.get(0);
                        canvas.drawLine(thisNode.x, thisNode.y, nextNode.x, nextNode.y, mPaint);
                    }
                }
            }
        }
        if (mPlayerTwoPaths.size() > 0) {
            mPaint.setColor(PLAYER_TWO_COLOR);
            mPaint.setStrokeWidth(mPointRadius);
            for (List<Node> path : mPlayerTwoPaths) {
                int pathLength = path.size() - 1;
                for (int j = 0; j < pathLength; j++) {
                    Node thisNode = path.get(j);
                    Node nextNode = path.get(j + 1);
                    canvas.drawLine(thisNode.x, thisNode.y, nextNode.x, nextNode.y, mPaint);
                    if (j + 1 == pathLength) {
                        thisNode = nextNode;
                        nextNode = path.get(0);
                        canvas.drawLine(thisNode.x, thisNode.y, nextNode.x, nextNode.y, mPaint);
                    }
                }
            }
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDirector.onTouchEvent(event);

        if (!mScaleGestureDirector.isInProgress()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    float x = event.getX();
                    float y = event.getY();
                    Node node = findNearestPoint(x, y);
                    if (node != null) { // click was inside field
                        switch (mNowMoves) {
                            case PLAYER_ONE_MOVE:
                                if (isApprovingMoveNeed) {
                                    if (node.equals(mPlayerOneTempPoint)) {
                                        mPlayerOneMoves.add(node);
                                        mPlayerOneTempPoint = null;
                                        mPossibleMoves[node.posX][node.posY] = null;
                                        mNowMoves = PLAYER_TWO_MOVE;
                                    } else {
                                        mPlayerOneTempPoint = node;
                                    }
                                } else {
                                    mPlayerOneMoves.add(node);
                                    mPossibleMoves[node.posX][node.posY] = null;
                                    mPlayerOneTempPoint = null;
                                    mNowMoves = PLAYER_TWO_MOVE;
                                }
                                break;

                            case PLAYER_TWO_MOVE:
                                if (isApprovingMoveNeed) {
                                    if (node.equals(mPlayerTwoTempPoint)) {
                                        mPlayerTwoMoves.add(node);
                                        mPlayerTwoTempPoint = null;
                                        mPossibleMoves[node.posX][node.posY] = null;
                                        mNowMoves = PLAYER_ONE_MOVE;
                                    } else {
                                        mPlayerTwoTempPoint = node;
                                    }
                                } else {
                                    mPlayerTwoMoves.add(node);
                                    mPossibleMoves[node.posX][node.posY] = null;
                                    mPlayerTwoTempPoint = null;
                                    mNowMoves = PLAYER_ONE_MOVE;
                                }
                        }
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mPrevAction != MotionEvent.ACTION_MOVE) {
                        mStartX = event.getX();
                        mStartY = event.getY();
                        mStartTranslateX = mTranslateX;
                        mStartTranslateY = mTranslateY;
                        mPrevAction = MotionEvent.ACTION_MOVE;
                    }

                    // don't allow to move cells too match
                    float newTranslateX = event.getX() - mStartX + mStartTranslateX;
                    if (newTranslateX < mTranslateX && mTranslateX > TRANSLATE_X_LEFT_MAX || newTranslateX > mTranslateX && mTranslateX < TRANSLATE_X_RIGHT_MAX)
                        mTranslateX = event.getX() - mStartX + mStartTranslateX;
                    float newTranslateY = event.getY() - mStartY + mStartTranslateY;
                    if (newTranslateY < mTranslateY && mTranslateY > TRANSLATE_Y_LEFT_MAX || newTranslateY > mTranslateY && mTranslateY < TRANSLATE_Y_RIGHT_MAX)
                        mTranslateY = event.getY() - mStartY + mStartTranslateY;

                    invalidate();
                    break;
            }

            if (mPrevAction == MotionEvent.ACTION_MOVE && event.getAction() != MotionEvent.ACTION_MOVE) {
                mPrevAction = event.getAction();
            }
        }
        return true;
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
                for (int posY = 0; posY < CELLS_IN_HEIGHT + 1; posY++)
                    for (int posX = 0; posX < CELLS_IN_WIDTH + 1; posX++) {
                        mPossibleMoves[posX][posY] = new Node((float) posX * CELL_SIZE + mShiftX, (float) posY * CELL_SIZE + mShiftY, posX, posY);
                    }
                mProgressDialog.dismiss();
            }
        }).start();
    }

    private Node findNearestPoint(float x, float y) {
        float X = x - mShiftX * mScaleFactor - mTranslateX;
        float Y = y - mShiftY * mScaleFactor - mTranslateY;

        X = (X - mScaleCenterX) / mScaleFactor + mScaleCenterX;
        Y = (Y - mScaleCenterY) / mScaleFactor + mScaleCenterY;

        int posX = Math.round((X) / CELL_SIZE);
        int posY = Math.round((Y) / CELL_SIZE);

        if (posX < POINT_IN_WIDTH && posX >= 0 && posY < POINT_IN_HEIGH && posY >= 0)
            return mPossibleMoves[posX][posY];
        else
            return null;
    }

    private void startSearch() {
        Node endPoint = null;
        int[][] grid = null;
        switch (mNowMoves) {
            case PLAYER_ONE_MOVE:
                endPoint = mPlayerOneMoves.get(mPlayerOneMoves.size() - 1);
                break;
            case PLAYER_TWO_MOVE:
                endPoint = mPlayerTwoMoves.get(mPlayerTwoMoves.size() - 1);
                break;
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}