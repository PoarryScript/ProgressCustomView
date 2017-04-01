package com.poarry.dimain.progresscustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.poarry.dimain.progresscustomview.view.AnimProgressView;
import com.poarry.dimain.progresscustomview.view.ProgressView;

public class MainActivity extends AppCompatActivity {

    private ProgressView progressView;
    private AnimProgressView animProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = (ProgressView) findViewById(R.id.normal_progress);
        animProgressView = (AnimProgressView) findViewById(R.id.anim_progress);
        progressView.setValue(30);
        animProgressView.setValue(78);
    }
}
