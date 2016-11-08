package cn.gavinliu.edgeviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void demo(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }

    public void percent(View view) {
        Intent intent = new Intent(this, ScalePercentDemoActivity.class);
        startActivity(intent);
    }

    public void percent2(View view) {
        Intent intent = new Intent(this, ScalePercentDemoActivity2.class);
        startActivity(intent);
    }

    public void percent3(View view) {
        Intent intent = new Intent(this, ScalePercentDemoActivity3.class);
        startActivity(intent);
    }

    public void percent4(View view) {
        Intent intent = new Intent(this, ScalePercentDemoActivity4.class);
        startActivity(intent);
    }

    public void ticket(View view) {
        Intent intent = new Intent(this, TicketDemoActivity.class);
        startActivity(intent);
    }

}
