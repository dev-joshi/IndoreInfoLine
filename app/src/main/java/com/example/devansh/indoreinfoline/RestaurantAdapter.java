package com.example.devansh.indoreinfoline;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devansh on 15-08-2017.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>{

    private List<Restaurant> restaurantList=new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,addr;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.res_name);
            addr=(TextView)itemView.findViewById(R.id.res_addr);
            imageView=(ImageView)itemView.findViewById(R.id.res_image);
        }
    }

    public RestaurantAdapter() {
    }
     public void setRestaurantList(List<Restaurant> list)
     {
         restaurantList=list;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Restaurant restaurant=restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.addr.setText(restaurant.getAddress());
        if(!restaurant.getImage().isEmpty())
        {
            Picasso.with(holder.itemView.getContext())
                    .load(restaurant.getImage())
                    .into(holder.imageView);
        }
        else if(!restaurant.getThumb().isEmpty()) {
            Picasso.with(holder.itemView.getContext())
                    .load(restaurant.getThumb())
                    .into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(restaurant.getLat()!=null&&restaurant.getLon()!=null)
                {
                    String geoUri="http://maps.google.com/maps?q=loc:" + restaurant.getLat() + "," + restaurant.getLon() + " (" + restaurant.getName() + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
