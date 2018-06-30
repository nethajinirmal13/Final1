package com.example.vignesh.afinal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Newdata extends AppCompatActivity {


    private static final String NAME_KEY = "Title";
    private static final String EMAIL_KEY = "Date";
    private static final String PHONE_KEY = "Article";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdata);
        db = FirebaseFirestore.getInstance();
        Button b1 =(Button)findViewById(R.id.save);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact();
            }
        });
    }
    private void addNewContact(){
        EditText e1 =(EditText)findViewById(R.id.title);
        EditText e2 =(EditText)findViewById(R.id.date);
        EditText e3 =(EditText)findViewById(R.id.message);

        String mName = e1.getText().toString();
        String mEmail = e2.getText().toString();
        String mPhone = e3.getText().toString();
        Map<String, Object> newContact = new HashMap<>();
        newContact.put(NAME_KEY, mName);
        newContact.put(EMAIL_KEY, mEmail);
        newContact.put(PHONE_KEY, mPhone);
//        db.collection("PhoneBook").document("Contacts").set(newContact,SetOptions.merge())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(Main3Activity.this, "User Registered",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Main3Activity.this, "ERROR" +e.toString(),
//                                Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", e.toString());
//                    }
//                });





//
//        db.collection("Jounalbook")
//                .add(newContact)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        Toast.makeText(Newdata.this, "Journal saved",
//                              Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                        Toast.makeText(Newdata.this, "Error in saving Journal",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }


        CollectionReference cities = db.collection("Journalbook");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("Title", e1.getText().toString());
        data1.put("Date", e2.getText().toString());
        data1.put("Message", e3.getText().toString());
       // data1.put("capital", false);
       // data1.put("population", 860000);
        cities.document(e1.getText().toString()).set(data1);
        Toast.makeText(Newdata.this, "Journal saved",
                             Toast.LENGTH_SHORT).show();
}



}
