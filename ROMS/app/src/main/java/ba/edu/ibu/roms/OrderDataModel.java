package ba.edu.ibu.roms;


public class OrderDataModel  {


    private String name;
    private String desc;
    private Integer price;
    private Integer id;

    public OrderDataModel(Integer id, String name, String desc,  Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer name) {
        this.id = id;
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
