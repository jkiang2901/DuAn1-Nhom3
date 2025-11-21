package fpl.manhph61584.duan1_nhom3_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // THAY ĐỔI IP NÀY THÀNH IP MÁY TÍNH CỦA BẠN
    private static final String BASE_URL = "http://10.24.39.228:3000/api/";
    private static Retrofit retrofit;

    public static ApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}