package com.example.ganeshr.easykeep.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.activity.Keep;
import com.example.ganeshr.easykeep.activity.UpdateActivity;
import com.example.ganeshr.easykeep.model.NotesModel;
import com.example.ganeshr.easykeep.rest.RealmManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganeshr on 23/3/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MViewHolder>{
    Context context;
    MViewHolder holder;

    List<NotesModel> list;
    NotesModel model;
    NotesModel model1;
    private int lastPosition = -1;

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

        model =list.get(position);

        holder.title.setText(list.get(position).getTitle());
        holder.notes.setText(list.get(position).getNote());

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setFilter(List<NotesModel> nModel){
        list=new ArrayList<>();
        list.addAll(nModel);
        notifyDataSetChanged();


    }

    /** Returns a share intent */
    public Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String shareMsg="Msg to share on Web  ";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Title"+shareMsg);
        intent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
        return intent;
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

            final CardView card=(CardView)itemView.findViewById(R.id.mycard);

            final RelativeLayout layout=(RelativeLayout)itemView.findViewById(R.id.layout);

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

                    Snackbar snackbar=Snackbar.make(layout,"Note Deleted",Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar snackbar1 = Snackbar.make(layout, "NOTE  is restored!", Snackbar.LENGTH_LONG);
                            RealmManger.addorUpadte(model1);
                            notifyDataSetChanged();
                            snackbar1.show();
                        }
                    });
                    snackbar.show();

                }
            });


            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    card.setCardBackgroundColor(Color.RED);
                    Keep.shareItem.setVisible(true);
                    return true;
                }
            });

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setCardBackgroundColor(Color.WHITE);
                    Keep.shareItem.setVisible(false);
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
          // final int adapterPosition = holder.getAdapterPosition();
            NotesModel model=list.get(getAdapterPosition());
            Log.d("Row Deleted" +model.getTitle().toString() ,":"+model.getNote().toString());
            model1=new NotesModel(model.getTitle(),model.getNote(),model.getId());

            RealmManger.deleteUser(model);
            notifyDataSetChanged();

        }



    }
}
