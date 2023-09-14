package com.trendsetter.deck_out.Profile;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.naver.android.helloyako.imagecrop.util.BitmapLoadUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trendsetter.deck_out.Extra.ImageViewer;
import com.trendsetter.deck_out.Extra.Imagecropper;
import com.trendsetter.deck_out.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Profilepic extends Fragment {

    private  Uri camimageUri , ImageUri;
    private CircleImageView profileimageview;
    private CircularProgressBar profileprogressbar;
    private NotificationCompat.Builder uploadNotification;
    private NotificationManager notificationManager;
    private String openpropicurl ="";
    SharedPreferences sp;
    String userid , channelId;
    int imageWidth = 1000 , imageHeight = 1000;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilepiclayout, container, false);

        sp=getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userid =sp.getString("userid","");
        profileprogressbar = view.findViewById(R.id.profile_progressbar);
        profileimageview = view.findViewById(R.id.profileimageview);


        try {
            final Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.deckoutlauncher);
            final Intent emptyIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            channelId = "1";
            notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notificationManager.getNotificationChannel(channelId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(channelId, "Upload Profile pic", importance);
                    notificationManager.createNotificationChannel(mChannel);
                }
                uploadNotification = new NotificationCompat.Builder(getActivity(), channelId);
                uploadNotification.setContentTitle("Filename")                            // required
                        .setSmallIcon(R.drawable.thumb_on)
                        .setLargeIcon(notificicon)
                        .setContentText("uploading")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker("processing");
            }

            else {


                uploadNotification = new NotificationCompat.Builder(getActivity())
                        .setProgress(0, 0, true)
                        .setSmallIcon(R.drawable.thumb_on, 30)
                        .setContentTitle("Filename")
                        .setContentText("processing")
                        .setLargeIcon(notificicon)
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .setColor(Color.parseColor("#c4ef0404"))
                        .setContentInfo("counting");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        profileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Choose from Library","View Profile pic","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Upload Profile pic");
                builder.setCancelable(false);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                         if (items[item].equals("Choose from Library")) {
                            try {
                                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickPhoto.setType("image/*");
                                startActivityForResult(pickPhoto, 1);
                            }
                            catch (Exception e)
                            {
                                /*final Dialog storagemessage = new Dialog(getContext());
                                storagemessage.setContentView(R.layout.storagemessage);
                                Button storageokbtn = (Button) storagemessage.findViewById(R.id.storagepermibtn);
                                storageokbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        storagemessage.dismiss();
                                    }
                                });
                                storagemessage.show();*/
                            }
                        }

                        else if (items[item].equals("View Profile pic")) {
                            if(openpropicurl.equals(""))
                                Toast.makeText(getContext() , "Image not load yet or not found please try again " , Toast.LENGTH_SHORT ).show();
                            else
                            getContext().startActivity(new Intent(getContext(),ImageViewer.class).putExtra("productimgurl",openpropicurl).putExtra("productname","Profile Picture"));
                        }

                        else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }

                });
                builder.show();

            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        setprofileimages();
    }

    public void profilemageupload(final Uri fileuri)
    {
        Uri uploadUri = Uri.fromFile(new File(fileuri.toString()));

        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath =  storageref.child("Profile").child(userid).child(uploadUri.getLastPathSegment());

        filepath.putFile(uploadUri).addOnSuccessListener(getActivity(),new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri imgurl = urlTask.getResult();
                uploadNotification.setContentText("Done");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                uploadimagedetail(imgurl.toString());
            }
        }).addOnFailureListener(getActivity(),new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadNotification.setContentText("Failed");
                uploadNotification.setOngoing(false);
                notificationManager.notify(1, uploadNotification.build());
                Toast.makeText(getActivity(),"Error ! to upload profile pic.",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(getActivity(),new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                String fileinfo = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount() + " bytes";
                uploadNotification.setProgress(100, progress.intValue(), false);
                uploadNotification.setContentInfo(fileinfo);
                uploadNotification.setContentText("Uploading");
                notificationManager.notify(1, uploadNotification.build());
            }
        });

    }

    public void uploadimagedetail(String propicdir)
    {
        DatabaseReference profilepicref= FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Profilepic");
        profilepicref.setValue(propicdir);

    }


    private void setprofileimages()
    {
        DatabaseReference profilepicrefrence = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+userid+"/Profilepic");
        profilepicrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileprogressbar.setVisibility(View.VISIBLE);
                openpropicurl = dataSnapshot.getValue().toString();
                Picasso.get().load(dataSnapshot.getValue().toString()).placeholder(R.drawable.ic_avatar).fit().into(profileimageview, new Callback() {
                    @Override
                    public void onSuccess() {
                        profileprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        profileimageview.setImageResource(R.drawable.ic_avatar);
                        profileprogressbar.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
                String filePath;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    filePath = getRealPathFromURI_API19(getContext(), data.getData());
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    filePath = getRealPathFromURI_API11to18(getContext(), data.getData());
                } else {
                    filePath = getRealPathFromURI_BelowAPI11(getContext(), data.getData());
                }

                Uri filePathUri = Uri.parse(filePath);
                loadAsync(filePathUri);
                 }

            if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {
                profilemageupload(data.getData());

            }

        }
        catch (Exception e)
        {

        }
    }

    private boolean opencropper(final Uri uri, final Bitmap bitmap) {

        Intent intent = new Intent(getApplicationContext(), Imagecropper.class);
        intent.setData(uri);
        startActivityForResult(intent, 2);
        return true;
    }



    private void loadAsync(final Uri uri) {
        DownloadAsync task = new DownloadAsync();
        task.execute(uri);
    }

    class DownloadAsync extends AsyncTask<Uri, Void, Bitmap> implements DialogInterface.OnCancelListener {

        ProgressDialog mProgress;
        private Uri mUri;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(getContext());
            mProgress.setIndeterminate(true);
            mProgress.setCancelable(true);
            mProgress.setMessage("Loading image...");
            mProgress.setOnCancelListener(this);
            mProgress.show();
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            mUri = params[0];

            Bitmap bitmap;

//            while (mImageContainer.getWidth() < 1) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            final int w = mImageContainer.getWidth();
//            Log.d(TAG, "width: " + w);
            bitmap = BitmapLoadUtils.decode(mUri.toString(), imageWidth, imageHeight, true);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (mProgress.getWindow() != null) {
                mProgress.dismiss();
            }

            if (result != null) {
                opencropper(mUri, result);
            }
            else {
                Toast.makeText(getContext(), "Failed to load image " + mUri, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            Log.i("Cancelled", "onProgressCancel");
            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("oncancelled", "onCancelled");
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null) {
            int columnIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(columnIndex);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int columnIndex
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

}
