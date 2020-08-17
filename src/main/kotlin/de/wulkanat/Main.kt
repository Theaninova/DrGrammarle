package de.wulkanat

import de.wulkanat.listeners.GrammarListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

fun main() {
    val builder = JDABuilder.createLight(
        Admin.token,
        GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
        .addEventListeners(GrammarListener())
        .setActivity(Activity.watching("Mein Grammar"))
        .build()

    // TODO: event listeners
    builder.awaitReady()

    Channels.jda = builder
    Admin.jda = builder
    Admin.info()

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Shutting down...")
            println("Sending shutdown notice to Admin, waiting 5s...")
            Admin.println("Shutting down")
            sleep(5000)
        }
    })
}