package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.EndlessScrollListener;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;
import com.tank.kavya.mvpandmore.ui.MainRecyclerAdapter;

import junit.framework.Assert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Kavya
 */
public class MainFragment extends Fragment implements IImageViewListener {

    @Inject
    ImageDataPresenter mPresenter;

    private MainRecyclerAdapter mAdapter;

    private EndlessScrollListener mScrollListener;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeRecyclerView((RecyclerView) view.findViewById(R.id.main_recycler_view));
        mPresenter.bind(this);
    }

    private void initializeRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new MainRecyclerAdapter(getContext());

        Assert.assertNotNull(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        mScrollListener = new EndlessScrollListener(manager) {};
        recyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    @NonNull
    public Observable<Integer> shouldFetchImages() {
        return mScrollListener.shouldLoadMoreImages();
    }

    public void updateImages(@NonNull List<ImageItem> item) {
        mAdapter.updateItems(item);
    }

    @Override
    public void onDestroyView() {
        mPresenter.unbind();
        super.onDestroyView();
    }

}
