package testdata;

import models.User;

public class TestUsers {
    public static final User STANDARD_USER = new User("tram_test_01", "123456789");
    public static final User ADMIN_USER = new User("1", "admin");

    private TestUsers() {
    }
}
