package com.bassem.tablereservation.ui;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.ui.customerslisting.CustomersFragment;
import com.bassem.tablereservation.ui.tables.TablesFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements CustomersFragment.OnCustomersFragmentInteractionListener, TablesFragment.OnFragmentInteractionListener {

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
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frm_container, TablesFragment.newInstance("Bassem"), TablesFragment.TAG)
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
