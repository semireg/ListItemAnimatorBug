/*
 * Copyright (c) 2017 Semireg Industries, LLC.  All rights reserved.
 */

package com.semierg.listitemanimatorbug.animation

import android.support.v7.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.WeakHashMap

/**
 * Standard animator for most uses of the StandardAdapter.
 * Created by tsandee on 9/30/16.
 */
class StandardItemAnimator : DraggableItemAnimator() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger("Animator")
    }

    private class AnimHolderInfo(val animationDefs: List<AnimationDefinition>) : RecyclerView.ItemAnimator.ItemHolderInfo()

    private val animationSetMap: MutableMap<RecyclerView.ViewHolder, MutableList<AnimationHandle>> = WeakHashMap()

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(state: RecyclerView.State, viewHolder: RecyclerView.ViewHolder, changeFlags: Int,
                                            payloads: MutableList<Any>): RecyclerView.ItemAnimator.ItemHolderInfo {
        if (changeFlags == RecyclerView.ItemAnimator.FLAG_CHANGED) {
            LOG.trace("recordPreLayoutInformation CHANGED {}, payloads {}", viewHolder, payloads)
            val payload = payloads.filterIsInstance(AnimationPayload::class.java).singleOrNull()
            if (payload != null && payload.animationDefs.isNotEmpty()) {
                return AnimHolderInfo(payload.animationDefs)
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

//    override fun isRunning(): Boolean {
//        val count = animationSetMap.values.flatMap { it }.count()
//        return super.isRunning() || (count > 0)
//    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder, preInfo: RecyclerView.ItemAnimator.ItemHolderInfo,
                               postInfo: RecyclerView.ItemAnimator.ItemHolderInfo): Boolean {
        cancelCurrentAnimationIfExists(newHolder)

        LOG.trace("animateChange {}, preInfo {}", newHolder, preInfo)
        if (preInfo is AnimHolderInfo) {
            animate(newHolder, preInfo.animationDefs)
            return false
        } else {
            return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
        }
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        super.endAnimation(item)

        cancelCurrentAnimationIfExists(item)
    }

    override fun endAnimations() {
        super.endAnimations()

        animationSetMap.values
                .forEach {
                    it.forEach(AnimationHandle::cancel)
                }

        animationSetMap.clear()
    }

    private fun animate(holder: RecyclerView.ViewHolder, animationDefs: List<AnimationDefinition>) {
        check(animationDefs.isNotEmpty())

        var animationHandleList = animationSetMap[holder]
        if (animationHandleList == null) {
            animationHandleList = mutableListOf()
            animationSetMap.put(holder, animationHandleList)
        }

        animationDefs
                .mapNotNullTo(animationHandleList) {
                    it.startAnimation(holder.itemView,
                            onAnimationEnd = { handle ->
                                outerAnimationEnd(holder, handle)
                            })
                }
    }

    private fun outerAnimationEnd(holder: RecyclerView.ViewHolder, animationHandle: AnimationHandle) {
        val list = animationSetMap[holder] ?: return
        list.remove(animationHandle)
        if (list.isEmpty()) {
            dispatchChangeFinished(holder, false)
//            dispatchAnimationFinished(holder) // Original
        }
    }

    private fun cancelCurrentAnimationIfExists(holder: RecyclerView.ViewHolder) {
        val list = animationSetMap[holder] ?: return
        list.forEach { it.cancel() }
        list.clear()
    }
}