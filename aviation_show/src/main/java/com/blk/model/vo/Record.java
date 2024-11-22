package com.blk.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Record {
       @JsonProperty("value2")
       private String value2;

       @JsonProperty("value1")
       private String value1;

       @JsonProperty("value3")
       private String value3;

       @JsonProperty("tab")
       private String tab;

       @JsonProperty("state")
       private String state;

       // Getters and Setters
       public String getValue2() {
           return value2;
       }

       public void setValue2(String value2) {
           this.value2 = value2;
       }

       public String getValue1() {
           return value1;
       }

       public void setValue1(String value1) {
           this.value1 = value1;
       }

       public String getValue3() {
           return value3;
       }

       public void setValue3(String value3) {
           this.value3 = value3;
       }

       public String getTab() {
           return tab;
       }

       public void setTab(String tab) {
           this.tab = tab;
       }

       public String getState() {
           return state;
       }

       public void setState(String state) {
           this.state = state;
       }
   }