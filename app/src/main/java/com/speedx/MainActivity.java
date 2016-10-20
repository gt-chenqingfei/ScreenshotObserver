package com.speedx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.speedx.screenshot.ScreenshotObserver;

public class MainActivity extends AppCompatActivity implements ScreenshotObserver.OnScreenshotListener {
    private ScreenshotObserver mScreenshotObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScreenshotObserver = new ScreenshotObserver(new Handler(getMainLooper()), this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return;
        }
        mScreenshotObserver.subscript(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScreenshotObserver.unSubscript();
    }

    @Override
    public void onScreenshot(String path) {
        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
        mScreenshotObserver.subscript(this);

    }
}
