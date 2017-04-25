package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.database.DatabaseHelper;
import com.bassem.tablereservation.models.Table;
import com.bassem.tablereservation.network.CustomersService;
import com.bassem.tablereservation.network.TablesService;
import com.bassem.tablereservation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class TablesInteractorImpl implements TablesInteractor {
    Retrofit retrofit;
    TablesService tablesService;
    DatabaseHelper mDatabaseHelper;

    public TablesInteractorImpl(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public Single<List<Boolean>> getTablesFromApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            tablesService = retrofit.create(TablesService.class);
        }
        return tablesService.getTables();
    }

    /**
     * creates table items from the boolean array returned from the api
     * @param items
     * @return
     */
    @Override
    public List<Table> getTablesFromApiResponse(List<Boolean> items) {
        ArrayList<Table> tables = new ArrayList<>();
        int index = 0;
        for (Boolean item : items
                ) {
            tables.add(new Table(index, item));
            index++;
        }
        return tables;
    }

    @Override
    public boolean insertOrUpdateTables(List<Table> items) {
        return mDatabaseHelper.insertOrUpdateTables(items);
    }

    @Override
    public boolean insertOrUpdateTableItem(Table item) {
      //  return mDatabaseHelper.insertOrUpdateTableItem(item);
        return mDatabaseHelper.setTableAvailable(item.getId(),item.isAvailable());
    }

    @Override
    public boolean dropTables() {
        return mDatabaseHelper.dropTables();
    }

    @Override
    public List<Table> getTablesFromDatabase() {

        List<Table> offlineItems= mDatabaseHelper.getAllTables();
        List<Table> items=new ArrayList<>();
        // avoid realm transaction limitation
        for (Table t:offlineItems
             ) {
            items.add(new Table(t));
        }
        return items;
    }
}
