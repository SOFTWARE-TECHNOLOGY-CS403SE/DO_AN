package org.example.advancedrealestate_be.controller.api.auth;
import java.text.ParseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.dto.response.ApiResponse;
import org.example.advancedrealestate_be.service.AuthenticationService;
import org.example.advancedrealestate_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.advancedrealestate_be.dto.request.*;
import org.example.advancedrealestate_be.dto.response.AuthenticationResponse;
import org.example.advancedrealestate_be.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Auth API", description = "Auth API")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;

    @PostMapping("/token")
    ApiResponse<Object> authenticate(@RequestBody AuthenticationRequest request) {
        // Lấy thông tin người dùng dựa trên email
        var userInfo = userService.getMyInfo(request.getEmail());

        // Kiểm tra nếu `status == 0`, trả về thông báo lỗi
        if (userInfo.getStatus() == 0) {
            return ApiResponse.builder()
                    .message("Bạn không có quyền truy cập")
                    .code(404)
                    .build();
        }

        // Nếu `status != 0`, tiếp tục xử lý đăng nhập
        var result = authenticationService.authenticate(request);
        JSONObject responseObject = new JSONObject();
        responseObject.put("infoUser", userInfo);
        responseObject.put("login", result);

        return ApiResponse.builder()
                .result(responseObject)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
