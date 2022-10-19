package com.example.meinebibliothek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BearbeitungUndLöschActivity extends AppCompatActivity {
    EditText title_input, author_input, pages_input;
    Button update_button, delete_button;
    String id, title,author,pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button= findViewById(R.id.delete_button);
        //first aufruf
        getAndSetIntentData();
        //Action bar title definieren
        ActionBar ab= getSupportActionBar();
        if( ab!= null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeinDatenBankManager myDB = new MeinDatenBankManager(BearbeitungUndLöschActivity.this);
                title= title_input.getText().toString().trim();
                author= author_input.getText().toString().trim();
                pages= pages_input.getText().toString().trim();
                //dann this
                myDB.updateData(Integer.valueOf(id),title,author,pages);
                finish();// nachdem Update wird direkt in die MainActivity zurückgegangen

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });




    }

    void getAndSetIntentData() {
        // GET Data from Intent
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("title")
                && getIntent().hasExtra("author") &&
                getIntent().hasExtra("pages")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");


            // SET Intent Data
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        } else {
            Toast.makeText(this, "keine Daten", Toast.LENGTH_SHORT).show();
        }



    }
     void confirmDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Loeschung von "+ title + " ?");
        builder.setMessage("Wollen sie wirklich " +title + " loeschen?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MeinDatenBankManager myDB= new MeinDatenBankManager(BearbeitungUndLöschActivity.this);

                myDB.LöschEinElement(Integer.valueOf(id));
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}