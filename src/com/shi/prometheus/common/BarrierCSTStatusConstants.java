package com.shi.prometheus.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @Description: 修改完枚举项之后，执行最下面的main方法，测试sign跟code是否多对一！！！
 * @Author: shizhikang
 * @Date: 2020/11/29 16:12
 */
public class BarrierCSTStatusConstants {

    public static final char SIGN_SEPARATE = ':';

    public static final int ILLEGAL_CODE = -1;

    /**
     * 查询道闸状态
     */
    public static final int CST_LM_UP1_CODE = 1;
    public static final int CST_LM_UP_CODE = 2;
    public static final int CST_LM_DOWN1_CODE = 3;
    public static final int CST_LM_DOWN_CODE = 4;
    public static final int CST_OPENING1_CODE = 5;
    public static final int CST_OPENING_CODE = 6;
    public static final int CST_MD_STOP1_CODE = 7;
    public static final int CST_MD_STOP_CODE = 8;
    public static final int CST_CLOSING_CODE = 9;
    public static final int LOCKED_CODE = 10;

    /**
     * 实时返回开、关闸控制信息及道闸状态信息
     */
    public static final int LM_UP_CODE = 11;
    public static final int LM_DOWN_CODE = 12;
    public static final int MD_STOP_CODE = 13;
    public static final int OPEN_XK_CODE = 14;
    public static final int OPEN_YK_CODE = 15;
    public static final int OPEN_DG_CODE = 16;
    public static final int OPEN_IR_CODE = 17;
    public static final int CLOSE_DG_CODE = 18;
    public static final int CLOSE_XK_CODE = 19;
    public static final int CLOSE_YK_CODE = 20;
    public static final int CLOSE_DL_CODE = 21;
    public static final int STOP_XK_CODE = 22;
    public static final int STOP_YK_CODE = 23;

    public static final int RDG_YES_CODE = 24;
    public static final int RDG_NO_CODE = 25;

    public static final int QUERY_STATUS_CST = 26;
    public static final int QUERY_STATUS_RDG = 27;


    public enum BarrierCSTStatusEnum {
        CST_LM_UP1_ENUM(CST_LM_UP1_CODE, Arrays.asList("cstLM_up1", "LM_up1"), "道闸起到位地感被压"),
        CST_LM_UP_ENUM(CST_LM_UP_CODE, Arrays.asList("cstLM_up"), "道闸起到位地感没有被压"),
        CST_LM_DOWN1_ENUM(CST_LM_DOWN1_CODE, Arrays.asList("cstLM_down1", "LM_down1"), "落到位地感被压"),
        CST_LM_DOWN_ENUM(CST_LM_DOWN_CODE, Arrays.asList("cstLM_down"), "落到位地感没有被压"),
        CST_OPENING1_ENUM(CST_OPENING1_CODE, Arrays.asList("cstOpening1", "Opening1"), "开闸过程地感被压"),
        CST_OPENING_ENUM(CST_OPENING_CODE, Arrays.asList("cstOpening", "Opening"), "开闸过程地感没有被压"),
        CST_MD_STOP1_ENUM(CST_MD_STOP1_CODE, Arrays.asList("cstMD_stop1", "MD_stop1"), "中间停止状态地感被压"),
        CST_MD_STOP_ENUM(CST_MD_STOP_CODE, Arrays.asList("cstMD_stop"), "中间停止状态地感没有被压"),
        CST_CLOSING_ENUM(CST_CLOSING_CODE, Arrays.asList("cstClosing", "Closing"), "关闸过程中"),
        LOCKED_ENUM(LOCKED_CODE, Arrays.asList("locked"), "已锁"),

        LM_UP_ENUM(LM_UP_CODE, Arrays.asList("LM_up"), "起杆到位"),
        LM_DOWN_ENUM(LM_DOWN_CODE, Arrays.asList("LM_down"), "落杆到位"),
        MD_STOP_ENUM(MD_STOP_CODE, Arrays.asList("MD_stop"), "中间停止"),

        OPEN_XK_ENUM(OPEN_XK_CODE, Arrays.asList("openXK"), "线控开闸"),
        OPEN_YK_ENUM(OPEN_YK_CODE, Arrays.asList("openYK"), "遥控开闸"),
        OPEN_DG_ENUM(OPEN_DG_CODE,  Arrays.asList("openDG"), "地感防砸开闸"),
        OPEN_IR_ENUM(OPEN_IR_CODE, Arrays.asList("openIR"), "红外对射防砸开闸"),
        CLOSE_DG_ENUM(CLOSE_DG_CODE, Arrays.asList("closeDG"), "车过地感自动关闸"),
        CLOSE_XK_ENUM(CLOSE_XK_CODE, Arrays.asList("closeXK"), "线控开闸"),
        CLOSE_YK_ENUM(CLOSE_YK_CODE, Arrays.asList("closeYK"), "遥控开闸"),
        CLOSE_DL_ENUM(CLOSE_DL_CODE, Arrays.asList("closeDL"), "延时自动关闸"),
        STOP_XK_ENUM(STOP_XK_CODE, Arrays.asList("stopXK"), "线控停止"),
        STOP_YK_ENUM(STOP_YK_CODE, Arrays.asList("stopYK"), "遥控停止"),
        RDG_YES_ENUM(RDG_YES_CODE, Arrays.asList("RDGyes", "yes"), "地感有信号"),
        RDG_NO_ENUM(RDG_NO_CODE, Arrays.asList("RDGno", "no"), "地感无信号"),

        QUERY_STATUS_CST_ENUM(QUERY_STATUS_CST, Arrays.asList("cst"), "查询道闸状态"),
        QUERY_STATUS_RDG_ENUM(QUERY_STATUS_RDG, Arrays.asList("RDG"), "查询道闸状态"),
        ;


        private Integer code;

        private List<String> signList;

        private String description;

        BarrierCSTStatusEnum(Integer code, List<String> signList, String description) {
            this.code = code;
            this.signList = signList;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public List<String> getSignList() {
            return signList;
        }

        public String getDescription() {
            return description;
        }

        public static int getCodeBySign(String sign) {
            if (StringUtils.isBlank(sign)) {
                return ILLEGAL_CODE;
            }
            for (BarrierCSTStatusEnum statusEnum : values()) {
                List<String> list = statusEnum.signList;
                if (list == null) {
                    return ILLEGAL_CODE;
                }
                for (String s : list) {
                    if (s.equals(sign)) {
                        return statusEnum.code;
                    }
                }
            }
            return ILLEGAL_CODE;
        }

        public static String getDescriptionByCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (BarrierCSTStatusEnum statusEnum : values()) {
                if (statusEnum.code.equals(code)) {
                    return statusEnum.description;
                }
            }
            return null;
        }
    }

    /**
     * 校验BarrierCSTStatusEnum内的sign是否唯一，确保sign和code是多对一的关系，根据sign能查到正确的code
     * @param args
     */
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        BarrierCSTStatusEnum[] values = BarrierCSTStatusEnum.values();
        boolean err = false;
        for (BarrierCSTStatusEnum statusEnum : values) {
            List<String> signList = statusEnum.getSignList();
            for (String s : signList) {
                if (set.contains(s)) {
                    if (!err) {
                        err = true;
                    }
                    System.err.println("Sign \"" + s + "\" is not unique. Please modify.");
                }
                set.add(s);
            }
        }
        if (!err) {
            System.out.println("No problem.");
        }
    }
}
