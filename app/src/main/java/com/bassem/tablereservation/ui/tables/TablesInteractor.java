package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.models.Table;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface TablesInteractor {
    Single<List<Boolean>> getTablesFromApi();

    List<Table> getTablesFromApiResponse(List<Boolean> items);

    boolean insertOrUpdateTables(List<Table> items);

    boolean dropTables();

    List<Table> getTablesFromDatabase();

}
