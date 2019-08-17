package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Parameter {
    private Key.SgId sgId;
    private Key.SdName sdName;
    private Key.SggName sggName;
}
