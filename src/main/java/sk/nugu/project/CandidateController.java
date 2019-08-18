package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.nugu.mapper.RegisterMapper;
import sk.nugu.model.Election.ElectionSpecRes;
import sk.nugu.model.Election.ElectionRes;
import sk.nugu.model.Key;
import sk.nugu.model.Output;
import sk.nugu.model.Transaction.TransactionRes;
import sk.nugu.model.Transaction.TransactionnReq;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CandidateController {

    private final OpenApiService openApiService;
    private final RegisterMapper registerMapper;

    public CandidateController(final OpenApiService openApiService, final RegisterMapper registerMapper) {
        this.openApiService = openApiService;
        this.registerMapper = registerMapper;
    }

    // 1. 등록 선거구 기반 기본 검색(완료)
    @RequestMapping(value="/registered_loc_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getRegisteredCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            String sggName;

            if(registerMapper.findSggNameOfRegistration() == null || registerMapper.findSggNameOfRegistration().trim().length() == 0){
                output.setSpeechText("등록된 선거구가 없습니다.");
                transactionRes.setOutput(output);
                return new ResponseEntity<>(transactionRes, HttpStatus.OK);
            }
            else{
                sggName = registerMapper.findSggNameOfRegistration();
            }

            JSONObject result = openApiService.getCandiateApiData(sggName);
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            List<ElectionRes> electionResList = new ArrayList<>();
            for(int i = 0; i<itemArray.length(); i++){
                ElectionRes temp = new ElectionRes();
                JSONObject jsonObject = itemArray.getJSONObject(i);
                temp.setGiho(jsonObject.getInt("giho"));
                temp.setJdName(jsonObject.getString("jdName"));
                temp.setName(jsonObject.getString("name"));
                electionResList.add(temp);
            }

            transactionRes.setVersion(version);

            String txt = "선거구의 후보에는 ";

            for(ElectionRes electionRes :  electionResList){
                Integer.toString(electionRes.getGiho());
                txt += ("기호 " + electionRes.getGiho() + "번 "
                        + electionRes.getJdName() + " "+ electionRes.getName() + " 후보자, ");
            }
            txt.substring(0,txt.length()-5);
            txt+= "가 있습니다.";
            output.setSpeechText(txt);
            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            output.setSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }

    // 2. 등록 선거구 기반 상세 검색(완료)
    @RequestMapping(value="/detailed_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getSpecificCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            String sggName;

            sggName = registerMapper.findSggNameOfRegistration();

            JSONObject result = openApiService.getCandiateApiData(
                    sggName);
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            ElectionSpecRes temp = new ElectionSpecRes();

            String name = ""; String jdName = "";
            boolean byName = true;

            if(transactionReq.getAction().getParameters().getName() != null){
                name = transactionReq.getAction().getParameters().getName().getValue();
            }
            else if(transactionReq.getAction().getParameters().getJdName() != null) {
                jdName = transactionReq.getAction().getParameters().getJdName().getValue();
                byName = false;
            }
            else{
                output.setSpeechText("검색할 후보자 또는 정당을 다시 말해주세요.");
                transactionRes.setOutput(output);
                return new ResponseEntity<>(transactionRes,HttpStatus.OK);
            }

            for(int i = 0; i<itemArray.length(); i++){
                JSONObject jsonObject = itemArray.getJSONObject(i);

                if(byName && name.equals(jsonObject.getString("name")) ){
                    temp.setGender(jsonObject.getString("gender"));
                    temp.setAge(jsonObject.getInt("age"));
                    temp.setEdu(jsonObject.getString("edu"));
                    temp.setJob(jsonObject.getString("job"));
                    temp.setCareer1(jsonObject.getString("career1"));
                    String bday = Integer.toString(jsonObject.getInt("birthday"));
                    String bdayFormat = bday.substring(0,4) + "년 " + bday.substring(4,6) + "월 " + bday.substring(6,8) + "일";
                    temp.setBirthday(bdayFormat);
                    break;
                }
                if(!byName && jdName.equals(jsonObject.getString("jdName"))){
                    temp.setGender(jsonObject.getString("gender"));
                    temp.setAge(jsonObject.getInt("age"));
                    temp.setEdu(jsonObject.getString("edu"));
                    temp.setJob(jsonObject.getString("job"));
                    temp.setCareer1(jsonObject.getString("career1"));
                    String bday = Integer.toString(jsonObject.getInt("birthday"));
                    String bdayFormat = bday.substring(0,4) + "년 " + bday.substring(4,6) + "월 " + bday.substring(6,8) + "일";
                    temp.setBirthday(bdayFormat);
                    break;
                }
                if(i == itemArray.length()-1){
                    output.setSpeechText("선택하신 선거구의 후보가 아닙니다. 다시 말해주세요.");
                    transactionRes.setOutput(output);
                    return new ResponseEntity<>(transactionRes,HttpStatus.OK);
                }
            }

            transactionRes.setVersion(version);
            output.setSpeechText("후보자 조회 성공");
            output.setAge(temp.getAge()); output.setEdu(temp.getEdu()); output.setCareer1(temp.getCareer1());
            output.setGender(temp.getGender()); output.setJob(temp.getJob()); output.setBirthday(temp.getBirthday());
            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            output.setSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    void deleteTable(){
        registerMapper.deleteTable();
    }

    @RequestMapping(value="/CityInfo", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity firstController(){
        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();
        output.setRegiSpeechText("샘플 발화");
        transactionRes.setOutput(output);
        return new ResponseEntity<>(transactionRes,HttpStatus.OK);
    }
}
