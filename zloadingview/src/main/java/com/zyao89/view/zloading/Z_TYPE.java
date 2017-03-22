package com.zyao89.view.zloading;

import com.zyao89.view.zloading.circle.DoubleCircleBuilder;
import com.zyao89.view.zloading.circle.PacManBuilder;
import com.zyao89.view.zloading.clock.CircleBuilder;
import com.zyao89.view.zloading.clock.ClockBuilder;
import com.zyao89.view.zloading.star.LeafBuilder;
import com.zyao89.view.zloading.star.StarBuilder;

/**
 * Created by zyao89 on 2017/3/19.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public enum Z_TYPE
{
    CIRCLE(CircleBuilder.class),
    CIRCLE_CLOCK(ClockBuilder.class),
    STAR_LOADING(StarBuilder.class),
    LEAF_ROTATE(LeafBuilder.class),
    DOUBLE_CIRCLE(DoubleCircleBuilder.class),
    PAC_MAN(PacManBuilder.class),
    ;

    private final Class<?> mBuilderClass;

    Z_TYPE(Class<?> builderClass)
    {
        this.mBuilderClass = builderClass;
    }

    <T extends ZLoadingBuilder>T newInstance(){
        try
        {
            return (T) mBuilderClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
