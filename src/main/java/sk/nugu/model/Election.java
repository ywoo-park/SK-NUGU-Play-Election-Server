package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class Election {
    @Getter
    @Setter
    @ToString
    public static class ElectionReq{
        private String sgId;
        private String sdName;
        private String wiwName;
    }
}
