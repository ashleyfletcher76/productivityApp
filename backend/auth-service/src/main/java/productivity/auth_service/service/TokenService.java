package productivity.auth_service.service;

import productivity.auth_service.controller.AuthController.UserServiceResponse;

public interface TokenService {
  String generateToken(UserServiceResponse userServiceResponse);
}
