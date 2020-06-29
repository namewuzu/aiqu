package com.diankong.sexstory.mobile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.utils.DensityUtils;


/**
 * 深圳喂车科技有限公司源代码，版权@归喂车车所有。
 * 项目: weicheche_b 商户端
 * 作者: created by 熊凯 on 2016/10/9 17:31
 * 邮箱: kai.xiong@weicheche.cn
 * 描述: SettingButton
 */

public class SettingButton extends FrameLayout {

    private TextView _titleText;
    private TextView _subtitle;
    private ImageView _logo;
    private LinearLayout _button;
    private View _divider;
    private ImageView iv_go;


    public SettingButton(Context context) {
        super(context);
        //  setOnClickListener(this);
    }

    public SettingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //  setOnClickListener(this);
        LayoutInflater.from(context).inflate(R.layout.setting_button, this);
        _titleText = (TextView) findViewById(R.id.title);
        _subtitle = (TextView) findViewById(R.id.subtitle);
        _logo = (ImageView) findViewById(R.id.logo);
        _button = (LinearLayout) findViewById(R.id.button);
        iv_go = (ImageView) findViewById(R.id.iv_go);
        _divider = findViewById(R.id.divider);
        _button.setClickable(true);


        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.SettingButton);
        initWidget(typedArray);

    }

    private void initWidget(TypedArray typedArray) {
        String text = typedArray.getString(R.styleable.SettingButton_tText);

        _titleText.setText(text);
        String subtext = typedArray.getString(R.styleable.SettingButton_subText);
        boolean showImg = typedArray.getBoolean(R.styleable.SettingButton_showImg, false);
        int color = typedArray.getColor(R.styleable.SettingButton_subTextColor, getResources().getColor(R.color.namecolor));
        int titleColor = typedArray.getColor(R.styleable.SettingButton_subTitleColor, getResources().getColor(R.color.titlecolor));
        float textSize = typedArray.getDimension(R.styleable.SettingButton_subTextSize, 13);
        _titleText.setTextColor(titleColor);
        _subtitle.setTextSize(textSize);
        _subtitle.setText(subtext);
        _subtitle.setTextColor(color);
        if (showImg) {
            _logo.setVisibility(VISIBLE);
            _subtitle.setVisibility(GONE);
            int resourceId = typedArray.getResourceId(R.styleable.SettingButton_imgSrc, R.mipmap.ic_mrtx);
            _logo.setImageResource(resourceId);
        } else {
            _logo.setVisibility(GONE);
            _subtitle.setVisibility(VISIBLE);
        }

        int backResource = typedArray.getResourceId(R.styleable.SettingButton_backSelector, R.drawable.white_graylight_selecter);
        _button.setBackgroundResource(backResource);

        boolean showLine = typedArray.getBoolean(R.styleable.SettingButton_showLine, false);
        if (showLine) {

            int lineHeight = typedArray.getInteger(R.styleable.SettingButton_lineHeight, 1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dpToPx(lineHeight));
            _divider.setLayoutParams(layoutParams);
            _divider.setVisibility(VISIBLE);
        } else {
            _divider.setVisibility(GONE);
        }

        typedArray.recycle();

    }

    public SettingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTitle(String text) {
        _titleText.setText(text);
    }

    public ImageView getImageView() {
        return _logo;
    }

    public void imageGone() {
        _logo.setVisibility(GONE);
    }

    public void imageVisiablity() {
        _logo.setVisibility(VISIBLE);
    }

    public void subtitleGone() {
        _subtitle.setVisibility(GONE);
    }

    public void subtitleVisiablity() {
        _subtitle.setVisibility(VISIBLE);
    }


    public void setSubtitle(String text) {
        _subtitle.setText(text);
    }

    public String getSubTitleText(){
        return _subtitle.getText().toString();
    }


    public void setButtonListener(OnClickListener l) {
        _button.setOnClickListener(l);
    }

    public void ivgoGone() {
        iv_go.setVisibility(GONE);
    }

}
