package com.bihe0832.shakeba.module.game;

import android.widget.Toast;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.Shakeba;
import com.bihe0832.shakeba.framework.fragment.base.BaseFragment;
import com.bihe0832.shakeba.libware.ui.ToastUtil;

public abstract class GameBaseFragment extends BaseFragment {

    @Override
    protected void initView() {
        ToastUtil.show(getActivity(), String.format( getString(R.string.game_start_toast), Shakeba.getInstance().getGame().getmGameName()), Toast.LENGTH_LONG);
        initGameUI();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract void startGame();

    protected abstract void initGameUI();

    protected abstract void restartGame();

}
