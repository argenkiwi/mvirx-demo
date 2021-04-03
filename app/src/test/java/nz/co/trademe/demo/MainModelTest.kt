package nz.co.trademe.demo

import org.junit.Assert.assertEquals
import org.junit.Test

internal class MainModelTest {

    @Test
    fun `on increment should increase`() {

        // Arrange
        val initialState = MainModel.State(0);

        // Act
        val actual = MainModel.reduce(initialState, MainModel.Event.Increment).time

        // Assert
        assertEquals(1, actual)
    }

    @Test
    fun `on decrement should decrease`() {

        // Arrange
        val initialState = MainModel.State(1);

        // Act
        val actual = MainModel.reduce(initialState, MainModel.Event.Decrement).time

        // Assert
        assertEquals(0, actual)
    }

    @Test
    fun `on pause should be true`() {

        // Arrange
        val initialState = MainModel.State(0)

        // Act
        val actual = MainModel.reduce(initialState, MainModel.Event.Pause).paused

        // Assert
        assertEquals(true, actual)
    }

    @Test
    fun `on resume should be false`() {

        // Arrange
        val initialState = MainModel.State(0, true)

        // Act
        val actual = MainModel.reduce(initialState, MainModel.Event.Resume).paused

        // Assert
        assertEquals(false, actual)
    }
}
