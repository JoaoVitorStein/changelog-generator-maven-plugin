package org.changelog.generator.plugin.apis;

import feign.Param;
import feign.RequestLine;
import org.changelog.generator.plugin.apis.gitlab.dtos.GitLabCommitDTO;
import org.changelog.generator.plugin.apis.gitlab.dtos.GitLabTagDTO;

import java.util.ArrayList;

public interface GitLabAPI {
    @RequestLine("GET /api/v4/projects/{id}/repository/commits?since={since}&ref_name={ref_name}")
    ArrayList<GitLabCommitDTO> getCommits(@Param("id") String id, @Param("since") String since, @Param("ref_name") String branch);

    @RequestLine("GET /api/v4/projects/{id}/repository/tags")
    ArrayList<GitLabTagDTO> getTags(@Param("id") String id);

}
