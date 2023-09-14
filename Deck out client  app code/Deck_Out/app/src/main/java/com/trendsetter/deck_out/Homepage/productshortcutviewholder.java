package com.trendsetter.deck_out.Homepage;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.R;

public class productshortcutviewholder extends RecyclerView.ViewHolder {

        ImageView itemimg1 , itemimg2 , itemimg3 ,itemimg4 , itemimg5 , itemimg6 ,itemimg7 , itemimg8 , itemimg9 ,itemimgbanner1 , itemimgbanner2 ;
        TextView itemimgtxt;

        public productshortcutviewholder(View itemView) {
            super(itemView);

            itemimg1 = itemView.findViewById(R.id.shortcutimg1);
            itemimg2 = itemView.findViewById(R.id.shortcutimg2);
            itemimg3 = itemView.findViewById(R.id.shortcutimg3);
            itemimg4 = itemView.findViewById(R.id.shortcutimg4);
            itemimg5 = itemView.findViewById(R.id.shortcutimg5);
            itemimg6 = itemView.findViewById(R.id.shortcutimg6);
            itemimg7 = itemView.findViewById(R.id.shortcutimg7);
            itemimg8 = itemView.findViewById(R.id.shortcutimg8);
            itemimg9 = itemView.findViewById(R.id.shortcutimg9);

            itemimgbanner1 = itemView.findViewById(R.id.homepageshortcutbanner1);
            itemimgbanner2 = itemView.findViewById(R.id.homepageshortcutbanner2);

            itemimgtxt = itemView.findViewById(R.id.homepageshortcutimgtxt);

    }

    void setads1Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimgbanner1);
    }

    void setads2Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimgbanner2);
    }


    void set1Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg1);
    }

    void set2Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg2);
    }

    void set3Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg3);
    }

    void set4Image(String imgurl )
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg4);
    }

    void set5Image(String imgurl)
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg5);
    }

    void set6Image(String imgurl)
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg6);
    }
    void set7Image(String imgurl)
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg7);
    }
    void set8Image(String imgurl)
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg8);
    }
    void set9Image(String imgurl)
    {

        Picasso.get().load(imgurl).placeholder(R.drawable.ic_loadingthumb).fit().into(itemimg9);
    }


    public  void setName(String nametxt)
    {
        itemimgtxt.setText(nametxt);
    }
}
