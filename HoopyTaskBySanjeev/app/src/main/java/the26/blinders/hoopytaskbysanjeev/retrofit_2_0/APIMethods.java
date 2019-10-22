package the26.blinders.hoopytaskbysanjeev.retrofit_2_0;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import the26.blinders.hoopytaskbysanjeev.models.ResponseFetch;
import the26.blinders.hoopytaskbysanjeev.models.ResponseInsert;
import the26.blinders.hoopytaskbysanjeev.models.ResponseUpdate;
import the26.blinders.hoopytaskbysanjeev.models.ResponseUploadImage;

/**
 * Created by Sanjeev on 01/7/19.
 */

public interface APIMethods {

    @Multipart
    @POST("upload_test")
    Call<ResponseUploadImage> uploadImage(@Part MultipartBody.Part imageFile);

    @FormUrlEncoded
    @POST("insert_test")
    Call<ResponseInsert> insertData(@Field("name") String name, @Field("email") String email,
                                    @Field("username") String username, @Field("contact") String contact,
                                    @Field("image_url") String image_url);

    @FormUrlEncoded
    @POST("update_data_test")
    Call<ResponseUpdate> updateData(@Field("name") String name, @Field("email") String email,
                                    @Field("username") String username, @Field("contact") String contact,
                                    @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("fetch_data_test")
    Call<ResponseFetch> fetchData(@Field("username") String username, @Field("contact") String contact);
}