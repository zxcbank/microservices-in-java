package kkkombinator.Requests;

import kkkombinator.CatDTO;

public class CatRequest {
    String action;
    CatDTO dto;

    public CatRequest(String s, CatDTO dto) {
        this.action = s;
        this.dto = dto;
    }
}
