package com.ndurska.coco3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClientCardEdit.ClientCardEditListener, ClientCardBig.ClientCardBigListener {

    private Button btnClientAdd;
    private EditText etClientSearch;
    private RecyclerView rvClientList;
    ClientCardAdapter adapter;
    List<Client> clients;
    private int activeClientPosition;
    DataBaseHelper dataBaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get clients from database
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
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
                    clients= dataBaseHelper.getClients();
                    adapter= new ClientCardAdapter(clients,MainActivity.this);
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


    @Override
    public void onBtnSaveClicked() {
        clients = dataBaseHelper.getClients();
        ClientCardBig clientCardBig = ClientCardBig.newInstance(clients.get(activeClientPosition));
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder,clientCardBig).commit();
        adapter.notifyItemChanged(activeClientPosition);
    }

    @Override
    public void onBtnDeleteClicked() {
         FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
          ft.remove(getSupportFragmentManager().findFragmentById(R.id.placeholder)).commit();
         adapter.items.remove(activeClientPosition);
         adapter.notifyItemRemoved(activeClientPosition);

    }

    @Override
    public void onBtnEditClicked(Client client) {
         ClientCardEdit clientCardEdit=ClientCardEdit.newInstance(client);
         FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.placeholder,clientCardEdit).commit();
    }
}