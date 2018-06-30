package com.example.vignesh.afinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.MyViewHolder> {

    private List<AddressBook> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public AddressBookAdapter(List<AddressBook> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddressBook movie = moviesList.get(position);
        holder.title.setText(movie.getName());
        holder.genre.setText(movie.getEmail());
        holder.year.setText(movie.getPhone());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
