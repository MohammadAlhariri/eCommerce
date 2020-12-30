package com.example.ma_ecommerce.viewHolder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.ma_ecommerce.MainActivity;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.buyer.ProductDetailsActivity;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.model.Users;
import com.example.ma_ecommerce.seller.SellerHomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {


    ArrayList<Products> products;
    FragmentActivity activity;
   View selectBank;
    private ProgressDialog progressDialog;
   public CartItemAdapter(ArrayList<Products> products, FragmentActivity activity) {
      this.products = products;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

   }


    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View orderListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent,false);
        CartItemViewHolder orderListViewHolder = new CartItemAdapter.CartItemViewHolder(orderListLayout);
        return orderListViewHolder;
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder cartViewHolder, int position) {
        //holder.bankName.setText(bankListModels.get(position).getBankName());

        cartViewHolder.txtProductQuantity.setText(products.get(position).getQuantity()+"");
        cartViewHolder.txtProductName.setText(products.get(position).getName());
        cartViewHolder.txtProductPrice.setText(products.get(position).getPrice()+"");
        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence sequence[]=new CharSequence[]{
                        "Edit",
                        "Remove"
                };
                int pid = products.get(position).getPid();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("this product will be removed ?");
                builder.setItems(sequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            deleteProductfromCart(pid);
                        } else {
                            Intent intent = new Intent(activity, ProductDetailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            // Prevalid.online = users;
                            activity.startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void deleteProductfromCart(int pid) {
        progressDialog.setTitle("Adding New Product  ");
        progressDialog.setMessage("Please Wait .....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final RequestQueue queue = Volley.newRequestQueue(activity);

        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/deleteproductFromCart.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("dp", response);
                Toast.makeText(activity, "Product deleted from cart successfully ", Toast.LENGTH_SHORT).show();


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
    }
//
//
//    @Override
//    public int getItemCount() {
//        return products.size();
//    }
//
//
    public class CartItemViewHolder extends RecyclerView.ViewHolder implements ItemClickListener {
        public TextView txtProductName,txtProductPrice,txtProductQuantity;
        private ItemClickListener listener;

        public CartItemViewHolder(View itemView) {
            super(itemView);
            txtProductName=itemView.findViewById(R.id.product_name);
            txtProductPrice=itemView.findViewById(R.id.product_price);
            txtProductQuantity=itemView.findViewById(R.id.product_quantity);


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
