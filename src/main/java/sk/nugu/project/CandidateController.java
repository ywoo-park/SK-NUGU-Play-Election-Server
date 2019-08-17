package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
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

    // 일반 검색
    @RequestMapping(value="/general_loc_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getGeneralCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            Key.SggName sggName = transactionReq.getAction().getParameters().getSggName();

            JSONObject result = openApiService.getCandiateApiData(
                    sggName.getValue());
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
            String txt = "해당 선거구의 후보에는 ";

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
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            output.setSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }

    // 등록 선거구 기반 검색
    @RequestMapping(value="/registered_loc_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getRegisteredCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            String sggName = registerMapper.findSggName();

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

            String txt = "해당 선거구의 후보에는 ";

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

    // 상세 검색(성별)
    @RequestMapping(value="/detailed_gender_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getSpecificGenderCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            Key.SggName sggName = transactionReq.getAction().getParameters().getSggName();
            Key.Name name= transactionReq.getAction().getParameters().getName();
            Key.jdName jdName = transactionReq.getAction().getParameters().getJdName();

            JSONObject result = openApiService.getCandiateApiData(
                    sggName.getValue());
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            ElectionSpecRes temp = new ElectionSpecRes();
            for(int i = 0; i<itemArray.length(); i++){
                JSONObject jsonObject = itemArray.getJSONObject(i);

                if(jsonObject.getString("name").equals(name.getValue())
                || jsonObject.getString("jdName").equals(jdName.getValue())){
                    temp.setGender(jsonObject.getString("gender"));
                    break;
                }
            }
            transactionRes.setVersion(version);

            String txt = sggName.getValue() + " " + name.getValue() + " 후보는 " + temp.getGender() +"입니다.";

            output.setSpeechText(txt);

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            output.setSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }

    // 상세 검색(성별)
    @RequestMapping(value="/detailed_age_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getSpecificAgeCandidateData(@RequestBody TransactionnReq transactionReq) {

        TransactionRes transactionRes = new TransactionRes();
        Output output = new Output();

        try {
            String version = transactionReq.getVersion();
            Key.SggName sggName = transactionReq.getAction().getParameters().getSggName();
            Key.Name name= transactionReq.getAction().getParameters().getName();
            Key.jdName jdName = transactionReq.getAction().getParameters().getJdName();

            JSONObject result = openApiService.getCandiateApiData(
                    sggName.getValue());
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            ElectionSpecRes temp = new ElectionSpecRes();
            for(int i = 0; i<itemArray.length(); i++){
                JSONObject jsonObject = itemArray.getJSONObject(i);

                if(jsonObject.getString("name").equals(name.getValue())
                        || jsonObject.getString("jdName").equals(jdName.getValue())){
                    temp.setAge(jsonObject.getInt("age"));
                    break;
                }
            }
            transactionRes.setVersion(version);

            String txt = sggName.getValue() + " " + name.getValue() + " 후보의 나이는 " + temp.getAge() +"세 입니다.";

            output.setSpeechText(txt);

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            output.setSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    void deleteTable(){
        registerMapper.deleteTable();
    }
}
