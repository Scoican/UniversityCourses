package ObjectProtocol;

import DTOPackage.UserDTO;

public class LoginRequest implements Request {
    UserDTO user;
    public LoginRequest(UserDTO user){
        this.user=user;
    }
    public UserDTO getUser(){return user;}
}

