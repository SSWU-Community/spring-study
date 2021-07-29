package sungshin.sooon.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PagingPostResponseDto {
    Integer currentPage;
    Integer currentSize;
    boolean hasNextPage;
    List<PostResponseDto> posts;
}
