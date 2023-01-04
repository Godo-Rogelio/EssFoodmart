package com.essfoodmart.MyCart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.essfoodmart.Model.MenuModel;
import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.R;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.Viewholder>
{
    OrderModel[] orderItems;
    final Context context;

    private onClickItemListener onClickItemListener;

    public MyCartAdapter(Context context, OrderModel[] orderItems, onClickItemListener onClickItemListener)
    {
        this.context = context;
        this.orderItems = orderItems;
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MyCartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_items, parent, false);
        return new Viewholder(view,this.onClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.Viewholder holder, int position) {
        OrderModel model = orderItems[position];
        holder.txtFoodName.setText(model.getFoodName());
        holder.txtFoodPrice.setText(model.getFoodPrice());
        holder.txtQuantity.setText("x" + String.valueOf(model.getQuantity()));
        int resourceId = model.getFoodImage();
        holder.imgFood.setImageResource(resourceId);
        int count = 1;
        System.out.println("Count" + count);
        count++;
    }

    @Override
    public int getItemCount() {
        return orderItems.length;
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtFoodName;
        TextView txtFoodPrice;
        TextView txtQuantity;
        CardView cardItem;
        ImageView imgFood;

        onClickItemListener onClickItemListener;

        public Viewholder(@NonNull View itemView, onClickItemListener onClickItemListener) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
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

    public void updateData(OrderModel[] orderItems)
    {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }

    public interface onClickItemListener
    {
        void onClickItem(int position);
    }
}
