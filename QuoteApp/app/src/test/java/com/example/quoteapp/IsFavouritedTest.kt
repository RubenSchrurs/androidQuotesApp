package com.example.quoteapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.quoteapp.database.Quote
import com.example.quoteapp.screens.dashboard.QuoteViewModel
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IsFavouritedTest {
    @Rule
    @JvmField
    val rule = createComposeRule()

    @Test
    fun checkQuoteFavouritedStateAfterCheckboxClick() {
        // Create a mock quote to be used for testing
        val mockQuote = Quote(1, "Test Quote", "Test Author", false)

        // Set up the ViewModel and LiveData for testing
        val viewModel = QuoteViewModel(ApplicationProvider.getApplicationContext())

        // Observe the LiveData for quote updates
        val observedQuotes = mutableListOf<Quote>()
        viewModel.allQuotes.observeForever {
            observedQuotes.clear()
            observedQuotes.addAll(it)
        }

        // Simulate clicking the checkbox
        viewModel.update(mockQuote.copy(isFavourite = true))

        // Verify that the quote's favourited state has been updated
        assertTrue(observedQuotes.isNotEmpty())
        val updatedQuote = observedQuotes.find { it.id == mockQuote.id }
        assertNotNull(updatedQuote)
        assertTrue(updatedQuote!!.isFavourite)
    }

}