package com.athenas.am.athenas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView imageCard,audioCard,notebookCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando os cards
        imageCard = (CardView) findViewById(R.id.image_card);
        audioCard = (CardView) findViewById(R.id.audio_card);
        notebookCard = (CardView) findViewById(R.id.notebook_card);

        //Adicionando o listener nos cards
        imageCard.setOnClickListener(this);
        audioCard.setOnClickListener(this);
        notebookCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            //@TODO - CRIAR CLASSES E AJUSTAR O SWITCH - AJUSTAR O CICLO DE VIDA DA ACTIVITY
            case R.id.image_card: i = new Intent(this, CameraActivity.class); startActivity(i); break;
            case R.id.audio_card: i = new Intent(this, CameraActivity.class); startActivity(i); break;
            case R.id.notebook_card: i = new Intent(this, CameraActivity.class); startActivity(i); break;
            default: break;
        }
    }



}
