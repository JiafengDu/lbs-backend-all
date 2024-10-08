package com.tarena.lbs.pojo.basic.vo;

import java.io.Serializable;

public class PictureVO implements Serializable {

    public PictureVO() {
    }

    public PictureVO(Integer id, String image) {
        this.id = id;
        this.image = image;
    }

    private Integer id;

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
