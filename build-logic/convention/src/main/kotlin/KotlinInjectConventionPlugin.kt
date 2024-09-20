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

import com.google.samples.apps.nowinandroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinInjectConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                add("kspCommonMainMetadata", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("commonMainImplementation", libs.findLibrary("kotlin.inject.runtime.kmp").get())
                // KSP will eventually have better multiplatform support and we'll be able to simply have
                // `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
                // https://github.com/google/ksp/pull/1021
                add("kspIosX64", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspIosArm64", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspIosSimulatorArm64", libs.findLibrary("kotlin.inject.compiler.ksp").get())
//                add("kspWasmJs", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspAndroid", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspJvm", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspMacosX64", libs.findLibrary("kotlin.inject.compiler.ksp").get())
                add("kspMacosArm64", libs.findLibrary("kotlin.inject.compiler.ksp").get())
            }
        }
    }
}