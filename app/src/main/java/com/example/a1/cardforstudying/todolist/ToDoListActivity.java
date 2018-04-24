package com.example.a1.cardforstudying.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.a1.cardforstudying.R;

public class ToDoListActivity extends AppCompatActivity {

    RecyclerView toDoListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        toDoListRecyclerView = findViewById(R.id.to_do_list_recycler_view);
    }
}
