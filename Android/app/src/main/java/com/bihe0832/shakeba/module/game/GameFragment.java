package com.bihe0832.shakeba.module.game;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.fragment.base.BaseFragment;
import com.bihe0832.shakeba.framework.common.eGAME;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:54
 */
public class GameFragment extends BaseFragment{

    private static final int ACTION_INIT = 0;
    private static final int ACTION_REFLESH = 1;
    private static final int ACTION_LOAD_MORE = 2;


    private XRecyclerView mRecyclerView;
    private GameAdapter mGameAdapter;

    private int mCurrentAction = ACTION_INIT;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.com_bihe0832_shakeba_game_fragment, container, false);
    }

    @Override
    protected void initView() {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = customFindViewById(R.id.recyclerview);
        mGameAdapter = new GameAdapter(getMContext(), new ArrayList<GameInfo>());
        mRecyclerView.setAdapter(mGameAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFLESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        TextView tv_empty = new TextView(getMContext());
        tv_empty.setText("Empty");
        mRecyclerView.setEmptyView(tv_empty);
    }

    @Override
    protected void initData() {
        switchAction(ACTION_INIT);
        List<GameInfo> list =  new ArrayList<>();
        GameInfo tempItem = new GameInfo(eGAME.DICE,getString(R.string.dice_back_text),GameAdapter.GAME_TYPE_DANJI, 100,"焕然一新骰子玩法，焕然一新骰子玩法","骰" , true);
        list.add(tempItem);
        tempItem = new GameInfo(eGAME.ADVENTURE,getString(R.string.adventure_back_text),GameAdapter.GAME_TYPE_DANJI, 20,"好新鲜，好刺激","险" , true);
        list.add(tempItem);
//        tempItem = new GameInfo(eGAME.DIVINE,"每日占卜",GameAdapter.GAME_TYPE_DANJI, 20,"每天看运势",R.drawable.dice_1 , true);
//        list.add(tempItem);
//        tempItem = new GameInfo(eGAME.HOME,"做家务",GameAdapter.GAME_TYPE_DANJI, 20,"今天谁当家",R.drawable.dice_1 , true);
//        list.add(tempItem);
        tempItem = new GameInfo(eGAME.EXAM,getString(R.string.exam_back_text),GameAdapter.GAME_TYPE_DANJI, 1,"尼玛，这个怎么做","选" , true);
        list.add(tempItem);
//        tempItem = new GameInfo(eGAME.LOVER,"亲密无间",GameAdapter.GAME_TYPE_DANJI, 20,"情侣打情骂俏必备",R.drawable.dice_1 , true);
//        list.add(tempItem);
//        tempItem = new GameInfo(eGAME.STONE,"石头剪刀步",GameAdapter.GAME_TYPE_DANJI, 20,"手机也能玩",R.drawable.dice_1 , true);
//        list.add(tempItem);
//        tempItem = new GameInfo(eGAME.PLANK,"平板支撑",GameAdapter.GAME_TYPE_DANJI, 20,"看你持久么",R.drawable.dice_1 , true);
//        list.add(tempItem);
        mGameAdapter.addAll(list);
    }

    private void getData() {
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_INIT:
                mGameAdapter.clear();
                break;
            case ACTION_REFLESH:
                mGameAdapter.clear();
                initData();
                mRecyclerView.refreshComplete(); //下拉刷新完成
                break;
            case ACTION_LOAD_MORE:
                getData();
                mRecyclerView.loadMoreComplete();
                break;
        }
    }
}
