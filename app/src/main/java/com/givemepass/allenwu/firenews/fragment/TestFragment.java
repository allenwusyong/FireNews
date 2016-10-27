package com.givemepass.allenwu.firenews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.givemepass.allenwu.firenews.R;

/**
 * Created by Admin on 2016/9/21.
 */
public class TestFragment extends Fragment {

   //toast Create and show Toast Toast.makeText(context, expr, Toast.LENGTH_SHORT).show();
   // .log Log Log.d("log", expr);
   // .logd If BuildConfig.DEBUG is true, Log message. Log.d("log", expr);
 //   .find Typed FindView (ViewType) findViewById(expr);
  //  .isemp isEmpty TextUtils.isEmpty(expr);
   // .vg ? View.VISIBLE : View.GONE; (expr) ? View.VISIBLE : View.GONE;
String str;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
TextUtils.isEmpty(str);
        Toast.makeText(getActivity(), "messag", Toast.LENGTH_SHORT).show();
      Log.d("TestFragment", "oncreat");
        Log.d("TestFragment", "test");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_test,container,false);

    Button btn= (Button) view.findViewById(R.id.buttontest);
   Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
       // switch (i) {

       // }

        return super.onOptionsItemSelected(item);
    }
}
