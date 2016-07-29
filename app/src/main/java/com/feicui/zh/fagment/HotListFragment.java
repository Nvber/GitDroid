package com.feicui.zh.fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.feicui.zh.R;
import com.feicui.zh.common.ActivityUtils;
import com.feicui.zh.fagment.view.HotListView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/7/27.
 */
public class HotListFragment extends Fragment implements HotListView {

    @BindView(R.id.lvRepos)
    ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private ArrayAdapter adapter;
     /** P 用来做当前页面业务逻辑及视图更新的*/
    private HotListPresenter presenter;
    /**上拉加载更多的视图*/
    private HotFootView hotFootView;
    private ActivityUtils activityUtils;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        activityUtils = new ActivityUtils(this);
        presenter = new HotListPresenter(this);
        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                new ArrayList<String>() );
        lvRepos.setAdapter(adapter);
        initPullfresh();
        initLoadMore();
    }
/**上拉加载更多的方法*/
    private void initLoadMore() {
        hotFootView = new HotFootView(getContext());
        /**下拉第三方包 必记*/
        Mugen.with(lvRepos, new MugenCallbacks() {
           /**listview，滚动到底部,将触发此方法*/
            @Override
            public void onLoadMore() {
                // 执行上拉加载数据的业务处理
                presenter.loadMore();
            }
            /**是否正在加载中,其内部将用此方法来判断是否触发onLoadMore*/
            @Override
            public boolean isLoading() {
                return lvRepos.getFooterViewsCount()>0 && hotFootView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return lvRepos.getFooterViewsCount()>0 && hotFootView.isComplete();
            }
        }).start();

    }

    /**下拉刷新的方法*/
    private void initPullfresh() {
        // 使用当前对象做为key，来记录上一次的刷新时间,如果两次下拉太近，将不会触发新刷新
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrClassicFrameLayout.setDurationToCloseHeader(1000);
       /** 下拉刷新监听处理*/
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 去做数据的加载，做具体的业务
                // 也就是说，你要抛开视图，到后台线程去做你的业务处理(数据刷新加载)
                presenter.refresh();
            }
        });

        // 以下代码（只是修改了header样式）
        StoreHouseHeader header= new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + " Android");
        header.setPadding(0,60,0,60);
        /**修改Ptr的HeaderView效果*/
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.addPtrUIHandler(header);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    // 显示内容 or 错误 or 空白 , 三选一
    @Override
    public void showContentView(){
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }
    @Override
    public void showErrorView(){
        ptrClassicFrameLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }
    @Override
    public void showEmptyView(){
        ptrClassicFrameLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }
    // 显示提示信息，如：Toast， 直接在当前页面上页面
    @Override
    public void showMessage(String msg){
        activityUtils.showToast(msg);
    }
    /**停止刷新*/
    @Override
    public void stopRefresh(){
        ptrClassicFrameLayout.refreshComplete();
    }
    /**刷新数据，将后台线程更新加载到的数据，刷新显示到视图(listview)上来显示给用户看*/
    @Override
    public void refreshDatas(List<String> data){
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }
    // 上拉加载更多视图实现----------------------
    /**显示更多*/
    @Override
    public void showLoadMoreLoading() {
        if (lvRepos.getFooterViewsCount()==0){
            lvRepos.addFooterView(hotFootView);
        }
        hotFootView.showLoading();
    }
    /**隐藏数据*/
    @Override
    public void hideLoadMore() {
        lvRepos.removeFooterView(hotFootView);/////
    }
    /**显示错误*/
    @Override
    public void showLoadMoreError(String erromsg) {
        if (lvRepos.getFooterViewsCount()==0){
            lvRepos.addFooterView(hotFootView);
        }
        hotFootView.showError(erromsg);
    }
    /**添加更多数据*/
    @Override
    public void addMoreDate(List<String> data) {
        adapter.clear();

        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }



}
