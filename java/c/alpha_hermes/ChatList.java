package c.alpha_hermes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


// It's Just Sloppy CraftmanShip

public class ChatList extends AppCompatActivity {


    static String text;

    private RecyclerView mRecyclerView;
    private final int RC_SIGN_IN = 1;
    private List<User> mList = new ArrayList<>();
    // private ChatListAdapter adapter;
    private FloatingActionButton mActionButton;



    // private FirebaseListAdapter<User> mFirebaseListAdapter;
    // private FirebaseRecyclerAdapter<User, CViewHolder> mAdappyterbitch;

    private FirebaseRecyclerAdapter mAdappyterbitch ;

    private List<User> listbitch;
    private Dapter dicktapter;
    private DatabaseReference swap;


    //   private ArrayAdapter<String> mAdapter ;
    private ArrayList<User> mArrayList;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;


    private RecyclerView mListView;


    private FirebaseAuth mAuth;
    private FirebaseUser mFireUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mImagesReferences;
    private ArrayList<String> mImage;
    private ArrayList<String> mName;
    private DatabaseReference mLast,mDataChange;
    private DatabaseReference mTime;




    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);



        swap = FirebaseDatabase.getInstance().getReference();


        //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mListView = (RecyclerView) findViewById(R.id.ChatDialogListView);
        mListView.setHasFixedSize(true);
        mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        // mListView = (RecyclerView) findViewById(R.id.ChatDialogListView);


        final String mGroupUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mAuth = FirebaseAuth.getInstance();

        final String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //  listbitch = new ArrayList<>();

        mArrayList = new ArrayList<User>();
        //  mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mArrayList);
        // mListView.setAdapter(mAdapter);

        // dicktapter = new Dapter(this, R.layout.chatlist, mArrayList);

        //  mListView.setAdapter(dicktapter);

        final String name = getIntent().getStringExtra("GROUPNAME");

        mName = new ArrayList<>();
        mImage = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(mUser);

        mDatabaseReference.keepSynced(true);






        final LinearLayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        mListView.setLayoutManager(layout);

        mAdappyterbitch = new FirebaseRecyclerAdapter<User, CViewHolder>(User.class,R.layout.chatlist,CViewHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(final CViewHolder viewHolder, User model, final int position) {


                viewHolder.setmGroupName(model.getmName());




                mImagesReferences = FirebaseDatabase.getInstance().getReference().child("UPhoto").child(model.getmName());
                mLast = FirebaseDatabase.getInstance().getReference().child("Last").child(model.getmName()) ;
                mTime = FirebaseDatabase.getInstance().getReference().child("TIME").child(model.getmName()) ;
                mDataChange = FirebaseDatabase.getInstance().getReference().child("Last") ;


                mDataChange.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {

                            notifyItemMoved(viewHolder.getAdapterPosition(),0);

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                mImagesReferences.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String url = dataSnapshot.getValue().toString();

                        viewHolder.setPhoto(url);

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



                mTime.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        String lala = dataSnapshot.getValue().toString();

                        viewHolder.setLastTime(lala);





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




                mLast.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String time = dataSnapshot.getValue().toString();
                        viewHolder.setLastMessage(time);



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



                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        String naam = viewHolder.mGroupName.getText().toString();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("messy",naam);
                        startActivity(intent);




                    }
                });






            }
        };






        mListView.setAdapter(mAdappyterbitch);
        mAdappyterbitch.notifyDataSetChanged();




        mDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mDatabase.getReference().child(mUser);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ChatList.this, CreateGroup.class);

                startActivity(i);

            }
        });

        /*

        swap = FirebaseDatabase.getInstance().getReference();
        swap.child("Last").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             mAdappyterbitch.notifyItemMoved(2,0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mAdappyterbitch.notifyItemMoved(2,0);


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


  */


















    }






    public void change(int position)
    {

        mAdappyterbitch.notifyItemMoved(2,0);


    }



    @Override
    protected void onPause() {
        super.onPause();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }




    private void swap(int i)
    {

        mAdappyterbitch.notifyItemMoved(i,0);


    }





    public static   class CViewHolder extends RecyclerView.ViewHolder
    {

        TextView mMessage ;
        TextView mLastTime ;
        CircleImageView mPhoto ;
        TextView mGroupName ;
        //Context context = this;

        View mView ;

        public CViewHolder(View itemView)
        {


            super(itemView);




            mView = itemView;



        }

        public void setLastTime(String name)
        {

            mLastTime = (TextView)itemView.findViewById(R.id.timebitch);

            mLastTime.setText(name);



        }

        public  void setPhoto(String name)
        {

            mPhoto = (CircleImageView)itemView.findViewById(R.id.thumbnail);

            Context cont=mView.getContext();
            Picasso.with(cont).load(name).into(mPhoto);




        }


        public  void setmGroupName(String name)
        {

            mGroupName = (TextView)itemView.findViewById(R.id.ZeeshanChatRoom);
            mGroupName.setText(name);


        }

        public void setLastMessage(String name)
        {

            mMessage = (TextView)itemView.findViewById(R.id.aakhiri);
            mMessage.setText(name);


        }




    }


}



