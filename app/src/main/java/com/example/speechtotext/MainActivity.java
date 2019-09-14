package com.example.speechtotext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
TextView voiceInput,speakButton;
Calendar  calendar; String timesysa; SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voiceInput=findViewById(R.id.txtview1);
        speakButton=findViewById(R.id.btnSpeak);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(MainActivity.this, "Clickeds", Toast.LENGTH_SHORT).show();

            }
        });
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bolhalkehalke();
            }
        });


    }

    private void bolhalkehalke() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi Speak Something");
        try {
            startActivityForResult(intent,100);
        }catch (ActivityNotFoundException a){

        }



    }
//Recieving speech input
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if (resultCode==RESULT_OK && null !=data){
//following result is object of string class
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceInput.setText(result.get(0));
                    //Save speech to text file
                    writedatainfile(voiceInput.getText().toString());
                }
                break;

        }
    }

    private void writedatainfile(String toString) {
      calendar=Calendar.getInstance();
      simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:ss");
      timesysa="ExternalData"+timesysa+".txt";
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);//Checking permission
        File folder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myFile = new File(folder,timesysa);//Filename 
        writeData(myFile,toString);
    }

    private void writeData(File myFile, String result) {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(myFile);
            try {
                fileOutputStream.write(result.getBytes());
                Toast.makeText(this, "Done"+myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
