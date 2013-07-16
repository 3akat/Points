package com.bedulin.dots.ui.views;

import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_BORDER_BOTTOM;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_BORDER_LEFT;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_BORDER_RIGHT;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_BORDER_TOP;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_CELL_HINT;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_CELL_NORMAL;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_CELL_PRESSED;
import static by.squareroot.kakuro.resource.atlas.TextureMap.Game.GAME_CELL_UNUSED;

import java.util.Arrays;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceGestureDetector.SurfaceGestureDetectorAdapter;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.annotation.SuppressLint;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import by.squareroot.kakuro.Log;
import by.squareroot.kakuro.puzzle.PuzzleModel;
import by.squareroot.kakuro.puzzle.PuzzleModel.Cell;
import by.squareroot.kakuro.puzzle.PuzzleModel.Cell.CellType;
import by.squareroot.kakuro.puzzle.PuzzleModel.EmptyCell;
import by.squareroot.kakuro.puzzle.PuzzleModel.HintCell;
import by.squareroot.kakuro.puzzle.PuzzleModel.SolidCell;
import by.squareroot.kakuro.puzzle.PuzzleValidator;
import by.squareroot.kakuro.puzzle.PuzzleValidator.CheckResult;
import by.squareroot.kakuro.puzzle.PuzzleValidator.CompletenessListener;
import by.squareroot.kakuro.resource.Game;
import by.squareroot.kakuro.resource.atlas.TextureMap;
import by.squareroot.kakuro.scene.item.AnswerText;
import by.squareroot.kakuro.scene.item.DigitsPanel;
import by.squareroot.kakuro.scene.item.DigitsPanel.DigitSelectedListener;
import by.squareroot.kakuro.scene.item.TwoTextsLabelFactory;
import by.squareroot.kakuro.settings.SettingsManager;

