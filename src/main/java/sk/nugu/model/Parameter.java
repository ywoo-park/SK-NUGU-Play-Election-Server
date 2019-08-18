package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Parameter {
    private Key.SggName sggName;
    private Key.SggName sggName_busan;
    private Key.SggName sggName_daegu;
    private Key.SggName sggName_incheon;
    private Key.SggName sggName_daejeon;
    private Key.SggName sggName_ulsan;
    private Key.SggName sggName_sejong;
    private Key.SggName sggName_gyeonggi;
    private Key.SggName sggName_gangwon;
    private Key.SggName sggName_chungcheong_bukdo;
    private Key.SggName sggName_chungcheong_namdo;
    private Key.SggName sggName_jeolla_bukdo;
    private Key.SggName sggName_jeolla_namdo;
    private Key.SggName sggName_jeju;
    private Key.SggName sggName_gwangju;
    private Key.SggName sggName_gyeongsang_bukdo;
    private Key.SggName sggName_gyeongsang_namdo;

    private Key.Name name;
    private Key.JdName jdName;
}
