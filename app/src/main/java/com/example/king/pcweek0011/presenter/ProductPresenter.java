package com.example.king.pcweek0011.presenter;

import com.example.king.pcweek0011.contract.ProductContact;
import com.example.king.pcweek0011.module.ProductModule;

import java.util.Map;

public class ProductPresenter {

    private ProductContact.IProductView iProductView;
    private ProductModule productModule;

    public ProductPresenter(ProductContact.IProductView iProductView) {
        this.iProductView = iProductView;
        this.productModule = new ProductModule();
    }


    public void login(String proudceApi, Map<String,String> params) {

        if (iProductView != null){
            productModule.setData(proudceApi, params, new ProductContact.Ipresenter() {
                @Override
                public void onSuccess(String s) {
                    if (iProductView != null){
                        iProductView.onSuccess(s);
                    }
                }

                @Override
                public void onFail(String meg) {
                    if (iProductView != null){
                        iProductView.onFail(meg);
                    }
                }
            });
        }
    }
}