public class GameField extends Rectangle implements IPinchZoomDetectorListener, 
		IScrollDetectorListener, DigitSelectedListener, CompletenessListener {
	private static abstract class GameCell {
	}
	
	private static class Hint extends GameCell {
		Text upper;
		Text lower;
	}
	
	public static class Answer extends GameCell {
		public final static String EMPTY = " ";
		AnswerText text;
	}
	
	private static class GameInfo {
		float width;
		float height;
		SimpleSpriteBatch batch;
		PuzzleModel model;
		GameCell[][] grid;
	}
	
	private static class FindResult {
		HintCell hint;
		int x;
		int y;
	}
	
	public static interface CellSelectionListener {
		void onCellSelected(int value, boolean hasNotes, boolean[] notes);
	}
	
	public static interface NotesModeListener {
		void onModeChanged(boolean noteMode);
	}
	
	private final static String TAG = GameField.class.getSimpleName();
	
	private final static int SCROLL_TO_POSITION_DURATION = 1000;
	private final static float AUTO_ZOOM_DURATION = 0.3f;
	
	private final static Color ANSWER_DEFAULT_COLOR = Color.BLACK;
	private final static Color ANSWER_INVALID_COLOR = Color.RED;
	private final static Color HINT_DEFAULT_COLOR = Color.WHITE;
	private final static Color HINT_INVALID_COLOR = Color.RED;
	private final static Color HINT_VALID_COLOR = new Color(0.298f, 0.608f, 0.271f); // 0x4c9b45
	
	private final static float NO_SCALE = 1f;
	private final Game game;
	private final VertexBufferObjectManager vboManager;
	private final PinchZoomDetector pinchZoomDetector;
	private final ScrollDetector scrollDetector;
	private volatile DoubleTapDetectorAdapter gestureDetector;
	private float scale = NO_SCALE;
	private final ContentRectangle content;
	private final Scroller scroller;
	private final VelocityTracker velocityTracker = VelocityTracker.obtain();
	private final int maximumVelocity;
	private final int minimumVelocity;
	private final ITextureRegion regionHint;
	private final ITextureRegion regionNormal;
	private final ITextureRegion regionPressed;
	private final ITextureRegion regionUnused;
	private final Font hintFont;
	private final Font font;
	private final float cellSize;
	private GameInfo info;
	private CursorSprite selected;
	private DigitsPanel digitsPanel;
	private final PuzzleValidator validator = new PuzzleValidator();
	private final FindResult[] results = new FindResult[] { new FindResult(), new FindResult() };
	private CompletenessListener listener;
	private final Sprite borderLeft;
	private final Sprite borderRight;
	private final Sprite borderTop;
	private final Sprite borderBottom;
	private boolean solved = false;
	private final float gameScale;
	private CellSelectionListener cellSelectionListener;
	private final SettingsManager settings;
	private boolean notesMode;
	private NotesModeListener notesModeListener;
	private final int index;
	
	private class DoubleTapDetectorAdapter extends SurfaceGestureDetectorAdapter {
		private float lastX;
		private float lastY;
		
		@Override
		protected boolean onDoubleTap() {
			GameField.this.onDoubleTap(lastX, lastY);
			return true;
		}
		
		public boolean onTouchEvent(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			this.lastX = pTouchAreaLocalX;
			this.lastY = pTouchAreaLocalY;
			return super.onTouchEvent(pSceneTouchEvent);
		}
	}
	
	public GameField(Game game, float x, float y, float width, float height) {
		super(x, y, width, height, game.getEngine().getVertexBufferObjectManager());
		this.index = game.getTypeIndex();
		
		scroller =  new Scroller(game.getContext());
		final ViewConfiguration config = ViewConfiguration.get(game.getContext());
		int slop = config.getScaledTouchSlop();
		scrollDetector = new ScrollDetector(slop, this);
        maximumVelocity = config.getScaledMaximumFlingVelocity();
        minimumVelocity = config.getScaledMinimumFlingVelocity();
		
		setColor(Color.TRANSPARENT);
		this.game = game;
		settings = SettingsManager.getInstance(game.getContext());
		game.getSceneManager().getRootScene().registerTouchArea(this);
		
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				gestureDetector = new DoubleTapDetectorAdapter();
			}
		});
		
		pinchZoomDetector = new PinchZoomDetector(this);
		vboManager = game.getEngine().getVertexBufferObjectManager();

		TextureMap map = game.getTextureMap();
		regionHint = map.get(GAME_CELL_HINT);
		regionNormal = map.get(GAME_CELL_NORMAL);
		regionPressed = map.get(GAME_CELL_PRESSED);
		regionUnused = map.get(GAME_CELL_UNUSED);
		
		cellSize = regionNormal.getWidth();
		
		hintFont = map.getFont(TextureMap.Fonts.ARIAL);
		
		font = map.getFont(TextureMap.Fonts.ARIAL_BIG);
		
		content = new ContentRectangle(0, 0, width, height, getVertexBufferObject());
		content.setColor(Color.TRANSPARENT);
		attachChild(content);
		
		validator.setCompletenessListener(this);
		
		borderLeft = new Sprite(0, 0, map.get(GAME_BORDER_LEFT), vboManager);
		borderLeft.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		borderLeft.setAlpha(0);
		attachChild(borderLeft);
		
		borderRight = new Sprite(0, 0, map.get(GAME_BORDER_RIGHT), vboManager);
		borderRight.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		borderRight.setAlpha(0);
		attachChild(borderRight);
		borderRight.setPosition(getWidthScaled() - borderRight.getWidthScaled(), 0);
		
		borderTop = new Sprite(0, 0, map.get(GAME_BORDER_TOP), vboManager);
		borderTop.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		borderTop.setAlpha(0);
		attachChild(borderTop);
		
		borderBottom = new Sprite(0, 0, map.get(GAME_BORDER_BOTTOM), vboManager);
		borderBottom.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		borderBottom.setAlpha(0);
		borderBottom.setPosition(0, getHeightScaled() - borderBottom.getHeightScaled());
		attachChild(borderBottom);
		
		gameScale = game.getScale();
	}
	
	public void setCompletenessListener(CompletenessListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		float[] xy = getParent().convertLocalToSceneCoordinates(mX, mY);
		int width = Math.round(getWidth() * gameScale);
		int height = Math.round(getHeight() * gameScale) - 1; // magic pixel;
		int x = Math.round(xy[0]);
		int y = Math.round(pCamera.getHeight() - xy[1] - height) - 1; // magic pixel
		
		pGLState.enableScissorTest();
		GLES20.glScissor(x, y, width, height);
		
		super.onManagedDraw(pGLState, pCamera);
		
		pGLState.disableScissorTest();
	}

	private void fling() {
		velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
		int velocityX = Math.round(velocityTracker.getXVelocity());
		int velocityY = Math.round(velocityTracker.getYVelocity());
		if (Math.abs(velocityX) > minimumVelocity || Math.abs(velocityY) > minimumVelocity) {
			int minX = 0;
			int maxX = 0;
			int minY = 0;
			int maxY = 0;
			
			if (content.getWidthScaled() > getWidthScaled()) {
				minX = Math.round(getWidthScaled() - content.getWidthScaled());
			} else {
				minX = Math.round((getWidthScaled() - content.getWidthScaled()) / 2f);
				maxX = minX;			
			}
			
			if (content.getHeightScaled() > getHeightScaled()) {
				minY = Math.round(getHeightScaled() - content.getHeightScaled());
			} else {
				minY = Math.round((getHeightScaled() - content.getHeightScaled()) / 2f);
				maxY = minY;			
			}
			
			int offsetX = Math.round(content.getScaleCenterX() * (1 - content.getScaleX()));
			int offsetY = Math.round(content.getScaleCenterY() * (1 - content.getScaleY()));
			minX -= offsetX;
			maxX -= offsetX;
			minY -= offsetY;
			maxY -= offsetY;			
			
			scroller.fling(Math.round(content.getX()), Math.round(content.getY()), 
					velocityX, velocityY, 
					minX, maxX,	minY, maxY);
		}
	}
	
	private void onDoubleTap(float tapX, float tapY) {
		if (content.getWidth() < getWidthScaled() && content.getHeight() < getHeightScaled()) {
			// content already fits parent bounds
			return;
		}
		
		final float startScale = content.getScaleX();
		float minScale = Math.min(getWidthScaled() / content.getWidth(), getHeightScaled() / content.getHeight());
		final float destScale;
		if (startScale > minScale + (1f - minScale) / 2f) {
			destScale = minScale; 
		} else {
			destScale = 1f;
		}
		final float scaleFactor = destScale / startScale;
		final float diff = (scaleFactor - 1f) / AUTO_ZOOM_DURATION;
		
		// find on which cell double tap was performed
		int x = -1;
		int y = -1;
		if (destScale > minScale) {
			final Cell[][] cells = info.model.getCells();
			y = (int) Math.floor((-content.getVisibleY() + tapY) / (cellSize * content.getScaleY()));
			if (y < 0) {
				y = 0;
			}
			if (y >= cells.length) {
				y = cells.length - 1;
			}
			x = (int) Math.floor((-content.getVisibleX() + tapX) / (cellSize * content.getScaleX()));
			if (x < 0) {
				x = 0;
			}
			if (x >= cells[y].length) {
				x = cells[y].length - 1;
			}
		}
		final int destX = x;
		final int destY = y;
		
		registerUpdateHandler(new IUpdateHandler() {
			float ellapsed = 0;
			
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				ellapsed += pSecondsElapsed;
				onPinchZoom(null, null, 1f + diff * ellapsed);
				if (ellapsed > AUTO_ZOOM_DURATION) {
					onPinchZoomFinished(null, null, destScale);
					unregisterUpdateHandler(this);

					if (destX >= 0 && destY >= 0) {
						scrollToCellIfNotVisible(destX, destY, true);
					}
				}
			}
		});
	}
	
	@SuppressLint({ "FloatMath", "FloatMath" })
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (info == null) {
			return false;
		}
		
		if (gestureDetector != null && gestureDetector.onTouchEvent(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY)) {
			return true;
		}
		
		// check if content is large and can be scrolled and zoomed
		if (content.getWidth() > getWidthScaled() || content.getHeight() > getHeightScaled()) {
			pinchZoomDetector.onTouchEvent(pSceneTouchEvent);
			if (pinchZoomDetector.isZooming()) {
				// cells shouldn't be selected and fling shouldn't be triggered while zooming
				return true;
			}
			
			scrollDetector.onTouchEvent(pSceneTouchEvent);
			velocityTracker.addMovement(pSceneTouchEvent.getMotionEvent());
			if (pSceneTouchEvent.isActionUp()) {
				fling();
				velocityTracker.clear();
			} else if (pSceneTouchEvent.isActionDown()) {
				if (!scroller.isFinished()) {
					scroller.abortAnimation();
				}
			}
		}
		
		if (solved) {
			return true;
		}
		
		final Cell[][] cells = info.model.getCells();
		
		// process cell press event
		if (pSceneTouchEvent.isActionDown()) {
			int y = (int) Math.floor((-content.getVisibleY() + pTouchAreaLocalY) / (cellSize * content.getScaleY()));
			if (y < 0 || y >= cells.length) {
				return true;
			}
			int x = (int) Math.floor((-content.getVisibleX() + pTouchAreaLocalX) / (cellSize * content.getScaleX()));
			if (x < 0 || x >= cells[y].length) {
				return true;
			}

			Cell cell = cells[y][x];
			if (cell instanceof EmptyCell) {
				getCursorSprite().setPosition(x, y);
				
				EmptyCell empty = (EmptyCell) cell;
				if (empty.hasNotes()) {
					if (notesModeListener != null) {
						notesModeListener.onModeChanged(true);
					}
				} else {
					if (notesModeListener != null) {
						notesModeListener.onModeChanged(false);
					}
				}
				
				if (cellSelectionListener != null) {
					cellSelectionListener.onCellSelected(empty.getValue(), empty.hasNotes(), empty.getNotes());
				}				
			}
		}

		return true;
	}
	
	public int getSelectedX() {
		if (selected != null && selected.isVisible()) {
			return selected.getCellX();
		} else {
			return -1;
		}
	}
	
	public int getSelectedY() {
		if (selected != null && selected.isVisible()) {
			return selected.getCellY();
		} else {
			return -1;
		}
	}
	
	public EmptyCell getCurrentCell() {
		final int x;
		if (selected != null && selected.isVisible()) {
			x = selected.getCellX();
		} else {
			return null;
		}
		final int y; 
		if (selected != null && selected.isVisible()) {
			y = selected.getCellY();
		} else {
			return null;
		}
		
		if (info == null) {
			return null;
		}
		if (info.model == null) {
			return null; 
		}
		Cell[][] cells = info.model.getCells();
		if (cells == null) {
			return null;
		}
		if (y < 0 || x < 0 || y >= cells.length || x >= cells[y].length) {
			return null;
		}
		
		Cell cell = cells[y][x];
		if (cell instanceof EmptyCell) {
			return ((EmptyCell) cell);
		}
		return null;
	}
	
	public int getCurrentValue() {
		EmptyCell empty = getCurrentCell();
		if (empty != null) {
			return empty.getValue();
		}
		return EmptyCell.NO_VALUE;
	}
	
	private CursorSprite getCursorSprite() {
		if (!selected.isVisible()) {
			selected.setVisible(true);
			// input is now enabled
			digitsPanel.setEnabled(true);
		}
		return selected;
	}

	@Override
	public void onPinchZoomStarted(PinchZoomDetector pPinchZoomDetector, TouchEvent pSceneTouchEvent) {
	}
	
	@Override
	public void onPinchZoomFinished(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
		scale = content.getScaleX();
	}

	@Override
	public void onPinchZoom(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
		Log.d(TAG, "zoom factor = " + pZoomFactor);
		
		float scale = this.scale * pZoomFactor;
		if (scale >= 1f) {
			return;
		}

		if (content.getWidth() * scale < getWidthScaled() && content.getHeight() * scale < getHeightScaled()) {
			scale = Math.min(getWidthScaled() / content.getWidth(), getHeightScaled() / content.getHeight());
		}
		content.setScale(scale);
		
		if (content.getWidthScaled() > getWidthScaled()) {
			float visibleX = content.getVisibleX();
			float offsetX = 0;

			if (visibleX > 0) {
				offsetX = -visibleX;
			}
			if (visibleX + content.getWidthScaled() < getWidthScaled()) {
				offsetX = getWidthScaled() - visibleX - content.getWidthScaled();
			}
			if (offsetX != 0) {
				content.setPositionOffset(offsetX, 0);
			}
		} else {
			float x = (getWidthScaled() - content.getWidthScaled()) / 2f;
			content.setVisiblePosition(x, content.getVisibleY());
		}
		
		if (content.getHeightScaled() > getHeightScaled()) {
			float visibleY = content.getVisibleY();
			float offsetY = 0;

			if (visibleY > 0) {
				offsetY = -visibleY;
			}
			if (visibleY + content.getHeightScaled() < getHeightScaled()) {
				offsetY = getHeightScaled() - visibleY - content.getHeightScaled();
			}

			if (offsetY != 0) {
				content.setPositionOffset(0, offsetY);
			}
		} else {		
			float y = (getHeightScaled() - content.getHeightScaled()) / 2f;
			content.setVisiblePosition(content.getVisibleX(), y);
		}
		
		onContentPositionChanged();
	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {
		if (pinchZoomDetector.isZooming()) {
			return;
		}
		float newX;
		if (content.getWidthScaled() > getWidthScaled()) {
			newX = content.getVisibleX() + pDistanceX;
			if (content.getWidthScaled() + newX < getWidthScaled()) {
				newX = getWidthScaled() - content.getWidthScaled();
			} else if (newX > 0) {
				newX = 0;
			}
		} else {
			newX = (getWidthScaled() - content.getWidthScaled()) / 2f; 
		}

		float newY;
		if (content.getHeightScaled() > getHeightScaled()) {
			newY = content.getVisibleY() + pDistanceY;
			if (content.getHeightScaled() + newY < getHeightScaled()) {
				newY = getHeightScaled() - content.getHeightScaled();
			} else if (newY > 0) {
				newY = 0;
			}
		} else {
			newY = (getHeightScaled() - content.getHeightScaled()) / 2f;
		}
		
		content.setVisiblePosition(newX, newY);
		
		float centerX = getWidthScaled() / 2f - content.getX();
		float centerY = getHeightScaled() / 2f - content.getY();
		content.setScaleCenter(centerX, centerY);
		
		onContentPositionChanged();
	}
	
	private void onContentPositionChanged() {
		float alpha;
		
		float leftOffset = -content.getVisibleX();
		if (leftOffset > 0) {
			alpha = leftOffset / borderLeft.getWidthScaled();
			borderLeft.setAlpha(alpha > 1 ? 1: alpha);
		} else {
			borderLeft.setAlpha(0f);
		}
		
		float topOffset = -content.getVisibleY();
		if (topOffset > 0) {
			alpha = topOffset / borderTop.getHeightScaled();
			borderTop.setAlpha(alpha > 1 ? 1: alpha);
		} else {
			borderTop.setAlpha(0f);
		}
		
		float rightOffset = content.getWidthScaled() + content.getVisibleX() - getWidthScaled();
		if (rightOffset > 0) {
			alpha = rightOffset / borderRight.getWidthScaled();
			borderRight.setAlpha(alpha > 1 ? 1: alpha);
		} else {
			borderRight.setAlpha(0f);
		}
		
		float bottomOffset = content.getHeightScaled() + content.getVisibleY() - getHeightScaled();
		if (bottomOffset > 0) {
			alpha = bottomOffset / borderBottom.getHeightScaled();
			borderBottom.setAlpha(alpha > 1 ? 1: alpha);
		} else {
			borderBottom.setAlpha(0f);
		}		
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (!scroller.isFinished()) {
			scroller.computeScrollOffset();
			content.setPosition(scroller.getCurrX(), scroller.getCurrY());
			onContentPositionChanged();
		}
	}
	
	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {
	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY) {
	}
	
	private final Runnable updateContent = new Runnable() {
		@Override
		public void run() {
			if (info == null) {
				Log.w(TAG, "updateContent: info is null");
				return;
			}
			content.setWidth(info.width);
			content.setHeight(info.height);
			
			if (content.getWidth() > getWidthScaled() || content.getHeight() > getHeightScaled()) {
				scale = Math.min(getWidthScaled() / content.getWidth(), getHeightScaled() / content.getHeight());
				content.setScale(scale);
				float x = (getWidthScaled() - content.getWidthScaled()) / 2f;
				float y = (getHeightScaled() - content.getHeightScaled()) / 2f;
				content.setVisiblePosition(x, y);
			} else {
				float x = (getWidthScaled() - info.width) / 2f;
				float y = (getHeight() - info.height) / 2f;
				content.setPosition(x, y);
			}
			
			if (info.batch.hasParent()) {
				info.batch.detachSelf();
			}
			content.attachChild(info.batch);
			
			onContentPositionChanged();
		}
	};

	private GameInfo createGameInfo(PuzzleModel model) {
		long time = -System.currentTimeMillis();
		
		GameInfo info = new GameInfo();
		info.model = model;
		info.width = model.getWidth() * cellSize;
		info.height = model.getHeight() * cellSize;
		
		Cell[][] cells = model.getCells();
		info.grid = new GameCell[model.getHeight()][model.getWidth()];
		
		int capacity = model.getHeight() * model.getWidth();
		SimpleSpriteBatch batch = new SimpleSpriteBatch(regionNormal.getTexture(), capacity, vboManager);
		Log.d(TAG, "batch size = " + capacity);
		
		info.batch = batch;
		selected = new CursorSprite(0, 0, regionPressed, vboManager);
		selected.setVisible(false);
		batch.attachChild(selected);
		
		float x;
		float y;
		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				x = cellSize * j;
				y = cellSize * i;
				
				Cell cell = cells[i][j];
				CellType type = cell.getType();
				ITextureRegion region = null;
				switch (type) {
				case EMPTY:
					region = regionNormal;
					Answer answer = new Answer();
					info.grid[i][j] = answer;
					EmptyCell empty = (EmptyCell) cell;
					int value = empty.getValue();
					if (value != EmptyCell.NO_VALUE) {
						setAnswer(batch, answer, j, i, value);
					} else if (empty.hasNotes()) {
						setAnswer(batch, answer, j, i, empty.getNotes());
					}
					break;
					
				case HINT:
					Hint gameHint = new Hint();
					info.grid[i][j] = gameHint;
					
					HintCell hint = (HintCell) cell;
					String upperText = hint.getHorizontalHint() > 0 ? String.valueOf(hint.getHorizontalHint()): null;
					if (upperText != null) {
						Text upper = TwoTextsLabelFactory.createUpperTextSprite(index, info.batch, x, y, 
								regionHint, hintFont, upperText, vboManager);
						gameHint.upper = upper;
					}
					String lowerText = hint.getVerticalHint() > 0 ? String.valueOf(hint.getVerticalHint()): null;
					if (lowerText != null) {
						Text lower = TwoTextsLabelFactory.createLowerTextSprite(index, info.batch, x, y, 
								regionHint, hintFont, lowerText, vboManager);
						gameHint.lower = lower;
					}
					region = regionHint;
					break;
					
				case SOLID:
					region = regionUnused;
					break;
				}
				
				info.batch.draw(region, x, y);
			}
		}
		
		info.batch.submit();
		
		time += System.currentTimeMillis();
		Log.d(TAG, "cells created in " + time + " ms");
		
		return info;
	}
	
	public void setPuzzle(PuzzleModel model) {
		info = createGameInfo(model);
		if (info != null) {
			settings.save(info.model);
		}
		validator.validate(model);
		checkSolvedBlocks();
		checkDuplicates();
		game.getEngine().runOnUpdateThread(updateContent);
	}
	
	public void reset() {
		Log.d(TAG, "reset");
		if (info != null) {
			info.batch.detachChildren();
		}
		content.detachChildren();
		content.setSize(getWidth(), getHeight());
		content.setScale(NO_SCALE);
		content.setPosition(0, 0);
		onContentPositionChanged();
		content.setScaleCenter(getWidthScaled() / 2f, getHeightScaled() / 2f);		
		selected = null;
		info = null;
		scale = NO_SCALE;
		solved = false;
	}

	public void setDigitsPanel(DigitsPanel digitsPanel) {
		this.digitsPanel = digitsPanel;
	}
	
	private FindResult[] findHintCell(int x, int y) {
		Cell[][] cells = info.model.getCells();
		HintCell hint = null;
		int hintX = x;
		int hintY = y;
		
		for (int i = x - 1; i >= 0; i--) {
			Cell cell = cells[y][i];
			if (cell instanceof SolidCell) {
				break;
			}
			if (cell instanceof HintCell) {
				hint = (HintCell) cell;
				hintX = i;
				hintY = y;
				break;
			}
		}
		
		if (hint != null) {
			results[0].hint = hint;
			results[0].x = hintX;
			results[0].y = hintY;
		} else {
			results[0].hint = null;
			results[0].x = -1;
			results[0].y = -1;
		}
		
		for (int i = y - 1; i >= 0; i--) {
			Cell cell = cells[i][x];
			if (cell instanceof SolidCell) {
				break;
			}
			if (cell instanceof HintCell) {
				hint = (HintCell) cell;
				hintX = x;
				hintY = i;
				break;
			}
		}
		
		if (hint != null) {
			results[1].hint = hint;
			results[1].x = hintX;
			results[1].y = hintY;
		} else {
			results[1].hint = null;
			results[1].x = -1;
			results[1].y = -1;
		}
		
		return results;
	}
	
	private Color getHintColor(boolean valid) {
		if (valid) {
			return HINT_VALID_COLOR;
		} else {
			return HINT_INVALID_COLOR;
		}
	}
	
	private void checkSolvedBlocks() {
		if (info == null || info.model == null) {
			return;
		}
		PuzzleModel model = info.model;
		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				checkSolvedBlocks(j, i);
			}
		}
	}
	
	/**
	 * Should be called only after validation because validator sets 
	 * necessary flags in puzzle model during validation process
	 * @param x 
	 * @param y
	 */
	private void checkSolvedBlocks(int x, int y) {
		Cell[][] cells = info.model.getCells(); 
		if (!(cells[y][x] instanceof EmptyCell)) {
			return;
		}
		
		FindResult[] results = findHintCell(x, y);
		for (int i = 0; i < results.length; i++) {
			FindResult result = results[i];
			if (result == null || result.hint == null || result.x < 0 || result.y < 0) {
				continue;
			}
			Hint hint = (Hint) info.grid[result.y][result.x];

			if (result.hint.hasHorizontalHint()) {
				final Color color;
				if (result.hint.isCompleteHorizontal()) {
					color = getHintColor(result.hint.isHorizontalSumValid());
				} else {
					color = HINT_DEFAULT_COLOR;
				}
				hint.upper.setColor(color);
			}

			if (result.hint.hasVerticalHint()) {
				final Color color;
				if (result.hint.isCompleteVertical()) {
					color = getHintColor(result.hint.isVerticalSumValid());
				} else {
					color = HINT_DEFAULT_COLOR;
				}
				hint.lower.setColor(color);
			}
		}
	}
	
	private void setValueInModel(int x, int y, int digit, boolean clear) {
		Cell[][] cells = info.model.getCells();
		Cell cell = cells[y][x];
		if (!(cell instanceof EmptyCell)) {
			return;
		}
		
		EmptyCell empty = (EmptyCell) cell;
		if (notesMode) {
			if (digit == EmptyCell.NO_VALUE) {
				empty.clearNotes();
			} else {
				empty.setNote(digit, !clear);
			}
			empty.setValue(EmptyCell.NO_VALUE);
		} else {
			if (clear) {
				empty.setValue(EmptyCell.NO_VALUE);
			} else {
				empty.setValue(digit);
			}
			empty.clearNotes();
		}
		
		settings.save(info.model);

		// model should be validated before checks
		validator.validate(info.model);
		// highlights glues red or green
		checkSolvedBlocks(x, y);
		// highlights duplicates with red
		checkDuplicates();
	}
	
	private final int[] digits = new int[10];
	
	private boolean setDublicateColor(EmptyCell empty, Answer answer) {
		int value = empty.getValue();
		if (value == EmptyCell.NO_VALUE) {
			return false;
		}

		Entity text = answer.text;
		if (text == null) {
			return false;
		}
		
		if (text.getUserData() == Boolean.TRUE) {
			// answer was already marked as duplicate in this iteration
			return false;
		}
		
		if (digits[value] > 1) {
			text.setColor(ANSWER_INVALID_COLOR);
			return true;
		} else {
			text.setColor(ANSWER_DEFAULT_COLOR);
			return false;
		}
	}
	
	private void checkDuplicates() {
		if (info == null || info.model == null) {
			return;
		}
		
		Cell[][] cells = info.model.getCells();
		final int height = info.model.getHeight();
		final int width = info.model.getWidth();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				GameCell cell = info.grid[y][x];
				if (cell instanceof Answer) {
					Entity text = ((Answer) cell).text;
					if (text != null) {
						text.setUserData(Boolean.FALSE);
					}
				}
			}
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Cell cell = cells[y][x];
				if (cell.getType() != CellType.HINT) {
					continue;
				}

				HintCell hint = (HintCell) cell;

				if (hint.hasHorizontalHint()) {
					Arrays.fill(digits, 0);
					for (int i = x + 1; i < width; i++) {
						Cell c = cells[y][i];
						if (c.getType() != CellType.EMPTY) {
							break;
						}

						int val = ((EmptyCell) c).getValue();
						if (val != EmptyCell.NO_VALUE) {
							digits[val]++;
						}
					}
					for (int i = x + 1; i < width; i++) {
						Cell c = cells[y][i];
						if (c.getType() != CellType.EMPTY) {
							break;
						}
						Answer answer = (Answer) info.grid[y][i];
						if (setDublicateColor((EmptyCell) c, answer)) {
							answer.text.setUserData(Boolean.TRUE);
						}
					}
				}

				if (hint.hasVerticalHint()) {
					Arrays.fill(digits, 0);
					for (int i = y + 1; i < height; i++) {
						Cell c = cells[i][x];
						if (c.getType() != CellType.EMPTY) {
							break;
						}

						int val = ((EmptyCell) c).getValue();
						if (val != EmptyCell.NO_VALUE) {
							digits[val]++;
						}
					}
					for (int i = y + 1; i < height; i++) {
						Cell c = cells[i][x];
						if (c.getType() != CellType.EMPTY) {
							break;
						}
						Answer answer = (Answer) info.grid[i][x];
						if (setDublicateColor((EmptyCell) c, answer)) {
							answer.text.setUserData(Boolean.TRUE);
						}
					}
				}
			}
		}	
	}
	
	private AnswerText getAnswerText(SimpleSpriteBatch batch, Answer answer, int x, int y) {
		if (answer == null) {
			return null;
		}
		
		float size = regionPressed.getWidth();
		if (answer.text == null) {
			answer.text = new AnswerText(size * x, size * y, size, size, font, vboManager);
			batch.attachChild(answer.text);
			return answer.text;
		} else {
			return answer.text;
		}
	}
	
	private void setAnswer(SimpleSpriteBatch batch, Answer answer, int x, int y, boolean[] notes) {
		AnswerText text = getAnswerText(batch, answer, x, y);
		if (text == null) {
			return;
		}
		
		for (int i = 0; i < notes.length; i++) {
			if (notes[i]) {
				text.showNote(i + 1);
			}
		}
	}
	
	private void setAnswer(SimpleSpriteBatch batch, Answer answer, int x, int y, int value) {
		AnswerText text = getAnswerText(batch, answer, x, y);
		if (text == null) {
			return;
		}
		
		if (notesMode) {
			text.showNote(value);
			text.setText(Answer.EMPTY);
		} else {
			text.setText(String.valueOf(value));
			text.clearNotes();
		}
	}
	
	private void setAnswer(int x, int y, int digit) {
		setSelected(x, y);
		GameCell cell = info.grid[y][x];
		if (cell instanceof Answer) {
			Answer answer = (Answer) cell;
			setAnswer(info.batch, answer, x, y, digit);
			setValueInModel(x, y, digit, false);
		}
	}
	
	private void setAnswer(int digit) {
		int x = selected.getCellX();
		int y = selected.getCellY();
		setAnswer(x, y, digit);
	}
	
	private void eraseAnswer() {
		Answer answer = getSelectedAnswer();
		if (answer == null) {
			return;
		}

		if (answer.text != null) {
			answer.text.clearNotes();
			answer.text.setText(Answer.EMPTY);
		}
		setValueInModel(selected.getCellX(), selected.getCellY(), EmptyCell.NO_VALUE, true);
	}
	
	private void unsetAnswer(int value) {
		Answer answer = getSelectedAnswer();
		if (answer == null) {
			return;
		}

		if (answer.text != null) {
			if (notesMode) {
				answer.text.hideNote(value);
			} else {
				answer.text.setText(Answer.EMPTY);
			}
		}
		setValueInModel(selected.getCellX(), selected.getCellY(), value, true);
	}
	
	private Answer getSelectedAnswer() {
		int x = selected.getCellX();
		int y = selected.getCellY();
		if (x < 0 || y < 0) {
			return null;
		}
		
		setSelected(x, y);
		if (info == null || info.grid == null) {
			return null;
		}
		if (y >= info.grid.length) {
			return null;
		}
		if (x >= info.grid[y].length) {
			return null;
		}
		
		GameCell cell = info.grid[y][x];
		if (cell instanceof Answer) {
			return (Answer) cell;
		} else {
			return null;
		}
	}
	
	private void scrollToCellIfNotVisible(int x, int y, boolean force) {
		// scroll to set cursor in the screen center
		float visibleX = content.getVisibleX();
		float visibleY = content.getVisibleY();
		float cursorX = visibleX + x * selected.getWidth() * scale;
		float cursorY = visibleY + y * selected.getHeight() * scale;
		float cursorWidth = selected.getWidth() * scale;
		float cursorHeight = selected.getHeight() * scale;
		
		if (force ||
				cursorX < 0 || cursorY < 0 || 
				cursorX + cursorWidth > getWidthScaled() + 1 || 
				cursorY + cursorHeight > getHeightScaled() + 1) {
			// need to scroll to it
			float destX = visibleX + (getWidthScaled() - cursorWidth) / 2.f - cursorX;
			float destY = visibleY + (getHeightScaled() - cursorHeight) / 2.f - cursorY;
			if (content.getWidthScaled() + destX < getWidthScaled()) {
				destX = getWidthScaled() - content.getWidthScaled();
			} else if (destX > 0) {
				destX = 0;
			}
			if (content.getHeightScaled() + destY < getHeightScaled()) {
				destY = getHeightScaled() - content.getHeightScaled();
			} else if (destY > 0) {
				destY = 0;
			}
			if (!scroller.isFinished()) {
				scroller.abortAnimation();
			}
			int dx = Math.round(destX - content.getVisibleX());
			int dy = Math.round(destY - content.getVisibleY());
			scroller.startScroll(Math.round(content.getX()), Math.round(content.getY()), dx, dy, SCROLL_TO_POSITION_DURATION);
		}
	}
	
	public void setSelected(int x, int y) {
		CursorSprite cursor = getCursorSprite(); 
		cursor.setPosition(x, y);
		scrollToCellIfNotVisible(x, y, false);
	}
	
	public void showNotesInCurrentCell(boolean[] notes) {
		EmptyCell empty = getCurrentCell();
		if (empty == null) {
			Log.w(TAG, "showNotesInCurrentCell: empty cell is null");
			return;
		}
		empty.setValue(EmptyCell.NO_VALUE);
		empty.clearNotes();
		for (int i = 0; i < notes.length; i++) {
			if (notes[i]) {
				empty.setNote(i + 1, true);
			}
		}
		
		int x = selected.getCellX();
		int y = selected.getCellY();
		GameCell cell = info.grid[y][x];
		if (cell instanceof Answer) {
			AnswerText text = getAnswerText(info.batch, (Answer) cell, x, y);
			text.setText(Answer.EMPTY);
			text.clearNotes();
			for (int i = 0; i < notes.length; i++) {
				if (notes[i]) {
					text.showNote(i + 1);
				}
			}
		}
	}
	
	@Override
	public void onDigitSelected(int digit) {
		Log.d(TAG, "onDigitSelected: " + digit);
		if (selected == null || !selected.isVisible()) {
			Log.w(TAG, "onDigitSelected: selected is null");
			return;
		}
		if (info == null) {
			Log.w(TAG, "onDigitSelected: info is null");
			return;
		}
		
		setAnswer(digit);
	}

	@Override
	public void onDigitUnselected(int digit) {
		Log.d(TAG, "onDigitUnselected: " + digit);
		if (selected == null || !selected.isVisible()) {
			Log.w(TAG, "onDigitUnselected: selected is null");
			return;
		}
		if (info == null) {
			Log.w(TAG, "onDigitUnselected: info is null");
			return;
		}
		
		unsetAnswer(digit);
	}
	
	public void erase() {
		if (selected == null || !selected.isVisible()) {
			return;
		}
		if (info == null) {
			return;
		}
		
		eraseAnswer();
	}

	public boolean hint() {
		if (info == null || info.model == null) {
			return false;
		}
		
		Cell[][] cells = info.model.getCells();
		final int width = info.model.getWidth();
		final int height = info.model.getHeight();
		
		EmptyCell withoutValue = null;
		int x = -1;
		int y = -1;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = cells[i][j];
				if (cell instanceof EmptyCell) {
					EmptyCell empty = (EmptyCell) cell;
					if (!empty.hasValue() && !empty.hasNotes()) {
						withoutValue = empty;
						x = j;
						y = i;
						break;
					}
				}
			}
			
			if (withoutValue != null) {
				break;
			}
		}
		
		if (withoutValue != null) {
			setAnswer(x, y, withoutValue.getAnswer());
			Answer answer = (Answer) info.grid[y][x];
			answer.text.registerEntityModifier(new AlphaModifier(1f, 0f, 1f));
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onPuzzleChecked(CheckResult result) {
		if (listener != null) {
			listener.onPuzzleChecked(result);
		}
		
		switch (result) {
		case SOLVED:
			if (selected != null) {
				selected.setVisible(false);
			}
			solved = true;
			break;
			
		case INCOMPLETE:
			break;
			
		case MISTAKE:
			break;
		}
	}
	
	public boolean isSolved() {
		return solved;
	}
	
	public void setCellSelectionListener(CellSelectionListener cellSelectionListener) {
		this.cellSelectionListener = cellSelectionListener;
	}
	
	public void setNotesMode(boolean notesMode) {
		this.notesMode = notesMode;
	}
	
	public void setNotesModeListener(NotesModeListener listener) {
		this.notesModeListener = listener;
	}
}
