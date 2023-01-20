package com.example.dz17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CircleView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private final MyThread thread;
    private Two circlecoor;
    private Two circleto;
    public CircleView(Context context) {
        super(context);
        circlecoor= new Two(500,500);
        circleto = new Two(500,500);
        getHolder().addCallback(this);
        thread = new MyThread();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        circleto.x=event.getX();
        circleto.y=event.getY();
        invalidate();
        return super.onTouchEvent(event);
    }
    public void move(){
        float dx = circleto.x-circlecoor.x;
        float dy = circleto.y - circlecoor.y;
        float len = (float) Math.sqrt(dx*dx+dy*dy);
        float cosd = dx/len;
        float sind = dy/len;
        if(len>40f){
            circlecoor.x+=40f*cosd;
            circlecoor.y+=40f*sind;
        }
        else{
            circlecoor.x = circleto.x;
            circlecoor.y=circleto.y;
        }
    }
    private class MyThread extends Thread {
        boolean running = true;
        @Override
        public void run() {
            Canvas canvas;
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            while(running){
                canvas = surfaceHolder.lockCanvas();
                try {
                    sleep(50);
                    move();
                    canvas.drawColor(Color.BLUE);
                    canvas.drawCircle(circlecoor.x,circlecoor.y,50f,paint);;

                } catch (InterruptedException e) {}
                finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
class Two{
    public float x;
    public float y;
    public Two(float x,float y) {
        this.x = x;
        this.y=y;
    }
}
