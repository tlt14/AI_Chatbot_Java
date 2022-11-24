import GUI.ConfigURL;
import GUI.LoginForm;

import java.io.IOException;

public class client {
    public static void main(String[] args) throws IOException {
        new ConfigURL();
        new LoginForm(null);
    }
}
