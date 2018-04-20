/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semireg.scout.ui.adapter.animation

import android.support.annotation.AnimRes
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.semierg.listitemanimatorbug.animation.AnimationDefinition
import com.semierg.listitemanimatorbug.animation.AnimationHandle
import java.lang.ref.WeakReference

/** Animations created from predefined resources */
class ResourceAnimationDefinition constructor(override val name: String, @AnimRes val animationResId: Int, val parentRid: Int,
                                              val viewRid: Int) : AnimationDefinition {
    override fun startAnimation(itemView: View, onAnimationEnd: (AnimationHandle) -> Unit): AnimationHandle? {
        val parent = when (parentRid) {
            0 -> itemView
            else -> itemView.findViewById(parentRid)
        }
        val view = parent?.findViewById<View>(viewRid) ?: return null

        val handle = Handle(view)

        val animation = AnimationUtils.loadAnimation(itemView.context, animationResId)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                onAnimationEnd.invoke(handle)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        ViewCompat.animate(view).start()
//        view.startAnimation(animation)
        return handle
    }

    private class Handle(view: View) : AnimationHandle {
        private val viewRef = WeakReference<View>(view)

        override fun cancel() {
            ViewCompat.animate(viewRef.get()!!).cancel()
//            viewRef.get()?.clearAnimation()
        }
    }
}