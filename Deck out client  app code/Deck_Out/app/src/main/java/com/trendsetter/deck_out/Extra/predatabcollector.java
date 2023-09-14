package com.trendsetter.deck_out.Extra;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sachinvarma.easypermission.EasyPermission;
import com.sachinvarma.easypermission.EasyPermissionConstants;
import com.sachinvarma.easypermission.EasyPermissionInit;
import com.sachinvarma.easypermission.EasyPermissionList;
import com.trendsetter.deck_out.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class predatabcollector extends AppCompatActivity {

    List<String> permission;
    TextView datacollectormessagetxt;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.preadatacollectionlayout);

        datacollectormessagetxt = findViewById(R.id.datacollectormessage);


        permission = new ArrayList<>();
        permission.add(EasyPermissionList.READ_EXTERNAL_STORAGE);
        permission.add(EasyPermissionList.WRITE_EXTERNAL_STORAGE);
        permission.add(EasyPermissionList.READ_PHONE_STATE);


    }


    @Override
    protected void onStart() {
        super.onStart();

        new settingpermission().execute();
    }

    private class settingpermission extends AsyncTask
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @SuppressLint("WrongThread")
        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            datacollectormessagetxt.setText("Checking app permission");
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            new EasyPermissionInit(predatabcollector.this, permission);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EasyPermissionConstants.INTENT_CODE:

                if (data != null) {
                    boolean isGotAllPermissions =
                            data.getBooleanExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, false);

                    if(data.hasExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION)){
                        if (isGotAllPermissions) {
                            openavailmessagediag();

                        }

                        else {
                              openpermissiondiag();

                        }}

                    // if you want to know which are the denied permissions.
                    if (data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST) != null) {

                        ArrayList deniedPermissions = new ArrayList<>();

                        deniedPermissions.addAll((Collection<? extends String>) data.getSerializableExtra(
                                EasyPermissionConstants.DENIED_PERMISSION_LIST));

                        if (deniedPermissions.size() > 0) {
                            for (int i = 0; i < deniedPermissions.size(); i++) {

                                if(EasyPermissionList.READ_EXTERNAL_STORAGE == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                    openpermissiondiag();

                                }



                                if(EasyPermissionList.READ_PHONE_STATE == deniedPermissions.get(i)) {

                                    Toast.makeText(this, "Phone state Permission not granted", Toast.LENGTH_SHORT)
                                            .show();

                                    openpermissiondiag();

                                }

                            }
                        }
                    }
                }
        }
    }


    private  void  openpermissiondiag()
    {
        AlertDialog.Builder permissionmsgdiag = new AlertDialog.Builder(predatabcollector.this , R.style.AlertDialog);
        permissionmsgdiag.setTitle("Permission Required");
        permissionmsgdiag.setMessage("\nSome permission are required to proceed further\n\n1) Storage [ to upload  profile picture ] .\n\n2) Phone state [ to check service avialibilty ] .");
        permissionmsgdiag.setCancelable(false);

        permissionmsgdiag.setNegativeButton(
                "proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new EasyPermissionInit(predatabcollector.this, permission);
                    }
                });




        AlertDialog alertDialog = permissionmsgdiag.create();
        alertDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }


    private boolean checkcountycode()
    {
        boolean checkavaiable = false;

        if (getUserCountry(predatabcollector.this ).equals("in"))

        {
            checkavaiable = true;
        }


        else
        {
            checkavaiable = false;
        }


        return  checkavaiable;
    }


    private  void openavailmessagediag()
    {

        /*if (!checkcountycode()) {
            Dialog avialmsgdialog = new Dialog(predatabcollector.this);
            avialmsgdialog.setContentView(R.layout.availibilitystatslayout);
            avialmsgdialog.setTitle(null);
            avialmsgdialog.show();

            Button appclosebtn = avialmsgdialog.findViewById(R.id.avialcloseappbtn);
            appclosebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = avialmsgdialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

        }

        else
        {*/

            datacollectormessagetxt.setText("Finializing ... ");
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();

            }
            startActivity(new Intent(predatabcollector.this , Introapp.class));
            finish();

    }


    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }
}
