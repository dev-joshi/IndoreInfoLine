package com.example.devansh.indoreinfoline;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Places extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Place,PlaceViewHolder> adapter;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("/places");
        adapter= new FirebaseRecyclerAdapter<Place, PlaceViewHolder>(
                Place.class,
                R.layout.place_card,
                PlaceViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(PlaceViewHolder viewHolder, final Place model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImage());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getLat()!=null&&model.getLng()!=null)
                        {
                            String geoUri="http://maps.google.com/maps?q=loc:" + model.getLat() + "," + model.getLng()+ " (" + model.getTitle() + ")" ;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                            v.getContext().startActivity(intent);
                        }
                    }
                });
            }
            @Override
            protected void onDataChanged()
            {
                super.onDataChanged();
                if(progressBar!=null)
                    progressBar.setVisibility(View.GONE);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView text_title,text_description;
        ImageView imageView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            text_title=(TextView)itemView.findViewById(R.id.title);
            text_description=(TextView)itemView.findViewById(R.id.description);
            imageView=(ImageView)itemView.findViewById(R.id.imageview);

    }

        public void setTitle(String title) {
            text_title.setText(title);
        }

        public void setImage(String image) {
            Picasso.with(itemView.getContext())
                    .load(image)
                    .into(imageView);
        }

        public void setDescription(String description) {
            text_description.setText(description);
        }
        }

    }
