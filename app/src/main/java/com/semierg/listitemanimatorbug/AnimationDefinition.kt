/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semierg.listitemanimatorbug.animation

import android.view.View

/** basic interface for animations that can be managed by the StandardItemAnimator */
interface AnimationDefinition {
    /** used for logging */
    val name: String

    /** given the parent itemView, start the animation and return a handle with which the animation can be cancelled */
    fun startAnimation(itemView: View, onAnimationEnd: (AnimationHandle) -> Unit): AnimationHandle?
}