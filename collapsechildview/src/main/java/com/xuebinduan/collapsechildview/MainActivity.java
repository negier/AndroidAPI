package com.xuebinduan.collapsechildview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private boolean isCollapse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View viewParent = findViewById(R.id.view_parent);
        View viewChild = findViewById(R.id.view_child);

        findViewById(R.id.image_collapse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这个代码不要放外面哦，放外面还没测量完呢，这时是获取不到宽高等信息的。
                int parentHeight = viewParent.getHeight();
                int childHeight = viewChild.getHeight();

                ValueAnimator anim;
                isCollapse = !isCollapse;
                if (isCollapse){
                    //折叠
                    anim = ValueAnimator.ofInt(parentHeight,parentHeight-childHeight);
                    ((ImageView) v).setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));
                }else{
                    //显示
                    anim = ValueAnimator.ofInt(parentHeight,parentHeight+childHeight);
                    ((ImageView) v).setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
                anim.setDuration(300);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams = viewParent.getLayoutParams();
                        layoutParams.height = currentValue;
                        viewParent.setLayoutParams(layoutParams);
                    }
                });
                anim.start();
            }
        });
    }
}