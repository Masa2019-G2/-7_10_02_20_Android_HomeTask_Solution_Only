package com.example.a10_02_20;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StoreProvider {
    public static final String SP_AUTH = "AUTH";
    public static final String SP_CONTACTS="CONTACTS";
    public static final String TOKEN= "TOKEN";

    private Context context;

    public StoreProvider(Context context) {
        this.context = context;
    }

    public boolean saveToken(String email, String password){
        return context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .edit()
                .putString(TOKEN,email+":"+password)
                .commit();
    }

    public String getToken(){
        return context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .getString(TOKEN,null);
    }

    public boolean clearToken(){
        return context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .edit()
                .remove(TOKEN)
                .commit();
    }


    public List<Contact> getAllContacts(){
        String token = getToken();
        List<Contact> list = new ArrayList<>();
        String str = context.getSharedPreferences(SP_CONTACTS,Context.MODE_PRIVATE)
                .getString(token,null);
        if(str != null){
            String[] arr = str.split(";");
            for(String s : arr){
                list.add(Contact.of(s));
            }

        }
        return list;
    }

    public boolean saveContacts(List<Contact> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if(i < list.size() - 1 ){
                sb.append(";");
            }
        }
        String token = getToken();
        return context.getSharedPreferences(SP_CONTACTS,Context.MODE_PRIVATE)
                .edit()
                .putString(token,sb.toString())
                .commit();
    }

    public boolean addContact(Contact contact){
        List<Contact> list = getAllContacts();
        list.add(contact);
        return saveContacts(list);
    }

    public boolean removeContact(int index){
        List<Contact> list = getAllContacts();
        list.remove(index);
        if(list.isEmpty()){
            return context.getSharedPreferences(SP_CONTACTS,Context.MODE_PRIVATE)
                    .edit()
                    .remove(getToken())
                    .commit();
        }
        return saveContacts(list);
    }

    public boolean updateContact(int index, Contact contact){
        List<Contact> list = getAllContacts();
        list.set(index, contact);
        return saveContacts(list);
    }

}
