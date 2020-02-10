package com.example.a10_02_20;

public class Contact {
    String name;
    String email;
    String phone;
    String address;
    String description;

    public Contact() {
    }

    public Contact(String name, String email, String phone, String address, String description) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    @Override
    public String toString(){
        return name + "," + email + "," + phone + "," + address + "," + description;
    }

    public static Contact of(String str){
        String[] arr = str.split(",");
        return new Contact(arr[0],arr[1],arr[2],arr[3],arr[4]);
    }
}
