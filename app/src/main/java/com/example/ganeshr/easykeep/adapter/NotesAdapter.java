package com.example.ganeshr.easykeep.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.activity.UpdateActivity;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import java.util.List;

/**
 * Created by ganeshr on 23/3/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MViewHolder>{
    Context context;
    MViewHolder holder;

    List<NotesModel> list;


    public NotesAdapter(Context context, List<NotesModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        holder=new MViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.notes.setText(list.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{

        TextView title,notes;
        ImageButton del,edit;

        public MViewHolder(View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.card_title);
            notes=(TextView)itemView.findViewById(R.id.card_note);
            del=(ImageButton)itemView.findViewById(R.id.del_btn);
            edit=(ImageButton)itemView.findViewById(R.id.edit_btn);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editRow();
                }
            });



            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delteRow();
                }
            });



        }

        private void editRow() {
            NotesModel model=list.get(getAdapterPosition());
            Intent updateIntent=new Intent(context, UpdateActivity.class);
            updateIntent.putExtra("AddUpdate",model);
            updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(updateIntent);

            notifyDataSetChanged();


        }

        private void delteRow() {
            NotesModel model=list.get(getAdapterPosition());
            RealmManger.deleteUser(model);
            notifyDataSetChanged();

        }

    }
}
