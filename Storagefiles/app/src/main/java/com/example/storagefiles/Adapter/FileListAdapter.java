package com.example.storagefiles.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.storagefiles.Database.FavoriteDB;
import com.example.storagefiles.Model.FavoriteModel;
import com.example.storagefiles.OnpdfSelectListener;
import com.example.storagefiles.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private final Context context;
    private final List<File> pdffiles;

    private final OnpdfSelectListener listener;
    ArrayList<Integer> arrayCheck = new ArrayList<Integer>();

    public FileListAdapter(Context context, List<File> pdffiles, OnpdfSelectListener listener) {
        this.context = context;
        this.pdffiles = pdffiles;
        this.listener = listener;
    }
  String favorite= String.valueOf(new ArrayList<String>());
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitems=layoutInflater.inflate(R.layout.listfile,parent,false);
        return new ViewHolder(listitems);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.checkfavorit.setChecked(false);
        holder.filelist.setText(pdffiles.get(position).getName());
        String file_paths= String.valueOf(pdffiles.get(position));
        Log.d("getposition","p+++++"+pdffiles.get(position));

        FavoriteDB db=new FavoriteDB(context);
        String query="select * from favorite_file";
        SQLiteDatabase data=db.getReadableDatabase();
        Cursor cursor=data.rawQuery(query,null);
        Log.d("databse_data","files cursor->"+cursor);
        Bundle bundle=new Bundle();
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String file_name=cursor.getString(1);
                String file_path=cursor.getString(2);
                Log.d("adapter", "onBindViewHolder: ++++++++++++++++++++++++++++++++++++"+file_path);
                if (file_path.equals(file_paths)){
                    arrayCheck.add(position);
                    holder.checkfavorit.setChecked(true);
                }
                else{
//                    holder.checkfavorit.setChecked(false);
                }
            }
            while(cursor.moveToNext());
        }
        Log.d("databse_data","files ->"+bundle);


        holder.mycard.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listener.onPdfSelected(pdffiles.get(position));
              Log.d("adapter", "onBindViewHolder: "+listener);
          }
      });

        holder.checkfavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkfavorit.isChecked()){
                    String file_name=pdffiles.get(position).getName();
                    String positions= String.valueOf(pdffiles.get(position));
                    FavoriteDB db=new FavoriteDB(context);
                    db.favorite_files(file_name,positions);
                    holder.checkfavorit.setChecked(true);
//                    arrayCheck.add(position);
                    Log.d("Asfsafsdf","asfasfsa "+arrayCheck);
                    notifyDataSetChanged();
                }else{
                    Log.d("Asfsafsdf","asfasfsa unchecked-000->"+arrayCheck);
                    Log.d("dfasfds",""+position);

                    Log.d("jisksnfkns", "onClick: "+arrayCheck);
                    for(int i=0; i<arrayCheck.size(); i++){
                       int newposition;
                        if (arrayCheck.get(i)==position){
                            newposition=i;
                            arrayCheck.remove(newposition);
                            unchecked(pdffiles.get(position).getPath());
                        }
                    }
                    holder.checkfavorit.setChecked(false);

                }
            }

            private void unchecked(String name) {
                FavoriteDB db=new FavoriteDB(context);
                Log.d("sfnsnknf", "unchecked: "+name);
                db.DeleteFav(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdffiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView filelist;
        CardView mycard;
        CheckBox checkfavorit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.filelist=itemView.findViewById(R.id.txt_files);
            this.mycard=itemView.findViewById(R.id.card_file);
            this.checkfavorit=itemView.findViewById(R.id.cbHeart);

        }
    }
}
