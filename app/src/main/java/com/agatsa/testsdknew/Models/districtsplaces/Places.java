package com.agatsa.testsdknew.Models.districtsplaces;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Places{
  @SerializedName("placename")
  @Expose
  private String placename;
  public void setPlacename(String placename){
   this.placename=placename;
  }
  public String getPlacename(){
   return placename;
  }
}