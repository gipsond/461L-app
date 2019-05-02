package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONException;
import org.json.JSONObject;

public class NFCActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback{
    NfcAdapter nfc_adapter;
    //EditText message;
    //private TextView response;
    private SingleResumeViewModel resumeViewModel;
    private CompanyInfoViewModel companyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        //message = (EditText) findViewById(R.id.SendText);
        //response = (TextView) findViewById(R.id.ReceiveText);
        nfc_adapter = NfcAdapter.getDefaultAdapter(this);
        if(nfc_adapter == null){
            status.setText("Could not access NFC adapter");
            return;
        }
        if(!nfc_adapter.isEnabled()) {
            status.setText("NFC adapter is disabled");
            return;
        }
        Intent intent = getIntent();
        String data = intent.getStringExtra("resumeJSON");
        if(data == null){
            data = intent.getStringExtra("companyInfoJSON");
        }
        if(data != null){
            //message.setText(data);
            NFCDataSingleton.getInstance().setMessage(data);
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if(type.equals("resume")){
                    JSONObject contactJson = dataJson.getJSONObject("contact");
                    String name = contactJson.getString("firstName") + " " + contactJson.getString("lastName");
                    status.setText("Ready to send the resume of " + name);
                }
                else{
                    String name = dataJson.getString("name");
                    status.setText("Ready to send the company information of " + name);
                }

            }
            catch (JSONException e) {}
            nfc_adapter.setNdefPushMessageCallback(this, this);
        }
        else{
            status.setText("Ready to receive resume or company information");
        }
        resumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
    }

    public NdefMessage createNdefMessage(NfcEvent nfcEvent){
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        //String to_send = message.getText().toString();
        String to_send = NFCDataSingleton.getInstance().getMessage();
        NdefRecord record = NdefRecord.createMime("application/vnd.com.example.android.beam", to_send.getBytes());
        NdefMessage msg = new NdefMessage(record);
        return msg;
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            TextView status = (TextView) findViewById(R.id.NFCStatus);
            Parcelable[] raw_messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) raw_messages[0];
            String received = new String(msg.getRecords()[0].getPayload());
            try {
                JSONObject receivedJson = new JSONObject(received);
                String type = receivedJson.getString("type");
                if(type.equals("resume")){
                    status.setText("Resume received. Ready to save");
                }
                else{
                    status.setText("Company Info received. Ready to save");
                }
            }
            catch (JSONException e) {}
            //getResponse().setText(received);
            NFCDataSingleton.getInstance().setResponse(received);
        }
    }

    public void saveResponseToDB(View view) {
        if (getResumeViewModel() == null) {
            resumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        }

        if (getCompanyInfoViewModel() == null) {
            companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
        }

        // only insert the resume if all of the response types are correct and array size is 3
        try {
            CharSequence toastText = "Invalid; couldn't save to database.";
            //JSONObject responseJson = new JSONObject(getResponse().getText().toString());
            JSONObject responseJson = new JSONObject(NFCDataSingleton.getInstance().getResponse());
            String type = responseJson.getString("type");

            if(type.equals("resume")){
                getResumeViewModel().insert(responseJson);
                toastText = "Resume added to database";
            }
            else if(type.equals("companyInfo")){
                getCompanyInfoViewModel().insert(responseJson);
                toastText = "CompanyInfo added to database";
            }

            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, toastText, duration);
            toast.show();

        }
        catch (Exception e) {
            // TODO: don't silently fail; at least do something
            //e.printStackTrace();
        }
    }

    //public TextView getResponse() {
        //return response;
    //}

    public SingleResumeViewModel getResumeViewModel() {
        return resumeViewModel;
    }

    public CompanyInfoViewModel getCompanyInfoViewModel() {
        return companyInfoViewModel;
    }
}
