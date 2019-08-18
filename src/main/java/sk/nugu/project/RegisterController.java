package sk.nugu.project;

import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    //선거구 등록
    @RequestMapping(value={"/seoul_loc","/busan_loc", "/daegu_loc", "/incheon_loc", "/daejeon_loc",
            "/ulsan_loc", "/sejong", "/gyeonggi_loc", "/gangwon_loc", "/chungcheong_bukdo_loc", "/chungcheong_namdo_loc",
            "/jeolla_bukdo_loc", "jeolla_namdo_loc", "gyeongsang_bukdo_loc", "gyeongsang_namdo_loc", "jeju_loc", "gwangju_loc"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity saveSddName(HttpServletRequest request, @RequestBody TransactionnReq transactionReq) {

        String urlValue = request.getServletPath();

        Key.SggName sggName = null;

        switch (urlValue) {
            case "/seoul_loc": sggName = transactionReq.getAction().getParameters().getSggName();
                break;
            case "/busan_loc" : sggName = transactionReq.getAction().getParameters().getSggName_busan();
                break;
            case "/daegu_loc": sggName = transactionReq.getAction().getParameters().getSggName_daegu();
                break;
            case "/incheon_loc":   sggName = transactionReq.getAction().getParameters().getSggName_incheon();
                break;
            case "/daejeon_loc" :  sggName = transactionReq.getAction().getParameters().getSggName_daejeon();
                break;
            case "/ulsan_loc" : sggName = transactionReq.getAction().getParameters().getSggName_ulsan();
                break;
            case "/sejong" :  sggName = transactionReq.getAction().getParameters().getSggName_sejong();
                break;
            case "/gyeonggi_loc" :  sggName = transactionReq.getAction().getParameters().getSggName_gyeonggi();
                break;
            case "/gangwon_loc":  sggName = transactionReq.getAction().getParameters().getSggName_gangwon();
                break;
            case "/chungcheong_bukdo_loc": sggName = transactionReq.getAction().getParameters().getSggName_chungcheong_bukdo();
                break;
            case "/chungcheong_namdo_loc": sggName = transactionReq.getAction().getParameters().getSggName_chungcheong_namdo();
                break;
            case "/jeolla_bukdo_loc":sggName = transactionReq.getAction().getParameters().getSggName_jeolla_bukdo();
                break;
            case "jeolla_namdo_loc": sggName = transactionReq.getAction().getParameters().getSggName_jeolla_namdo();
                break;
            case "gyeongsang_bukdo_loc": sggName = transactionReq.getAction().getParameters().getSggName_gyeongsang_bukdo();
                break;
            case "gyeongsang_namdo_loc": sggName = transactionReq.getAction().getParameters().getSggName_gyeongsang_namdo();
                break;
            case "jeju_loc": sggName = transactionReq.getAction().getParameters().getSggName_jeju();
                break;
            case "gwangju_loc": sggName = transactionReq.getAction().getParameters().getSggName_gwangju();
                break;
        }

        TransactionRes transactionRes = new TransactionRes(); Output output = new Output();
        try {
            String version = transactionReq.getVersion();

            if(sggName.getValue().equals("FAIL")){
                output.setRegiSpeechText("중복된 동이 있습니다. 시와 동 붙여 말씀해주세요.");
                transactionRes.setOutput(output);
                return new ResponseEntity<>(transactionRes, HttpStatus.OK);
            }

            registerService.saveSggNameInRegistration(sggName.getValue());
            output.setRegiSpeechText("사용자 선거구 정보를 등록하였습니다.");

            transactionRes.setVersion(version);

            transactionRes.setOutput(output);
            transactionRes.setResultCode("OK");
            return new ResponseEntity<>(transactionRes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            output.setRegiSpeechText("서버 연결 상태가 좋지 않습니다.");
            transactionRes.setOutput(output);
            return new ResponseEntity<>(transactionRes, HttpStatus.NOT_FOUND);
        }
    }
}
