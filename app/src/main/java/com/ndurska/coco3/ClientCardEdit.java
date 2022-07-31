package com.ndurska.coco3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private static final String ARG_PHONE_NUMBER1 = "phoneNumber1";
    private static final String ARG_PHONE_NUMBER2 = "phoneNumber2";
    private static final String ARG_PHONE_NUMBER_lABEL_1 = "phoneNumberLabel1";
    private static final String ARG_PHONE_NUMBER_LABEL_2 = "phoneNumberLabel2";
    private Client client;
    private int ID;
    private String name;
    private String adjective;
    private String breed;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumberLabel1;
    private String phoneNumberLabel2;
    DataBaseHelper dataBaseHelper;
    MainActivity activity;


    //references to elements
    TextView etClientName;
    TextView etClientAdjective;
    TextView etClientBreed;
    TextView etPhoneNumber1;
    TextView etPhoneNumber2;
    TextView etPhoneNumberLabel1;
    TextView etPhoneNumberLabel2;
    TextView tvClientID;
    Button btnSaveChanges;
    Button btnDeleteClient;

    private ClientCardEditListener listener;

    interface ClientCardEditListener {

         void onBtnSaveClicked(boolean isNewClient);
         void onBtnDeleteClicked(boolean isNewClient);
    }

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
        args.putString(ARG_PHONE_NUMBER1, client.getPhoneNumber1());
        args.putString(ARG_PHONE_NUMBER2, client.getPhoneNumber2());
        args.putString(ARG_PHONE_NUMBER_lABEL_1, client.getPhoneNumberLabel1());
        args.putString(ARG_PHONE_NUMBER_LABEL_2, client.getPhoneNumberLabel2());
        fragment.setArguments(args);
        return fragment;
    }
    public static ClientCardEdit newInstance() { //display an empty card for new client creation
        ClientCardEdit fragment = new ClientCardEdit();
        //placeholder empty client
        Client client = new Client();
        Bundle args = new Bundle();
        args.putSerializable("client", client);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ClientCardEditListener ){
            listener= (ClientCardEditListener) context;
        }else
            throw new RuntimeException(context+" must implement ClientCardEditListener");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ID = getArguments().getInt(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            adjective= getArguments().getString(ARG_ADJECTIVE);
            breed = getArguments().getString(ARG_BREED);
            phoneNumber1 = getArguments().getString(ARG_PHONE_NUMBER1);
            phoneNumber2 = getArguments().getString(ARG_PHONE_NUMBER2);
            phoneNumberLabel1 = getArguments().getString(ARG_PHONE_NUMBER_lABEL_1);
            phoneNumberLabel2 = getArguments().getString(ARG_PHONE_NUMBER_LABEL_2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_card_edit, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etClientName= view.findViewById(R.id.etClientName);
        etClientAdjective= view.findViewById(R.id.etClientAdjective);
        etClientBreed= view.findViewById(R.id.etClientBreed);
        etPhoneNumber1= view.findViewById(R.id.etPhoneNumber1);
        etPhoneNumber2= view.findViewById(R.id.etPhoneNumber2);
        etPhoneNumberLabel1= view.findViewById(R.id.etPhoneNumberLabel1);
        etPhoneNumberLabel2= view.findViewById(R.id.etPhoneNumberLabel2);
        tvClientID=view.findViewById(R.id.tvClientID);
        btnSaveChanges=view.findViewById(R.id.btnSaveChanges);
        btnDeleteClient=view.findViewById(R.id.btnDeleteClient);
        client = (Client) getArguments().getSerializable("client");
        dataBaseHelper = new DataBaseHelper(getActivity());
        try{
            tvClientID.setText(String.valueOf(ID));
            etClientName.setText(name);
            etClientAdjective.setText(adjective);
            etClientBreed.setText(breed);
            etPhoneNumber1.setText(phoneNumber1);
            etPhoneNumber2.setText(phoneNumber2);
            if(phoneNumberLabel1.length()==0)
                etPhoneNumberLabel1.setText("Telefon:");
            else
                etPhoneNumberLabel1.setText(phoneNumberLabel1);
            if(phoneNumberLabel2.length()==0)
                etPhoneNumberLabel2.setText("Telefon:");
            else
                etPhoneNumberLabel2.setText(phoneNumberLabel2);

        }catch (Exception e){
        Toast.makeText(getActivity(),"Blad w onViewCreated Edit",Toast.LENGTH_LONG).show();
    }
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(client.getClientId()!=0){
                    saveClientEdits();
                    listener.onBtnSaveClicked(false);
                }
                else {
                    createNewClient();
                    listener.onBtnSaveClicked(true);
                }
            }
        });
        btnDeleteClient.setOnClickListener(view1 -> {

            if(client.getClientId()!=0){    //if we are removing an existing client remove it from database
                try {
                    activity = (MainActivity) getActivity();
                    Toast.makeText(activity,"Usuwam klienta "+activity.getActiveClientPosition()+ " o ID "+client.getClientId(),Toast.LENGTH_LONG).show();
                    dataBaseHelper.deleteClient(client.getClientId());
                }catch (Exception e){
                    Toast.makeText(activity,"Nastąpił błąd przy usuwaniu klienta",Toast.LENGTH_LONG).show();
                }
                listener.onBtnDeleteClicked(false);

            }else {           //if we are closing the creation of a new client
                listener.onBtnDeleteClicked(true);
            }

        });
    }
    private void saveClientEdits(){
        try {
            client.setName(etClientName.getText().toString());
            client.setAdjective(etClientAdjective.getText().toString());
            client.setBreed(etClientBreed.getText().toString());
            client.setPhoneNumber1(etPhoneNumber1.getText().toString());
            client.setPhoneNumber2(etPhoneNumber2.getText().toString());
            client.setPhoneNumberLabel1(etPhoneNumberLabel1.getText().toString());
            client.setPhoneNumberLabel2(etPhoneNumberLabel2.getText().toString());
            boolean success= dataBaseHelper.editClient(client);
            if (success)
                Toast.makeText(getActivity(),"Zapisano zmiany klienta",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(),"Nie zapisano zmian klienta",Toast.LENGTH_LONG).show();
         }catch (Exception e){
            Toast.makeText(getActivity(),"Blad podczas zapisywania zmian klienta",Toast.LENGTH_LONG).show();
         }
    }

    private void createNewClient(){
        try {
            client.setName(etClientName.getText().toString());
            client.setAdjective(etClientAdjective.getText().toString());
            client.setBreed(etClientBreed.getText().toString());
            client.setPhoneNumber1(etPhoneNumber1.getText().toString());
            client.setPhoneNumber2(etPhoneNumber2.getText().toString());
            client.setPhoneNumberLabel1(etPhoneNumberLabel1.getText().toString());
            client.setPhoneNumberLabel2(etPhoneNumberLabel2.getText().toString());
            boolean success= dataBaseHelper.addClient(client);
            if (success)
                Toast.makeText(getActivity(),"Zapisano nowego klienta",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(),"Nie zapisano nowego klienta",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getActivity(),"Blad podczas tworzenia klienta",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}

