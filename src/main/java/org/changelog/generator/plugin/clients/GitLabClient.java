package org.changelog.generator.plugin.clients;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.changelog.generator.plugin.apis.GitLabAPI;
import org.changelog.generator.plugin.interceptors.GitLabTokenInterceptor;
import org.changelog.generator.plugin.utils.Configuration;

public class GitLabClient {
    private static GitLabAPI instance;


    public static GitLabAPI getInstance() {
        if (instance == null){
            instance = Feign.builder()
                    .decoder(new GsonDecoder())
                    .requestInterceptor(new GitLabTokenInterceptor())
                    .target(GitLabAPI.class, Configuration.getInstance().getGitUrl());
        }
        return instance;
    }



}
