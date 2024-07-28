/*
* Copyright (c) 2024, Patrick Wilmes <p.wilmes89@gmail.com>
* All rights reserved.
*
* SPDX-License-Identifier: BSD-2-Clause
*/
package com.bit.lake

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

enum class LearningCurve {
    Medium, Steep, VerySteep
}

suspend fun fetchLearningCurveMapping(): Map<String, LearningCurve> {
    delay(500) // simulate I/O delay
    return mapOf(
        "C" to LearningCurve.Steep,
        "C++" to LearningCurve.VerySteep,
        "ZIG" to LearningCurve.Medium,
        "RUST" to LearningCurve.VerySteep,
    )
}

data class Language(
    val name: String,
    val learningCurve: LearningCurve,
)

suspend fun main() {
    val languagesToMap = listOf("C", "C++")

    val languages = coroutineScope {
        val deferredLangMap = async { fetchLearningCurveMapping() }
        deferredLangMap
            .await()
            .filter { languagesToMap.contains(it.key) }
            .map {
                Language(it.key, it.value)
            }
    }

    languages.forEach { println(it) }
}
