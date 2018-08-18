package com.athenas.am.athenas;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    ProgressBar loginProgress;
    private EditText edtLogin,edtSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.login_progress_bar);
        loginProgress.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                TextView tLoginMail = (TextView) findViewById(R.id.login_mail);
                TextView tLoginPassword = (TextView) findViewById(R.id.login_password);
                String loginMail =tLoginMail.getText().toString();
                String loginPassword =tLoginPassword.getText().toString();

                if (loginMail.equals("adm@athenas") && loginPassword.equals("123")) {
                    alert("Login realizado com sucesso");
                    launchMainActivity(v);
                    finish();

                } else {
                    alert("Login e/ou senha incorretos");
                    loginProgress.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    public void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
    public void launchMainActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
