package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ListUser extends AppCompatActivity {
RecyclerView rvCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        CustomerList[] customerLists=new CustomerList[5];
        customerLists[0]=new CustomerList("Reemu","9876543210","Mercury","Female");
        customerLists[1]=new CustomerList("Jinkal","7896543210","Mars","Female");
        customerLists[2]=new CustomerList("Akshat","8974563210","A-5,Mars","Female");
        customerLists[3]=new CustomerList("Dipesh","9976543211","Shahibaugh","Female");
        customerLists[4]=new CustomerList("Lalita","7896541333","Manianagr","Female");
        rvCustomer=findViewById(R.id.rvCustomer);

        MyCustomerAdapter myCustomerAdapter=new MyCustomerAdapter();
        myCustomerAdapter.setCustomerList(customerLists);

        rvCustomer.setAdapter(myCustomerAdapter);
    }
}