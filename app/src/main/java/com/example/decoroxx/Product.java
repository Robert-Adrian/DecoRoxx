package com.example.decoroxx;


import java.util.Arrays;
import java.util.List;

public class Product {
    private int idProduct;
    private String type;
    private byte[] imageProduct;
    private String productTitle;
    private String productDescription;
    private int price;
    private int quantity;
    private String selectedColor;
    private String colorList = null;

    public Product() { }

    public Product(String type, byte[] imageProduct, String productTitle, String productDescription, int price, String colorList) {
        this.type = type;
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

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getColorList() {
        return colorList;
    }

    public void setColorList(String colorList) {
        this.colorList = colorList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", type='" + type + '\'' +
                ", imageProduct=" + Arrays.toString(imageProduct) +
                ", productTitle='" + productTitle + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", selectedColor='" + selectedColor + '\'' +
                ", colorList='" + colorList + '\'' +
                '}';
    }
}
