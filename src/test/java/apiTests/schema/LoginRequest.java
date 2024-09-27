package apiTests.schema;

import lombok.Value;

@Value
public class LoginRequest {
    String username;
    String password;
}
