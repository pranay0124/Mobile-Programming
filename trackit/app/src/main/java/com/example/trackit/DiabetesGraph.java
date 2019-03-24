package com.example.trackit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiabetesGraph extends AppCompatActivity {
    DatabaseReference mRef;
    GraphView graphView;
    PointsGraphSeries series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_graph);
        graphView = (GraphView) findViewById(R.id.graph_diabetes);
        series = new PointsGraphSeries();
        graphView.addSeries(series);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getName()).child("Diabetes");

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;



                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
//                    long x = 0;
                    Table table = myDataSnapshot.getValue(Table.class);
                    String x =table.getTimestamp();
                    x = x.replace("-","");
                    x = x.substring(4,x.length());
//                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//                    try {
//                        Date d = f.parse(table.getTimestamp());
//                        x = d.getTime();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    long x1 = Long.parseLong(x);
                    long y = Long.parseLong(table.getValue());
                    dp[index] = new DataPoint(x1,y);
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
