package chatbot_ex.chatbot_ex.dialogflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class KakaoRest {

    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json"})
    public String callAPI(@RequestBody Map<String, Object> params){

        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(params);
            int x = 0;
            System.out.println(jsonInString);

            return "정상적으로 작동되었습니다";
        } catch (Exception e) {
            System.out.println("에러 발생 " + e);

            return "에러발생";
        }

    }
}
