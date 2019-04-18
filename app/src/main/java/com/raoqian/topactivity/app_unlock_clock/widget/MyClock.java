package com.raoqian.topactivity.app_unlock_clock.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raoqian on 2019/4/10.
 */

public class MyClock extends View implements View.OnClickListener {
    public MyClock(Context context) {
        this(context, null, 0);
    }

    public MyClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBound = new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(60);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
        time = System.currentTimeMillis();
        setOnClickListener(this);
    }

    long time = 0;
    Rect mBound;
    Paint mPaint;
    String mText = "";
    //中点坐标
    int mX = 0;
    int mY = 0;
    int radias = 0;//圆半径

    /**
     * 比onDraw先执行
     * <p>
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
     * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p>
     * 它常用的三个函数：
     * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
     * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
     * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        Log.w("YView", "---minimumWidth = " + minimumWidth + "");
        Log.w("YView", "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        int showSize = Math.min(width, height);
        this.radias = Math.min(width - getPaddingLeft() - getPaddingRight(), height - getPaddingTop() - getPaddingBottom()) / 2;
        this.mY = height / 2;
        this.mX = width / 2;
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.w("YViewWidth", "---speSize = " + specSize + "");


        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();

                Log.w("YViewWidth", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.w("YViewWidth", "---speMode = EXACTLY  match_parent ");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.w("YViewWidth", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.w("YViewHeight", "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (-mPaint.ascent() + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
                Log.w("YViewHeight", "---speMode = AT_MOST  wrap_content");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                Log.w("YViewHeight", "---speSize = EXACTLY  100dp");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                Log.w("YViewHeight", "---speSize = UNSPECIFIED");
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值

                break;
        }
        return defaultHeight;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.e("MyClock.LINE", "154 onFocusChanged " + gainFocus);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        isRunning = hasWindowFocus;
        Log.e("MyClock", "onWindowFocusChanged.hasWindowFocus = " + hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.e("MyClock.LINE", "onVisibilityChanged " + (visibility == View.VISIBLE));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawRunningText(canvas);
        drawTextAndLine(canvas);
        canvas.drawCircle(mX, mY, 10, mPaint);
        drawTime(canvas);
        Log.e("MyClock.LINE", "164 isRunning = " + isRunning);
        if (isRunning) {
            postInvalidateDelayed(200);
        }

    }

    private void drawRunningText(Canvas canvas) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd\nHH:mm:ss", Locale.CHINA);
        String time = format.format(new Date());
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(60);
        canvas.drawText(time, mX - mPaint.measureText(time) / 2, mY, mPaint);
    }

    private void drawTime(Canvas canvas) {
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        int secondDegree = (int) (second / 60F * 360);
        int minDegree = (int) (min / 60F * 360);
        int hourDegree = (int) (hour / 12F * 360 + min / 2);
        int hourWidth = 10;
        int hourHeight = (int) (radias * 0.4F);
        int minWidth = 6;
        int minHeight = (int) (radias * 0.6F);
        int secondWidth = 4;
        int secondHeight = (int) (radias * 0.8F);
        canvas.rotate(hourDegree, mX, mY);
        canvas.drawRect(mX - hourWidth, mY - hourHeight, mX + hourWidth, mY, mPaint);
        canvas.rotate(-hourDegree, mX, mY);

        mPaint.setColor(Color.RED);
        canvas.rotate(minDegree, mX, mY);
        canvas.drawRect(mX - minWidth, mY - minHeight, mX + minWidth, mY, mPaint);
        canvas.rotate(-minDegree, mX, mY);

        mPaint.setColor(Color.BLUE);
        canvas.rotate(secondDegree, mX, mY);
        canvas.drawRect(mX - secondWidth, mY - secondHeight, mX + secondWidth, mY, mPaint);
        canvas.rotate(-secondDegree, mX, mY);


    }

    private void drawCircle(Canvas canvas) {//画圆形表盘
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(mX, mY, radias - 3, mPaint);
    }

    private void drawTextAndLine(Canvas canvas) {//画刻度以及文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        int lagerHeight = 30;
        int lagerWidth = 8;
        int smallHeight = 20;
        int smallWidth = 6;
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                mPaint.setStrokeWidth(2 * lagerWidth);
                mPaint.setTextSize(30);
                canvas.drawRect(
                        mX - lagerWidth / 2, mY - radias,
                        mX + lagerWidth / 2, mY - radias + lagerHeight, mPaint);
            } else {
                mPaint.setStrokeWidth(2 * smallWidth);
                mPaint.setTextSize(20);
                canvas.drawRect(
                        mX - smallWidth / 2, mY - radias,
                        mX + smallWidth / 2, mY - radias + smallHeight, mPaint);
            }
            String text = "" + i;
            float textHeight = 1.2F * mPaint.measureText("0");
            int textX = (int) (mX - mPaint.measureText(text) / 2);
            int textY = (int) (mY - radias + lagerHeight + 1.5F * textHeight);
            int textRotateX = mX;
            int textRotateY = (int) (textY - textHeight / 2);
            canvas.rotate(-30 * i, textRotateX, textRotateY);
            canvas.drawText(text, textX, textY, mPaint);
            canvas.rotate(30 * i, textRotateX, textRotateY);
            canvas.rotate(30, mX, mY);

        }
    }


    volatile boolean isRunning = true;

    @Override
    public void onClick(View v) {
        isRunning = !isRunning;
    }
}
