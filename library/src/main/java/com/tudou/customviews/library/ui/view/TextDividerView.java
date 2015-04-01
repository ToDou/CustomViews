package com.tudou.customviews.library.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tudou on 15-4-1.
 */
public class TextDividerView extends View {

  private Paint mTextPaint;
  private Paint mDividerPaint;
  private RectF mDividerRectFPre;
  private RectF mDividerRectFAfter;

  private int mTextColor;
  private int mDividerColor;
  private int mColor;

  private float mTextSize;
  private float mRectHeight;

  private float mTextWidth;
  private float mTextBaseX;
  private float mTextBaseY;

  private String mFix = " ";
  private String mText = "or";

  public TextDividerView(Context context) {
    this(context, null);
  }

  public TextDividerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TextDividerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    mTextColor = getResources().getColor(android.R.color.darker_gray);
    mDividerColor = getResources().getColor(android.R.color.darker_gray);
    mColor = mTextColor;
    mTextSize = sp2px(10);
    mRectHeight = dp2px(1.0f);

    initPainters();
    initRectFs();
  }

  private void initRectFs() {
    mDividerRectFAfter = new RectF(0, 0, 0, 0);
    mDividerRectFPre = new RectF(0, 0, 0, 0);
  }

  private void initPainters() {
    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setTextSize(mTextSize);
    mTextPaint.setColor(mTextColor);

    mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDividerPaint.setColor(mDividerColor);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    calculateRectFs();

    canvas.drawRect(mDividerRectFPre, mDividerPaint);
    canvas.drawRect(mDividerRectFAfter, mDividerPaint);

    canvas.drawText(mText, mTextBaseX, mTextBaseY, mTextPaint);
  }

  private void calculateRectFs() {
    mText = mFix + mText + mFix;
    mTextWidth = mTextPaint.measureText(mText);

    mTextBaseY =
        (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));

    mDividerRectFPre.left = getPaddingLeft();
    mDividerRectFPre.top = getHeight() / 2 - mRectHeight / 2;
    mDividerRectFPre.right = getWidth() / 2 - getPaddingLeft() - mTextWidth / 2;
    mDividerRectFPre.bottom = getHeight() / 2 + mRectHeight / 2;

    mTextBaseX = mDividerRectFPre.right;

    mDividerRectFAfter.left = getWidth() / 2 + mTextWidth / 2;
    mDividerRectFAfter.top = getHeight() / 2 - mRectHeight / 2;
    mDividerRectFAfter.right = getWidth() - getPaddingRight();
    mDividerRectFAfter.bottom = getHeight() / 2 + mRectHeight / 2;
  }

  public float dp2px(float dp) {
    final float scale = getResources().getDisplayMetrics().density;
    return dp * scale + 0.5f;
  }

  public float sp2px(float sp) {
    final float scale = getResources().getDisplayMetrics().scaledDensity;
    return sp * scale;
  }

}
