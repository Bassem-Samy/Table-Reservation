package com.bassem.tablereservation;

import com.bassem.tablereservation.models.Customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Bassem Samy on 4/23/2017.
 * A unit test that tests the isInFilter(String filterText) Method in Customer Class
 */
@RunWith(JUnit4.class)
public class FilterCustomerUnitTest {
    private static final String SEARCH_NAME_ONE = "ravi";
    private static final String SEARCH_NAME_TWO = "lawrence";
    private static final String SEARCH_NAME_ZERO = "blablablabla";
    private static final int SEARCH_ONE_EXPECTED_COUNT = 1;
    private static final int SEARCH_TWO_EXPECTED_COUNT = 2;
    private static final int SEARCH_ZERO_EXPECTED_COUNT = 0;
    List<Customer> customerList;

    @Before
    public void setup() {
        customerList = new ArrayList<>();
        Customer c1 = new Customer();
        c1.setCustomerFirstName("Tom");
        c1.setCustomerLastName("Hanks");
        Customer c2 = new Customer();
        c2.setCustomerFirstName("Martin");
        c2.setCustomerLastName("Lawrence");
        Customer c3 = new Customer();
        c3.setCustomerFirstName("Jennifer");
        c3.setCustomerLastName("Lawrence");
        Customer c4 = new Customer();
        c4.setCustomerFirstName("Ravi");

        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        customerList.add(c4);

    }


    @Test
    public void testSearch() {

        assertEquals(SEARCH_ONE_EXPECTED_COUNT, performFilter(SEARCH_NAME_ONE));
        assertEquals(SEARCH_TWO_EXPECTED_COUNT, performFilter(SEARCH_NAME_TWO));
        assertEquals(SEARCH_ZERO_EXPECTED_COUNT, performFilter(SEARCH_NAME_ZERO));

    }

    int performFilter(String filter) {
        ArrayList<Customer> resultList = new ArrayList<>();
        for (Customer c : customerList
                ) {
            if (c.isInFilter(filter))
                resultList.add(c);
        }
        return resultList.size();
    }
}