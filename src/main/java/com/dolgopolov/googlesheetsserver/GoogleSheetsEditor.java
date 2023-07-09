package com.dolgopolov.googlesheetsserver;

import com.dolgopolov.googlesheetsserver.services.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class GoogleSheetsEditor {
    private static Sheets sheetsService;
    private String SPREADSHEET_ID;

    @Autowired public GoogleSheetsEditor(){
        SPREADSHEET_ID="1_ElnqYq5S5VNjsyPJFsVQLxxqOCDW-D34WqiXZba9Jk";
    }
    public GoogleSheetsEditor setSpreadsheetId(String id){
        SPREADSHEET_ID=id;
        return this;
    }

    @PostConstruct
    public void setup() throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
    }
    public ValueRange getRange(String range) throws IOException {
        return sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID,  range)
                .setRange(range)
                .execute();
    }
    public String getCell(String cell) throws IOException {
        return (String) getRange(cell).getValues().get(0).get(0);
    }
    public boolean checkConnection(String sheet) {
        try{
            sheetsService.spreadsheets().values()
                    .get(sheet,  "A1")
                    .setRange("A1")
                    .execute();
            return true;
        }catch(IOException io){
            return false;
        }

    }
}
