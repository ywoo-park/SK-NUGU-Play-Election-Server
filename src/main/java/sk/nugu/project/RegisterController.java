package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sk.nugu.mapper.RegiMapper;
import sk.nugu.model.Election;
import sk.nugu.model.Key;
import sk.nugu.model.Output;
import sk.nugu.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@RestController
@Slf4j
public class RegisterController {
    private final OpenApiService openApiService;
    //private final RegiMapper regiMapper;
    public RegisterController(final OpenApiService openApiService) {
        this.openApiService = openApiService;
        //this.regiMapper = regiMapper;
    }

    @RequestMapping(value="/action/place_registration", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity saveSdName(@RequestBody Transaction.TransactionnReq transactionReq) {
        try {
            String version = transactionReq.getVersion();
            Key.SdName sdName = transactionReq.getAction().getParameters().getSdName();

            //regiMapper.saveSdName(sdName.getValue());

            Transaction.TransactionRes transactionRes = new Transaction.TransactionRes();
            transactionRes.setVersion(version);

            Output output = new Output();
            output.setSpeechText("등록 되었습니다.");

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}
