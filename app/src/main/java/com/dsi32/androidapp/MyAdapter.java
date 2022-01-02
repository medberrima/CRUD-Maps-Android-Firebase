package com.dsi32.androidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAdapter extends FirebaseRecyclerAdapter<Resto,MyAdapter.myviewholder>
{
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Resto> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder,  int position, @NonNull final Resto resto)
    {

        holder.nom.setText(resto.getNom());
        holder.location.setText(resto.getLocation());
        holder.services.setText(resto.getServices());
        DAOResto dao = new DAOResto();
        String r="";
        for (int i = 0; i < resto.getRank() ; i++)   r = r + "⭐" ;
     /*   if (resto.getRank()<5){
            for (int i = 0;  resto.getRank()<5 ; i++)   r = r + "☆" ;
        }*/

        holder.rank.setText(r);
        Glide.with(holder.img.getContext()).load(resto.getImageResto()).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_editresto))
                        .setExpanded(true,1800)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText nom = myview.findViewById(R.id.nom);
                final EditText location = myview.findViewById(R.id.location);
                final EditText services = myview.findViewById(R.id.services);
                final EditText rank = myview.findViewById(R.id.rank);
               final EditText description = myview.findViewById(R.id.description);
                final EditText imageResto = myview.findViewById(R.id.imgLink);
                Button submit=myview.findViewById(R.id.usubmit);

                imageResto.setText(resto.getImageResto());
                nom.setText(resto.getNom());
                location.setText(resto.getLocation());
                services.setText(resto.getServices());
                rank.setText(resto.getRank()+"");
                description.setText(resto.getDesc()+"");

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(  nom.getText().toString().isEmpty()){ nom.setError("nom is required !");   nom.requestFocus();    return ;   }
                        if(  location.getText().toString().isEmpty()){ location.setError("location is required !");   location.requestFocus();    return ;   }
                        if(  services.getText().toString().isEmpty()){ services.setError("services is required !");   services.requestFocus();    return ;   }
                        if(  rank.getText().toString().isEmpty()){ rank.setError("rank is required !");   rank.requestFocus();    return ;   }
                        if( Integer.parseInt(rank.getText().toString()) > 5 || Integer.parseInt(rank.getText().toString()) < 1){ rank.setError("Rank should be between 1 and 5 !");   rank.requestFocus();    return ;   }
                        if(  imageResto.getText().toString().isEmpty()){ imageResto.setError("imageResto is required !");   imageResto.requestFocus();    return ;   }
                        if(  description.getText().toString().isEmpty()){ description.setError("description is required !");   description.requestFocus();    return ;   }

                        Map<String,Object> map=new HashMap<>();
                        map.put("imageResto",imageResto.getText().toString());
                        map.put("nom",nom.getText().toString());
                        map.put("location",location.getText().toString());
                        map.put("services",services.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("rank",Integer.parseInt(rank.getText().toString()));

                        FirebaseDatabase.getInstance().getReference().child(Resto.class.getSimpleName())
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete "+resto.getNom() +" ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.remove(getRef(position).getKey())
                            .addOnSuccessListener(suc -> { Toast.makeText(holder.img.getContext(), "Delete Successfully", Toast.LENGTH_LONG).show(); })
                            .addOnFailureListener(err -> {Toast.makeText(holder.img.getContext(), err.getMessage(), Toast.LENGTH_LONG).show(); });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });



    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(parent.getContext(), "rak nja7t",Toast.LENGTH_LONG).show();
                  parent.getContext().startActivity(new Intent(parent.getContext(),ConsulterActivity.class));



            }
        });


        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView nom,services,location,rank;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.imageResto);
            nom=(TextView)itemView.findViewById(R.id.txtNom);
            location=(TextView)itemView.findViewById(R.id.txtLocation);
            services=(TextView)itemView.findViewById(R.id.txtServices);
            rank=(TextView)itemView.findViewById(R.id.txtRank);

            edit=(ImageView)itemView.findViewById(R.id.editbtn);
            delete=(ImageView)itemView.findViewById(R.id.deletebtn);
        }
    }
}
