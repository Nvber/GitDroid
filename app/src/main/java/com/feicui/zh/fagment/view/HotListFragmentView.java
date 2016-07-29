package com.feicui.zh.fagment.view;


import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface  HotListFragmentView {
    //内容
     void showContentView();
    //错误
     void showErrorView();
    //空白
     void showEmptyView();
    //信息
     void showMessage(String msg);
    //停止刷新
     void stopRefresh();
    //刷新加数据
     void refreshDatas(List<String> data);
}
