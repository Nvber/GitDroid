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
public class HotListFragment extends Fragment {

    @BindView(R.id.lvRepos)
    ListView lvRepos;
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.errorView)
    TextView errorView;

    private ArrayAdapter adapter;
    // 用来做当前页面业务逻辑及视图更新的
    private HotListPresenter presenter;

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

        presenter = new HotListPresenter(this);
        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                new ArrayList<String>() );
        lvRepos.setAdapter(adapter);
        initPullfresh();
    }
    // 刷新的方法
    private void initPullfresh() {
        // 使用当前对象做为key，来记录上一次的刷新时间,如果两次下拉太近，将不会触发新刷新
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        // 下拉刷新监听处理
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
        // 修改Ptr的HeaderView效果
        ptrClassicFrameLayout.setHeaderView(header);
        ptrClassicFrameLayout.addPtrUIHandler(header);
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    // 显示内容 or 错误 or 空白 , 三选一
    public void showContentView(){
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    public void showErrorView(){
        ptrClassicFrameLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }
    public void showEmptyView(){
        ptrClassicFrameLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }
    // 显示提示信息
    // 如：Toast， 直接在当前页面上页面
    public void showMessage(String msg){

    }

    public void stopRefresh(){
        ptrClassicFrameLayout.refreshComplete();
    }
    // 刷新数据
    // 将后台线程更新加载到的数据，刷新显示到视图(listview)上来显示给用户看
    public void refreshDatas(List<String> data){
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
