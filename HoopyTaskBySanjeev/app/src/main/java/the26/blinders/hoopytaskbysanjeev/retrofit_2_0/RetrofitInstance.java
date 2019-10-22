package the26.blinders.hoopytaskbysanjeev.retrofit_2_0;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import the26.blinders.hoopytaskbysanjeev.helper.Constant;
import the26.blinders.hoopytaskbysanjeev.helper.SharedPrefrencesMG;

public class RetrofitInstance {
    static SharedPrefrencesMG sharedPrefrencesMG;
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.team.hoopy.in/api/1.0/testApis/";

    /**
     * Create an instance of Retrofit object
     */


    public static Retrofit getRetrofitInstance() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

/*
    public static Retrofit getRetrofitInstanceToken(Context context) {

        sharedPrefrencesMG = SharedPrefrencesMG.getInstance(context);
        final String token = sharedPrefrencesMG.getData(Constant.ACCESS_TOKEN);
        Log.e("my token is ",token);


        OkHttpClient authorization = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("authorization",  token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        OkHttpClient.Builder builder = authorization.newBuilder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);



        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
*/
}
