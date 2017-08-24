package com.ubt.ubtalpha2motiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ubtechinc.cruzr.sdk.motion.MotionRobotApi2;
import com.ubtechinc.cruzr.serverlibutil.aidl.ActionMotorInfo;
import com.ubtechinc.cruzr.serverlibutil.interfaces.ActionMotorFrameListener2;

import java.util.ArrayList;
import java.util.List;

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
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        motionApi2Initialize();
    }

    /**
     * MotionRobotApi2初始化
     */
    private void motionApi2Initialize() {
        Log.i(TAG, "motionApi2Initialize()");
        MotionRobotApi2.getInstance().initialize(this);
        MotionRobotApi2.getInstance().setServiceBindedCallback(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "MotionRobotApi2 is binded");
            }
        });
        MotionRobotApi2.getInstance().setServiceUnbindedCallback(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "MotionRobotApi2 is unbinded");
            }
        });
    }

    private void initViews() {
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
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doEnterReadbackMode() start");
                boolean enterReadMode = MotionRobotApi2.getInstance().enterReadMode();
                Log.i(TAG, "doEnterReadbackMode() end, enterReadMode: " + enterReadMode);
            }
        }.start();
    }

    /**
     * 退出掉电模式
     */
    private void doExitReadbackMode() {
        Log.i(TAG, "doExitReadbackMode()");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doExitReadbackMode() start");
                boolean exitReadMode = MotionRobotApi2.getInstance().exitReadMode();
                Log.i(TAG, "doExitReadbackMode() end, exitReadMode: " + exitReadMode);
            }
        }.start();
    }

    /**
     * 检查掉电模式
     */
    private void doCheckReadbackMode() {
        Log.i(TAG, "doCheckReadbackMode()");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doCheckReadbackMode() start");
                // 1表示进入readMode，-1表示退出readMode，0表示不确定
                int isReadMode = MotionRobotApi2.getInstance().isReadMode();
                Log.i(TAG, "doCheckReadbackMode() end, isReadMode: " + isReadMode);
            }
        }.start();
    }

    /**
     * 获取舵机角度
     */
    private void doGetMotorDegrees() {
        Log.i(TAG, "doGetMotorDegrees()");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doGetMotorDegrees() start");
                int[] motorsId = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
                // 舵机角度或-1
                int[] motorsDegree = new int[motorsId.length];
                MotionRobotApi2.getInstance().getMotorsDegree(motorsId, motorsDegree);
                Log.i(TAG, "doGetMotorDegrees() end");
                for (int i=0;i<motorsId.length;i++) {
                    Log.i(TAG, "motorId: " + motorsId[i] + ", motorDegree: " + motorsDegree[i]);
                }
            }
        }.start();
    }

    private int _setMotorDegreesCount = 0;

    // 帧动作id，用于舵机运动取消
    private int[] mFrameId = new int[1];

    /**
     * 设置舵机角度
     */
    private void doSetMotorDegrees() {
        Log.i(TAG, "doSetMotorDegrees()");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doSetMotorDegrees() start");
                List<ActionMotorInfo> motorInfos = new ArrayList<ActionMotorInfo>();

                final int[] ids = new int[]{2, 5};
                ++_setMotorDegreesCount;

                for (int i = 0; i < ids.length; i++) {
                    ActionMotorInfo actionMotorInfo = new ActionMotorInfo();
                    // 舵机最小角度值，实际每个舵机会有不同
                    int lowerLimitAngle = 0;
                    // 舵机最大角度值，实际每个舵机会有不同
                    int upperLimitAngle = 360;
                    int angle = _setMotorDegreesCount % 2 == 0 ? lowerLimitAngle : upperLimitAngle;
                    int delayMilli = 0;
                    int runMilli = 5000;

                    actionMotorInfo.setMotorId(ids[i]);
                    actionMotorInfo.setAbsoluteDegree(angle);
                    actionMotorInfo.setDelayMilli(delayMilli);
                    actionMotorInfo.setRunMilli(runMilli);

                    motorInfos.add(actionMotorInfo);
                }



                ActionMotorFrameListener2 listener2 = new ActionMotorFrameListener2() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "doSetMotorDegrees() onStart()");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "doSetMotorDegrees() onComplete()");
                    }

                    @Override
                    public void onError(int i) {
                        Log.i(TAG, "doSetMotorDegrees() onError() i: " + i);
                    }

                    @Override
                    public void onCancel() {
                        Log.i(TAG, "doSetMotorDegrees() onCancel()");
                    }
                };
                mFrameId[0] = MotionRobotApi2.getInstance().controlMotors2(motorInfos, listener2);
                Log.i(TAG, "doSetMotorDegrees() end, mFrameId[0]: " + mFrameId[0]);
            }
        }.start();
    }

    /**
     * 取消舵机运动
     */
    private void doCancelMotorDegrees() {
        Log.i(TAG, "doCancelMotorDegrees()");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "doCancelMotorDegrees() start");
                final boolean isCancelled = MotionRobotApi2.getInstance().cancelActionMotorFrame2(mFrameId[0]);
                Log.i(TAG, "doCancelMotorDegrees() end, isCancelled: " + isCancelled);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        motionApi2Destroy();
    }

    private void motionApi2Destroy() {
        Log.i(TAG, "motionApi2Destroy()");
        MotionRobotApi2.getInstance().destroy();
    }
}
