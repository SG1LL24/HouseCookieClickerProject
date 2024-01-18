package com.example.housecookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView vicodin, pillCount, plusOne, caneText, ppsHeader, ppsText, motorcycleText, ctScanText;
    ImageView house, cane, pillBottle, boughtCane, caneInfo, motorcycle, motorcycleInfo, ctScan, ctScanInfo;
    ConstraintLayout layout;
    LinearLayout layoutL, layout2L, layout3L;
    Button canePurchase, motorcyclePurchase, ctScanPurchase;

    float pills, passivePillsPerSecond, si, pricePointCane, pricePointMotorcycle, pricePointCT;
    int caneNums, motorcycleNums, ctScanBought, pillPerClick;
    boolean ctPurchasedChecker;

    final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
    final TranslateAnimation animation2 = new TranslateAnimation(0f, 0f, 0f, -200f);
    final RotateAnimation animation3 = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    final ScaleAnimation animation5 = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
    final RotateAnimation animation6 = new RotateAnimation(0f, 55f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 2.5f);

    SpringAnimation animation4;

    AnimationSet animationSet = new AnimationSet(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        house = findViewById(R.id.imageView);
        cane = findViewById(R.id.imageView2);
        pillBottle = findViewById(R.id.imageView3);
        boughtCane = findViewById(R.id.imageView4);
        caneInfo = findViewById(R.id.imageView5);
        motorcycle = findViewById(R.id.imageView6);
        motorcycleInfo = findViewById(R.id.imageView7);
        ctScan = findViewById(R.id.imageView8);
        ctScanInfo = findViewById(R.id.imageView9);

        vicodin = findViewById(R.id.textView);
        pillCount = findViewById(R.id.textView2);
        caneText = findViewById(R.id.textView3);
        ppsHeader = findViewById(R.id.textView4);
        ppsText = findViewById(R.id.textView5);
        motorcycleText = findViewById(R.id.textView6);
        ctScanText = findViewById(R.id.textView7);

        layout = findViewById(R.id.layout);
        layoutL = findViewById(R.id.linearLayout);
        layout2L = findViewById(R.id.linearLayout2);
        layout3L = findViewById(R.id.linearLayout3);

        canePurchase = findViewById(R.id.button);
        motorcyclePurchase = findViewById(R.id.button2);
        ctScanPurchase = findViewById(R.id.button3);

        AnimationDrawable backgroundAnimation = (AnimationDrawable) layout.getBackground();
        backgroundAnimation.setEnterFadeDuration(2500);
        backgroundAnimation.setExitFadeDuration(5000);
        backgroundAnimation.start();

        animation4 = new SpringAnimation(boughtCane, DynamicAnimation.TRANSLATION_Y);

        animationSet.addAnimation(animation3);
        animationSet.addAnimation(animation5);

        animation.setDuration(200);
        animation2.setDuration(100);
        animation3.setDuration(400);
        animation5.setDuration(400);
        animation6.setDuration(300);

        animation4.setSpring(new SpringForce().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM));

        boughtCane.setVisibility(View.INVISIBLE);

        ctPurchasedChecker = false;

        pillPerClick = 1;
        pricePointCane = 50;
        pricePointMotorcycle = 200;
        pricePointCT = 1000;
        caneNums = 0;
        motorcycleNums = 0;
        ctScanBought = 0;

        layoutL.setBackgroundColor(/*Color.rgb(160, 155, 155)*/ Color.BLACK);
        layout2L.setBackgroundColor(/*Color.rgb(160, 155, 155)*/ Color.BLACK);
        layout3L.setBackgroundColor(/*Color.rgb(160, 155, 155)*/ Color.BLACK);
        caneText.setTextColor(Color.WHITE);
        motorcycleText.setTextColor(Color.WHITE);
        ctScanText.setTextColor(Color.WHITE);

        pillCount.setText("" + (int)pills);
        caneText.setText("Cane (" + caneNums + "): " + ((int)pricePointCane) + " pills");
        motorcycleText.setText("Motorcycle (" + motorcycleNums + "): " + ((int)pricePointMotorcycle) + " pills");
        ctScanText.setText("CT Scan (" + ctScanBought + "): " + ((int)pricePointCT) + " pills");
        ppsText.setText("" + passivePillsPerSecond);

        if(Build.VERSION.SDK_INT > 25) {
            caneInfo.setTooltipText((CharSequence) "+0.25pps");
            motorcycleInfo.setTooltipText((CharSequence) "+2.0pps");
            ctScanInfo.setTooltipText((CharSequence) "+2 pills per click");
        }

        canePurchase.setOnClickListener(new ButtonClass());
        motorcyclePurchase.setOnClickListener(new ButtonClass());
        ctScanPurchase.setOnClickListener(new ButtonClass());

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills += pillPerClick;
                pillCount.setText("" + (int)pills);
                plusOne = new TextView(MainActivity.this);
                plusOne.setId(View.generateViewId());
                plusOne.setText("+" + pillPerClick);
                plusOne.setTextSize(24);
                plusOne.setTextColor(Color.BLACK);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                plusOne.setLayoutParams(params);
                layout.addView(plusOne);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                constraintSet.connect(plusOne.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP); // Top of that view constrained to top of layout
                constraintSet.connect(plusOne.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(plusOne.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                constraintSet.connect(plusOne.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);

                constraintSet.setVerticalBias(plusOne.getId(), (float)((Math.random() * 5 + 2)/10));
                constraintSet.setHorizontalBias(plusOne.getId(), (float)((Math.random() * 5 + 0)/10));

                constraintSet.applyTo(layout);

                checkPills();

                view.startAnimation(animation);
                plusOne.startAnimation(animation2);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        plusOne.setVisibility(View.GONE);
                    }
                }, 70);}
        });

        Thread pillThread = new Thread() {
            @Override
            public void run() {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(boughtCane.getVisibility() == View.VISIBLE) {
                                    animation4.getSpring().setFinalPosition(400f);
                                    animation4.start();

                                    boughtCane.startAnimation(animation6);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            animation4.getSpring().setFinalPosition(0f);
                                            animation4.start();
                                        }
                                    }, 500);
                                }

                                checkPills();

                                addMethod(passivePillsPerSecond);
                                pills += si;
                                si = 0;

                                pillCount.setText("" + (int)pills);
                                ppsText.setText("" + passivePillsPerSecond);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        pillThread.start();
    }

    public synchronized void addMethod(float k) {
        si+=k;
    }

    public class ButtonClass implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int viewID = view.getId();

            switch(viewID) {
                case R.id.button:
                    pills -= pricePointCane;
                    passivePillsPerSecond += 0.25f;
                    caneNums++;
                    pricePointCane += 20;
                    pillCount.setText("" + (int)pills);
                    caneText.setText("Cane (" + caneNums + "): " + ((int)pricePointCane) + " pills");
                    cane.startAnimation(animationSet);
                    boughtCane.setVisibility(View.VISIBLE);
                    checkPills();
                    //canePurchase.setEnabled(false);
                    break;
                case R.id.button2:
                    pills -= pricePointMotorcycle;
                    passivePillsPerSecond += 2.0f;
                    motorcycleNums++;
                    pricePointMotorcycle += 120;
                    pillCount.setText("" + (int)pills);
                    motorcycleText.setText("Motorcycle (" + motorcycleNums + "): " + ((int)pricePointMotorcycle) + " pills");
                    checkPills();
                    //motorcyclePurchase.setEnabled(false);
                    break;
                case R.id.button3:
                    pills -= pricePointCT;
                    pillPerClick = 2;
                    ctScanBought++;
                    pillCount.setText("" + (int)pills);
                    ctScanText.setText("CT Scan (" + ctScanBought + "): " + ((int)pricePointCT) + " pills");
                    ctPurchasedChecker = true;
                    checkPills();
            }
        }
    }

    public void checkPills() {
        if(pills >= pricePointCane)
            canePurchase.setEnabled(true);
        else
            canePurchase.setEnabled(false);

        if(pills >= pricePointMotorcycle)
            motorcyclePurchase.setEnabled(true);
        else
            motorcyclePurchase.setEnabled(false);

        if(pills >= pricePointCT && !ctPurchasedChecker)
            ctScanPurchase.setEnabled(true);
        else
            ctScanPurchase.setEnabled(false);
    }
}





