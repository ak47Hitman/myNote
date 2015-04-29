package com.mygymaster.mynote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener {

    ListView noteListView;
    final SimpleDateFormat dateFormatForSearch = new SimpleDateFormat("dd.MM.yyyy");//формат даты
    Calendar today = new GregorianCalendar();//Календарь для отображения текущей даты
    NoteWorkDBHelper noteWorkDBHelper;//Наш класс для работы с базой данных
    // формируем столбцы сопоставления
    String[] from = new String[] { NoteWorkDBHelper.NOTE_ID_COLUMN, NoteWorkDBHelper.NOTE_NAME_COLUMN, NoteWorkDBHelper.NOTE_DATE_COLUMN };
    int[] to = new int[] { R.id.idNote, R.id.nameNote, R.id.dateNote };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Заполняем наш ListView и задаем значение
        noteWorkDBHelper = new NoteWorkDBHelper(this);
        updateAdapter();
        //Обработка долгого удержания
        noteListView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onMyButtonClick(View view)
    {
        //Извлекаем текст из нашего текстового поля и передаем его в БД
        EditText noteEditText = (EditText)findViewById(R.id.noteEditText);
        Note note = new Note(noteEditText.getText().toString(), dateFormatForSearch.format(today.getTime()));
        //Передаем значение в БД
        noteWorkDBHelper = new NoteWorkDBHelper(this);
        noteWorkDBHelper.addNote(note);
        //Обновляем наш список заметок
        updateAdapter();
        //Обнуляем текстовое поле
        noteEditText.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Задаем наше текстовое поле с именем заметки
        TextView noteNameTextView = (TextView)findViewById(R.id.nameNote);
        //Конструктор заметки
        Note note = new Note((int)id, "", dateFormatForSearch.format(today.getTime()));
        //Оповещаем об удалении
        Log.d("Name: ", "Note delete: " + noteWorkDBHelper.getNote((int)id).getName());
        Toast.makeText(getApplicationContext(), noteWorkDBHelper.getNote((int)id).getName() + " удалён.", Toast.LENGTH_SHORT).show();
        //Удаялем из БД нашу заметку
        noteWorkDBHelper = new NoteWorkDBHelper(this);
        noteWorkDBHelper.deleteNote(note);
        //Полсе удаления обновляем наш ListView
        updateAdapter();

        return true;
    }

    //Метод обновления ListView
    private void updateAdapter() {
        //Задаем Адаптер - 1 - параметр контекст, 2 - итем в которй ложем значение из БД,
        //3 - откуда ложем (ячейка таблицы), 4 - что в какое именно поле ложем, 5 - 0
        SimpleCursorAdapter ad = new SimpleCursorAdapter(this, R.layout.item, noteWorkDBHelper.getCursor(), from, to, 0);
        noteListView = (ListView)findViewById(R.id.noteListView);
        noteListView.setAdapter(ad);
        noteWorkDBHelper.close();
        //Выводим все значения из таблицы
        showAllNotes();
    }

    private void showAllNotes() {
        Log.d("Reading: ", "Reading all notes..");
        List<Note> notes = noteWorkDBHelper.getAllNotes();

        for (Note note : notes) {
            String log = "Id: " + note.getID() + ", Name: " + note.getName() + ", Phone: " + note.getDate();
            Log.d("Name: ", log);
        }
    }
}