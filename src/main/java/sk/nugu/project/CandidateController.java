package sk.nugu.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.nugu.model.Election.ElectionReq;

import static sk.nugu.model.DefaultRes.FAIL_DEFAULT_RES;

@RestController
public class CandidateController {

    private final OpenApiService openApiService;

    public CandidateController(final OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    @GetMapping("/cand")
    public ResponseEntity getCandidateData(@RequestBody ElectionReq electionReq) {
        try {
            String sb = openApiService.getCandiateApiData(electionReq.getSgId(),
                    electionReq.getSdName(),electionReq.getWiwName());
            return new ResponseEntity<>(sb, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.NOT_FOUND);
        }
    }

}
