package com.example.ma_ecommerce.viewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ma_ecommerce.R;


public class SellerItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView sellerproductname, sellerproductdescreption, sellerproductprice, sellerproductstate;
    public ImageView sellerproductimage;
    private ItemClickListener listener;

    public SellerItemHolder(@NonNull View itemView) {
        super(itemView);
        sellerproductimage = (ImageView) itemView.findViewById(R.id.seller_productimage);
        sellerproductname = (TextView) itemView.findViewById(R.id.seller_product_name);
        sellerproductdescreption = (TextView) itemView.findViewById(R.id.seller_productDes);
        sellerproductstate = (TextView) itemView.findViewById(R.id.seller_productState);
        sellerproductprice = (TextView) itemView.findViewById(R.id.seller_productPrice);

    }

    public void itemClickLisner(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}

