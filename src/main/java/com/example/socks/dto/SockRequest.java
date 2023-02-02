package com.example.socks.dto;


import com.example.socks.Model.Color;
import com.example.socks.Model.Size;

public class SockRequest {
    private Color color;
    private Size size;
    private int cottonPercent;
    private int quantity;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getCottonPercent() {
        return cottonPercent;
    }

    public void setCottonPercent(int cottonPercent) {
        this.cottonPercent = cottonPercent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}