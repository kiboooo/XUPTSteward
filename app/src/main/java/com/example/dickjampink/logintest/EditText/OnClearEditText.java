package com.example.dickjampink.logintest.EditText;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.dickjampink.logintest.R;

/**
 * Created by DickJampink on 2017/4/23.
 */

public class OnClearEditText extends AppCompatEditText implements
        View.OnFocusChangeListener, TextWatcher {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public OnClearEditText(Context context) {
        this(context, null);
    }

    public OnClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public OnClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    /**
     * setClearIconVisible()方法，
     * 设置隐藏和显示清除图标的方法，
     * 我们这里不是调用setVisibility()方法，setVisibility()这个方法是针对View的，
     * 我们可以调用setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)来设置上下左右的图标
     *
     * setOnFocusChangeListener(this)
     * 为输入框设置焦点改变监听，如果输入框有焦点，我们判断输入框的值是否为空，为空就隐藏清除图标，否则就显示
     *
     * addTextChangedListener(this)
     * 为输入框设置内容改变监听，其实很简单呢，
     * 当输入框里面的内容发生改变的时候，我们需要处理显示和隐藏清除小图标，里面的内容长度不为0我们就显示，
     * 否是就隐藏，但这个需要输入框有焦点我们才改变显示或者隐藏，为什么要需要焦点，
     * 比如我们一个登陆界面，我们保存了用户名和密码，在登陆界面onCreate()的时候，我们把我们保存的密码显示在用户名输入框和密码输入框里面，
     * 输入框里面内容发生改变，导致用户名输入框和密码输入框里面的清除小图标都显示了，这显然不是我们想要的效果，所以加了一个是否有焦点的判断
     */
    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];//0-左  1-上  2-右  3-下
        if (mClearDrawable == null) {
//     throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.delete);
        }

        //设置距离
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        //默认设置隐藏图标
        setClearIconVisible(false);

        //设置焦点改变的监听
        setOnFocusChangeListener(this);

        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     * 这个方法是在Text改变过程中触发调用的，它的意思就是说在原有的文本s中，
     * 从start开始的count个字符替换长度为before的旧文本，注意这里没有将要之类的字眼，
     *
     * 也就是说一句执行了替换动作
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if(hasFoucs){
            setClearIconVisible(s.length() > 0);
        }
    }

    /**
     * 这个方法是在Text改变之前被调用，它的意思就是说在原有的文本s中，
     * 从start开始的count个字符将会被一个新的长度为after的文本替换，注意这里是将被替换，还没有被替换。、
     * 以下并没有重写该方法
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
