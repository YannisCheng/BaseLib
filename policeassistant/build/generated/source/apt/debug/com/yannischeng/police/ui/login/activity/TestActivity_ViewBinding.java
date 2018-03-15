// Generated code from Butter Knife. Do not modify!
package com.yannischeng.police.ui.login.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yannischeng.guolvtj.R;
import com.yannischeng.police.widget.recycler_view_loading.LoadMoreFooterView;
import com.yannischeng.police.widget.recycler_view_loading.RefreshHeadView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TestActivity_ViewBinding implements Unbinder {
  private TestActivity target;

  @UiThread
  public TestActivity_ViewBinding(TestActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TestActivity_ViewBinding(TestActivity target, View source) {
    this.target = target;

    target.mSwipeRefreshHeader = Utils.findRequiredViewAsType(source, R.id.swipe_refresh_header, "field 'mSwipeRefreshHeader'", RefreshHeadView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.swipe_target, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeLoadMoreFooter = Utils.findRequiredViewAsType(source, R.id.swipe_load_more_footer, "field 'mSwipeLoadMoreFooter'", LoadMoreFooterView.class);
    target.mSwipeToLoadLayout = Utils.findRequiredViewAsType(source, R.id.swipeToLoadLayout, "field 'mSwipeToLoadLayout'", SwipeToLoadLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TestActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeRefreshHeader = null;
    target.mRecyclerView = null;
    target.mSwipeLoadMoreFooter = null;
    target.mSwipeToLoadLayout = null;
  }
}
