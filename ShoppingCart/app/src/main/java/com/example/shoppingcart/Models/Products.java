package com.example.shoppingcart.Models;

public class Products {
    private String CurrencyCode, Description, Name, ProductId, ProductPicUrl, Status;
    private long Price, Quantity;

    public Products() { }

    public Products(String currencyCode, String description, String name, Long price, String productId, String productPicUrl, Long quantity, String status) {
        CurrencyCode = currencyCode;
        Description = description;
        Name = name;
        Price = price;
        ProductId = productId;
        ProductPicUrl = productPicUrl;
        Quantity = quantity;
        Status = status;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductPicUrl() {
        return ProductPicUrl;
    }

    public void setProductPicUrl(String productPicUrl) {
        ProductPicUrl = productPicUrl;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long quantity) {
        Quantity = quantity;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
