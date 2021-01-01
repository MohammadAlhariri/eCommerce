package com.example.ma_ecommerce.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.MainActivity;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.viewHolder.ProductListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String type = "Users";
    private ArrayList<Products> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            type = getIntent().getStringExtra("Admins");
        }

        Paper.init(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Admins")) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.profile_image);
        if (Prevalid.online != null) {
            if (!TextUtils.isEmpty(Prevalid.online.getName())) {
                userNameTextView.setText(Prevalid.online.getName());
                if (!Prevalid.online.getImage().equals("") || !TextUtils.isEmpty(Prevalid.online.getImage()))
                    Picasso.get().load(Prevalid.online.getImage()).placeholder(R.drawable.profile).into(profileImageView);
                Log.e("user", Prevalid.online.toString());
            }
        }


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        fillListWithProducts();
        products = new ArrayList<Products>();
    }

    private void fillListWithProducts() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getAllValidateProducts.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject row = response.getJSONObject(i);
                        int id = row.getInt("productID");
                        String name = row.getString("productName");
                        int sellerID = row.getInt("sellerID");
                        double price = row.getDouble("productPrice");
                        String productCategory = row.getString("productCategory");
                        String productImage = row.getString("productImage");
                        String productState = row.getString("productState");
                        String productDate = row.getString("productDate");
                        String productDescription = row.getString("productDescription");

                        products.add(new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState));
                    } catch (Exception ex) {
                        Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
                ProductListAdapter adapter = new ProductListAdapter(products, HomeActivity.this, type);

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dp", error.toString());

            }
        });

        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            if (!type.equals("Admins")) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_search) {
            if (!type.equals("Admins")) {
                Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_category) {
            Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("uid", Prevalid.online.getID() + "");
            startActivity(intent);

        } else if (id == R.id.nav_setting) {

            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}