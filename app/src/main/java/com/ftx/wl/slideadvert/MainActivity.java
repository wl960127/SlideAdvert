package com.ftx.wl.slideadvert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mView;

    private List<String> list;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        init();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("<<<<--------" + i + "---------->>>>");
        }
    }

    private void initView() {
        mView = findViewById(R.id.id_recyclerview);

        mView.setLayoutManager( mLinearLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void init() {
        mView.setAdapter(new CommonAdapter<String>(MainActivity.this,
                R.layout.item,
                list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                if (position > 0 && position % 7 == 0) {
                    holder.setVisible(R.id.id_tv_title, false);
                    holder.setVisible(R.id.id_iv_ad, true);
                } else {
                    holder.setVisible(R.id.id_tv_title, true);
                    holder.setVisible(R.id.id_iv_ad, false);
                }
            }
        });

        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();

                LogUtils.e("   fPos  ------->>>> " + fPos);

                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                LogUtils.e("   lPos  ------->>>> " + lPos);

                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);

                    LogUtils.e("      for  i  ------->>>> " + i);

                    SlideAD_View adImageView = view.findViewById(R.id.id_iv_ad);
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());

                        LogUtils.e("mLinearLayoutManager.getHeight()  --->>"+mLinearLayoutManager.getHeight() +"\t\t\t view.getTop() --->>"+view.getTop());

                    }
                }
            }
        });

    }
}
