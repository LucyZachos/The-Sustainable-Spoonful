package com.example.sustainablespoonfulapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<DiscountedProduct> productList;
    private Context context;

    public ProductAdapter(List<DiscountedProduct> productList, Context context) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discounted_products, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        DiscountedProduct product = productList.get(position);
        holder.bindData(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDiscountPercentage;
        TextView productDiscountCode;

        public ProductViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.discounted_product_image);
            productName = itemView.findViewById(R.id.discounted_product_name);
            productDiscountPercentage = itemView.findViewById(R.id.discounted_product_discount_percentage);
            productDiscountCode = itemView.findViewById(R.id.discounted_product_discount_code);

        }
        public void bindData(DiscountedProduct product) {
            // Set the data for each view
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getDiscountImage(), 0, product.getDiscountImage().length);
            productImage.setImageBitmap(bitmap);
            productName.setText(product.getProductName());
            productDiscountPercentage.setText(product.getDiscountPercentage());
            productDiscountCode.setText(product.getDiscountCode());
        }
    }
}
