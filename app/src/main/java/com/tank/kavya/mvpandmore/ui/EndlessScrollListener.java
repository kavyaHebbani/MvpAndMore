package com.tank.kavya.mvpandmore.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Kavya
 */
public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private boolean mLoading = true;

    private int mPreviousTotal = 0;

    private int mCurrentPage = 1;

    private LinearLayoutManager mLayoutManager;

    private PublishSubject<Integer> mLoadMoreStream = PublishSubject.create();

    protected EndlessScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) //check for scroll down
        {
            final int visibleItemCount = recyclerView.getChildCount();
            final int totalItemCount = mLayoutManager.getItemCount();
            final int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

            if (mLoading) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            final int visibleThreshold = 5;
            if (!mLoading && (totalItemCount - visibleItemCount)
                             <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                mCurrentPage++;
                mLoadMoreStream.onNext(mCurrentPage);
                mLoading = true;
            }
        }
    }

    public Observable<Integer> shouldLoadMoreImages() {
        return mLoadMoreStream.observeOn(Schedulers.computation());
    }

}
