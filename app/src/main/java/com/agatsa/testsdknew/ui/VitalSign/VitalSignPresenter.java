package com.agatsa.testsdknew.ui.VitalSign;

import android.content.Context;
import android.widget.Toast;

import com.agatsa.testsdknew.Models.DiabetesResponse;
import com.agatsa.testsdknew.Models.VitalResponse;
import com.agatsa.testsdknew.Network.ApiClient;
import com.agatsa.testsdknew.Network.ApiService;
import com.agatsa.testsdknew.ui.Diabetes.DiabetesView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VitalSignPresenter {
    private Context mContext;
    private VitalSignView mView;

    private ApiService apiService;
    private CompositeDisposable mDisposable;

    public VitalSignPresenter(Context context, VitalSignView view) {
        this.mContext = context;
        this.mView = view;

        apiService = ApiClient.getClient(context).create(ApiService.class);
        mDisposable = new CompositeDisposable();
    }

//    public void vitalsign(String patient_id,String weight,String height,StIntring tmp,String pulse, String sto2,String bps,String bpd,String createdon,String modifieon){
//
//        mDisposable.add(
//                apiService.vitaltest(patient_id,weight,height,tmp,pulse,sto2,"96","99",createdon,modifieon)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<VitalResponse>() {
//                            @Override
//                            public void onNext(VitalResponse response) {
//                                if(response!=null){
//                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
//
//                                }else{
//                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();
//
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        }));
//    }
}
