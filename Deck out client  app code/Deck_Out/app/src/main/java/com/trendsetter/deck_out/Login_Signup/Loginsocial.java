/*
package com.trendsetter.deck_out.Login_Signup;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.trendsetter.deck_out.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Loginsocial extends AppCompatActivity {

    String  useremail , userprofilepic;
    ImageView userpics;
    Button submitbtn;
    CallbackManager callbackManager;
    String facebook_id , profile_image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsociallayout);

    */
/*     useremail = getIntent().getExtras().getString("useremail");
         userprofilepic =  getIntent().getExtras().getString("userimgurl");*//*

         userpics = findViewById(R.id.userpic);
         submitbtn = findViewById(R.id.socialloginbtn);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

         submitbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 LoginManager.getInstance().logInWithReadPermissions(Loginsocial.this, Arrays.asList("email","public_profile"));
                 LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                     @Override
                     public void onSuccess(LoginResult loginResult) {
                */
/*handleFacebookAccessToken(loginResult.getAccessToken());
                Log.e(">>error" , loginResult.toString());*//*


                         //By Profile Class
                         Profile profile = Profile.getCurrentProfile();
                         if (profile != null) {
                             facebook_id=profile.getId();
                             String  full_name=profile.getName();
                             profile_image=profile.getProfilePictureUri(400, 400).toString();
                             checkdata("" , facebook_id, profile_image);
                         }
                         //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                         GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                 new GraphRequest.GraphJSONObjectCallback() {
                                     @Override
                                     public void onCompleted(JSONObject object, GraphResponse response) {
                                         try {
                                             String  email_id=object.getString("email");
                                             String profile_name=object.getString("name");
                                             long fb_id=object.getLong("id"); //use this for logout
                                             checkdata(email_id , facebook_id, profile_image);

                                         } catch (JSONException e) {
                                             Log.e(">>error" , e.toString());
                                         }

                                     }

                                 });

                         request.executeAsync();
                     }

                     @Override
                     public void onCancel() {

                     }

                     @Override
                     public void onError(FacebookException error) {

                         Toast.makeText(getApplicationContext() , error.toString() , Toast.LENGTH_LONG).show();

                     }
                 });
             }
         });

    }



    private  void checkdata (String emailid , String id , String picurl)
    {

       Log.e(">>data" ,  emailid +"  ,  "+id+"  ,  "+picurl);

    }

}
*/
