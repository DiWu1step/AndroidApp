package com.juan.deardiary;


import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends ListFragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    View view;




    public NotesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = inflater;
        view = inflater.inflate(android.R.layout.list_content, container, false);
        Log.d("NotesFragment:", "onCreateView works");
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            SQLiteOpenHelper diaryDatabaseHelper = new DiaryDatabaseHelper(getActivity());
            Log.d("NotesFragment:", "database helper created fine");
            db = diaryDatabaseHelper.getReadableDatabase();

            cursor = db.query("NOTES",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            if(cursor == null){
                Log.d("NotesFragment:", "cursor is null");
            }
            if(cursor.moveToFirst()){
                Log.d("NotesFragment","run first");
                Log.d("NoteFragment",Integer.toString(cursor.getInt(0)));
                Log.d("NoteFragment", cursor.getString(1));
            }else{
                Log.d("NotesFragment","no data");
            }

            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            Log.d("NoteFragments:", "cursor adapter works fine");
            setListAdapter(listAdapter);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }



    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.EXTRA_NOTENO, (int) id);
        startActivity(intent);
    }

}
