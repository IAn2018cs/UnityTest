package cn.ian2018.unitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amber.unity.UnitySurface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DecisionMakers.Decision, TextToSpeech.OnInitListener {

    private TextView textView;
    private DecisionMakers decisionMakers;
    private DateChangeBroadcast mBroadcast;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        control();

    }

    private void initData() {
        registeredBroadcast();
        initTextToSpeech();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (streamMaxVolume*0.4), AudioManager.FLAG_PLAY_SOUND);
    }

    private void initTextToSpeech() {
        mTextToSpeech = new TextToSpeech(this,this);
    }

    private void registeredBroadcast() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        mBroadcast = new DateChangeBroadcast();
        registerReceiver(mBroadcast,intentFilter);
    }

    private void control() {
        // 创建决策者 用来判断是否执行圣诞节操作
        decisionMakers = new DecisionMakers(this);
        decisionMakers.doDecision();
    }

    private void initView() {
        FrameLayout contentLayout = findViewById(R.id.content_layout);
        contentLayout.addView(new UnitySurface(this));

        textView = findViewById(R.id.tv_love);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS && mTextToSpeech != null) {
            int result = mTextToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("ASD", "onInit: 语言不支持");
            }
        } else {
            Log.e("ASD", "onInit: 语音初始化失败");
        }
    }

    @Override
    public void christmas() {
        textView.setVisibility(View.VISIBLE);
        textView.setText(getResources().getString(R.string.love_text2));
    }

    @Override
    public void christmasing() {
        textView.setVisibility(View.VISIBLE);
        mTextToSpeech.setPitch(0.5f);
        mTextToSpeech.setSpeechRate(1f);
        mTextToSpeech.speak(getResources().getString(R.string.love_text_speech),TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void hide() {
        textView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBroadcast != null) {
            unregisterReceiver(mBroadcast);
        }
    }



    class DateChangeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null && decisionMakers == null) return;
            switch (intent.getAction()) {
                case Intent.ACTION_TIME_TICK:
                    decisionMakers.doDecision();
                    break;
            }
        }
    }
}
