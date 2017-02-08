package com.bihe0832.shakeba.module.game;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.Shakeba;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.util.TextUtils;


public abstract class GameCardFragment extends GameBaseFragment {

    private FrameLayout mFlCardBack;
    private FrameLayout mFlCardFront;
    //是否封面向上
    private boolean mIsShowBack = true;
    private boolean mIsFirstStart = true;
    private AnimatorSet mAnimSetOut;
    private AnimatorSet mAnimSetIn;

    private TextView mBackTextView;
    private TextView mFrontTitleTextView;
    private TextView mFrontContentTextView;
    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.com_bihe0832_shakeba_game_fragment_card, container, false);
    }

    @Override
    protected void initView() {
        mFlCardBack = customFindViewById(R.id.main_fl_card_back);
        mFlCardFront = customFindViewById(R.id.main_fl_card_front);
        mBackTextView = (TextView)  getContentView().findViewById(R.id.cardBackTextView);
        mFrontTitleTextView = (TextView)  getContentView().findViewById(R.id.cardFrontTitleTextView);
        mFrontContentTextView = (TextView)  getContentView().findViewById(R.id.cardFrontContentTextView);
        setCameraDistance();
        setAnimators();
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setAnimators() {
        mAnimSetOut = (AnimatorSet) AnimatorInflater.loadAnimator(Shakeba.getInstance().getApplicationContext(), R.animator.anim_out);
        mAnimSetIn = (AnimatorSet) AnimatorInflater.loadAnimator(Shakeba.getInstance().getApplicationContext(), R.animator.anim_in);
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    protected void setBackText(String text){
        if(!TextUtils.ckIsEmpty(text)){
            mBackTextView.setText(text);
        }else{
            mBackTextView.setText("");
        }

    }

    protected void setFrontTitleTextView(String text){
        if(!TextUtils.ckIsEmpty(text)){
            mFrontTitleTextView.setText(text);
        }else{
            mFrontTitleTextView.setText("");
        }
    }

    protected void setFrontContentTextView(String text){
        if(!TextUtils.ckIsEmpty(text)){
            mFrontContentTextView.setText(text);
        }else{
            mFrontContentTextView.setText("");
        }
    }

    @Override
    public void startGame() {
        if (mIsShowBack) {
            // 封面朝上
            showFront();
        } else { // 背面朝上
            if(!mIsFirstStart){
                showFront();
            }else{
                showBack();
            }
        }
    }

    private void showBack(){
        // 封面朝上
        mAnimSetOut.setTarget(mFlCardFront);
        mAnimSetOut.start();
        mAnimSetIn.setTarget(mFlCardBack);
        mAnimSetIn.start();
        mIsShowBack = true;
    }

    private void showFront(){
        mIsFirstStart = false;
        restartGame();
        mAnimSetOut.setTarget(mFlCardBack);
        mAnimSetOut.start();
        mAnimSetIn.setTarget(mFlCardFront);
        mAnimSetIn.start();
        mIsShowBack = false;
    }
}
