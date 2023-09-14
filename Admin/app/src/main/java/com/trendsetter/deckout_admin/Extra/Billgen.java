package com.trendsetter.deckout_admin.Extra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.trendsetter.deckout_admin.R;

import java.io.File;
import java.util.ArrayList;

public class Billgen extends AppCompatActivity {

WebView webview ;
    String orderno = "";
    String orderinitdate = "";
    String orderduedate = "";
    String custname = "";
    String custmobno = "";
    String custdelhaddrs = "";
    String paymenttype = "";
    String totalamount = "";
    ProgressDialog pdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Uri uri = Uri.parse(getIntent().getExtras().getString("qrimgurl"));
        orderno = getIntent().getExtras().getString("orderidcode");
        orderduedate = getIntent().getExtras().getString("orderduedate");
        orderinitdate = getIntent().getExtras().getString("orderinitdate");
        custname = getIntent().getExtras().getString("custname");
        custmobno = getIntent().getExtras().getString("custmobno");
        custdelhaddrs = getIntent().getExtras().getString("custdelhaddrs");
        paymenttype = getIntent().getExtras().getString("paymenttype");
        totalamount = getIntent().getExtras().getString("totalamount");

        File imgfile = new File(uri.toString());


        pdialog = new ProgressDialog(Billgen.this, R.style.billgenprodialog );
        pdialog.setTitle("Gathering data");
        pdialog.setCancelable(false);
        pdialog.setMessage("Please wait ...........");
        pdialog.show();

        ArrayList<String> productlist = (ArrayList<String>) getIntent().getSerializableExtra("productnames");


        webview = findViewById(R.id.webviewtest);
        String newUA= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50";
        webview.getSettings().setUserAgentString(newUA);
        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
            }
        });

        StringBuilder htmlString = new StringBuilder();
        htmlString.append(new String("<html> <head> <title>Deck Out Processed bill</title><style>"));
        htmlString.append(new String(".invoice-box {max-width: 800px;margin: auto; padding: 30px; border: 1px solid #eee;box-shadow: 0 0 10px rgba(0, 0, 0, .15);font-size: 16px;line-height: 24px;font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; color: #555;}.invoice-box table {width: 100%;line-height: inherit;text-align: left;    }.invoice-box table td { padding: 5px; vertical-align: top;    }.invoice-box table tr td:nth-child(2) {text-align: right; } .invoice-box table tr.top table td { padding-bottom: 20px;  }.invoice-box table tr.top table td.title {font-size: 45px; line-height   } .invoice-box table tr.information table td { padding-bottom: 40px; } .invoice-box table tr.heading td {background: #eee;border-bottom: 1px solid #ddd;   font-weight: bold;   }.invoice-box table tr.details td {padding-bottom: 20px;    } .invoice-box table tr.item td{ border-bottom: 1px solid #eee;} .invoice-box table tr.item.last td {border-bottom: none;}.invoice-box table tr.total td:nth-child(2) {border-top: 2px solid #eee;font-weight: bold;}@media only screen and (max-width: 600px) {.invoice-box table tr.top table td {width: 100%;display: block;text-align: center;}.invoice-box table tr.information table td {width: 100%;display: block;text-align: center;}}.rtl {direction: rtl;font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; } .rtl table {text-align: right;}.rtl table tr td:nth-child(2){text-align: left;}</style></head>"));
        htmlString.append(new String("<body><div class=\"invoice-box\"> <table cellpadding=\"0\" cellspacing=\"0\"><tr class=\"top\">  <td colspan=\"2\"> <table> <tr> <td class=\"title\"><img src=\"https://deckout.000webhostapp.com/bills/companybilllogo.PNG\" style=\"width:100%; max-width:300px;\"></td> <td>   Order Number : "+orderno+"<br> Initiate : "+orderinitdate+"<br>Due : "+orderduedate+"</td></tr></table></td></tr><tr class=\"information\"><td colspan=\"2\"><table><tr><td>#5288 Modern ,  Housing Complex , Manimajra <br>Chandigarh  (160101)   <br> Mobile No - 9417404076 , 7888796343 <br>E- Mail : contact.deckout@gmail.com</td>  <td>Customer Name : "+custname+"<br>Mob no : "+custmobno+"<br> Address :  "+custdelhaddrs+"</td></tr></table>"));
        htmlString.append(new String("<div style=\"display: flex; justify-content: center;\"><img src=\"qrcode.png\"  style=\"width:100%; max-width:200px; \"></div></td></tr>"));
        htmlString.append(new String("<tr class=\"heading\"><td>Payment Method</td><td></td></tr> <tr class=\"details\"><td>"+paymenttype+"</td><td>&#x20B9; "+totalamount+" </td></tr>"));
        htmlString.append(new String("<tr class=\"heading\"><td>Item</td><td>Price</td></tr>"));

        for (String name : productlist) {

            String data [] = name.split(";");
            htmlString.append(new String("<tr class=\"item\"><td>"+data[0]+"</td><td>&#x20B9; "+data[1]+"</td></tr>"));
        }


        htmlString.append(new String("<tr class=\"total\"><td></td><td>Total: &#x20B9; "+totalamount+"</td> </tr>"));
        htmlString.append(new String("</table></div></body></html>"));

        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("file://"+imgfile.getAbsolutePath(), String.valueOf(htmlString), "text/HTML", "UTF-8", null);

    }


    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter("MyDocument");
        }

        String jobName = getString(R.string.app_name) + " Print docs";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        setafterprocesseddialog ();

    }

    private void setafterprocesseddialog ()
    {
        pdialog.dismiss();
        AlertDialog.Builder afterprocessedsmsg = new AlertDialog.Builder(Billgen.this);
        afterprocessedsmsg.setTitle("Message");
        afterprocessedsmsg.setCancelable(false);
        afterprocessedsmsg.setMessage("\nChoose any one option .");
        afterprocessedsmsg.setPositiveButton("Redownload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pdialog.show();
                createWebPrintJob(webview);
            }
        });
        afterprocessedsmsg.setNegativeButton("Go back",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        afterprocessedsmsg.show();
    }
}
