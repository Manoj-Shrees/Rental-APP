package com.trendsetter.deck_out.Productdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Extra.ImageViewer;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;

public class Productslideimageadapter extends PagerAdapter{

        private ArrayList<String> imageModelArrayList;
        private LayoutInflater inflater;
        private Context context;
        private String Productnametxt;


        public Productslideimageadapter(Context context, ArrayList<String> imageModelArrayList , String Productnametxt) {
            this.context = context;
            this.imageModelArrayList = imageModelArrayList;
            this.Productnametxt = Productnametxt;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageModelArrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.mainpagedressviewpagerimage, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);


            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.get().load(imageModelArrayList.get(position)).placeholder(R.drawable.ic_loadingthumb).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,ImageViewer.class).putExtra("productimgurl",imageModelArrayList.get(position)).putExtra("productname",Productnametxt));
                }
            });

            view.addView(imageLayout, 0);

            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
}
