package com.trendsetter.deckout_admin.Extra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.android.helloyako.imagecrop.view.ImageCropView;
import com.trendsetter.deckout_admin.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Imagecropper extends AppCompatActivity {

ImageCropView cropView ;
ImageView undobtn;
Uri imguri , cropimguri;
TextView cropbtn , donebtn , ratio1_1text, ratio2_3text, ratio3_4text, ratio4_3text, ratio9_16text, ratio16_9text;
View ratio1_1box, ratio2_3box , ratio3_4box , ratio4_3box , ratio9_16box , ratio16_9box ;
LinearLayout ration1_1btn ,ration2_3btn  , ration3_4btn , ration4_3btn , ration9_16btn , ration16_9btn ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.imagecropperlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.imgcroppertoolbar);
        setSupportActionBar(toolbar);

        imguri = getIntent().getData();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Rescale and Crop");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeelemets();
    }

    private void initializeelemets()
    {

        cropView = findViewById(R.id.imgcropper);
        cropView.setImageFilePath(imguri.toString());
        cropView.setAspectRatio(1 ,1);

        undobtn = findViewById(R.id.imgcropperundobtn);
        donebtn = findViewById(R.id.imgcropperdonebtn);
        cropbtn = findViewById(R.id.imgcroppercropbtn);

        ration1_1btn = findViewById(R.id.ratio_1_1layout);
        ration2_3btn = findViewById(R.id.ratio_2_3layout);
        ration3_4btn = findViewById(R.id.ratio_3_4layout);
        ration4_3btn = findViewById(R.id.ratio_4_3layout);
        ration9_16btn = findViewById(R.id.ratio_9_16layout);
        ration16_9btn = findViewById(R.id.ratio_16_9layout);

        ratio1_1box = findViewById(R.id.ratio_1_1box);
        ratio2_3box = findViewById(R.id.ratio_2_3box);
        ratio3_4box = findViewById(R.id.ratio_3_4box);
        ratio4_3box = findViewById(R.id.ratio_4_3box);
        ratio9_16box = findViewById(R.id.ratio_9_16box);
        ratio16_9box = findViewById(R.id.ratio_16_9box);

        ratio1_1text = findViewById(R.id.ratio_1_1text);
        ratio2_3text = findViewById(R.id.ratio_2_3text);
        ratio3_4text = findViewById(R.id.ratio_3_4text);
        ratio4_3text = findViewById(R.id.ratio_4_3text);
        ratio9_16text = findViewById(R.id.ratio_9_16text);
        ratio16_9text = findViewById(R.id.ratio_16_9text);


        undobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(1,1);
                donebtn.setEnabled(false);
                donebtn.setTextColor(Color.parseColor("#7CFFFFFF"));
                cropbtn.setEnabled(true);
                cropbtn.setTextColor(Color.parseColor("#FFFFFF"));
                ratio1_1box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio1_1text.setTextColor(Color.parseColor("#00E6FF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        ration1_1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(1,1);
                ratio1_1box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio1_1text.setTextColor(Color.parseColor("#00E6FF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        ration2_3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(2,3);
                ratio1_1box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio1_1text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio2_3text.setTextColor(Color.parseColor("#00E6FF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        ration3_4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(3,4);
                ratio1_1box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio1_1text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio3_4text.setTextColor(Color.parseColor("#00E6FF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        ration4_3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(4,3);
                ratio1_1box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio1_1text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio4_3text.setTextColor(Color.parseColor("#00E6FF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

        ration9_16btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(9,16);
                ratio1_1box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio1_1text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio9_16text.setTextColor(Color.parseColor("#00E6FF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio16_9text.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        ration16_9btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setAspectRatio(16,9);
                ratio1_1box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio1_1text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio2_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio2_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio3_4box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio3_4text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio4_3box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio4_3text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio9_16box.setBackgroundResource(R.drawable.boxwhiteborder);
                ratio9_16text.setTextColor(Color.parseColor("#FFFFFF"));
                ratio16_9box.setBackgroundResource(R.drawable.boxwithblueborder);
                ratio16_9text.setTextColor(Color.parseColor("#00E6FF"));
            }
        });


        cropbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cropView.isChangingScale()) {
                    Bitmap b = cropView.getCroppedImage();
                    if (b != null) {
                        bitmapConvertToFile(b);
                        donebtn.setEnabled(true);
                        donebtn.setTextColor(Color.parseColor("#FFFFFF"));
                        cropbtn.setEnabled(false);
                        cropbtn.setTextColor(Color.parseColor("#7CFFFFFF"));
                        cropView.saveState();
                    }
                    else {
                        Toast.makeText(Imagecropper.this, "Image Cropping Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(cropimguri);
                setResult(RESULT_OK , intent);
                finish();
            }
        });
    }

    public File bitmapConvertToFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile = null;
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory("Deckoutadmin"), "");
                if (!file.exists()) {
                    file.mkdir();
                }


            bitmapFile = new File(file, "Dresspic_" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime()) + ".png");
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            MediaScannerConnection.scanFile(this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    runOnUiThread(() -> Toast.makeText(Imagecropper.this, "File saved Sucessfully", Toast.LENGTH_LONG).show());
                }
            });
        } catch (Exception e) {
            Log.e(">>data" , e.toString());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }

        cropimguri =  Uri.parse(bitmapFile.getPath());
        return bitmapFile;
    }
}
