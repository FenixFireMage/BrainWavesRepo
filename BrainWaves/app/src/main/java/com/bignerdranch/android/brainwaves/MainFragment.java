package com.bignerdranch.android.brainwaves;


import android.app.Fragment;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView myTextView;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);
//        /* For devices equal or higher than lollipop set the translation above everything else */
        photoHeader.setTranslationZ(6);
//        /* Redraw the view to show the translation */
        photoHeader.invalidate();

        return view;
    }


    /*@Override
    public void onClick(View v) {
        firebaseAuth.signOut();
        Intent main = new Intent(getActivity(), LoginActivity.class);
        SharedPreferences prefs = getActivity().getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LOGIN_ID", null);
        editor.putString("LOGIN_PWD", null);
        editor.commit();
        startActivity(main);
    }*/
}
