package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * レスポンス用のユーザー情報を格納するDTO
 */
@Data
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String name;
  private String email;
}
