package com.ndurska.coco3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientCardEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientCardEdit extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "ID";
    private static final String ARG_NAME = "name";
    private static final String ARG_ADJECTIVE = "adjective";
    private static final String ARG_BREED = "breed";
    private static final String ARG_OWNER_ID = "ownerID";

    private Client client;
    private int ID;
    private String name;
    private String adjective;
    private String breed;
    private int ownerID;
    DataBaseHelper dataBaseHelper;
    MainActivity activity;
    Fragment me;

    //references to elements
    TextView etClientName;
    TextView etClientAdjective;
    TextView etClientBreed;
    TextView etClientOwnerID;
    Button btnSaveChanges;
    Button btnDeleteClient;

    public ClientCardEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientCardEdit.
     */

    public static ClientCardEdit newInstance(Client client) {
        ClientCardEdit fragment = new ClientCardEdit();
        Bundle args = new Bundle();


        args.putSerializable("client", client);
        args.putInt(ARG_ID,client.getClientId());
        args.putString(ARG_NAME, client.getName());
        args.putString(ARG_ADJECTIVE, client.getAdjective());
        args.putString(ARG_BREED, client.getBreed());
        args.putInt(ARG_OWNER_ID, client.getOwnerId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ID = getArguments().getInt(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            adjective= getArguments().getString(ARG_ADJECTIVE);
            breed = getArguments().getString(ARG_BREED);
            ownerID = getArguments().getInt(ARG_OWNER_ID);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        me = this;
        return inflater.inflate(R.layout.fragment_client_card_edit, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etClientName= view.findViewById(R.id.etClientName);
        etClientAdjective= view.findViewById(R.id.etClientAdjective);
        etClientBreed= view.findViewById(R.id.etClientBreed);
        etClientOwnerID = view.findViewById(R.id.etClientOwnerID);
        btnSaveChanges=view.findViewById(R.id.btnSaveChanges);
        btnDeleteClient=view.findViewById(R.id.btnDeleteClient);
        client = (Client) getArguments().getSerializable("client");
        dataBaseHelper = new DataBaseHelper(getActivity());
        try{
            etClientName.setText( name);
            etClientAdjective.setText(adjective);
            etClientBreed.setText(breed);
            etClientOwnerID.setText(String.valueOf(ownerID));
        }catch (Exception e){
        Toast.makeText(getActivity(),"Blad w onViewCreated Edit",Toast.LENGTH_LONG).show();
    }
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    client.setName(etClientName.getText().toString());
                    client.setAdjective(etClientAdjective.getText().toString());
                    client.setBreed(etClientBreed.getText().toString());
                    client.setOwnerId(Integer.parseInt(etClientOwnerID.getText().toString()));
                   boolean success= dataBaseHelper.editClient(client);
                   if (success)
                       Toast.makeText(getActivity(),"Zapisano klienta",Toast.LENGTH_LONG).show();
                   else
                       Toast.makeText(getActivity(),"Nie zapisano klienta",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Blad podczas zapisywania klienta",Toast.LENGTH_LONG).show();
                }
                ClientCardBig clientCardBig = ClientCardBig.newInstance(client);
                FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder,clientCardBig).commit();

            }
        });
        btnDeleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //blad
                    activity = (MainActivity) getActivity();
                    Toast.makeText(activity,"Usuwam klienta "+activity.getActiveClientPosition()+ " o ID "+client.getClientId(),Toast.LENGTH_LONG).show();
                    dataBaseHelper.deleteClient(client.getClientId());
                    activity.adapter.items.remove(activity.getActiveClientPosition());
                    activity.adapter.notifyItemRemoved(activity.getActiveClientPosition());


                   // FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
                  //  ft.remove(activity.getSupportFragmentManager().findFragmentById(R.id.placeholder)).commit();
                }catch (Exception e){
                    Toast.makeText(activity,"Nastąpił błąd przy usuwaniu klienta",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = (MainActivity) getActivity();
        activity.adapter.notifyItemChanged(activity.getActiveClientPosition());
    }
}

