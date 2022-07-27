package com.ndurska.coco3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClientCardAdapter extends RecyclerView.Adapter<ClientCardVH> {
    List<Client> items;
    Context context;
    DataBaseHelper dataBaseHelper;
    MainActivity mainActivity;
    Client activeClient;

    public ClientCardAdapter(List<Client> items,Context context) {
        this.items = items;
        this.context=context;

        dataBaseHelper = new DataBaseHelper(context);
        mainActivity= (MainActivity) context;

    }

    @NonNull
    @Override
    public ClientCardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_card_item,parent,false);
        return new ClientCardVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientCardVH holder, int position) {
        try {
            Client client=items.get(position);
            holder.tvClientID.setText( String.valueOf(client.getClientId()));
            StringBuffer clientName= new StringBuffer(client.getName()+" "+client.getAdjective()+" "+client.getBreed());
            holder.tvClientName.setText(clientName);

        }catch(Exception e){
            holder.tvClientID.setText("ERR");
            holder.tvClientName.setText(items.get(position).getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeClient=items.get(holder.getAdapterPosition());
                mainActivity.setActiveClientPosition(items.indexOf(activeClient));
                FragmentTransaction ft= mainActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder,ClientCardBig.newInstance(activeClient)).commit(); //tworzenie CardBig

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Client getActiveClient() {
        return activeClient;
    }
}
class ClientCardVH extends RecyclerView.ViewHolder{

    TextView tvClientID;
    TextView tvClientName;



    private  ClientCardAdapter adapter;


    public ClientCardVH(@NonNull View itemView) {
        super(itemView);
        tvClientName = itemView.findViewById(R.id.tvClientCardItemName);
        tvClientID = itemView.findViewById(R.id.tvClientCardItemID);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public ClientCardVH linkAdapter(ClientCardAdapter adapter){
        this.adapter = adapter;
        return this;

    }
}