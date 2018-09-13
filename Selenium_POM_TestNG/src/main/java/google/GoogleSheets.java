package google;
// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// [START sheets_quickstart]
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import constants.Environments;
import helpers.FileHelper;

import static utilities.Utility.getUnique;

public class GoogleSheets {
    public static Map<String,String> googleSheetInfo = new HashMap<String, String>();
    private static final String APPLICATION_NAME = "Google Sheets API Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    static String SPREADSHEET_ID = FileHelper.getJSONNode(Environments.RESOURCES_PATH + "credentials.json",
            "installed>spreadsheet_id", ">");
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void setCellValue(String sheetName, String columnName, int rowIndex, String value){
        try {
            String range = sheetName + "!" + columnName + rowIndex;//"UI-Report-Firefox!A2";
            ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList(value)));
            UpdateValuesResponse result = getSheetsService().spreadsheets().values()
                    .update(SPREADSHEET_ID, range, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static String getCellValue(String sheetName, String columnName, int rowIndex){
        try {
            String range = sheetName + "!" + columnName + rowIndex;//"UI-Report-Firefox!A2";
            ValueRange response = getSheetsService().spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            return String.valueOf(response.getValues().get(0).get(0));
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Update to google sheet: https://docs.google.com/spreadsheets/d/1UwclfT7WzOvtQA7qa5o2KdATal8LIWO1yLkQss6tXj4/edit#gid=0
    public static void updateTestCaseStatus(String testCaseName, String startDate, String duration, String status){
        try {
            String range = googleSheetInfo.get("sheetName") + "!A:A";
            ValueRange response = getSheetsService().spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<String> testCase = Arrays.asList(testCaseName);
            int findRowIndex = response.getValues().indexOf(testCase);
            if(findRowIndex <= 0) {
                findRowIndex = response.getValues().size() + 1;
                setCellValue(googleSheetInfo.get("sheetName"), "A", findRowIndex, testCaseName);
            } else findRowIndex += 1;
            setCellValue(googleSheetInfo.get("sheetName"), "C", findRowIndex, startDate);
            setCellValue(googleSheetInfo.get("sheetName"), "D", findRowIndex, duration);
            setCellValue(googleSheetInfo.get("sheetName"), "F", findRowIndex, status);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static void updateTestCaseIssue(String testCaseName, String descriptionIssue){
        try {
            String range = googleSheetInfo.get("sheetName") + "!A:A";
            ValueRange response = getSheetsService().spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<String> testCase = Arrays.asList(testCaseName);
            int findRowIndex = response.getValues().indexOf(testCase);
            if (findRowIndex <= 0) {
                findRowIndex = response.getValues().size() + 1;
                setCellValue(googleSheetInfo.get("sheetName"), "A", findRowIndex, testCaseName);
            } else findRowIndex += 1;
            setCellValue(googleSheetInfo.get("sheetName"), "E", findRowIndex, descriptionIssue);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static void insertColumnTestStatus(){
        try {
            BatchUpdateSpreadsheetResponse response;
            BatchUpdateSpreadsheetRequest batchRequests;// = new BatchUpdateSpreadsheetRequest();
            List<Request> requests = new ArrayList<Request>();

            InsertDimensionRequest insertDimensionRequest = new InsertDimensionRequest();
            DimensionRange dimRange = new DimensionRange();
            dimRange.setStartIndex(5);
            dimRange.setEndIndex(6);
            dimRange.setSheetId(Integer.valueOf(googleSheetInfo.get("sheetId")));
            dimRange.setDimension("COLUMNS");

            insertDimensionRequest.setRange(dimRange);
            insertDimensionRequest.setInheritFromBefore(false);
            requests.add( new Request().setInsertDimension(insertDimensionRequest));
            batchRequests = new BatchUpdateSpreadsheetRequest();
            batchRequests.setRequests( requests );
            response = getSheetsService().spreadsheets().batchUpdate(SPREADSHEET_ID, batchRequests).execute();
            System.out.println(response.toPrettyString());

            String startDate = getUnique("yyyy/MM/dd HH:mm:ss");
            setCellValue(googleSheetInfo.get("sheetName"), "F", 1, startDate);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String... args) throws IOException, GeneralSecurityException {
//        googleSheetInfo.put("sheetName", "UI-Report-Chrome");
//        googleSheetInfo.put("sheetId", "0");
//        insertColumnTestStatus();
//        updateTestCaseStatus("FE002-Tours - Verify Tours Filter", "2018/09/13 11:11:11", "00:00:15.234", "skipped");
//    }
}
// [END sheets_quickstart]
