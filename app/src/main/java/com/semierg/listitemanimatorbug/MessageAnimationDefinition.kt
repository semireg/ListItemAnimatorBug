/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semireg.scout.ui.adapter.animation

import com.semierg.listitemanimatorbug.animation.AnimationDefinition
import com.semierg.listitemanimatorbug.animation.AnimationPayload
import com.semierg.listitemanimatorbug.animation.Animations

/**
 * Used to generate animations based on app-specific business logic that is shared across the app.
 * Created by caylan on 3/29/17.
 */
class MessageAnimationDefinition {
    companion object {
        fun createPayload(oldItem: String, newItem: String): AnimationPayload? {
            val definition = create(oldItem, newItem)
            return AnimationPayload.create(definition)
        }

        fun create(oldItem: String, newItem: String): AnimationDefinition? {
            var retval: AnimationDefinition? = null

            retval = Animations.throb()

            return retval
        }

    }
}