package project.sd.client.util;

import project.sd.client.api.LoginApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SingletonRetrofit {

    private static SingletonRetrofit instance = new SingletonRetrofit();

    private SingletonRetrofit() {

    }

    public static SingletonRetrofit getInstance() {
        return instance;
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8090/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
