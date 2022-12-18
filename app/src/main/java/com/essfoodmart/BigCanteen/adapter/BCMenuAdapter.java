package com.essfoodmart.BigCanteen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.essfoodmart.BigCanteen.model.BCMenuModel;
import com.essfoodmart.R;

import java.util.ArrayList;

public class BCMenuAdapter extends RecyclerView.Adapter<BCMenuAdapter.Viewholder>{

    ArrayList<BCMenuModel> menuItems;
    final Context context;

    private onClickItemListener onClickItemListener;

    public BCMenuAdapter(Context context, ArrayList<BCMenuModel> menuItems, onClickItemListener onClickItemListener)
    {
        this.context = context;
        this.menuItems = menuItems;
        this.onClickItemListener = onClickItemListener;
    }


    @NonNull
    @Override
    public BCMenuAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_canteen_card_menu, parent, false);
        return new Viewholder(view,this.onClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BCMenuAdapter.Viewholder holder, int position) {
        BCMenuModel model = menuItems.get(position);
        holder.txtFoodName.setText(model.getFoodName());
        holder.txtFoodPrice.setText(model.getFoodPrice());
        holder.imgFood.setImageResource(model.getFoodImage());

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtFoodName;
        TextView txtFoodPrice;
        CardView cardItem;
        ImageView imgFood;

        onClickItemListener onClickItemListener;

        public Viewholder(@NonNull View itemView, onClickItemListener onClickItemListener) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            cardItem = itemView.findViewById(R.id.cardItem);
            imgFood = itemView.findViewById(R.id.imgFood);

            itemView.setOnClickListener(this);
            this.onClickItemListener = onClickItemListener;
        }

        @Override
        public void onClick(View view)
        {
            this.onClickItemListener.onClickItem(getAdapterPosition());
        }
    }

    public interface onClickItemListener
    {
        void onClickItem(int position);
    }
    
}
