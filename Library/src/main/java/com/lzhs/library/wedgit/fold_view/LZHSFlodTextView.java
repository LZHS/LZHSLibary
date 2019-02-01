package com.lzhs.library.wedgit.fold_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.lzhs.library.R;


/**
 * Description: 实现折叠功能的TextView  <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/9/14$ 下午2:31$ <br/>
 */
public class LZHSFlodTextView extends AppCompatTextView implements View.OnClickListener {
    private static final String TAG = "LZHSFlodTextView";
    private static final String ELLIPSIZE_END = "...";
    private static final int MAX_LINE = 4;
    private static final String EXPAND_TIP_TEXT = "收起全文";
    private static final String FOLD_TIP_TEXT = "全文";
    private static final int TIP_COLOR = 0xFF3F51B5;
    /**
     * 全文显示的位置
     */
    private static final int END = 0;
    /**
     * 显示的文本的最大行数
     */
    private int mShowMaxLine;
    /**
     * 折叠文本
     */
    private String mFoldText;
    /**
     * 展开文本
     */
    private String mExpandText;
    /**
     * 原始文本
     */
    private CharSequence mOriginalText;
    /**
     * 是否展开
     */
    private boolean isExpand;
    /**
     * 全文显示的位置
     */
    private int mTipGravity;
    /**
     * 全文文字的颜色
     */
    private int mTipColor;
    /**
     * 全文是否可点击
     */
    private boolean mTipClickable;
    /**
     * 全文点击的span
     */
    private ExpandSpan mSpan;
    private boolean flag;
    /**
     * 展开后是否显示文字提示
     */
    private boolean isShowTipAfterExpand;

    /**
     * 是否是Span的点击
     */
    private boolean isExpandSpanClick;
    /**
     * 父view是否设置了点击事件
     */
    private boolean isParentClick;

    private OnClickListener listener;

    public LZHSFlodTextView(Context context) {
        this(context, null);
    }


