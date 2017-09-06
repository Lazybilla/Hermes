package c.alpha_hermes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import c.alpha_hermes.dummy.LoginActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    static String text;
    private RecyclerView mRecyclerView;
    private final int RC_SIGN_IN = 1;
    private List<User> mList = new ArrayList<>();
    // private ChatListAdapter adapter;
    private FloatingActionButton mActionButton;
    private TextView mEmailwa;




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
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Hermes");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(NavDrawer.this,CreateGroup.class));


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





//--------------New Code ------------------//



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

        mAdappyterbitch = new FirebaseRecyclerAdapter<User, ChatList.CViewHolder>(User.class,R.layout.chatlist,ChatList.CViewHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(final ChatList.CViewHolder viewHolder, User model, final int position) {


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





//-------------END------------//









































    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(NavDrawer.this, LoginActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.kreate_group) {


            startActivity(new Intent(NavDrawer.this,CreateGroup.class));


            // Handle the camera action
        } else if (id == R.id.Humre_bareyme) {

            startActivity(new Intent(NavDrawer.this,AboutUs.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {


            Toast.makeText(getApplicationContext(),"You can't Share it Bitch",Toast.LENGTH_SHORT).show();



        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, "Lazybillla@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Describe your problem");
            intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

            startActivity(Intent.createChooser(intent, "Send Email"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
