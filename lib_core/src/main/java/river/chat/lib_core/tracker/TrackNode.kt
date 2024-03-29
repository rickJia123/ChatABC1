/*
 * Copyright (c) 2022. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package river.chat.lib_core.tracker

import android.app.Activity
import river.chat.lib_core.R

fun interface TrackNode {
  fun fillTackParams(params: TrackParams)
}

fun TrackNode(vararg params: Pair<String, String>): TrackNode =
  TrackNode { it.putAll(mutableMapOf(*params)) }

fun Activity.PageTrackNode(vararg params: Pair<String, String>): TrackNode =
  PageTrackNode(emptyMap()) { it.putAll(mutableMapOf(*params)) }

fun Activity.PageTrackNode(referrerKeyMap: MutableMap<String, String>, vararg params: Pair<String, String>): TrackNode =
  PageTrackNode( ) { it.putAll(mutableMapOf(*params)) }

@Suppress("UNCHECKED_CAST")
fun Activity.PageTrackNode(referrerKeyMap: Map<String, String> = emptyMap(), trackNode: TrackNode = TrackNode { }): TrackNode {
  val referrerParams = intent.getSerializableExtra(KEY_TRACK_PARAMS) as? Map<String, Any>
  val threadNodeSet = intent.getStringArrayExtra(KEY_TRACK_THREAD_NODES)?.map { allThreadNodes[it] }?.toMutableSet()
  window.decorView.setTag(R.id.tag_thread_nodes, threadNodeSet)
  return TrackNode { params ->
    referrerParams?.forEach {
      params.put(referrerKeyMap.getOrElse(it.key) { it.key }, it.value)
    }
    trackNode.fillTackParams(params)
  }
}

abstract class ThreadTrackNode : TrackNode {
  @JvmName("removeKey")
  fun TrackParams.remove(key: String) = internalRemove(key)
}