package com.example.android_project;

import java.util.HashMap;
import java.util.Map;

public class Recipe {

    private Long id;
    private String ATT_FILE_NO_MAIN, ATT_FILE_NO_MK;
    private String MANUAL01, MANUAL02, MANUAL03, MANUAL04, MANUAL05, MANUAL06, MANUAL07, MANUAL08, MANUAL09, MANUAL10;
    private String MANUAL11, MANUAL12, MANUAL13, MANUAL14, MANUAL15, MANUAL16, MANUAL17, MANUAL18, MANUAL19, MANUAL20;
    private String MANUAL_IMG01, MANUAL_IMG02, MANUAL_IMG03, MANUAL_IMGO4, MANUAL_IMG05, MANUAL_IMG06, MANUAL_IMG07, MANUAL_IMGO8, MANUAL_IMG09, MANUAL_IMG10, MANUAL_IMG11, MANUAL_IMG12;
    private String MANUAL_IMG13, MANUAL_IMG14, MANUAL_IMG15, MANUAL_IMG16, MANUAL_IMG17, MANUAL_IMG18, MANUAL_IMG19, MANUAL_IMG20;
    private String RCP_NM;
    private String RCP_PARTS_DTLS, RCP_PAT2, RCP_SEQ, RCP_WAY2;
    private String HASH_TAG;
    private String INFO_CAR, INFO_ENG, INFO_FAT, INFO_NA, INFO_PRO, INFO_WGT;

