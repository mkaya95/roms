package ba.edu.ibu.roms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements  Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("table_number")
    private String table_number;
    @SerializedName("food_id")
    private Integer food_id;
    @SerializedName("date")
    private String date;
    @SerializedName("is_finished")
    private Integer is_finished;

    @SerializedName("name")
    private String name;
    @SerializedName("desc")
    private String desc;
    @SerializedName("price")
    private Integer price;


    public Order(Integer id, String table_number,  Integer food_id, String date,  Integer is_finished) {
        this.id = id;
        this.table_number = table_number;
        this.food_id = food_id;
        this.date = date;
        this.is_finished = is_finished;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableNumber() {
        return table_number;
    }

    public void setTableNumber(String table_number) {
        this.table_number = table_number;
    }

    public Integer getFoodId() {
        return food_id;
    }

    public void setFoodId(Integer food_id) {
        this.food_id = food_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getIsFinished() {
        return is_finished;
    }

    public void setIsFinished(Integer is_finished) {
        this.is_finished = is_finished;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
