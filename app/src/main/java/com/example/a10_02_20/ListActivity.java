package com.example.a10_02_20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    StoreProvider provider;
    ListView list;
    ContactAdapter adapter;
    TextView emptyTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provider = new StoreProvider(this);
        setContentView(R.layout.activity_list);
        list = findViewById(R.id.contactList);
        emptyTxt = findViewById(R.id.emptyTxt);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = (Contact) adapter.getItem(position);
            Intent intent = new Intent(this,EditActivity.class);
            intent.putExtra("POS",position);
            intent.putExtra("NAME",contact.name);
            intent.putExtra("EMAIL",contact.email);
            intent.putExtra("PHONE",contact.phone);
            intent.putExtra("ADDRESS",contact.address);
            intent.putExtra("DESC",contact.description);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ContactAdapter(provider.getAllContacts());
        emptyTxt.setVisibility(adapter.getCount() > 0 ? View.GONE : View.VISIBLE);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemLogout){
            provider.clearToken();
            setResult(RESULT_OK);
            finish();
        }else if(item.getItemId() == R.id.itemAdd){
            Intent intent = new Intent(this,EditActivity.class);
            intent.putExtra("POS",-1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
