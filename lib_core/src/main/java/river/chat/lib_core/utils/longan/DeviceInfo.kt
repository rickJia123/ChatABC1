/*
 * Copyright (c) 2021. Dylan Cai
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

package river.chat.lib_core.utils.longan

import android.os.Build

inline val sdkVersionName: String get() = Build.VERSION.RELEASE

inline val sdkVersionCode: Int get() = Build.VERSION.SDK_INT

inline val deviceManufacturer: String get() = Build.MANUFACTURER

inline val deviceModel: String get() = Build.MODEL

  var deviceOaid:String=""

inline fun deviceInfos()=
  """
        手机型号:${Build.MODEL}
        系统版本:${Build.VERSION.RELEASE}
        版本显示:${Build.DISPLAY}
        系统定制商:${Build.BRAND}】
        ROM制造商:${Build.MANUFACTURER}
        """.trimIndent() // 这行返回的是rom定制商的名称