    public Recipe(Long id, String ATT_FILE_NO_MAIN, String ATT_FILE_NO_MK, String MANUAL01, String MANUAL02, String MANUAL03, String MANUAL04, String MANUAL05, String MANUAL06, String MANUAL07, String MANUAL08, String MANUAL09, String MANUAL10, String MANUAL11, String MANUAL12, String MANUAL13, String MANUAL14, String MANUAL15, String MANUAL16, String MANUAL17, String MANUAL18, String MANUAL19, String MANUAL20, String MANUAL_IMG01, String MANUAL_IMG02, String MANUAL_IMG03, String MANUAL_IMGO4, String MANUAL_IMG05, String MANUAL_IMG06, String MANUAL_IMG07, String MANUAL_IMGO8, String MANUAL_IMG09, String MANUAL_IMG10, String MANUAL_IMG11, String MANUAL_IMG12, String MANUAL_IMG13, String MANUAL_IMG14, String MANUAL_IMG15, String MANUAL_IMG16, String MANUAL_IMG17, String MANUAL_IMG18, String MANUAL_IMG19, String MANUAL_IMG20, String RCP_NM, String RCP_PARTS_DTLS, String RCP_PAT2, String RCP_SEQ, String RCP_WAY2, String HASH_TAG, String INFO_CAR, String INFO_ENG, String INFO_FAT, String INFO_NA, String INFO_PRO, String INFO_WGT) {
        this.id = id;
        this.ATT_FILE_NO_MAIN = ATT_FILE_NO_MAIN;
        this.ATT_FILE_NO_MK = ATT_FILE_NO_MK;
        this.MANUAL01 = MANUAL01;
        this.MANUAL02 = MANUAL02;
        this.MANUAL03 = MANUAL03;
        this.MANUAL04 = MANUAL04;
        this.MANUAL05 = MANUAL05;
        this.MANUAL06 = MANUAL06;
        this.MANUAL07 = MANUAL07;
        this.MANUAL08 = MANUAL08;
        this.MANUAL09 = MANUAL09;
        this.MANUAL10 = MANUAL10;
        this.MANUAL11 = MANUAL11;
        this.MANUAL12 = MANUAL12;
        this.MANUAL13 = MANUAL13;
        this.MANUAL14 = MANUAL14;
        this.MANUAL15 = MANUAL15;
        this.MANUAL16 = MANUAL16;
        this.MANUAL17 = MANUAL17;
        this.MANUAL18 = MANUAL18;
        this.MANUAL19 = MANUAL19;
        this.MANUAL20 = MANUAL20;
        this.MANUAL_IMG01 = MANUAL_IMG01;
        this.MANUAL_IMG02 = MANUAL_IMG02;
        this.MANUAL_IMG03 = MANUAL_IMG03;
        this.MANUAL_IMGO4 = MANUAL_IMGO4;
        this.MANUAL_IMG05 = MANUAL_IMG05;
        this.MANUAL_IMG06 = MANUAL_IMG06;
        this.MANUAL_IMG07 = MANUAL_IMG07;
        this.MANUAL_IMGO8 = MANUAL_IMGO8;
        this.MANUAL_IMG09 = MANUAL_IMG09;
        this.MANUAL_IMG10 = MANUAL_IMG10;
        this.MANUAL_IMG11 = MANUAL_IMG11;
        this.MANUAL_IMG12 = MANUAL_IMG12;
        this.MANUAL_IMG13 = MANUAL_IMG13;
        this.MANUAL_IMG14 = MANUAL_IMG14;
        this.MANUAL_IMG15 = MANUAL_IMG15;
        this.MANUAL_IMG16 = MANUAL_IMG16;
        this.MANUAL_IMG17 = MANUAL_IMG17;
        this.MANUAL_IMG18 = MANUAL_IMG18;
        this.MANUAL_IMG19 = MANUAL_IMG19;
        this.MANUAL_IMG20 = MANUAL_IMG20;
        this.RCP_NM = RCP_NM;
        this.RCP_PARTS_DTLS = RCP_PARTS_DTLS;
        this.RCP_PAT2 = RCP_PAT2;
        this.RCP_SEQ = RCP_SEQ;
        this.RCP_WAY2 = RCP_WAY2;
        this.HASH_TAG = HASH_TAG;
        this.INFO_CAR = INFO_CAR;
        this.INFO_ENG = INFO_ENG;
        this.INFO_FAT = INFO_FAT;
        this.INFO_NA = INFO_NA;
        this.INFO_PRO = INFO_PRO;
        this.INFO_WGT = INFO_WGT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getATT_FILE_NO_MAIN() {
        return ATT_FILE_NO_MAIN;
    }

    public void setATT_FILE_NO_MAIN(String ATT_FILE_NO_MAIN) {
        this.ATT_FILE_NO_MAIN = ATT_FILE_NO_MAIN;
    }

    public String getATT_FILE_NO_MK() {
        return ATT_FILE_NO_MK;
    }

    public void setATT_FILE_NO_MK(String ATT_FILE_NO_MK) {
        this.ATT_FILE_NO_MK = ATT_FILE_NO_MK;
    }

    public String getMANUAL01() {
        return MANUAL01;
    }

    public void setMANUAL01(String MANUAL01) {
        this.MANUAL01 = MANUAL01;
    }

    public String getMANUAL02() {
        return MANUAL02;
    }

    public void setMANUAL02(String MANUAL02) {
        this.MANUAL02 = MANUAL02;
    }

    public String getMANUAL03() {
        return MANUAL03;
    }

    public void setMANUAL03(String MANUAL03) {
        this.MANUAL03 = MANUAL03;
    }

    public String getMANUAL04() {
        return MANUAL04;
    }

    public void setMANUAL04(String MANUAL04) {
        this.MANUAL04 = MANUAL04;
    }

    public String getMANUAL05() {
        return MANUAL05;
    }

    public void setMANUAL05(String MANUAL05) {
        this.MANUAL05 = MANUAL05;
    }

    public String getMANUAL06() {
        return MANUAL06;
    }

    public void setMANUAL06(String MANUAL06) {
        this.MANUAL06 = MANUAL06;
    }

    public String getMANUAL07() {
        return MANUAL07;
    }

    public void setMANUAL07(String MANUAL07) {
        this.MANUAL07 = MANUAL07;
    }

    public String getMANUAL08() {
        return MANUAL08;
    }

    public void setMANUAL08(String MANUAL08) {
        this.MANUAL08 = MANUAL08;
    }

    public String getMANUAL09() {
        return MANUAL09;
    }

    public void setMANUAL09(String MANUAL09) {
        this.MANUAL09 = MANUAL09;
    }

    public String getMANUAL10() {
        return MANUAL10;
    }

    public void setMANUAL10(String MANUAL10) {
        this.MANUAL10 = MANUAL10;
    }

    public String getMANUAL11() {
        return MANUAL11;
    }

    public void setMANUAL11(String MANUAL11) {
        this.MANUAL11 = MANUAL11;
    }

    public String getMANUAL12() {
        return MANUAL12;
    }

    public void setMANUAL12(String MANUAL12) {
        this.MANUAL12 = MANUAL12;
    }

    public String getMANUAL13() {
        return MANUAL13;
    }

    public void setMANUAL13(String MANUAL13) {
        this.MANUAL13 = MANUAL13;
    }

    public String getMANUAL14() {
        return MANUAL14;
    }

    public void setMANUAL14(String MANUAL14) {
        this.MANUAL14 = MANUAL14;
    }

    public String getMANUAL15() {
        return MANUAL15;
    }

    public void setMANUAL15(String MANUAL15) {
        this.MANUAL15 = MANUAL15;
    }

    public String getMANUAL16() {
        return MANUAL16;
    }

    public void setMANUAL16(String MANUAL16) {
        this.MANUAL16 = MANUAL16;
    }

    public String getMANUAL17() {
        return MANUAL17;
    }

    public void setMANUAL17(String MANUAL17) {
        this.MANUAL17 = MANUAL17;
    }

    public String getMANUAL18() {
        return MANUAL18;
    }

    public void setMANUAL18(String MANUAL18) {
        this.MANUAL18 = MANUAL18;
    }

    public String getMANUAL19() {
        return MANUAL19;
    }

    public void setMANUAL19(String MANUAL19) {
        this.MANUAL19 = MANUAL19;
    }

    public String getMANUAL20() {
        return MANUAL20;
    }

    public void setMANUAL20(String MANUAL20) {
        this.MANUAL20 = MANUAL20;
    }

    public String getMANUAL_IMG01() {
        return MANUAL_IMG01;
    }

    public void setMANUAL_IMG01(String MANUAL_IMG01) {
        this.MANUAL_IMG01 = MANUAL_IMG01;
    }

    public String getMANUAL_IMG02() {
        return MANUAL_IMG02;
    }

    public void setMANUAL_IMG02(String MANUAL_IMG02) {
        this.MANUAL_IMG02 = MANUAL_IMG02;
    }

    public String getMANUAL_IMG03() {
        return MANUAL_IMG03;
    }

    public void setMANUAL_IMG03(String MANUAL_IMG03) {
        this.MANUAL_IMG03 = MANUAL_IMG03;
    }

    public String getMANUAL_IMGO4() {
        return MANUAL_IMGO4;
    }

    public void setMANUAL_IMGO4(String MANUAL_IMGO4) {
        this.MANUAL_IMGO4 = MANUAL_IMGO4;
    }

    public String getMANUAL_IMG05() {
        return MANUAL_IMG05;
    }

    public void setMANUAL_IMG05(String MANUAL_IMG05) {
        this.MANUAL_IMG05 = MANUAL_IMG05;
    }

    public String getMANUAL_IMG06() {
        return MANUAL_IMG06;
    }

    public void setMANUAL_IMG06(String MANUAL_IMG06) {
        this.MANUAL_IMG06 = MANUAL_IMG06;
    }

    public String getMANUAL_IMG07() {
        return MANUAL_IMG07;
    }

    public void setMANUAL_IMG07(String MANUAL_IMG07) {
        this.MANUAL_IMG07 = MANUAL_IMG07;
    }

    public String getMANUAL_IMGO8() {
        return MANUAL_IMGO8;
    }

    public void setMANUAL_IMGO8(String MANUAL_IMGO8) {
        this.MANUAL_IMGO8 = MANUAL_IMGO8;
    }

    public String getMANUAL_IMG09() {
        return MANUAL_IMG09;
    }

    public void setMANUAL_IMG09(String MANUAL_IMG09) {
        this.MANUAL_IMG09 = MANUAL_IMG09;
    }

    public String getMANUAL_IMG10() {
        return MANUAL_IMG10;
    }

    public void setMANUAL_IMG10(String MANUAL_IMG10) {
        this.MANUAL_IMG10 = MANUAL_IMG10;
    }

    public String getMANUAL_IMG11() {
        return MANUAL_IMG11;
    }

    public void setMANUAL_IMG11(String MANUAL_IMG11) {
        this.MANUAL_IMG11 = MANUAL_IMG11;
    }

    public String getMANUAL_IMG12() {
        return MANUAL_IMG12;
    }

    public void setMANUAL_IMG12(String MANUAL_IMG12) {
        this.MANUAL_IMG12 = MANUAL_IMG12;
    }

    public String getMANUAL_IMG13() {
        return MANUAL_IMG13;
    }

    public void setMANUAL_IMG13(String MANUAL_IMG13) {
        this.MANUAL_IMG13 = MANUAL_IMG13;
    }

    public String getMANUAL_IMG14() {
        return MANUAL_IMG14;
    }

    public void setMANUAL_IMG14(String MANUAL_IMG14) {
        this.MANUAL_IMG14 = MANUAL_IMG14;
    }

    public String getMANUAL_IMG15() {
        return MANUAL_IMG15;
    }

    public void setMANUAL_IMG15(String MANUAL_IMG15) {
        this.MANUAL_IMG15 = MANUAL_IMG15;
    }

    public String getMANUAL_IMG16() {
        return MANUAL_IMG16;
    }

    public void setMANUAL_IMG16(String MANUAL_IMG16) {
        this.MANUAL_IMG16 = MANUAL_IMG16;
    }

    public String getMANUAL_IMG17() {
        return MANUAL_IMG17;
    }

    public void setMANUAL_IMG17(String MANUAL_IMG17) {
        this.MANUAL_IMG17 = MANUAL_IMG17;
    }

    public String getMANUAL_IMG18() {
        return MANUAL_IMG18;
    }

    public void setMANUAL_IMG18(String MANUAL_IMG18) {
        this.MANUAL_IMG18 = MANUAL_IMG18;
    }

    public String getMANUAL_IMG19() {
        return MANUAL_IMG19;
    }

    public void setMANUAL_IMG19(String MANUAL_IMG19) {
        this.MANUAL_IMG19 = MANUAL_IMG19;
    }

    public String getMANUAL_IMG20() {
        return MANUAL_IMG20;
    }

    public void setMANUAL_IMG20(String MANUAL_IMG20) {
        this.MANUAL_IMG20 = MANUAL_IMG20;
    }

    public String getRCP_NM() {
        return RCP_NM;
    }

    public void setRCP_NM(String RCP_NM) {
        this.RCP_NM = RCP_NM;
    }

    public String getRCP_PARTS_DTLS() {
        return RCP_PARTS_DTLS;
    }

    public void setRCP_PARTS_DTLS(String RCP_PARTS_DTLS) {
        this.RCP_PARTS_DTLS = RCP_PARTS_DTLS;
    }

    public String getRCP_PAT2() {
        return RCP_PAT2;
    }

    public void setRCP_PAT2(String RCP_PAT2) {
        this.RCP_PAT2 = RCP_PAT2;
    }

    public String getRCP_SEQ() {
        return RCP_SEQ;
    }

    public void setRCP_SEQ(String RCP_SEQ) {
        this.RCP_SEQ = RCP_SEQ;
    }

    public String getRCP_WAY2() {
        return RCP_WAY2;
    }

    public void setRCP_WAY2(String RCP_WAY2) {
        this.RCP_WAY2 = RCP_WAY2;
    }

    public String getHASH_TAG() {
        return HASH_TAG;
    }

    public void setHASH_TAG(String HASH_TAG) {
        this.HASH_TAG = HASH_TAG;
    }

    public String getINFO_CAR() {
        return INFO_CAR;
    }

    public void setINFO_CAR(String INFO_CAR) {
        this.INFO_CAR = INFO_CAR;
    }

    public String getINFO_ENG() {
        return INFO_ENG;
    }

    public void setINFO_ENG(String INFO_ENG) {
        this.INFO_ENG = INFO_ENG;
    }

    public String getINFO_FAT() {
        return INFO_FAT;
    }

    public void setINFO_FAT(String INFO_FAT) {
        this.INFO_FAT = INFO_FAT;
    }

    public String getINFO_NA() {
        return INFO_NA;
    }

    public void setINFO_NA(String INFO_NA) {
        this.INFO_NA = INFO_NA;
    }

    public String getINFO_PRO() {
        return INFO_PRO;
    }

    public void setINFO_PRO(String INFO_PRO) {
        this.INFO_PRO = INFO_PRO;
    }

    public String getINFO_WGT() {
        return INFO_WGT;
    }

    public void setINFO_WGT(String INFO_WGT) {
        this.INFO_WGT = INFO_WGT;
    }

    public Recipe() {

    }
}
