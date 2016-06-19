package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.api.ApiService;
import com.tank.kavya.mvpandmore.api.INetworkStatus;
import com.tank.kavya.mvpandmore.pojo.ImageItem;
import com.tank.kavya.mvpandmore.ui.IImageViewListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
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
        mImageDataPresenter.setListeners(mImageViewListener);
    }

    @Test
    public void testGetImages_returnsListOfImages() {
        List<ImageItem> items = getDummyItems(10);
        when(mNetworkStatus.connectedStream()).thenReturn(Observable.just(true));
        when(mApiService.getImages(1)).thenReturn(Observable.just(items));
        when(mImageViewListener.shouldFetchImages()).thenReturn(Observable.never());

        mImageDataPresenter.bind();

        verify(mImageViewListener).updateImages(items);
        verify(mApiService).getImages(anyInt());
    }

    private List<ImageItem> getDummyItems(int count) {
        return Observable.range(0, count)
                         .map(i -> new ImageItem(i.toString()))
                         .toList()
                         .toBlocking()
                         .single();
    }

}
