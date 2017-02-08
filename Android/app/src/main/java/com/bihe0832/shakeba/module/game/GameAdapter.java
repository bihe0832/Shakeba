package com.bihe0832.shakeba.module.game;

import android.content.Context;
import android.content.Intent;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.activity.MainActivity;
import com.bihe0832.shakeba.framework.adapter.base.SolidRVBaseAdapter;
import com.bihe0832.shakeba.framework.Shakeba;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/4/5
 * Time:11:34
 */
public class GameAdapter extends SolidRVBaseAdapter<GameInfo> {

    public static final String INTENT_EXTRA_KEY_GAMEID = "GAMEID";
    public static final String INTENT_EXTRA_KEY_GAMENAME = "GAMENAME";
    public static final int GAME_TYPE_DANJI = 1;
    public static final int GAME_TYPE_WANGYOU = 2;

    public GameAdapter(Context context, List<GameInfo> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.com_bihe0832_shakeba_game_item_game;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, MainActivity.class);
        GameInfo info = mBeans.get(position - 1);
        if(null != info){
            intent.putExtra(INTENT_EXTRA_KEY_GAMEID, info.getmGameId().val());
            intent.putExtra(INTENT_EXTRA_KEY_GAMENAME, info.getmGameName());
            Shakeba.getInstance().setGame(info);
            mContext.startActivity(intent);
        }
    }
    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, GameInfo bean) {
        holder.setText(R.id.game_title, bean.getmGameName());
        holder.setText(R.id.game_type, "游戏类型：" + getGameTypeStringById(bean.getmGameType()));
        if(bean.getmGameMaxPlayer() > 99){
            holder.setText(R.id.game_maxnum, "最大参与人数：无限制");
        }else{
            holder.setText(R.id.game_maxnum, "最大参与人数：" + bean.getmGameMaxPlayer() + "");
        }
        holder.setText(R.id.game_desc, "游戏简介：" + bean.getmGameDesc());
        holder.setText(R.id.game_image,bean.getGameLogoText());
    }

    private String getGameTypeStringById(int id){
        switch (id){
            case GAME_TYPE_WANGYOU:
                return "网游";
            case GAME_TYPE_DANJI:
            default:
                return "单机";
        }
    }
}
