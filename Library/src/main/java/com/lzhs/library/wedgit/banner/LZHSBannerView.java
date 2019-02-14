package com.lzhs.library.wedgit.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzhs.library.R;
import com.lzhs.library.wedgit.banner.holder.LZHSHolderCreator;
import com.lzhs.library.wedgit.banner.holder.LZHSViewHolder;
import com.lzhs.library.wedgit.banner.scroller.ViewPagerScroller;
import com.lzhs.library.wedgit.banner.transformers.CoverModeTransformer;
import com.lzhs.library.wedgit.banner.transformers.ScaleYTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: android 可折叠 Banner
 * <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/9/19$ 下午2:12$ <br/>
 */
public class LZHSBannerView<T> extends RelativeLayout {
    private static final String TAG = "LZHSBannerView";
    private CustomViewPager mViewPager;
    private BannerPagerAdapter mAdapter;
    private List<T> mDatas;
    /**
     * 是否自动播放
     */
    private boolean mIsAutoPlay = true;
    /**
     * 当前位置
     */
    private int mCurrentItem = 0;
    private Handler mHandler = new Handler();
    /**
     * Banner 切换时间间隔
     */
    private int mDelayedTime = 3000;
    /**
     * 控制ViewPager滑动速度的Scroller
     */
    private ViewPagerScroller mViewPagerScroller;
    /**
     * 开启魅族Banner效果
     */
    private boolean mIsOpenFoldEffect = true;
    /**
     * 是否轮播图片
     */
    private boolean mIsCanLoop = true;
    /**
     * indicator容器
     */
    private LinearLayout mIndicatorContainer;
    /**
     * mIndicatorRes[0] 为为选中，mIndicatorRes[1]为选中
     */
    private ArrayList<ImageView> mIndicators = new ArrayList<>();
    private int[] mIndicatorRes = new int[]{R.drawable.indicator_normal, R.drawable.indicator_selected};
    /**
     * indicator 距离左边的距离
     */
    private int mIndicatorPaddingLeft = 0;
    /**
     * indicator 距离右边的距离
     */
    private int mIndicatorPaddingRight = 0;
    /**
     * indicator 距离上边的距离
     */
    private int mIndicatorPaddingTop = 0;
    /**
     * indicator 距离下边的距离
     */
    private int mIndicatorPaddingBottom = 0;
    /**
     * 在开启折叠模式下，由于前后显示了上下一个页面的部分，因此需要计算这部分padding
     */
    private int mMZModePadding = 0;
    private int mIndicatorAlign = 1;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private BannerPageClickListener mBannerPageClickListener;

    public enum IndicatorAlign {
        /**
         * 做对齐
         */
        LEFT,
        /**
         * 居中对齐
         */
        CENTER,
        /**
         * 右对齐
         */
        RIGHT
    }

