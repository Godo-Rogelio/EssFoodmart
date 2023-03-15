package com.essfoodmart.MyCart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.R;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.Viewholder>
{
    List<OrderModel> orderItems;
    final Context context;

    public OrderSummaryAdapter(Context context, List<OrderModel> orderItems)
    {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderSummaryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_breakdown_items, parent, false);
        return new OrderSummaryAdapter.Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.Viewholder holder, int position) {
        OrderModel model = orderItems.get(position);
        holder.txtItemName.setText(model.getFoodName());
        holder.txtItemPrice.setText("₱" + model.getFoodPrice());
        holder.txtItemQty.setText("x" + String.valueOf(model.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {
        TextView txtItemName;
        TextView txtItemQty;
        TextView txtItemPrice;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemQty = itemView.findViewById(R.id.txtItemQty);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
        }

    }
}
