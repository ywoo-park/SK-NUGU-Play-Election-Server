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
    private Key.Name name;
    private Key.jdName jdName;
}
