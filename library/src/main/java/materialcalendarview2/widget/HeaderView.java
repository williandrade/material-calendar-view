/*
 * Copyright (C) 2015 Jonatan E. Salas { link: http://the-android-developer.blogspot.com.ar }
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package materialcalendarview2.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import materialcalendarview2.R;
import materialcalendarview2.exception.IllegalViewArgumentException;

import static materialcalendarview2.util.CalendarUtil.getDateTitle;
import static materialcalendarview2.exception.IllegalViewArgumentException.*;

/**
 * LinearLayout class that provides the Calendar Title View.
 *
 * @author jonatan.salas
 */
public class HeaderView extends LinearLayout {
    private OnButtonClickedListener onButtonClicked;
    private ImageView nextButton;
    private ImageView backButton;
    private TextView dateTitle;
    private View view;

    private int monthIndex = 0;

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final int id = v.getId();

            if (id == R.id.back_button) {
                monthIndex--;
            } else {
                monthIndex++;
            }

            prepareListeners(v);
        }
    };

    /**
     * Constructor with arguments. It only takes the Context as param.
     *
     * @param context the context used to inflate or get resources
     */
    public HeaderView(Context context) {
        this(context, null);
    }

    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     */
    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * Constructor with arguments. It takes the Context as main param
     * and as second param an AttributeSet, and as third param the defStyleAttr.
     *
     * @param context the context used to inflate or get resources
     * @param attrs the attributes styled from a XML file
     * @param defStyleAttr int resource used to get the Styles array
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Method that init all components that are part of the HeaderView.
     */
    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.header_view, this, true);

        backButton = (ImageView) view.findViewById(R.id.back_button);
        nextButton = (ImageView) view.findViewById(R.id.next_button);
        dateTitle = (TextView) view.findViewById(R.id.date_title);

        setDateTitleText();

        dateTitle.setEnabled(true);

        backButton.setEnabled(true);
        backButton.setClickable(true);
        backButton.setOnClickListener(onClickListener);

        nextButton.setEnabled(true);
        nextButton.setClickable(true);
        nextButton.setOnClickListener(onClickListener);
    }

    /**
     * Method that sets the text in the centered TextView, the text showed is in the following format
     * "Month + Year".
     */
    private void setDateTitleText() {
        dateTitle.setText(getDateTitle(monthIndex));
        updateLayout();
    }

    /**
     * Method that changes the title and takes a view to call the Listener associated to this view.
     *
     * @param view the view to associate with the listener.
     */
    private void prepareListeners(View view) {
        setDateTitleText();

        if (null != onButtonClicked) {
            onButtonClicked.onButtonClicked(view, monthIndex);
        } else {
            throw new IllegalViewArgumentException(BUTTON_CLICK_LISTENER_NOT_NULL_MESSAGE);
        }
    }

    /**
     * This method should be called after setting a custom attribute, in order to request a new
     * layout pass.
     */
    private void updateLayout() {
        invalidate();
        requestLayout();
    }

    /**
     * Method that sets a Typeface object in the Title view.
     *
     * @param typeface a custom Typeface object create from a font file.
     */
    public void setTitleTextTypeface(Typeface typeface) {
        setTitleTextTypeface(typeface, Typeface.NORMAL);
    }

    /**
     * Method that sets a Typeface object in the Title view and applies a particular style to the
     * font.That means the font could be NORMAL, BOLD, ITALIC.
     *
     * @param typeface a custom Typeface object create from a font file.
     * @param style the style of the font.
     */
    public void setTitleTextTypeface(Typeface typeface, int style) {
        if (null != typeface) {
            dateTitle.setTypeface(typeface, style);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(TYPEFACE_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets the size of the text to show in the TitleView.
     *
     * @param size the size of the Typeface as a float value
     */
    public void setTitleTextSize(Float size) {
        if (null != size) {
            dateTitle.setTextSize(size);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(SIZE_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets the TitleView text color from a color resource id
     *
     * @param colorId the color resource id
     */
    public void setTitleTextColor(@ColorRes int colorId) {
        if (0 != colorId) {
            setTitleTextColor(getColor(colorId));
        } else {
            throw new IllegalViewArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that sets the TitleView text color from a color Integer value.
     *
     * @param color the color as an Integer value
     */
    public void setTitleTextColor(Integer color) {
        if (null != color) {
            dateTitle.setTextColor(color);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets the next Button drawable from a drawable resource id.
     *
     * @param drawableId the resource id of the drawable we want to set
     */
    public void setNextButtonDrawable(@DrawableRes int drawableId) {
        if (0 != drawableId) {
            setNextButtonDrawable(getDrawable(drawableId));
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that sets the back Button drawable from a drawable resource id.
     *
     * @param drawableId the resource id of the drawable we want to set
     */
    public void setBackButtonDrawable(@DrawableRes int drawableId) {
        if (0 != drawableId) {
            setBackButtonDrawable(getDrawable(drawableId));
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that sets the next Button drawable from a drawable object.
     *
     * @param drawable the drawable we want to set
     */
    public void setNextButtonDrawable(Drawable drawable) {
        if (null != drawable) {
            nextButton.setImageDrawable(drawable);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets the back Button drawable from a drawable object.
     *
     * @param drawable the drawable we want to set
     */
    public void setBackButtonDrawable(Drawable drawable) {
        if (null != drawable) {
            backButton.setImageDrawable(drawable);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that changes the next button drawable color from a color resource id int value.
     *
     * @param colorId the resource id value of the color to be applied to the drawable
     */
    public void setNextButtonDrawableColor(@ColorRes int colorId) {
        if (0 != colorId) {
            setNextButtonDrawableColor(getColor(colorId));
        } else {
            throw new IllegalViewArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that changes the back button drawable color from a color resource id int value.
     *
     * @param colorId the resource id value of the color to be applied to the drawable
     */
    public void setBackButtonDrawableColor(@ColorRes int colorId) {
        if (0 != colorId) {
            setBackButtonDrawableColor(getColor(colorId));
        } else {
            throw new IllegalViewArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that changes the next button drawable color from a color int value.
     *
     * @param color the value of the color to be applied to the drawable
     */
    public void setNextButtonDrawableColor(Integer color) {
        if (null != color) {
            nextButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that changes the back button drawable color from a color int value.
     *
     * @param color the value of the color to be applied to the drawable
     */
    public void setBackButtonDrawableColor(Integer color) {
        if (null != color) {
            backButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets a background to the HeaderView from a color id resource int value.
     *
     * @param colorId an int value representing the resource id of the color to be set.
     */
    public void setBackgroundColorResource(@ColorRes int colorId) {
        if (0 != colorId) {
            view.setBackgroundResource(colorId);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that sets a background to the HeaderView from a color id int value.
     *
     * @param color an int value representing the color to be set.
     */
    public void setBackgroundColor(Integer color) {
        if (null != color) {
            view.setBackgroundColor(color);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets a background from a drawable resource id.
     *
     * @param drawableId the resource id of the drawable to be set.
     */
    public void setBackgroundColorDrawable(@DrawableRes int drawableId) {
        if (0 != drawableId) {
            setBackgroundColorDrawable(getDrawable(drawableId));
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     * Method that sets a background from a drawable object.
     *
     * @param drawable the drawable to be set as background
     */
    public void setBackgroundColorDrawable(Drawable drawable) {
        if (null != drawable) {
            //TODO JS: Investigate how to this without a deprecated method.
            view.setBackgroundDrawable(drawable);
            updateLayout();
        } else {
            throw new IllegalViewArgumentException(DRAWABLE_NOT_NULL_MESSAGE);
        }
    }

    /**
     * Method that sets the OnButtonClicked callback.
     *
     * @param onButtonClicked the interface as an anonymous class.
     */
    public void setOnButtonClicked(@NonNull OnButtonClickedListener onButtonClicked) {
        this.onButtonClicked = onButtonClicked;
        invalidate();
    }

    /**
     * MonthIndex field getter
     *
     * @return int value representing the current month index
     */
    public int getMonthIndex() {
        return monthIndex;
    }

    /**
     * Method that gets a Drawable object by its resource id.
     *
     * @param resId int value representing the resource id of the drawable to get.
     * @return a Drawable object ready to use.
     */
    private Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * Method that gets an int color representation by its resource id.
     *
     * @param resId int value that represents the resource id of the color to get.
     * @return an int value that represents a color.
     */
    private int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * Callback interface used to handle a Button clicked.
     *
     * @author jonatan.salas
     */
    public interface OnButtonClickedListener {

        /**
         * Method that handles a button clicked by the user. This callback receives a param the
         * view touched and the int month index which represent the actual month value saved in the
         * class CalendarTitleLayout.
         *
         * @param view the view touched
         * @param monthIndex the actual month index
         */
        void onButtonClicked(@NonNull View view, int monthIndex);
    }
}