package com.yannischeng.police.widget.recycler_view_item;

/**
 * 实现Adapter和ItemTouchHelper之间涉及数据的操作
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/1/24
 */
public interface ItemTouchHelperAdapter {

    /**
     * 数据交换
     * @param fromPosition 。
     * @param toPosition 。
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 数据删除
     * @param position 。
     */
    void onItemDissmiss(int position);
}
