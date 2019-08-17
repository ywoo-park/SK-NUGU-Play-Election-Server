package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Election {
    @Getter
    @Setter
    @ToString
    public static class ElectionRes{
        private int giho;
        private String jdName;
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class ElectionSpecRes{
        private String gender;
        private int age;
        private String job;
        private String edu;
        private String career1;
    }
}
