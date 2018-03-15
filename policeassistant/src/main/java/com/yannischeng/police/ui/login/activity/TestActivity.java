package com.yannischeng.police.ui.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yannischeng.guolvtj.R;
import com.yannischeng.police.adapter.TestAdapter;
import com.yannischeng.police.base.TestBean;
import com.yannischeng.police.widget.recycler_view_loading.LoadMoreFooterView;
import com.yannischeng.police.widget.recycler_view_loading.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TestActivity  测试用Activity，无实际意义
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/14
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_header)
    RefreshHeadView mSwipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    private List<TestBean> mList = null;
    private TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        initMyData();
        initMyView();
        initSwipeLayout();

        clickSingleItem();
        clickSingleChildrenItem();
    }

    /**
     * item中的子控件的点击事件
     */
    private void clickSingleChildrenItem() {
        mTestAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(TestActivity.this, position + "子控件，被点击了！！", Toast.LENGTH_SHORT).show();
            }
        });

        mTestAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(TestActivity.this, position + "子控件，被长按了 -！-！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /**
     * item的点击事件
     */
    private void clickSingleItem() {
        // 点击时间响应
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(TestActivity.this, position + "被点击了！！", Toast.LENGTH_SHORT).show();
            }
        });

        // 长按时间响应
        mTestAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(TestActivity.this, position + "被长按了 -！-！", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initMyView() {
        // 设置RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTestAdapter = new TestAdapter(R.layout.test_layout, mList);
        // 开启动画
        mTestAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置重复执行动画
        mTestAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mTestAdapter);
    }

    private void initMyData() {
        mList = new ArrayList<TestBean>();

        for (int i = 0; i < 20; i++) {
            TestBean bean = new TestBean();
            bean.setTitle("这是标题：" + i);
            bean.setContent("这是内容-- -- ：" + i);
            mList.add(bean);
        }
    }

    private void initSwipeLayout() {
        mSwipeToLoadLayout.setRefreshHeaderView(mSwipeRefreshHeader);
        mSwipeToLoadLayout.setLoadMoreFooterView(mSwipeLoadMoreFooter);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
        mSwipeToLoadLayout.setRefreshEnabled(true);

        //刷新
        mSwipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mSwipeToLoadLayout.isLoadingMore()) {
                    mSwipeToLoadLayout.setLoadMoreEnabled(false);
                }
                mList.clear();
                initMyData();

                mSwipeToLoadLayout.setLoadMoreEnabled(true);
                mSwipeToLoadLayout.setRefreshing(false);
            }
        });

        //加载更多
        mSwipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mSwipeToLoadLayout.isRefreshing()) {
                    mSwipeToLoadLayout.setRefreshEnabled(false);
                }
                initMyData();

                mSwipeToLoadLayout.setRefreshEnabled(true);
                mSwipeToLoadLayout.setLoadingMore(false);

            }
        });
    }
}
