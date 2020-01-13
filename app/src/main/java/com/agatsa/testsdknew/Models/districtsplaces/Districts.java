package com.agatsa.testsdknew.Models.districtsplaces;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Districts{
  @SerializedName("places")
  @Expose
  private List<Places> places;
  @SerializedName("name")
  @Expose
  private String name;
  public void setPlaces(List<Places> places){
   this.places=places;
  }
  public List<Places> getPlaces(){
   return places;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
}