package com.example.ma_ecommerce.viewHolder;

import android.view.View;
import android.widget.TextView;
import com.example.ma_ecommerce.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ma_ecommerce.viewHolder.ItemClickListener;
public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
       // txtProductName=itemView.findViewById(R.id.product_name);
        //txtProductPrice=itemView.findViewById(R.id.product_price);
        //txtProductQuantity=itemView.findViewById(R.id.product_quantity);

    }

    @Override
    public void onClick(View v) {
itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
