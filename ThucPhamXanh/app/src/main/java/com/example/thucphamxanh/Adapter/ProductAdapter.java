package com.example.thucphamxanh.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {
    private List<Product> list;
    private List<Cart> listCart;
    private Context context;

    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Product product = list.get(position);
            NumberFormat numberFormat = new DecimalFormat("#,##0");
            listCart = getAllCart();
            byte[] imgByte = Base64.getDecoder().decode(product.getImgProduct());
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
            holder.imgProduct.setImageBitmap(bitmap);
            holder.tvNameProduct.setText(String.valueOf(product.getNameProduct()));
            holder.tvPriceProduct.setText(numberFormat.format(product.getPriceProduct())+" đ");
            holder.cardProuct.setOnClickListener(view -> {
                if (holder.btnUpdateProduct.getVisibility()==View.VISIBLE || holder.btnDeleteProduct.getVisibility()==View.VISIBLE){
                    holder.btnUpdateProduct.setVisibility(View.GONE);
                    holder.btnDeleteProduct.setVisibility(View.GONE);
                }else {
                    holder.btnUpdateProduct.setVisibility(View.GONE);
                    holder.btnDeleteProduct.setVisibility(View.GONE);
                }
            });
            holder.btnUpdateProduct.setOnClickListener(view -> {

            });
            holder.btnDeleteProduct.setOnClickListener(view -> {

            });
            holder.btn_addCart.setOnClickListener(view -> {
                SharedPreferences preferences = context.getSharedPreferences("My_User",Context.MODE_PRIVATE);
                String user = preferences.getString("username","");
                Cart cart = new Cart();
                cart.setUserClient(user);
                cart.setIdProduct(product.getCodeProduct());
                cart.setImgProduct(product.getImgProduct());
                cart.setNameProduct(product.getNameProduct());
                cart.setPriceProduct(product.getPriceProduct());
                cart.setNumberProduct(1);
                cart.setIdPartner(product.getUserPartner());
                cart.setTotalPrice(cart.getPriceProduct()*cart.getNumberProduct());
                addProductCart(cart);
            });

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameProduct,tvPriceProduct;
        private ImageView imgProduct;
        private CardView cardProuct;
        private Button btnUpdateProduct,btnDeleteProduct;
        private Button btn_addCart;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct_item);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct_item);
            imgProduct = itemView.findViewById(R.id.imgProduct_item);
            cardProuct = itemView.findViewById(R.id.cardProduct);
            btnUpdateProduct = itemView.findViewById(R.id.btn_updateProduct_item);
            btnDeleteProduct = itemView.findViewById(R.id.btn_deleteProduct_item);
            btn_addCart = itemView.findViewById(R.id.btn_addCart_item);
        }
    }
    public  List<Product> getAllProduct(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    list1.add(product);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

    public void addProductCart(Cart cart){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        if (listCart.size()==0){
            cart.setIdCart(1);
            reference.child("1").setValue(cart);

        }else {
            int i = listCart.size()-1;
            int id = listCart.get(i).getIdCart()+1;
            cart.setIdCart(id);
            reference.child(""+id).setValue(cart);
        }
    }
    public  List<Cart> getAllCart(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        List<Cart> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    list1.add(cart);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

}
