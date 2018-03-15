package com.yannischeng.police.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yannischeng.guolvtj.R;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/27
 */
public class LineFragment extends Fragment {

    private TextView mainTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_line_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainTv = (TextView) view.findViewById(R.id.line_tv);
    }
}
