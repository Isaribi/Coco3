package com.ndurska.coco3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ClientCardEdit.ClientCardEditListener, ClientCardBig.ClientCardBigListener {

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
        Button btnClientAdd = findViewById(R.id.btnClientAdd);
        etClientSearch=findViewById(R.id.etClientSearch);
        rvClientList=findViewById(R.id.rvClientList);
        rvClientList.setLayoutManager(new LinearLayoutManager(this));

        //show clients on recycler view
        adapter= new ClientCardAdapter(clients,this);
        rvClientList.setAdapter(adapter);
        etClientSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //search bar behaviour, works with fragments of words in random order
                List<Client> searchResults= new ArrayList<>();
                String[] keywords= editable.toString().split(" ");
                int matches;

                for (Client client : clients){
                    matches=0;
                    for(String keyword : keywords)
                        if( client.toStringShort().toLowerCase().contains(keyword.toLowerCase())){
                            matches++;
                        }
                    if (matches == keywords.length)
                        searchResults.add(client);
                }
                adapter= new ClientCardAdapter(searchResults,MainActivity.this);
                rvClientList.setAdapter(adapter);

            }
        } );


        btnClientAdd.setOnClickListener(view -> {

            try{
                //show creation card for a new client
                etClientSearch.setText(null);
                ClientCardEdit clientCardEdit = ClientCardEdit.newInstance();
                activeClientPosition=clients.size();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder,clientCardEdit).commit();
            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "Błąd przy tworzeniu nowego klienta", Toast.LENGTH_SHORT).show();
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
    public void onBtnSaveClicked(boolean isNewClient) {

        //close editing and show client
        clients = dataBaseHelper.getClients();
        ClientCardBig clientCardBig = ClientCardBig.newInstance(clients.get(activeClientPosition));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, clientCardBig).commit();

        if(isNewClient){    //if this is a new client add it to the recycler view
            adapter.items.add(clients.get(activeClientPosition));
            adapter.notifyItemInserted(activeClientPosition);
        }else {     //if this is an existing client update recycler view and show edited client card
            adapter.notifyItemChanged(activeClientPosition);
        }

    }

    @Override
    public void onBtnDeleteClicked(boolean isNewClient) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.placeholder))).commit();

        if(!isNewClient){       //if this is an existing client remove it from view
            adapter.items.remove(activeClientPosition);
            adapter.notifyItemRemoved(activeClientPosition);

        }

    }

    @Override
    public void onBtnEditClicked(Client client) {
         ClientCardEdit clientCardEdit=ClientCardEdit.newInstance(client);
         FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.placeholder,clientCardEdit).commit();
    }

    @Override
    public void onSameOwnerClientClicked(Client client) {
        ClientCardBig clientCardBig=ClientCardBig.newInstance(client);
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder,clientCardBig).commit();
        for (Client c : clients)
            if (c.getClientId() == client.getClientId())
                setActiveClientPosition(clients.indexOf(c));

    }
}