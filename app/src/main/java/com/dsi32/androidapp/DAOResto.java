package com.dsi32.androidapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOResto {
     private DatabaseReference databaseReference ;

     public DAOResto() {
          FirebaseDatabase db = FirebaseDatabase.getInstance();
          databaseReference = db.getReference(Resto.class.getSimpleName());
     }

     public Task<Void> add(Resto resto){
          //if(resto ==null )
          return  databaseReference.push().setValue(resto);
     }
/*
     public Task<Void> update(String id, HashMap<String, Object> hashMap){
          //if(resto ==null )
          return  databaseReference.child(id).updateChildren(hashMap);
     }

     public Task<Void> remove(String id){
          return  databaseReference.child(id).removeValue();
     }
*/

}
