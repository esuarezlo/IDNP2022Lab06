package org.idnp.idnp2022lab06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.idnp.idnp2022lab06.chart.SimpleClock;

public class ClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        Button btnReset=findViewById(R.id.btnReset);
        SimpleClock simpleClock=findViewById(R.id.simpleClock);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleClock.reset();
            }
        });

    }
}