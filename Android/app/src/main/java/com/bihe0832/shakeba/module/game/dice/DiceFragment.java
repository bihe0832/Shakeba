package com.bihe0832.shakeba.module.game.dice;

import android.graphics.Rect;
import android.net.Uri;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.util.Math;
import com.bihe0832.shakeba.module.game.GameBaseFragment;

import java.util.ArrayList;
import java.util.Collections;

public class DiceFragment extends GameBaseFragment {

    private boolean mIsLocked = false;
    private boolean mIsSort = false;
    private boolean mIsPreGame = true;
    private ArrayList<Integer> mDiceResultList = null;
    private ArrayList<Integer> mDiceSortResultList =  null;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.com_bihe0832_shakeba_game_fragment_dice, container, false);
    }

    @Override
    protected void initView() {
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

    @Override
    public void startGame() {
        if(!mIsLocked){
            Logger.d("startGame start");
            mIsPreGame = true;
            restartGame();
            resetGameConfig();
            // 获取骰子数量
            SeekBar mSeekBarDef = (SeekBar) getContentView().findViewById(R.id.diceSeekBar);
            int num = mSeekBarDef.getProgress() + 1;

            //摇出骰子点数
            mDiceResultList = Math.getRandNumByLimitAndNum(1, 6, num);
            mDiceSortResultList =  new ArrayList<Integer>();
            for(int result : mDiceResultList){
                mDiceSortResultList.add(result);
            }
            Collections.sort(mDiceSortResultList);

            startAnimation();

            Logger.d("startGame end");
        }

    }

    @Override
    public void initGameUI() {
        mIsPreGame = true;
        mIsLocked = false;
        mIsSort = true;
        initDiceResultUI();

        SeekBar diceSeekbar = (SeekBar) getContentView().findViewById(R.id.diceSeekBar);
        diceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TextView numTextView = (TextView)  getContentView().findViewById(R.id.diceTextViewSeekBarNum);
                numTextView.setText(String.format(getString(R.string.dice_seekbar_stop), String.valueOf(seekBar.getProgress() + 1)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                TextView numTextView = (TextView)  getContentView()
                        .findViewById(R.id.diceTextViewSeekBarNum);
                numTextView.setText(String.format(getString(R.string.dice_seekbar_start), String.valueOf(seekBar.getProgress() + 1)));
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                TextView numTextView = (TextView)  getContentView()
                        .findViewById(R.id.diceTextViewSeekBarNum);
                numTextView.setText(String.format(getString(R.string.dice_seekbar_changed), String.valueOf(seekBar.getProgress() + 1)));
                mIsLocked = false;
                showLockButton();
            }
        });

        TextView numTextView = (TextView)  getContentView()
                .findViewById(R.id.diceTextViewSeekBarNum);
        numTextView.setText(String.format(getString(R.string.dice_seekbar_stop), String.valueOf(diceSeekbar.getProgress() + 1)));

        final CheckBox lockCheckbox = (CheckBox) getContentView().findViewById(R.id.diceCheckBoxLock);
        lockCheckbox.setChecked(mIsLocked);

        lockCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean locked) {
                if(!mIsPreGame){
                    mIsLocked = locked;
                    showLockButton();
                }
            }
        });
        final Button lockDiceBtn = (Button) getContentView().findViewById(R.id.diceBtnLock);
        lockDiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsLocked = !mIsLocked;
                showLockButton();
            }
        });

        final CheckBox sortCheckBox = (CheckBox) getContentView().findViewById(R.id.diceCheckBoxSort);
        sortCheckBox.setChecked(mIsSort);

        sortCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean sort) {
                mIsSort = sort;
                showDiceResult();
            }
        });
        final Button sortDiceBtn = (Button) getContentView().findViewById(R.id.diceBtnSort);
        sortDiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsSort = !mIsSort;
                showDiceResult();
            }
        });
    }

    private void startAnimation(){

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）

        TranslateAnimation mAnimation = new TranslateAnimation(-30f * density,30f * density, 0, 0);
        mAnimation.setDuration(100);//设置动画持续时间
        mAnimation.setRepeatCount(5);
        mAnimation.setRepeatMode(Animation.REVERSE);
        ImageView barrleView = (ImageView) getContentView().findViewById(R.id.diceBarrleImg);

        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Logger.d("onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Logger.d("onAnimationEnd");
                //展示UI
                mIsPreGame = false;
                showDiceResult();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        barrleView.setAnimation(mAnimation);
        barrleView.startAnimation(mAnimation);
        barrleView.invalidate();
    }

    @Override
    protected void restartGame(){
        mIsLocked = false;
        initDiceResultUI();
        showBarrleLayout();
    }

    private void showDiceResult(){
        if(!mIsPreGame){
            hideBarrleLayout();
            initDiceResultUI();
            if(mIsSort){
                showDice(mDiceSortResultList);
            }else{
                showDice(mDiceResultList);
            }
            showLockButton();
            showSortButton();
        }
    }

    private void hideBarrleLayout(){
        LinearLayout diceBackLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutPre);
        diceBackLayout.setVisibility(View.GONE);
    }

    private void showBarrleLayout(){
        LinearLayout diceBackLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutPre);
        diceBackLayout.setVisibility(View.VISIBLE);
    }


    private void resetGameConfig(){
        final CheckBox lockCheckbox = (CheckBox)getContentView().findViewById(R.id.diceCheckBoxLock);
        mIsLocked = lockCheckbox.isChecked();

        final CheckBox sortCheckBox = (CheckBox)getContentView().findViewById(R.id.diceCheckBoxSort);
        mIsSort = sortCheckBox.isChecked();

        mIsPreGame = false;

        Logger.d("mIsLocked:"+mIsLocked + ";mIsSort:" + mIsSort +";mIsPreGame:" + mIsPreGame);
    }

    private void showDice(ArrayList<Integer> resultList){

        if(resultList.size() > 4){
            showMoreThanFourDice(resultList);
        }else{
            showLessThanFourDice(resultList);
        }
    }

    private void initDiceResultUI(){
        //清除上一次的结果
        LinearLayout diceTopLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutTop);
        diceTopLayout.removeAllViews();
        diceTopLayout.setVisibility(View.GONE);

        LinearLayout diceBottomLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutBottom);
        diceBottomLayout.removeAllViews();
        diceBottomLayout.setVisibility(View.GONE);

        TextView numTextView = (TextView) getContentView()
                .findViewById(R.id.diceTextViewDiceNumSum);
        numTextView.setText(getString(R.string.dice_tips_dice_result) + 0);
        numTextView.setVisibility(View.GONE);

        hideLockButton();
        hideSortButton();
    }

    private void showLessThanFourDice(ArrayList<Integer> resultList){
        if(resultList.size() > 4){
            showMoreThanFourDice(resultList);
            return;
        }
        int diceWidth = getDiceWithAndPosition(resultList.size());
        int diceNumSum = 0;

        LinearLayout diceTopLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutTop);
        diceTopLayout.setVisibility(View.VISIBLE);
        for(int result : resultList){
            ImageView img = new ImageView(getActivity());
            LinearLayout.LayoutParams
                    tempParams = new LinearLayout.LayoutParams(diceWidth,diceWidth);
            img.setLayoutParams(tempParams);
            img.setImageURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/drawable/dice_" + result));
            diceTopLayout.addView(img);
            diceNumSum +=result;
        }
        showTotalDice(diceNumSum);
    }


    private void showMoreThanFourDice(ArrayList<Integer> resultList){
        if(resultList.size() < 5){
            showLessThanFourDice(resultList);
            return;
        }
        int diceWidth = getDiceWithAndPosition(resultList.size());

        int diceNumSum = 0;
        int topNum = 0;
        LinearLayout diceTopLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutTop);
        diceTopLayout.setVisibility(View.VISIBLE);
        LinearLayout diceBottomLayout = (LinearLayout) getContentView().findViewById(R.id.diceImgLayoutBottom);
        diceBottomLayout.setVisibility(View.VISIBLE);
        for(int result : resultList){
            if(topNum  < resultList.size() / 2){
                ImageView img = new ImageView(getActivity());
                LinearLayout.LayoutParams
                        tempParams = new LinearLayout.LayoutParams(diceWidth,diceWidth);
                img.setLayoutParams(tempParams);
                img.setImageURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/drawable/dice_"+result));
                diceTopLayout.addView(img);
            }else{
                ImageView img = new ImageView(getActivity());
                LinearLayout.LayoutParams
                        tempParams = new LinearLayout.LayoutParams(diceWidth,diceWidth);
                img.setLayoutParams(tempParams);
                img.setImageURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/drawable/dice_"+result));
                diceBottomLayout.addView(img);
            }
            topNum++;
            diceNumSum +=result;
        }
        showTotalDice(diceNumSum);

    }

    private void showTotalDice(int diceNumSum){
        TextView numTextView = (TextView) getContentView()
                .findViewById(R.id.diceTextViewDiceNumSum);
        numTextView.setText(getString(R.string.dice_tips_dice_result) + diceNumSum);
        numTextView.setVisibility(View.VISIBLE);
    }



    private void showLockButton(){
        final Button lockDiceBtn = (Button)getContentView().findViewById(R.id.diceBtnLock);
        if(mIsLocked){
            lockDiceBtn.setText(getString(R.string.dice_btn_lock_unlock));
        }else{
            lockDiceBtn.setText(getString(R.string.dice_btn_lock_lock));
        }
        if(!mIsPreGame){
            lockDiceBtn.setVisibility(View.VISIBLE);
        }
    }

    private void hideLockButton(){

        final Button lockDiceBtn = (Button)getContentView().findViewById(R.id.diceBtnLock);
        lockDiceBtn.setVisibility(View.GONE);

    }

    private void showSortButton(){

        final Button sortDiceBtn = (Button)getContentView().findViewById(R.id.diceBtnSort);
        Logger.d("mIsSort:" +mIsSort);
        if(mIsSort){
            sortDiceBtn.setText(getString(R.string.dice_btn_sort_unsort));
        }else{
            sortDiceBtn.setText(getString(R.string.dice_btn_sort_sort));
        }
        if(!mIsPreGame){
            sortDiceBtn.setVisibility(View.VISIBLE);
        }
    }

    private void hideSortButton(){

        Button sortDiceBtn = (Button)getContentView().findViewById(R.id.diceBtnSort);
        sortDiceBtn.setVisibility(View.GONE);

    }

    private int getDiceWithAndPosition(int diceNum){

        View tempView = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        Rect rect = new Rect();
        tempView.getDrawingRect(rect);
        int diceWidth  = 0;
        if(diceNum > 4){
            diceWidth = rect.width() / 6;
        }else{
            diceWidth = rect.width() / (diceNum + 2);
        }
        return diceWidth;
    }
}
