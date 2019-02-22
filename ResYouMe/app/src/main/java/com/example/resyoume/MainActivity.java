package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // start the NFC activity
    public void onClickNFC(View view){
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        startActivity(nfc_intent);
    }

    // start the Database activity
    public void onClickDatabase(View view) {
        Intent db_intent = new Intent(this, DatabaseActivity.class);
        startActivity(db_intent);
    }
}
