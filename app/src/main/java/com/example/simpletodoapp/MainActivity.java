package com.example.simpletodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button tvButton;
    TextView tvEditName;
    RecyclerView rvItems;
     ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvButton = findViewById(R.id.tvButton);
        tvEditName = findViewById(R.id.tvEditName);
        rvItems = findViewById(R.id.rvItems);
        //tvEditName.setText("Turned out a lil bit problematic");

        loadItems();



        ItemsAdapter.OnLongClickedListener onLongClickedListener = new ItemsAdapter.OnLongClickedListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
               items.remove(position);

                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                 savedItems();
            }
        };
         itemsAdapter = new ItemsAdapter(items, onLongClickedListener);
        //final ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickedListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));



        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toDoItem = tvEditName.getText().toString();
                //Add the item to the model
                items.add(toDoItem);
                //Notify the adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                tvEditName.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                 savedItems();


            }
        });
    }

private File getDataFile() {
        return new File(getFilesDir(),"data.txt");
 }


//This function will load items by reading every line of the data file
    private void  loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error reading Message", e);
            items = new ArrayList<>();
        }
    }

// this function saves the items by writing them into the data file
 private void savedItems(){
     try {
         FileUtils.writeLines(getDataFile(),items);
     } catch (IOException e) {
         e.printStackTrace();
         Log.e("MainActivity", "Error writing message", e);
         items = new ArrayList<>();
     }
 }

}


