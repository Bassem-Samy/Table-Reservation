package com.bassem.tablereservation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.ui.customerslisting.CustomersFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements CustomersFragment.OnCustomersFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCustomersFragment();
    }

    void addCustomersFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CustomersFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frm_container, CustomersFragment.newInstance(), CustomersFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onCustomerClicked() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }
}
