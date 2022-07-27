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
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String ARG_OWNER_ID = "ownerID";

    // TODO: Rename and change types of parameters
    private int ID;
    private String name;
    private String adjective;
    private String breed;
    private int ownerID;
    private ClientCardBigListener listener;


    //references to elements
    TextView tvClientName;
    TextView tvClientAdjective;
    TextView tvClientBreed;
    TextView tvClientOwnerID;
    TextView tvClientID;
    Button btnClientEdit;

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
        tvClientName= view.findViewById(R.id.tvClientName);
        tvClientAdjective= view.findViewById(R.id.tvClientAdjective);
        tvClientBreed= view.findViewById(R.id.tvClientBreed);
        tvClientOwnerID = view.findViewById(R.id.tvClientOwnerID);
        tvClientID =  view.findViewById(R.id.tvClientID);
        btnClientEdit = view.findViewById(R.id.btnClientEdit);
        try {
            tvClientName.setText(name);
            tvClientAdjective.setText(adjective);
            tvClientBreed.setText(breed);
            tvClientOwnerID.setText(String.valueOf(ownerID));
            tvClientID.setText(String.valueOf(ID));
        }catch (Exception e){
            Toast.makeText(getActivity(),"Blad w onViewCreated",Toast.LENGTH_LONG).show();
        }
        btnClientEdit.setOnClickListener(new View.OnClickListener() {
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