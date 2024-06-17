/*
 * Copyright 2023 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.core.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.google.samples.apps.nowinandroid.core.database.NiaDatabase
import com.google.samples.apps.nowinandroid.core.database.model.TopicFtsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

/**
 * DAO for [TopicFtsEntity] access.
 */
@Inject
class TopicFtsDao(db: NiaDatabase, private val dispatcher: CoroutineDispatcher) : TopicFtsDaoInterface {

    private val dbQuery = db.topicFtsQueries

    override suspend fun insertAll(topics: List<TopicFtsEntity>) {
        topics.forEach {
            dbQuery.insert(
                topic_id = it.topicId,
                name = it.name,
                short_description = it.shortDescription,
                long_description = it.longDescription,
            )
        }
    }

    override fun searchAllTopics(query: String): Flow<List<String>> {
        return dbQuery.searchAllTopics(query) {
            it.orEmpty()
        }
            .asFlow()
            .mapToList(dispatcher)
    }

    override fun getCount(): Flow<Int> {
        return dbQuery.getCount()
            .asFlow()
            .mapToOne(dispatcher)
            .map { it.toInt() }
    }
}
