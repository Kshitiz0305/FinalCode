package com.agatsa.testsdknew.ui.BloodPressure;

import android.content.Context;
import android.widget.Toast;

import com.agatsa.testsdknew.Models.BloodPressureModel;
import com.agatsa.testsdknew.Models.BloodPressureResponse;
import com.agatsa.testsdknew.Models.PersonalDetailsResponse;
import com.agatsa.testsdknew.Network.ApiClient;
import com.agatsa.testsdknew.Network.ApiService;
import com.agatsa.testsdknew.ui.Personaldetails.PersonalDetailsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BloodPressurePresenter {
    private Context mContext;
    private BloodPressureView mView;

    private ApiService apiService;
    private CompositeDisposable mDisposable;

    public BloodPressurePresenter(Context context, BloodPressureView view) {
        this.mContext = context;
        this.mView = view;

        apiService = ApiClient.getClient(context).create(ApiService.class);
        mDisposable = new CompositeDisposable();
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
}
