package com.kamilo.deparche.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kamilo.deparche.fragments.UserFragment;
import com.kamilo.deparche.fragments.chatsFragment;
import com.kamilo.deparche.fragments.mis_solicitudesFragment;
import com.kamilo.deparche.fragments.solicitudesFragment;

public class PaginasAdapter extends FragmentStateAdapter {

    public PaginasAdapter(@NonNull FragmentActivity fragmentActivity) {super(fragmentActivity);}

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return  new UserFragment();
            case 1 :
                return new chatsFragment();
            case 2 :
                return new solicitudesFragment();
           /* case 3:
                return new mis_solicitudesFragment();*/
            default:
                return new UserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
