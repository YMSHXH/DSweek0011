package com.example.king.pcweek0011.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.king.pcweek0011.R;
import com.example.king.pcweek0011.XrecyclerViewAdapter;
import com.example.king.pcweek0011.api.ProductApi;
import com.example.king.pcweek0011.beans.ProductBean;
import com.example.king.pcweek0011.contract.ProductContact;
import com.example.king.pcweek0011.presenter.ProductPresenter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ProductContact.IProductView {

    private int page = 1;
    private EditText mEdProductName;
    private Button mBtnFind;
    private XRecyclerView mXRecyclerView;
    private ProductPresenter productPresenter;
    private XrecyclerViewAdapter xrecyclerViewAdapter;
    private Map<String, String> params;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ininData();
    }

    private void ininData() {
        productPresenter = new ProductPresenter(this);
        //设置适配器
        xrecyclerViewAdapter = new XrecyclerViewAdapter(MainActivity.this);
        mXRecyclerView.setAdapter(xrecyclerViewAdapter);
        page = 1;
        params = new HashMap<>();
        productName = "手机";
        toLogin(productName,page);

        xrecyclerViewAdapter.setItemListener(new XrecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClickListener(int postion, View view) {
                Toast.makeText(MainActivity.this,"点击"+postion,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClickListener(int postion, View view) {
                Toast.makeText(MainActivity.this,"长按"+postion,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toLogin(String name, int page) {
        params.put("keywords",name);
        params.put("page",page + "");
        //设置数据
        productPresenter.login(ProductApi.PROUDCE_API, params);
    }

    private void initView() {
        mEdProductName = findViewById(R.id.ed_product_name);
        mBtnFind =  findViewById(R.id.btn_find);
        mXRecyclerView = findViewById(R.id.xRecyclerView);

        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFind();
            }
        });

        //开启加载刷新
        mXRecyclerView.setLoadingMoreEnabled(true);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                toLogin(productName,page);
            }

            @Override
            public void onLoadMore() {
                toLogin(productName,page);
            }
        });
    }

    /**
     * mBtnFind点击事件
     */
    private void toFind() {
        productName = mEdProductName.getText().toString();
        page = 1;
        toLogin(productName,page);
    }

    @Override
    public void onSuccess(String s) {
        ProductBean productBean = new Gson().fromJson(s,ProductBean.class);
        String msg = productBean.getMsg();
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
        List<ProductBean.DataBean> list = productBean.getData();
        if ("查询成功".equals(msg)) {
            setAdapterData(list);
        }
    }

    /**
     * 设置适配器的数据
     * 是刷新 还是加载
     * @param list
     */
    private void setAdapterData(List<ProductBean.DataBean> list) {
        //判断数据是否为空
        if (list == null){
            Toast.makeText(MainActivity.this,"数据为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (page ==1){
            xrecyclerViewAdapter.setList(list);
        } else {
            xrecyclerViewAdapter.addList(list);
        }
        page++;
        mXRecyclerView.refreshComplete();
    }

    @Override
    public void onFail(String meg) {
        Toast.makeText(MainActivity.this,meg,Toast.LENGTH_SHORT).show();
    }
}
