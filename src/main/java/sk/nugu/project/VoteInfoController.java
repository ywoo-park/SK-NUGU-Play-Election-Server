package sk.nugu.project;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.nugu.model.Key;
import sk.nugu.model.Transaction.TransactionnReq;

import java.util.List;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@RestController
public class VoteInfoController {

    private final OpenApiService openApiService;
    public VoteInfoController(final OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

/*
    @GetMapping("/vote")
    public ResponseEntity getVoteInfoData(@RequestBody TransactionnReq transactionnReq) {
        try {
            JSONObject result = openApiService.getCandiateApiData();
            JSONObject items  = result.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items");
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
    */
    @GetMapping("/code")
    public ResponseEntity getCode() {
        try {
            String result = openApiService.getElectionCode();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}
