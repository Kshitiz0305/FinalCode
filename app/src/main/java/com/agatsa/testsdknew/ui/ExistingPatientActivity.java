package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.customviews.DialogUtil;
import com.agatsa.testsdknew.databinding.ActivityExistingPatientBinding;
import com.agatsa.testsdknew.ui.adapters.PatientDetailAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ExistingPatientActivity extends AppCompatActivity {

    ActivityExistingPatientBinding binding;
    LabDB labDB;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    PatientDetailAdapter adapter;





    @Override
    protected void onStop(){
        super.onStop();
        mDisposable.clear();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_existing_patient);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        binding.rvOption.setLayoutManager(linearLayoutManager);
        labDB = new LabDB(getApplicationContext());
        binding.spSearchType.setSelection(0);
        binding.spSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    binding.llName.setVisibility(View.VISIBLE);
                    binding.llId.setVisibility(View.GONE);
                }
                else {

                    binding.llName.setVisibility(View.GONE);
                    binding.llId.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.spSearchType.setSelection(0);
                binding.llName.setVisibility(View.VISIBLE);
                binding.llId.setVisibility(View.GONE);

            }
        });
        binding.btnproceed.setOnClickListener(v -> {
            hideSoftKeyboard();

            if(adapter==null){
                DialogUtil.getOKDialog(ExistingPatientActivity.this,"","Please select atleast one patient","ok");

            }
            else {
             if (adapter.getSelectedLocationitem()==null){

                 DialogUtil.getOKDialog(ExistingPatientActivity.this,"","Please select atleast one patient","ok");

             }
             else {
                 DialogUtil.getOKCancelDialog(ExistingPatientActivity.this, "", "Do you want to load the details of " + adapter.getSelectedLocationitem().getPtName() + "?", "Yes", "No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Intent intent = new Intent(ExistingPatientActivity.this, PerformTestActivity.class);
                         intent.putExtra("patient",adapter.getSelectedLocationitem());
                         intent.putExtra("ptid",adapter.getSelectedLocationitem().getId());
                         intent.putExtra("ptname",adapter.getSelectedLocationitem().getPtName());
                         startActivity(intent);
                     }
                 });



             }


            }
        });

        binding.btnsearch.setOnClickListener(v -> {
            hideSoftKeyboard();


            if(binding.llName.getVisibility()==View.VISIBLE)
            {

                mDisposable.add(labDB.getPatientObservableByName(binding.etName.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(patientModelllist -> {
                       if( patientModelllist.size()>0)
                       {

                            adapter= new PatientDetailAdapter(ExistingPatientActivity.this);
                        adapter.setPatientModels(patientModelllist);
                           binding.rvOption.setAdapter(adapter);

                       }
                       else {
                           DialogUtil.getOKDialog(ExistingPatientActivity.this,"","Search not found","ok");
                       }
                            },
                            throwable -> Log.d("rantest", "Unable to get username")));

        }
            else if(binding.llId.getVisibility()==View.VISIBLE){
                mDisposable.add(labDB.getPatientObservableId(binding.etId.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(patientModelList -> {

                                    if( patientModelList.size()>0)
                                    {

                                         adapter= new PatientDetailAdapter(ExistingPatientActivity.this);
                                        adapter.setPatientModels(patientModelList);
                                        binding.rvOption.setAdapter(adapter);

                                    }
                                    else {
                                        DialogUtil.getOKDialog(ExistingPatientActivity.this,"","Search not found","ok");
                                    }


                                },
                                throwable -> Log.e("rantest", "Unable to get username", throwable)));

            }
        });



    }

    public void hideSoftKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent i=new Intent(this, PatientEntryActivity.class);
       startActivity(i);

    }



    public  void  updateSearchTable(PatientModel patientModel){




    }




}

