package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Action {
    private String actionName;
    private Parameter parameters;
}
