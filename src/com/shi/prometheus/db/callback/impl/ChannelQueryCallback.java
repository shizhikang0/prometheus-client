package com.shi.prometheus.db.callback.impl;

import com.shi.prometheus.RootBootStrap;
import com.shi.prometheus.business.channel.entity.Channel;
import com.shi.prometheus.db.callback.SqlCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/11/29
 */
public class ChannelQueryCallback implements SqlCallback {
    public static final Logger logger = LogManager.getLogger(ChannelQueryCallback.class);

    private List<Channel> channelList;

    public ChannelQueryCallback(List<Channel> channelList) {
        this.channelList = channelList;
    }

    @Override
    public void execute(ResultSet rs) {
        try {
            while (rs.next()) {
                Channel channel = new Channel();
                int id = rs.getInt("ID");
                channel.setId(id);
                int parkID = rs.getInt("ParkID");
                channel.setParkId(parkID);
                int channelNO = rs.getInt("ChannelNO");
                channel.setChannelNo(channelNO);
                String channelName = rs.getString("ChannelName");
                channel.setChannelName(channelName);
                int channelProp = rs.getInt("ChannelProp");
                channel.setChannelProp(channelProp);
                String controlMachIP = rs.getString("ControlMachIP");
                channel.setControlMachIP(controlMachIP);
                int controlMachNum = rs.getInt("ControlMachNum");
                channel.setControlMachPort(controlMachNum);
                int isUseCarmera1 = rs.getInt("IsUseCarmera1");
                channel.setUseCamera1(isUseCarmera1);
                String cameraIP1 = rs.getString("CameraIP1");
                channel.setCameraIP1(cameraIP1);
                int cameraPort1 = rs.getInt("CameraPort1");
                channel.setCameraPort1(cameraPort1);
                String cameraUser1 = rs.getString("CameraUser1");
                channel.setCameraUser1(cameraUser1);
                String cameraPwd1 = rs.getString("CameraPwd1");
                channel.setCameraPwd1(cameraPwd1);
                int isUseCarmera2 = rs.getInt("IsUseCarmera2");
                channel.setUseCamera2(isUseCarmera2);
                String cameraIP2 = rs.getString("CameraIP2");
                channel.setCameraIP2(cameraIP2);
                int cameraPort2 = rs.getInt("CameraPort2");
                channel.setCameraPort2(cameraPort2);
                String cameraUser2 = rs.getString("CameraUser2");
                channel.setCameraUser2(cameraUser2);
                String cameraPwd2 = rs.getString("CameraPwd2");
                channel.setCameraPwd2(cameraPwd2);
                int isUseSonCamera = rs.getInt("IsUseSonCamera");
                channel.setUseSonCamera(isUseSonCamera);
                String sonCameraIP = rs.getString("SonCameraIP");
                channel.setSonCameraIP(sonCameraIP);
                int sonCameraPort = rs.getInt("SonCameraPort");
                channel.setSonCameraPort(sonCameraPort);
                String sonCameraUser = rs.getString("SonCameraUser");
                channel.setSonCameraUser(sonCameraUser);
                String sonCameraPwd = rs.getString("SonCameraPwd");
                channel.setSonCameraPwd(sonCameraPwd);
                int isUseTideCarmera = rs.getInt("IsUseTideCarmera");
                channel.setUseTideCamera(isUseTideCarmera);
                String tideCameraIP = rs.getString("TideCameraIP");
                channel.setTideCameraIP(tideCameraIP);
                int tideCameraPort = rs.getInt("TideCameraPort");
                channel.setTideCameraPort(tideCameraPort);
                String tideCameraUser = rs.getString("TideCameraUser");
                channel.setTideCameraUser(tideCameraUser);
                String tideCameraPwd = rs.getString("TideCameraPwd");
                channel.setTideCameraPwd(tideCameraPwd);
                int state = rs.getInt("State");
                channel.setState(state);
                channelList.add(channel);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
