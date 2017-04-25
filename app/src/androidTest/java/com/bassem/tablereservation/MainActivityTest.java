package com.bassem.tablereservation;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bassem.tablereservation.ui.MainActivity;
import com.bassem.tablereservation.utils.Constants;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Bassem Samy on 4/23/2017.
 * Android unit test that tests customers recyclerview displays correctly
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    static final int EXPECTED_ITEMS_COUNT = 14;
    private static MockWebServer mockWebServer;
    private static final String ASSETS_CUSTOMERS_FILE_NAME = "customers_response.json";
    private static final String LAST_ITEM_EXPECTED_FULL_NAME = "Cleave Staples Lewis";


    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private static String getCustomersMockResponseBody() throws IOException {
        return AssetsFileReader.readFileAsString(ASSETS_CUSTOMERS_FILE_NAME);
    }

    private static void clearSearchEditText() {
        onView(withId(R.id.edt_filter)).perform(ViewActions.clearText());
    }

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        Constants.SERVICE_BASE_URL = mockWebServer.url("test/").toString();
        MockResponse mockResponse = new MockResponse().setResponseCode(200)
                .setBody(getCustomersMockResponseBody());
        mockWebServer.enqueue(mockResponse);
    }

    @Before
    public void before() {
        clearSearchEditText();

    }

    /**
     * Tests that the recycler view displays (mocked) data correctly
     */
    @Test
    public void checkCustomers() {
        // test number of items displayed
        onView(withId(R.id.rclr_customers)).check(new RecyclerViewItemsCountAssertion(EXPECTED_ITEMS_COUNT));
        // scroll to last item
        onView(withId(R.id.rclr_customers)).perform(scrollToPosition(EXPECTED_ITEMS_COUNT - 1));
        // check if the last item is the expected  name
        onView(withId(R.id.rclr_customers)).check(new RecyclerViewItemStringDataAssertion(
                R.id.txt_full_name,
                LAST_ITEM_EXPECTED_FULL_NAME,
                EXPECTED_ITEMS_COUNT - 1)
        );
    }


}
