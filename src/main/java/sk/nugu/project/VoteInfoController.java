package sk.nugu.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sk.nugu.model.Election.ElectionReq;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@Service
public class VoteInfoController {

    private final OpenApiService openApiService;
    public VoteInfoController(final OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    @GetMapping("/vote")
    public ResponseEntity getVoteInfoData(@RequestBody ElectionReq electionReq) {
        try {
            String sb = openApiService.getVoteInfoApiData(electionReq.getSgId(),
                    electionReq.getSdName(),electionReq.getWiwName());
            return new ResponseEntity<>(sb, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }
}
