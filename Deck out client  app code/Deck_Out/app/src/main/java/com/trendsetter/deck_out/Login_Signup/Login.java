package com.trendsetter.deck_out.Login_Signup;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.trendsetter.deck_out.Extra.Drawablesdata;
import com.trendsetter.deck_out.Homepage.homepage;
import com.trendsetter.deck_out.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.Executor;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

public class Login extends Fragment {

    Button switchsignupbtn , loginbtn ;
    ImageView facebookloginbtn , googleloginbtn , showhidebtn;
    EditText loginid , loginpass;
    TextView frgtpassbtn;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 007;
    private boolean checkemailblock = false , checkphnoblock = false  , isNumeric , hideshowcheck = false;
    Drawable erroricon;
    CircleProgressBar loginprogressbar;
    Handler handler = new Handler();
    Runnable runnable;
    int finalI = 0 , logincounter=0;
    private SharedPreferences sp;
    String facebook_id , profile_image;
    private   SharedPreferences.Editor Ed;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sp=getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Ed=sp.edit();

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();



        mAuth = FirebaseAuth.getInstance();

         gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("792473902163-gh6j1uej2gmbih2kcior2sj6fbocevvl.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();



        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        erroricon = new Drawablesdata().getdrawablewhite(getActivity());
        View view = inflater.inflate(R.layout.loginpageloginlayout, container, false);
        switchsignupbtn = view.findViewById(R.id.switchsignup);
        showhidebtn = view.findViewById(R.id.showhidebtn);
        loginid = view.findViewById(R.id.loginid);
        loginpass = view.findViewById(R.id.loginpass);
        loginbtn = view.findViewById(R.id.loginbtn);
        frgtpassbtn = view.findViewById(R.id.frgtpassbtn);
        facebookloginbtn = view.findViewById(R.id.facebookloginbtn);
        googleloginbtn = view.findViewById(R.id.googleloginbtn);
        loginprogressbar = view.findViewById(R.id.loginprogressBar);

        instlistener();

        return view;
    }



    private void instlistener()
    {
        switchsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.dialog_in,R.anim.dialog_out);
                transaction.replace(R.id.loginpagefragmentcontainer, new Signup());
                transaction.addToBackStack(null).commit();
            }
        });

        showhidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hideshowcheck == false)
                {
                    loginpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showhidebtn.setImageResource(R.drawable.ic_hidepass);
                    hideshowcheck = true;
                }

                else
                {
                    loginpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showhidebtn.setImageResource(R.drawable.ic_showpass);
                    hideshowcheck = false;
                }
            }
        });

        loginpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if (s.length() > 0)
              {
                  showhidebtn.setVisibility(View.VISIBLE);
              }

              else
              {
                  showhidebtn.setVisibility(View.GONE);
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        frgtpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginprogressbar.setVisibility(View.VISIBLE);
                setprogress();
               getuid( loginid.getText().toString().replace("@", "~").replace(".", "`"), null);
            }
        });




        loginbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(loginid.getText().toString().trim().length() == 0)
                {
                    loginid.setError("is Empty",erroricon);
                    logincounter+=1;
                }

                if(loginpass.getText().toString().trim().length() == 0)
                {
                    loginpass.setError("is Empty",erroricon);
                    logincounter+=1;
                }

                if(logincounter == 0)
                {
                    setprogress();
                    proceedlogin();
                }


            }
        });


        /*facebookloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                *//*handleFacebookAccessToken(loginResult.getAccessToken());
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

        googleloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });*/



    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       /* FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);*/
    }


    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    private void startfrgtprocess(String UIDtxt)
    {
        startActivity(new Intent(getContext() , Loginforgotpass.class).putExtra("uid", UIDtxt));
        loginprogressbar.setVisibility(View.GONE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }



/*
    GraphRequest graphRequest;

    private void handleFacebookAccessToken(AccessToken token) {

        Profile fbprofile = Profile.getCurrentProfile();

        if (fbprofile != null)
        {
            String fullname = fbprofile.getName();
            String emailid = fbprofile.getId();
            String propicurl = fbprofile.getProfilePictureUri(300 , 300).toString();
            Toast.makeText(getApplicationContext(), ">> fbdata - "+fullname+" , "+emailid+" , "+propicurl , Toast.LENGTH_LONG );

            graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {

                        String fbemailid = object.getString("email");
                        String fbproname = object.getString("name");
                        Toast.makeText(getApplicationContext(), ">> fbdata - "+fullname+" , "+emailid+" , "+propicurl , Toast.LENGTH_LONG );
                        Log.e(">> fbdata" ," - "+fbemailid+" , "+fbproname+" , "+propicurl);
                    }

                    catch (JSONException e)
                    {

                    }
                }
            });

            graphRequest.executeAsync();
        }
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();

        }
        else {

        }
    }*/



        private void checkblockeduseremail(final String id)
    {
        final DatabaseReference checkdata = FirebaseDatabase.getInstance().getReference("blockedusers").child("emailid");
        checkdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id))
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("You have been Blocked by Deck Out .");
                    message.setCancelable(true);

                    message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

                else
                {
                    getuid(id  ,  loginpass.getText().toString().trim());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkblockeduserphoneno(final String id)
    {
        final DatabaseReference checkdata = FirebaseDatabase.getInstance().getReference("blockedusers").child("phno");
        checkdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id))
                {
                    AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                    message.setTitle("Message");
                    AlertDialog alert;
                    message.setMessage("You have been Blocked by Deck Out .");
                    message.setCancelable(true);

                    message.setNegativeButton("Ok",null);
                    alert = message.create();
                    alert.show();
                }

                else
                {
                    getuid(id ,  loginpass.getText().toString().trim());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loginuserwithemail(String uid , String id , String password)
    {
        final DatabaseReference checkuid = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid.trim());
        checkuid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             if(dataSnapshot.child("Emailid").getValue().equals(id.replace("~", "@").replace("`", ".")))
             {
                DatabaseReference checkpass = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid.trim());
                checkpass.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Password").getValue().equals(password)) {
                            startActivity(new Intent(getContext() , homepage.class).putExtra("userid",uid));
                            Ed.putString("userid", uid);
                            Ed.commit();
                        }

                        else
                        {

                            AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                            message.setTitle("Password not matched");
                            AlertDialog alert;
                            message.setMessage("\nDo you want to reset Password ?");
                            message.setCancelable(true);

                            message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startfrgtprocess(dataSnapshot.child(id).getValue().toString());
                                }
                            });
                            message.setNegativeButton("No",null);
                            alert = message.create();
                            alert.show();
                        }


                        handler.removeCallbacks(runnable);
                        loginprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
             }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginuserwithphone( String uid , String id , String password)
    {
        final DatabaseReference checkuid = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        checkuid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Mobno").getValue().equals(""+id))
                {
                    DatabaseReference checkpass = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
                    checkpass.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("Password").getValue().equals(password)) {
                                startActivity(new Intent(getContext() , homepage.class).putExtra("userid",uid));
                                Ed.putString("userid", uid);
                                Ed.commit();
                            }

                            else
                            {

                                AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                                message.setTitle("Password not matched");
                                AlertDialog alert;
                                message.setMessage("\nDo you want to reset Password ?");
                                message.setCancelable(true);

                                message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startfrgtprocess(dataSnapshot.child(id).getValue().toString());
                                    }
                                });
                                message.setNegativeButton("No",null);
                                alert = message.create();
                                alert.show();
                            }


                            handler.removeCallbacks(runnable);
                            loginprogressbar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



