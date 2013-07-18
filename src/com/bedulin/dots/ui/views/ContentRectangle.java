//package com.bedulin.dots.ui.views;
//
//import org.andengine.entity.primitive.Rectangle;
//
//public class ContentRectangle extends Rectangle {
//		public ContentRectangle(float pX, float pY, float pWidth, float pHeight,
//				IRectangleVertexBufferObject pRectangleVertexBufferObject) {
//			super(pX, pY, pWidth, pHeight, pRectangleVertexBufferObject);
//		}
//
//		@Override
//		public void setScaleCenter(float centerX, float centerY) {
//			if (centerX == getScaleCenterX() && centerY == getScaleCenterY()) {
//				return;
//			}
//			float offsetX = (1 - getScaleX()) * (centerX - getScaleCenterX());
//			float offsetY = (1 - getScaleY()) * (centerY - getScaleCenterY());
//			super.setScaleCenter(centerX, centerY);
//			setPosition(getX() - offsetX , getY() - offsetY);
//		}
//
//		public float getVisibleX() {
//			return getX() + getScaleCenterX() * (1 - getScaleX());
//		}
//
//		public float getVisibleY() {
//			return getY() + getScaleCenterY() * (1 - getScaleY());
//		}
//
//		public void setPositionOffset(float dx, float dy) {
//			setPosition(getX() + dx , getY() + dy);
//		}
//
//		public void setVisiblePosition(float x, float y) {
//			float scale = 1 - getScaleX();
//			x -= getScaleCenterX() * scale;
//			y -= getScaleCenterY() * scale;
//			setPosition(x, y);
//		}
//	}