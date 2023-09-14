package com.trendsetter.deck_out.Extra;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.trendsetter.deck_out.R;

public class Sizerangegetadapter {

   private Context context;
   private ArrayAdapter<CharSequence> kidspantlengthsizesmalladapter , kidspantwaistsizesmalladapter
                             , kidspantlengthsizemediumadapter  , kidspantwaistsizemediumadapter
                             , kidspantlengthsizelargeadapter , kidspantwaistsizelargeadapter
                             , kidspantlengthsizexlargeadapter , kidspantwaistsizexlargeladapter

                             , kidsjumpsuitlengthsizesmalladapter   , kidsjumpsuitwaistsizesmalladapter, kidsjumpsuitchestsizesmalladapter , kidsjumpsuithipsizesmalladapter
                             , kidsjumpsuitlengthsizemediumadapter , kidsjumpsuitwaistsizemediumadapter, kidsjumpsuitchestsizemediumadapter , kidsjumpsuithipsizemediumadapter
                             , kidsjumpsuitlengthsizelargeadapter , kidsjumpsuitwaistsizelargeadapter, kidsjumpsuitchestsizelargeadapter , kidsjumpsuithipsizelargeadapter
                             , kidsjumpsuitlengthsizeextralargeadapter , kidsjumpsuitwaistsizeextralargeadapter, kidsjumpsuitchestsizeextralargeadapter , kidsjumpsuithipsizeextralargeadapter

                             , kidsfrocklengthsizesmalladapter  , kidsfrockwaistsizesmalladapter, kidsfrockchestsizesmalladapter
                             , kidsfrocklengthsizemediumadapter  , kidsfrockwaistsizemediumadapter , kidsfrockchestsizemediumadapter
                             , kidsfrocklengthsizelargeadapter , kidsfrockwaistsizelargeadapter , kidsfrockchestsizelargeadapter
                             , kidsfrocklengthsizeextralargeadapter , kidsfrockwaistsizeextralargeadapter , kidsfrockchestsizeextralargeadapter

                             , kidstshirtlengthsizesmalladapter , kidstshirtchestsizesmalladapter
                             , kidstshirtlengthsizemediumadapter , kidstshirtchestsizemediumadapter
                             , kidstshirtlengthsizelargeadapter , kidstshirtchestsizelargeadapter
                             , kidstshirtlengthsizeextralargeadapter , kidstshirtchestsizeextralargeadapter

                             , kidsshirtlengthsizesmalladapter , kidsshirtchestsizesmalladapter
                             , kidsshirtlengthsizemediumadapter , kidsshirtchestsizemediumadapter
                             , kidsshirtlengthsizelargeadapter , kidsshirtchestsizelargeadapter
                             , kidsshirtlengthsizeextralargeadapter , kidsshirtchestsizeextralargeadapter

                             , Menspantlengthsizesmalladapter , Menspantwaistsizesmalladapter
                             , Menspantlengthsizemediumadapter , Menspantwaistsizemediumadapter
                             , Menspantlengthsizelargeadapter , Menspantwaistsizelargeadapter
                             , Menspantlengthsizeextralargeadapter , Menspantwaistsizeextralargeadapter
                             , Menspantlengthsizeextraextralargeadapter , Menspantwaistsizeextraextralargeadapter

                             , Menstshirtlengthsizesmalladapter , Menstshirtchestsizesmalladaper, Menstshirtshouldersizesmalladapter
                             , Menstshirtlengthsizemediumadapter , Menstshirtchestsizemediumadapter , Menstshirtshouldersizemediumadapter
                             , Menstshirtlengthsizelargeadapter , Menstshirtchestsizelargeadapter , Menstshirtshouldersizelargeadapter
                             , Menstshirtlengthsizeextralargeadapter , Menstshirtchestsizeextralargeadapter , Menstshirtshouldersizeeextralargeadapter
                             , Menstshirtlengthsizeextraextralargeadapter , Menstshirtchestsizeextraextralargeadapter , Menstshirtshouldersizeextraextralargeadapter

                             , Mensshirtlengthsizesmalladapter , Mensshirtchestsizesmalladapter , Mensshirtsleevesizesmalladapter
                             , Mensshirtlengthsizemediumadapter , Mensshirtchestsizemediumadapter , Mensshirtsleevesizemediumadapter
                             , Mensshirtlengthsizelargeadapter , Mensshirtchestsizelargeadapter , Mensshirtsleevesizelargeadapter
                             , Mensshirtlengthsizeextralargeadapter , Mensshirtchestsizeextralargeadapter , Mensshirtsleevesizeeextralargeadapter
                             , Mensshirtlengthsizeextraextralargeadapter , Mensshirtchestsizeextraextralargeadapter , Mensshirtsleevesizeextraextralargeadapter

                             , Womenpantlengthsizeextrasmalladapter , Womenpantwaistsizeextrasmalladapter
                             , Womenpantlengthsizesmalladapter  , Womenpantwaistsizesmalladapter
                             , Womenpantlengthsizemediumadapter , Womenpantwaistsizemediumadapter
                             , Womenpantlengthsizelargeadapter , Womenpantwaistsizelargeadaopter
                             , Womenpantlengthsizeextralargeadapter , Womenpantwaistsizeextralargeadapter
                             , Womenpantlengthsizeextraextralargeadapter , Womenpantwaistsizeextraextralargeadapter

