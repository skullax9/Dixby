package Dixby;

public class Functions {
    static String matchName;
    static String divisionName;
    void matchType(int i) {
        if (i == 30) {
            matchName = "리그 친선";
        } else if (i == 40) {
            matchName = "클래식 1on1";
        } else if (i == 50) {
            matchName = "공식경기";
        } else if (i == 52) {
            matchName = "감독모드";
        } else if (i == 60) {
            matchName = "공식 친선";
        } else if (i == 204) {
            matchName = "볼타 친선";
        } else if (i == 214) {
            matchName = "볼타 공식";
        } else if (i == 224) {
            matchName = "볼타 AI대전";
        } else if (i == 234) {
            matchName = "";
        }
    }

    void matchDivision(int i) {
        if (i == 3100) {
            divisionName = "유망주3";
        } else if (i == 3000) {
            divisionName = "유망주2";
        } else if (i == 2900) {
            divisionName = "유망주1";
        } else if (i == 2800) {
            divisionName = "세미프로3";
        } else if (i == 2700) {
            divisionName = "세미프로2";
        } else if (i == 2600) {
            divisionName = "세미프로1";
        } else if (i == 2500) {
            divisionName = "프로3";
        } else if (i == 2400) {
            divisionName = "프로2";
        } else if (i == 2300) {
            divisionName = "프로1";
        } else if (i == 2200) {
            divisionName = "월드클래스3";
        } else if (i == 2100) {
            divisionName = "월드클래스2";
        } else if (i == 2000) {
            divisionName = "월드클래스1";
        } else if (i == 1300) {
            divisionName = "챌린지3";
        } else if (i == 1200) {
            divisionName = "챌린지2";
        } else if (i == 1100) {
            divisionName = "챌린지1";
        } else if (i == 1000) {
            divisionName = "슈퍼챌린지";
        } else if (i == 900) {
            divisionName = "챔피언스";
        } else if (i < 800) {
            divisionName = "슈퍼챔피언스";
        }
    }
}
