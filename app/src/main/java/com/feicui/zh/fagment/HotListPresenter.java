package com.feicui.zh.fagment;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/28.
 */
public class HotListPresenter {

    private HotListFragment hotListFragment;
    private int count;


    public HotListPresenter(HotListFragment hotListFragment) {
        this.hotListFragment = hotListFragment;
    }
/**刷新*/
    public void refresh(){
        new RefreshTask().execute();

    }

    class RefreshTask extends AsyncTask<Void,Void,Void>{

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
            hotListFragment.stopRefresh();
            hotListFragment.refreshDatas(datas);
            hotListFragment.showContentView();
        }
    }
}
