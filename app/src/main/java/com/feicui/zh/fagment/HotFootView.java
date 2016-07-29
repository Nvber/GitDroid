package com.feicui.zh.fagment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicui.zh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/28.
 *
 * 本视图用来实现上拉功能视图，
 * 当ListView滚动到尾部时，我们将在会过listView的addFooterView方法将此视图添加上去，
 * 并且将在后台线程去加更多数据
 * 此视图将考虑三种不同状态：
 * 展示loading (进度条)
 * 显示错误（当点击时，重新load）
 * 显示没有更多数据
 */
public class HotFootView extends FrameLayout {
    /**加载*/
    private static final int STATE_LOADING = 0;
    /**空白*/
    private static final int STATE_COMPLETE = 1;
    /**错误*/
    private static final int STATE_ERROR = 2;

    private int state=STATE_LOADING;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.tv_error)
    TextView tvError;

    public HotFootView(Context context) {
        this(context, null);
    }

    public HotFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
/**加载布局*/
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_load_footer, this, true);
        ButterKnife.bind(this);
    }
    /** 显示加载中*/
    public void showLoading(){
        state = STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /** 显示加载完成(没有更多数据时)*/
    public void showComplete(){
        state = STATE_COMPLETE;
        tvComplete.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    /** 显示没有更多数据*/
    public void showError(String erroMsg){
        state = STATE_ERROR;
        tvError.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
    /** 是否正在加载中*/
    public boolean isLoading(){
        return state==STATE_LOADING;
    }

    /** 是否加载完成(没有更多数据)*/
    public boolean isComplete(){
        return state == STATE_COMPLETE;
    }
/**错误时的点击事件*/
    public void setErrorClickListener(OnClickListener onClickListener){
        tvError.setOnClickListener(onClickListener);
    }
}