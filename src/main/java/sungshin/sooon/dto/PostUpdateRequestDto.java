package sungshin.sooon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private Boolean is_anonymous;
    private LocalDateTime created_at;
}
