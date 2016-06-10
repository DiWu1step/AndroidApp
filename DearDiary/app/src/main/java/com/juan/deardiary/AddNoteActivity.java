package com.juan.deardiary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends Activity {

    String name;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

    }

    public void saveNote(View view){
        EditText nameView = (EditText)findViewById(R.id.name);
        EditText descriptionView = (EditText)findViewById(R.id.description);
        name = nameView.getText().toString();
        description = descriptionView.getText().toString();

        DiaryDatabaseHelper diaryDatabaseHelper = new DiaryDatabaseHelper(this);
        try{
            SQLiteDatabase db = diaryDatabaseHelper.getWritableDatabase();
            DiaryDatabaseHelper.insertNote(db, name, description);
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        Toast toast = Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT);
        toast.show();


    }
}
