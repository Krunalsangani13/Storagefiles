package com.example.storagefiles.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.storagefiles.Database.FavoriteDB;
import com.example.storagefiles.Model.FavoriteModel;
import com.example.storagefiles.R;
import java.util.ArrayList;

public class FavoriteFileAdapter extends RecyclerView.Adapter<FavoriteFileAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<FavoriteModel> favarray;
    String file_name,file_path;
    int id;


    public FavoriteFileAdapter(Context context, ArrayList<FavoriteModel> favarray) {
        this.context = context;
        this.favarray = favarray;
    }

    @NonNull
    @Override
    public FavoriteFileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.favoritecardlist,parent,false);
        return new ViewHolder(listitem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFileAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.favorite_name.setText(favarray.get(position).getFavorite_file());
//        notifyDataSetChanged();

        int ids=favarray.get(position).getId();
        FavoriteDB db=new FavoriteDB(context);
        String query="select * from favorite_file";
        SQLiteDatabase database=db.getReadableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                id=cursor.getInt(0);
                file_name= cursor.getString(1);
                file_path= cursor.getString(2);
                            if (id==ids){
                                Log.d("hhjhkjk", "onClick: ........................."+id);
                                Log.d("hhjhkjk", "onClick: .........................");
                                holder.filter_ch.setChecked(true);
                            }
            }while (cursor.moveToNext());
        }

        holder.filter_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.filter_ch.isChecked()){
                }
                else {
                    String  id = String.valueOf(favarray.get(position).getId());
                    FavoriteDB db = new FavoriteDB(context);
                    Log.d("filter", "filter path++++++++++++" + favarray.get(position).getId());
                    favarray.remove(favarray.get(position));
                    db.DeleteFavs(id);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favarray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView favorite_name;
        public CheckBox filter_ch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.favorite_name=itemView.findViewById(R.id.txt_files_favorite);
            this.filter_ch=itemView.findViewById(R.id.chfiter);

        }
    }
}
