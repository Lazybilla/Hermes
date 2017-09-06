package c.alpha_hermes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import c.alpha_hermes.dummy.LoginActivity;

public class SignUp extends AppCompatActivity {

    private TextView  mLoginTextView;
    private EditText mEmail,mPassword,mAgainPassword ;
    private Button mCreate ;
    private FirebaseAuth mAuth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mLoginTextView = (TextView)findViewById(R.id.link_login);
        mEmail = (EditText)findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        mAgainPassword = (EditText)findViewById(R.id.input_passwordAgain);
        mCreate = (Button)findViewById(R.id.SignupWA);
        mAuth = FirebaseAuth.getInstance();





        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, LoginActivity.class));


            }
        });




        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString() ;
                String password = mPassword.getText().toString() ;
                String confirmPassword = mAgainPassword.getText().toString() ;


                if(password.equals(confirmPassword))
                {


                      mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {

                              if(task.isSuccessful())
                              {

                                  Toast.makeText(getApplicationContext(),"Authentication Successful",Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(SignUp.this,LoginActivity.class));

                              }
                              else {

                                  Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();

                              }


                          }
                      });



                }
                else
                {


                    Toast.makeText(getApplicationContext(),"Password doesn't match ",Toast.LENGTH_SHORT).show();


                }






            }
        });





    }
}
