package com.bihe0832.shakeba.module.about;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.fragment.base.WebViewFragment;

public class AboutAuthorFragment extends WebViewFragment {


    @Override
    public String getLoadUrl() {
        return getString(R.string.link_author);
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
