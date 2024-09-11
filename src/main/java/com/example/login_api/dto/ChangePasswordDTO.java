package com.example.login_api.dto;

public record ChangePasswordDTO (String email, String password, String newPassword) { }
