package com.example.android.mb24an.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.android.mb24an.R;
import com.example.android.mb24an.adapters.ProjectAdapter;
import com.example.android.mb24an.data.DBHelper;
import com.example.android.mb24an.data.DatabaseUtils;
import com.example.android.mb24an.utilities.ExcelUtils;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectsList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    private static final String TAG = "SubjectList Acivity";
    private ProjectAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private Context context;
    private String selectedSubjectName;
    private int selectedSubjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }





    @Override
    protected void onStart() {
        super.onStart();

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllSubjectsWithTaskCount(db);

        mAdapter = new ProjectAdapter( cursor ,  new ProjectAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int pos, int subjectId, String subjectName) {
                Log.d(TAG, " item click id: " + subjectId);

                saveExcelFile(subjectId,subjectName);

            }


        });

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }

    public void saveExcelFile(int subjectId,String subjectName){

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAllTasksOfSubject(db,subjectId);

        selectedSubjectName = subjectName;

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1, null, this).forceLoad();

    }

    @Override
    public androidx.loader.content.Loader<Void> onCreateLoader(int id, Bundle args) {

        return new androidx.loader.content.AsyncTaskLoader<Void>(this) {


            @Override
            public Void loadInBackground() {

                ExcelUtils.saveExcelFileForSubjectTasks(cursor , context, "myExcel.xls", selectedSubjectName);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        //Toast.makeText(context,"The project "+String.valueOf(selectedProjectName)+" data exported!", Toast.LENGTH_SHORT).show();

        String filename="myExcel.xls";

        File filelocation = new File(context.getExternalFilesDir(null), filename);

        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {""};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);

        emailIntent .putExtra(Intent.EXTRA_STREAM, path);

        emailIntent .putExtra(Intent.EXTRA_SUBJECT, selectedSubjectName+" Time Sheet");
        this.startActivity(Intent.createChooser(emailIntent , "Send TimeManagement email..."));

    }

    @Override
    public void onLoaderReset(androidx.loader.content.Loader<Void> loader) {

    }
}