    public LZHSFlodTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
    }

    public LZHSFlodTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShowMaxLine = MAX_LINE;
        mSpan = new ExpandSpan();
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.LZHSFlodTextView);
            mShowMaxLine = arr.getInt(R.styleable.LZHSFlodTextView_showMaxLine, MAX_LINE);
            mTipGravity = arr.getInt(R.styleable.LZHSFlodTextView_tipGravity, END);
            mTipColor = arr.getColor(R.styleable.LZHSFlodTextView_tipColor, TIP_COLOR);
            mTipClickable = arr.getBoolean(R.styleable.LZHSFlodTextView_tipClickable, false);
            mFoldText = arr.getString(R.styleable.LZHSFlodTextView_foldText);
            mExpandText = arr.getString(R.styleable.LZHSFlodTextView_expandText);
            isShowTipAfterExpand = arr.getBoolean(R.styleable.LZHSFlodTextView_showTipAfterExpand, false);
            isParentClick = arr.getBoolean(R.styleable.LZHSFlodTextView_isSetParentClick, false);
            arr.recycle();
        }
        if (TextUtils.isEmpty(mExpandText)) {
            mExpandText = EXPAND_TIP_TEXT;
        }
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = FOLD_TIP_TEXT;
        }
    }

    @Override
    public void setText(final CharSequence text, final BufferType type) {
        if (TextUtils.isEmpty(text) || mShowMaxLine == 0)
            super.setText(text, type);
         else if (isExpand) {  //文字展开
            SpannableStringBuilder spannable = new SpannableStringBuilder(mOriginalText);
            addTip(spannable, type);
        } else {
            if (!flag) {
                getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        flag = true;
                        formatText(text, type);
                        return true;
                    }
                });
            } else  formatText(text, type);
        }
    }

    /**
     * 增加提示文字
     *
     * @param span
     * @param type
     */
    private void addTip(SpannableStringBuilder span, BufferType type) {
        if (!(isExpand && !isShowTipAfterExpand)) { //折叠或者展开并且展开后显示提示
            if (mTipGravity == END)   span.append("  ");
            else  span.append("\n");
            int length;
            if (isExpand) {
                span.append(mExpandText);
                length = mExpandText.length();
            } else {
                span.append(mFoldText);
                length = mFoldText.length();
            }
            if (mTipClickable) {
                span.setSpan(mSpan, span.length() - length, span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (isParentClick) {
                    setMovementMethod(LinkMovementMethod.getInstance());
                    setClickable(false);
                    setFocusable(false);
                    setLongClickable(false);
                } else   setMovementMethod(LinkMovementMethod.getInstance());
            }
            span.setSpan(new ForegroundColorSpan(mTipColor), span.length() - length, span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        super.setText(span, type);
    }

    private void formatText(CharSequence text, final BufferType type) {
        mOriginalText = text;
        Layout layout = getLayout();
        if (layout == null || !layout.getText().equals(mOriginalText)) {
            super.setText(mOriginalText, type);
            layout = getLayout();
        }
        if (layout == null) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    translateText(getLayout(), type);
                }
            });
        } else {
            translateText(layout, type);
        }
    }

    private void translateText(Layout layout, BufferType type) {
        if (layout.getLineCount() > mShowMaxLine) {
            SpannableStringBuilder span = new SpannableStringBuilder();
            int start = layout.getLineStart(mShowMaxLine - 1);
            int end = layout.getLineVisibleEnd(mShowMaxLine - 1);
            TextPaint paint = getPaint();
            StringBuilder builder = new StringBuilder(ELLIPSIZE_END);
            if (mTipGravity == END) {
                builder.append("  ").append(mFoldText);
                end -= paint.breakText(mOriginalText, start, end, false, paint.measureText(builder.toString()), null) + 1;
                float x = getWidth() - getPaddingLeft() - getPaddingRight() - getTextWidth(ELLIPSIZE_END.concat(mFoldText));
                while (layout.getPrimaryHorizontal(end - 1) + getTextWidth(mOriginalText.subSequence(end - 1, end).toString()) < x) {
                    end++;
                }
                end -= 2;
            } else {
                end -= paint.breakText(mOriginalText, start, end, false, paint.measureText(builder.toString()), null) + 1;
            }
            CharSequence ellipsize = mOriginalText.subSequence(0, end);
            span.append(ellipsize);
            span.append(ELLIPSIZE_END);
            addTip(span, type);
        }
    }

    public void setShowMaxLine(int mShowMaxLine) {
        this.mShowMaxLine = mShowMaxLine;
    }

    public void setFoldText(String mFoldText) {
        this.mFoldText = mFoldText;
    }

    public void setExpandText(String mExpandText) {
        this.mExpandText = mExpandText;
    }

    public void setTipGravity(int mTipGravity) {
        this.mTipGravity = mTipGravity;
    }

    public void setTipColor(int mTipColor) {
        this.mTipColor = mTipColor;
    }

    public void setTipClickable(boolean mTipClickable) {
        this.mTipClickable = mTipClickable;
    }


    public void setShowTipAfterExpand(boolean showTipAfterExpand) {
        isShowTipAfterExpand = showTipAfterExpand;
    }

    public void setParentClick(boolean parentClick) {
        isParentClick = parentClick;
    }

    @Override
    public void onClick(View v) {
        if (isExpandSpanClick) {
            isExpandSpanClick = false;
        } else {
            listener.onClick(v);
        }
    }

    private class ExpandSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            if (mTipClickable) {
                isExpand = !isExpand;
                isExpandSpanClick = true;
                setText(mOriginalText);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mTipColor);
            ds.setUnderlineText(false);
        }
    }

    /**
     * 重写，解决span跟view点击同时触发问题
     *
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        listener = l;
        super.setOnClickListener(this);
    }

    private float getTextWidth(String text) {
        Paint paint = getPaint();
        return paint.measureText(text);
    }
}

class LinkMovementMethod extends ScrollingMovementMethod {
    private static final int CLICK = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    @Override
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override
    protected boolean handleMovementKey(TextView widget, Spannable buffer, int keyCode,
                                        int movementMetaState, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                if (KeyEvent.metaStateHasNoModifiers(movementMetaState)) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getRepeatCount() == 0 && action(CLICK, widget, buffer)) {
                        return true;
                    }
                }
                break;

        }
        return super.handleMovementKey(widget, buffer, keyCode, movementMetaState, event);
    }

    @Override
    protected boolean up(TextView widget, Spannable buffer) {
        if (action(UP, widget, buffer)) {
            return true;
        }

        return super.up(widget, buffer);
    }

    @Override
    protected boolean down(TextView widget, Spannable buffer) {
        if (action(DOWN, widget, buffer)) {
            return true;
        }

        return super.down(widget, buffer);
    }

    @Override
    protected boolean left(TextView widget, Spannable buffer) {
        if (action(UP, widget, buffer)) {
            return true;
        }

        return super.left(widget, buffer);
    }

    @Override
    protected boolean right(TextView widget, Spannable buffer) {
        if (action(DOWN, widget, buffer)) {
            return true;
        }

        return super.right(widget, buffer);
    }

    private boolean action(int what, TextView widget, Spannable buffer) {
        Layout layout = widget.getLayout();

        int padding = widget.getTotalPaddingTop() +
                widget.getTotalPaddingBottom();
        int areaTop = widget.getScrollY();
        int areaBot = areaTop + widget.getHeight() - padding;

        int lineTop = layout.getLineForVertical(areaTop);
        int lineBot = layout.getLineForVertical(areaBot);

        int first = layout.getLineStart(lineTop);
        int last = layout.getLineEnd(lineBot);

        ClickableSpan[] candidates = buffer.getSpans(first, last, ClickableSpan.class);

        int a = Selection.getSelectionStart(buffer);
        int b = Selection.getSelectionEnd(buffer);

        int selStart = Math.min(a, b);
        int selEnd = Math.max(a, b);

        if (selStart < 0) {
            if (buffer.getSpanStart(FROM_BELOW) >= 0) {
                selStart = selEnd = buffer.length();
            }
        }

        if (selStart > last)
            selStart = selEnd = Integer.MAX_VALUE;
        if (selEnd < first)
            selStart = selEnd = -1;

        switch (what) {
            case CLICK:
                if (selStart == selEnd) {
                    return false;
                }

                ClickableSpan[] link = buffer.getSpans(selStart, selEnd, ClickableSpan.class);

                if (link.length != 1)
                    return false;

                link[0].onClick(widget);
                break;

            case UP:
                int bestStart, bestEnd;

                bestStart = -1;
                bestEnd = -1;

                for (int i = 0; i < candidates.length; i++) {
                    int end = buffer.getSpanEnd(candidates[i]);

                    if (end < selEnd || selStart == selEnd) {
                        if (end > bestEnd) {
                            bestStart = buffer.getSpanStart(candidates[i]);
                            bestEnd = end;
                        }
                    }
                }

                if (bestStart >= 0) {
                    Selection.setSelection(buffer, bestEnd, bestStart);
                    return true;
                }

                break;

            case DOWN:
                bestStart = Integer.MAX_VALUE;
                bestEnd = Integer.MAX_VALUE;

                for (int i = 0; i < candidates.length; i++) {
                    int start = buffer.getSpanStart(candidates[i]);

                    if (start > selStart || selStart == selEnd) {
                        if (start < bestStart) {
                            bestStart = start;
                            bestEnd = buffer.getSpanEnd(candidates[i]);
                        }
                    }
                }

                if (bestEnd < Integer.MAX_VALUE) {
                    Selection.setSelection(buffer, bestStart, bestEnd);
                    return true;
                }

                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);

            if (links.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    links[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(links[0]),
                            buffer.getSpanEnd(links[0]));
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
                return false;
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }

    @Override
    public void initialize(TextView widget, Spannable text) {
        Selection.removeSelection(text);
        text.removeSpan(FROM_BELOW);
    }

    @Override
    public void onTakeFocus(TextView view, Spannable text, int dir) {
        Selection.removeSelection(text);

        if ((dir & View.FOCUS_BACKWARD) != 0) {
            text.setSpan(FROM_BELOW, 0, 0, Spannable.SPAN_POINT_POINT);
        } else {
            text.removeSpan(FROM_BELOW);
        }
    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LinkMovementMethod();

        return sInstance;
    }

    private static LinkMovementMethod sInstance;
    private static Object FROM_BELOW = new NoCopySpan.Concrete();
}

