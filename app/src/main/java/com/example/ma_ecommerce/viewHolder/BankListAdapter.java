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
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.seller.SellerAddNewProduct;
import com.example.ma_ecommerce.seller.SellerHomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListViewHolder> {

    ArrayList<Products> products;
    FragmentActivity activity;
    View selectBank;
    private ProgressDialog progressDialog;
    public BankListAdapter(ArrayList<Products> products, FragmentActivity activity) {
        this.products = products;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

    }


    @Override
    public BankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bankListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent,false);
        BankListViewHolder bankListViewHolder = new BankListViewHolder(bankListLayout);
        return bankListViewHolder;
    }

    @Override
    public void onBindViewHolder(BankListViewHolder holder, int position) {
        //holder.bankName.setText(bankListModels.get(position).getBankName());

        holder.sellerproductname.setText(products.get(position).getName());
        holder.sellerproductdescreption.setText(products.get(position).getDescription());
        holder.sellerproductprice.setText("Price = " + products.get(position).getPrice() + "$");
        holder.sellerproductstate.setText(products.get(position).getProductState());
        Picasso.get().load(products.get(position).getImage()).into(holder.sellerproductimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence options[] = new CharSequence[]{
                        "yes",
                        "No"
                };
                int pid = products.get(position).getPid();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("this product will be removed ?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            deleteProduct(pid);
                        } else {
                            //activity.finish();
                        }
                    }
                });
                builder.show();
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

                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map2 = new HashMap<>();

                map2.put("pid", pid+"");
;
                return map2;
            }
        };

        queue.add(request);
        activity.recreate();
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    public class BankListViewHolder extends RecyclerView.ViewHolder implements ItemClickListener {
        TextView bankName;
        public TextView sellerproductname, sellerproductdescreption, sellerproductprice, sellerproductstate;
        public ImageView sellerproductimage;
        private ItemClickListener listener;

        public BankListViewHolder(View itemView) {
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
        public void onClick(View view, int position, boolean isLongClick) {
            {
                listener.onClick(view, getAdapterPosition(), false);
            }
        }
    }
}