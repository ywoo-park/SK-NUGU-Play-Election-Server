package sk.nugu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sk.nugu.model.Election.ElectionRes;

import java.util.List;

@Getter
@Setter
@ToString
public class Output {
    private String speechText;
    private String regiSpeechText;

    private String gender;
    private int age;
    private String edu;
    private String job;
    private String career1;
    private String birthday;

    private String sggName;
}
