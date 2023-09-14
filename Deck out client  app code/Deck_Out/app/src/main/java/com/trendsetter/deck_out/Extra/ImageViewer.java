package com.trendsetter.deck_out.Extra;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class ImageViewer  extends AppCompatActivity {

    ImageView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String productimgurl = getIntent().getExtras().getString("productimgurl");
        String productname =getIntent().getExtras().getString("productname");

        setContentView(R.layout.productimageviewerlayout);
        photoview = findViewById(R.id.photo_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.imageviewertoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(productname);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        photoview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.get().load(productimgurl).placeholder(R.drawable.ic_loadingthumb).into(photoview);


    }

}
