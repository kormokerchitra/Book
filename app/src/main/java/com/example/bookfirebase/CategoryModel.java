package com.example.bookfirebase;

public class CategoryModel {
    String id;
    String category;

    CategoryModel(String id, String category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}
