package ba.edu.ibu.roms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettingsProvider implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("label")
    private String label;
    @SerializedName("value")
    private String value;


    public SettingsProvider(Integer id, String title, String label, String value) {
        this.id = id;
        this.title = title;
        this.label = label;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}