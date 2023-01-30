package com.example.socks.Model;

import java.util.Objects;

public class Socks {
    private final Color color;
    private final Size size;
    private final int cottonPercent;


    public Socks(Color color, Size size, int cottonPercent) {
        this.color = color;
        this.size = size;
        this.cottonPercent = cottonPercent;
    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public int getCottonPercent() {
        return cottonPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPercent == socks.cottonPercent && color == socks.color && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPercent);
    }
}
