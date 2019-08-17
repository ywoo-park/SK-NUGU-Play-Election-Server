package sk.nugu.project;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.nugu.model.Election.ElectionRes;
import sk.nugu.model.Key;
import sk.nugu.model.Output;
import sk.nugu.model.Transaction.TransactionRes;
import sk.nugu.model.Transaction.TransactionnReq;

import java.util.ArrayList;
import java.util.List;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@RestController
@Slf4j
public class CandidateController {

    private final OpenApiService openApiService;

    public CandidateController(final OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    // 일반 검색
    @RequestMapping(value="/general_loc_search", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getCandidateData(@RequestBody TransactionnReq transactionReq) {
        try {
            String version = transactionReq.getVersion();
            Key.SgId sgId = transactionReq.getAction().getParameters().getSgId();
            Key.SdName sdName = transactionReq.getAction().getParameters().getSdName();
            Key.SggName sggName = transactionReq.getAction().getParameters().getSggName();

            JSONObject result = openApiService.getCandiateApiData(sgId.getValue(),
                   sdName.getValue(),
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
            TransactionRes transactionRes = new TransactionRes();
            transactionRes.setVersion(version);

            Output output = new Output();

            String txt = "해당 선거구의 후보에는 ";

            for(ElectionRes electionRes :  electionResList){
                Integer.toString(electionRes.getGiho());
                txt += ("기호 " + electionRes.getGiho() + "번 "
                        + electionRes.getJdName() + " " + electionRes.getName() + "후보자 입니다. ");
            }

            output.setSpeechText(txt);

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}
