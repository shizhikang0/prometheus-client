package com.shi.prometheus.business.channel.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @Description: 通道
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
@DatabaseTable(tableName = "channel")
public class Channel {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "park_id")
    private Integer parkId;

    @DatabaseField(columnName = "channel_no")
    private Integer channelNo;  //道口号

    @DatabaseField(columnName = "channel_name")
    private String channelName;  //道口号

    @DatabaseField(columnName = "channel_prop")
    private Integer channelProp;    //进出方向（1-入口；2-出口）

    @DatabaseField(columnName = "control_mach_ip")
    private String controlMachIP;   //主板IP

    @DatabaseField(columnName = "control_mach_port")
    private Integer controlMachPort;   //主板端口号

    @DatabaseField(columnName = "use_camera1")
    private Integer useCamera1; //是否启用主相机1（0-不启用；1-启用）

    @DatabaseField(columnName = "camera_ip1")
    private String cameraIP1;   //CameraIP1

    @DatabaseField(columnName = "camera_port1")
    private Integer cameraPort1;    //主相机1端口

    @DatabaseField(columnName = "camera_user1")
    private String cameraUser1; //主相机1用户名

    @DatabaseField(columnName = "camera_pwd1")
    private String cameraPwd1;  //主相机1登录密码

    @DatabaseField(columnName = "use_camera2")
    private Integer useCamera2; //是否启用主相机2（0-不启用；1-启用）

    @DatabaseField(columnName = "camera_ip2")
    private String cameraIP2;   //主相机2IP

    @DatabaseField(columnName = "camera_port2")
    private Integer cameraPort2;    //主相机2端口

    @DatabaseField(columnName = "camera_user2")
    private String cameraUser2; //主相机2用户名

    @DatabaseField(columnName = "camera_pwd2")
    private String cameraPwd2;  //主相机2登录密码

    @DatabaseField(columnName = "use_son_camera")
    private Integer useSonCamera; //是否启用从相机（0-不启用；1-启用）

    @DatabaseField(columnName = "son_camera_ip")
    private String sonCameraIP;   //从相机IP

    @DatabaseField(columnName = "son_camera_port")
    private Integer sonCameraPort;    //从相机端口

    @DatabaseField(columnName = "son_camera_user")
    private String sonCameraUser; //从相机用户名

    @DatabaseField(columnName = "son_camera_pwd")
    private String sonCameraPwd;  //从相机登录密码

    @DatabaseField(columnName = "use_tide_camera")
    private Integer useTideCamera; //是否启用潮汐相机（0-不启用；1-启用）

    @DatabaseField(columnName = "tide_camera_ip")
    private String tideCameraIP;   //潮汐相机IP

    @DatabaseField(columnName = "tide_camera_port")
    private Integer tideCameraPort;    //潮汐相机端口

    @DatabaseField(columnName = "tide_camera_user")
    private String tideCameraUser; //潮汐相机用户名

    @DatabaseField(columnName = "tide_camera_pwd")
    private String tideCameraPwd;  //潮汐相机登录密码

    @DatabaseField(columnName = "state")
    private Integer state;  //启用状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public Integer getChannelProp() {
        return channelProp;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setChannelProp(Integer channelProp) {
        this.channelProp = channelProp;
    }

    public String getControlMachIP() {
        return controlMachIP;
    }

    public void setControlMachIP(String controlMachIP) {
        this.controlMachIP = controlMachIP;
    }

    public Integer getControlMachPort() {
        return controlMachPort;
    }

    public void setControlMachPort(Integer controlMachPort) {
        this.controlMachPort = controlMachPort;
    }

    public Integer getUseCamera1() {
        return useCamera1;
    }

    public void setUseCamera1(Integer useCamera1) {
        this.useCamera1 = useCamera1;
    }

    public String getCameraIP1() {
        return cameraIP1;
    }

    public void setCameraIP1(String cameraIP1) {
        this.cameraIP1 = cameraIP1;
    }

    public Integer getCameraPort1() {
        return cameraPort1;
    }

    public void setCameraPort1(Integer cameraPort1) {
        this.cameraPort1 = cameraPort1;
    }

    public String getCameraUser1() {
        return cameraUser1;
    }

    public void setCameraUser1(String cameraUser1) {
        this.cameraUser1 = cameraUser1;
    }

    public String getCameraPwd1() {
        return cameraPwd1;
    }

    public void setCameraPwd1(String cameraPwd1) {
        this.cameraPwd1 = cameraPwd1;
    }

    public Integer getUseCamera2() {
        return useCamera2;
    }

    public void setUseCamera2(Integer useCamera2) {
        this.useCamera2 = useCamera2;
    }

    public String getCameraIP2() {
        return cameraIP2;
    }

    public void setCameraIP2(String cameraIP2) {
        this.cameraIP2 = cameraIP2;
    }

    public Integer getCameraPort2() {
        return cameraPort2;
    }

    public void setCameraPort2(Integer cameraPort2) {
        this.cameraPort2 = cameraPort2;
    }

    public String getCameraUser2() {
        return cameraUser2;
    }

    public void setCameraUser2(String cameraUser2) {
        this.cameraUser2 = cameraUser2;
    }

    public String getCameraPwd2() {
        return cameraPwd2;
    }

    public void setCameraPwd2(String cameraPwd2) {
        this.cameraPwd2 = cameraPwd2;
    }

    public Integer getUseSonCamera() {
        return useSonCamera;
    }

    public void setUseSonCamera(Integer useSonCamera) {
        this.useSonCamera = useSonCamera;
    }

    public String getSonCameraIP() {
        return sonCameraIP;
    }

    public void setSonCameraIP(String sonCameraIP) {
        this.sonCameraIP = sonCameraIP;
    }

    public Integer getSonCameraPort() {
        return sonCameraPort;
    }

    public void setSonCameraPort(Integer sonCameraPort) {
        this.sonCameraPort = sonCameraPort;
    }

    public String getSonCameraUser() {
        return sonCameraUser;
    }

    public void setSonCameraUser(String sonCameraUser) {
        this.sonCameraUser = sonCameraUser;
    }

    public String getSonCameraPwd() {
        return sonCameraPwd;
    }

    public void setSonCameraPwd(String sonCameraPwd) {
        this.sonCameraPwd = sonCameraPwd;
    }

    public Integer getUseTideCamera() {
        return useTideCamera;
    }

    public void setUseTideCamera(Integer useTideCamera) {
        this.useTideCamera = useTideCamera;
    }

    public String getTideCameraIP() {
        return tideCameraIP;
    }

    public void setTideCameraIP(String tideCameraIP) {
        this.tideCameraIP = tideCameraIP;
    }

    public Integer getTideCameraPort() {
        return tideCameraPort;
    }

    public void setTideCameraPort(Integer tideCameraPort) {
        this.tideCameraPort = tideCameraPort;
    }

    public String getTideCameraUser() {
        return tideCameraUser;
    }

    public void setTideCameraUser(String tideCameraUser) {
        this.tideCameraUser = tideCameraUser;
    }

    public String getTideCameraPwd() {
        return tideCameraPwd;
    }

    public void setTideCameraPwd(String tideCameraPwd) {
        this.tideCameraPwd = tideCameraPwd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
