package com.americapinoy.usacitizenshipapplication;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TakeAQuizActivity extends AppCompatActivity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion, times, scored;
    RadioGroup radioGroup;
    RadioButton r1, r2, r3, r4;
    InterstitialAd mInterstitialAd;
    private static final String DB_PATH = "data/data/com.americapinoy.usacitizenshipapplication2020/databases/mathsone";
    private MediaPlayer CorrectSound;
    private MediaPlayer FailedSound;
    CounterClass timer;
    long total_time = 75000;
    AlertDialog  alertDialog;
    CheckBox Mutesound_check,Timer_check;
    ScrollView ScrollsQuiz;

    // Database delete Refresh
    private void doDBCheck() {
        try {
            File file = new File(DB_PATH);
            file.delete();
        } catch (Exception ex) {
        }
    }
    // End of Database.

    public void onBackPressed() {
        moveTaskToBack(true);
        timer.cancel();

    }

    // Interstitial Ads
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        InterstitialAd.load(this,"ca-app-pub-4831821806575014/2893483241", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

    }
    // End of Interstitial Ads

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doDBCheck();
        setContentView(R.layout.activity_take_quiz);
        QuizHelper db = new QuizHelper(this); // my question65 bank class
        quesList = db.getAllQuestions(); // this will fetch all quetonall
        // questions
        currentQ = quesList.get(qid); // the current question65
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        r1 = (RadioButton) findViewById(R.id.option1);
        r2 = (RadioButton) findViewById(R.id.option2);
        r3 = (RadioButton) findViewById(R.id.option3);
        r4 = (RadioButton) findViewById(R.id.option4);
        scored = (TextView) findViewById(R.id.score);
        CorrectSound = MediaPlayer.create(this, R.raw.right_answer);
        FailedSound = MediaPlayer.create(this, R.raw.wrong_answer);
        Mutesound_check = (CheckBox) findViewById(R.id.Mutesound_check);
        Timer_check = (CheckBox) findViewById(R.id.TimerQuiz_check);
        ScrollsQuiz = (ScrollView) findViewById(R.id.quizscrollview) ;


        // Interstitial Ads.
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-4831821806575014/2893483241");
       // AdRequest request_Interstitial = new AdRequest.Builder().build();
        //mInterstitialAd.loadAd(request_Interstitial);

        //mInterstitialAd.setAdListener(new AdListener() {
           // @Override
           // public void onAdClosed() {
                //requestNewInterstitial();
           // }
        //});
        requestNewInterstitial();
        // End of Interstitial Ads

    // the timer
        times = (TextView) findViewById(R.id.timers);
        // method which will set the things up for our game
        setQuestionView();
        times.setText(R.string.times);
        // A timer of 60 seconds to play for, with an interval of 1 second (1000
        // milliseconds)
        timer = new CounterClass(total_time, 1000);
        timer.start();
        // button click listeners


        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-4831821806575014~8940016843");
        /// Banner
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.setAdUnitId("ca-app-pub-4831821806575014~8940016843");
        mAdView.loadAd(adRequest);

        //Ends Adds program



    //Radio Button Animation
        final Animation mAnimation = new AlphaAnimation(1.8f, 0.2f);
        mAnimation.setDuration(900);
        mAnimation.setStartOffset(100);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);

    //End of Animation

        r1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // passing the button text to other method
                // to check whether the answer is correct or not
                // same for all three buttons
                    r1.setChecked(false);
                    r1.startAnimation(mAnimation);
                    Timer buttonTimer = new Timer();
                    if ((currentQ.getANSWER().equals(r1.getText().toString())))
                    {
                        r1.setTypeface(null, Typeface.BOLD);
                        r1.setTextColor(Color.parseColor("#D7FFC9"));
                        r2.setTextColor(Color.parseColor("#877F7F"));
                        r3.setTextColor(Color.parseColor("#877F7F"));
                        r4.setTextColor(Color.parseColor("#877F7F"));


                        r1.setEnabled(false);
                        r2.setEnabled(false);
                        r3.setEnabled(false);
                        r4.setEnabled(false);
                        r1.setClickable(false);
                        r2.setClickable(false);
                        r3.setClickable(false);
                        r4.setClickable(false);

                        score++;
                        CorrectSound.start();
                    }
                    else
                    {
                        r1.setTextColor(Color.parseColor("#C63E3E"));
                        r1.setText(R.string.Incorrect_Answer);
                        r1.setTypeface(null, Typeface.BOLD);

                        r2.setTextColor(Color.parseColor("#877F7F"));
                        r3.setTextColor(Color.parseColor("#877F7F"));
                        r4.setTextColor(Color.parseColor("#877F7F"));


                        r1.setEnabled(false);
                        r2.setEnabled(false);
                        r3.setEnabled(false);
                        r4.setEnabled(false);

                        r1.setClickable(false);
                        r2.setClickable(false);
                        r3.setClickable(false);
                        r4.setClickable(false);

                        FailedSound.start();
                    }

                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    r1.setChecked(true);
                                    r1.setTypeface(null, Typeface.NORMAL);
                                    getAnswer();

                                    r1.setTextColor(Color.WHITE);
                                    r2.setTextColor(Color.WHITE);
                                    r3.setTextColor(Color.WHITE);
                                    r4.setTextColor(Color.WHITE);


                                    r1.setEnabled(true);
                                    r2.setEnabled(true);
                                    r3.setEnabled(true);
                                    r4.setEnabled(true);

                                    r1.setClickable(true);
                                    r2.setClickable(true);
                                    r3.setClickable(true);
                                    r4.setClickable(true);


                                    v.clearAnimation();
                                }
                            });
                        }
                    }, 4000);

                }



        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                r2.setChecked(false);
                r2.startAnimation(mAnimation);
                Timer buttonTimer = new Timer();
                if ((currentQ.getANSWER().equals(r2.getText().toString())))
                {
                    r2.setTypeface(null, Typeface.BOLD);
                    r2.setTextColor(Color.parseColor("#D7FFC9"));

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r3.setTextColor(Color.parseColor("#877F7F"));
                    r4.setTextColor(Color.parseColor("#877F7F"));



                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    score++;
                    CorrectSound.start();
                }
                else
                {
                    r2.setTextColor(Color.parseColor("#C63E3E"));
                    r2.setText(R.string.Incorrect_Answer);
                    r2.setTypeface(null, Typeface.BOLD);

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r3.setTextColor(Color.parseColor("#877F7F"));
                    r4.setTextColor(Color.parseColor("#877F7F"));



                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    FailedSound.start();

                }

                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                r2.setChecked(true);
                                r2.setTypeface(null, Typeface.NORMAL);
                                getAnswer();

                                r1.setTextColor(Color.WHITE);
                                r2.setTextColor(Color.WHITE);
                                r3.setTextColor(Color.WHITE);
                                r4.setTextColor(Color.WHITE);



                                r1.setEnabled(true);
                                r2.setEnabled(true);
                                r3.setEnabled(true);
                                r4.setEnabled(true);

                                r1.setClickable(true);
                                r2.setClickable(true);
                                r3.setClickable(true);
                                r4.setClickable(true);

                                v.clearAnimation();
                            }
                        });
                    }
                }, 4000);
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                r3.setChecked(false);
                r3.startAnimation(mAnimation);
                Timer buttonTimer = new Timer();
                if ((currentQ.getANSWER().equals(r3.getText().toString())))
                {
                    r3.setTypeface(null, Typeface.BOLD);
                    r3.setTextColor(Color.parseColor("#D7FFC9"));

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r2.setTextColor(Color.parseColor("#877F7F"));
                    r4.setTextColor(Color.parseColor("#877F7F"));


                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    score++;
                    CorrectSound.start();
                }
                else
                {
                    r3.setTextColor(Color.parseColor("#C63E3E"));
                    r3.setText(R.string.Incorrect_Answer);
                    r3.setTypeface(null, Typeface.BOLD);

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r2.setTextColor(Color.parseColor("#877F7F"));
                    r4.setTextColor(Color.parseColor("#877F7F"));



                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    FailedSound.start();

                }

                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                r3.setChecked(true);
                                r3.setTypeface(null, Typeface.NORMAL);
                                getAnswer();

                                r1.setTextColor(Color.WHITE);
                                r2.setTextColor(Color.WHITE);
                                r3.setTextColor(Color.WHITE);
                                r4.setTextColor(Color.WHITE);



                                r1.setEnabled(true);
                                r2.setEnabled(true);
                                r3.setEnabled(true);
                                r4.setEnabled(true);

                                r1.setClickable(true);
                                r2.setClickable(true);
                                r3.setClickable(true);
                                r4.setClickable(true);

                                v.clearAnimation();
                            }
                        });
                    }
                }, 4000);
            }
        });

        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                r4.setChecked(false);
                r4.startAnimation(mAnimation);
                Timer buttonTimer = new Timer();
                if ((currentQ.getANSWER().equals(r4.getText().toString())))
                {
                    r4.setTypeface(null, Typeface.BOLD);
                    r4.setTextColor(Color.parseColor("#D7FFC9"));

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r2.setTextColor(Color.parseColor("#877F7F"));
                    r3.setTextColor(Color.parseColor("#877F7F"));


                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    score++;
                    CorrectSound.start();
                }
                else
                {
                    r4.setTextColor(Color.parseColor("#C63E3E"));
                    r4.setText(R.string.Incorrect_Answer);
                    r4.setTypeface(null, Typeface.BOLD);

                    r1.setTextColor(Color.parseColor("#877F7F"));
                    r2.setTextColor(Color.parseColor("#877F7F"));
                    r3.setTextColor(Color.parseColor("#877F7F"));


                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    r4.setEnabled(false);

                    r1.setClickable(false);
                    r2.setClickable(false);
                    r3.setClickable(false);
                    r4.setClickable(false);

                    FailedSound.start();

                }

                buttonTimer.schedule(new TimerTask(){

                    @Override
                    public void run(){
                        runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                r4.setChecked(true);
                                r4.setTypeface(null, Typeface.NORMAL);
                                getAnswer();

                                r1.setTextColor(Color.WHITE);
                                r2.setTextColor(Color.WHITE);
                                r3.setTextColor(Color.WHITE);
                                r4.setTextColor(Color.WHITE);



                                r1.setEnabled(true);
                                r2.setEnabled(true);
                                r3.setEnabled(true);
                                r4.setEnabled(true);

                                r1.setClickable(true);
                                r2.setClickable(true);
                                r3.setClickable(true);
                                r4.setClickable(true);

                                v.clearAnimation();
                            }
                        });
                    }
                }, 4000);

            }
        });

        Mutesound_check.setChecked(true);
        //attach a listener to check for changes in state
        Mutesound_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    CorrectSound.setVolume(1,1);
                    FailedSound.setVolume(1,1);
                    Toast.makeText(getApplicationContext(), "Sound On", Toast.LENGTH_LONG).show();
                }else{
                    CorrectSound.setVolume(0,0);
                    FailedSound.setVolume(0,0);
                    Toast.makeText(getApplicationContext(), "Sound Off", Toast.LENGTH_LONG).show();
                }

            }
        });

        Timer_check.setChecked(true);
        //attach a listener to check for changes in state
        Timer_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    timer.start();
                    Toast.makeText(getApplicationContext(), "Timer On", Toast.LENGTH_LONG).show();
                }else{
                    timer.cancel();
                    Toast.makeText(getApplicationContext(), "Timer Off", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    public void getAnswer() {
        String str = String.valueOf(score);

        scored.setText(MessageFormat.format("Score : {0} / 20", str));

        if (qid <=19) {

            if (!Timer_check.isChecked()){
                timer.cancel();}
            else
            {
                timer.cancel();
                timer.start();
            }
            // if questions are not over then do this
            currentQ = quesList.get(qid);
            setQuestionView();
            System.out.println(qid + "qid");
        }
        else {
            System.out.println(qid + "qidElse");

            timer.cancel();

            alertDialog = new AlertDialog.Builder(TakeAQuizActivity.this).create();
            alertDialog.setTitle("ALERT");
            alertDialog.setMessage(TakeAQuizActivity.this.getString(R.string.Alert65));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);



            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Rate This App!",
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public void onClick(DialogInterface dialog, int which) {
                            TakeAQuizActivity.this.finish();
                            Uri uri = Uri.parse("market://details?id=" + getPackageName());
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            // To count with Play market backstack, After pressing back button,
                            // to taken back to our application, we need to add following flags to intent.
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }


                    });

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            doDBCheck();

                            TakeAQuizActivity.this.finish();
                            Intent intent = new Intent(TakeAQuizActivity.this, TakeAQuizActivity.class);
                            startActivity(intent);


                        }

                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(TakeAQuizActivity.this);
                            } else {
                                Log.d("TAG", "The interstitial ad wasn't ready yet.");
                            }
                            doDBCheck();
                            TakeAQuizActivity.this.finish();

                        }

                    });

            if (isFinishing()){System.out.println("qidActivity is finishing"); }else{System.out.println("qidActivity is not finishing");}
            alertDialog.show();

        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    private class CounterClass extends CountDownTimer {


        CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            AtomicLong millis = new AtomicLong(millisUntilFinished);
            @SuppressLint("DefaultLocale") String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis.get()),
                    TimeUnit.MILLISECONDS.toMinutes(millis.get())
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis.get())),
                    TimeUnit.MILLISECONDS.toSeconds(millis.get())
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis.get())));

            System.out.println(hms);
            times.setText(hms);
        }





        @Override
        public void onFinish() {

            //if (times.getText().toString().equals(times.getText().toString())) {
                //currentQ = quesList.get(qid);
                //setQuestionView();
                //timer.cancel();
                //timer.start();
           // }
            System.out.println(qid + "qidOnFinish");
            getAnswer();

        }




    }

    private void setQuestionView() {
        // the method which will put all things together
        ScrollsQuiz.scrollTo(0, 0);
        txtQuestion.setText(currentQ.getQUESTION());
        r1.setText(currentQ.getOPTA());
        r2.setText(currentQ.getOPTB());
        r3.setText(currentQ.getOPTC());
        r4.setText(currentQ.getOPTD());


        r1.setSelected(false);
        r2.setSelected(false);
        r3.setSelected(false);
        r4.setSelected(false);
        qid++;
    }

    @Override
    public void onResume() {
        super.onResume();
        timer.start();
        // Hide Status Bar and Navigation bar On Create
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


}