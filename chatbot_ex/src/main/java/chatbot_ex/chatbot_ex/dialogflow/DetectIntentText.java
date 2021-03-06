package chatbot_ex.chatbot_ex.dialogflow;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetectIntentText {

    public static void detectIntentTexts(String projectId, List<String> texts, String sessionId,
                                         String languageCode) throws Exception {

        // Instantiates a client 클라이언트를 인스턴스화함
        try (SessionsClient sessionsClient = SessionsClient.create()) {

            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            // 세션 아이디와 프로젝트 아이디를 사용해서 세션 이름을 설정함
            SessionName session = SessionName.of(projectId, sessionId);
            System.out.println("Session Path: " + session.toString());

            // Detect intents for each text input
            for (String text : texts) {
                // Set the text (hello) and language code (en-US) for the query
                // 쿼리를 만들기 위해 받은 텍스트와 언어를 설정함
                Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

                // Build the query with the TextInput
                // 위에서 만든 TextInput으로 쿼리를 만듦
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                // DetectIntentResponse - The message returned from the DetectIntent method.
                // queryInput으로 response를 받아오나봄?
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                // response에서 쿼리 결과 저장
                QueryResult queryResult = response.getQueryResult();

                System.out.println("====================");
                System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
                System.out.format("Detected Intent: %s (confidence: %f)\n",
                        queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
                System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());
            }
        }
    }
    // [END dialogflow_detect_intent_text]

    // [START run_application]
    public static void main(String[] args) throws Exception {
        ArrayList<String> texts = new ArrayList<>();
        String projectId = "";
        String sessionId = UUID.randomUUID().toString();
        String languageCode = "en-US";

        try {
            String command = args[0];
            if (command.equals("--projectId")) {
                projectId = args[1];
            }

            for (int i = 2; i < args.length; i++) {
                switch (args[i]) {
                    case "--sessionId":
                        sessionId = args[++i];
                        break;
                    case "--languageCode":
                        languageCode = args[++i];
                        break;
                    default:
                        texts.add(args[i]);
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Usage:");
            System.out.println("mvn exec:java -DDetectIntentTexts "
                    + "-Dexec.args=\"--projectId PROJECT_ID --sessionId SESSION_ID "
                    + "'hello' 'book a meeting room' 'Mountain View' 'tomorrow' '10 am' '2 hours' "
                    + "'10 people' 'A' 'yes'\"\n");

            System.out.println("Commands: text");
            System.out.println("\t--projectId <projectId> - Project/Agent Id");
            System.out.println("\tText: \"hello\" \"book a meeting room\" \"Mountain View\" \"tomorrow\" "
                    + "\"10am\" \"2 hours\" \"10 people\" \"A\" \"yes\"");
            System.out.println("Optional Commands:");
            System.out.println("\t--languageCode <languageCode> - Language Code of the query (Defaults "
                    + "to \"en-US\".)");
            System.out.println("\t--sessionId <sessionId> - Identifier of the DetectIntent session "
                    + "(Defaults to a random UUID.)");
        }

        detectIntentTexts(projectId, texts, sessionId, languageCode);
    }
}
