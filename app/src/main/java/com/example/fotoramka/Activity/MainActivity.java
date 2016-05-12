package com.example.fotoramka.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.fotoramka.R;

public class MainActivity extends Activity {

    private EditText _clientId;
    private EditText _edToken;
    private TextView _text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        _clientId = (EditText) findViewById(R.id.EdClientId);

    }

    public void  SignIn(View view){
        Uri address = Uri.parse("https://oauth.yandex.ru/authorize?response_type=token&client_id="+_clientId.getText().toString());
         Intent openLink = new Intent("browser",address);
        startActivity(openLink);
    }




}




