package vn.co.vis.restful.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String token;
}
