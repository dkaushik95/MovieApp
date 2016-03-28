package com.example.dishantkaushik.movieapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by dishantkaushik on 23/03/16.
 */
class favAdapter extends RecyclerView.Adapter<favAdapter.myViewHolder>{
    Context context;
    Cursor cursor;
    LayoutInflater layoutInflater;
    public favAdapter(Context context,Cursor cursor){
        this.context=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cursor=cursor;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(layoutInflater.inflate(R.layout.movieposters,null));
    }
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500//"+cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.POSTER_URL))).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.movie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Movie_detail.class);
                    cursor.moveToPosition(getAdapterPosition());
                    Log.e("Image Position", "" + getAdapterPosition());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("movie_id",cursor.getInt(cursor.getColumnIndex(MovieDBContract.ListColumns._ID)));
                    context.startActivity(intent);
                }
            });

        }
    }
}