                             , Womenjumpsuitlengthsizeextrasmalladapter , Womenjumpsuitwaistsizeextrasmalladapter , Womenjumpsuitchestsizeextrasmalladapter , Womenjumpsuithipsizeextrasmalladapter
                             , Womenjumpsuitlengthsizesmalladapter , Womenjumpsuitwaistsizesmalladapter , Womenjumpsuitchestsizesmalladapter , Womenjumpsuithipsizesmalladapter
                             , Womenjumpsuitlengthsizemediumadapter , Womenjumpsuitwaistsizemediumadapter , Womenjumpsuitchestsizemediumadapter , Womenjumpsuithipsizemediumadapter
                             , Womenjumpsuitlengthsizelargeadapter , Womenjumpsuitwaistsizelargeadapter , Womenjumpsuitchestsizelargeadapter , Womenjumpsuithipsizelargeadapter
                             , Womenjumpsuitlengthsizeextralargeadapter , Womenjumpsuitwaistsizeextralargeadapter , Womenjumpsuitchestsizeextralargeadapter , Womenjumpsuithipsizeextralargeadapter
                             , Womenjumpsuitlengthsizeextraextralargeadapter , Womenjumpsuitwaistsizeextraextralargeadapter , Womenjumpsuitchestsizeextraextralargeadapter , Womenjumpsuithipsizeextraextralargeadapter

                             , Womenfrocklengthsizeextrasmalladapter , Womenfrockwaistsizeextrasmalladapter , Womenfrockhipsizeextrasmalladapter
                             , Womenfrocklengthsizesmalladapter , Womenfrockwaistsizesmalladapter , Womenfrockhipsizesmalladapter
                             , Womenfrocklengthsizemediumadapter , Womenfrockwaistsizemediumadapter , Womenfrockhipsizemediumadapter
                             , Womenfrocklengthsizelargeadapter , Womenfrockwaistsizelargeadapter , Womenfrockhipsizelargeadapter
                             , Womenfrocklengthsizeextralargeadapter , Womenfrockwaistsizeextralargeadapter , Womenfrockhipsizeextralargeadapter
                             , Womenfrocklengthsizeextraextralargeadapter , Womenfrockwaistsizeextraextralargeadapter , Womenfrockhipsizeextraextralargeadapter

                             , Womentshirtlengthsizeextrasmalladapter , Womentshirtshouldersizeextrasmalladapter
                             , Womentshirtlengthsizesmalladapter , Womentshirtshouldersizesmalladapter
                             , Womentshirtlengthsizemediumadapter , Womentshirtshouldersizemediumadapter
                             , Womentshirtlengthsizelargeadapter , Womentshirtshouldersizelargeadapter
                             , Womentshirtlengthsizeextralargeadapter , Womentshirtshouldersizeextralargeadapter
                             , Womentshirtlengthsizeextraextralargeadapter , Womentshirtshouldersizeextraextralargeadapter

                             , Womenshirtlengthsizeextrasmalladapter
                             , Womenshirtlengthsizesmalladapter
                             , Womenshirtlengthsizemediumadapter
                             , Womenshirtlengthsizelargeadapter
                             , Womenshirtlengthsizeextralargeadapter
                             , Womenshirtlengthsizeextraextralargeadapter ;







    private  String kidspantsmall , kidspantmedium , kidspantlarge , kidspantxlarge ,
             kidsjumpsuitsmall , kidsjumpsuitmedium , kidsjumpsuitlarge , kidsjumpsuitxlarge ,
             kidsfrocksmall , kidsfrockmedium , kidsfrocklarge , kidsfrockxlarge ,
             kidstshirtsmall , kidstshirtmedium , kidstshirtlarge , kidstshirtxlarge ,
             kidsshirtsmall , kidsshirtmedium , kidsshirtlarge , kidsshirtxlarge ,

             menspantsmall , menspantmedium , menspantlarge , menspantxlarge , menspantxxlarge ,
             menstshirtsmall , menstshirtmedium , menstshirtlarge , menstshirtxlarge , menstshirtxxlarge ,
             mensshirtsmall , mensshirtmedium , mensshirtlarge , mensshirtxlarge , mensshirtxxlarge ,

             womenpantxsmall , womenpantsmall , womenpantmedium , womenpantlarge , womenpantxlarge ,  womenpantxxlarge ,
             womenjumpsuitxsmall , womenjumpsuitsmall , womenjumpsuitmedium , womenjumpsuitlarge , womenjumpsuitxlarge , womenjumpsuitxxlarge ,
             womenfrockxsmall ,  womenfrocksmall , womenfrockmedium , womenfrocklarge , womenfrockxlarge , womenfrockxxlarge ,
             womentshirtxsmall , womentshirtsmall , womentshirtmedium , womentshirtlarge , womentshirtxlarge ,womentshirtxxlarge ,
             womenshirtxsmall , womenshirtsmall , womenshirtmedium , womenshirtlarge , womenshirtxlarge  , womenshirtxxlarge ;


