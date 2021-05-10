package com.hllbr.coinbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> resimArray;
    ArrayAdapter arrayAdapter;
    CoinAdapter coinAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.main_activity_listView);
        nameArray = new ArrayList<>();
        resimArray= new ArrayList<>();

        coinAdapter1=new CoinAdapter(nameArray,resimArray,this);
        listView.setAdapter(coinAdapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra()//veri aktarımı...
            }
        });
        getData();
        /*
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,nameArray);
        listView.setAdapter(arrayAdapter);*/

    }
    public void getData(){
        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Coins",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM coins",null);
            int nameIx = cursor.getColumnIndex("coinname");
            int imageIx = cursor.getColumnIndex("image");
            while(cursor.moveToNext()){
                nameArray.add(cursor.getString(nameIx));
                resimArray.add(cursor.getInt(imageIx));
        }
            arrayAdapter.notifyDataSetChanged();
            cursor.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_coin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_coin_item){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}