package com.bytedance.xly.thumbnail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytedance.xly.R;

import androidx.annotation.Nullable;

/*
 * 包名：      com.bytedance.xly.view.view
 * 文件名：      AlbumBottomMenu
 * 创建时间：      2020/6/3 11:00 AM
 *
 */
public class AlbumBottomMenu extends LinearLayout {
    private TextView  iv_share;

    //动画
    private TranslateAnimation mShowActionBottom;
    private TranslateAnimation mHiddenActionBottom;

    private AlbumBottomMenu.AlubmBottomMenuListener menuListener;

    public AlbumBottomMenu(Context context) {
        this(context, null);
    }

    public AlbumBottomMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_album_menu, this);
        initAnimation();
        initView();
    }

    private void initView() {
//        iv_delete = findViewById(R.id.iv_delete);
        iv_share = findViewById(R.id.iv_share);
//        iv_delete.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (menuListener != null) {
//                    menuListener.onDeleteClick();
//                }
//            }
//        });
        iv_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener != null) {
                    menuListener.onShareClick();
                }
            }
        });
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        //显示
        mShowActionBottom = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowActionBottom.setDuration(500);
        //隐藏
        mHiddenActionBottom = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenActionBottom.setDuration(500);
    }

    public void setMenuListener(AlbumBottomMenu.AlubmBottomMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            startAnimation(mShowActionBottom);
        } else {
            startAnimation(mHiddenActionBottom);
        }
        super.setVisibility(visibility);
    }

    public interface AlubmBottomMenuListener {
        /**
         * the delete button click callback
         */
        void onDeleteClick();

        /**
         * the share button click callback
         */
        void onShareClick();
    }
}
