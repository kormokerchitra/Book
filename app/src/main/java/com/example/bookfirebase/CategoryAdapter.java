package com.example.bookfirebase;

import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {

    ArrayList<CategoryModel> products;
    Context context;
    int resource;

    public CategoryAdapter(Context context, int resource, ArrayList<CategoryModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_item, null, true);

        }
        CategoryModel product = getItem(position);

        TextView category = (TextView) convertView.findViewById(R.id.title); //type casting
        String category_title = product.getCategory();
        category.setText(category_title);



        return convertView;
    }

    ;
}