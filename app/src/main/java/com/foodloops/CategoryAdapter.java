package com.foodloops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.foodloops.R;
import com.foodloops.Category;

import java.util.List;

/**
 * Adapter for displaying category items in a horizontal RecyclerView.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    private final List<Category> categories;
    private final OnCategoryClickListener listener;
    private int selectedPosition = 0; // Default to first position (usually "All")

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category, int position);
    }

    public CategoryAdapter(Context context, List<Category> categories, OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        boolean isSelected = position == selectedPosition;
        holder.bind(category, isSelected);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;

        // Update the previously selected and newly selected items
        notifyItemChanged(previousPosition);
        notifyItemChanged(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final ImageView categoryIcon;
        private final TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.category_card);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    setSelectedPosition(position);
                    listener.onCategoryClick(categories.get(position), position);
                }
            });
        }

        public void bind(Category category, boolean isSelected) {
            categoryName.setText(category.getName());

            // Set icon based on category ID
            int iconResourceId = getIconResourceId(category.getIconName());
            if (iconResourceId != 0) {
                categoryIcon.setImageResource(iconResourceId);
            }

            // Update appearance based on selection state
            if (isSelected) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                categoryName.setTextColor(ContextCompat.getColor(context, R.color.white));
                categoryIcon.setColorFilter(ContextCompat.getColor(context, R.color.white));
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.categoryBackground));
                categoryName.setTextColor(ContextCompat.getColor(context, R.color.textPrimary));
                categoryIcon.setColorFilter(ContextCompat.getColor(context, R.color.iconGray));
            }
        }

        private int getIconResourceId(String iconName) {
            // Convert string icon names to drawable resource IDs
            switch (iconName) {
                case "ic_all":
                case "shopping-basket":
                    return R.drawable.ic_all_products;
                case "ic_dairy":
                case "milk":
                    return R.drawable.ic_dairy;
                case "ic_produce":
                case "apple":
                    return R.drawable.ic_produce;
                case "ic_bakery":
                case "bread":
                    return R.drawable.ic_bakery;
                case "ic_meat":
                case "drumstick":
                    return R.drawable.ic_meat;
                case "ic_grocery":
                case "shopping-cart":
                    return R.drawable.ic_grocery;
                default:
                    return R.drawable.ic_default_category;
            }
        }
    }
}

