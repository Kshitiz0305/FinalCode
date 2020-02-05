package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.widget.Toast;

import com.agatsa.testsdknew.Models.PersonalDetailsResponse;
import com.agatsa.testsdknew.Network.ApiClient;
import com.agatsa.testsdknew.Network.ApiService;
import com.agatsa.testsdknew.ui.Personaldetails.PersonalDetailsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SingleLeadecgPresenter {
    private Context mContext;
    private PersonalDetailsView mView;

    private ApiService apiService;
    private CompositeDisposable mDisposable;

    public SingleLeadecgPresenter(Context context, PersonalDetailsView view) {
        this.mContext = context;
        this.mView = view;

        apiService = ApiClient.getClient(context).create(ApiService.class);
        mDisposable = new CompositeDisposable();
    }

    public void postsingleleaddata(String first_name,String middle_name,String last_name,String email,String mobile,String address,String district,String nationality,String dob,String gender,boolean marital_status){

        mDisposable.add(
                apiService.personaldetails(first_name,middle_name,last_name,email,mobile,address,district,nationality,dob,gender,marital_status)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<PersonalDetailsResponse>() {
                            @Override
                            public void onNext(PersonalDetailsResponse response) {
                                if(response!=null){
                                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();



                                }




                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    ErrorUtil.getErrorMessage(mContext, e), true;


                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }


}
