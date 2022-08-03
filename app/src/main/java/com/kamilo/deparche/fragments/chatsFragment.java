package com.kamilo.deparche.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamilo.deparche.R;
import com.kamilo.deparche.adapter.AdapterChatLista;
import com.kamilo.deparche.pojos.Users;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public chatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chatsFragment newInstance(String param1, String param2) {
        chatsFragment fragment = new chatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ProgressBar progressBar;

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chats, container, false);




        progressBar = view.findViewById(R.id.progress_bar);

        assert user != null;

        RecyclerView rv;
        ArrayList<Users> usersArrayList;
        AdapterChatLista adapter;
        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(mLayoutManager);

        usersArrayList = new ArrayList<>();
        adapter = new AdapterChatLista(usersArrayList,getContext());

        rv.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myfer = database.getReference("User");

        myfer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    rv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    usersArrayList.removeAll(usersArrayList);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Users user = snapshot.getValue(Users.class);
                        usersArrayList.add(user);

                    }
                    adapter.notifyDataSetChanged();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "No existen usuarios", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}