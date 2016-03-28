package com.example.dishantkaushik.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


class imageAdapter extends RecyclerView.Adapter<imageAdapter.myViewHolder>{
    Context context;
    LayoutInflater layoutInflater;
    public imageAdapter(Context context){
        this.context=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(layoutInflater.inflate(R.layout.movieposters,null));
    }
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500//"+movieData.allMovies.get(position).getPosterurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieData.allMovies.size();
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
                    intent.putExtra("movie_position", getAdapterPosition());
                    intent.putExtra("movie_id",movieData.allMovies.get(getAdapterPosition()).getMovieID().toString());
                    context.startActivity(intent);
                }
            });

        }
    }
}