    private void codechecker( String code , String sizetype)
    {


        if(code.trim().equals(kidspantsmall.trim()) && sizetype.trim().equals(context.getResources().getString(R.string.sizesmall).trim()))
        {
            kidspantlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidspantlengthsizesmall));
            kidspantwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidspantwaistsizesmall));
        }


        if(code.trim().equals(kidspantmedium.trim()) && sizetype.equals(context.getResources().getString(R.string.sizemedium)))
        {
            kidspantlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidspantlengthsizemedium));
            kidspantwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidspantwaistsizemedium));
        }

        if(code.trim() .equals( kidspantlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            kidspantlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidspantlengthsizelarge));
            kidspantwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidspantwaistsizelarge));
        }

        if(code.trim() .equals( kidspantxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            kidspantlengthsizexlargeadapter = setadapter(context.getResources().getStringArray(R.array.kidspantlengthsizeextralarge));
            kidspantwaistsizexlargeladapter = setadapter(context.getResources().getStringArray(R.array.kidspantwaistsizeextralarge));
        }


        if(code.trim() .equals( kidsjumpsuitsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            kidsjumpsuitlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitlengthsizesmall));
            kidsjumpsuitwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitwaistsizesmall));
            kidsjumpsuitchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitchestsizesmall));
            kidsjumpsuithipsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuithipsizesmall));
        }


        if(code.trim() .equals( kidsjumpsuitmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            kidsjumpsuitlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitlengthsizemedium));
            kidsjumpsuitwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitwaistsizemedium));
            kidsjumpsuitchestsizemediumadapter= setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitchestsizemedium));
            kidsjumpsuithipsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuithipsizemedium));
        }

        if(code.trim() .equals( kidsjumpsuitlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            kidsjumpsuitlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitlengthsizelarge));
            kidsjumpsuitwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitwaistsizelarge));
            kidsjumpsuitchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitchestsizelarge));
            kidsjumpsuithipsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuithipsizelarge));
        }

        if(code.trim() .equals( kidsjumpsuitxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            kidsjumpsuitlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitlengthsizeextralarge));
            kidsjumpsuitwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitwaistsizeextralarge));
            kidsjumpsuitchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuitchestsizeextralarge));
            kidsjumpsuithipsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsjumpsuithipsizeextralarge));
        }

        if(code.trim() .equals( kidsfrocksmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            kidsfrocklengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsfrocklengthsizesmall));
            kidsfrockwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockwaistsizesmall));
            kidsfrockchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockchestsizesmall));
        }

        if(code.trim() .equals( kidsfrockmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            kidsfrocklengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrocklengthsizemedium));
            kidsfrockwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockwaistsizemedium));
            kidsfrockchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockchestsizemedium));
        }

        if(code.trim() .equals( kidsfrocklarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            kidsfrocklengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrocklengthsizelarge));
            kidsfrockwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockwaistsizelarge));
            kidsfrockchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockchestsizelarge));
        }

        if(code.trim() .equals( kidsfrockxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            kidsfrocklengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrocklengthsizeextralarge));
            kidsfrockwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockwaistsizeextralarge));
            kidsfrockchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsfrockchestsizeextralarge));
        }

        if(code.trim() .equals( kidstshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            kidstshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtlengthsizesmall));
            kidstshirtchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtchestsizesmall));
        }

        if(code.trim() .equals( kidstshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            kidstshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtlengthsizemedium));
            kidstshirtchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtchestsizemedium));
        }

        if(code.trim() .equals( kidstshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            kidstshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtlengthsizelarge));
            kidstshirtchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtchestsizelarge));
        }


        if(code.trim() .equals( kidstshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            kidstshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtlengthsizeextralarge));
            kidstshirtchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidstshirtchestsizeextralarge));
        }

        if(code.trim() .equals( kidsshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            kidsshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtlengthsizesmall));
            kidsshirtchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtchestsizesmall));
        }

        if(code.trim() .equals( kidsshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            kidsshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtlengthsizemedium));
            kidsshirtchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtchestsizemedium));
        }

        if(code.trim() .equals( kidsshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            kidsshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtlengthsizelarge));
            kidsshirtchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtchestsizelarge));
        }


        if(code.trim() .equals( kidsshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            kidsshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtlengthsizeextralarge));
            kidsshirtchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.kidsshirtchestsizeextralarge));
        }

        if(code.trim() .equals( menspantsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Menspantlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Menspantlengthsizesmall));
            Menspantwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Menspantwaistsizesmall));
        }

        if(code.trim() .equals( menspantmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Menspantlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Menspantlengthsizemedium));
            Menspantwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Menspantwaistsizemedium));
        }

        if(code.trim() .equals( menspantlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Menspantlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantlengthsizelarge));
            Menspantwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantwaistsizelarge));
        }

        if(code.trim() .equals( menspantxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Menspantlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantlengthsizeextralarge));
            Menspantwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantwaistsizeextralarge));
        }

        if(code.trim() .equals( menspantxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Menspantlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantlengthsizeextraextralarge));
            Menspantwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menspantwaistsizeextraextralarge));
        }

        if(code.trim() .equals( menstshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Menstshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtlengthsizesmall));
            Menstshirtchestsizesmalladaper = setadapter(context.getResources().getStringArray(R.array.Menstshirtchestsizesmall));
            Menstshirtshouldersizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtshouldersizesmall));
        }

        if(code.trim() .equals( menstshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Menstshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtlengthsizemedium));
            Menstshirtchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtchestsizemedium));
            Menstshirtshouldersizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtshouldersizemedium));
        }

        if(code.trim() .equals( menstshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Menstshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtlengthsizelarge));
            Menstshirtchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtchestsizelarge));
            Menstshirtshouldersizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtshouldersizelarge));
        }

        if(code.trim() .equals( menstshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Menstshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtlengthsizeextralarge));
            Menstshirtchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtchestsizeextralarge));
            Menstshirtshouldersizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtshouldersizeextraextralarge));
        }

        if(code.trim() .equals( menstshirtxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Menstshirtlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtlengthsizeextraextralarge));
            Menstshirtchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtchestsizeextraextralarge));
            Menstshirtshouldersizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Menstshirtshouldersizeextraextralarge));
        }


        if(code.trim() .equals( mensshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Mensshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtlengthsizesmall));
            Mensshirtchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtchestsizesmall));
            Mensshirtsleevesizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtsleevesizesmall));

        }

        if(code.trim() .equals( mensshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Mensshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtlengthsizemedium));
            Mensshirtchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtchestsizemedium));
            Mensshirtsleevesizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtsleevesizemedium));
        }

        if(code.trim() .equals( mensshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Mensshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtlengthsizelarge));
            Mensshirtchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtchestsizelarge));
            Mensshirtsleevesizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtsleevesizelarge));
        }

        if(code.trim() .equals( mensshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Mensshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtlengthsizeextralarge));
            Mensshirtchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtchestsizeextralarge));
            Mensshirtsleevesizeeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtsleevesizeextraextralarge));
        }

        if(code.trim() .equals( mensshirtxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Mensshirtlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtlengthsizeextraextralarge));
            Mensshirtchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtchestsizeextraextralarge));
            Mensshirtsleevesizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Mensshirtsleevesizeextraextralarge));
        }

        if(code.trim() .equals( womenpantxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womenpantlengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizeextrasmall));
            Womenpantwaistsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizeextrasmall));
        }

        if(code.trim() .equals( womenpantsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Womenpantlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizesmall));
            Womenpantwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizesmall));
        }


        if(code.trim() .equals( womenpantmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Womenpantlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizemedium));
            Womenpantwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizemedium));
        }

        if(code.trim() .equals( womenpantlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Womenpantlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizelarge));
            Womenpantwaistsizelargeadaopter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizelarge));
        }

        if(code.trim() .equals( womenpantxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Womenpantlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizeextralarge));
            Womenpantwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizeextralarge));
        }

        if(code.trim() .equals( womenpantxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenpantlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantlengthsizeextraextralarge));
            Womenpantwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenpantwaistsizeextraextralarge));
        }

        if(code.trim() .equals( womenjumpsuitxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womenjumpsuitlengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextrasmall));
            Womenjumpsuitwaistsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextrasmall));
            Womenjumpsuitchestsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextrasmall));
            Womenjumpsuithipsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextrasmall));
        }

        if(code.trim() .equals( womenjumpsuitsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Womenjumpsuitlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizesmall));
            Womenjumpsuitwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizesmall));
            Womenjumpsuitchestsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizesmall));
            Womenjumpsuithipsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizesmall));
        }

        if(code.trim() .equals( womenjumpsuitmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Womenjumpsuitlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizemedium));
            Womenjumpsuitwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizemedium));
            Womenjumpsuitchestsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizemedium));
            Womenjumpsuithipsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizemedium));
        }

        if(code.trim() .equals( womenjumpsuitlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Womenjumpsuitlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizelarge));
            Womenjumpsuitwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizelarge));
            Womenjumpsuitchestsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizelarge));
            Womenjumpsuithipsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizelarge));
        }

        if(code.trim() .equals( womenjumpsuitxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Womenjumpsuitlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextralarge));
            Womenjumpsuitwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextralarge));
            Womenjumpsuitchestsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextralarge));
            Womenjumpsuithipsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextralarge));
        }

        if(code.trim() .equals( womenjumpsuitxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenjumpsuitlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextraextralarge));
            Womenjumpsuitwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextraextralarge));
            Womenjumpsuitchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextraextralarge));
            Womenjumpsuithipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextraextralarge));
        }

        if(code.trim() .equals( womenfrockxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womenfrocklengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizeextrasmall));
            Womenfrockwaistsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizeextrasmall));
            Womenfrockhipsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizeextrasmall));
        }

        if(code.trim() .equals( womenjumpsuitxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenjumpsuitlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextraextralarge));
            Womenjumpsuitwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextraextralarge));
            Womenjumpsuitchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextraextralarge));
            Womenjumpsuithipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextraextralarge));
        }


        if(code.trim() .equals( womenjumpsuitxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenjumpsuitlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextraextralarge));
            Womenjumpsuitwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextraextralarge));
            Womenjumpsuitchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextraextralarge));
            Womenjumpsuithipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextraextralarge));
        }

        if(code.trim() .equals( womenjumpsuitxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenjumpsuitlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextraextralarge));
            Womenjumpsuitwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextraextralarge));
            Womenjumpsuitchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextraextralarge));
            Womenjumpsuithipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextraextralarge));
        }

        if(code.trim() .equals( womenjumpsuitxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenjumpsuitlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitlengthsizeextraextralarge));
            Womenjumpsuitwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitwaistsizeextraextralarge));
            Womenjumpsuitchestsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuitchestsizeextraextralarge));
            Womenjumpsuithipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenjumpsuithipsizeextraextralarge));
        }

        if(code.trim() .equals( womenfrockxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womenfrocklengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizeextrasmall));
            Womenfrockwaistsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizeextrasmall));
            Womenfrockhipsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizeextrasmall));
        }

        if(code.trim() .equals( womenfrocksmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Womenfrocklengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizesmall));
            Womenfrockwaistsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizesmall));
            Womenfrockhipsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizesmall));
        }

        if(code.trim() .equals( womenfrockmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Womenfrocklengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizemedium));
            Womenfrockwaistsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizemedium));
            Womenfrockhipsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizemedium));
        }

        if(code.trim() .equals( womenfrocklarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Womenfrocklengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizelarge));
            Womenfrockwaistsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizelarge));
            Womenfrockhipsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizelarge));
        }

        if(code.trim() .equals( womenfrockxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Womenfrocklengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizeextralarge));
            Womenfrockwaistsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizeextralarge));
            Womenfrockhipsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizeextralarge));
        }

        if(code.trim() .equals( womenfrockxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenfrocklengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrocklengthsizeextraextralarge));
            Womenfrockwaistsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockwaistsizeextraextralarge));
            Womenfrockhipsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenfrockhipsizeextraextralarge));
        }

        if(code.trim() .equals( womentshirtxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womentshirtlengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizeextrasmall));
            Womentshirtshouldersizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizeextrasmall));
        }

        if(code.trim() .equals( womentshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Womentshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizesmall));
            Womentshirtshouldersizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizesmall));
        }

        if(code.trim() .equals( womentshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Womentshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizemedium));
            Womentshirtshouldersizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizemedium));
        }

        if(code.trim() .equals( womentshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Womentshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizelarge));
            Womentshirtshouldersizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizelarge));
        }

        if(code.trim() .equals( womentshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Womentshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizeextralarge));
            Womentshirtshouldersizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizeextralarge));
        }

        if(code.trim() .equals( womentshirtxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womentshirtlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtlengthsizeextraextralarge));
            Womentshirtshouldersizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womentshirtshouldersizeextraextralarge));
        }

        if(code.trim() .equals( womenshirtxsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexsmall)))
        {
            Womenshirtlengthsizeextrasmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizeextrasmall));
        }

        if(code.trim() .equals( womenshirtsmall.trim()) && sizetype .equals( context.getResources().getString(R.string.sizesmall)))
        {
            Womenshirtlengthsizesmalladapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizesmall));
        }

        if(code.trim() .equals( womenshirtmedium.trim()) && sizetype .equals( context.getResources().getString(R.string.sizemedium)))
        {
            Womenshirtlengthsizemediumadapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizemedium));
        }

        if(code.trim() .equals( womenshirtlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizelarge)))
        {
            Womenshirtlengthsizelargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizelarge));
        }

        if(code.trim() .equals( womenshirtxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexlarge)))
        {
            Womenshirtlengthsizeextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizeextralarge));
        }

        if(code.trim() .equals( womenshirtxxlarge.trim()) && sizetype .equals( context.getResources().getString(R.string.sizexxlarge)))
        {
            Womenshirtlengthsizeextraextralargeadapter = setadapter(context.getResources().getStringArray(R.array.Womenshirtlengthsizeextraextralarge));
        }


    }

    private void inistilizedata()
    {
        kidspantsmall = context.getResources().getString(R.string.kps);
        kidspantmedium = context.getResources().getString(R.string.kpm);
        kidspantlarge = context.getResources().getString(R.string.kpl);
        kidspantxlarge = context.getResources().getString(R.string.kpxl);

        kidsjumpsuitsmall = context.getResources().getString(R.string.kjss);
        kidsjumpsuitmedium = context.getResources().getString(R.string.kjsm);
        kidsjumpsuitlarge = context.getResources().getString(R.string.kjsl);
        kidsjumpsuitxlarge = context.getResources().getString(R.string.kjsxl);

        kidsfrocksmall = context.getResources().getString(R.string.kfs);
        kidsfrockmedium = context.getResources().getString(R.string.kfm);
        kidsfrocklarge = context.getResources().getString(R.string.kfl);
        kidsfrockxlarge = context.getResources().getString(R.string.kfxl);

        kidstshirtsmall = context.getResources().getString(R.string.ktss);
        kidstshirtmedium = context.getResources().getString(R.string.ktsm);
        kidstshirtlarge = context.getResources().getString(R.string.ktsl);
        kidstshirtxlarge = context.getResources().getString(R.string.ktsxl);

        kidsshirtsmall = context.getResources().getString(R.string.kss);
        kidsshirtmedium = context.getResources().getString(R.string.ksm);
        kidsshirtlarge = context.getResources().getString(R.string.ksl);
        kidsshirtxlarge = context.getResources().getString(R.string.ksxl);


        menspantsmall = context.getResources().getString(R.string.mps);
        menspantmedium = context.getResources().getString(R.string.mpm);
        menspantlarge= context.getResources().getString(R.string.mpl);
        menspantxlarge = context.getResources().getString(R.string.mpxl);
        menspantxxlarge = context.getResources().getString(R.string.mpxxl);

        menstshirtsmall = context.getResources().getString(R.string.mtss);
        menstshirtmedium = context.getResources().getString(R.string.mtsm);
        menstshirtlarge= context.getResources().getString(R.string.mtsl);
        menstshirtxlarge = context.getResources().getString(R.string.mtsxl);
        menstshirtxxlarge = context.getResources().getString(R.string.mtsxxl);

        mensshirtsmall = context.getResources().getString(R.string.mss);
        mensshirtmedium = context.getResources().getString(R.string.msm);
        mensshirtlarge= context.getResources().getString(R.string.msl);
        mensshirtxlarge = context.getResources().getString(R.string.msxl);
        mensshirtxxlarge = context.getResources().getString(R.string.msxxl);

        womenpantxsmall =  context.getResources().getString(R.string.wpxs);
        womenpantsmall =  context.getResources().getString(R.string.wps);
        womenpantmedium =  context.getResources().getString(R.string.wpm);
        womenpantlarge =  context.getResources().getString(R.string.wpl);
        womenpantxlarge =  context.getResources().getString(R.string.wpxl);
        womenpantxxlarge =  context.getResources().getString(R.string.wpxxl);

        womenjumpsuitxsmall =  context.getResources().getString(R.string.wjsxs);
        womenjumpsuitsmall =  context.getResources().getString(R.string.wjss);
        womenjumpsuitmedium =  context.getResources().getString(R.string.wjsm);
        womenjumpsuitlarge =  context.getResources().getString(R.string.wjsl);
        womenjumpsuitxlarge =  context.getResources().getString(R.string.wjsxl);
        womenjumpsuitxxlarge =  context.getResources().getString(R.string.wjsxxl);


        womenfrockxsmall =  context.getResources().getString(R.string.wfxs);
        womenfrocksmall =  context.getResources().getString(R.string.wfs);
        womenfrockmedium =  context.getResources().getString(R.string.wfm);
        womenfrocklarge =  context.getResources().getString(R.string.wfl);
        womenfrockxlarge =  context.getResources().getString(R.string.wfxl);
        womenfrockxxlarge =  context.getResources().getString(R.string.wfxxl);


        womentshirtxsmall =  context.getResources().getString(R.string.wtsxs);
        womentshirtsmall =  context.getResources().getString(R.string.wtss);
        womentshirtmedium =  context.getResources().getString(R.string.wtsm);
        womentshirtlarge =  context.getResources().getString(R.string.wtsl);
        womentshirtxlarge =  context.getResources().getString(R.string.wtsxl);
        womentshirtxxlarge =  context.getResources().getString(R.string.wtsxxl);

        womenshirtxsmall =  context.getResources().getString(R.string.wsxs);
        womenshirtsmall =  context.getResources().getString(R.string.wss);
        womenshirtmedium =  context.getResources().getString(R.string.wsm);
        womenshirtlarge =  context.getResources().getString(R.string.wsl);
        womenshirtxlarge =  context.getResources().getString(R.string.wsxl);
        womenshirtxxlarge =  context.getResources().getString(R.string.wsxxl);
    }


    public void setdata(Context context , String code , String sizetype)
    {
        this.context = context;
        inistilizedata();
        codechecker(code , sizetype);

    }

    private ArrayAdapter <CharSequence> setadapter (String [] sizerange)
    {
        return new ArrayAdapter<CharSequence>(context, R.layout.sizeselectortext,sizerange);
    }


    public ArrayAdapter <CharSequence> setkidspantlengthsizesmalldapter()
    {
        return kidspantlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidspantwaistsizesmalldapter()
    {
        return kidspantwaistsizesmalladapter;
    }


    public ArrayAdapter <CharSequence> setkidspantlengthsizemediumadapter()
    {
        return kidspantlengthsizemediumadapter;
    }


    public ArrayAdapter <CharSequence> setkidspantwaistsizemediumadapter()
    {
        return kidspantwaistsizemediumadapter;
    }


    public ArrayAdapter <CharSequence> setkidspantlengthsizelargeadapter ()
    {
        return kidspantlengthsizelargeadapter;
    }


    public ArrayAdapter <CharSequence> setkidspantwaistsizelargeadapter()
    {
        return kidspantwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidspantlengthsizexlargeadapter()
    {
        return kidspantlengthsizexlargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidspantwaistsizexlargeladapter()
    {
        return kidspantwaistsizexlargeladapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitlengthsizesmalladapter()
    {
        return kidsjumpsuitlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitwaistsizesmalladapter()
    {
        return kidsjumpsuitwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitchestsizesmalladapter()
    {
        return kidsjumpsuitchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuithipsizesmalladapter()
    {
        return kidsjumpsuithipsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitlengthsizemediumadapter()
    {
        return kidsjumpsuitlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitwaistsizemediumadapter()
    {
        return kidsjumpsuitwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitchestsizemediumadapter()
    {
        return kidsjumpsuitchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuithipsizemediumadapter()
    {
        return kidsjumpsuithipsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitlengthsizelargeadapter()
    {
        return kidsjumpsuitlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitwaistsizelargeadapter()
    {
        return kidsjumpsuitwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitchestsizelargeadapter()
    {
        return kidsjumpsuitchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuithipsizelargeadapter()
    {
        return kidsjumpsuithipsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitlengthsizeextralargeadapter()
    {
        return kidsjumpsuitlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitwaistsizeextralargeadapter()
    {
        return kidsjumpsuitwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuitchestsizeextralargeadapter()
    {
        return kidsjumpsuitchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsjumpsuithipsizeextralargeadapter()
    {
        return kidsjumpsuithipsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrocklengthsizesmalladapter()
    {
        return kidsfrocklengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockwaistsizesmalladapter()
    {
        return kidsfrockwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockchestsizesmalladapter()
    {
        return kidsfrockchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrocklengthsizemediumadapter()
    {
        return kidsfrocklengthsizemediumadapter;
    }


    public ArrayAdapter <CharSequence> setkidsfrockwaistsizemediumadapter()
    {
        return kidsfrockwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockchestsizemediumadapter()
    {
        return kidsfrockchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrocklengthsizelargeadapter()
    {
        return kidsfrocklengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockwaistsizelargeadapter()
    {
        return kidsfrockwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockchestsizelargeadapter()
    {
        return kidsfrockchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrocklengthsizeextralargeadapter()
    {
        return kidsfrocklengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockwaistsizeextralargeadapter()
    {
        return kidsfrockwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsfrockchestsizeextralargeadapter()
    {
        return kidsfrockchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtlengthsizesmalladapter()
    {
        return kidstshirtlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtchestsizesmalladapter()
    {
        return kidstshirtchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtlengthsizemediumadapter()
    {
        return kidstshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtchestsizemediumadapter()
    {
        return kidstshirtchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtlengthsizelargeadapter()
    {
        return kidstshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtchestsizelargeadapter()
    {
        return kidstshirtchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtlengthsizeextralargeadapter()
    {
        return kidstshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidstshirtchestsizeextralargeadapter()
    {
        return kidstshirtchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtlengthsizesmalladapter()
    {
        return kidsshirtlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtchestsizesmalladapter()
    {
        return kidsshirtchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtlengthsizemediumadapter()
    {
        return kidsshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtchestsizemediumadapter()
    {
        return kidsshirtchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtlengthsizelargeadapter()
    {
        return kidsshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtchestsizelargeadapter()
    {
        return kidsshirtchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtlengthsizeextralargeadapter()
    {
        return kidsshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setkidsshirtchestsizeextralargeadapter()
    {
        return kidsshirtchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantlengthsizesmalladapter()
    {
        return Menspantlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMenspantwaistsizesmalladapter()
    {
        return Menspantwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMenspantlengthsizemediumadapter()
    {
        return Menspantlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantwaistsizemediumadapter()
    {
        return Menspantwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantlengthsizelargeadapter()
    {
        return Menspantlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantwaistsizelargeadapter()
    {
        return Menspantwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantlengthsizeextralargeadapter()
    {
        return Menspantlengthsizeextralargeadapter;
    }


    public ArrayAdapter <CharSequence> setMenspantwaistsizeextralargeadapter()
    {
        return Menspantwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantlengthsizeextraextralargeadapter()
    {
        return Menspantlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenspantwaistsizeextraextralargeadapter()
    {
        return Menspantwaistsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtlengthsizesmalladapter()
    {
        return Menstshirtlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtchestsizesmalladaper()
    {
        return Menstshirtchestsizesmalladaper;
    }

    public ArrayAdapter <CharSequence> setMenstshirtshouldersizesmalladapter()
    {
        return Menstshirtshouldersizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtlengthsizemediumadapter()
    {
        return Menstshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtchestsizemediumadapter()
    {
        return Menstshirtchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtshouldersizemediumadapter()
    {
        return Menstshirtshouldersizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtlengthsizelargeadapter()
    {
        return Menstshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtchestsizelargeadapter()
    {
        return Menstshirtchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtshouldersizelargeadapter()
    {
        return Menstshirtshouldersizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtlengthsizeextralargeadapter()
    {
        return Menstshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtchestsizeextralargeadapter()
    {
        return Menstshirtchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtshouldersizeeextralargeadapter()
    {
        return Menstshirtshouldersizeeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtlengthsizeextraextralargeadapter()
    {
        return Menstshirtlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtchestsizeextraextralargeadapter()
    {
        return Menstshirtchestsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMenstshirtshouldersizeextraextralargeadapter()
    {
        return Menstshirtshouldersizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtlengthsizesmalladapter()
    {
        return Mensshirtlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtchestsizesmalladapter()
    {
        return Mensshirtchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtsleevesizesmalladapter()
    {
        return Mensshirtsleevesizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtlengthsizemediumadapter()
    {
        return Mensshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtchestsizemediumadapter()
    {
        return Mensshirtchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtsleevesizemediumadapter()
    {
        return Mensshirtsleevesizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtlengthsizelargeadapter()
    {
        return Mensshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtchestsizelargeadapter()
    {
        return Mensshirtchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtsleevesizelargeadapter()
    {
        return Mensshirtsleevesizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtlengthsizeextralargeadapter()
    {
        return Mensshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtchestsizeextralargeadapter()
    {
        return Mensshirtchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtsleevesizeeextralargeadapter()
    {
        return Mensshirtsleevesizeeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtlengthsizeextraextralargeadapter()
    {
        return Mensshirtlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtchestsizeextraextralargeadapter()
    {
        return Mensshirtchestsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setMensshirtsleevesizeextraextralargeadapter()
    {
        return Mensshirtsleevesizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizeextrasmalladapter()
    {
        return Womenpantlengthsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizeextrasmalladapter()
    {
        return Womenpantwaistsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizesmalladapter()
    {
        return Womenpantlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizesmalladapter()
    {
        return Womenpantwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizemediumadapter()
    {
        return Womenpantlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizemediumadapter()
    {
        return Womenpantwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizelargeadapter()
    {
        return Womenpantlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizelargeadaopter()
    {
        return Womenpantwaistsizelargeadaopter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizeextralargeadapter()
    {
        return Womenpantlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizeextralargeadapter()
    {
        return Womenpantwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantlengthsizeextraextralargeadapter()
    {
        return Womenpantlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenpantwaistsizeextraextralargeadapter()
    {
        return Womenpantwaistsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizeextrasmalladapter()
    {
        return Womenjumpsuitlengthsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizeextrasmalladapter()
    {
        return Womenjumpsuitwaistsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizeextrasmalladapter()
    {
        return Womenjumpsuitchestsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizeextrasmalladapter()
    {
        return Womenjumpsuithipsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizesmalladapter()
    {
        return Womenjumpsuitlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizesmalladapter()
    {
        return Womenjumpsuitwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizesmalladapter()
    {
        return Womenjumpsuitchestsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizesmalladapter()
    {
        return Womenjumpsuithipsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizemediumadapter()
    {
        return Womenjumpsuitlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizemediumadapter()
    {
        return Womenjumpsuitwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizemediumadapter()
    {
        return Womenjumpsuitchestsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizemediumadapter()
    {
        return Womenjumpsuithipsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizelargeadapter()
    {
        return Womenjumpsuitlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizelargeadapter()
    {
        return Womenjumpsuitwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizelargeadapter()
    {
        return Womenjumpsuitchestsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizelargeadapter()
    {
        return Womenjumpsuithipsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizeextralargeadapter()
    {
        return Womenjumpsuitlengthsizeextralargeadapter;
    }


    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizeextralargeadapter()
    {
        return Womenjumpsuitwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizeextralargeadapter()
    {
        return Womenjumpsuitchestsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizeextralargeadapter()
    {
        return Womenjumpsuithipsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitlengthsizeextraextralargeadapter()
    {
        return Womenjumpsuitlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitwaistsizeextraextralargeadapter()
    {
        return Womenjumpsuitwaistsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuitchestsizeextraextralargeadapter()
    {
        return Womenjumpsuitchestsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenjumpsuithipsizeextraextralargeadapter()
    {
        return Womenjumpsuithipsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizeextrasmalladapter()
    {
        return Womenfrocklengthsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizeextrasmalladapter()
    {
        return Womenfrockwaistsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizeextrasmalladapter()
    {
        return Womenfrockhipsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizesmalladapter()
    {
        return Womenfrocklengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizesmalladapter()
    {
        return Womenfrockwaistsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizesmalladapter()
    {
        return Womenfrockhipsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizemediumadapter()
    {
        return Womenfrocklengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizemediumadapter()
    {
        return Womenfrockwaistsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizemediumadapter()
    {
        return Womenfrockhipsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizelargeadapter()
    {
        return Womenfrocklengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizelargeadapter()
    {
        return Womenfrockwaistsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizelargeadapter()
    {
        return Womenfrockhipsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizeextralargeadapter()
    {
        return Womenfrocklengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizeextralargeadapter()
    {
        return Womenfrockwaistsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizeextralargeadapter()
    {
        return Womenfrockhipsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrocklengthsizeextraextralargeadapter()
    {
        return Womenfrocklengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockwaistsizeextraextralargeadapter()
    {
        return Womenfrockwaistsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenfrockhipsizeextraextralargeadapter()
    {
        return Womenfrockhipsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtlengthsizeextrasmalladapter()
    {
        return Womentshirtlengthsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizeextrasmalladapter()
    {
        return Womentshirtshouldersizeextrasmalladapter;
    }


    public ArrayAdapter <CharSequence> setWomentshirtlengthsizesmalladapter()
    {
        return Womentshirtlengthsizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizesmalladapter()
    {
        return Womentshirtshouldersizesmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtlengthsizemediumadapter()
    {
        return Womentshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizemediumadapter()
    {
        return Womentshirtshouldersizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtlengthsizelargeadapter()
    {
        return Womentshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizelargeadapter()
    {
        return Womentshirtshouldersizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtlengthsizeextralargeadapter()
    {
        return Womentshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizeextralargeadapter()
    {
        return Womentshirtshouldersizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtlengthsizeextraextralargeadapter()
    {
        return Womentshirtlengthsizeextraextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomentshirtshouldersizeextraextralargeadapter()
    {
        return Womentshirtshouldersizeextraextralargeadapter;
    }


    public ArrayAdapter <CharSequence> setWomenshirtlengthsizeextrasmalladapter()
    {
        return Womenshirtlengthsizeextrasmalladapter;
    }

    public ArrayAdapter <CharSequence> setWomenshirtlengthsizesmalladapter()
    {
        return Womenshirtlengthsizesmalladapter;
    }


    public ArrayAdapter <CharSequence> setWomenshirtlengthsizemediumadapter()
    {
        return Womenshirtlengthsizemediumadapter;
    }

    public ArrayAdapter <CharSequence> setWomenshirtlengthsizelargeadapter()
    {
        return Womenshirtlengthsizelargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenshirtlengthsizeextralargeadapter()
    {
        return Womenshirtlengthsizeextralargeadapter;
    }

    public ArrayAdapter <CharSequence> setWomenshirtlengthsizeextraextralargeadapter()
    {
        return Womenshirtlengthsizeextraextralargeadapter;
    }



}
