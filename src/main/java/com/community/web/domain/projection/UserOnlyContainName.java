package com.community.web.domain.projection;

import com.community.web.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="getOnlyName", types = {User.class})
public interface UserOnlyContainName {
    String getName();
}
