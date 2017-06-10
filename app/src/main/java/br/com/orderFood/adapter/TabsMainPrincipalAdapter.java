package br.com.orderFood.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.fragment.PedidosFragment;
import br.com.orderFood.fragment.ResumoFragment;

/**
 * @author Ruan Alves
 */
public class TabsMainPrincipalAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabsMainPrincipalAdapter(FragmentManager fm, Context c) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        mFragmentList.get(position);

        if (position == 0) {
            frag = new ResumoFragment();
        } else if (position == 1) {
            frag = new PedidosFragment();
        } else {
            frag = new ResumoFragment();
        }

        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;

    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
