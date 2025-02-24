package ru.spshop.projects.projects_services.csvFilter.csv;

import java.util.Objects;

public class StructureCSV {
    private final String name;
    private final String artucul;
    private final int price;
    private int item;

    public StructureCSV(String name, String artucul, int price, int item) {
        this.name = name;
        this.artucul = artucul;
        this.price = price;
        this.item = item;
    }

    public StructureCSV(String name, int price, int item) {
        this.name = name;
        this.artucul = null;
        this.price = price;
        this.item = item;
    }


    public String getName() {
        return name;
    }

    public String getArtucul() {
        return artucul;
    }

    public int getPrice() {
        return price;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "name= " + name + ", artucul= " + artucul + ", price= " + price + ", item= " + item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructureCSV that = (StructureCSV) o;
        return price == that.price && item == that.item && Objects.equals(name, that.name) && Objects.equals(artucul, that.artucul);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artucul, price, item);
    }
}
