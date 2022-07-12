package com.ndurska.coco3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnClientAdd;
    private EditText etClientSearch;
    private RecyclerView rvClientList;
    ClientCardAdapter adapter;
    List<Client> clients;
    private int activeClientPosition;


/////////////AAAAAAAAAAAAAA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get clients from database
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        clients = dataBaseHelper.getClients();

        //find buttons on layout
        btnClientAdd=findViewById(R.id.btnClientAdd);
        etClientSearch=findViewById(R.id.etClientSearch);
        rvClientList=findViewById(R.id.rvClientList);
        rvClientList.setLayoutManager(new LinearLayoutManager(this));

        //show clients on recycler view
        adapter= new ClientCardAdapter(clients,this);
        rvClientList.setAdapter(adapter);


        btnClientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client;

                try{
                    //add new client and update recycler view
                    client=new Client(etClientSearch.getText().toString());
                    dataBaseHelper.addClient(client);
                    List<Client> items= dataBaseHelper.getClients();
                    ClientCardAdapter adapter= new ClientCardAdapter(items,MainActivity.this);
                    rvClientList.setAdapter(adapter);

                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "New client error", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    public int getActiveClientPosition() {
        return activeClientPosition;
    }

    public void setActiveClientPosition(int activeClientPosition) {
        this.activeClientPosition = activeClientPosition;
    }


}