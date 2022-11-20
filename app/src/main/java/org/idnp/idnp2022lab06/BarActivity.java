package org.idnp.idnp2022lab06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.idnp.idnp2022lab06.chart.BarChart;
import org.idnp.idnp2022lab06.chart.LeyendChart;
import org.idnp.idnp2022lab06.datasource.DataSet;

public class BarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        BarChart barChart=findViewById(R.id.barChart);
        LeyendChart leyendChart =findViewById(R.id.barChartLeyend);

        barChart.setSerie(DataSet.getSerie());
        barChart.setColorCatalog(DataSet.getColorCatalog());

        leyendChart.setLeyendData(barChart.getLeyend());
    }
}