package org.techtown.capstoneprojectcocktail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MJH_CustomView extends View {

    private Paint paint;

    public MJH_CustomView(Context context) {
        super(context);
    }

    public MJH_CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 뷰가 화면에 디스플레이 될때 자동으로 호출
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        Paint Pnt = new Paint();
        Pnt.setAntiAlias(true);

        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE};
        float[] pos = {0.0f, 0.1f, 0.6f, 0.9f, 1.0f};

        Pnt.setShader(new LinearGradient(0, 0, 100, 0, Color.BLUE, Color.WHITE, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, 100, 100, Pnt);

        // 반복
        Pnt.setShader(new LinearGradient(0, 0, 100, 0, Color.BLUE, Color.WHITE, Shader.TileMode.REPEAT));
        canvas.drawRect(0, 160, 320, 200, Pnt);

        //여러 색
        Pnt.setShader(new LinearGradient(0, 0, 320, 0, colors, null, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 260, 320, 300, Pnt);

        //여러 색 with 배치
        Pnt.setShader(new LinearGradient(0, 0, 320, 0, colors, pos, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 310, 320, 350, Pnt);


        // 좌표값과 페인트 객체를 이용해서 사각형을 그리는 drawRect()
    }

    /**
     * 터치 이벤트를 처리
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " +
                    event.getX() + ", " + event.getY(), Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }
}