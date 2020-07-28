package com.example.decoroxx;

import java.util.List;

public class Product {
    private int idProduct;
    private String type;
    private int imageProduct;
    private String productTitle;
    private String productDescription;
    private double price;
    private List<String> colorList = null;

    public Product() { }

    public Product(int imageProduct, String productTitle, String productDescription, double price, List<String> colorList) {
        this.imageProduct = imageProduct;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.price = price;
        this.colorList = colorList;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getColorList() {
        return colorList;
    }

    public void setColorList(List<String> colorList) {
        this.colorList = colorList;
    }
}
