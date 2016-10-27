package com.givemepass.allenwu.firenews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    private static final int REQ_INVITE = 102;
    private ListView listview;
    private ArrayList<News> list = new ArrayList<>();
    final String NEWS_DATE = "date";
    final String NEWS_PNG = "png";
    final String NEWS_TITLE = "title";
    final String NEWS_URL = "url";
    private NewsAdapter adapter;
    private static final String imgURL="https://lh3.googleusercontent.com/-CH7KoupI7uI/URquu0FF__I/AAAAAAAAAbs/R7GDmI7v_G0/s160-c/Jelly%252520Fish%2525202.jpg";
  //private AdView mAdview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        listview= (ListView) findViewById(R.id.listView);
        adapter=new NewsAdapter(this,list);

        setupFirebase();
     boolean test = false;
//boolean变量上使用.if    如下
      //  if (test) {
        //  }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQ_INVITE){

            // Get the invitation IDs of all sent messages   by  call getInvitationIds(resultCode , data)
            String[] ids=AppInviteInvitation.getInvitationIds(resultCode,data);
            for(String id: ids){

                Log.d("onActivityResult","id"+id);
            }


        }else {
            Toast.makeText(getApplicationContext(),"you click cancel ",Toast.LENGTH_SHORT).show();
        }

    }

    private void setupFirebase() {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("news");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                News  item=new News();

                item.setPng((String) dataSnapshot.child(NEWS_PNG).getValue());
                item.setTitle((String) dataSnapshot.child(NEWS_TITLE).getValue());
                item.setUrl((String) dataSnapshot.child(NEWS_URL).getValue());
                item.setDate((String) dataSnapshot.child(NEWS_DATE).getValue());

                list.add(item);
                //list.add
                adapter.notifyDataSetChanged();
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

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemClick);
    }

    private AdapterView.OnItemClickListener itemClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            NewsAdapter.ViewHolder holder= (NewsAdapter.ViewHolder) view.getTag();
            //getText to string
            String webUrl=  holder.url.getText().toString();
            Intent itent=new Intent(SignInActivity.this, DummyWebvActivity.class);
            itent.putExtra("key",webUrl);
            startActivity(itent);

            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_in,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            MainActivity.mAuth.signOut();
            loadMainActivity();
            return  true;
        }else if(item.getItemId()==R.id.action_invite){

            Intent intent = new AppInviteInvitation.IntentBuilder("Friendly chat Invite")  //it show actionbar
                    .setMessage("Pls join me for a chat")
                    .setCustomImage(Uri.parse(imgURL))
                    .setCallToActionText(getString(R.string.invitation_cta))
                    .build();
            startActivityForResult(intent,REQ_INVITE);

            return true;
        }else if(item.getItemId()==R.id.action_get){

            Intent itent=new Intent(this,StoragePicsActivity.class);
            startActivity(itent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMainActivity() {
        Intent itnent=new Intent(this,MainActivity.class);
        itnent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        itnent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(itnent);
    }

}
