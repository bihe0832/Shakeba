package com.bihe0832.shakeba.module.game.exam;


import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.libware.util.Math;
import com.bihe0832.shakeba.module.game.GameCardFragment;


public class ExamFragment extends GameCardFragment {

    private String[] mResultTextArray = null;
    @Override
    protected void initView() {
        super.initView();
        mResultTextArray = getResources().getStringArray(R.array.exam_item_text_arr);
    }

    @Override
    public void initGameUI() {
        setBackText(getString(R.string.exam_back_text));
        setFrontTitleTextView("");
        setFrontContentTextView("");
    }

    @Override
    protected void restartGame(){

        int result = Math.getRandNumByLimit(0,4);

        if(null != mResultTextArray && mResultTextArray.length > result){
            setFrontTitleTextView(mResultTextArray[result]);
        }else{
            setFrontTitleTextView(getString(R.string.exam_error_text));
        }
        setFrontContentTextView("");
    }

}
