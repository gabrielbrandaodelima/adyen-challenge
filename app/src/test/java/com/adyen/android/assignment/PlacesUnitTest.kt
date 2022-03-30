package com.adyen.android.assignment

import com.adyen.android.assignment.core.data.api.PlacesServiceApi
import com.adyen.android.assignment.core.utils.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.util.CoroutineTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlacesUnitTest {

    @Test
    fun testResponseCode() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()


        val response = runBlocking {
            PlacesServiceApi.instance
                .getVenueRecommendations(query)
        }


        val errorBody = response.errorBody()
        assertNull("Received an error: ${errorBody?.string()}", errorBody)

        val responseWrapper = response.body()
        assertNotNull("Response is null.", responseWrapper)
        assertEquals("Response code", 200, response.code())
    }

    @Test
    fun testResponseResultsNotNullOrEmpty() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()

            val response = runBlocking {
                PlacesServiceApi.instance
                    .getVenueRecommendations(query)
            }
            val errorBody = response.errorBody()
            assertNull("Received an error: ${errorBody?.string()}", errorBody)

            val responseWrapper = response.body()
            assertNotNull("Response is null.", responseWrapper)
            assertEquals("Response code", 200, response.code())
            assertNotNull("Response list is null.", responseWrapper?.results)
        }
    @Test
    fun testResponseFirstItemNotNull() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()

            val response = runBlocking {
                PlacesServiceApi.instance
                    .getVenueRecommendations(query)
            }
            val errorBody = response.errorBody()
            assertNull("Received an error: ${errorBody?.string()}", errorBody)

            val responseWrapper = response.body()
            assertNotNull("Response is null.", responseWrapper)
            assertEquals("Response code", 200, response.code())
            assertNotNull("Response list is null.", responseWrapper?.results)
            assertNotNull("First item list is null.", responseWrapper?.results?.get(0))
        }
    @Test
    fun testResponseFirstItemName() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()

            val response = runBlocking {
                PlacesServiceApi.instance
                    .getVenueRecommendations(query)
            }
            val errorBody = response.errorBody()
            assertNull("Received an error: ${errorBody?.string()}", errorBody)

            val responseWrapper = response.body()
            assertNotNull("Response is null.", responseWrapper)
            assertEquals("Response code", 200, response.code())
            assertNotNull("Response list is null.", responseWrapper?.results)
            assertNotNull("First item list is null.", responseWrapper?.results?.get(0))
            assertEquals("Adyen BV", responseWrapper?.results?.get(0)?.name)
        }
}
