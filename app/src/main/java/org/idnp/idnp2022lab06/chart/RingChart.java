package org.idnp.idnp2022lab06.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.idnp.idnp2022lab06.entity.LeyendEntity;
import org.idnp.idnp2022lab06.entity.SerieEntity;

import java.util.List;

public class RingChart extends View {
    private static final String TAG = "RingChart";
    private float WIDTH;
    private float HEIGHT;
    private Paint sectionPaint;
    private RectF arcRectangle;
    private List<SerieEntity> serie;
    private List<Color> colorCatalog;
    private float TOTAL;
    private Paint circlePaint;
    private Paint labelPaint;
    private LeyendHelper leyendHelper;

    public RingChart(Context context) {
        super(context);
        init();
    }

    public RingChart(Context context, @Nullable AttributeSet attrs) {
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
    }

    private void init() {

        leyendHelper=new LeyendHelper();
        sectionPaint = new Paint();
        circlePaint = new Paint();
        labelPaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(30f);

        //Center graphic
        float ring_squard_size = Math.min(WIDTH, HEIGHT);
        float originX = (WIDTH - ring_squard_size) / 2;
        float originY = (HEIGHT - ring_squard_size) / 2;

        arcRectangle = new RectF(originX, originY, ring_squard_size + originX, ring_squard_size + originY);

    }

    public void setSerie(@NonNull List<SerieEntity> serie) {
        this.serie = serie;

        for (int i = 0; i < serie.size(); i++) {
            TOTAL += serie.get(i).getValue();
        }

        invalidate();
    }

    public void setColorCatalog(List<Color> colorCatalog) {
        this.colorCatalog = colorCatalog;
        invalidate();
    }

    public List<LeyendEntity> getLeyend() {
        return leyendHelper.getLeyendItems();
    }

    private void drawSection(Canvas canvas, float startAngle, float sweepAngle, float value, Color color) {

        sectionPaint.setColor(color.toArgb());
        float[] xy = xyLabel(startAngle, sweepAngle);
        float x = xy[0];
        float y = xy[1];

        canvas.drawArc(arcRectangle, startAngle, sweepAngle, true, sectionPaint);
        canvas.drawText(String.format("%.02f", value) + "%", x, y, labelPaint);
    }

    private float[] xyLabel(float startAngle, float sweepAngle) {
        float[] xy = new float[2];
        float r = (arcRectangle.width() / 2);
        float angle = (startAngle + sweepAngle / 2) * (float) Math.PI / 180f;
        float factor = 36f;
        float x = WIDTH / 2 + (float) Math.cos(angle) * 0.8f * r - factor;
        float y = HEIGHT / 2 + (float) Math.sin(angle) * 0.8f * r;

        xy[0] = x;
        xy[1] = y;

        return xy;
    }

    private void drawSections(Canvas canvas) {
        float startAngle = 0;
        float sweepAngle = 0;
        float value = 0;
        Color color;

        for (int i = 0; i < serie.size(); i++) {
            color = colorCatalog.get(i);
            value = serie.get(i).getValue() / TOTAL;
            sweepAngle = 360 * value;
            drawSection(canvas, startAngle, sweepAngle, value * 100, color);
            startAngle += sweepAngle;

            leyendHelper.addLeyendItem(serie.get(i).getCategory(), color);
        }
    }

    private void drawInnerCircle(Canvas canvas) {
        float centerX = arcRectangle.centerX();
        float centerY = arcRectangle.centerY();
        float r = (Math.min(arcRectangle.height(), arcRectangle.width()) / 2f) * 0.4f;

        canvas.drawCircle(centerX, centerY, r, circlePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (serie == null) return;
        drawSections(canvas);
        drawInnerCircle(canvas);

    }


}
