package com.essfoodmart.BigCanteen.model;

public class BCMenuModel
{
    String foodName;
    String foodPrice;
    int foodImage;

    public BCMenuModel(String foodName, String foodPrice, int foodImage) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }
}
