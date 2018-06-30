package com.example.vignesh.afinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Main4Activity extends AppCompatActivity {

    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        TextView e1 = (TextView) findViewById(R.id.e1);
        TextView e2 = (TextView)findViewById(R.id.e2);
        TextView e3 = (TextView) findViewById(R.id.e3);
        //Button b1=(Button)findViewById(R.id.upt);
        db = FirebaseFirestore.getInstance();


        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String date = bundle.getString("date");
        String mes = bundle.getString("mes");

        e1.setText(title);
        e2.setText(date);
        e3.setText(mes);


//        CollectionReference cities = db.collection("Journalbook");
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addNewContact();
//            }
//        });
    }



//    public void addNewContact(){
//        EditText e1 =(EditText)findViewById(R.id.e1);
//        EditText e2 =(EditText)findViewById(R.id.e2);
//        EditText e3 =(EditText)findViewById(R.id.e3);
//
//
//
//        CollectionReference cities = db.collection("Journalbook");
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("Title", e1.getText().toString());
//        data1.put("Date", e2.getText().toString());
//        data1.put("Message", e3.getText().toString());
//        // data1.put("capital", false);
//        // data1.put("population", 860000);
//        cities.document(e1.getText().toString()).set(data1);
//        Toast.makeText(Main4Activity.this, "Journal saved",
//                Toast.LENGTH_SHORT).show();
//    }

}
