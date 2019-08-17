package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Key {
    @Getter
    @Setter
    @ToString
    public static class SgId{
        private String type;
        private String value;
    }
    @Getter
    @Setter
    @ToString
    public static class SdName{
        private String type;
        private String value;
    }
    @Getter
    @Setter
    @ToString
    public static class SggName{
        private String type;
        private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class Name{
        private String type;
        private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class jdName{
        private String type;
        private String value;
    }
}
