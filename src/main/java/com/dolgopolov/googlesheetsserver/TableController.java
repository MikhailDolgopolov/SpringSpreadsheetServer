package com.dolgopolov.googlesheetsserver;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("table")
public class TableController {
    private final GoogleSheetsEditor reader;

    public TableController(GoogleSheetsEditor reader) {
        this.reader = reader;
    }

    @PostConstruct
    public void test(){
        if(reader!=null)
            System.out.println("InjectionCheck successful");
    }
    @GetMapping(value = "{id}/{sheet}/range/{range}", produces = "application/json")
    public List<List<Object>> getRange(@PathVariable String id, @PathVariable String sheet, @PathVariable String range) throws IOException {
        return reader.setSpreadsheetId(id).getRange(sheet+"!"+range).getValues();
    }

    @GetMapping(value = "{id}/cell/{cell}", produces = "application/json")
    public String getValue(@PathVariable String id, @PathVariable String cell) throws IOException {
        return reader.setSpreadsheetId(id).getCell(cell);
    }
    @GetMapping(value = "/", produces = "application/json")
    public String connect(){
        return "Ok";
    }


}
