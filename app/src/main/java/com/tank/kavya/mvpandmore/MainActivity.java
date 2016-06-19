package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.EndlessScrollListener;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;
import com.tank.kavya.mvpandmore.ui.MainRecyclerAdapter;

import junit.framework.Assert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Kavya
 */
public class MainActivity extends AppCompatActivity implements IImageViewListener {

    @Inject
    ImageDataPresenter mPresenter;
    private MainRecyclerAdapter mAdapter;
    private EndlessScrollListener mScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getComponent().inject(this);

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        mAdapter = new MainRecyclerAdapter(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        Assert.assertNotNull(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        mScrollListener = new EndlessScrollListener(manager) {};
        recyclerView.addOnScrollListener(mScrollListener);
        mPresenter.setListeners(this);
    }

    @Override
    public Observable<Integer> shouldFetchImages() {
        return mScrollListener.shouldLoadMoreImages();
    }

    public void updateImages(List<ImageItem> item) {
        mAdapter.updateItems(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bind();
    }

    @Override
    protected void onPause() {
        mPresenter.unbind();
        super.onPause();
    }

}
