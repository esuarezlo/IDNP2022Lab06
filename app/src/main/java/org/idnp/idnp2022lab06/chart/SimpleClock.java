package org.idnp.idnp2022lab06.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SimpleClock extends View {
    private static final String TAG = "RingChart";
    private float WIDTH;
    private float HEIGHT;
    private RectF arcRectangle;
    private RectF minuteRectF;
    private RectF hourRectF;
    private Paint inCirclePaint;
    private Paint labelPaint;
    private Paint outCirclePaint;
    private Paint minutePaint;
    private Paint hourPaint;
    private float minuteAngle = 0;
    private float hourAngle = 0;
    private float centerX;
    private float centerY;
    private float r1;
    private float r2;

    public SimpleClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        String layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        String layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");

        layout_height = layout_height.replace("dip", "");
        layout_width = layout_width.replace("dip", "");

        float density = getResources().getDisplayMetrics().density;
        HEIGHT = density * Float.parseFloat(layout_height);
        WIDTH = density * Float.parseFloat(layout_width);
        Log.d(TAG, "WIDTH:" + WIDTH + ",HEIGHT:" + HEIGHT);
        init();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "rotation");
                rotate();
            }
        });

    }

    private void init() {
        hourPaint = new Paint();
        minutePaint = new Paint();
        outCirclePaint = new Paint();
        inCirclePaint = new Paint();
        labelPaint = new Paint();

        inCirclePaint.setColor(Color.WHITE);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(30f);
        minutePaint.setColor(Color.RED);
        hourPaint.setColor(Color.BLUE);
        outCirclePaint.setColor(Color.MAGENTA);

        //Center graphic
        float ring_squard_size = Math.min(WIDTH, HEIGHT);
        float originX = (WIDTH - ring_squard_size) / 2;
        float originY = (HEIGHT - ring_squard_size) / 2;

        arcRectangle = new RectF(originX, originY, ring_squard_size + originX, ring_squard_size + originY);

        centerX = arcRectangle.centerX();
        centerY = arcRectangle.centerY();
        r1 = (Math.min(arcRectangle.height(), arcRectangle.width()) / 2f) * 0.6f;
        r2 = (Math.min(arcRectangle.height(), arcRectangle.width()) / 2f);

        minuteRectF = new RectF(centerX - 8, centerY - r2, centerX + 8, centerY);
        hourRectF = new RectF(centerX - 8, centerY - r1, centerX + 8, centerY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawClock(canvas);
        drawNumbers(canvas);
        drawClockHour(canvas);
        drawClockMinute(canvas);

    }

    public void reset() {
        minuteAngle = 0;
        hourAngle = 0;
        invalidate();
    }

    private float[] xyLabel(float angle) {
        float[] xy = new float[2];
        float r = (arcRectangle.width() / 2);
        float angle_rad = (angle) * (float) Math.PI / 180f;
        float x = WIDTH / 2 + (float) Math.cos(angle_rad) * 0.8f * r;
        float y = HEIGHT / 2 + (float) Math.sin(angle_rad) * 0.8f * r;

        xy[0] = x;
        xy[1] = y;

        return xy;
    }

    private void rotate() {
        minuteAngle = minuteAngle + 60;
        int factor = (int) minuteAngle / 360;
        hourAngle = 30 * factor;

        float[] xy = xyLabel(minuteAngle);
        minuteRectF = new RectF(centerX - 8, centerY - r2, centerX + 8, centerY);
        hourRectF = new RectF(centerX - 8, centerY - r1, centerX + 8, centerY);

        Log.d(TAG, "minuteAngle:" + minuteAngle);
        Log.d(TAG, "hourAngle:" + hourAngle);
        invalidate();
    }

    private void drawClockHour(Canvas canvas) {
        canvas.save();
        canvas.rotate(hourAngle, centerX, centerY);
        canvas.drawRect(hourRectF, hourPaint);
        canvas.restore();
    }

    private void drawClockMinute(Canvas canvas) {
        canvas.save();
        canvas.rotate(minuteAngle, centerX, centerY);
        canvas.drawRect(minuteRectF, minutePaint);
        canvas.restore();

    }

    private void drawClock(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, r2, outCirclePaint);
        canvas.drawCircle(centerX, centerY, r1, inCirclePaint);
    }

    private void drawNumbers(Canvas canvas) {

        float[] xy = xyLabel(0f);
        canvas.drawText("3", xy[0], xy[1], labelPaint);

        xy = xyLabel(90f);
        canvas.drawText("6", xy[0], xy[1], labelPaint);

        xy = xyLabel(180f);
        canvas.drawText("9", xy[0], xy[1], labelPaint);

        xy = xyLabel(270f);
        canvas.drawText("12", xy[0], xy[1], labelPaint);
    }
}
