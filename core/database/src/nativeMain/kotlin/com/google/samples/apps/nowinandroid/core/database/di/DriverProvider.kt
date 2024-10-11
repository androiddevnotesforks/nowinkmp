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

package com.google.samples.apps.nowinandroid.core.database.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult.AsyncValue
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import co.touchlab.sqliter.DatabaseConfiguration.Extended
import org.koin.core.annotation.Single

@Single
internal actual class DriverProvider {

    actual suspend fun provideDbDriver(
        schema: SqlSchema<AsyncValue<Unit>>,
    ): SqlDriver {
        val synchronousSchema = schema.synchronous()
        return NativeSqliteDriver(
            schema = synchronousSchema,
            name = "nia-database.db",
            onConfiguration = { config: DatabaseConfiguration ->
                config.copy(
                    extendedConfig = Extended(foreignKeyConstraints = true),
                )
            },
        )
    }
}
