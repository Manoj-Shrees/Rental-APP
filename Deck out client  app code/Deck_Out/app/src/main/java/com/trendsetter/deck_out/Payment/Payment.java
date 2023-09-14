package com.trendsetter.deck_out.Payment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.shuhart.stepview.StepView;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    StepView  stepView;
    Button paymentbtn;
    private int fragcount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.paymenttoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        stepView = findViewById(R.id.paymentstep_view);
        stepView.getState()
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .steps(new ArrayList<String>() {{
                    add("Address");
                    add("Review Orders");
                    add("Payment");
                }})
                .stepsNumber(4)
                .animationDuration(1000)
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .typeface(ResourcesCompat.getFont(getApplicationContext(), R.font.whitneymedium))
                .commit();




        paymentbtn = findViewById(R.id.paymentbtn);

        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(fragcount == 0)
                {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         /*           transaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.fade_out);*/
                    transaction.replace(R.id.paymentfragcontainer, new paymentreview());
                    transaction.addToBackStack(null).commit();
                    paymentbtn.setText("Proceed to payment");
                    stepView.go(1,true);
                    fragcount+=1;
                }

             else if(fragcount == 1)
                {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            /*        transaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.fade_out);*/
                    transaction.replace(R.id.paymentfragcontainer, new paymentproceed());
                    transaction.addToBackStack(null).commit();
                    paymentbtn.setVisibility(View.GONE);
                    stepView.go(2,true);
                    fragcount+=1;

                }



            }
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.paymentfragcontainer, new paymentfragaddress());
        transaction.addToBackStack(null).commit();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        paymentbtn.setText(data.getDataString());
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);

        if(fragcount==1) {
            paymentbtn.setText(title);
            fragcount -= 1;
            stepView.go(0,true);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
