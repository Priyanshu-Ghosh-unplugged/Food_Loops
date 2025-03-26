package com.foodloops;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodloops.R;
import com.foodloops.ProductDetailActivity;
import com.foodloops.Product;
import com.foodloops.PricingAlgorithm;

import java.util.List;

/**
 * Adapter for displaying product cards in a RecyclerView.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> products;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
        void onAddToCartClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> products, OnProductClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productDescription;
        private final TextView productPrice;
        private final TextView originalPrice;
        private final TextView discountPercentage;
        private final TextView expiryText;
        private final TextView stockText;
        private final Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            originalPrice = itemView.findViewById(R.id.original_price);
            discountPercentage = itemView.findViewById(R.id.discount_percentage);
            expiryText = itemView.findViewById(R.id.expiry_text);
            stockText = itemView.findViewById(R.id.stock_text);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(products.get(position));
                }
            });

            addToCartButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onAddToCartClick(products.get(position));
                }
            });
        }

        public void bind(Product product) {
            productName.setText(product.getName());

            if (product.getDescription() != null) {
                productDescription.setText(product.getDescription());
                productDescription.setVisibility(View.VISIBLE);
            } else {
                productDescription.setVisibility(View.GONE);
            }

            // Load image
            if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(product.getImageUrl())
                        .placeholder(R.drawable.placeholder_image)
                        .into(productImage);
            } else {
                // Set a default placeholder
                productImage.setImageResource(R.drawable.placeholder_image);
            }

            // Set price information
            productPrice.setText(String.format("₹%.2f", product.getCurrentPrice()));
            originalPrice.setText(String.format("₹%.2f", product.getOriginalPrice()));

            // Show discount percentage if there is one
            int discount = product.getDiscountPercentage();
            if (discount > 0) {
                discountPercentage.setText(String.format("%d%% OFF", discount));
                discountPercentage.setVisibility(View.VISIBLE);
                originalPrice.setVisibility(View.VISIBLE);
            } else {
                discountPercentage.setVisibility(View.GONE);
                originalPrice.setVisibility(View.GONE);
            }

            // Set expiry information
            expiryText.setText(PricingAlgorithm.formatTimeRemaining(product.getExpiryDate()));

            // Set stock information
            stockText.setText(String.format("In Stock: %d %s", product.getStockRemaining(), product.getUnit()));
        }
    }

    public void updateProducts(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }
}
