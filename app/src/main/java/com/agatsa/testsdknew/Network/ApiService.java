package com.agatsa.testsdknew.Network;

import com.agatsa.testsdknew.Models.PersonalDetailsResponse;
import com.agatsa.testsdknew.Models.UserDetailResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("users/api/")
    Observable<UserDetailResponse> user(@Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("email") String email,
                                        @Field("mobile") String mobile,
                                        @Field("is_active") boolean is_active,
                                        @Field("is_staff") boolean is_staff,
                                        @Field("is_superuser") boolean is_superuser);

    @FormUrlEncoded
    @POST("users/patient/")
    Observable<PersonalDetailsResponse> personaldetails(@Field("first_name") String first_name,
                                             @Field("middle_name") String middle_name,
                                             @Field("last_name") String last_name,
                                             @Field("email") String email,
                                             @Field("mobile") String mobile,
                                             @Field("address") String address,
                                             @Field("district") String district,
                                             @Field("nationality") String nationality,
                                             @Field("dob") String dob,
                                             @Field("gender") String gender, @Field("marital_status") boolean marital_status);


//    @FormUrlEncoded
//    @POST("/report/ecg/")
//    Observable<PersonalDetailsResponse> personaldetails(@Field("first_name") String first_name,
//                                                        @Field("middle_name") String middle_name,
//                                                        @Field("last_name") String last_name,
//                                                        @Field("email") String email,
//                                                        @Field("mobile") String mobile,
//                                                        @Field("address") String address,
//                                                        @Field("district") String district,
//                                                        @Field("nationality") String nationality,
//                                                        @Field("dob") String dob,
//                                                        @Field("gender") String gender, @Field("marital_status") boolean marital_status);
//














}
