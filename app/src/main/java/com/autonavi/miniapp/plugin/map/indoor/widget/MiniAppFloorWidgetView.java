package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

@SuppressFBWarnings({"UWF_UNWRITTEN_FIELD"})
public class MiniAppFloorWidgetView extends View {
    private static final int DEF_VISIBLE_ITEMS = 3;
    public static final int INTERNAL_PADDING = 5;
    public static final int LOCATION_BOTTOM = 3;
    public static final int LOCATION_NORMAL = 2;
    public static final int LOCATION_NULL = 0;
    public static final int LOCATION_TOP = 1;
    private static final int MESSAGE_FLING = 2;
    private static final int MESSAGE_JUSTIFY = 1;
    private static final int MESSAGE_SCROLL = 0;
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    public static final String NULL_FLOOR = "";
    private static final int SCROLLING_DURATION = 250;
    private static final int[] SHADOWS_COLORS = {-15658735, 11184810, 11184810};
    /* access modifiers changed from: private */
    public MiniAppBaseFloorAdapter adapter = null;
    private int additionalItemsHeight;
    private int addtionalItemSpace;
    /* access modifiers changed from: private */
    public Handler animationHandler = new Handler() {
        public void handleMessage(Message message) {
            MiniAppFloorWidgetView.this.scroller.computeScrollOffset();
            int currY = MiniAppFloorWidgetView.this.scroller.getCurrY();
            int access$800 = MiniAppFloorWidgetView.this.lastScrollY - currY;
            MiniAppFloorWidgetView.this.lastScrollY = currY;
            if (access$800 != 0) {
                MiniAppFloorWidgetView.this.doScroll(access$800);
            }
            if (Math.abs(currY - MiniAppFloorWidgetView.this.scroller.getFinalY()) <= 0) {
                MiniAppFloorWidgetView.this.scroller.getFinalY();
                MiniAppFloorWidgetView.this.scroller.forceFinished(true);
            }
            if (!MiniAppFloorWidgetView.this.scroller.isFinished()) {
                MiniAppFloorWidgetView.this.animationHandler.sendEmptyMessage(message.what);
            } else if (message.what == 0) {
                MiniAppFloorWidgetView.this.justify(false);
            } else {
                MiniAppFloorWidgetView.this.finishScrolling();
            }
        }
    };
    private GradientDrawable bottomShadow;
    private Drawable centerDrawable;
    private List<MiniAppFloorChangedListener> changingListeners = new LinkedList();
    /* access modifiers changed from: private */
    public int currentItem = 0;
    /* access modifiers changed from: private */
    public int distanceYOffset = 0;
    private GestureDetector gestureDetector;
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            float y = motionEvent.getY() - (((float) MiniAppFloorWidgetView.this.getHeight()) / 2.0f);
            if (MiniAppFloorWidgetView.this.isLastItem() && y > 0.0f) {
                return true;
            }
            if (MiniAppFloorWidgetView.this.isFirstItem() && y < 0.0f) {
                return true;
            }
            MiniAppFloorWidgetView.this.startScrolling();
            MiniAppFloorWidgetView.this.doScroll((int) (-y));
            MiniAppFloorWidgetView.this.justify(true);
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            if (!MiniAppFloorWidgetView.this.isScrollingPerformed) {
                return false;
            }
            MiniAppFloorWidgetView.this.scroller.forceFinished(true);
            MiniAppFloorWidgetView.this.clearMessages();
            return true;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (MiniAppFloorWidgetView.this.isLastItem()) {
                if (MiniAppFloorWidgetView.this.distanceYOffset > MiniAppFloorWidgetView.this.getItemHeight() && f2 > 0.0f) {
                    return true;
                }
                MiniAppFloorWidgetView.this.distanceYOffset = (int) (((float) MiniAppFloorWidgetView.this.distanceYOffset) + f2);
            } else if (!MiniAppFloorWidgetView.this.isFirstItem()) {
                MiniAppFloorWidgetView.this.distanceYOffset = 0;
            } else if (MiniAppFloorWidgetView.this.distanceYOffset < (-MiniAppFloorWidgetView.this.getItemHeight()) && f2 < 0.0f) {
                return true;
            } else {
                MiniAppFloorWidgetView.this.distanceYOffset = (int) (((float) MiniAppFloorWidgetView.this.distanceYOffset) + f2);
            }
            MiniAppFloorWidgetView.this.startScrolling();
            MiniAppFloorWidgetView.this.doScroll((int) (-f2));
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int i;
            MiniAppFloorWidgetView.this.lastScrollY = (MiniAppFloorWidgetView.this.currentItem * MiniAppFloorWidgetView.this.getItemHeight()) + MiniAppFloorWidgetView.this.scrollingOffset;
            if (MiniAppFloorWidgetView.this.isCyclic) {
                i = Integer.MAX_VALUE;
            } else {
                i = MiniAppFloorWidgetView.this.adapter.getItemsCount() * MiniAppFloorWidgetView.this.getItemHeight();
            }
            MiniAppFloorWidgetView.this.scroller.fling(0, MiniAppFloorWidgetView.this.lastScrollY, 0, ((int) (-f2)) / 4, 0, 0, MiniAppFloorWidgetView.this.isCyclic ? -i : 0 - MiniAppFloorWidgetView.this.getItemHeight(), i);
            MiniAppFloorWidgetView.this.setNextMessage(2);
            return true;
        }
    };
    private Drawable gradientDarkerDrawable;
    private Drawable gradientLighterDrawable;
    private int horizontalPadding;
    boolean isCyclic = false;
    /* access modifiers changed from: private */
    public boolean isScrollingPerformed;
    private int itemHeight = 0;
    public int itemOffset;
    private int itemTextColor;
    private float itemTextSize;
    private StaticLayout itemsLayout;
    private TextPaint itemsPaint;
    private int itemsWidth = 0;
    private String label;
    private StaticLayout labelLayout;
    private int labelOffset;
    private int labelWidth = 0;
    /* access modifiers changed from: private */
    public int lastScrollY;
    private String mBuidingPoiId;
    private String mBuildingFloor;
    private String mBuildingName;
    private String mBuildingType;
    private WeakReference<IContainer> mContainer;
    private String mCurrentLocationFloor = "";
    public int mLocationHeight;
    public volatile int mLocationType = 0;
    private int oldItem = 0;
    /* access modifiers changed from: private */
    public Scroller scroller;
    private List<MiniAppFloorScrollListener> scrollingListeners = new LinkedList();
    /* access modifiers changed from: private */
    public int scrollingOffset;
    private GradientDrawable topShadow;
    private Drawable valueBorderDrawable;
    private StaticLayout valueLayout;
    private TextPaint valuePaint;
    private int valueTextColor;
    private float valueTextSize;
    private int visibleItems = 3;

    public interface IContainer {
        boolean invisibleWhenSmallerThanDesiredHeight();

        void setDesireVisibilty(int i);
    }

    public void setLastDay() {
    }

    public MiniAppFloorWidgetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initData(context);
    }

    public MiniAppFloorWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initData(context);
    }

    public MiniAppFloorWidgetView(Context context) {
        super(context);
        initData(context);
    }

    public void setContainer(IContainer iContainer) {
        if (iContainer != null) {
            this.mContainer = new WeakReference<>(iContainer);
        } else {
            this.mContainer = null;
        }
    }

    public void setCurrentLocationFloor(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        this.mCurrentLocationFloor = str;
    }

    public String getCurrentLocationFloor() {
        return this.mCurrentLocationFloor;
    }

    public void setBuildingType(String str) {
        this.mBuildingType = str;
    }

    public String getBuildingType() {
        return this.mBuildingType;
    }

    public void setBuildingPoiId(String str) {
        this.mBuidingPoiId = str;
    }

    public String getBuildingPoiId() {
        return this.mBuidingPoiId;
    }

    public void setBuildingFloor(String str) {
        this.mBuildingFloor = str;
    }

    public String getBuildingFloor() {
        return this.mBuildingFloor;
    }

    public void setBuildingName(String str) {
        this.mBuildingName = str;
    }

    public String getBuildingName() {
        return this.mBuildingName;
    }

    private void initData(Context context) {
        this.gestureDetector = new GestureDetector(context, this.gestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(context);
        initDefaultResoure();
    }

    private void initDefaultResoure() {
        this.valueBorderDrawable = getResources().getDrawable(R.drawable.map_indoor_select);
        this.horizontalPadding = getResources().getDimensionPixelSize(R.dimen.floor_widget_item_margin);
        if (this.adapter == null || this.adapter.getMaximumLength() < 3) {
            this.valueTextSize = getResources().getDimension(R.dimen.floor_widget_value_text_size);
            this.itemTextSize = getResources().getDimension(R.dimen.floor_widget_item_text_size);
        } else {
            this.valueTextSize = getResources().getDimension(R.dimen.floor_widget_value_text_size_small);
            this.itemTextSize = getResources().getDimension(R.dimen.floor_widget_item_text_size_small);
        }
        this.itemOffset = 0;
        this.additionalItemsHeight = (int) this.itemTextSize;
        this.addtionalItemSpace = 0;
        this.valueTextColor = getResources().getColor(R.color.floor_widget_value);
        this.itemTextColor = getResources().getColor(R.color.floor_widget_items);
    }

    public MiniAppBaseFloorAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(MiniAppBaseFloorAdapter miniAppBaseFloorAdapter) {
        this.adapter = miniAppBaseFloorAdapter;
        initDefaultResoure();
        invalidateLayouts();
        invalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.forceFinished(true);
        this.scroller = new Scroller(getContext(), interpolator);
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public void setVisibleItems(int i) {
        this.visibleItems = i;
        invalidate();
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        if (this.label == null || !this.label.equals(str)) {
            this.label = str;
            this.labelLayout = null;
            invalidate();
        }
    }

    public void addChangingListener(MiniAppFloorChangedListener miniAppFloorChangedListener) {
        if (miniAppFloorChangedListener != null && !this.changingListeners.contains(miniAppFloorChangedListener)) {
            this.changingListeners.add(miniAppFloorChangedListener);
        }
    }

    public void removeChangingListener(MiniAppFloorChangedListener miniAppFloorChangedListener) {
        if (miniAppFloorChangedListener != null) {
            this.changingListeners.remove(miniAppFloorChangedListener);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyChangingListeners(int i, int i2) {
        MiniAppMapIndoorFloor mapIndoorFloorByIndex = getMapIndoorFloorByIndex(i2);
        MiniAppMapIndoorFloor mapIndoorFloorByIndex2 = getMapIndoorFloorByIndex(i);
        int i3 = 1;
        int i4 = mapIndoorFloorByIndex != null ? mapIndoorFloorByIndex.getmFloor_number() : 1;
        if (mapIndoorFloorByIndex2 != null) {
            i3 = mapIndoorFloorByIndex2.getmFloor_number();
        }
        for (MiniAppFloorChangedListener onFloorChanged : this.changingListeners) {
            onFloorChanged.onFloorChanged(i3, i4);
        }
    }

    public MiniAppMapIndoorFloor getCurrentMapIndoorFloor() {
        MiniAppMapIndoorFloorAdapter miniAppMapIndoorFloorAdapter = (MiniAppMapIndoorFloorAdapter) getAdapter();
        if (miniAppMapIndoorFloorAdapter != null) {
            return (MiniAppMapIndoorFloor) miniAppMapIndoorFloorAdapter.getItem(this.currentItem);
        }
        return null;
    }

    public MiniAppMapIndoorFloor getMapIndoorFloorByFloorNum(int i) {
        MiniAppMapIndoorFloorAdapter miniAppMapIndoorFloorAdapter = (MiniAppMapIndoorFloorAdapter) getAdapter();
        if (miniAppMapIndoorFloorAdapter != null) {
            for (int i2 = 0; i2 < miniAppMapIndoorFloorAdapter.getItemsCount(); i2++) {
                MiniAppMapIndoorFloor miniAppMapIndoorFloor = (MiniAppMapIndoorFloor) miniAppMapIndoorFloorAdapter.getItem(i2);
                if (miniAppMapIndoorFloor != null && i == miniAppMapIndoorFloor.getmFloor_number()) {
                    return miniAppMapIndoorFloor;
                }
            }
        }
        return null;
    }

    public MiniAppMapIndoorFloor getMapIndoorFloorByIndex(int i) {
        MiniAppMapIndoorFloorAdapter miniAppMapIndoorFloorAdapter = (MiniAppMapIndoorFloorAdapter) getAdapter();
        if (miniAppMapIndoorFloorAdapter != null) {
            return (MiniAppMapIndoorFloor) miniAppMapIndoorFloorAdapter.getItem(i);
        }
        return null;
    }

    public void addScrollingListener(MiniAppFloorScrollListener miniAppFloorScrollListener) {
        if (miniAppFloorScrollListener != null && !this.scrollingListeners.contains(miniAppFloorScrollListener)) {
            this.scrollingListeners.add(miniAppFloorScrollListener);
        }
    }

    public void removeScrollingListener(MiniAppFloorScrollListener miniAppFloorScrollListener) {
        if (miniAppFloorScrollListener != null) {
            this.scrollingListeners.remove(miniAppFloorScrollListener);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyScrollingListenersAboutStart() {
        for (MiniAppFloorScrollListener onScrollingStarted : this.scrollingListeners) {
            onScrollingStarted.onScrollingStarted(this);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyScrollingListenersAboutEnd() {
        for (MiniAppFloorScrollListener onScrollingFinished : this.scrollingListeners) {
            onScrollingFinished.onScrollingFinished(this);
        }
    }

    public boolean isLastItem() {
        MiniAppBaseFloorAdapter adapter2 = getAdapter();
        if (adapter2 != null && this.currentItem == adapter2.getItemsCount() - 1) {
            return true;
        }
        return false;
    }

    public boolean isFirstItem() {
        return this.currentItem == 0;
    }

    private void setCurrentItem(int i, boolean z, boolean z2) {
        if (this.adapter != null && this.adapter.getItemsCount() != 0) {
            if (i < 0 || i >= this.adapter.getItemsCount()) {
                if (this.isCyclic) {
                    while (i < 0) {
                        i += this.adapter.getItemsCount();
                    }
                    i %= this.adapter.getItemsCount();
                } else {
                    return;
                }
            }
            if (z) {
                scroll(i - this.currentItem, 250);
                return;
            }
            invalidateLayouts();
            int i2 = this.currentItem;
            this.currentItem = i;
            this.oldItem = i2;
            if (i2 != this.currentItem && z2) {
                notifyChangingListeners(i2, this.currentItem);
            }
            invalidate();
        }
    }

    public void setCurrentValue(int i, boolean z) {
        MiniAppBaseFloorAdapter adapter2 = getAdapter();
        for (int i2 = 0; i2 < adapter2.getItemsCount(); i2++) {
            if (i == ((MiniAppMapIndoorFloor) adapter2.getItem(i2)).mFloor_number) {
                setCurrentItem(i2, false, z);
                return;
            }
        }
    }

    public void setCurrentValue(String str) {
        if (!TextUtils.isEmpty(str)) {
            MiniAppBaseFloorAdapter adapter2 = getAdapter();
            int i = 0;
            while (i < adapter2.getItemsCount()) {
                MiniAppMapIndoorFloor miniAppMapIndoorFloor = (MiniAppMapIndoorFloor) adapter2.getItem(i);
                if (miniAppMapIndoorFloor == null || !TextUtils.equals(String.valueOf(miniAppMapIndoorFloor.getmFloor_number()), str)) {
                    i++;
                } else {
                    setCurrentItem(i, false, true);
                    return;
                }
            }
        }
    }

    public void setCurrentValueByFloorName(String str) {
        if (!TextUtils.isEmpty(str)) {
            MiniAppBaseFloorAdapter adapter2 = getAdapter();
            int i = 0;
            while (i < adapter2.getItemsCount()) {
                MiniAppMapIndoorFloor miniAppMapIndoorFloor = (MiniAppMapIndoorFloor) adapter2.getItem(i);
                if (miniAppMapIndoorFloor == null || !TextUtils.equals(miniAppMapIndoorFloor.getmFloor_name(), str)) {
                    i++;
                } else {
                    setCurrentItem(i, false, true);
                    return;
                }
            }
        }
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void setCyclic(boolean z) {
        this.isCyclic = z;
        invalidate();
        invalidateLayouts();
    }

    private void invalidateLayouts() {
        this.itemsLayout = null;
        this.valueLayout = null;
        this.scrollingOffset = 0;
    }

    private void initResourcesIfNecessary() {
        if (this.itemsPaint == null) {
            this.itemsPaint = new TextPaint(1);
            this.itemsPaint.setTextSize(this.itemTextSize);
        }
        if (this.valuePaint == null) {
            this.valuePaint = new TextPaint(5);
            this.valuePaint.setTextSize(this.valueTextSize);
            this.valuePaint.setShadowLayer(0.1f, 0.0f, 0.1f, -4144960);
        }
        if (this.centerDrawable == null) {
            this.centerDrawable = getContext().getResources().getDrawable(R.drawable.timepicker_item);
        }
        if (this.gradientDarkerDrawable == null) {
            this.gradientDarkerDrawable = getResources().getDrawable(R.drawable.gradient_coverer_darker);
        }
        if (this.gradientLighterDrawable == null) {
            this.gradientLighterDrawable = getResources().getDrawable(R.drawable.gradient_coverer_lighter);
        }
        if (this.topShadow == null) {
            this.topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }
        if (this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }
    }

    private int getDesiredHeight(Layout layout) {
        if (layout == null) {
            return 0;
        }
        int paddingTop = getPaddingTop();
        return Math.max((((getItemHeight() * this.visibleItems) - (this.itemOffset * 2)) - this.additionalItemsHeight) + paddingTop + getPaddingBottom(), getSuggestedMinimumHeight());
    }

    public String getTextItem(int i) {
        if (this.adapter == null || this.adapter.getItemsCount() == 0) {
            return null;
        }
        int itemsCount = this.adapter.getItemsCount();
        if ((i < 0 || i >= itemsCount) && !this.isCyclic) {
            return null;
        }
        while (i < 0) {
            i += itemsCount;
        }
        return this.adapter.getItem(i % itemsCount).toString();
    }

    private String buildText(boolean z) {
        StringBuilder sb = new StringBuilder();
        int i = (this.visibleItems / 2) + 1;
        for (int i2 = this.currentItem - i; i2 <= this.currentItem + i; i2++) {
            if (z || i2 != this.currentItem) {
                String textItem = getTextItem(i2);
                if (textItem != null) {
                    sb.append(textItem);
                }
            }
            if (i2 < this.currentItem + i) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private int getMaxTextLength() {
        MiniAppBaseFloorAdapter adapter2 = getAdapter();
        if (adapter2 == null) {
            return 0;
        }
        int maximumLength = adapter2.getMaximumLength();
        if (maximumLength > 0) {
            return maximumLength;
        }
        String str = null;
        for (int max = Math.max(this.currentItem - (this.visibleItems / 2), 0); max < Math.min(this.currentItem + this.visibleItems, adapter2.getItemsCount()); max++) {
            String obj = adapter2.getItem(max).toString();
            if (obj != null && (str == null || str.length() < obj.length())) {
                str = obj;
            }
        }
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int getItemHeight() {
        if (this.itemHeight != 0) {
            return this.itemHeight;
        }
        if (this.visibleItems == 1 && this.valueBorderDrawable != null) {
            return this.valueBorderDrawable.getIntrinsicHeight();
        }
        if (this.itemsLayout == null || this.itemsLayout.getLineCount() <= 2) {
            return getHeight() / this.visibleItems;
        }
        this.itemHeight = this.itemsLayout.getLineTop(2) - this.itemsLayout.getLineTop(1);
        return this.itemHeight;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int calculateLayoutWidth(int r6, int r7) {
        /*
            r5 = this;
            r5.initResourcesIfNecessary()
            int r0 = r5.getMaxTextLength()
            r1 = 0
            if (r0 <= 0) goto L_0x004d
            com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppBaseFloorAdapter r2 = r5.getAdapter()
            if (r2 == 0) goto L_0x0038
            r0 = 0
        L_0x0011:
            int r3 = r2.getItemsCount()
            if (r0 >= r3) goto L_0x004f
            java.lang.Object r3 = r2.getItem(r0)
            java.lang.String r3 = r3.toString()
            android.text.TextPaint r4 = r5.valuePaint
            float r3 = android.text.Layout.getDesiredWidth(r3, r4)
            double r3 = (double) r3
            double r3 = java.lang.Math.ceil(r3)
            float r3 = (float) r3
            int r4 = r5.itemsWidth
            float r4 = (float) r4
            int r4 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r4 >= 0) goto L_0x0035
            int r3 = (int) r3
            r5.itemsWidth = r3
        L_0x0035:
            int r0 = r0 + 1
            goto L_0x0011
        L_0x0038:
            java.lang.String r2 = "B"
            android.text.TextPaint r3 = r5.valuePaint
            float r2 = android.text.Layout.getDesiredWidth(r2, r3)
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            float r2 = (float) r2
            float r0 = (float) r0
            float r0 = r0 * r2
            int r0 = (int) r0
            r5.itemsWidth = r0
            goto L_0x004f
        L_0x004d:
            r5.itemsWidth = r1
        L_0x004f:
            int r0 = r5.itemsWidth
            int r2 = r5.addtionalItemSpace
            int r0 = r0 + r2
            r5.itemsWidth = r0
            r5.labelWidth = r1
            java.lang.String r0 = r5.label
            if (r0 == 0) goto L_0x0074
            java.lang.String r0 = r5.label
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x0074
            java.lang.String r0 = r5.label
            android.text.TextPaint r2 = r5.valuePaint
            float r0 = android.text.Layout.getDesiredWidth(r0, r2)
            double r2 = (double) r0
            double r2 = java.lang.Math.ceil(r2)
            int r0 = (int) r2
            r5.labelWidth = r0
        L_0x0074:
            r0 = 1073741824(0x40000000, float:2.0)
            r2 = 1
            if (r7 != r0) goto L_0x007b
        L_0x0079:
            r0 = r6
            goto L_0x009c
        L_0x007b:
            int r0 = r5.itemsWidth
            int r3 = r5.labelWidth
            int r0 = r0 + r3
            int r3 = r5.horizontalPadding
            int r3 = r3 * 2
            int r0 = r0 + r3
            int r3 = r5.labelWidth
            if (r3 <= 0) goto L_0x008c
            int r3 = r5.labelOffset
            int r0 = r0 + r3
        L_0x008c:
            int r3 = r5.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r0, r3)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r7 != r3) goto L_0x009b
            if (r6 >= r0) goto L_0x009b
            goto L_0x0079
        L_0x009b:
            r2 = 0
        L_0x009c:
            if (r2 == 0) goto L_0x00cb
            int r7 = r5.labelOffset
            int r6 = r6 - r7
            int r7 = r5.horizontalPadding
            int r7 = r7 * 2
            int r6 = r6 - r7
            if (r6 > 0) goto L_0x00ac
            r5.labelWidth = r1
            r5.itemsWidth = r1
        L_0x00ac:
            int r7 = r5.labelWidth
            if (r7 <= 0) goto L_0x00c6
            int r7 = r5.itemsWidth
            double r1 = (double) r7
            double r3 = (double) r6
            double r1 = r1 * r3
            int r7 = r5.itemsWidth
            int r3 = r5.labelWidth
            int r7 = r7 + r3
            double r3 = (double) r7
            double r1 = r1 / r3
            int r7 = (int) r1
            r5.itemsWidth = r7
            int r7 = r5.itemsWidth
            int r6 = r6 - r7
            r5.labelWidth = r6
            goto L_0x00cb
        L_0x00c6:
            int r7 = r5.labelOffset
            int r6 = r6 + r7
            r5.itemsWidth = r6
        L_0x00cb:
            int r6 = r5.itemsWidth
            if (r6 <= 0) goto L_0x00d6
            int r6 = r5.itemsWidth
            int r7 = r5.labelWidth
            r5.createLayouts(r6, r7)
        L_0x00d6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.calculateLayoutWidth(int, int):int");
    }

    private void createLayouts(int i, int i2) {
        if (this.itemsLayout == null || this.itemsLayout.getWidth() > i) {
            StaticLayout staticLayout = new StaticLayout(buildText(this.isScrollingPerformed), this.itemsPaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.additionalItemsHeight, false);
            this.itemsLayout = staticLayout;
        } else {
            this.itemsLayout.increaseWidthTo(i);
        }
        String str = null;
        if (!this.isScrollingPerformed && (this.valueLayout == null || this.valueLayout.getWidth() > i)) {
            if (getAdapter() != null) {
                str = getAdapter().getItem(this.currentItem).toString();
            }
            if (str == null) {
                str = "";
            }
            StaticLayout staticLayout2 = new StaticLayout(str, this.valuePaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.additionalItemsHeight, false);
            this.valueLayout = staticLayout2;
        } else if (this.isScrollingPerformed) {
            this.valueLayout = null;
        } else {
            this.valueLayout.increaseWidthTo(i);
        }
        if (i2 > 0) {
            if (this.labelLayout == null || this.labelLayout.getWidth() > i2) {
                StaticLayout staticLayout3 = new StaticLayout(this.label, this.valuePaint, i2, Alignment.ALIGN_NORMAL, 1.0f, (float) this.additionalItemsHeight, false);
                this.labelLayout = staticLayout3;
                return;
            }
            this.labelLayout.increaseWidthTo(i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int calculateLayoutWidth = calculateLayoutWidth(size, mode);
        boolean z = true;
        int i4 = (this.adapter != null ? this.adapter.getItemsCount() : 5) >= 2 ? 3 : 1;
        if (mode2 == 1073741824) {
            i3 = getDesiredHeight(this.itemsLayout);
            if (i3 > size2 && this.visibleItems > 1) {
                while (i3 > size2 && this.visibleItems > 1) {
                    this.visibleItems -= 2;
                    i3 = getDesiredHeight(this.itemsLayout);
                }
                invalidateLayouts();
            }
        } else {
            int desiredHeight = getDesiredHeight(this.itemsLayout);
            if (mode2 == Integer.MIN_VALUE) {
                if (desiredHeight > size2 && this.visibleItems > 1) {
                    while (desiredHeight > size2 && this.visibleItems > 1) {
                        this.visibleItems -= 2;
                        desiredHeight = getDesiredHeight(this.itemsLayout);
                    }
                    invalidateLayouts();
                } else if (desiredHeight < size2 && this.visibleItems + 2 <= i4) {
                    while (true) {
                        if (desiredHeight >= size2 || this.visibleItems + 2 > i4) {
                            break;
                        }
                        this.visibleItems += 2;
                        desiredHeight = getDesiredHeight(this.itemsLayout);
                        if (desiredHeight >= size2) {
                            if (desiredHeight != size2) {
                                this.visibleItems -= 2;
                                desiredHeight = getDesiredHeight(this.itemsLayout);
                            }
                        }
                    }
                    invalidateLayouts();
                }
                i3 = desiredHeight;
                size2 = Math.min(i3, size2);
            } else {
                size2 = desiredHeight;
                i3 = size2;
            }
        }
        if (size2 >= i3) {
            z = false;
        }
        resetViewState(z);
        calculateIndoorLocation();
        setMeasuredDimension(calculateLayoutWidth, size2);
    }

    private void resetViewState(boolean z) {
        if (this.mContainer != null && this.mContainer.get() != null) {
            int i = 0;
            boolean z2 = true;
            if (this.visibleItems <= 1 && ((IContainer) this.mContainer.get()).invisibleWhenSmallerThanDesiredHeight() && z) {
                z2 = false;
            }
            IContainer iContainer = (IContainer) this.mContainer.get();
            if (!z2) {
                i = 4;
            }
            iContainer.setDesireVisibilty(i);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.visibleItems > 1) {
            canvas.clipRect(0, getPaddingTop() - 5, getWidth(), (getHeight() - getPaddingBottom()) + 5);
        }
        if (this.itemsLayout == null) {
            if (this.itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), UCCore.VERIFY_POLICY_QUICK);
            } else {
                createLayouts(this.itemsWidth, this.labelWidth);
            }
        }
        drawValueBorder(canvas);
        if (this.itemsWidth > 0) {
            canvas.save();
            canvas.translate((float) this.horizontalPadding, (float) (-this.itemOffset));
            drawItems(canvas);
            drawValue(canvas);
            canvas.restore();
        }
        drawGradientCover(canvas);
    }

    private void drawShadows(Canvas canvas) {
        this.topShadow.setBounds(0, 0, getWidth(), getHeight() / this.visibleItems);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, getHeight() - (getHeight() / this.visibleItems), getWidth(), getHeight());
        this.bottomShadow.draw(canvas);
    }

    private void drawValue(Canvas canvas) {
        this.valuePaint.setColor(this.valueTextColor);
        this.valuePaint.drawableState = getDrawableState();
        Rect rect = new Rect();
        this.itemsLayout.getLineBounds(this.visibleItems / 2, rect);
        if (this.labelLayout != null) {
            canvas.save();
            canvas.translate((float) (this.itemsLayout.getWidth() + this.labelOffset), (float) rect.top);
            this.labelLayout.draw(canvas);
            canvas.restore();
        }
        if (this.valueLayout != null) {
            canvas.save();
            canvas.translate(0.0f, (float) (((rect.top + this.scrollingOffset) + getPaddingTop()) - 5));
            this.valueLayout.draw(canvas);
            canvas.restore();
        }
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, (float) ((-this.itemsLayout.getLineTop(1)) + this.scrollingOffset + getPaddingTop()));
        this.itemsPaint.setColor(this.itemTextColor);
        this.itemsPaint.drawableState = getDrawableState();
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawCenterRect(Canvas canvas) {
        int itemHeight2 = getItemHeight() / 2;
        int height = (getHeight() / 2) + itemHeight2;
        this.centerDrawable.setBounds(0, height - itemHeight2, getWidth(), height + itemHeight2);
        this.centerDrawable.draw(canvas);
    }

    private void drawValueBorder(Canvas canvas) {
        canvas.save();
        canvas.translate((float) (getWidth() > this.valueBorderDrawable.getIntrinsicWidth() ? (getWidth() - this.valueBorderDrawable.getIntrinsicWidth()) / 2 : 0), (float) ((getHeight() / 2) - (this.valueBorderDrawable.getIntrinsicHeight() / 2)));
        this.valueBorderDrawable.setBounds(0, 0, this.valueBorderDrawable.getIntrinsicWidth(), this.valueBorderDrawable.getIntrinsicHeight());
        this.valueBorderDrawable.draw(canvas);
        canvas.restore();
    }

    private void calculateIndoorLocation() {
        int i = 0;
        if (TextUtils.equals("", this.mCurrentLocationFloor)) {
            this.mLocationType = 0;
        } else if (this.adapter == null || this.adapter.getItemsCount() <= 0) {
            this.mLocationType = 0;
        } else {
            int itemsCount = this.adapter.getItemsCount();
            while (true) {
                if (i >= itemsCount) {
                    i = -1;
                    break;
                } else if (TextUtils.equals(String.valueOf(((MiniAppMapIndoorFloor) this.adapter.getItem(i)).getmFloor_number()), this.mCurrentLocationFloor)) {
                    break;
                } else {
                    i++;
                }
            }
            if (-1 != i) {
                int displayPosition = getDisplayPosition(i);
                int i2 = (this.visibleItems / 2) + 1;
                if (displayPosition == (this.currentItem - i2) - 1) {
                    this.mLocationType = 1;
                } else if (displayPosition == this.currentItem + i2 + 1) {
                    this.mLocationType = 3;
                } else {
                    this.mLocationType = 2;
                    this.mLocationHeight = (int) (((((double) ((displayPosition - this.currentItem) + i2)) - 0.5d) * ((double) getItemHeight())) - (((double) this.additionalItemsHeight) * 0.5d));
                }
            }
        }
    }

    private int getDisplayPosition(int i) {
        int i2 = (this.visibleItems / 2) + 1;
        if (i <= this.currentItem - i2) {
            return (this.currentItem - i2) - 1;
        }
        if (i <= this.currentItem - i2 || i >= this.currentItem + i2) {
            return this.currentItem + i2 + 1;
        }
        return i;
    }

    private void drawGradientCover(Canvas canvas) {
        int i;
        getItemHeight();
        getHeight();
        int maxTextLength = getMaxTextLength();
        if (maxTextLength > 0) {
            MiniAppBaseFloorAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                int i2 = 0;
                for (int i3 = 0; i3 < adapter2.getItemsCount(); i3++) {
                    float ceil = (float) Math.ceil((double) Layout.getDesiredWidth(adapter2.getItem(i3).toString(), this.itemsPaint));
                    if (((float) i2) < ceil) {
                        i2 = (int) ceil;
                    }
                }
                i = i2;
            } else {
                i = (int) (((float) maxTextLength) * ((float) Math.ceil((double) Layout.getDesiredWidth(DiskFormatter.B, this.itemsPaint))));
            }
        } else {
            i = 0;
        }
        if (i == 0) {
            i = getWidth();
        }
        if (getVisibleItems() > 3) {
            this.gradientDarkerDrawable.setBounds((getWidth() - i) / 2, (getPaddingTop() + 0) - 5, ((getWidth() - i) / 2) + i, ((getPaddingTop() + 0) - 5) + getItemHeight());
            this.gradientLighterDrawable.setBounds((getWidth() - i) / 2, ((getHeight() - getPaddingBottom()) + 5) - getItemHeight(), ((getWidth() - i) / 2) + i, (getHeight() - getPaddingBottom()) + 5);
            this.gradientDarkerDrawable.draw(canvas);
            this.gradientLighterDrawable.draw(canvas);
            return;
        }
        if (getVisibleItems() == 3) {
            this.gradientDarkerDrawable.setBounds((getWidth() - i) / 2, (getPaddingTop() + 0) - 5, ((getWidth() - i) / 2) + i, (getHeight() - this.valueBorderDrawable.getIntrinsicHeight()) / 2);
            this.gradientLighterDrawable.setBounds((getWidth() - i) / 2, ((getHeight() - this.valueBorderDrawable.getIntrinsicHeight()) / 2) + this.valueBorderDrawable.getIntrinsicHeight() + 5, ((getWidth() - i) / 2) + i, (getHeight() - getPaddingBottom()) + 5);
            this.gradientDarkerDrawable.draw(canvas);
            this.gradientLighterDrawable.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getAdapter() != null && !this.gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
            justify(false);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doScroll(int i) {
        if (getHeight() > 0 && getItemHeight() > 0) {
            this.scrollingOffset += i;
            int itemHeight2 = this.scrollingOffset / getItemHeight();
            int i2 = this.currentItem - itemHeight2;
            if (this.isCyclic && this.adapter.getItemsCount() > 0) {
                while (i2 < 0) {
                    i2 += this.adapter.getItemsCount();
                }
                i2 %= this.adapter.getItemsCount();
            } else if (!this.isScrollingPerformed) {
                i2 = Math.min(Math.max(i2, 0), this.adapter.getItemsCount() - 1);
            } else if (i2 < 0) {
                itemHeight2 = this.currentItem;
                i2 = 0;
            } else if (i2 >= this.adapter.getItemsCount()) {
                itemHeight2 = (this.currentItem - this.adapter.getItemsCount()) + 1;
                i2 = this.adapter.getItemsCount() - 1;
            }
            int i3 = this.scrollingOffset;
            if (i2 != this.currentItem) {
                setCurrentItem(i2, false, false);
            } else {
                invalidate();
            }
            this.scrollingOffset = i3 - (itemHeight2 * getItemHeight());
            if (this.scrollingOffset > getHeight()) {
                this.scrollingOffset = (this.scrollingOffset % getHeight()) + getHeight();
            }
        }
    }

    /* access modifiers changed from: private */
    public void setNextMessage(int i) {
        clearMessages();
        this.animationHandler.sendEmptyMessage(i);
    }

    /* access modifiers changed from: private */
    public void clearMessages() {
        this.animationHandler.removeMessages(0);
        this.animationHandler.removeMessages(1);
        this.animationHandler.removeMessages(2);
    }

    /* access modifiers changed from: private */
    public void justify(boolean z) {
        if (this.adapter != null) {
            boolean z2 = false;
            this.distanceYOffset = 0;
            this.lastScrollY = 0;
            int i = this.scrollingOffset;
            int itemHeight2 = getItemHeight();
            if (i <= 0 ? this.currentItem > 0 : this.currentItem < this.adapter.getItemsCount()) {
                z2 = true;
            }
            if ((this.isCyclic || z2 || z) && Math.abs((float) i) > ((float) itemHeight2) / 2.0f) {
                i = i < 0 ? i + itemHeight2 + 1 : i - (itemHeight2 + 1);
            }
            int i2 = i;
            if (Math.abs(i2) > 1) {
                this.scroller.startScroll(0, 0, 0, i2, 250);
                setNextMessage(1);
                return;
            }
            finishScrolling();
        }
    }

    /* access modifiers changed from: private */
    public void startScrolling() {
        if (!this.isScrollingPerformed) {
            this.isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }
    }

    /* access modifiers changed from: 0000 */
    public void finishScrolling() {
        if (this.isScrollingPerformed) {
            notifyScrollingListenersAboutEnd();
            notifyChangingListeners(this.oldItem, this.currentItem);
            this.isScrollingPerformed = false;
        }
        invalidateLayouts();
        invalidate();
    }

    public void scroll(int i, int i2) {
        this.scroller.forceFinished(true);
        this.lastScrollY = this.scrollingOffset;
        this.scroller.startScroll(0, this.lastScrollY, 0, (i * getItemHeight()) - this.lastScrollY, i2);
        setNextMessage(0);
        startScrolling();
    }
}
