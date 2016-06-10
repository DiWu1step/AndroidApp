package com.juan.deardiary;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDetailActivity extends Activity {

    public static final String EXTRA_NOTENO = "noteNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        int noteNo = (Integer)getIntent().getExtras().get(EXTRA_NOTENO);
        try{
            DiaryDatabaseHelper databaseHelper = new DiaryDatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("NOTES",
                    new String[]{"NAME","DESCRIPTION"},
                    "_id=?",
                    new String[]{Integer.toString(noteNo)},
                    null,
                    null,
                    null);
            if(cursor.moveToFirst()){
                Log.d("NoteDetailActivity", "get first cursor");
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);




                //Populate the drink name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                //Populate the drink description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

            }


        }catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
