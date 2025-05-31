package kkkombinator.Requests;

import kkkombinator.UserDTO;

public class UserRequest {
    private String action;
    private UserDTO dto;

    public UserRequest(String action, UserDTO dto) {
        this.action = action;
        this.dto = dto;
    }
}
