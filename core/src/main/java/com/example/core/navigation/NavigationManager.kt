package com.example.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object NavigationManager {
    private val _commands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val commands: SharedFlow<NavigationCommand> = _commands

    fun navigate(command: NavigationCommand) {
        _commands.tryEmit(command)
    }
}

sealed class NavigationCommand {
    data class ToScreen(val route: String) : NavigationCommand()
    data object Back : NavigationCommand()
}
