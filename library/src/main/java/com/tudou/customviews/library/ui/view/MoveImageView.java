package com.tudou.customviews.library.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;

/**
 * Created by tudou on 15-4-3.
 */
public class MoveImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

  private boolean mOnce;
  private boolean isAnim;

  private AnimatorSet mAnimatorSet;
  private ValueAnimator mCurrentAnimator;

  private int width;
  private int height;
  private int drawableWidth;
  private int drawableHeight;

  private Matrix mMatrix;

  private enum Way {L2R, R2L, B2T, T2B}

  private ArrayList<Way> mWays;

  public MoveImageView(Context context) {
    this(context, null);
  }

  public MoveImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    isAnim = true;
    mAnimatorSet = new AnimatorSet();
    mWays = new ArrayList<>();
    mMatrix = this.getImageMatrix();
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    getViewTreeObserver().addOnGlobalLayoutListener(this);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      getViewTreeObserver().removeGlobalOnLayoutListener(this);
    } else {
      getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
  }

  @Override public void onGlobalLayout() {
    if (!mOnce) {
      width = getWidth();
      height = getHeight();

      Drawable d = getDrawable();
      if (d == null) {
        return;
      }
      drawableWidth = d.getIntrinsicWidth();
      drawableHeight = d.getIntrinsicHeight();
      if (drawableHeight > height && drawableWidth > width) {
        startImageAnimation();
        mMatrix = this.getImageMatrix();
      }
      mOnce = true;
    }
  }

  private void startImageAnimation() {
    postDelayed(new Runnable() {
      @Override public void run() {
        getRandomWayAndMove();
      }
    }, 1000);
  }

  private void getRandomWayAndMove() {
    int i = (int) (Math.random() * 3);
    int j = (int) (Math.random() * 3);
    mWays.add(0, Way.values()[i]);
    mWays.add(1, Way.values()[j]);
    createAnimator2();
  }

  private void createAnimator2() {
    RectF rectF = getMatrixRectF();
    ValueAnimator animator = new ValueAnimator();
    if (rectF.left < 0) {
      animator = ValueAnimator.ofFloat(rectF.left, 0);
    }
    if (rectF.right > width) {
      animator = ValueAnimator.ofFloat(rectF.left, rectF.left + width - rectF.right);
    }

  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()

  {
    private float preValue = 0;
    @Override public void onAnimationUpdate (ValueAnimator animation){
    float value = (Float) animation.getAnimatedValue();
    //mMatrix.reset();
      mMatrix.postTranslate(value - preValue, 0);
      preValue = value;
      setImageMatrix(mMatrix);
  }
  }

  );
  animator.addListener(new

  AnimatorListenerAdapter() {

    @Override public void onAnimationEnd (Animator animation){
    }

    @Override public void onAnimationCancel (Animator animation){
    }
  }

  );
  animator.setDuration(2000);
  animator.setInterpolator(new

  AccelerateDecelerateInterpolator()

  );
  animator.start();

}

  private RectF getMatrixRectF() {
    Matrix matrix = mMatrix;
    RectF rectF = new RectF();

    Drawable d = getDrawable();
    if (d != null) {
      rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
      matrix.mapRect(rectF);
    }
    return rectF;
  }

  private void applyScaleOnMatrix(boolean mIsPortrait) {
    int drawableSize = mIsPortrait ? drawableHeight : drawableWidth;
    int imageViewSize = mIsPortrait ? height : width;
    float scaleFactor = (float) imageViewSize / (float) drawableSize;

    mMatrix.postScale(scaleFactor, scaleFactor);
  }
}
