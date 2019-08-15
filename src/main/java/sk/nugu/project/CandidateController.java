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

    @RequestMapping(value="/cand", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity getCandidateData(@RequestBody TransactionnReq transactionReq) {
        try {
            String version = transactionReq.getVersion();
            Key.SgId sgId = transactionReq.getAction().getParameters().getSgId();
            Key.SdName sdName = transactionReq.getAction().getParameters().getSdName();
            Key.WiwName wiwName = transactionReq.getAction().getParameters().getWiwName();
            JSONObject result = openApiService.getCandiateApiData(sgId.getValue(),
                   sdName.getValue(),
                    wiwName.getValue());
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            JSONArray itemArray = items.getJSONArray("item");
            List<ElectionRes> electionResList = new ArrayList<>();
            for(int i = 0; i<itemArray.length(); i++){
                ElectionRes temp = new ElectionRes();
                JSONObject jsonObject = itemArray.getJSONObject(i);
                temp.setJdName(jsonObject.getString("jdName"));
                temp.setEdu(jsonObject.getString("edu"));
                electionResList.add(temp);
            }
            TransactionRes transactionRes = new TransactionRes();
            transactionRes.setVersion(version);

            Output output = new Output();
            output.setEdu(electionResList.get(0).getEdu());
            output.setJdName(electionResList.get(0).getJdName());
            output.setSpeechText("잘 작동합니다.");

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}
