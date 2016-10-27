package com.givemepass.allenwu.firenews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    //referenced by    http://litotom.com/2016/08/02/firebase-news-glide/
    private AdView mAdview;
    private static final String TAG ="MainActivity" ;
    private EditText edtEmail,edtPass;
    public ProgressDialog mProgressDialog;
    private Button buttonSignUp;

    private CheckBox checkRemberData;
    private SharedPreferences sharedPreferences;
    //********
    static FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener  authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("filePassId",MODE_PRIVATE);


// Initialize and request AdMob ad.
        // Initialize the Mobile Ads SDK.
      //  MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mAdview = (AdView) findViewById(R.id.adView);

        AdRequest request=new AdRequest.Builder().build();
        mAdview.loadAd(request);

        mAuth=FirebaseAuth.getInstance();
        authListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user==null){
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }else {
                    Log.d(TAG, "onAuthStateChanged:signed In");

                }
            }
        };

        if(mAuth.getCurrentUser()!=null){

            Intent it=new Intent(this,SignInActivity.class);
            startActivity(it);
            finish();
            return;
        }
        checkRemberData= (CheckBox) findViewById(R.id.checkBox_rember);
        edtEmail= (EditText) findViewById(R.id.field_email);
        edtPass= (EditText) findViewById(R.id.field_password);

        findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signin Method
                if(edtEmail.getText().toString().isEmpty()|| edtPass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"not null",Toast.LENGTH_SHORT).show();
                }else {
                    signIn(edtEmail.getText().toString(), edtPass.getText().toString());}
            }
        });
        buttonSignUp= (Button) findViewById(R.id.button_sign_up);
        buttonSignUp.setOnClickListener(signUpListener);


     //   if (getIntent().getExtras() != null)
          //  for (String key : getIntent().getExtras().keySet()) {
             //   String value = getIntent().getExtras().getString(key);
              //  Log.d(TAG, "Key: " + key + " Value: " + value);
           // }
        retoreSharedData();
   checkRemberData.setOnCheckedChangeListener(checkedChangeListen);
    }

    private void retoreSharedData() {
     String email=   sharedPreferences.getString("email","");
        String password=   sharedPreferences.getString("pass","");
        edtPass.setText(password);
        edtEmail.setText(email);
    }


    private CompoundButton.OnCheckedChangeListener checkedChangeListen=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            if(isChecked){
                sharedPreferences.edit()
                        .putString("pass",edtPass.getText().toString())

                        .putString("email",edtEmail.getText().toString())
                        .commit();

            }else {

                sharedPreferences.edit()
                        .putString("pass","")
                        .putString("email","")
                        .commit();
            }

        }
    };

    private View.OnClickListener signUpListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createAccount(edtEmail.getText().toString(),edtPass.getText().toString());
        }
    };

    private void createAccount(String email, String pass) {
        if(email.isEmpty()|| pass.isEmpty()){
            Toast.makeText(this, "not null ,must be keyed in", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "註冊失敗 請重新填入", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Success Sign Up", Toast.LENGTH_SHORT).show();

                }
                hideProgressDialog();

            }
        });

    }

    private void signIn(String s, String s1) {
//show progress Dialog
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(s,s1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    // Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(getApplicationContext(), "auth fail",
                            Toast.LENGTH_SHORT).show();
                }else {

                    if(checkRemberData.isChecked()){
                        sharedPreferences.edit()
                                .putString("pass",edtPass.getText().toString())

                                .putString("email",edtEmail.getText().toString())
                                .commit();

                    }

                    Intent itent=new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(itent);
                    finish();
                }

                hideProgressDialog();
                //hideprogress

                //Intent it ;
            }
        });
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {

        mProgressDialog.dismiss();
    }
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authListener);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdview != null) {
            mAdview.resume();
        }
        // Get token
      //  String token = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
//Log.d(TAG,token);
    }

    @Override
    protected void onPause() {
        if (mAdview != null) {
            mAdview.pause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(authListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAdview!=null){
            mAdview.destroy();
        }

        if(!checkRemberData.isChecked()){
            sharedPreferences.edit().putString("email","")
                    .putString("pass","")
                    .commit();
        }
    }

}
