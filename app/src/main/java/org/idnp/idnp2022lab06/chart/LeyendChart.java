package org.idnp.idnp2022lab06.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.idnp.idnp2022lab06.entity.LeyendEntity;

import java.util.List;

public class LeyendChart extends View {
    private List<LeyendEntity> leyend;
    private Paint paintText;
    private static final float width_box = 40;
    private static final float height_box = 40;
    private static final float vertical_separation = 10;

    public LeyendChart(Context context) {
        super(context);
    }

    public LeyendChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLeyendData(List<LeyendEntity> leyend) {
        this.leyend = leyend;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (leyend == null) return;

        Paint paint = new Paint();
        paintText = new Paint();
        paintText.setTextSize(34f);

        float x = 0;
        float y = 0;

        for (LeyendEntity leyendEntity : leyend) {

            String category = leyendEntity.getCategory();
            Color value = leyendEntity.getColor();

            paint.setColor(value.toArgb());
            canvas.drawRect(x, y, x + width_box, y + height_box, paint);
            canvas.drawText(category, x + width_box + 5, y + height_box, paintText);
            y = y + height_box + vertical_separation;
        }

    }
}
