package com.example.resyoume;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.resyoume.db.Resume;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class NFCActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback{
    NfcAdapter nfc_adapter;
    EditText message;
    TextView response;
    SingleResumeViewModel resumeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        message = (EditText) findViewById(R.id.SendText);
        response = (TextView) findViewById(R.id.ReceiveText);
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
        String resume = intent.getStringExtra("resumeJSON");
        if(resume != null){
            message.setText(resume);
        }
        resumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
    }

    public void send_nfc(View view){
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        status.setText("Callback set. Looking for other device...");
        nfc_adapter.setNdefPushMessageCallback(this, this);
    }

    public NdefMessage createNdefMessage(NfcEvent nfcEvent){
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        status.setText("Device found. Sending message...");
        String to_send = message.getText().toString();
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
            status.setText("Message received");
            Parcelable[] raw_messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) raw_messages[0];
            response.setText(new String(msg.getRecords()[0].getPayload()));
        }
    }

    public void saveResponseToDB(View view) throws JSONException {
        if (resumeViewModel == null) {
            resumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        }
        // only insert the resume if all of the response types are correct and array size is 3
        try{
            JSONArray responseJson = new JSONArray(response.getText().toString());
            if(responseJson.length() == 3){
                JSONObject contact = responseJson.getJSONObject(0);
                JSONArray education = responseJson.getJSONArray(1);
                JSONArray work = responseJson.getJSONArray(2);
                Resume resume = new Resume(responseJson);
                resumeViewModel.insert(resume);
            }
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
}
