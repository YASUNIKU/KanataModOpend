package com.github.yasuniku.kanata.entity.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ModAnimationDefinitions {
    public static final AnimationDefinition walk =
        AnimationDefinition.Builder.withLength(4.0F).looping().addAnimation("leg_left", new AnimationChannel(
            AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F,
                KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.75F,
                KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.0F,
                KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("leg_left_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.25F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F,
                KeyframeAnimations.degreeVec(55.31F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.5F,
                KeyframeAnimations.degreeVec(39.37F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.75F,
                KeyframeAnimations.degreeVec(20.93F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.0F,
                KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.25F,
                KeyframeAnimations.degreeVec(-4.38F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.75F,
                KeyframeAnimations.degreeVec(13.54F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.25F,
                KeyframeAnimations.degreeVec(43.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("leg_left_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,
                KeyframeAnimations.degreeVec(-21.25F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.0F,
                KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.5F,
                KeyframeAnimations.degreeVec(-41.25F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.75F,
                KeyframeAnimations.degreeVec(-30.63F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.25F,
                KeyframeAnimations.degreeVec(-1.88F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.5F,
                KeyframeAnimations.degreeVec(-16.25F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.75F,
                KeyframeAnimations.degreeVec(-29.38F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F,
                KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.25F,
                KeyframeAnimations.degreeVec(-48.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.75F,
                KeyframeAnimations.degreeVec(-38.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,
                KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.5F,
                KeyframeAnimations.degreeVec(-8.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.5F,
                KeyframeAnimations.degreeVec(-28.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.25F,
                KeyframeAnimations.degreeVec(-5.63F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("leg_right_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,
                KeyframeAnimations.degreeVec(23.12F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.75F,
                KeyframeAnimations.degreeVec(-63.13F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.0F,
                KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.5F,
                KeyframeAnimations.degreeVec(-0.63F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.75F,
                KeyframeAnimations.degreeVec(11.14F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.25F,
                KeyframeAnimations.degreeVec(39.68F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F,
                KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.25F,
                KeyframeAnimations.degreeVec(-13.13F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.5F,
                KeyframeAnimations.degreeVec(26.25F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("leg_right_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,
                KeyframeAnimations.degreeVec(-41.88F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.5F,
                KeyframeAnimations.degreeVec(-23.75F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.75F,
                KeyframeAnimations.degreeVec(36.87F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.75F,
                KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.0F,
                KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(2.25F,
                KeyframeAnimations.degreeVec(-50.63F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.25F,
                KeyframeAnimations.degreeVec(15.62F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(3.5F,
                KeyframeAnimations.degreeVec(-5.42F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        )).build();

    public static final AnimationDefinition attack = AnimationDefinition.Builder
        .withLength(0.5F)
        .addAnimation("arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0417F,
                KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.25F,
                KeyframeAnimations.degreeVec(55.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("arm_2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0417F,
                KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.25F,
                KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            )
        ))
        .addAnimation("arm_3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0417F,
                KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("arm_finger_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0417F,
                KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            )
        ))
        .addAnimation("arm_finger_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0417F,
                KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.25F,
                KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F),
                AnimationChannel.Interpolations.LINEAR
            ),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}
