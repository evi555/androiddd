package com.example.sharedsecuritysystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.SystemConnectedResponse;

import java.util.ArrayList;

public class SystemConnectedAdapter extends RecyclerView.Adapter<SystemConnectedAdapter.ViewHolder> {
    ArrayList<SystemConnectedResponse> list;
    Context context;
    public SystemConnectedAdapter(ArrayList<SystemConnectedResponse> list, Context context){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SystemConnectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.system_connected_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SystemConnectedAdapter.ViewHolder holder, int position) {
    holder.textViewName.setText(list.get(position).getName());
    holder.textViewEmail.setText(list.get(position).getEmail());
    holder.textViewphone.setText(list.get(position).getPhone());

    if (position%2==0)
        holder.relativeLayout.getBackground().setTint(context.getResources().getColor(R.color.orange));
    else
        holder.relativeLayout.getBackground().setTint(context.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail, textViewphone;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewSystemConnectedName);
            textViewEmail = itemView.findViewById(R.id.textViewSystemConnectedEmail);
            textViewphone = itemView.findViewById(R.id.textViewSystemConnectedPhone);
            relativeLayout = itemView.findViewById(R.id.rlSystemConnected);
        }
    }
}
