package com.example.a10_02_20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    ViewGroup viewGroup, editGroup;
    EditText inputName, inputEmail, inputPhone, inputAddress, inputDesc;
    TextView nameTxt, emailTxt, phoneTxt, addressTxt, descTxt;
    MenuItem itemDelete, itemSave, itemEdit;
    int currIndex;
    Contact currContact;
    StoreProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provider = new StoreProvider(this);
        setContentView(R.layout.activity_edit);
        init();
        currContact = new Contact();
        Intent intent = getIntent();
        currIndex = intent.getIntExtra("POS",-1);
        currContact.name = intent.getExtras().getString("NAME","");
        currContact.email = intent.getExtras().getString("EMAIL","");
        currContact.phone = intent.getExtras().getString("PHONE","");
        currContact.address = intent.getExtras().getString("ADDRESS","");
        currContact.description = intent.getExtras().getString("DESC","");

        if(currIndex < 0){
            showMode("EDIT");
        }else{
            showMode("VIEW");
        }
    }

    private void init(){
        viewGroup = findViewById(R.id.viewGroup);
        editGroup = findViewById(R.id.editGroup);

        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        inputDesc = findViewById(R.id.inputDesc);

        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        addressTxt = findViewById(R.id.addressTxt);
        descTxt = findViewById(R.id.descTxt);
    }

    private void showMode(String mode){
        Log.d("MY_TAG", "showMode: " + mode);
        viewGroup.setVisibility(mode.equals("EDIT") ? View.GONE : View.VISIBLE);
        editGroup.setVisibility(mode.equals("EDIT") ? View.VISIBLE: View.GONE);


        inputName.setText(currContact.name);
        inputEmail.setText(currContact.email);
        inputPhone.setText(currContact.phone);
        inputAddress.setText(currContact.address);
        inputDesc.setText(currContact.description);

        nameTxt.setText(currContact.name);
        emailTxt.setText(currContact.email);
        phoneTxt.setText(currContact.phone);
        addressTxt.setText(currContact.address);
        descTxt.setText(currContact.description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        itemDelete = menu.findItem(R.id.itemDelete);
        itemEdit = menu.findItem(R.id.itemEdit);
        itemSave = menu.findItem(R.id.itemSave);
        if(currIndex < 0){
            itemSave.setVisible(true);
            itemEdit.setVisible(false);
            itemDelete.setVisible(false);
        }else{
            itemSave.setVisible(false);
            itemDelete.setVisible(true);
            itemEdit.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemSave){
            currContact.name = inputName.getText().toString();
            currContact.email = inputEmail.getText().toString();
            currContact.phone = inputPhone.getText().toString();
            currContact.address = inputAddress.getText().toString();
            currContact.description = inputDesc.getText().toString();
            if(currIndex < 0){
                provider.addContact(currContact);
            }else{
                provider.updateContact(currIndex,currContact);
            }
            finish();
        }else if(item.getItemId() == R.id.itemDelete){
            provider.removeContact(currIndex);
            finish();
        }else if(item.getItemId() == R.id.itemEdit){
            itemEdit.setVisible(false);
            itemDelete.setVisible(false);
            itemSave.setVisible(true);
            showMode("EDIT");
        }
        return super.onOptionsItemSelected(item);
    }
}
