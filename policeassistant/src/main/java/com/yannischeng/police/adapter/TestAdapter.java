package com.yannischeng.police.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yannischeng.guolvtj.R;
import com.yannischeng.police.base.TestBean;

import java.util.List;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/14
 */
public class TestAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    public TestAdapter(int layoutResId, @Nullable List<TestBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .addOnClickListener(R.id.tv_title)
                .addOnLongClickListener(R.id.tv_title);
    }
}