    /**
     * 中间Page是否覆盖两边，默认覆盖
     */
    private boolean mIsMiddlePageCover = true;

    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            //region 
            if (mIsAutoPlay) {
                mCurrentItem = mViewPager.getCurrentItem();
                mCurrentItem++;
                if (mCurrentItem == mAdapter.getCount() - 1) {
                    mCurrentItem = 0;
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    mHandler.postDelayed(this, mDelayedTime);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    mHandler.postDelayed(this, mDelayedTime);
                }
            } else {
                mHandler.postDelayed(this, mDelayedTime);
            }
            //endregion
        }
    };

    public LZHSBannerView(@NonNull Context context) {
        this(context, null);
    }

    public LZHSBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LZHSBannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LZHSBannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LZHSBannerView);
        mIsOpenFoldEffect = typedArray.getBoolean(R.styleable.LZHSBannerView_isFoldEffect, true);
        mIsMiddlePageCover = typedArray.getBoolean(R.styleable.LZHSBannerView_middle_page_cover, true);
        mIsCanLoop = typedArray.getBoolean(R.styleable.LZHSBannerView_canLoop, true);
        mIndicatorAlign = typedArray.getInt(R.styleable.LZHSBannerView_indicatorAlign, IndicatorAlign.CENTER.ordinal());
        mIndicatorPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.LZHSBannerView_indicatorPaddingLeft, 0);
        mIndicatorPaddingRight = typedArray.getDimensionPixelSize(R.styleable.LZHSBannerView_indicatorPaddingRight, 0);
        mIndicatorPaddingTop = typedArray.getDimensionPixelSize(R.styleable.LZHSBannerView_indicatorPaddingTop, 0);
        mIndicatorPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.LZHSBannerView_indicatorPaddingBottom, 0);
        typedArray.recycle();
    }


    private void init() {
        View view = null;
        if (mIsOpenFoldEffect)
            view = LayoutInflater.from(getContext()).inflate(R.layout.banner_effect_layout, this, true);
        else
            view = LayoutInflater.from(getContext()).inflate(R.layout.banner_normal_layout, this, true);
        mIndicatorContainer = view.findViewById(R.id.mIndicatorContainer);
        mViewPager = view.findViewById(R.id.mViewPager);
        mViewPager.setOffscreenPageLimit(4);
        mMZModePadding = dpToPx(30);
        // 初始化Scroller
        initViewPagerScroll();
        sureIndicatorPosition();
    }


    /**
     * make sure the indicator
     */
    private void sureIndicatorPosition() {
        if (mIndicatorAlign == IndicatorAlign.LEFT.ordinal())
            setIndicatorAlign(IndicatorAlign.LEFT);
        else if (mIndicatorAlign == IndicatorAlign.CENTER.ordinal())
            setIndicatorAlign(IndicatorAlign.CENTER);
        else setIndicatorAlign(IndicatorAlign.RIGHT);

    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mViewPagerScroller = new ViewPagerScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, mViewPagerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化指示器Indicator
     */
    private void initIndicator() {
        mIndicatorContainer.removeAllViews();
        mIndicators.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            if (mIndicatorAlign == IndicatorAlign.LEFT.ordinal()) {
                if (i == 0) {
                    int paddingLeft = mIsOpenFoldEffect ? mIndicatorPaddingLeft + mMZModePadding : mIndicatorPaddingLeft;
                    imageView.setPadding(paddingLeft + 6, 0, 6, 0);
                } else imageView.setPadding(6, 0, 6, 0);

            } else if (mIndicatorAlign == IndicatorAlign.RIGHT.ordinal()) {
                if (i == mDatas.size() - 1) {
                    int paddingRight = mIsOpenFoldEffect ? mMZModePadding + mIndicatorPaddingRight : mIndicatorPaddingRight;
                    imageView.setPadding(6, 0, 6 + paddingRight, 0);
                } else imageView.setPadding(6, 0, 6, 0);
            } else imageView.setPadding(6, 0, 6, 0);

            if (i == (mCurrentItem % mDatas.size()))
                imageView.setImageResource(mIndicatorRes[1]);
            else imageView.setImageResource(mIndicatorRes[0]);

            mIndicators.add(imageView);
            mIndicatorContainer.addView(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mIsCanLoop) return super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            // 按住Banner的时候，停止自动轮播
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_DOWN:
                int paddingLeft = mViewPager.getLeft();
                float touchX = ev.getRawX();
                // 如果是魅族模式，去除两边的区域
                if (touchX >= paddingLeft && touchX < getScreenWidth(getContext()) - paddingLeft) {
                    mIsAutoPlay = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsAutoPlay = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    //region  对外API  

    /******************************************************************************************************/
    /**                             对外API                                                               **/
    /******************************************************************************************************/
    /**
     * 开始轮播
     * <p>应该确保在调用用了{@link LZHSBannerView {@link #setPages(List, LZHSHolderCreator)}} 之后调用这个方法开始轮播</p>
     */
    public void start() {
        // 如果Adapter为null, 说明还没有设置数据，这个时候不应该轮播Banner
        if (mAdapter == null) {
            return;
        }
        if (mIsCanLoop) {
            mIsAutoPlay = true;
            mHandler.postDelayed(mLoopRunnable, mDelayedTime);
        }
    }

    /**
     * 停止轮播
     */
    public void pause() {
        mIsAutoPlay = false;
        mHandler.removeCallbacks(mLoopRunnable);
    }

    /**
     * 设置BannerView 的切换时间间隔
     *
     * @param delayedTime
     */
    public void setDelayedTime(int delayedTime) {
        mDelayedTime = delayedTime;
    }

    /**
     * 设置Banner 轮播页面，改变监听
     * @param onPageChangeListener
     */
    public void addPageChangeLisnter(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 添加Page点击事件
     *
     * @param bannerPageClickListener {@link BannerPageClickListener}
     */
    public void setBannerPageClickListener(BannerPageClickListener bannerPageClickListener) {
        mBannerPageClickListener = bannerPageClickListener;
    }

    /**
     * 是否显示Indicator
     *
     * @param visible true 显示Indicator，否则不显示
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) mIndicatorContainer.setVisibility(VISIBLE);
        else mIndicatorContainer.setVisibility(GONE);
    }

    /**
     * 设置indicator padding
     *
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void setIndicatorPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mIndicatorPaddingLeft = paddingLeft;
        mIndicatorPaddingTop = paddingTop;
        mIndicatorPaddingRight = paddingRight;
        mIndicatorPaddingBottom = paddingBottom;
        sureIndicatorPosition();
    }

    /**
     * 返回ViewPager
     *
     * @return {@link ViewPager}
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * 设置indicator 图片资源
     *
     * @param unSelectRes 未选中状态资源图片
     * @param selectRes   选中状态资源图片
     */
    public void setIndicatorRes(@DrawableRes int unSelectRes, @DrawableRes int selectRes) {
        mIndicatorRes[0] = unSelectRes;
        mIndicatorRes[1] = selectRes;
    }

    /**
     * 设置数据，这是最重要的一个方法。
     * <p>其他的配置应该在这个方法之前调用</p>
     *
     * @param datas           Banner 展示的数据集合
     * @param mHolderCreator ViewHolder生成器 {@link LZHSHolderCreator} And {@link LZHSViewHolder}
     */
    public void setPages(List<T> datas, LZHSHolderCreator mHolderCreator) {
        if (datas == null || mHolderCreator == null) return;
        mDatas = datas;
        pause();
        if (datas.size() < 3) {//数据集合的长度至少为3,否则，自动为普通Banner模式 不管配置的:open_mz_mode 属性的值是否为true
            mIsOpenFoldEffect = false;
            MarginLayoutParams layoutParams = (MarginLayoutParams) mViewPager.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            mViewPager.setLayoutParams(layoutParams);
            setClipChildren(true);
            mViewPager.setClipChildren(true);
        }
        setOpenFoldEffect();
        initIndicator();//将Indicator初始化放在Adapter的初始化之前，解决更新数据变化更新时crush. 
        mAdapter = new BannerPagerAdapter(datas, mHolderCreator, mIsCanLoop);
        mAdapter.setUpViewViewPager(mViewPager);
        mAdapter.setPageClickListener(mBannerPageClickListener);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int realPosition = position % mIndicators.size();
                if (mOnPageChangeListener != null)
                    mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                int realSelectPosition = mCurrentItem % mIndicators.size();// 切换indicator
                for (int i = 0; i < mDatas.size(); i++) {
                    if (i == realSelectPosition)
                        mIndicators.get(i).setImageResource(mIndicatorRes[1]);
                    else mIndicators.get(i).setImageResource(mIndicatorRes[0]);
                }
                // 不能直接将mOnPageChangeListener 设置给ViewPager ,否则拿到的position 是原始的positon
                if (mOnPageChangeListener != null)
                    mOnPageChangeListener.onPageSelected(realSelectPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mIsAutoPlay = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mIsAutoPlay = true;
                        break;
                }
                if (mOnPageChangeListener != null)
                    mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        });


    }

    /**
     * 注意该方法只在 普通Banner 切换下，才会生效
     *
     * @param mPageTransformer
     */
    public void setPageTransformer(ViewPager.PageTransformer mPageTransformer) {
        if (!mIsOpenFoldEffect) mViewPager.setPageTransformer(true, mPageTransformer);
    }

    /**
     * 是否开启折叠模式
     */
    private void setOpenFoldEffect() {
        if (mIsOpenFoldEffect) {
            if (mIsMiddlePageCover)
                mViewPager.setPageTransformer(true, new CoverModeTransformer(mViewPager));                // 中间页面覆盖两边
            else
                mViewPager.setPageTransformer(false, new ScaleYTransformer()); // 中间页面不覆盖，页面并排，只是Y轴缩小
        }
    }

    /**
     * 设置Indicator 的对齐方式
     *
     * @param indicatorAlign {@link IndicatorAlign#CENTER }{@link IndicatorAlign#LEFT }{@link IndicatorAlign#RIGHT }
     */
    public void setIndicatorAlign(IndicatorAlign indicatorAlign) {
        mIndicatorAlign = indicatorAlign.ordinal();
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        if (indicatorAlign == IndicatorAlign.LEFT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (indicatorAlign == IndicatorAlign.RIGHT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
        layoutParams.setMargins(0, mIndicatorPaddingTop, 0, mIndicatorPaddingBottom);
        mIndicatorContainer.setLayoutParams(layoutParams);
    }

    /**
     * 获取 下标指示器控件
     * @return
     */
    public LinearLayout getIndicatorContainer() {
        return mIndicatorContainer;
    }

    /**
     * 设置Banner切换的速度
     *
     * @param duration 切换动画时间
     */
    public void setDuration(int duration) {
        mViewPagerScroller.setDuration(duration);
    }

    /**
     * 设置是否使用ViewPager默认是的切换速度
     *
     * @param useDefaultDuration 切换动画时间
     */
    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mViewPagerScroller.setUseDefaultDuration(useDefaultDuration);
    }

    /**
     * 获取Banner页面切换动画时间
     *
     * @return
     */
    public int getDuration() {
        return mViewPagerScroller.getScrollDuration();
    }

    //endregion

    public static class BannerPagerAdapter<T> extends PagerAdapter {
        private List<T> mDatas;
        private LZHSHolderCreator mLZHSHolderCreator;
        private ViewPager mViewPager;
        private boolean canLoop;
        private BannerPageClickListener mPageClickListener;
        private final int mLooperCountFactor = 500;

        public BannerPagerAdapter(List<T> datas, LZHSHolderCreator LZHSHolderCreator, boolean canLoop) {
            if (mDatas == null)
                mDatas = new ArrayList<>();
            for (T t : datas)
                mDatas.add(t);
            mLZHSHolderCreator = LZHSHolderCreator;
            this.canLoop = canLoop;
        }

        public void setPageClickListener(BannerPageClickListener pageClickListener) {
            mPageClickListener = pageClickListener;
        }

        /**
         * 初始化Adapter和设置当前选中的Item
         *
         * @param viewPager
         */
        public void setUpViewViewPager(ViewPager viewPager) {
            mViewPager = viewPager;
            mViewPager.setAdapter(this);
            mViewPager.getAdapter().notifyDataSetChanged();
            int currentItem = canLoop ? getStartSelectItem() : 0;
            mViewPager.setCurrentItem(currentItem);            //设置当前选中的Item
        }

        private int getStartSelectItem() {
            // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
            // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
            int currentItem = getRealCount() * mLooperCountFactor / 2;
            if (currentItem % getRealCount() == 0)
                return currentItem;
            // 直到找到从0开始的位置
            while (currentItem % getRealCount() != 0) {
                currentItem++;
            }
            return currentItem;
        }

        public void setDatas(List<T> datas) {
            mDatas = datas;
        }

        @Override
        public int getCount() {
            // 如果getCount 的返回值为Integer.MAX_VALUE 的话，那么在setCurrentItem的时候会ANR(除了在onCreate 调用之外)
            return canLoop ? getRealCount() * mLooperCountFactor : getRealCount();//ViewPager返回int 最大值
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getView(position, container);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            // 轮播模式才执行
            if (canLoop) {
                int position = mViewPager.getCurrentItem();
                if (position == getCount() - 1) {
                    position = 0;
                    setCurrentItem(position);
                }
            }

        }

        private void setCurrentItem(int position) {
            try {
                mViewPager.setCurrentItem(position, false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取真实的Count
         *
         * @return
         */
        private int getRealCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        /**
         * @param position
         * @param container
         * @return
         */
        private View getView(int position, ViewGroup container) {

            final int realPosition = position % getRealCount();
            LZHSViewHolder holder = null;
            holder = mLZHSHolderCreator.createViewHolder();

            if (holder == null)
                throw new RuntimeException("can not return a null holder");
            View view = holder.createView(container.getContext());
            if (mDatas != null && mDatas.size() > 0)
                holder.onBind(container.getContext(), realPosition, mDatas.get(realPosition));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPageClickListener != null)
                        mPageClickListener.onPageClick(v, realPosition);
                }
            });
            return view;
        }
    }


    /**
     * Banner page 点击回调
     */
    public interface BannerPageClickListener {
        void onPageClick(View view, int position);
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
