package com.example.storagefiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.storagefiles.Adapter.FavoriteFileAdapter;
import com.example.storagefiles.Database.FavoriteDB;
import com.example.storagefiles.Model.FavoriteModel;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<FavoriteModel> favarray=new ArrayList<>();
    int id;
    ArrayList<FavoriteModel> arrayList=new ArrayList<>();
    String file_name,file_path;
    RecyclerView favorite_files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favorite_files=findViewById(R.id.rc_favorite);

        FavoriteDB db=new FavoriteDB(this);
        String query="select * from favorite_file";
        SQLiteDatabase data=db.getReadableDatabase();
        Cursor cursor=data.rawQuery(query,null);
        Log.d("databse_data","files cursor->"+cursor);
        if (cursor.moveToFirst())
        {
            do{
                id=cursor.getInt(0);
                file_name=cursor.getString(1);
                file_path=cursor.getString(2);
                favarray.add(new FavoriteModel(id,file_name,file_path));
                Log.d("favorite","file_name ----->"+favarray);
            }
            while(cursor.moveToNext());
        }
        FavoriteFileAdapter f=new FavoriteFileAdapter(this,favarray);
        favorite_files.scrollToPosition(favarray.size() - 1);
        favorite_files.setLayoutManager(new LinearLayoutManager(this));
        favorite_files.setAdapter(f);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.popup_menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getTitle()!=null && item.getTitle().equals("all")){
            allfile();
        }else if(item.getTitle()!=null && item.getTitle().equals("pdf")){
            pdffilter();
        }else if (item.getTitle()!=null && item.getTitle().equals("xlsx")){
            xmlfilter();
        }else if (item.getTitle()!=null && item.getTitle().equals("doc") && item.getTitle().equals("docx")){
            docfilter();
        }else if(item.getTitle()!=null && item.getTitle().equals("txt")){
            textfilter();
        }else {
            Toast.makeText(this, "nothing else it's", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void allfile() {
        favarray.clear();
        FavoriteDB db = new FavoriteDB(this);
        String query = "select * from favorite_file";
//        String query = "select * from favorite_file where favorite_file LIKE '%.pdf' AND '%.xlsx' AND '%.docx' AND '%.txt'";
        SQLiteDatabase data = db.getReadableDatabase();
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                file_name = cursor.getString(1);
                file_path = cursor.getString(2);
                favarray.add(new FavoriteModel(id, file_name, file_path));
            }
            while (cursor.moveToNext());
        }
        FavoriteFileAdapter file=new FavoriteFileAdapter(this,favarray);
        favorite_files.scrollToPosition(favarray.size() - 1);
        favorite_files.setLayoutManager(new LinearLayoutManager(this));
        favorite_files.setAdapter(file);
    }

    private void pdffilter() {
        favarray.clear();
                FavoriteDB db = new FavoriteDB(this);
                String query = "select * from favorite_file where favorite_file LIKE '%.pdf'";
                SQLiteDatabase data = db.getReadableDatabase();
                Cursor cursor = data.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        id = cursor.getInt(0);
                        file_name = cursor.getString(1);
                        file_path = cursor.getString(2);
                        favarray.add(new FavoriteModel(id, file_name, file_path));
                    }
                    while (cursor.moveToNext());
                }
                 FavoriteFileAdapter file=new FavoriteFileAdapter(this,favarray);
                favorite_files.scrollToPosition(favarray.size() - 1);
                favorite_files.setLayoutManager(new LinearLayoutManager(this));
                favorite_files.setAdapter(file);
    }

    private void xmlfilter() {
        favarray.clear();
        FavoriteDB db = new FavoriteDB(this);
        String query = "select * from favorite_file where favorite_file LIKE '%.xlsx'";
        SQLiteDatabase data = db.getReadableDatabase();
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                file_name = cursor.getString(1);
                file_path = cursor.getString(2);
                favarray.add(new FavoriteModel(id, file_name, file_path));
            }
            while (cursor.moveToNext());
        }
        FavoriteFileAdapter file=new FavoriteFileAdapter(this,favarray);
        favorite_files.scrollToPosition(favarray.size() - 1);
        favorite_files.setLayoutManager(new LinearLayoutManager(this));
        favorite_files.setAdapter(file);
    }

    private void docfilter() {
        favarray.clear();
        FavoriteDB db = new FavoriteDB(this);
        String query = "select * from favorite_file where favorite_file LIKE '%.docx'";
        SQLiteDatabase data = db.getReadableDatabase();
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                file_name = cursor.getString(1);
                file_path = cursor.getString(2);
                favarray.add(new FavoriteModel(id, file_name, file_path));
            }
            while (cursor.moveToNext());
        }
        FavoriteFileAdapter file=new FavoriteFileAdapter(this,favarray);
        favorite_files.scrollToPosition(favarray.size() - 1);
        favorite_files.setLayoutManager(new LinearLayoutManager(this));
        favorite_files.setAdapter(file);
    }

    private void textfilter() {
        favarray.clear();
        FavoriteDB db = new FavoriteDB(this);
        String query = "select * from favorite_file where favorite_file LIKE '%.txt'";
        SQLiteDatabase data = db.getReadableDatabase();
        Cursor cursor = data.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                file_name = cursor.getString(1);
                file_path = cursor.getString(2);
                favarray.add(new FavoriteModel(id, file_name, file_path));
            }
            while (cursor.moveToNext());
        }
        FavoriteFileAdapter file=new FavoriteFileAdapter(this,favarray);
        favorite_files.scrollToPosition(favarray.size() - 1);
        favorite_files.setLayoutManager(new LinearLayoutManager(this));
        favorite_files.setAdapter(file);
    }
}