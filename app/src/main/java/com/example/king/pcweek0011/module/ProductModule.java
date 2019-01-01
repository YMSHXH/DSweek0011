package com.example.king.pcweek0011.module;

import com.example.king.pcweek0011.contract.ProductContact;
import com.example.king.pcweek0011.utils.OkHttpUtils;

import java.util.Map;

public class ProductModule implements ProductContact.IProductmodule {

    @Override
    public void setData(String api, Map<String, String> params, ProductContact.Ipresenter ipresenter) {
        OkHttpUtils.getInstance().toPost(api,params,ipresenter);
    }
}
