/*
 *     Copyright 2016 - 2017 Florian Spieß
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:JvmName("KJDAClientBuilder")
@file:Suppress("UNUSED")

package club.minnced.kjda

import com.neovisionaries.ws.client.WebSocketFactory
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.audio.factory.IAudioSendFactory
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.hooks.IEventManager
import okhttp3.OkHttpClient

/**
 * Constructs a new [JDABuilder] and applies the specified
 * init function `() -> Unit` to that receiver.
 * This uses [JDABuilder.buildAsync]
 *
 * The token is not required here, however needs to be configured in the given function!
 *
 * @param[accountType]
 *       The [AccountType] for the account being issued for creation
 * @param[init]
 *       The function which uses the constructed JDABuilder as receiver to setup
 *       the JDA information before building it
 *
 * @sample client
 *
 * @see    JDABuilder
 */
fun client(accountType: AccountType, init: JDABuilder.() -> Unit): JDA {
    val builder = JDABuilder(accountType)
    builder.init()
    return builder.buildAsync()
}

/** Lazy infix overload for [JDABuilder.setToken] */
inline infix fun <reified T : JDABuilder> T.token(lazyToken: () -> String): T = this.setToken(lazyToken()) as T

/** Lazy infix overload for [JDABuilder.setGame(Game.watching)] */
inline infix fun <reified T : JDABuilder> T.watching(lazy: () -> String): T = this.setGame(Game.watching(lazy())) as T

/** Lazy infix overload for [JDABuilder.setGame(Game.listening)] */
inline infix fun <reified T : JDABuilder> T.listening(lazy: () -> String): T = this.setGame(Game.listening(lazy())) as T

/** Lazy infix overload for [JDABuilder.setGame(Game.playing)] */
inline infix fun <reified T : JDABuilder> T.playing(lazy: () -> String): T = this.setGame(Game.playing(lazy())) as T

/** Overload for [JDABuilder.setGame(Game.streaming)] */
inline fun <reified T : JDABuilder> T.streaming(name: String, url: String): T = this.setGame(Game.streaming(name, url)) as T

/** Lazy infix overload for [JDABuilder.setStatus] */
inline infix fun <reified T : JDABuilder> T.status(lazy: () -> OnlineStatus): T = this.setStatus(lazy()) as T

/** Lazy infix overload for [JDABuilder.setEventManager] */
inline infix fun <reified T : JDABuilder> T.manager(lazy: () -> IEventManager): T = this.setEventManager(lazy()) as T

/** Lazy infix overload for [JDABuilder.addEventListener] */
inline infix fun <reified T : JDABuilder> T.listener(lazy: () -> Any): T = this.addEventListener(lazy()) as T

/** Lazy infix overload for [JDABuilder.setAudioSendFactory] */
inline infix fun <reified T : JDABuilder> T.audioSendFactory(lazy: () -> IAudioSendFactory): T = this.setAudioSendFactory(lazy()) as T

/** Infix overload for [JDABuilder.setIdle] */
inline infix fun <reified T : JDABuilder> T.idle(lazy: Boolean): T = this.setIdle(lazy) as T

/** Infix overload for [JDABuilder.setEnableShutdownHook] */
inline infix fun <reified T : JDABuilder> T.shutdownHook(lazy: Boolean): T = this.setEnableShutdownHook(lazy) as T

/** Infix overload for [JDABuilder.setAudioEnabled] */
inline infix fun <reified T : JDABuilder> T.audio(lazy: Boolean): T = this.setAudioEnabled(lazy) as T

/** Infix overload for [JDABuilder.setAutoReconnect] */
inline infix fun <reified T : JDABuilder> T.autoReconnect(lazy: Boolean): T = this.setAutoReconnect(lazy) as T

/**
 * Provides new WebSocketFactory and calls the provided lazy
 * initializer to allow setting options like timeouts
 */
inline infix fun <reified T : JDABuilder> T.websocketSettings(init: WebSocketFactory.() -> Unit): T {
    val factory = WebSocketFactory()
    factory.init()
    setWebsocketFactory(factory)
    return this
}

/**
 * Provides new OkHttpClient.Builder and calls the provided lazy
 * initializer to allow setting options like timeouts
 */
inline infix fun <reified T : JDABuilder> T.httpSettings(init: OkHttpClient.Builder.() -> Unit): T {
    val builder = OkHttpClient.Builder()
    builder.init()
    setHttpClientBuilder(builder)
    return this
}

/** Overload for [JDABuilder.addEventListener] */
inline fun <reified T : JDABuilder> T.listener(vararg listener: Any): T = this.addEventListener(*listener) as T

/** Overload for [JDABuilder.removeEventListener] */
inline fun <reified T : JDABuilder> T.removeListener(vararg listener: Any): T = this.removeEventListener(*listener) as T

/** Operator overload for [JDABuilder.addEventListener] */
inline operator fun <reified T : JDABuilder> T.plusAssign(other: Any) {
    listener(other)
}

/** Operator overload for [JDABuilder.removeEventListener] */
inline operator fun <reified T : JDABuilder> T.minusAssign(other: Any) {
    removeListener(other)
}
