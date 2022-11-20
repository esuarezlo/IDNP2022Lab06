package org.idnp.idnp2022lab06.datasource;

import android.graphics.Color;

import org.idnp.idnp2022lab06.entity.SerieEntity;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private static DataSet instance;

    private DataSet() {

    }
    public static DataSet getInstance(){
        if(instance==null)instance=new DataSet();
        return instance;
    }

    public static List<SerieEntity> getSerie() {
        List<SerieEntity> serieEntities = new ArrayList<>();
        serieEntities.add(new SerieEntity("Argentina", 20.7f));
        serieEntities.add(new SerieEntity("Bolivia", 46.6f));
        serieEntities.add(new SerieEntity("Brazil", 28.6f));
        serieEntities.add(new SerieEntity("Canada", 14.5f));
        serieEntities.add(new SerieEntity("Chile", 23.4f));
        serieEntities.add(new SerieEntity("Columbia", 27.4f));
        serieEntities.add(new SerieEntity("Ecuador", 32.9f));
        serieEntities.add(new SerieEntity("Guyana", 28.3f));
        serieEntities.add(new SerieEntity("Mexico", 29));
        serieEntities.add(new SerieEntity("Paraguay", 34.8f));
        serieEntities.add(new SerieEntity("Peru", 32.9f));
        serieEntities.add(new SerieEntity("U.S.A.", 16.7f));
        serieEntities.add(new SerieEntity("Uruguay", 18f));
        serieEntities.add(new SerieEntity("Venezuela", 27.5f));

        return serieEntities;
    }

    public static List<Color> getColorCatalog() {
        List<Color> colors = new ArrayList<>();
        for (int r = 64; r < 255; r = r + 64)
            for (int g = 64; g < 240; g = g + 64)
                for (int b = 16; b < 240; b = b + 80) {
                    Color color = Color.valueOf(r, g, b);
                    colors.add(color);
                }
        return colors;
    }
}
