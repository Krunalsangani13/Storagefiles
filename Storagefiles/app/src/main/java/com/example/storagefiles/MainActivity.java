package com.example.storagefiles;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.example.storagefiles.Adapter.FileListAdapter;
import com.example.storagefiles.Database.FavoriteDB;
import com.example.storagefiles.Model.FavoriteModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnpdfSelectListener{
    private FileListAdapter fileListAdapter;
    private RecyclerView recyclerView=null;
    private List<File> pdffile;
    ArrayList<File> arrayList=new ArrayList<>();
    String file_name,file_path;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runtimePermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.popup_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("favorite")){
            favorite();
        }else if(item.getTitle().equals("all")){
            allfile();
        }else if(item.getTitle().equals("pdf")){
            pdffilter();
        }else if (item.getTitle().equals("xlsx")){
            xmlfilter();
        }else if (item.getTitle().equals("doc")||item.getTitle().equals("docx")){
            docfilter();
        }else if(item.getTitle().equals("txt")){
            textfilter();
        }else {
//            Toast.makeText(MainActivity.this, "nothing else it's", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }




    private void runtimePermission(){
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displaypdf();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findpdf(File file){
        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=file.listFiles();

        for (File singleFile: files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findpdf(singleFile));

            }
            else {
                if (singleFile.getName().endsWith(".pdf")||singleFile.getName().endsWith(".xlsx")||singleFile.getName().endsWith(".doc")||
                        singleFile.getName().endsWith(".docx")|| singleFile.getName().endsWith(".txt"))
                {
                    arrayList.add(singleFile);
                    Log.d("loop log", "findpdf:00000 "+arrayList);
                }
            }
        }
        Log.d("list1", "findpdfs: "+ arrayList);
        return arrayList;

    }

    public void displaypdf(){
        recyclerView=findViewById(R.id.storage_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        pdffile=new ArrayList<>();
        pdffile.addAll(findpdf(Environment.getExternalStorageDirectory()));
        Log.d("list", "findpdfs: "+pdffile.toString());
        fileListAdapter=new FileListAdapter(this,pdffile,this);
        Log.d("list", "findpdfs: 1111"+fileListAdapter);
        recyclerView.setAdapter(fileListAdapter);


        FavoriteDB db=new FavoriteDB(this);
        String query="select * from favorite_file";
        SQLiteDatabase data=db.getReadableDatabase();
        Cursor cursor=data.rawQuery(query,null);
        Log.d("databse_data","files cursor->"+cursor);
        Bundle bundle=new Bundle();
        if (cursor.moveToFirst())
        {
            do{
                id=cursor.getInt(0);
                file_name=cursor.getString(1);
                file_path=cursor.getString(1);
                Log.d("mainactivity","file_name ----->"+file_name);
            }
            while(cursor.moveToNext());
        }
        Log.d("databse_data","files ->"+bundle);
    }

    private void favorite() {
        Intent i=new Intent(this,FavoriteActivity.class);
        startActivity(i);
        Toast.makeText(this, "favorite page", Toast.LENGTH_SHORT).show();
    }

    private void allfile() {
        arrayList.clear();
        for(File end:pdffile){
            if (end.isDirectory() && !end.isHidden()){
                arrayList.addAll(findpdf(end));
            }else{
                if(end.getName().endsWith(".pdf")||end.getName().endsWith(".xlsx")||end.getName().endsWith(".docx")||end.getName().endsWith(".doc")||end.getName().endsWith(".txt")){
                    Log.d("finallsit", "pdffilter: "+end);
                    arrayList.add(end);
                    Log.d("mylist", "filelist *********************: "+arrayList);
                }
            }
        }
        fileListAdapter=new FileListAdapter(this,arrayList,this);
        recyclerView.setAdapter(fileListAdapter);
    }

    public void pdffilter() {
        arrayList.clear();
        for(File end:pdffile){
            if (end.isDirectory() && !end.isHidden()){
                arrayList.addAll(findpdf(end));
            }else{
                if(end.getName().endsWith(".pdf")){
                    Log.d("finallsit", "pdffilter: "+end);
                    arrayList.add(end);
                    Log.d("mylist", "filelist *********************: "+arrayList);
                }
            }
        }
        fileListAdapter=new FileListAdapter(this,arrayList,this);
        recyclerView.setAdapter(fileListAdapter);
        
    }


    public void xmlfilter(){
        arrayList.clear();
        for(File end:pdffile){
//            ArrayList<File> arrayList=new ArrayList<>();
            if(end.getName().endsWith(".xlsx")){
                arrayList.add(end);
                Log.d("mylist", "count array file: "+arrayList);
            }
        }
        fileListAdapter=new FileListAdapter(this,arrayList,this);
        fileListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(fileListAdapter);
        Toast.makeText(this, "xml filter ", Toast.LENGTH_SHORT).show();
    }

    public void docfilter(){
        arrayList.clear();
        for(File end:pdffile){
            if(end.getName().endsWith(".doc")||end.getName().endsWith(".docx")){
                arrayList.add(end);
                Log.d("mylist", "count array file: "+arrayList);
            }
        }
        fileListAdapter=new FileListAdapter(this,arrayList,this);
        recyclerView.setAdapter(fileListAdapter);
        Toast.makeText(this, "doc type filer", Toast.LENGTH_SHORT).show();
    }

    public void textfilter(){
        arrayList.clear();
        for(File end:pdffile){
            if(end.getName().endsWith(".txt")){
                arrayList.add(end);
                Log.d("mylist", "countrray file: "+arrayList);
            }
        }
        fileListAdapter=new FileListAdapter(this,arrayList,this);
        Log.d("mylist", "countrray file: "+fileListAdapter);
        recyclerView.setAdapter(fileListAdapter);
        Toast.makeText(this, "text type filer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPdfSelected(File file) {
        Log.d(TAG, "onPdfSelected: "+file);
        startActivity(new Intent(MainActivity.this,FileOpenActivity.class).putExtra("path",file.getAbsolutePath()));

    }
}