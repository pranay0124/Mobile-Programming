package com.example.trackit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StatsActivity extends AppCompatActivity {
    private ImageButton BPTable, DiabetesTable, BPGraph, DiabetesGraph;
    String bp_1 = "1";
    String sugar_1 = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        BPTable = (ImageButton) findViewById(R.id.button_bp_table);
        DiabetesTable = (ImageButton) findViewById(R.id.button_diabetes_table);
        BPGraph = (ImageButton) findViewById(R.id.button_bp_graph);
        DiabetesGraph = (ImageButton) findViewById(R.id.button_diabetes_graph);

        BPTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, BPTable.class);
                intent.putExtra("Table_Type", bp_1);
                startActivity(intent);
            }
        });

        DiabetesTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, BPTable.class);
                intent.putExtra("Table_Type", sugar_1);
                startActivity(intent);
            }
        });

        BPGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, BPGraph.class);
                startActivity(intent);
            }
        });
//
        DiabetesGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, DiabetesGraph.class);
                startActivity(intent);
            }
        });
    }
}
