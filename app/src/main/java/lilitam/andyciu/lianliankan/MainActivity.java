package lilitam.andyciu.lianliankan;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements
        ImageView.OnClickListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        DialogInterface.OnClickListener{



    int[] num1=new int[16];
    int[] num2=new int[16];
    int numyet,numnotyet;
    final int numFinalStar=8;
    int tempivinum=0;
    int numtime=0;

    TextView txt_time;
    TextView txt_finish;
    TextView txt_Xfinish;
    Button btn_restart;
    Uri uri;
    MediaPlayer mper;
    private Handler mIncHandler = new Handler();
    private boolean mCounterEnable = true;
    private Runnable mIncRunner = new Runnable() {
        @Override
        public void run() {

            //若計時器仍啟動中，設定下次執行（延遲）時間
            if(mCounterEnable) {
                mIncHandler.postDelayed(mIncRunner, 1000);
                //累加計數值
                txt_time.setText(String.format("%d",++numtime));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_restart = (Button)findViewById(R.id.btn_restart);
        txt_time = (TextView)findViewById(R.id.txt_time);
        txt_finish = (TextView)findViewById(R.id.txt_finish);
        txt_Xfinish = (TextView)findViewById(R.id.txt_Xfinish);
        txt_finish.setText(String.format("%d",numyet));
        txt_Xfinish.setText(String.format("%d",numnotyet));
        btn_restart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                stratFuc();
                //finish();
            }
        });


        //mIncRunner.run();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mper=new MediaPlayer();
        mper.setOnPreparedListener(this);
        mper.setOnErrorListener(this);
        mper.setOnCompletionListener(this);

        uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clap);
        try{
            mper.reset();
            mper.setDataSource(this,uri);
            mper.prepareAsync();
        }catch (Exception e){
        }

        stratFuc();

    }

    @Override
    public void onClick(View v) {
        final ImageView ivi= (ImageView) findViewById(v.getId());
        String ivistr=ivi.toString();
        ivistr=ivistr.substring(ivistr.length()-3,ivistr.length()-1);
        //Toast.makeText(this,ivistr,Toast.LENGTH_LONG).show();
        //Toast.makeText(this,ivistr.substring(ivistr.length()-3,ivistr.length()-1),Toast.LENGTH_LONG).show();
        int ivinum=Integer.parseInt(ivistr);
        //========================================
        switch (num1[ivinum-1]){
            case 1:
                ivi.setImageResource(android.R.drawable.presence_audio_online);
                break;
            case 2:
                ivi.setImageResource(android.R.drawable.presence_audio_away);
                break;
            case 3:
                ivi.setImageResource(android.R.drawable.presence_audio_busy);
                break;
            case 4:
                ivi.setImageResource(android.R.drawable.presence_video_online);
                break;
            case 5:
                ivi.setImageResource(android.R.drawable.presence_video_away);
                break;
            case 6:
                ivi.setImageResource(android.R.drawable.presence_video_busy);
                break;
            case 7:
                ivi.setImageResource(android.R.drawable.presence_away);
                break;
            case 8:
                ivi.setImageResource(android.R.drawable.presence_busy);
                break;
        }
        //========================================
        if(num2[ivinum-1]==0 && tempivinum!=ivinum){
            if(tempivinum==0){
                tempivinum=ivinum;
            }
            else if(tempivinum>0){
                if(num1[tempivinum-1]==num1[ivinum-1]){
                    num2[tempivinum-1]=1;
                    num2[ivinum-1]=1;
                    tempivinum=0;
                    //===================
                    txt_finish.setText(String.format("%d",++numyet));
                    txt_Xfinish.setText(String.format("%d",--numnotyet));
                    //===================
                    int numyn=0;
                    for(int i=0;i<16;i++){
                        if(num2[i]!=1){
                            numyn=1;
                            break;
                        }
                    }
                    if(numyn==0){
                        mCounterEnable=false;
                        gameFinFuc();
                    }
                }
                else{
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long millisUntilFinished){}
                        @Override
                        public void onFinish() {
                            String str1="imageView"+String.format("%02d",tempivinum);
                            int resID1=getResources().getIdentifier(str1,"id",getPackageName());
                            ImageView ivi2= (ImageView) findViewById(resID1);
                            ivi2.setImageResource(android.R.drawable.btn_star_big_on);
                            ivi.setImageResource(android.R.drawable.btn_star_big_on);
                            tempivinum=0;
                        }
                    }.start();

                }
            }
        }
    }

    //restart不需要再跑一次
    public void stratFuc(){
        numyet=0;
        numnotyet=numFinalStar;
        numtime=0;

        mIncHandler.removeCallbacks(mIncRunner);
        mCounterEnable=true;
        mIncRunner.run();

        txt_finish.setText(String.format("%d",numyet));
        txt_Xfinish.setText(String.format("%d",numnotyet));

        try{
            mper.reset();
            mper.setDataSource(this,uri);
            mper.prepareAsync();
        }
        catch (Exception e){
        }

        for(int i=1;i<=16;i++){
            num1[i-1]=0;
            num2[i-1]=0;
        }
        for(int i=1;i<=16;i++){
            String str1="imageView"+String.format("%02d",i);
            int resID=getResources().getIdentifier(str1,"id",getPackageName());
            ImageView iva= (ImageView) findViewById(resID);
            iva.setImageResource(android.R.drawable.btn_star_big_on);
            iva.setOnClickListener(this);
        }

        Random rn=new Random();
        for(int i=1;i<=8;i++){
            for(int j=1;j<=2;j++){
                while(true){
                    int n1=rn.nextInt(16);
                    if(num1[n1]==0){
                        num1[n1]=i;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mper.seekTo(0);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    protected void onDestroy(){
        mper.release();
        super.onDestroy();
    }

    protected void gameFinFuc(){
        //String strend="congratulations!";
        //Toast.makeText(this,strend,Toast.LENGTH_LONG).show();
        AlertDialog.Builder bdr = new AlertDialog.Builder(this);
            bdr.setTitle("Congratulation");
            bdr.setMessage("Finish!!!\n" + "完成時間: "+txt_time.getText().toString()+"秒");
            bdr.setIcon(R.drawable.jjw);
            bdr.setCancelable(true);
            bdr.setNegativeButton("重來", this);
            bdr.setPositiveButton("返回第一頁", this);
        bdr.show();

        mper.start();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_NEGATIVE){
            mper.stop();
            stratFuc();
        }

        else if (which == DialogInterface.BUTTON_POSITIVE){
            finish();
        }
    }
}