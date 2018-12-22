package cn.ian2018.unitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.amber.unity.UnitySurface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        FrameLayout contentLayout = findViewById(R.id.content_layout);
        contentLayout.addView(new UnitySurface(this));
    }
}
