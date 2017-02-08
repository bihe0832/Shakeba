package com.bihe0832.shakeba.module.update;


import com.bihe0832.shakeba.framework.common.BaseRet;
import com.bihe0832.shakeba.framework.request.HttpResponse;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.util.SafeJSONObject;

import org.json.JSONException;
import org.json.JSONObject;


public class CheckUpdateResponse extends HttpResponse {

    private static final String PARAM_VERSION = "version";
    private static final String PARAM_URL = "url";
    private static final String PARAM_PACKAGE_INFO = "package";


    public PackageUpdateInfo mPackageUpdateInfo;
    public CheckUpdateResponse() {
    	super();
    }
    
    @Override
    public void parseJson(SafeJSONObject json) {
        super.parseBaseJson(json);
        
        if(BaseRet.RET_SUCC == ret){
            parseCheckUpdateRespones(json);
        }else{
            Logger.w(json.toString());
        }
    }
    
    private void parseCheckUpdateRespones (SafeJSONObject json) {
		Logger.d("parseCheckUpdateRespones");
		if (null != json) {
            try {
                mPackageUpdateInfo = new PackageUpdateInfo();
                JSONObject tempJson = json.getJSONObject(PARAM_PACKAGE_INFO);
                mPackageUpdateInfo.pckVersion = tempJson.getString(PARAM_VERSION);
                mPackageUpdateInfo.url = tempJson.getString(PARAM_URL);
            }catch(JSONException e) {
                Logger.d("JSONException : " + json.toString());
                e.printStackTrace();
            }
		} else{
			Logger.w("parseCheckUpdateRespones json is null");
		}
    }

}
