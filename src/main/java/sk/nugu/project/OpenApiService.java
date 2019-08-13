package sk.nugu.project;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;

@Service
public class OpenApiService {

    public String getVoteInfoApiData(String sgId, String sdName, String wiwName) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/PolplcInfoInqireService2/getPolplcOtlnmapTrnsportInfoInqire"); /*URL*/
            String serviceKey = "FCP2QZqd3Gp4WgFjf6p72gznY9WKTFusvefoLCiMwtOO1nwhbOWw%2FeG62h1ArYKKEn0cSKDbeE6ZznAEsDsT9w%3D%3D";
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(sgId, "UTF-8")); /*선거ID*/
            urlBuilder.append("&" + URLEncoder.encode("sdName","UTF-8") + "=" + URLEncoder.encode(sdName, "UTF-8")); /*시도명*/
            urlBuilder.append("&" + URLEncoder.encode("wiwName","UTF-8") + "=" + URLEncoder.encode(wiwName, "UTF-8")); /*위원회명*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            JSONObject jsonObject = XML.toJSONObject(sb.toString());
            return jsonObject.toString();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public String getCandiateApiData(String sgId, String sdName, String wiwName) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/PofelcddInfoInqireService/getPofelcddRegistSttusInfoInqire"); /*URL*/
            String serviceKey = "fZpwa9mNIBJnu%2Bk%2FoHnzvUqya%2FnPJsAFMMsdnQ01w5WSpr%2BuJTGJT1uA5PikFfNOYxoQpl%2FPi6M1LNAi60Fx9Q%3D%3D";
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(sgId, "UTF-8")); /*선거ID*/
            urlBuilder.append("&" + URLEncoder.encode("sgTypecode","UTF-8") + "=" + URLEncoder.encode("4", "UTF-8")); /*선거종류코드*/
            urlBuilder.append("&" + URLEncoder.encode("sggName","UTF-8") + "=" + URLEncoder.encode(wiwName, "UTF-8")); /*선거구명*/
            urlBuilder.append("&" + URLEncoder.encode("sdName","UTF-8") + "=" + URLEncoder.encode(sdName, "UTF-8")); /*시도명*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            JSONObject jsonObject = XML.toJSONObject(sb.toString());
            return jsonObject.toString();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
