package com.shi.prometheus.business.park.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
@DatabaseTable(tableName = "park")
public class Park {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "park_no")
    private String parkNo;

    @DatabaseField(columnName = "park_id")
    private Integer parkId;

    @DatabaseField(columnName = "state")
    private Integer state;

    public Park() {
    }

    public Park(Integer id, String name, String parkNo) {
        this.id = id;
        this.name = name;
        this.parkNo = parkNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
