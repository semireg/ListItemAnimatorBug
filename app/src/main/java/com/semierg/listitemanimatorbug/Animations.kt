/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semierg.listitemanimatorbug.animation

import com.semierg.listitemanimatorbug.R

/**
 * Default animation definitions.<br>
 */
object Animations {
    val DEFAULT_THROB = throb("default_throb")

    /** create a new throb animation definition */
    fun throb(name: String = "throb", parentRid: Int = 0, viewRid: Int = R.id.row_circle): AnimationDefinition {
        return ResourceAnimationDefinition(name, R.anim.throb, parentRid, viewRid)
    }
}