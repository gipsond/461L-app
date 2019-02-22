package com.example.resyoume;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NFCActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback{
    NfcAdapter nfc_adapter;
    EditText message;
    TextView response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        message = (EditText) findViewById(R.id.SendText);
        response = (TextView) findViewById(R.id.ReceiveText);
        nfc_adapter = NfcAdapter.getDefaultAdapter(this);
        if(nfc_adapter == null){
            return;
        }
        if(!nfc_adapter.isEnabled()) {
            return;
        }
    }

    public void send_nfc(View view){
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        status.setText("Sending...");
        nfc_adapter.setNdefPushMessageCallback(this, this);
    }

    public void receive_nfc(View view){
        TextView status = (TextView) findViewById(R.id.NFCStatus);
        status.setText("Receiving...");
    }

    public NdefMessage createNdefMessage(NfcEvent nfcEvent){
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
            Parcelable[] raw_messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) raw_messages[0];
            response.setText(new String(msg.getRecords()[0].getPayload()));
        }
    }
}
