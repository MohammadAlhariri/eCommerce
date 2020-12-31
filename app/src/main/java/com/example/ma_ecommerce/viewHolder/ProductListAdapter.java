package com.example.ma_ecommerce.viewHolder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.admin.AdminMaintainProduct;
import com.example.ma_ecommerce.buyer.ProductDetailsActivity;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.seller.SellerCategory;
import com.example.ma_ecommerce.seller.SellerHomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    ArrayList<Products> products;
    FragmentActivity activity;
    View selectBank;
    String parent;
    private ProgressDialog progressDialog;


    public ProductListAdapter(ArrayList<Products> products, FragmentActivity activity,String parent) {
        this.products = products;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        this.parent=parent;
        Paper.init(activity);

    }


    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bankListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent,false);
        ProductListViewHolder bankListViewHolder = new ProductListViewHolder(bankListLayout);
        return bankListViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        //holder.bankName.setText(bankListModels.get(position).getBankName());
        String pname=products.get(position).getName();
        holder.sellerproductname.setText(pname);
        String pdecs=products.get(position).getDescription();
        holder.sellerproductdescreption.setText(pdecs);
        String price=products.get(position).getPrice()+"";
        holder.sellerproductprice.setText("Price = " + price + "$");
        String pimage=products.get(position).getImage();
        Picasso.get().load(pimage).into(holder.sellerproductimage);
        int pid = products.get(position).getPid();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parent.equals("Users")) {
                    Intent intent = new Intent(activity, ProductDetailsActivity.class);
                    intent.putExtra("pid", pid + "");
                    intent.putExtra("name", pname);
                    intent.putExtra("desc", pdecs);
                    intent.putExtra("pimage", pimage);
                    intent.putExtra("price", price);
                    activity.startActivity(intent);
                }
                else if (parent.equals("Admins")){
                    Intent intent = new Intent(activity, AdminMaintainProduct.class);
                    intent.putExtra("pid", pid + "");
                    intent.putExtra("name", pname);
                    intent.putExtra("desc", pdecs);
                    intent.putExtra("pimage", pimage);
                    intent.putExtra("price", price);
                    activity.startActivity(intent);
                }
            }
        });
    }

    private void deleteProduct(int pid) {
        progressDialog.setTitle("Adding New Product  ");
        progressDialog.setMessage("Please Wait .....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final RequestQueue queue = Volley.newRequestQueue(activity);

        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/deleteProduct.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("dp", response);
                Toast.makeText(activity, "Product deleted successfully ", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("dp", error.toString());
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map2 = new HashMap<>();

                map2.put("pid", pid + "");

                return map2;
            }
        };

        queue.add(request);
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductListViewHolder extends RecyclerView.ViewHolder implements ItemClickListener {
        TextView bankName;
        public TextView sellerproductname, sellerproductdescreption, sellerproductprice;
        public ImageView sellerproductimage;
        private ItemClickListener listener;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            sellerproductimage = (ImageView) itemView.findViewById(R.id.productimage);
            sellerproductname = (TextView) itemView.findViewById(R.id.productname);
            sellerproductdescreption = (TextView) itemView.findViewById(R.id.productDes);
            // sellerproductstate = (TextView) itemView.findViewById(R.id.productState);
            sellerproductprice = (TextView) itemView.findViewById(R.id.productPrice);


        }

        public void itemClickLisner(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view, int position, boolean isLongClick) {
            {
                listener.onClick(view, getAdapterPosition(), false);
            }
        }
    }
}