/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.revendo;

import java.util.ArrayList;

class Product {
    String name;
    String status;
    String owner;

    Product(String name, String status, String owner) {
        this.name = name;
        this.status = status;
        this.owner = owner;
    }
}

class User {
    String username;
    ArrayList<Product> donatedProducts = new ArrayList<>();
    ArrayList<Product> soldProducts = new ArrayList<>();
    ArrayList<Product> boughtProducts = new ArrayList<>();

    User(String username) {
        this.username = username;
    }
}

public class Ecoimpact {

    static ArrayList<Product> products = new ArrayList<>();
    static User loggedInUser;

    public static void main(String[] args) {

        
        loggedInUser = new User("john");

        loggedInUser.donatedProducts.add(new Product("Old Phone", "DONATED", "john"));
        loggedInUser.donatedProducts.add(new Product("Books", "DONATED", "john"));

        loggedInUser.soldProducts.add(new Product("Laptop", "FOR_SALE", "john"));
        loggedInUser.boughtProducts.add(new Product("Chair", "FOR_SALE", "alice"));

        products.add(new Product("TV", "REPAIR", "john"));
        products.add(new Product("Fan", "REPAIR", "john"));

        
        ecoImpact();
    }

    static void ecoImpact() {
        int donated = loggedInUser.donatedProducts.size();
        int repaired = 0;

        for (Product p : products) {
            if (p.status.equals("REPAIR") && p.owner.equals(loggedInUser.username)) {
                repaired++;
            }
        }

        int ReSale = loggedInUser.soldProducts.size();
        int bought = loggedInUser.boughtProducts.size();

        System.out.println("\n=== Your Environmental Impact ===");
        System.out.println("🎁 Items Donated: " + donated);
        System.out.println("🔧 Repair Requests: " + repaired);
        System.out.println("💰 Items Sold: " + ReSale);
        System.out.println("🛒 Items Bought: " + bought);

        int carbonSaved = (donated * 5) + (repaired * 7) + (ReSale * 3) + (bought * 2);
        System.out.println("🌍 Carbon Footprint Saved: " + carbonSaved + " kg CO2");

        int ecoScore = Math.min(100, (donated * 25) + (repaired * 20) + (ReSale * 10) + (bought * 5));
        System.out.println("⭐ Eco Score: " + ecoScore + "/100");
    }
}

    