package vn.edu.likelion.front_ice.common.constants;

public interface ApiEndpoints {

    String DOMAIN = "https://front-ice.up.railway.app";
    String DOMAIN_LOCAL_FE = "http://localhost:5173";
    String BASE_API_URL = "/api/v1";


    // Authenticate API
    String AUTH_API = BASE_API_URL + "/auth";
    String LOGIN = "/login";  // POST
    String LOGOUT = "/logout";  // POST
    String SIGN_UP = "/signup";  // POST
    String RESET_PASSWORD = "/reset-password";  // POST
    String AUTH_INFO = "/auth-info";  // GET
    String GOOGLE_LOGIN = "/google";  // POST
    String VERIFY_EMAIL = "/email-verification";  // POST
    String EMAIL_SIGNUP = "/email-signup";  // POST
    String SIGNUP_VERIFY = "/signup-verify";  // POST
    String FORGOT_PASSWORD = "/forgot-password";  // POST
    String REFRESH_TOKEN = "/refresh-token";  // POST
    String SEND_OTP = "/otp-sending";  // POST
    String VERIFY_FORGOT_PASSWORD_OTP = "/verify-forgot-password-otp"; //POST


    // Common API
    String ID  = "{id}";
    String IMAGES = "/images";
    String UPLOAD_CV = "/cv";
    String DOWNLOAD_CV = "/cv/{id}";

    // Profile API
    String PROFILE_API = "/profile";
    String GET_BY_ID = "/{id}"; // GET
    String UPDATE_INFO = "/{id}";  // PUT
    String UPLOAD_AVATAR = "/images";  // PUT
    String UPLOAD_BANNER = "/banner";  // PUT


    // Challenger API
    String CHALLENGER_API = BASE_API_URL + "/challenger";
    String FOLLOW = "/follow"; // POST
    String GET_FOLLOW = "/get-follow"; // GET

    // Recruiter API
    String RECRUITER_API = BASE_API_URL + "/recruiter";

    // ADMIN API
    String ADMIN_API = BASE_API_URL + "/admin";

    // ADMIN API
    String MANAGER = BASE_API_URL + "/manager";

    // Challenge API
    String CHALLENGE_API = BASE_API_URL + "/challenges";
    String DOWNLOAD_ASSETS = "/assets/{id}";
    String DOWNLOAD_FIGMA = "/figma/{id}";
    String SEARCH = "/search";

    // Solution API
    String SOLUTION_API = BASE_API_URL + "/solutions";

    // Mentor API
    String MENTOR_API = BASE_API_URL + "/mentor";

    // Manager API
    String MANAGER_API = BASE_API_URL + "/manager";
    String UPLOAD_ASSETS = "/assets";
    String UPLOAD_FIGMA = "/figma";
    String UPLOAD_DESKTOP_DESIGN = "/desktop";
    String UPLOAD_MOBILE_DESIGN = "/mobile";
    String UPLOAD_TABLET_DESIGN = "/tablet";



    // Notification API
    String NOTIFICATION_API = BASE_API_URL + "/notifications";




}
