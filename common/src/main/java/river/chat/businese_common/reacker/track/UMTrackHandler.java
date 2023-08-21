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

package river.chat.businese_common.reacker.track;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import androidx.annotation.NonNull;
import river.chat.common.BuildConfig;
import river.chat.lib_core.tracker.TrackHandler;

/**
 * @author Dylan Cai
 */
public class UMTrackHandler implements TrackHandler {

    @Override
    public void onEvent(@NonNull Context context,
                        @NonNull String eventId,
                        @NonNull Map<String, String> params) {
        if (BuildConfig.DEBUG) {
            Log.d("Tracker", "onEvent: eventId = " + eventId + ", params = " + params);
        }
    }
}