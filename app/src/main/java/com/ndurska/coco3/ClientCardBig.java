package com.ndurska.coco3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientCardBig#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientCardBig extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "ID";
    private static final String ARG_NAME = "name";
    private static final String ARG_ADJECTIVE = "adjective";
    private static final String ARG_BREED = "breed";
    private static final String ARG_PHONE_NUMBER_1 = "phoneNumber1";
    private static final String ARG_PHONE_NUMBER_2 = "phoneNumber2";
    private static final String ARG_OWNER_ID = "ownerID";

    // TODO: Rename and change types of parameters

    private int ID;
    private String name;
    private String adjective;
    private String breed;
    private String phoneNumber1;
    private String phoneNumber2;
    private int ownerID;
    private ClientCardBigListener listener;


    //references to elements
    TextView tvName;
    TextView tvAdjective;
    TextView tvBreed;
    TextView tvID;
    TextView tvPhoneNumber1;
    TextView tvPhoneNumber2;
    Button btnEdit;
    LinearLayout sameOwnerClients;

    interface ClientCardBigListener{
        public void onBtnEditClicked(Client client);
    }
    public ClientCardBig() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientCardBig.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientCardBig newInstance(Client client) {
        ClientCardBig fragment = new ClientCardBig();
        Bundle args = new Bundle();


        args.putSerializable("client", client);
        args.putInt(ARG_ID,client.getClientId());
        args.putString(ARG_NAME, client.getName());
        args.putString(ARG_ADJECTIVE, client.getAdjective());
        args.putString(ARG_BREED, client.getBreed());
        args.putString(ARG_PHONE_NUMBER_1, client.getPhoneNumber1());
        args.putString(ARG_PHONE_NUMBER_2, client.getPhoneNumber2());
        args.putInt(ARG_OWNER_ID, client.getOwnerId());
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ClientCardBig.ClientCardBigListener){
            listener= (ClientCardBig.ClientCardBigListener) context;
        }else
            throw new RuntimeException(context.toString()+" must implement ClientCardBigListener");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ID = getArguments().getInt(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            adjective= getArguments().getString(ARG_ADJECTIVE);
            breed = getArguments().getString(ARG_BREED);
            phoneNumber1 = getArguments().getString(ARG_PHONE_NUMBER_1);
            phoneNumber2 = getArguments().getString(ARG_PHONE_NUMBER_2);
            ownerID = getArguments().getInt(ARG_OWNER_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_client_card_big, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName= view.findViewById(R.id.tvClientName);
        tvAdjective= view.findViewById(R.id.tvClientAdjective);
        tvBreed= view.findViewById(R.id.tvClientBreed);
        tvPhoneNumber1 = view.findViewById(R.id.tvPhoneNumber1);
        tvPhoneNumber2 = view.findViewById(R.id.tvPhoneNumber2);
        tvID =  view.findViewById(R.id.tvClientID);
        btnEdit = view.findViewById(R.id.btnClientEdit);
        sameOwnerClients = view.findViewById(R.id.llSameOwnerClients);


        try {
            tvName.setText(name);
            tvAdjective.setText(adjective);
            tvBreed.setText(breed);
            tvPhoneNumber1.setText(phoneNumber1);
            tvPhoneNumber2.setText(phoneNumber2);
            tvID.setText(String.valueOf(ID));

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            Client c =  dataBaseHelper.getClient(ID);



            for (Client client : dataBaseHelper.getSameOwnerClients(c)){
                TextView clientName = new TextView(getActivity());
                clientName.setText(client.getName());
                clientName.setTextSize(20);
                clientName.setPadding(5,5,5,5);

                sameOwnerClients.addView(clientName);


            }


        }catch (Exception e){
            Toast.makeText(getActivity(),"Blad w onViewCreated",Toast.LENGTH_LONG).show();
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBtnEditClicked((Client) getArguments().getSerializable("client"));
            }
        });

    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}