package com.trendsetter.deck_out.Extra;

import android.app.Activity;
import android.content.Context;

import com.trendsetter.deck_out.R;

public class Emaildata {


    private String otp = new Otpgenerator().generateotp();
    private String  data= "<html style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n" +
            " <head> \n" +
            "  <meta charset=\"UTF-8\"> \n" +
            "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n" +
            "  <meta name=\"x-apple-disable-message-reformatting\"> \n" +
            "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n" +
            "  <meta content=\"telephone=no\" name=\"format-detection\"> \n" +
            "  <title>hdh</title> \n" +
            "  <!--[if (mso 16)]>\n" +
            "    <style type=\"text/css\">\n" +
            "    a {text-decoration: none;}\n" +
            "    </style>\n" +
            "    <![endif]--> \n" +
            "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n" +
            "  <style type=\"text/css\">\n" +
            "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important } h1 { font-size:30px!important; text-align:center } h2 { font-size:26px!important; text-align:center } h3 { font-size:20px!important; text-align:center } h1 a { font-size:30px!important } h2 a { font-size:26px!important } h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } .es-button { font-size:20px!important; display:block!important; border-width:10px 0px 10px 0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\n" +
            "#outlook a {\n" +
            "	padding:0;\n" +
            "}\n" +
            ".ExternalClass {\n" +
            "	width:100%;\n" +
            "}\n" +
            ".ExternalClass,\n" +
            ".ExternalClass p,\n" +
            ".ExternalClass span,\n" +
            ".ExternalClass font,\n" +
            ".ExternalClass td,\n" +
            ".ExternalClass div {\n" +
            "	line-height:100%;\n" +
            "}\n" +
            ".es-button {\n" +
            "	mso-style-priority:100!important;\n" +
            "	text-decoration:none!important;\n" +
            "}\n" +
            "a[x-apple-data-detectors] {\n" +
            "	color:inherit!important;\n" +
            "	text-decoration:none!important;\n" +
            "	font-size:inherit!important;\n" +
            "	font-family:inherit!important;\n" +
            "	font-weight:inherit!important;\n" +
            "	line-height:inherit!important;\n" +
            "}\n" +
            ".es-desk-hidden {\n" +
            "	display:none;\n" +
            "	float:left;\n" +
            "	overflow:hidden;\n" +
            "	width:0;\n" +
            "	max-height:0;\n" +
            "	line-height:0;\n" +
            "	mso-hide:all;\n" +
            "}\n" +
            "</style> \n" +
            " </head> \n" +
            " <body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:10;Margin:10;\"> \n" +
            "  <div class=\"es-wrapper-color\" style=\"background-color:#F6F6F6;\"> \n" +
            "   <!--[if gte mso 9]>\n" +
            "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
            "				<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
            "			</v:background>\n" +
            "		<![endif]--> \n" +
            "   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-wrapper\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\"> \n" +
            "     <tr style=\"border-collapse:collapse;\"> \n" +
            "      <td valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:20px;padding-left:20px;padding-right:20px;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"560\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" style=\"padding:0;Margin:0;\"> <a href=\"https://play.google.com/store/apps/details?id=com.trendsetter.deck_out\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:14px;text-decoration:underline;color:#1376C8;\"><img src=\"https://lh3.googleusercontent.com/HEd5s-te7JU2qVXnbNlWBlpAOZDmySIjHRlDL39BDE4qdPKPrnWVgN0IxZpeqjiKAHg\" alt=\"\" width=\"124\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\"> </a> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"560\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:18px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:27px;color:#000000;\"><u><strong>Deck Out The Dress Rental Application</strong></u></p> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:20px;Margin:0;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"560\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"left\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">A rental store to fulfill the requirements and needs to people on the basis of the functions.</p> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"600\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" height=\"40\" bgcolor=\"#f6f6f6\" style=\"padding:0;Margin:0;\"> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"560\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:20px;\"> <h3 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:15px;font-style:normal;font-weight:normal;color:#333333;\">Enter this code to verify your Email ID</h3> </td> \n" +
            "                     </tr> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;\">OTP CODE -&nbsp;<span style=\"font-size:20px;\"><strong>"+otp+"</strong></span></p> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"600\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" height=\"40\" bgcolor=\"#f6f6f6\" style=\"padding:0;Margin:0;\"> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> \n" +
            "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n" +
            "         <tr style=\"border-collapse:collapse;\"> \n" +
            "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n" +
            "           <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n" +
            "             <tr style=\"border-collapse:collapse;\"> \n" +
            "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;background-image:url(https://deckout.000webhostapp.com/bills/deckoutemaildbottom.jpg);background-position:left top;background-color:transparent;background-repeat:no-repeat;\" bgcolor=\"transparent\"> \n" +
            "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                 <tr style=\"border-collapse:collapse;\"> \n" +
            "                  <td width=\"560\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n" +
            "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n" +
            "                     <tr style=\"border-collapse:collapse;\"> \n" +
            "                      <td align=\"center\" style=\"padding:0;Margin:0;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:15px;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:23px;color:#FFFFFF;\">For further quiers feel free to mail us on - contact.deckout@gmail.com</p> </td> \n" +
            "                     </tr> \n" +
            "                   </table> </td> \n" +
            "                 </tr> \n" +
            "               </table> </td> \n" +
            "             </tr> \n" +
            "           </table> </td> \n" +
            "         </tr> \n" +
            "       </table> </td> \n" +
            "     </tr> \n" +
            "   </table> \n" +
            "  </div>  \n" +
            " </body>\n" +
            "</html>";


    public String getdata()
    {
        return data;
    }

    public String getOtp()
    {
        return otp;
    }
}
