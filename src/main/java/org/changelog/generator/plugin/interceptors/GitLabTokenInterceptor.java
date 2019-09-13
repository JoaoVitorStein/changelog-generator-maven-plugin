package org.changelog.generator.plugin.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.changelog.generator.plugin.utils.Configuration;

public class GitLabTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("PRIVATE-TOKEN", Configuration.getInstance().getPrivateToken());
    }
}
