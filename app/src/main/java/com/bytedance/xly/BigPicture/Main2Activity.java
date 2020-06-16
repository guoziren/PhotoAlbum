package com.bytedance.xly.BigPicture;

import android.annotation.SuppressLint;
import android.content.Intent;
//<<<<<<< HEAD
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
//=======
//import android.os.Build;
//import android.os.Bundle;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//>>>>>>> master
import android.view.WindowManager;
import android.widget.Button;

//<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
//=======
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//>>>>>>> master
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bytedance.xly.R;
import com.bytedance.xly.model.bean.AlbumBean;
import com.bytedance.xly.tuya.model.BLScrawlParam;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;


import java.io.File;
import java.io.Serializable;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private List<AlbumBean> picturePath;
    private ViewPager ViewPage;
    private int currentPage;
    private GestureDetector gd1;//手势
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
//<<<<<<< HEAD
    private ScaleView[] mScaleViews;
    private ScalePagerAdapter mAdapter;
    private static final String TAG = "Main2Activity";
    private boolean isFirst=true;
    private Button mBtnTuya;
    private Button mBtnCaiJian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //gd1 = new GestureDetector(this,new SimpleOnGestureListener());

        Button button=findViewById(R.id.huaban);//这是涂鸦
        Button button1=findViewById(R.id.bianji);//这是旋转功能的入口
        mBtnTuya = findViewById(R.id.tuya);
        mBtnCaiJian = findViewById(R.id.caijian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,Tuya.class);//转到涂鸦的界面
                intent.putExtra("path",currentPage);
                intent.putExtra("array", (Serializable) picturePath);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,Bianji.class);
                intent.putExtra("path",currentPage);
                intent.putExtra("array", (Serializable) picturePath);
                startActivity(intent);
            }
        });//转到编辑的界面
        gd1 = new GestureDetector(this,new SimpleOnGestureListener());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnTuya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScrawlClick();
            }
        });
        mBtnCaiJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUCropActivity();
            }
        });
    }
    private void onScrawlClick() {
        BLScrawlParam.bitmap = BitmapFactory.decodeFile(picturePath.get(currentPage).getPath());
//        Intent intent = new Intent(mInstance, BLScrawlActivity.class);
//        intent.putExtra(BLScrawlParam.KEY, new BLScrawlParam());
//        ActivityUtils.startActivityForResult(mInstance, intent, BLScrawlParam.REQUEST_CODE_SCRAWL);
        BLScrawlParam.startActivity(Main2Activity.this, new BLScrawlParam());
    }
    private void gotoUCropActivity() {
        Uri source = Uri.fromFile(new File(picturePath.get(currentPage).getPath()));
        Uri destination = Uri.fromFile(new File(getCacheDir(), getPackageName()));
        UCrop uCrop = UCrop.of(source, destination);

        uCrop.useSourceImageAspectRatio();
//        uCrop.withAspectRatio(3,2);
        UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.SCALE);
////        设置裁剪框自由移动
        options.setFreeStyleCropEnabled(true);
//        //设置裁剪比例
//        options.setAspectRatioOptions(1, new AspectRatio(null, 5, 4), new AspectRatio(null, 16,9),new AspectRatio(getString(R.string.ucrop_label_original).toUpperCase(),
//                CropImageView.SOURCE_IMAGE_ASPECT_RATIO, CropImageView.SOURCE_IMAGE_ASPECT_RATIO), new AspectRatio(null, 3, 4), new AspectRatio(null, 6, 7));
        options.setStatusBarColor(Color.BLACK);
        options.setToolbarColor(Color.BLACK);
        options.setActiveWidgetColor(Color.BLACK);
        uCrop.withOptions(options);
        uCrop.start(Main2Activity.this);
    }
    private class ScalePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //Log.d(TAG, "getCount: "+picturePath.size());
            return picturePath.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//          if (object instanceof ScaleView) {
//              ScaleView scaleView = (ScaleView) object;
//              container.removeView(scaleView);
//          }
            Log.d(TAG, "destroyItem: ");
            container.removeView(mScaleViews[position]);
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, currentPage, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ScaleView scaleView = new ScaleView(getApplicationContext());
            Log.d(TAG, "instantiateItem: "+currentPage+" position:"+position);
            scaleView.setImageURI(Uri.parse(picturePath.get(position).getPath()));
            mScaleViews[position] = scaleView;
            container.addView(scaleView);
            return scaleView;
        }

    }



//<<<<<<< HEAD
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gd1.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
//=======
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        gd1.onTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }
//>>>>>>> master
    @SuppressLint("ClickableViewAccessibility")
    private void initView(){
        Intent intent = getIntent();
        this.picturePath = (List<AlbumBean>) intent.getSerializableExtra("picturePath");
        currentPage = intent.getIntExtra("CurrentPage",0);
        ViewPage = findViewById(R.id.ViewPage);

        mScaleViews = new ScaleView[picturePath.size()];
        mAdapter = new ScalePagerAdapter();

        ViewPage.setAdapter(mAdapter);

        ViewPage.setCurrentItem(currentPage);
    }

    class SimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int verticalMinDistance = 300;
            int minVelocity = 10;
            if (Math.abs(e1.getY() - e2.getY()) > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                //上下滑动
                Main2Activity.this.finish();
            }
            return true;
        }

    }
}
