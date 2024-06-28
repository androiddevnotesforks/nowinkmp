/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.data.di

import com.google.samples.apps.nowinandroid.core.data.util.NetworkMonitor
import com.google.samples.apps.nowinandroid.core.data.util.TimeZoneMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

/**
 * JVM module that provides platform dependent data
 * Leave empty for now
 */
@Component
abstract class JvmPlatformDependentDataModule : PlatformDependentDataModule() {
    @Provides
    override fun bindsNetworkMonitor(): NetworkMonitor {
        return object : NetworkMonitor {
            override val isOnline: Flow<Boolean>
                get() = flowOf(true)
        }
    }

    @Provides
    override fun bindsTimeZoneMonitor(): TimeZoneMonitor {
        return object : TimeZoneMonitor {
            override val currentTimeZone: Flow<TimeZone>
                get() = flowOf(TimeZone.UTC)
        }
    }
}
