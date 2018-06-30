package com.example.vignesh.afinal;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editcycler extends AppCompatActivity {


    private List<AddressBook> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddressBookAdapter mAdapter;


    private static final String NAME_KEY = "Name";
    private static final String EMAIL_KEY = "Email";
    private static final String PHONE_KEY = "Phone";
    FirebaseFirestore db;
    TextView textDisplay;
    TextView message;
    EditText name, email, phone;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcycler);
        db = FirebaseFirestore.getInstance();
        //textDisplay = findViewById(R.id.textDisplay);
        //message = findViewById(R.id.displayMessage);
        //save    = findViewById(R.id.save);


        recyclerView = findViewById(R.id.recycler_view);
        ReadSingleContact();

        mAdapter = new AddressBookAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListene(this,
                recyclerView, new ClickListene() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.title)).getText().toString();
                Toast.makeText(getApplicationContext(), title1, Toast.LENGTH_SHORT).show();
                //Toast.makeText(Main3Activity.this, "Single Click on position        :"+position,
                //      Toast.LENGTH_SHORT).show();

                final Intent intent=new Intent(Editcycler.this,Edit.class);
                DocumentReference docRef = db.collection("Journalbook").document(title1);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                                DocumentSnapshot documnet = task.getResult();
                                //StringBuilder fields = new StringBuilder("");
                                //fields.append("Name: ").append(document.get("Name"));
                                //fields.append("\nEmail: ").append(document.get("Email"));
                                //fields.append("\nPhone: ").append(document.get("Phone"));
                                //message.setText(fields.toString());
                                String title= (String) document.get("Title");
                                String date= (String) document.get("Message");
                                String mes= (String) document.get("Date");

                                intent.putExtra("title",title);
                                intent.putExtra("date",date);
                                intent.putExtra("mes",mes);

                                startActivity(intent);




                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
//                String title=String.valueOf(a);
//                String date ="254262";
//                String mes="erguhergiwberguiebrgiug";
//
//                intent.putExtra("title",title);
//                intent.putExtra("date",date);
//                intent.putExtra("mes",mes);




            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(Editcycler.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));







//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ReadSingleContact();
//            }
//        });

    }

    private void addRealtimeUpdate() {
        DocumentReference contactListener = db.collection("PhoneBook").document("Contacts");
        contactListener.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null ){
                    Log.d("ERROR", e.getMessage());
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()){
                    message.setText(documentSnapshot.getData().toString());
                }
            }
        });
    }
    private void DeleteData() {



        db.collection("PhoneBook").document("Contacts")
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(Editcycler.this, "Data deleted !",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void UpdateData() {

        DocumentReference contact = db.collection("PhoneBook").document("Contacts");
        contact.update(NAME_KEY, "Kenny");
        contact.update(EMAIL_KEY, "kenny@gmail.com");
        contact.update(PHONE_KEY, "090-911-419")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Editcycler.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void ReadSingleContact() {

//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
//        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    StringBuilder fields = new StringBuilder("");
//                    fields.append("Name: ").append(doc.get("Name"));
//                    fields.append("\nEmail: ").append(doc.get("Email"));
//                    fields.append("\nPhone: ").append(doc.get("Phone"));
//                    message.setText(fields.toString());
//
//                }
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//

        db.collection("Journalbook")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                QuerySnapshot documnet = task.getResult();
                                //StringBuilder fields = new StringBuilder("");
                                //fields.append("Name: ").append(document.get("Name"));
                                //fields.append("\nEmail: ").append(document.get("Email"));
                                //fields.append("\nPhone: ").append(document.get("Phone"));
                                //message.setText(fields.toString());
                                String a= (String) document.get("Title");
                                String b= (String) document.get("Message");
                                String c= (String) document.get("Date");

                                AddressBook movie = new AddressBook(a,b,c);
                                movieList.add(movie);
                                mAdapter.notifyDataSetChanged();


                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }



}




interface ClickListene{
    public void onClick(View view,int position);
    public void onLongClick(View view,int position);
}

class RecyclerTouchListene implements RecyclerView.OnItemTouchListener{

    private ClickListene clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListene(Context context, final RecyclerView recycleView, final ClickListene clicklistene){

        this.clicklistener=clicklistene;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}


