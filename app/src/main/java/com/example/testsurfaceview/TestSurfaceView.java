package com.example.testsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    MyThread myThread;
    public float x=0;
    public float y=0;
    public float forx=1;
    public float fory=1;
    public float radius = 5;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        myThread = new MyThread();
        myThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        myThread.perm = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        radius=0;
        x=0;
        y=0;
        x = event.getX();
        y = event.getY();
        forx=x;
        fory=y;

        return super.onTouchEvent(event);
    }

    class MyThread extends Thread {
        boolean perm = true;
        int viewWidth = getHolder().getSurfaceFrame().right;
        int viewHeight = getHolder().getSurfaceFrame().bottom;

        @Override
        public void run() {
            Canvas canvas;
            SurfaceHolder holder = getHolder();
            Paint paint = new Paint();
            while (perm) {
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLUE);
                if (forx==x && fory==y){
                    paint.setColor(Color.YELLOW);
                    canvas.drawCircle(x, y, radius, paint);
                    if (x == viewWidth && y == viewHeight) {
                        perm = false;
                    }
                }
                radius += 5;
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}

