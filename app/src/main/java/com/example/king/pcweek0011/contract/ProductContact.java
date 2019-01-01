package com.example.king.pcweek0011.contract;

import java.util.Map;

public interface ProductContact {

    interface Ipresenter{
        void onSuccess(String s);
        void onFail(String meg);
    }

    interface IProductmodule{
        void setData(String api, Map<String,String> params,Ipresenter ipresenter);
    }

    interface IProductView{
        void onSuccess(String s);
        void onFail(String meg);
    }


}
