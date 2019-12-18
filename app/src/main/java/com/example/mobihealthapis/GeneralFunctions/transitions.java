package com.example.mobihealthapis.GeneralFunctions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class transitions {


    public static int TransitionTitleBar(int y, int oldy, int flag_title_scroll,
                                         ViewGroup Parent, View BackArrow, View TitleBar,
                                         Animation animTitleSlideup, Animation animTitleSlideDown){
        int diff = y-oldy;
        if(diff<0)
            diff*=-1;

        if(diff>10){
            if(y>oldy && flag_title_scroll == 0 ){
                flag_title_scroll = 1;
                SlideUpDown(Parent,BackArrow,true,1);
                //transitions.FadeInOut(ll_toolbar_addevent,true,ngo_add_event.this);
                TitleBar.startAnimation(animTitleSlideup);

            }
            else if(y<oldy && flag_title_scroll==1 && y <100){
                flag_title_scroll = 0;
                SlideUpDown(Parent,BackArrow,false,1);
                //transitions.FadeInOut(ll_toolbar_addevent,false,ngo_add_event.this);
                TitleBar.startAnimation(animTitleSlideDown);
            }
            else{}

        }

        if(y==0 && flag_title_scroll==1){
            flag_title_scroll = 0;
            SlideUpDown(Parent,BackArrow,false,1);
            //transitions.FadeInOut(ll_toolbar_addevent,false,ngo_add_event.this);
            TitleBar.startAnimation(animTitleSlideDown);
        }


        return flag_title_scroll;
    }


    public static Boolean SlideUpDown(ViewGroup parent, View target, Boolean isVisible, int gravity){
        Transition transition = new Slide(Gravity.BOTTOM);
        if(gravity == 1)
            transition = new Slide(Gravity.TOP);
        else if(gravity == 2)
            transition = new Slide(Gravity.CENTER);
        else if(gravity == 3)
            transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(300);
        transition.addTarget(target.getId());
        TransitionManager.beginDelayedTransition(parent,transition);
        isVisible = !isVisible;
        target.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return isVisible;
    }

    public static Boolean FadeInOut(View target, Boolean isVisible, Context context, int duration){

        Animation mLoadAnimation;
        if(isVisible == true){
            mLoadAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        }
        else
        {
           mLoadAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        }

        mLoadAnimation.setDuration(duration);
        target.startAnimation(mLoadAnimation);
        isVisible = !isVisible;
        target.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        return isVisible;
    }

    public static void slideUp(final View view, float height){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -height);                // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);

        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public static void slideDown(final View view, float height){

        view.setVisibility(View.VISIBLE);

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -height,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        animate.setAnimationListener(null);
        view.startAnimation(animate);
    }


    public static void expand(View view) {
        Animation animation = expandAction(view);
        view.startAnimation(animation);
    }

    private static Animation expandAction(final View view) {

        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int actualheight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                view.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (actualheight * interpolatedTime);
                view.requestLayout();
            }
        };


        animation.setDuration((long) (actualheight / view.getContext().getResources().getDisplayMetrics().density));

        view.startAnimation(animation);

        return animation;


    }

    public static void collapse(final View view) {

        final int actualHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = actualHeight - (int) (actualHeight * interpolatedTime);
                    view.requestLayout();

                }
            }
        };

        animation.setDuration((long) (actualHeight/ view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }




}
