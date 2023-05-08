package com.elcheno.trues.model.dto;

import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.service.LineService;

import java.sql.SQLException;
import java.util.List;

public class LineDTO {

    private static LineDTO _newInstance;
    private static Line _line;
    private static LineService lineService = new LineService();

    private LineDTO(Line line) {
        _line = searchLine(line.getId());
    }
    private LineDTO(){
        try {
            List<Line> lineList = lineService.getAll();
            if(lineList != null){
                _line = lineList.get(0);
            }else{
                _line = null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Line searchLine(int id){
        Line result = null;
        try {
            result = lineService.getById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Line getLine(){
        if (_newInstance == null) {
            _newInstance = new LineDTO();
        }
        return _line;
    }

    public static Line getLine(Line line){
        _newInstance = new LineDTO(line);
        return _line;
    }

}
