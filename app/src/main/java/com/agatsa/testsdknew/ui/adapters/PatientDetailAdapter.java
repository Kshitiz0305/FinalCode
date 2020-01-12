package com.agatsa.testsdknew.ui.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.agatsa.testsdknew.Models.PatientModel;
import com.agatsa.testsdknew.R;
import com.agatsa.testsdknew.databinding.TableItemBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDetailAdapter extends RecyclerView.Adapter<PatientDetailAdapter.PatientDetailViewHolder> {

    private final Context context;

    private List<PatientModel> patientModels = new ArrayList<>();
    private int lastCheckedPosition = -1;

   PatientModel selectedPatient;
    private  int selectedItemCount = 0;







    public PatientDetailAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public PatientDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TableItemBinding cartBinding = DataBindingUtil.inflate(layoutInflater, R.layout.table_item,parent,false);
        return new PatientDetailViewHolder(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientDetailViewHolder holder, int position) {

        final PatientModel patient = patientModels.get(position);
        holder.bind(patient);
        if(position==0&&position!=lastCheckedPosition)
        {
            holder.tableItemBinding.container.setBackgroundColor(context.getResources().getColor(R.color.blue_btn_bg_pressed_color));
            holder.tableItemBinding.checker.setChecked(true);
            selectedPatient= patient;


        }
        if(lastCheckedPosition!=-1) {

            holder.tableItemBinding.checker.setChecked(position == lastCheckedPosition);
            if(position == lastCheckedPosition){

                holder.tableItemBinding.container.setBackgroundColor(context.getResources().getColor(R.color.blue_btn_bg_pressed_color));
            }
            else {
                holder.tableItemBinding.container.setBackgroundColor(context.getResources().getColor(R.color.white));

            }
            selectedPatient =patient;
        }









    }




    public  int getSelectedItemCount(){

        return this.selectedItemCount;
    }

    @Override
    public int getItemCount() {
        return patientModels.size();
    }

//    public void removeCartItem(CartItem cartItem){
//        offeredServices.remove(cartItem);
//        notifyDataSetChanged();
//    }

    public void setPatientModels(List<PatientModel> patientModels) {
        this.patientModels = patientModels;
        notifyDataSetChanged();


    }



    public  PatientModel getSelectedLocationitem(){

        return this.selectedPatient;


    }

//    public void  setSelectedCartItem(CartItem cartItem ){
//        this.cartItem= cartItem;
//
//    }

    public class PatientDetailViewHolder extends RecyclerView.ViewHolder {
        TableItemBinding tableItemBinding;
        public PatientDetailViewHolder(@NonNull TableItemBinding itemView) {
            super(itemView.getRoot());
            this.tableItemBinding = itemView;
            this.tableItemBinding.checker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });
//

        }

        public void bind(  PatientModel patientModel) {
            tableItemBinding.ptsex.setText(patientModel.getPtSex());
            tableItemBinding.name.setText(patientModel.getPtName());
            tableItemBinding.ptid.setText(String.valueOf(patientModel.getId()));


        }


    }

}
