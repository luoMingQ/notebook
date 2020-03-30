package com.lsc.notebook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author luosc
 * @since 2020-03-29
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    private Long patientMenuId;

    private String menuName;

    private String menuUrl;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getPatientMenuId() {
        return patientMenuId;
    }

    public void setPatientMenuId(Long patientMenuId) {
        this.patientMenuId = patientMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Override
    public String toString() {
        return "Menu{" +
        "menuId=" + menuId +
        ", patientMenuId=" + patientMenuId +
        ", menuName=" + menuName +
        ", menuUrl=" + menuUrl +
        "}";
    }
}
