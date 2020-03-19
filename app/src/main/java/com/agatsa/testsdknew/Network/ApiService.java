package com.agatsa.testsdknew.Network;


import com.agatsa.testsdknew.Models.BloodPressureResponse;
import com.agatsa.testsdknew.Models.DiabetesResponse;
import com.agatsa.testsdknew.Models.EcgResponse;
import com.agatsa.testsdknew.Models.LoginResponse;
import com.agatsa.testsdknew.Models.MedicalHistoryResponse;
import com.agatsa.testsdknew.Models.PersonalDetailsResponse;
import com.agatsa.testsdknew.Models.UrineResponse;
import com.agatsa.testsdknew.Models.UserDetailResponse;
import com.agatsa.testsdknew.Models.VitalResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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


    @FormUrlEncoded

    @POST("/report/blood-pressure-test/")
    Observable<BloodPressureResponse> bloodpressure(@Field("patient_id") String patient_id,
                                                    @Field("systolic") String systolic,
                                                    @Field("diastolic") String diastolic,
                                                    @Field("created_on") String created_on,
                                                    @Field("modified_on") String modified_on);



    @FormUrlEncoded
    @POST("/report/diabities-test/")
//    @Headers("Authorization: d2c897d823c8816180c28d1eb5849b8aa1160dde")
    Observable<DiabetesResponse> diabetes(
                                              @Field("patient_id") String patient_id,
                                               @Field("diabities") String diabities,
                                               @Field("created_on") String created_on,
                                               @Field("modified_on") String modified_on);

    @FormUrlEncoded
    @POST("/report/ecg/")
    Observable<EcgResponse> ecgdata(@Field("patient_id") String patient_id,
                                     @Field("date_of_test_conducted") String date_of_test_conducted,
                                     @Field("heart_rate") Double heart_rate,
                                     @Field("pr") Double pr,
                                     @Field("qrs") Double qrs,
                                     @Field("qt") Double qt,
                                     @Field("qtc") Double qtc,
                                     @Field("sdnn") Double sdnn,
                                     @Field("mrr") Double mrr,
                                     @Field("rmssd") Double rmssd,
                                     @Field("overall_result") Double overall_result);



    @FormUrlEncoded
    @POST("/report/medical-history/")
    Observable<MedicalHistoryResponse> medicalhistory(@Field("patient_id") String patient_id,
                                                      @Field("any_allergy") String any_allergy,
                                                      @Field("any_disease_before") String any_disease_before,
                                                      @Field("other_illness") String other_illness,
                                                      @Field("current_medication") String current_medication,
                                                      @Field("do_excercise") boolean do_excercise,
                                                      @Field("do_smoke") boolean do_smoke,
                                                      @Field("do_alcohol") boolean do_alcohol,
                                                      @Field("date") String date,
                                                      @Field("location_of_registration") String location_of_registration);


    @FormUrlEncoded
    @POST("/report/vital-sign/")
    Observable<VitalResponse> vitaltest(@Field("patient_id") String patient_id,
                                        @Field("weight") String weight,
                                        @Field("height") String height,
                                        @Field("tmpt") Integer tmpt,
                                        @Field("pulse") Integer pulse,
                                        @Field("sto_2") String sto_2,
                                        @Field("bp_s") String bp_s,
                                        @Field("bp_d") String bp_d,
                                        @Field("created_on") String created_on,
                                        @Field("modified_on") String modified_on);

    @FormUrlEncoded
    @POST("/api/auth-token/")
    Observable<LoginResponse> logintest(@Field("username") String username,
                                        @Field("password") String password);

    @GET("token")
    Call<ResponseBody>getsecret(@Header("token")String token);



    @FormUrlEncoded
    @POST("/report/urine-test/")
    Observable<UrineResponse> urine(@Field("patient_id") String patient_id,
                                        @Field("leuk") Integer leuk,
                                        @Field("nit") Integer height,
                                        @Field("urb") Integer tmpt,
                                        @Field("pro") Integer pulse,
                                        @Field("ph") Integer sto_2,
                                        @Field("blood") Integer bp_s,
                                        @Field("sg") Integer sg,
                                        @Field("ket") Integer ket,
                                        @Field("bil") Integer bil,
                                        @Field("glucose") Integer glucose,
                                        @Field("aa") Integer aa,
                                        @Field("average_colo_test") String average_colo_test,
                                        @Field("strip_photo_uri") String strip_photo_uri,
                                        @Field("created_on") String created_on,
                                        @Field("modified_on") String modified_on);
































}
