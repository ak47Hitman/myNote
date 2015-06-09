package com.mygymaster.mynote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * Created by logvinov on 30.04.2015.
 */
public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        GridView dayOfweekGridView = (GridView)findViewById(R.id.dayOfWeekGridView);
        GridView numberOfWeekGridView = (GridView)findViewById(R.id.numberOfWeekGridView);

        final String[] days = {"  ", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                R.layout.day_calendar_item, days);
        dayOfweekGridView.setAdapter(mAdapter);
        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<>(this,
                R.layout.day_calendar_item, days);
        numberOfWeekGridView.setAdapter(mAdapter2);
    }
    
    public void onNoteButtonClick(View view) {
        finish();
    }
}