package vn.co.vis.restful.exception;


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
public class TokenInvalidException extends RuntimeException {
    private String code;

    public TokenInvalidException(String code, String message) {
        super(message);
        this.code = code;
    }
}
