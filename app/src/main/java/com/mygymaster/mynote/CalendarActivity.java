package com.mygymaster.mynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by logvinov on 30.04.2015.
 */
public class CalendarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendar);
    }
    
    public void onNoteButtonClick(View view) {
        finish();
    }
}
