package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sk.nugu.model.Key;
import sk.nugu.model.Output;
import sk.nugu.model.Transaction.TransactionnReq;
import sk.nugu.model.Transaction.TransactionRes;

import java.util.ArrayList;
import java.util.List;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@RestController
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    //선거구 등록
    @RequestMapping(value="/register_electionPlace", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity saveSddName(@RequestBody TransactionnReq transactionReq) {
        TransactionRes transactionRes = new TransactionRes(); Output output = new Output();
        try {
            String version = transactionReq.getVersion();
            Key.SggName sggName = transactionReq.getAction().getParameters().getSggName();

            registerService.saveSggName(sggName.getValue());
            transactionRes.setVersion(version);
            output.setSpeechText("선거구 정보를 등록하였습니다.");

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
}
