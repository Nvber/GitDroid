package com.feicui.zh.fagment.view;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface HotLoadMoreView {
    void showLoadMoreLoading();
    void hideLoadMore();
    void showLoadMoreError(String erromsg);
    void addMoreDate(List<String> datas);
}
