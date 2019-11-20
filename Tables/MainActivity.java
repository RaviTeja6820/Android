package com.example.advancedandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void tableSelect(View view){
        @SuppressLint("WrongViewCast") TextView number = (TextView) findViewById(R.id.number);

        ListView listView = findViewById(R.id.tableList);

        final ArrayList<String> tables = new ArrayList<String>();

        for (int i = 1; i <= 10; i++) {
            int value = i * Integer.parseInt(number.getText().toString());
            tables.add(Integer.toString(i) + " * " + number.getText().toString() + " = " + Integer.toString(value));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tables);

        listView.setAdapter(arrayAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "hello " + myFriends.get(position), Toast.LENGTH_LONG).show();
//            }
//        });

    }
}
