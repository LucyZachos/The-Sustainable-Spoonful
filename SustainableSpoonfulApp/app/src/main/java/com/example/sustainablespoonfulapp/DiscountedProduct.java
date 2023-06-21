package com.example.sustainablespoonfulapp;

public class DiscountedProduct {
    private String discountCode;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getDiscountImage() {
        return discountImage;
    }

    public void setDiscountImage(byte[] discountImage) {
        this.discountImage = discountImage;
    }

    private String discountPercentage;
    private String productName;
    private byte[] discountImage;

    public DiscountedProduct(String discountCode, String discountPercentage, String productName, byte[] discountImage ){
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
        this.productName = productName;
        this.discountImage = discountImage;
    }
}
