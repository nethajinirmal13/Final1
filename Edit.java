package com.example.vignesh.afinal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {


    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText e1 = (EditText) findViewById(R.id.e1);
        EditText e2 = (EditText) findViewById(R.id.e2);
        EditText e3 = (EditText) findViewById(R.id.e3);
        Button b1 = (Button) findViewById(R.id.upt);
        Button b2 =(Button)findViewById(R.id.del);
        db = FirebaseFirestore.getInstance();


        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String date = bundle.getString("date");
        String mes = bundle.getString("mes");

        e1.setText(title);
        e2.setText(date);
        e3.setText(mes);


        CollectionReference cities = db.collection("Journalbook");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }


    public void addNewContact() {
        EditText e1 = (EditText) findViewById(R.id.e1);
        EditText e2 = (EditText) findViewById(R.id.e2);
        EditText e3 = (EditText) findViewById(R.id.e3);


        CollectionReference cities = db.collection("Journalbook");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("Title", e1.getText().toString());
        data1.put("Date", e2.getText().toString());
        data1.put("Message", e3.getText().toString());
        // data1.put("capital", false);
        // data1.put("population", 860000);
        cities.document(e1.getText().toString()).set(data1);
        Toast.makeText(Edit.this, "Journal saved",
                Toast.LENGTH_SHORT).show();
    }


    public void delete(){


        EditText e1 = (EditText) findViewById(R.id.e1);
        EditText e2 = (EditText) findViewById(R.id.e2);
        EditText e3 = (EditText) findViewById(R.id.e3);


        db.collection("Journalbook").document(e1.getText().toString())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });


//        DocumentReference docRef = db.collection("Journalbook").document("infia");
//
//        // Remove the 'capital' field from the document
//        Map<String,Object> updates = new HashMap<>();
//        updates.put("Title", FieldValue.delete());
//        updates.put("Date", FieldValue.delete());
//        updates.put("Message", FieldValue.delete());
//
//        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            // [START_EXCLUDE]
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {}
//            // [START_EXCLUDE]
//        });

}}

