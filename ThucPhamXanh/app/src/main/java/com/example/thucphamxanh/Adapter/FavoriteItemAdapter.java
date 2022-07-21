package com.example.thucphamxanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.FavoviteModel;
import com.example.thucphamxanh.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemAdapter extends RecyclerView.Adapter<FavoriteItemAdapter.FavoriteItemViewHolder> {

    private List<FavoviteModel> favoviteModelList;
    private List<String> list = new ArrayList<>();

    public FavoriteItemAdapter(List<FavoviteModel> favoviteModelList) {
        this.favoviteModelList = favoviteModelList;
    }

    @NonNull
    @Override
    public FavoriteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item_favorite_home,parent,false);
        return new FavoriteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteItemViewHolder holder, int position) {

        FavoviteModel model = favoviteModelList.get(position);
        holder.favoriteItem_tv.setText(model.getItemText());

        boolean isHidden = model.isHidden();
        holder.hiddenLayout.setVisibility(isHidden ? View.VISIBLE : View.GONE);
        if(isHidden){
            holder.arrowImage.setImageResource(R.drawable.ic_arrow_drop_up);
        }else {
            holder.arrowImage.setImageResource(R.drawable.ic_arrow_drop_down);

        }

        ChildFavoriteAdapter childFavoriteAdapter = new ChildFavoriteAdapter(list);
        holder.child_favorite_rcView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.child_favorite_rcView.setAdapter(childFavoriteAdapter);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setHidden(!model.isHidden());
                list = model.getFavoriteList();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }


    @Override
    public int getItemCount() {
        return favoviteModelList.size();
    }

    public class FavoriteItemViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;
        private RelativeLayout hiddenLayout;
        private TextView favoriteItem_tv;
        private ImageView arrowImage;
        private RecyclerView child_favorite_rcView;
        public FavoriteItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            hiddenLayout = itemView.findViewById(R.id.hidden_layout);
            favoriteItem_tv = itemView.findViewById(R.id.itemTv);
            arrowImage = itemView.findViewById(R.id.arrow_imageview);
            child_favorite_rcView = itemView.findViewById(R.id.child_favorite_rcView);

        }
    }
}
