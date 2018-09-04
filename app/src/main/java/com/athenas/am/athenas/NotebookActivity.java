package com.athenas.am.athenas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotebookActivity extends AppCompatActivity {

    private static final String TAG = "NotebookActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText edtTextTitle;
    private EditText edtTextDescription;
    private EditText edtTextPriority;

    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        edtTextTitle = findViewById(R.id.edit_text_title);
        edtTextDescription = findViewById(R.id.edit_text_Description);
        edtTextPriority = findViewById(R.id.edit_text_priority);
        textViewData = findViewById(R.id.text_view_data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.orderBy("priority", Query.Direction.DESCENDING)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                String data= "";
                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    Note note = documentSnapshots.toObject(Note.class);
                    note.setDocumentId(documentSnapshots.getId());

                    String documentId = note.getDocumentId();
                    String title = note.getTitle();
                    String description = note.getDescription();
                    int priority = note.getPriority();
                    data+= "DocumentId"+documentId
                            +"\nTitle: "+title+"\nDescription"+description
                            +"\nPriority: "+priority+"\n\n";
                }

                textViewData.setText(data);
            }
        });
    }

    public void addNote(View v){
        String title = edtTextTitle.getText().toString();
        String description = edtTextDescription.getText().toString();
        if (edtTextPriority.length() == 0){
            edtTextPriority.setText("0");
        }
        int priority = Integer.parseInt(edtTextPriority.getText().toString());

        Note note = new Note(title, description, priority);

        notebookRef.add(note); //Adicionar feedback onSuccsessListener
    }


    public void loadNotes(View v){
       Task task1 = notebookRef.whereGreaterThan("priority",2)
               .orderBy("priority")
               .get();
       Task task2 = notebookRef.whereLessThan("priority",2)
               .orderBy("priority")
               .get();
       Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1,task2);
       allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
           @Override
           public void onSuccess(List<QuerySnapshot> querySnapshots) {
                   String data ="";

                   for(QuerySnapshot queryDocumentSnapshots: querySnapshots){
                       for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                           Note note = documentSnapshots.toObject(Note.class);
                           note.setDocumentId(documentSnapshots.getId());

                           String documentId = note.getDocumentId();
                           String title = note.getTitle();
                           String description = note.getDescription();
                           int priority = note.getPriority();
                           data+= "DocumentId"+documentId
                                   +"\nTitle: "+title+"\nDescription"+description
                                   +"\nPriority: "+priority+"\n\n";
                       }
                   }
                   textViewData.setText(data);
           }
       });
    }
}
