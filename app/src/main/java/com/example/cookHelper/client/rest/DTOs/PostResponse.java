package com.example.cookHelper.client.rest.DTOs;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostResponse {
    @SerializedName("recipes")
    @Expose
    private ArrayList<DataObject> Recipes;

    public ArrayList<DataObject> getRecipes() {
        return Recipes;
    }
    public void setRecipes(ArrayList<DataObject> recipes) {
        Recipes = recipes;
    }

    public class DataObject {
        @SerializedName("recipeName")
        @Expose
        private String recipeName;

        @SerializedName("cookTime")
        @Expose
        private String cookTime;

        @SerializedName("ingredients")
        @Expose
        private List<String> ingredients;

        @SerializedName("algorithm")
        @Expose
        private String algorithm;

        @SerializedName("searchAccuracy")
        @Expose
        private Double searchAccuracy;

        public String getRecipeName() {
            return recipeName;
        }
        public void setRecipeName(String name) {
            this.recipeName = recipeName;
        }

        public String getCookTime() {
            return cookTime;
        }
        public void setCookTime(String cookTime) {
            this.cookTime = cookTime;
        }

        public List<String> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public Double getSearchAccuracy() {
            return searchAccuracy;
        }

        public void setSearchAccuracy(Double searchAccuracy) {
            this.searchAccuracy = searchAccuracy;
        }
    }
}