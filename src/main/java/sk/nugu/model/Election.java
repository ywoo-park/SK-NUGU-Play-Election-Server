package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Election {
    @Getter
    @Setter
    @ToString
    public static class ElectionRes{
        private String jdName;
        private String edu;
    }
}
