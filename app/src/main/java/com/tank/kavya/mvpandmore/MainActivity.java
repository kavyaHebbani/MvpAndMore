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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
