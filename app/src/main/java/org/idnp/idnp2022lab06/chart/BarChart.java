package org.idnp.idnp2022lab06.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.idnp.idnp2022lab06.entity.LeyendEntity;
import org.idnp.idnp2022lab06.entity.SerieEntity;

import java.util.List;

public class BarChart extends View {
    private static final String TAG = "BartChart";
    private List<SerieEntity> serie;
    private float width_bar;
    private final float separation = 10;
    private float originY;
    private float originX;
    private float marginX;
    private Paint paintBar;
    private Paint paintText;
    private Paint paintAxis;
    private Paint paintHorizontalLine;
    private float WIDTH;
    private float HEIGHT;
    private float MAX_SERIE_VALUE;
    private float scale_factor;
    private List<Color> colors;
    private LeyendHelper leyendHelper;

    public BarChart(Context context) {
        super(context);
        init();
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
        String layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");

        layout_height = layout_height.replace("dip", "");
        layout_width = layout_width.replace("dip", "");

        float density = getResources().getDisplayMetrics().density;
        HEIGHT = density * Float.parseFloat(layout_height);
        WIDTH = density * Float.parseFloat(layout_width);


        Log.d(TAG, "WIDTH:" + WIDTH + "," + originX);
        Log.d(TAG, "HEIGHT:" + HEIGHT + "," + originY);


        init();

    }


    private void init() {
        leyendHelper = new LeyendHelper();
        originX = 0f;
        originY = HEIGHT;
        marginX = 80f;

        paintBar = new Paint();
        paintBar.setColor(Color.CYAN);

        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(26);

        paintAxis = new Paint();
        paintAxis.setColor(Color.BLACK);
        paintAxis.setStrokeWidth(4f);

        paintHorizontalLine = new Paint();
        paintHorizontalLine.setColor(Color.parseColor("#1EB2FC"));
        paintHorizontalLine.setStrokeWidth(2f);


    }

    public void setSerie(List<SerieEntity> serie) {
        this.serie = serie;
        for (SerieEntity s : serie) {
            if (MAX_SERIE_VALUE < s.getValue())
                MAX_SERIE_VALUE = s.getValue();
        }

        scale_factor = HEIGHT / MAX_SERIE_VALUE;
        width_bar = (WIDTH - marginX - separation*serie.size()) / serie.size();

        invalidate();
    }

    public void setColorCatalog(List<Color> colors) {
        this.colors = colors;
        invalidate();
    }

    public List<LeyendEntity> getLeyend() {
        return leyendHelper.getLeyendItems();
    }

    private float drawBar(Canvas canvas, float position_x, String item, float value, Color colour) {

        paintBar.setColor(colour.toArgb());
        float xf = position_x;
        float yf = originY - value;
        float xr = position_x + width_bar;
        float yr = originY;

        canvas.drawRect(xf, yf, xr, yr, paintBar);

        return xr;
    }

    private void drawAxis(Canvas canvas) {
        //axis Y
        //canvas.drawLine(originX, originY - HEIGHT, originX, originY, paintAxis);
        //axis X
        //canvas.drawLine(originX, originY, originX + WIDTH, originY, paintAxis);

    }

    private void drawAxeYLabel(Canvas canvas, int div) {
        float x = marginX;
        float y = originY;

        while (y > (originY - HEIGHT)) {
            String value = String.format("%.02f", (HEIGHT - y) / scale_factor);
            canvas.drawLine(x, y, WIDTH, y, paintHorizontalLine);
            canvas.drawText(value, x - marginX, y, paintText);

            y = y - div;

        }

    }

    private void drawBars(Canvas canvas) {
        float x = originX + marginX;
        float value;
        Color color;
        String category;

        for (int i = 0; i < serie.size(); i++) {
            category = serie.get(i).getCategory();
            value = serie.get(i).getValue();
            color = colors.get(i);
            x = drawBar(canvas, x + separation, category, value * scale_factor, color);
            leyendHelper.addLeyendItem(category, color);
            Log.d(TAG, category);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (colors == null) return;
        drawAxeYLabel(canvas, 40);
        drawBars(canvas);
        drawAxis(canvas);
    }
}
