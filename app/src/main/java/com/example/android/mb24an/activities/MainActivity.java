package com.example.android.mb24an.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.mb24an.R;
import com.example.android.mb24an.adapters.TaskAdapter;
import com.example.android.mb24an.data.Contract;
import com.example.android.mb24an.data.DBHelper;
import com.example.android.mb24an.fragment.DatePickerFragment;
import com.example.android.mb24an.utilities.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.android.mb24an.data.DatabaseUtils.getDailyTask;
import static com.example.android.mb24an.data.DatabaseUtils.removeTask;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.OnDialogCloseListener  {

    private TextView currentDateTextView;
    private Button previousDateButton;
    private Button nextDateButton;
    private Button addTaskButton;
    private RecyclerView taskRecyclerView;
    private TextView tv_day_all;
    private TextView targetTime;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd, yyyy");
    private SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Date currentDate;
    private Calendar  calendar = Calendar.getInstance();
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private TaskAdapter adapter;
    private Context context;

    private FragmentManager fragmentManager;

    private final String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_day_all = (TextView) findViewById(R.id.tv_day_all);
        targetTime = (TextView) findViewById(R.id.target_time);
        currentDateTextView = (TextView) findViewById(R.id.tv_currentDate);
        previousDateButton = (Button) findViewById(R.id.btn_previousDate);
        nextDateButton = (Button) findViewById(R.id.btn_nextDate);
        addTaskButton = (Button) findViewById(R.id.btn_addTask);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        currentDate = new Date();
        currentDateTextView.setText(dateFormat.format(currentDate));

        //store date to currentDate when button clicked
        previousDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentDate = changeDate(currentDate, -1);
                refreshViewForNewDate(currentDate);
            }
        });

        nextDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentDate = changeDate(currentDate, 1);
                refreshViewForNewDate(currentDate);
            }
        });

        currentDateTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                new DatePickerFragment().show( fragmentManager, "datepicker");
            }
        });

        taskRecyclerView = (RecyclerView) findViewById(R.id.rv_tasks);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onPause()
    {
        Log.d(TAG, ": onPause");
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        Log.d(TAG, ": onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG, ": onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        updateShow();
        super.onResume();
    }

    public void updateShow(){
        if(db == null) db = helper.getWritableDatabase();
        setTargetTime(dbDateFormat.format(currentDate));

        cursor = getDailyTask(db, dbDateFormat.format(currentDate));

        int durationTimeMinutes = 0;
        if(cursor.moveToFirst())
        {
            do{
                durationTimeMinutes += cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));
            }while(cursor.moveToNext());
        }

        String durationTime = String.format("%d:%02d", durationTimeMinutes/60, durationTimeMinutes%60);
        tv_day_all.setText(durationTime);

        cursor = getDailyTask(db, dbDateFormat.format(currentDate));
        adapter.swapCursor(cursor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = getDailyTask(db, dbDateFormat.format(currentDate));


        adapter = new TaskAdapter(cursor, new TaskAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int pos, String subject, String project, String startTime, String endTime, String date, long id) {
                Log.d(TAG, "item click id: " + id);
                Log.e("xxx","pos = "+pos+",subject="+subject+",project="+project+",startTime="+startTime+",endTime="+endTime+",date="+date+",id="+id);
                Intent intent = new Intent(MainActivity.this, UpdateTaskActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_SUBJECT", subject);
                extras.putString("EXTRA_PROJECT", project);
                extras.putString("EXTRA_START_TIME", startTime);
                extras.putString("EXTRA_END_TIME", endTime);
                extras.putString("EXTRA_DATE", date);
                extras.putLong("EXTRA_ID", id);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        taskRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + id);
                if(removeTask(db, id)){
                    Toast.makeText(MainActivity.this, "Remove task success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Remove task failed", Toast.LENGTH_SHORT).show();
                }
                adapter.swapCursor(getDailyTask(db, dbDateFormat.format(currentDate)));
                updateShow();
            }
        }).attachToRecyclerView(taskRecyclerView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }

    private void refreshViewForNewDate(Date date) {
        currentDateTextView.setText(dateFormat.format(date));
        cursor = getDailyTask(db, dbDateFormat.format(date));
        adapter.swapCursor(cursor);

        setTargetTime(dbDateFormat.format(date));
    }

    private void setTargetTime(String date)
    {
        String[] monthDayYear = date.split("/");
        Calendar c = GregorianCalendar.getInstance();
        c.set(Integer.parseInt(monthDayYear[2]),
                Integer.parseInt(monthDayYear[0]) - 1,
                Integer.parseInt(monthDayYear[1]));

        String currentTargetTime = null;
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.MONDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.TUESDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.WEDNESDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.THURSDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.FRIDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.SATURDAY_TARGET_TIME);
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            currentTargetTime = PreferenceUtils.getDayTargetTime(this, PreferenceUtils.SUNDAY_TARGET_TIME);
        }

        targetTime.setText(currentTargetTime);
    }

    private Date changeDate(Date currentDate, int amount){
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    @Override
    public void closeDialog(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        currentDate = calendar.getTime();
        currentDateTextView.setText(dateFormat.format(currentDate));
        refreshViewForNewDate(currentDate);
    }

}
