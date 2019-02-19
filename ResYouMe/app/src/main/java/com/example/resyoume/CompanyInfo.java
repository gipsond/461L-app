package com.example.resyoume;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CompanyInfo extends AppCompatActivity {

    EditText CompanyDomainName;
    TextView CompanyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        CompanyDomainName = (EditText) findViewById(R.id.SendText);
        CompanyInfo = (TextView) findViewById(R.id.ReceiveText);
    }

    public void onClickInfo(View view) throws IOException {
        Content response =  Request.Post("https://api.fullcontact.com/v3/company.enrich").addHeader("Authorization", "Bearer Kom1m2ezqBt2n2P46bo4dEw0uuZCNAIT").
                 body(new StringEntity("{" + "\"domain\":\"microsoft.com\"" + "}")).execute().returnContent();
        try {
            JSONObject compnayInfo = new JSONObject(response.asString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
