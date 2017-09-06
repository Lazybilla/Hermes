package c.alpha_hermes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUser extends AppCompatActivity {


    private static  String mNAAM;
       private ArrayList<String> mArrayList;
       private ArrayAdapter<String> mArrayAdapter;
       private ListView mListView;
       private DatabaseReference mDatabaseReference;
       private FirebaseAuth mAuth;
       private DatabaseReference mDeleteRefernce;
       private FirebaseListAdapter<Test> mFirebaseListAdapter;
       private DatabaseReference mAdmin;
       private ArrayList<String> mUserList;
       private ImageView mGroupPhoto;
       private StorageReference  mStorageReference;
       private DatabaseReference mref;
       private FloatingActionButton DeleteGroup;
       private DatabaseReference ChangeImageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);


        mGroupPhoto = (ImageView)findViewById(R.id.groupPhoto);
        DeleteGroup = (FloatingActionButton)findViewById(R.id.DELETE);



      //  StickyView = (TextView) findViewById(R.id.stickyView);


        final String NAAM = getIntent().getStringExtra("LALABHAI");


        final String group = getIntent().getStringExtra("GROUP");

        String human = getIntent().getStringExtra("RICKALL");

        mListView = (ListView)findViewById(R.id.userslist);

         mArrayList = new ArrayList<>();
         mUserList =  new ArrayList<>();


        // Database References
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UTest").child(group);
        mDeleteRefernce = FirebaseDatabase.getInstance().getReference();





        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)findViewById(R.id.twitter);
        collapsingToolbar.setTitle(group);






        mFirebaseListAdapter = new FirebaseListAdapter<Test>(this,Test.class,R.layout.firebaseusers,mDatabaseReference) {
            @Override
            protected void populateView(View v, Test model, int position) {


                TextView Name = (TextView)v.findViewById(R.id.NAAM);


                Name.setText(model.getmUser());





            }
        } ;


        mListView.setAdapter(mFirebaseListAdapter);
        mFirebaseListAdapter.notifyDataSetChanged();




         final  String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();




        // Working code Don't Alter
       mDatabaseReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                   String UserId = dataSnapshot.child("uuid").getValue().toString();
                   String UserName = dataSnapshot.child("mUser").getValue().toString();
                // String admin = dataSnapshot.child("admin").getValue().toString();





                   mArrayList.add(UserId);
                   mUserList.add(UserName);

                   mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                             final  String user = mArrayList.get(i);
                             final  String naam = mUserList.get(i);


                           mAdmin = FirebaseDatabase.getInstance().getReference().child("UAdmin").child(group);

                           mAdmin.addChildEventListener(new ChildEventListener() {
                               @Override
                               public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                   String user = dataSnapshot.child("admin").getValue().toString();



                                   if(user.equals(ID))
                                   {



                                       //Obligatory Blank Space



                                    }
                                   else
                                   {
                                       Toast.makeText(ChatUser.this,"You are not an Admin",Toast.LENGTH_SHORT).show();

                                   }
                               }

                               @Override
                               public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                               }

                               @Override
                               public void onChildRemoved(DataSnapshot dataSnapshot) {

                               }

                               @Override
                               public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                               }
                           });


                               Query query = mAdmin.orderByChild("admin").equalTo(ID);
                               query.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {

                                   for(DataSnapshot dada: dataSnapshot.getChildren()) {


                                       final Query delete = mDeleteRefernce.child("USERS").child(user).orderByChild("mName").equalTo(group);

                                       delete.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {


                                               for (DataSnapshot data : dataSnapshot.getChildren()) {


                                                   data.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful()) {
                                                               Toast.makeText(ChatUser.this, "Removed From the Group", Toast.LENGTH_SHORT).show();
                                                           }
                                                           else
                                                           {
                                                               Toast.makeText(ChatUser.this,"You're Not an Admin",Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   });
                                               }
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {
                                               Toast.makeText(ChatUser.this,"You're not an Admin",Toast.LENGTH_SHORT).show();

                                           }
                                       });



                                       Query deleteName = mDeleteRefernce.child("UTest").child(group).orderByChild("mUser").equalTo(naam);

                                       deleteName.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {

                                               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                   dataSnapshot1.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {

                                                          // Toast.makeText(ChatUser.this, "LAAAAALA", Toast.LENGTH_SHORT).show();

                                                       }
                                                   });
                                               }


                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {
                                               Toast.makeText(ChatUser.this,"You're not an Admin",Toast.LENGTH_SHORT).show();

                                           }
                                       });


                                   }
                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                                   Toast.makeText(ChatUser.this,"You're not an Admin",Toast.LENGTH_SHORT).show();

                               }
                           });

                           //     mFirebaseListAdapter.notifyDataSetChanged();


                       }
                   });



           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });




        mGroupPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(ChatUser.this);
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(ChatUser.this);




            }
        });





        ChangeImageRef = FirebaseDatabase.getInstance().getReference().child("UPhoto").child(group);

        ChangeImageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String img = dataSnapshot.getValue().toString();


                Picasso.with(ChatUser.this).load(img).into(mGroupPhoto);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




         DeleteGroup.setOnClickListener(new View.OnClickListener() {
          @Override
     public void onClick(View view) {


              startActivity(new Intent(ChatUser.this,NavDrawer.class));





          }
});



    }


    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data) ;


//        mStorageReference = FirebaseStorage.getInstance().getReference() ;

        final String group = getIntent().getStringExtra("GROUP");




        mref = FirebaseDatabase.getInstance().getReference().child("UPhoto").child(group);




        if(requestcode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult actresult = CropImage.getActivityResult(data);


            if(resultcode == RESULT_OK)
            {
                Uri uri = actresult.getUri();


                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();


                final StorageReference file = FirebaseStorage.getInstance().getReference().child("groupPhoto").child(group+".jpg");

                file.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {

                          final String imgUrl = task.getResult().getDownloadUrl().toString();


                            mref.child("image").setValue(imgUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    Toast.makeText(ChatUser.this,"Done Bitch",Toast.LENGTH_SHORT).show();


                                }
                            });





                        //    Toast.makeText(ChatUser.this,"Lady Bug",Toast.LENGTH_SHORT).show();



                        }
                    }
                });





            }





        }



    }



    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }




}



