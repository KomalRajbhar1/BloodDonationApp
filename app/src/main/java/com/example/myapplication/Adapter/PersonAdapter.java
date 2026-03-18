package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Model.Person;
import java.util.List;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    List<Person> personList;
    Context context;
    AdapterView.OnItemClickListener listener;

    public PersonAdapter(List<Person> personList, Context context, AdapterView.OnItemClickListener listener) {
        this.personList = personList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person,parent,false);
        return new RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder._person_tv_id.setText(string.valueOf(person.getId()));
        holder._person_tv_firstName.setText(person.getFirstName());
        holder._person_tv_lastName.setText(person.getLastName());
        holder._person_tv_address.setText(person.getAddress());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView _person_tv_id,_person_tv_firstName,_person_tv_lastName,_person_tv_address,_person_tv_editPerson,_person_tv_deletePerson;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            _person_tv_id = itemView.findViewById(R.id.person_tv_id);
            _person_tv_firstName = itemView.findViewById(R.id.person_tv_firstName);
            _person_tv_lastName = itemView.findViewById(R.id.person_tv_lastName);
            _person_tv_address = itemView.findViewById(R.id.person_tv_address);
            _person_tv_editPerson = itemView.findViewById(R.id.person_tv_editPerson);
            _person_tv_deletePerson = itemView.findViewById(R.id.person_tv_deletePerson);
        }
    }
}