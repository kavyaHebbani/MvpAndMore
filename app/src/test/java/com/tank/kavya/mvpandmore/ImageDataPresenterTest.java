package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.network.ApiService;
import com.tank.kavya.mvpandmore.network.INetworkStatus;
import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageDataPresenterTest {

    private ImageDataPresenter mImageDataPresenter;

    @Mock
    private ApiService mApiService;

    @Mock
    private INetworkStatus mNetworkStatus;

    @Mock
    private IImageViewListener mImageViewListener;

    private TestSchedulerProvider mSchedulerProvider = new TestSchedulerProvider();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mImageDataPresenter = new ImageDataPresenter(mApiService,
                                                     mNetworkStatus,
                                                     mSchedulerProvider);

        when(mNetworkStatus.getIsConnectedStream()).thenReturn(Observable.just(true));
    }

    @Test
    public void testGetImages_returnsListOfImages() {
        List<ImageItem> items = getDummyItems(10);
        when(mApiService.getImages(1)).thenReturn(Observable.just(items));
        when(mImageViewListener.shouldFetchImages()).thenReturn(Observable.just(1));

        mImageDataPresenter.bind(mImageViewListener);

        verify(mApiService).getImages(anyInt());
        verify(mImageViewListener).updateImages(items);
    }

    @Test
    public void testGetImages_fetchesMoreImages_whenScrollEnd() {
        List<ImageItem> items = getDummyItems(10);
        when(mApiService.getImages(anyInt())).thenReturn(Observable.just(items));
        when(mImageViewListener.shouldFetchImages()).thenReturn(Observable.just(1, 2));

        mImageDataPresenter.bind(mImageViewListener);

        verify(mApiService, times(2)).getImages(anyInt());
        verify(mImageViewListener, times(2)).updateImages(items);
    }

    private List<ImageItem> getDummyItems(int count) {
        return Observable.range(0, count)
                         .map(i -> new ImageItem(i.toString()))
                         .toList()
                         .toBlocking()
                         .single();
    }

}
