package com.agatsa.testsdknew.Models.districtsplaces;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class DistrictPlaces{
  @SerializedName("districts")
  @Expose
  private List<Districts> districts;
  public void setDistricts(List<Districts> districts){
   this.districts=districts;
  }
  public List<Districts> getDistricts(){
   return districts;
  }
}