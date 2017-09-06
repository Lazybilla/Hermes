package c.alpha_hermes.dummy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.internal.zzbjp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import c.alpha_hermes.ChatList;
import c.alpha_hermes.CreateGroup;
import c.alpha_hermes.MainActivity;
import c.alpha_hermes.NavDrawer;
import c.alpha_hermes.R;
import c.alpha_hermes.SignUp;

import static android.Manifest.permission.READ_CONTACTS;



/**
 * A login screen that offers login via email/password.
 */


public class LoginActivity extends AppCompatActivity  {


    private EditText mPassword ;
    private EditText mEmail ;
    private ProgressDialog mProgressDialog ;
    private Button mloginButton ;
    private  FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener ;
    private static final int RC_SIGN_IN = 1;
    private DatabaseReference mRef ;

    private TextView mSignUp ;






    protected void onCreate(Bundle savedInstanceState)
    {


        mRef = FirebaseDatabase.getInstance().getReference().child("TokeenID") ;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;


        mPassword = (EditText)findViewById(R.id.password);
        mEmail = (EditText)findViewById(R.id.email);
        mloginButton = (Button)findViewById(R.id.email_sign_in_button);
        mSignUp = (TextView)findViewById(R.id.link_signup);





        mSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, SignUp.class));

            }
        });



         mloginButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {





                 try {
                     // mProgressDialog.setMessage("Signing UP ...Please Wait");
                     //   mProgressDialog.show();


                     String pass = mPassword.getText().toString();
                     final String email = mEmail.getText().toString();


                     mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {


                             if (task.isSuccessful()) {


                                 Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();


                            //   startActivity(new Intent(getApplicationContext(), CreateGroup.class));


                                 Intent iii = new Intent(getApplicationContext(),NavDrawer.class);

                                 iii.putExtra("mailwa",email);

                                  startActivity(new Intent(getApplicationContext(), NavDrawer.class));




                             } else {
                                 Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                             }


                         }
                     });

                 }
                 catch (Exception e)
                 {

                     Snackbar.make(view,"Enter Valid Entries",Snackbar.LENGTH_SHORT).setAction("Action",null).show();


                 }

                 // TODO check
                 //   startActivity(new Intent(getApplicationContext(),CreateGroup.class));

             }
         });



    }



    @Override
    public void onResume(){
        super.onResume();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;


                if(user!=null)
                {

                }
            else {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders(AuthUI.GOOGLE_PROVIDER).build(),1);
                }






            }
        };

    }







}





