package com.athenas.am.athenas;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    ProgressBar loginProgress;
    private EditText edtLogin,edtSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.login_progress_bar);
        loginProgress.setVisibility(View.INVISIBLE);

        edtLogin = (EditText) findViewById(R.id.login_mail);
        edtSenha = (EditText) findViewById(R.id.login_password);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                String loginMail = edtLogin.getText().toString();
                String loginPassword = edtSenha.getText().toString();

                if (loginMail.equals("adm@athenas") && loginPassword.equals("123")) {
                    alert("Login realizado com sucesso");
                    launchMainActivity(v);
                    finish();

                } else {
                    alert("Login e/ou senha incorretos");
                    loginProgress.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                }
                /*
                if(!loginMail.equals("") && !loginPassword.equals("")){
                    mAuth.signInWithEmailAndPassword(loginMail,loginPassword);
                }else{
                    alert("Login e/ou senha incorretos");
                }*/

            }
        });



    }

    //Firebase
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void launchMainActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

}