/*    private void loginwithsocial(String id , String picurl)
    {
        startActivity(new Intent(getContext() , Loginsocial.class).putExtra("useremail" , id ).putExtra("userimgurl" , picurl));
    }*/

    private void getuid(String id , String pass)
    {
        final DatabaseReference checkdata = FirebaseDatabase.getInstance().getReference("dataindex/userindex/client");
        if(isNumeric)
        {
            checkdata.child("Phoneno").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(id).exists()) {
                         if (pass == null)
                        {
                            startfrgtprocess(dataSnapshot.child(id).getValue().toString());
                        }
                         else
                         {
                             checkphverification(dataSnapshot.child(id).getValue().toString() , id , pass);
                         }
                    }

                    else
                        {

                        handler.removeCallbacks(runnable);
                        loginprogressbar.setVisibility(View.GONE);
                        AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                        message.setTitle("Message");
                        AlertDialog alert;
                        message.setMessage("Please Sign Up to use our service.");
                        message.setCancelable(true);

                        message.setNegativeButton("Ok", null);
                        alert = message.create();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        else {
            checkdata.child("Emailid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(id).exists())
                    {
                        if (pass == null)
                        {
                        startfrgtprocess(dataSnapshot.child(id).getValue().toString());
                        }
                        else
                        {
                            checkphverification(dataSnapshot.child(id).getValue().toString() , id , pass);
                        }

                    }

                    else
                    {

                        handler.removeCallbacks(runnable);
                        loginprogressbar.setVisibility(View.GONE);
                        AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                        message.setTitle("Message");
                        AlertDialog alert;
                        message.setMessage("Please Sign Up to use our service.");
                        message.setCancelable(true);
                        message.setNegativeButton("Ok",null);
                        alert = message.create();
                        alert.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void proceedlogin()
    {
        setprogress();
        loginprogressbar.setVisibility(View.VISIBLE);
        isNumeric = loginid.getText().toString().chars().allMatch( Character::isDigit );
        if (isNumeric)
        {
            checkblockeduserphoneno(loginid.getText().toString());
        }

        else
            {
            checkblockeduseremail(loginid.getText().toString().replace("@", "~").replace(".", "`"));
        }


    }


    @SuppressLint("ResourceAsColor")
    private void setprogress()
    {
        loginprogressbar.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_blue_dark,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        runnable = new Runnable() {

            @Override
            public void run() {
                if(finalI *10>=90){

                }
                else {
                    loginprogressbar.setProgress(finalI * 10);
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            finalI = i;
            handler.postDelayed(runnable,1000*(i+1));
        }


    }

    private void checkphverification(String uid , String id , String pass) {
        DatabaseReference checkphref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/" + uid);
        checkphref.child("Phonenoverifictaion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("#N")) {
                    getuserdata(uid);
                }

                else
               {
                   checkmnailverification(uid , id , pass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void checkmnailverification(String uid , String id , String pass)
    {
        DatabaseReference checkmailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid);
        checkmailidref.child("Mailidverifictaion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("#N"))
                {
                    getuserdata(uid);
                }

                else
                {
                    if (isNumeric)
                    {
                        loginuserwithphone(uid ,id , pass);
                    }

                    else
                    {
                        loginuserwithemail(uid,id , pass);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void getuserdata(String uid)
    {
        DatabaseReference useremailidref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/Emailid");
        useremailidref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String useremailidtxt = dataSnapshot.getValue().toString();
                DatabaseReference usermobnoref = FirebaseDatabase.getInstance().getReference("datarecords/deckoutusers/Client/"+uid+"/Mobno");
                usermobnoref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        openregverification(useremailidtxt , dataSnapshot.getValue().toString() , uid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void openregverification(String useremailid , String userphno , String uid)
    {
        startActivity(new Intent(getContext() , Regverificationph.class).putExtra("useremail",useremailid).putExtra("userphoneno" , userphno).putExtra("uid",uid));
    }



}
