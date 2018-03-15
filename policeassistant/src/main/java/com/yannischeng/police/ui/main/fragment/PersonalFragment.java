package com.yannischeng.police.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yannischeng.police.widget.SingleInfo;
import com.yannischeng.guolvtj.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/27
 */
public class PersonalFragment extends Fragment {

    private static final String TAG = "PersonalFragment";

    //@BindView(R.id.personal_mine)
    SingleInfo mPersonalMine;
    //@BindView(R.id.personal_charge_recode)
    SingleInfo mPersonalChargeRecode;
    //@BindView(R.id.personal_fix_recode)
    SingleInfo mPersonalFixRecode;
    //@BindView(R.id.personal_up_recode)
    SingleInfo mPersonalUpRecode;
    //@BindView(R.id.personal_chage_pwd)
    SingleInfo mPersonalChagePwd;
    //@BindView(R.id.personal_login_out)
    SingleInfo mPersonalLoginOut;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_personal_layout, null);
        unbinder = ButterKnife.bind(this, view);
        mPersonalMine = view.findViewById(R.id.personal_mine);
        mPersonalChargeRecode = view.findViewById(R.id.personal_charge_recode);
        mPersonalFixRecode = view.findViewById(R.id.personal_fix_recode);
        mPersonalUpRecode = view.findViewById(R.id.personal_up_recode);
        mPersonalChagePwd = view.findViewById(R.id.personal_chage_pwd);
        mPersonalLoginOut = view.findViewById(R.id.personal_login_out);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doOnClick();

    }

    private void doOnClick() {
        mPersonalMine.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "我的信息", Toast.LENGTH_SHORT).show();
            }
        });

        mPersonalChargeRecode.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "缴费记录", Toast.LENGTH_SHORT).show();
            }
        });

        mPersonalFixRecode.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "故障报修记录", Toast.LENGTH_SHORT).show();
            }
        });

        mPersonalUpRecode.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "投诉举报记录", Toast.LENGTH_SHORT).show();
            }
        });

        mPersonalChagePwd.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "修改密码", Toast.LENGTH_SHORT).show();
            }
        });

        mPersonalLoginOut.setImgClickListener(new SingleInfo.ClickListener() {
            @Override
            public void singleOnClickListener(View view) {
                Toast.makeText(getContext(), "注销登录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
