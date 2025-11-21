package fpl.manhph61584.duan1_nhom3_app;


import java.util.List;
import fpl.manhph61584.duan1_nhom3_app.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {




    @GET("products")
    Call<List<Product>> getProducts(@Query("search") String search);


    @GET("products/{id}")
    Call<Product> getProductDetail(@Path("id") String id);
}