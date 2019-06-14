package com.example.liaoyikang.tomandjerry;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class ThirdActivity extends AppCompatActivity {
    Button Qz[] = new Button[10];
    int BG[][] = new int[5][4];
    TextView txt1;
    TextView size;
    TextView time;
    int count = 0;
    float SW;
    float x1, x2, y1, y2;
    int Step=0;
    Timer T=new Timer();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Qz[0] = (Button) findViewById(R.id.Qz1);
        Qz[1] = (Button) findViewById(R.id.Qz2);
        Qz[2] = (Button) findViewById(R.id.Qz3);
        Qz[3] = (Button) findViewById(R.id.Qz4);
        Qz[4] = (Button) findViewById(R.id.Qz5);
        Qz[5] = (Button) findViewById(R.id.Qz6);
        Qz[6] = (Button) findViewById(R.id.Qz7);
        Qz[7] = (Button) findViewById(R.id.Qz8);
        Qz[8] = (Button) findViewById(R.id.Qz9);
        Qz[9] = (Button) findViewById(R.id.Qz10);

        size = findViewById(R.id.Text);
        txt1 = (TextView) findViewById(R.id.Textcount);
        time = findViewById(R.id.timer);


        //注册监听器
        for (int i = 0; i < 10; i++)
            Qz[i].setOnTouchListener(new mTouch());
        //背景数组对应填充
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++)
                BG[i][j] = 1;
        BG[4][1] = 0;
        BG[4][2] = 0;
        //输出屏幕宽度和
        size.post(new Runnable() {
            @Override
            public void run() {
                SW = size.getWidth();
                init();
            }
        });
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time.setText("时间: "+count+"s");
                count++;
            }
        }, 1000, 1000);
        intent = new Intent(this, MainActivity.class);

    }
    public void tipClick() {
        T.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
        builder.setTitle("恭喜！");
        builder.setMessage("你已经通过了本关卡，步数为： "+ Step+" 时间为： "+count+"s");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);

        //设置反面按钮
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }
    //监听实现
    public class mTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int type;
            int r, c;
            if(v.getWidth()==v.getHeight())
            {
                if(v.getHeight()>300)
                    type=4;
                else
                    type =1;

            }
            else
            {
                if(v.getHeight()>v.getWidth())
                    type =2;
                else
                    type =3;
            }

            r=(int)(v.getY()/270f);
            c=(int)(v.getX()/270f);

            //继承了Activity的onTouchEvent方法，直接监听点击事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 50) //"向上滑:"
                {
                    switch (type) {
                        case 1:
                            if(r>0 && BG[r-1][c]==0) {
                                SetPois(v, r - 1, c);
                                BG[r - 1][c] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 2:
                            if(r>0 && BG[r-1][c]==0){
                                SetPois(v,r-1,c);
                                BG[r-1][c]=1;
                                BG[r+1][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 3:
                            if(r>0 && BG[r-1][c]==0 && BG[r-1][c+1]==0){
                                SetPois(v,r-1,c);
                                BG[r-1][c]=BG[r-1][c+1]=1;
                                BG[r][c]=BG[r][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 4:
                            if(r>0 && BG[r-1][c]==0 && BG[r-1][c+1]==0){
                                SetPois(v,r-1,c);
                                BG[r-1][c]=BG[r-1][c+1]=1;
                                BG[r+1][c]=BG[r+1][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;

                    }

                } else if (y2 - y1 > 50){ //向下滑
                    switch (type) {
                        case 1:
                            if(r<4 && BG[r+1][c]==0) {
                                SetPois(v, r + 1, c);
                                BG[r + 1][c] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 2:
                            if(r<3 && BG[r+2][c]==0){
                                SetPois(v,r+1,c);
                                BG[r+2][c]=1;
                                BG[r][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }

                            break;
                        case 3:
                            if(r<4 && BG[r+1][c]==0 &&BG[r+1][c+1]==0)
                            {
                                SetPois(v,r+1,c);
                                BG[r+1][c]=BG[r+1][c+1]=1;
                                BG[r][c]=BG[r][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 4:
                            if(r<3 && BG[r+2][c]==0 && BG[r+2][c+1]==0){
                                SetPois(v,r+1,c);
                                BG[r+2][c]=BG[r+2][c+1]=1;
                                BG[r][c]=BG[r][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                                if(r+1==3 && c==1){
                                    txt1.setText("步数："+Step);
                                    tipClick();


                                }
                            }
                            break;
                    }
                } else if (x1 - x2 > 50) //向左滑
                {
                    switch (type) {
                        case 1:
                            if(c>0 && BG[r][c-1]==0) {
                                SetPois(v, r , c- 1);
                                BG[r][c- 1] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 2:
                            if(c>0 && BG[r][c-1]==0 && BG[r+1][c-1]==0){
                                SetPois(v,r,c-1);
                                BG[r][c-1]=1;
                                BG[r+1][c-1]=1;
                                BG[r][c]=0;
                                BG[r+1][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 3:
                            if(c>0 & BG[r][c-1]==0)
                            {
                                SetPois(v,r,c-1);
                                BG[r][c-1]=1;
                                BG[r][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 4:
                            if(c>0 && BG[r][c-1]==0 && BG[r+1][c-1]==0){
                                SetPois(v,r,c-1);
                                BG[r][c-1]= BG[r+1][c-1]=1;
                                BG[r][c+1]=BG[r+1][c+1]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                                if(r+1==3 && c==1){
                                    txt1.setText("步数："+Step);
                                    tipClick();
                                }
                            }
                            break;
                    }
                } else if (x2 - x1 > 50) //向右滑
                {
                    switch (type) {
                        case 1:
                            if(c<3 && BG[r][c+1]==0) {
                                SetPois(v, r , c+1);
                                BG[r ][c+1] = 1;
                                BG[r][c] = 0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 2:
                            if(c<3 & BG[r][c+1]==0 && BG[r+1][c+1]==0)
                            {
                                SetPois(v,r,c+1);
                                BG[r][c+1]=1;
                                BG[r+1][c+1]=1;
                                BG[r][c]=0;
                                BG[r+1][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 3:
                            if(c<2 && BG[r][c+2]==0)
                            {
                                SetPois(v,r,c+1);
                                BG[r][c+2]=1;
                                BG[r][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                            }
                            break;
                        case 4:
                            if(c<2 && BG[r][c+2]==0 && BG[r+1][c+2]==0){
                                SetPois(v,r,c+1);
                                BG[r][c+2]= BG[r+1][c+2]=1;
                                BG[r][c]=BG[r+1][c]=0;
                                Step++;
                                txt1.setText("步数："+Step);
                                if(r+1==3 && c==1){
                                    txt1.setText("步数："+Step);
                                    tipClick();
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue){
        float scale =context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    void SetSize(Button v, int w, int h) {
        v.setWidth(w * dip2px(getApplicationContext(),SW/4));
        v.setHeight(h * dip2px(getApplicationContext(), SW / 4));
    }
    void SetPois(View v, int r ,int c) {
        v.setX(c*SW/4f);
        v.setY(r*SW/4f);
    }
    void init() {
        SetSize(Qz[0],1,1);SetPois(Qz[0],4,0);
        SetSize(Qz[1],1,1);SetPois(Qz[1], 3, 1);
        SetSize(Qz[2], 1, 1);
        SetPois(Qz[2], 3, 2);
        SetSize(Qz[3], 1, 1);
        SetPois(Qz[3], 4, 3);

        SetSize(Qz[4], 1, 2);SetPois(Qz[4], 0, 0);
        SetSize(Qz[5],1,2);SetPois(Qz[5],0,3);
        SetSize(Qz[6],1,2);SetPois(Qz[6],2,0);
        SetSize(Qz[7],1,2);SetPois(Qz[7],2,3);

        SetSize(Qz[8],2,1);SetPois(Qz[8],2,1);
        SetSize(Qz[9],2,2);SetPois(Qz[9], 0, 1);
        //txt1.setText("SW：" +dip2px(getApplicationContext(), SW)+","+getApplicationContext().getResources().getDisplayMetrics().density);
    }
}