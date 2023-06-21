package com.example.sustainablespoonfulapp;

public class DiscountedProduct {
    private String discountCode;

    // Get discount code:
    public String getDiscountCode() {
        return discountCode;
    }

    // Set discount code:
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    // Set discounts percentage:
    public String getDiscountPercentage() {
        return discountPercentage;
    }

    // Get discounts percentage:
    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    // Get product name:
    public String getProductName() {
        return productName;
    }

    // Set product name:
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Get product image:
    public byte[] getDiscountImage() {
        return discountImage;
    }

    // Set product image:
    public void setDiscountImage(byte[] discountImage) {
        this.discountImage = discountImage;
    }

    private String discountPercentage;
    private String productName;
    private byte[] discountImage;

    // Initializes the discount code, discount percentage, product name and product image:
    public DiscountedProduct(String discountCode, String discountPercentage, String productName, byte[] discountImage ){
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
        this.productName = productName;
        this.discountImage = discountImage;
    }
}
