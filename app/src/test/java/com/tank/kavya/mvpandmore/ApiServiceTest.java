package com.tank.kavya.mvpandmore;

import com.tank.kavya.mvpandmore.api.ApiService;
import com.tank.kavya.mvpandmore.api.IApiRequest;
import com.tank.kavya.mvpandmore.pojo.ImageData;
import com.tank.kavya.mvpandmore.pojo.ImageItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;

public class ApiServiceTest {

    private ApiService mApiService;

    @Mock
    private IApiRequest mApiRequest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mApiService = new ApiService(mApiRequest);
    }

    @Test
    public void testApiService_fetchesImages() {
        TestSubscriber<List<ImageItem>> ts = new TestSubscriber<>();
        Mockito.when(mApiRequest.fetchImages(10, 1, "full", "drum"))
               .thenReturn(Observable.just(mock(ImageData.class)));

        mApiService.getImages(1).subscribe(ts);

        ts.assertNoErrors();
        ts.assertCompleted();
        Mockito.verify(mApiRequest).fetchImages(10, 1, "full", "drum");
    }

}