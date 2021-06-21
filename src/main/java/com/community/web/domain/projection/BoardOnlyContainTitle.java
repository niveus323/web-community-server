package com.community.web.domain.projection;

import com.community.web.domain.Board;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="getOnlyTitle", types = {Board.class})
public interface BoardOnlyContainTitle {
    String getTitle();
}
