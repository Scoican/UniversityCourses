package ObjectProtocol;

import DTOPackage.UserDTO;

public class LogoutRequest implements Request{
    UserDTO user;
    public LogoutRequest(UserDTO user){
        this.user = user;
    }
    public UserDTO getUser(){return user;}
}
