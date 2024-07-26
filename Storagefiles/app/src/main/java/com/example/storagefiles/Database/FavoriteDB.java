package com.example.storagefiles.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class FavoriteDB extends SQLiteOpenHelper {
    public static final String name = "favorite";
    public static final String TABLE_NAME = "favorite_file";

    String listf= String.valueOf(new ArrayList<String>());

    public FavoriteDB(@Nullable Context context) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE '"+TABLE_NAME+"' (id INTEGER PRIMARY KEY AUTOINCREMENT,favorite_file TEXT,position_path TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void favorite_files(String file,String position){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("favorite_file",file);
        values.put("position_path",position);
        db.insert(TABLE_NAME,"",values);
//        long result=db.insert(TABLE_NAME," ",values);

//        if (result==-1)
//            return false;
//        else
//            return true;
    }

    public int get_check_list(String title){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor rs=db.rawQuery("select * from '"+TABLE_NAME+"' where favorite_file = '"+title+"'",null);
        rs.moveToFirst();
        int count=rs.getCount();
        Log.d("database log", "get_check_list: "+count);
        return count;
    }

    public void  DeleteFav(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"position_path=?",new String[]{title});
    }


    public void  DeleteFavs(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{id});
    }


}
