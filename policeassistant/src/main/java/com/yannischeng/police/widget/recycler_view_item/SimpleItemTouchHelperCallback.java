package com.yannischeng.police.widget.recycler_view_item;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

/**
 * 使用ItemTouchHelper需要一个Callback,重写其数个方法来实现我们的需求
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/1/24
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "SimpleItemTouchHelperCa";

    private ItemTouchHelperAdapter mAdapter;
    private int oldDX = 0;

    /**
     * 构造函数，初始化 接口
     * @param adapter
     */
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        this.mAdapter = adapter;
    }

    /**
     * 设置 拖动方向处理、滑动方向处理
     *
     * @param recyclerView 。
     * @param viewHolder 。
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;/*ItemTouchHelper.UP | ItemTouchHelper.DOWN;*/
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    /**
     * 拖动处理
     *
     * @param recyclerView。
     * @param viewHolder。
     * @param target。
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    /**
     * 滑动处理
     *
     * @param viewHolder。
     * @param direction。
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }

    /**
     * 是否能可以滑动
     * @return 。
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;

    }

    /**
     * 是否可以删除
     * @return 。
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ViewGroup mViewGroup = (ViewGroup) viewHolder.itemView;
        //重置改变，防止由于复用而导致的显示问题
        mViewGroup.setScrollX(0);
        mViewGroup.getChildAt(2).setVisibility(View.GONE);
        mViewGroup.getChildAt(1).setVisibility(View.VISIBLE);
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        ViewGroup mViewGroup = (ViewGroup) viewHolder.itemView;
        // 当前itemView的宽度 1080
        int viewWidth = viewHolder.itemView.getMeasuredWidth();
        //仅对侧滑状态下的效果做出改变
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            mViewGroup.scrollTo(-(int)dX,0);
            if (Math.abs(-(int) dX) >= viewWidth / 2) {
                mViewGroup.getChildAt(1).setVisibility(View.GONE);
                mViewGroup.getChildAt(2).setVisibility(View.VISIBLE);
            } else {
                mViewGroup.getChildAt(1).setVisibility(View.VISIBLE);
                mViewGroup.getChildAt(2).setVisibility(View.GONE);

                // 此处的操作是根据 具体的item view布局具体执行的。
                if ((int) dX > oldDX) {
                    //((TextView) mViewGroup.getChildAt(1).findViewById(R.id.notice_view)).setText("松手取消删除");
                } else {
                    //((TextView) mViewGroup.getChildAt(1).findViewById(R.id.notice_view)).setText("继续左划删除");
                }
            }
        }else {
            //拖拽状态下不做改变，需要调用父类的方法
            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        }

        oldDX = (int) dX;
    }

}
