package ba.edu.ibu.roms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("settings")
    Call<List<SettingsProvider>> getAllSettings();

    @POST("settings/update")
    @FormUrlEncoded
    Call<List<SettingsProvider>> updateSettings(@Field("title") String title, @Field("label") String label, @Field("value") String value);

    @GET("login/{email}/{password}")
    Call<Login> authenticate(@Path("email") String email, @Path("password") String password);

    @GET("order/{table_number}")
    Call<List<Order>> getOrderByTableNumber(@Path("table_number") Integer table_number);

    @GET("order/{table_number}/close_table")
    Call<List> closeTable(@Path("table_number") Integer table_number);

    @GET("get_overview")
    Call<List> getOverview();

    @GET("foods")
    Call<List<Foods>> getFoods();

    @GET("order/make_order/{table_number}/{food_id}")
    Call<List> order(@Path("table_number") Integer table_number, @Path("food_id") Integer food_id);


}