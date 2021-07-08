package vn.co.vis.restful.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
