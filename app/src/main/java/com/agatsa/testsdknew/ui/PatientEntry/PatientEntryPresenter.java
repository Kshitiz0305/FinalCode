package com.agatsa.testsdknew.ui.PatientEntry;

import android.content.Context;
import android.widget.Toast;

import com.agatsa.testsdknew.Models.BloodPressureResponse;
import com.agatsa.testsdknew.Models.DiabetesResponse;
import com.agatsa.testsdknew.Models.EcgResponse;
import com.agatsa.testsdknew.Models.LoginResponse;
import com.agatsa.testsdknew.Models.MedicalHistoryResponse;
import com.agatsa.testsdknew.Models.UrineResponse;
import com.agatsa.testsdknew.Models.VitalResponse;
import com.agatsa.testsdknew.Network.ApiClient;
import com.agatsa.testsdknew.Network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PatientEntryPresenter {
    private Context mContext;
    private PatientEntryView mView;

    private ApiService apiService;
    private CompositeDisposable mDisposable;

    public PatientEntryPresenter(Context context, PatientEntryView view) {
        this.mContext = context;
        this.mView = view;

        apiService = ApiClient.getClient(context).create(ApiService.class);
        mDisposable = new CompositeDisposable();
    }

    public void vitalsign(String patient_id,String weight,String height,Integer tmp,Integer pulse, String sto2,String bps,String bpd,String createdon,String modifieon){

        mDisposable.add(
                apiService.vitaltest(patient_id,weight,height,tmp,pulse,sto2,"96","99",createdon,modifieon)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<VitalResponse>() {
                            @Override
                            public void onNext(VitalResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }
    public void login(){

        mDisposable.add(
                apiService.logintest("sunyahealthnepal@gmail.com","1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<LoginResponse>() {
                            @Override
                            public void onNext(LoginResponse response) {
                                if(response!=null){
                                    String token=response.getToken();
                                    Toast.makeText(mContext, response.getToken(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }
    public void bloodpressure(String patient_id,String systolic,String diastolic,String createdon,String modifiedon){

        mDisposable.add(
                apiService.bloodpressure(patient_id,systolic,diastolic,createdon,modifiedon)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<BloodPressureResponse>() {
                            @Override
                            public void onNext(BloodPressureResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    public void diabetes(String patient_id,String diabetes,String createdon,String modifiedon){

        mDisposable.add(
                apiService.diabetes(patient_id,diabetes,createdon,modifiedon)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<DiabetesResponse>() {
                            @Override
                            public void onNext(DiabetesResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    public void medicalhistory(String patient_id, String any_allergy, String any_disease_before, String other_illness, String current_medication, boolean do_excercise, boolean do_smoke, boolean do_alcohol, String date, String  location_of_registration){

        mDisposable.add(
                apiService.medicalhistory(patient_id,any_allergy,any_disease_before,other_illness,current_medication,do_excercise,do_smoke,do_alcohol,date,location_of_registration)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MedicalHistoryResponse>() {
                            @Override
                            public void onNext(MedicalHistoryResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    public void ecg(String patient_id, String date,double heartrate,double pr,double qrs,double qt,double qtc,double sdnn,double mrr,double rmssd,Integer finding){

        mDisposable.add(
                apiService.ecgdata(patient_id,date,heartrate,pr,qrs,qt,qtc,sdnn,mrr,rmssd,0.0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EcgResponse>() {
                            @Override
                            public void onNext(EcgResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    public void urinereport(String patient_id, Integer leuk,Integer nit,Integer urb,Integer pro,Integer ph,Integer blood,Integer sg,Integer ket,Integer bil,Integer glucose,Integer aa,String average_colo_test,String strip_photo_uri,String created_on,String modified_on){

        mDisposable.add(
                apiService.urine(patient_id,leuk,nit,urb,pro,ph,blood,sg,ket,bil,glucose,aa,average_colo_test,strip_photo_uri,created_on,modified_on)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UrineResponse>() {
                            @Override
                            public void onNext(UrineResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }







}
