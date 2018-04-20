/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semierg.listitemanimatorbug.animation

/** represents the animations to be executed for each item when updates occur to the adapter */
data class AnimationPayload(val animationDefs: List<AnimationDefinition>) {
    companion object Factory {
        fun create(): AnimationPayload? {
            return null
        }

        fun create(def: AnimationDefinition?): AnimationPayload? {
            def ?: return null
            return AnimationPayload(listOf(def))
        }

        fun create(def1: AnimationDefinition?, def2: AnimationDefinition?): AnimationPayload? {
            // optimize creation of return list
            if (def1 == null) {
                if (def2 == null) {
                    return null
                } else {
                    return create(def2)
                }
            } else {
                if (def2 == null) {
                    return create(def1)
                } else {
                    return AnimationPayload(listOf(def1, def2))
                }
            }
        }
    }
}