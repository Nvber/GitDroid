package com.feicui.zh.fagment;

import android.os.AsyncTask;
import android.util.Log;

import com.feicui.zh.fagment.view.HotListFragmentView;
import com.feicui.zh.fagment.view.HotListView;
import com.feicui.zh.network.GitHubApi;
import com.feicui.zh.network.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/28.
 * P
 */
public class HotListPresenter {

    private HotListView hotListView;
    private int count;


    public HotListPresenter(HotListView hotListView) {
        this.hotListView = hotListView;
    }
    /**刷新*/
    public void refresh(){
//        new RefreshTask().execute();

//        GitHubClient gitHubClient = new GitHubClient();
//        GitHubApi gitHubApi = gitHubClient.getGitHubApi();
//        Call<ResponseBody> call = gitHubApi.getRetrofitContributors();
//
//        call.enqueue(refreshcallback);

    }
    private Callback<ResponseBody> refreshcallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            hotListView.stopRefresh();
            if (response.isSuccessful()){

                try {
                    ResponseBody body = response.body();
                    if (body==null){
                        hotListView.showMessage("未知错误");
                        return;
                    }
                    String content = body.string();
                    Log.d("TAG", content);
                    hotListView.showContentView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                hotListView.showMessage("code:" + response.code());
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            hotListView.stopRefresh();
            hotListView.showMessage(t.getMessage());
            hotListView.showContentView();
        }
    };
    /**加载更多*/
    public void loadMore(){
        hotListView.showLoadMoreLoading();
        new LoadMoreTask().execute();
    }
    /**上拉刷新加载更多*/
    final class LoadMoreTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> data = new ArrayList<>();
            for (int i=0;i<20;i++){
                data.add("数据A"+(count++));
            }
            hotListView.addMoreDate(data);
            hotListView.hideLoadMore();/////
        }
    }
/**下拉刷新*/
    final class RefreshTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArrayList<String> datas = new ArrayList<>();
            for (int i=0;i<20;i++){
                datas.add("数据"+(count++));
            }
            hotListView.stopRefresh();
            hotListView.refreshDatas(datas);
            hotListView.showContentView();
        }
    }
}
