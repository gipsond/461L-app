package com.example.resyoume;

import android.content.Context;
import android.content.Intent;

public class IntentFactory {
    public Intent createIntent(String type, Context context, Extra extra){
        Intent result = null;
        if(type.equals("NFC")){
            result = new Intent(context, NFCActivity.class);
        }
        else if(type.equals("Basic")){
            result = new Intent(context, DisplayBasic.class);
        }
        else if(type.equals("Card")){
            result = new Intent(context, DisplayActivityCard.class);
        }
        else if(type.equals("McCombs")){
            result = new Intent(context, DisplayMccombs.class);
        }
        else if(type.equals("CLA")){
            result = new Intent(context, DisplayCLA.class);
        }
        else if(type.equals("ECAC")){
            result = new Intent(context, DisplayECAC.class);
        }
        else if(type.equals("Functional")){
            result = new Intent(context, DisplayFunctional.class);
        }
        else if(type.equals("NR")){
            result = new Intent(context, SetRatingAndNote.class);
        }
        else if(type.equals("Edit")){
            result = new Intent(context, ResumeEditActivity.class);
        }
        else if(type.equals("CompanyDisplay")){
            result = new Intent(context, DisplayCompanyInfo.class);
        }
        else if(type.equals("CompanyNR")){
            result = new Intent(context, SetRatingAndNoteCompany.class);
        }
        if(extra != null && result != null){
            result.putExtra(extra.name, extra.data);
        }
        return result;
    }
}

class Extra{
    String name;
    String data;
    public Extra(String name, String data){
        this.name = name;
        this.data = data;
    }
    public Extra(){
        this.name = null;
        this.data = null;
    }
}
