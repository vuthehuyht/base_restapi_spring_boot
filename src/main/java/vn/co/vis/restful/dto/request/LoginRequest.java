package vn.co.vis.restful.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "userName{validate.required}")
    @Size(max = 30, message = "userName{validate.over.length}")
    private String userName;
    @NotEmpty(message = "password{validate.required}")
    @Size(max = 30, message = "userName{validate.over.length}")
    private String password;

}
