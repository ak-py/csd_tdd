import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class AuthenticationServiceTest {
    private static final String EMAIL = "contact@akgupta.tech";
    private static final String PASSWORD = "abcd1234";
    private static final User USER = new User("Akshit", /* age= */ 24);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private UserDao mockUserDao;
    private AuthenticationService authenticationService;

    @Before
    public void setUp() {
        authenticationService = new AuthenticationService(mockUserDao);
    }

    @After
    public void cleanUp() {
        mockUserDao = null;
        authenticationService = null;
    }

    @Test
    public void getUser_ValidCredentials() throws DatabaseDownException {
        when(mockUserDao.getUserOrNull(EMAIL, PASSWORD)).thenReturn(USER);

        Status<Boolean> userExistsStatus = authenticationService.checkUser(EMAIL, PASSWORD);

        verify(mockUserDao).getUserOrNull(EMAIL, PASSWORD);
        assert userExistsStatus.getData();
    }

    @Test
    public void authorization_ValidAuthorizedCredentials() throws DatabaseDownException {
        when(mockUserDao.getKey(EMAIL, PASSWORD)).thenReturn(EMAIL + PASSWORD);

        Status<AuthorizationResponse> userExistsStatus = authenticationService.authorization(EMAIL, PASSWORD);

        verify(mockUserDao).getKey(EMAIL, PASSWORD);
        assert userExistsStatus.getData().isAuthorized();
        assert userExistsStatus.getData().getMessage().equals("Authorized");
    }

    @Test
    public void authorization_InvalidCredentials() throws DatabaseDownException {
        when(mockUserDao.getKey(EMAIL, PASSWORD)).thenReturn("some completely other key");

        Status<AuthorizationResponse> userExistsStatus = authenticationService.authorization(EMAIL, PASSWORD);

        verify(mockUserDao).getKey(EMAIL, PASSWORD);
        assert !userExistsStatus.getData().isAuthorized();
        assert userExistsStatus.getData().getMessage().equals("Keys did not match");
    }

    @Test
    public void authorization_EmptyCredentials() {
        Status<AuthorizationResponse> userExistsStatus = authenticationService.authorization("", "");

        assert !userExistsStatus.getData().isAuthorized();
        assert userExistsStatus.getData().getMessage().equals("Please provide non-empty email and password");
    }

    @Test
    public void getUser_InvalidEmail() throws DatabaseDownException {
        String invalidEmail = "invalid@email.com";
        when(mockUserDao.getUserOrNull(invalidEmail, PASSWORD)).thenReturn(null);

        Status<Boolean> userExistsStatus = authenticationService.checkUser(invalidEmail, PASSWORD);

        verify(mockUserDao).getUserOrNull(invalidEmail, PASSWORD);
        assert !userExistsStatus.getData();
    }

    @Test
    public void getUser_EmptyInputs() throws DatabaseDownException {
        String email = "";
        String password = "";
        when(mockUserDao.getUserOrNull(email, password)).thenReturn(null);

        Status<Boolean> userExistsStatus = authenticationService.checkUser(email, password);

        verify(mockUserDao).getUserOrNull(email, password);
        assert !userExistsStatus.getData();
    }

    @Test
    public void getUser_CheckErrorMessage() throws Exception {
        String expectedMessage = "Database down";
        int databaseDownCode = 500;
        when(mockUserDao.getUserOrNull(EMAIL, PASSWORD)).thenThrow(new DatabaseDownException());

        Status<Boolean> userExistsStatus = authenticationService.checkUser(EMAIL, PASSWORD);

        assert userExistsStatus.getError().getMessage().equals(expectedMessage);
        assert userExistsStatus.getError().getCode() == databaseDownCode;
    }
}
