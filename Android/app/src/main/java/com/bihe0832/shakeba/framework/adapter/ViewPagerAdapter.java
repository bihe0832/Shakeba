package com.bihe0832.shakeba.framework.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.Shakeba;
import com.bihe0832.shakeba.module.game.dice.DiceFragment;
import com.bihe0832.shakeba.module.game.GameFragment;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:48
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    private final List<String> mTitleList;
    private final Context mContext;

    public ViewPagerAdapter(Context context, List<String> titles, FragmentManager fm) {
        super(fm);
        mContext = context;
        mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = createFrgmentByTitle(mTitleList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    private Fragment createFrgmentByTitle(String title) {
        //默认的Fragment
        Fragment result = new GameFragment();
        if (title.equals( getString(R.string.fragment_name_select_game))) {
            result = new GameFragment();
        }else if(title.equals( getString(R.string.fragment_name_game_rule))){
            result = new DiceFragment();
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }


    private String getString(int id){
        return Shakeba.getInstance().getApplicationContext().getString(id);
    }
}
