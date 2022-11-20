package org.idnp.idnp2022lab06.entity;

public class SerieEntity {
    private String category;
    private float value;

    public SerieEntity(String category, float value){
        this.category = category;
        this.value = value;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
