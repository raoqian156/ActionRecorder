package com.raoqian.topactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raoqian.topactivity.bean.UseAppInfoBean;
import com.raoqian.topactivity.utils.DateHelper;
import com.raoqian.topactivity.utils.TimeParse;

import java.util.List;

/**
 * Created by raoqian on 2017/7/23.
 */

public class TongJiActivity extends Activity {

    RecyclerView mRecycler;
    TestShowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);
        mRecycler = (RecyclerView) findViewById(R.id.base_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(TongJiActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(llm);

        mAdapter = new TestShowAdapter(TongJiActivity.this);
        mRecycler.setAdapter(mAdapter);

        List<UseAppInfoBean> listData = DateHelper.instance((BaseApplication) getApplication()).queryNearData();
        mAdapter.setData(listData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<UseAppInfoBean> listData = DateHelper.instance((BaseApplication) getApplication()).queryNearData();
        mAdapter.setData(listData);
    }

    class TestShowAdapter extends BaseAdapter<BaseHolder, UseAppInfoBean> {

        public TestShowAdapter(Activity context) {
            super(context);
        }

        @Override
        public BaseHolder onCreateHolder(ViewGroup parent, int viewType) {
            View view = getView(R.layout.recycler_item, parent);
            return new TestShowHolder(view);
        }

        @Override
        public void onBindingHolder(BaseHolder holder, int position) {
            ((TestShowHolder) holder).mName.setText(getDataItem(position).getNickName());
            ((TestShowHolder) holder).mStartTime.setText(TimeParse.stampToDate(getDataItem(position).getStartTime() + ""));
            ((TestShowHolder) holder).mEndTime.setText(TimeParse.stampToDate(getDataItem(position).getEndTime() + ""));
            ((TestShowHolder) holder).mUsingTime.setText((getDataItem(position).getEndTime() - getDataItem(position).getStartTime()) / 1000 + "秒");
        }
    }

    class TestShowHolder extends BaseHolder {

        TextView mName;
        TextView mStartTime;
        TextView mEndTime;
        TextView mUsingTime;

        public TestShowHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mStartTime = (TextView) itemView.findViewById(R.id.startTime);
            mEndTime = (TextView) itemView.findViewById(R.id.endTime);
            mUsingTime = (TextView) itemView.findViewById(R.id.usingTime);
        }
    }
}