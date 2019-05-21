package com.raoqian.topactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raoqian.topactivity.bean.UseAppInfoBean;
import com.raoqian.topactivity.utils.DateHelper;
import com.raoqian.topactivity.utils.SPHelper;
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

    class TestShowAdapter extends BaseAdapter<TestShowHolder, UseAppInfoBean> {

        public TestShowAdapter(Activity context) {
            super(context);
        }

        @Override
        public TestShowHolder onCreateHolder(ViewGroup parent, int viewType) {
            View view = getView(R.layout.recycler_item, parent);
            return new TestShowHolder(view);
        }

        @Override
        public void onBindingHolder(TestShowHolder holder, int position, UseAppInfoBean data) {
            holder.mName.setText(data.getNickName());
            holder.mStartTime.setText(TimeParse.stampToDate(data.getStartTime() + ""));
            if (data.getEndTime() > 0) {
                holder.mEndTime.setText(TimeParse.stampToDate(data.getEndTime() + ""));
            }
            holder.mUsingTime.setText(data.getShowUsingTime());
            holder.pan.setOnLongClickListener(longClickListener);
            holder.pan.setTag(data);
        }
    }

    View.OnLongClickListener longClickListener = v -> {
        UseAppInfoBean data = (UseAppInfoBean) v.getTag();
        if(SPHelper.saveSelectPackageId(v.getContext(), data.getPageName())){
            Toast.makeText(v.getContext(), "添加成功:"+data.getPageName(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(v.getContext(), "移除成功:"+data.getPageName(), Toast.LENGTH_SHORT).show();
        }
        return true;
    };

    class TestShowHolder extends BaseHolder {

        TextView mName;
        TextView mStartTime;
        TextView mEndTime;
        TextView mUsingTime;
        View pan;

        public TestShowHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mStartTime = (TextView) itemView.findViewById(R.id.startTime);
            mEndTime = (TextView) itemView.findViewById(R.id.endTime);
            mUsingTime = (TextView) itemView.findViewById(R.id.usingTime);
            pan = itemView.findViewById(R.id.view_pan);
        }
    }
}