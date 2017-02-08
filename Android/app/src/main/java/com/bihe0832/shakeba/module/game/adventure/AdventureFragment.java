package com.bihe0832.shakeba.module.game.adventure;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.database.ShakebaDBHelper;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.util.Math;
import com.bihe0832.shakeba.module.game.GameCardFragment;


public class AdventureFragment extends GameCardFragment {


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void initGameUI() {
        setBackText(getString(R.string.adventure_back_text));
        setFrontTitleTextView("");
        setFrontContentTextView("");
    }

    @Override
    protected void restartGame(){
        int result = Math.getRandNumByLimit(1,3);
        AdventureItem info = getContentByType(result);
        setFrontTitleTextView(getTypeStringByID(info.getType()));
        setFrontContentTextView(info.getContent());
    }

    private AdventureItem getContentByType(int itemType){
        //TODO 读DB获取
        AdventureItem temp = new AdventureItem();
        if(ShakebaDBHelper.getInstance().isOk()){
            temp = AdventureTableModel.getAdventureItemByType();
            Logger.d(temp.toString());
        }else{
            //TODO 提示 请稍后
        }
        return temp;
    }

    public String getTypeStringByID(int id){
        if(AdventureItem.TYPE_ID_QUESTION == id){
            return getString(R.string.adventure_question_text);
        }else if(AdventureItem.TYPE_ID_TASK == id){
            return getString(R.string.adventure_task_text);
        }else{
            return getString(R.string.adventure_back_text);
        }
    }


}
