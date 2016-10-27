package com.givemepass.allenwu.firenews;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StoragePicsActivity extends AppCompatActivity {
    FirebaseAuth.AuthStateListener  authListener;
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerview;
    private SharedPreferences sharedPrefece;
    boolean isFirstTime;
    DatabaseReference picRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_pics);

        initActionbar();

        sharedPrefece=getSharedPreferences("file",MODE_PRIVATE);
       // createDBdata();

        mRecyclerview= (RecyclerView) findViewById(R.id.recycle_views);

        //getInstance
        mAuth=FirebaseAuth.getInstance();

        authListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                 isFirstTime = sharedPrefece.getBoolean("FIRST_TIME", true);
                    if(isFirstTime){
                        Log.d("firstitme"," work");
createDBdata(user);
                    }
                    Log.d("firstitme"," already created");
                    setUpRecyclerView(user);

                }else{


                }

            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void initActionbar() {
        ActionBar actionbar=getSupportActionBar();
        actionbar.setTitle(getString(R.string.storage_label));
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){

            onBackPressed();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDBdata(FirebaseUser user) {
        picRef = FirebaseDatabase.getInstance()
                .getReference("users").child(user.getUid()).child("pics");
        Photo photo=new Photo();
        photo.setContent("aaaaaa");
        photo.setImgvUrl("https://firebasestorage.googleapis.com/v0/b/firenews-7a852.appspot.com/o/headphones-headset-audio-technology-music-sound.jpg?alt=media&token=8af7d432-f04f-47c8-80db-782b8b285af8");
        photo.setTitle("hahahaha");
        picRef.push().setValue(photo);
        HashMap<String,Object>  map01=new HashMap<>();
        map01.put("content","bbbbbbb");
        picRef.push().updateChildren(map01);
        HashMap<String,Object>  map02=new HashMap<>();
        map02.put("content","ccccc");
        picRef.push().updateChildren(map02);

        sharedPrefece.edit().putBoolean("FIRST_TIME",false).commit();
    }

    private void setUpRecyclerView(FirebaseUser user) {
        picRef = FirebaseDatabase.getInstance()
                .getReference("users").child(user.getUid()).child("pics");

       // picRef.addValueEventListener(new ValueEventListener() {
          //  @Override
          //  public void onDataChange(DataSnapshot dataSnapshot) {
            //  for(DataSnapshot shot: dataSnapshot.getChildren()){

            //  Photo phto=    shot.getValue(Photo.class);

             // }
          //  }

          //  @Override
           // public void onCancelled(DatabaseError databaseError) {
//}});
        //Linelayout manager
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
FavFirebaseRecylerAdapter mAdapter=new FavFirebaseRecylerAdapter(Photo.class,R.layout.item_row,FavFirebaseRecylerAdapter.MyviewHolder.class,picRef,StoragePicsActivity.this);
      /*  FirebaseRecyclerAdapter<Photo,MyviewHolder> mAdapter=new FirebaseRecyclerAdapter<Photo, MyviewHolder>(Photo.class,R.layout.item_row,MyviewHolder.class,picRef) {


            @Override
            protected void populateViewHolder(MyviewHolder viewHolder, Photo model, final int position) {

                //set data  to Textview  and imageview
                viewHolder.txtTitle.setText(model.getTitle());
                viewHolder.txtContent.setText(model.getContent());
                Glide.with(StoragePicsActivity.this)
                        .load(model.getImgvUrl())
                        .placeholder(R.drawable.holder_image)
                        .into(viewHolder.image);}
        };*/

        mRecyclerview.setAdapter(mAdapter);
          mAdapter.setOnItemClickListener(new FavFirebaseRecylerAdapter.OnItemClickListener() {
              @Override
              public void onItemClick(int position, View newsType) {
                  TextView  txtTitle = (TextView) newsType.findViewById(R.id.row_photo_title);
                  Toast.makeText(getApplicationContext(),txtTitle.getText(),Toast.LENGTH_SHORT).show();
                 // Log.d("Test",position+"");
              }
          });



    }

/*static class MyviewHolder extends RecyclerView.ViewHolder  {
ImageView image;
    TextView txtTitle,txtContent;
//View mView;
 //static    onItemClickListener itemClickListener;
    public MyviewHolder(View itemView) {

        super(itemView);
        image= (ImageView) itemView.findViewById(R.id.row_photo_image);
        txtContent= (TextView) itemView.findViewById(R.id.row_photo_content);
        txtTitle= (TextView) itemView.findViewById(R.id.row_photo_title);
       // itemView.setOnClickListener(this);
        //mView=itemView;
        //itemView.setOnClickListener(this);
    }*/






    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}
