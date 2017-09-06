package c.alpha_hermes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chatbox_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chatbox_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class Chatbox_Fragment extends Fragment
{


     static boolean test = false;



  // UserLists
    DatabaseReference mUsersReference ;
    FirebaseListAdapter<Test> mUsserListAdapter;
    FirebaseDatabase mUserDatabase ;
    DatabaseReference mLastMessage ;


    private static final int RC_SIGN_IN  =  1 ;



// TODO APP DRAWER VARIABLES
    private Context mContext ;
    private DrawerLayout mDrawerLayout ;
    private View mDrawerView ;
    private ListView userListView ;
    private ArrayList<String> mUserArrayList ;
    private ArrayAdapter<String> mUserArrayAdapter ;



    //VIEW CONTENTS
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private ChildEventListener mChildEventListener;
    private FirebaseListAdapter<Messages> adapter;
    private DatabaseReference mAdmin;



    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener;



    private ListView listView;
    private EditText mEditText;
    private FloatingActionButton mbutton;



    private String group ,human;
    private OnFragmentInteractionListener mListener;

    private DatabaseReference mLastTime;








    public Chatbox_Fragment() {

        // Required empty public constructor
    }

   // // TODO: Rename and change types and number of parameters


    public static Chatbox_Fragment newInstance() {
        Chatbox_Fragment fragment = new Chatbox_Fragment();
        Bundle args = new Bundle();


        args.putString("GROUPY","Yadav");

        args.putString("PEOPPY","Hai");

        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_chatbox_, container, false);


        final String group = getArguments().getString("G3");

        final  String human = getArguments().getString("H3");

        final String Rick = getArguments().getString("R3");





        mLastMessage = FirebaseDatabase.getInstance().getReference().child("Last").child(group);
        mLastTime = FirebaseDatabase.getInstance().getReference().child("TIME").child(group);



        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptsView = layoutInflater.inflate(R.layout.user_name,null);
        //userListView = (ListView)v.findViewById(R.id.drawerlist);

      //  mDrawerLayout = (DrawerLayout)v.findViewById(R.id.drawer_layout);

        /*    ALERT DIALOG CODE    */


        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());

        alertdialog.setView(promptsView);

        final EditText getname = (EditText)promptsView.findViewById(R.id.userNameEdit);

        alertdialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                // Test code


                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    mUsersReference = FirebaseDatabase.getInstance().getReference().child("UTest").child(group);

                    // mUsersReference.push().setValue(new Test(userId));

                    mUsersReference.push().setValue(new Test(getname.getText().toString(), userId));
                    Intent intent = new Intent(getContext(), ChatUser.class);
                    intent.putExtra("LALABHAI", userId);


                    //Working Code
                    // Intent intent = new Intent(getActivity(),ChatUser.class);
                    //  String UserName = getname.getText().toString();
                    // mUsersReference = FirebaseDatabase.getInstance().getReference().child("UTest").child(group);
                    //   mUsersReference.push().setValue(new Test(UserName));
                    //  mUsserListAdapter.notifyDataSetChanged();

                    // intent.putExtra("RICKALL",getname.getText().toString());


                    // startActivity(intent);


            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        AlertDialog alertDialoga = alertdialog.create();

        alertDialoga.show();





        User user = new User();

        String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // TODO Create a list of uers engaged in a group

        mbutton = (FloatingActionButton) v.findViewById(R.id.messageSendButton);
        mEditText = (EditText)v.findViewById(R.id.EditMessage) ;
        listView = (ListView)v.findViewById(R.id.listView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();



        // final String name = getIntent().getStringExtra("USERNAME");



      //  Log.e("The","The name of the Group is"+group);

        try {


            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("GROUPPY").child(group);


        }
        catch (NullPointerException e )
        {


        //    mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");


        }




        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




               Messages messages = new Messages() ;

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String strDate = sdf.format(c.getTime());


                Toast.makeText(getActivity(),group,Toast.LENGTH_LONG).show();

                messages.setmSender("Zee");

                if(human==null)
                {


                  HashMap<String , String> map = new HashMap<String, String>();
                   // map.put("time",strDate);
                    map.put("mUser",mEditText.getText().toString());
                    mLastMessage.setValue(map);
                    mLastTime.setValue(new Time(strDate));


                    mDatabaseReference.push().setValue(new Messages(mEditText.getText().toString(), getname.getText().toString()));


                }

                else{


                    HashMap<String , String> map = new HashMap<String, String>();
                   // map.put("time",strDate);
                    map.put("mUser",mEditText.getText().toString());

                    mLastMessage.setValue(map);

                    mLastTime.setValue(new Time(strDate));

                  //  mLastMessage.setValue(new Time(strDate,mEditText.getText().toString()));
                    mDatabaseReference.push().setValue(new Messages(mEditText.getText().toString(),human.toString()));

                    }
              //  mDatabaseReference.push().setValue(new Messages(mEditText.getText().toString()),"IBM");

                mEditText.setText("");


            }
        });



        listView = (ListView)v.findViewById(R.id.listView);

        listView.setDivider(null);

        mAdmin = FirebaseDatabase.getInstance().getReference().child("UTest").child(group);



        adapter = new FirebaseListAdapter<Messages>(getActivity(),Messages.class,R.layout.messages,mDatabaseReference) {

            @Override
            protected void populateView(View v,final Messages model, int position) {

                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final  TextView messageText = (TextView)v.findViewById(R.id.Maaaasage);
               final TextView messsageName = (TextView)v.findViewById(R.id.Name) ;

            final  LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)messageText.getLayoutParams();
                final LinearLayout.LayoutParams bayout = (LinearLayout.LayoutParams)messsageName.getLayoutParams();





                mAdmin.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                  //      String user = dataSnapshot.child("mUser").getValue().toString();

                        try {

                            if (model.getmName().equals(getname.getText().toString())) {


                                messageText.setText(model.getmText());
                                //messageText.setBackground(getContext().getDrawable(R.drawable.chatkitbubble));
                                messageText.setBackground(getContext().getDrawable(R.drawable.jeeturahobeta));
                                layout.gravity = Gravity.RIGHT;
                                bayout.gravity = Gravity.RIGHT;

                            } else {
                                messageText.setText(model.getmText() + "--\t" + model.getmName());
                                // messageText.setBackground(getContext().getDrawable(R.drawable.chatkitbubbleout));
                                messageText.setBackground(getContext().getDrawable(R.drawable.bubble_right_gray));
                                layout.gravity = Gravity.LEFT;
                                bayout.gravity = Gravity.LEFT;
                            }

                        }catch (NullPointerException e)
                        {
                            Toast.makeText(getContext(),"Exception Invoked",Toast.LENGTH_SHORT).show();
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





               // messsageName.setBackground(getContext().getDrawable(R.drawable.bubble_right_gray));
               // messsageName.setText(model.getmName());


            }
        };




        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        return  v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name


        void onFragmentInteraction(Uri uri);
    }






}
