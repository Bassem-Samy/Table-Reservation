package com.bassem.tablereservation.ui;

import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.ui.customerslisting.CustomersFragment;
import com.bassem.tablereservation.ui.tables.TablesFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements CustomersFragment.OnCustomersFragmentInteractionListener, TablesFragment.OnFragmentInteractionListener {
    boolean loadTablesFromDatabase;
    private static final String SAVE_LOAD_TABLES_FROM_DATABASE = "save_load_tables_from_database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCustomersFragment();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            loadTablesFromDatabase = savedInstanceState.getBoolean(SAVE_LOAD_TABLES_FROM_DATABASE, false);
        }
    }

    void addCustomersFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CustomersFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frm_container, CustomersFragment.newInstance(), CustomersFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onCustomerClicked(String customerName) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frm_container, TablesFragment.newInstance(customerName, loadTablesFromDatabase), TablesFragment.TAG)
                .addToBackStack(null)
                .commit();
        // next time this fragment is added, load from db (to preserve a session of tables per run )
        loadTablesFromDatabase = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_LOAD_TABLES_FROM_DATABASE, loadTablesFromDatabase);
    }
}
