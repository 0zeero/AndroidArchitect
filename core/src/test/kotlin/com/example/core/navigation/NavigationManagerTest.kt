package com.example.core.navigation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NavigationManagerTest {

    @Test
    fun navigate_ToScreen_emitsCommandInFlow() = runTest {
        NavigationManager.commands.test {
            NavigationManager.navigate(NavigationCommand.ToScreen("details"))
            val command = awaitItem()
            assertThat(command).isInstanceOf(NavigationCommand.ToScreen::class.java)
            assertThat((command as NavigationCommand.ToScreen).route).isEqualTo("details")
        }
    }

    @Test
    fun navigate_Back_emitsBackCommand() = runTest {
        NavigationManager.commands.test {
            NavigationManager.navigate(NavigationCommand.Back)
            val command = awaitItem()
            assertThat(command).isEqualTo(NavigationCommand.Back)
        }
    }
}
