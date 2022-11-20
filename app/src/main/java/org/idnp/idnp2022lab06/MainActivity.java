package org.idnp.idnp2022lab06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBarChart = findViewById(R.id.btnBarChart);
        Button btnRingChart = findViewById(R.id.btnRingChart);

        btnBarChart.setOnClickListener(btnBarChart_Event);
        btnRingChart.setOnClickListener(btnRingChart_Event);
    }

    private View.OnClickListener btnBarChart_Event = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), BarActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnRingChart_Event = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), RingActivity.class);
            startActivity(intent);
        }
    };
}