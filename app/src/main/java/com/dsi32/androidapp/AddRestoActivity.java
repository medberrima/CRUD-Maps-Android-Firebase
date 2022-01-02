package com.dsi32.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.List;

public class AddRestoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresto);

        // initializing all our variables.
        final EditText nom = findViewById(R.id.nom);
        final EditText location = findViewById(R.id.location);
        final EditText services = findViewById(R.id.services);
        final EditText rank = findViewById(R.id.rank);
        final EditText description = findViewById(R.id.description);
        final EditText imageResto = findViewById(R.id.imgLink);
        Button addRestoBtn = findViewById(R.id.add_btn);
        DAOResto dao = new DAOResto();



        addRestoBtn.setOnClickListener(v -> {

            if(  nom.getText().toString().isEmpty()){ nom.setError("nom is required !");   nom.requestFocus();    return ;   }
            if(  location.getText().toString().isEmpty()){ location.setError("location is required !");   location.requestFocus();    return ;   }
            if(  services.getText().toString().isEmpty()){ services.setError("services is required !");   services.requestFocus();    return ;   }
            if(  rank.getText().toString().isEmpty()){ rank.setError("rank is required !");   rank.requestFocus();    return ;   }
            if( Integer.parseInt(rank.getText().toString()) > 5 || Integer.parseInt(rank.getText().toString()) < 1){ rank.setError("Rank should be between 1 and 5 !");   rank.requestFocus();    return ;   }
            if(  imageResto.getText().toString().isEmpty()){ imageResto.setError("imageResto is required !");   imageResto.requestFocus();    return ;   }
            if(  description.getText().toString().isEmpty()){ description.setError("description is required !");   description.requestFocus();    return ;   }

            Resto r = new Resto(
                    nom.getText().toString(),
                    location.getText().toString(),
                    services.getText().toString(),
                    Integer.parseInt(rank.getText().toString()),
                    description.getText().toString(),
                    imageResto.getText().toString()
            );

            dao.add(r)
                .addOnSuccessListener(suc -> {
                    Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddRestoActivity.this, MainActivity.class);
                    startActivity(i);
                })
                .addOnFailureListener(err -> {Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_LONG).show(); });
        });
    }
}