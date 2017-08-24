package com.ubt.ubtalpha2motiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mBtnEnterReadbackMode;
    private Button mBtnExitReadbackMode;
    private Button mBtnCheckReadbackMode;
    private Button mBtnGetMotorDegrees;
    private Button mBtnSetMotorDegrees;
    private Button mBtnCancelMotorDegrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnEnterReadbackMode = (Button) findViewById(R.id.btnEnterReadbackMode);
        mBtnEnterReadbackMode.setOnClickListener(this);

        mBtnExitReadbackMode = (Button) findViewById(R.id.btnExitReadbackMode);
        mBtnExitReadbackMode.setOnClickListener(this);

        mBtnCheckReadbackMode = (Button) findViewById(R.id.btnCheckReadbackMode);
        mBtnCheckReadbackMode.setOnClickListener(this);

        mBtnGetMotorDegrees = (Button) findViewById(R.id.btnGetMotorDegrees);
        mBtnGetMotorDegrees.setOnClickListener(this);

        mBtnSetMotorDegrees = (Button) findViewById(R.id.btnSetMotorDegrees);
        mBtnSetMotorDegrees.setOnClickListener(this);

        mBtnCancelMotorDegrees = (Button) findViewById(R.id.btnCancelMotorDegrees);
        mBtnCancelMotorDegrees.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnEnterReadbackMode) {
            doEnterReadbackMode();
        } else if (view == mBtnExitReadbackMode) {
            doExitReadbackMode();
        } else if (view == mBtnCheckReadbackMode) {
            doCheckReadbackMode();
        } else if (view == mBtnGetMotorDegrees) {
            doGetMotorDegrees();
        } else if (view == mBtnSetMotorDegrees) {
            doSetMotorDegrees();
        } else if (view == mBtnCancelMotorDegrees) {
            doCancelMotorDegrees();
        }
    }

    /**
     * 进入掉电模式
     */
    private void doEnterReadbackMode() {
        Log.i(TAG, "doEnterReadbackMode()");
    }

    /**
     * 退出掉电模式
     */
    private void doExitReadbackMode() {
        Log.i(TAG, "doExitReadbackMode()");
    }

    /**
     * 检查掉电模式
     */
    private void doCheckReadbackMode() {
        Log.i(TAG, "doCheckReadbackMode()");
    }

    /**
     * 获取舵机角度
     */
    private void doGetMotorDegrees() {
        Log.i(TAG, "doGetMotorDegrees()");
    }

    /**
     * 设置舵机角度
     */
    private void doSetMotorDegrees() {
        Log.i(TAG, "doSetMotorDegrees()");
    }

    /**
     * 取消舵机运动
     */
    private void doCancelMotorDegrees() {
        Log.i(TAG, "doCancelMotorDegrees()");
    }
}
