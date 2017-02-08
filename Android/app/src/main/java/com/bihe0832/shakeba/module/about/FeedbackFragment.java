package com.bihe0832.shakeba.module.about;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.fragment.base.WebViewFragment;

public class FeedbackFragment extends WebViewFragment {

    @Override
    public String getLoadUrl() {
        return getString(R.string.link_feedback);
    }
}
