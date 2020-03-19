package com.agatsa.testsdknew.ui.Diabetes;

import android.content.Context;
import android.widget.Toast;

import com.agatsa.testsdknew.Models.DiabetesResponse;
import com.agatsa.testsdknew.Network.ApiClient;
import com.agatsa.testsdknew.Network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DiabetesPresenter {
    private Context mContext;
    private DiabetesView mView;

    private ApiService apiService;
    private CompositeDisposable mDisposable;

    public DiabetesPresenter(Context context, DiabetesView view) {
        this.mContext = context;
        this.mView = view;

        apiService = ApiClient.getClient(context).create(ApiService.class);
        mDisposable = new CompositeDisposable();
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
}
