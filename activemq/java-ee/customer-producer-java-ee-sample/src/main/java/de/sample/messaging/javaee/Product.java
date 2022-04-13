package de.sample.messaging.javaee;

import lombok.Data;

@Data
public class Product {

    private String shortcut;
    private String name;
    private double price;
    private int stock;

}
