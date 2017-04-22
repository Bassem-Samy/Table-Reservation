package com.bassem.tablereservation.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public interface TablesService {
    @GET("table-map.json")
    Single<List<Boolean>> getTables();
}
