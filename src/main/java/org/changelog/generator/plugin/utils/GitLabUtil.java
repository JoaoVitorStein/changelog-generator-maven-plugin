package org.changelog.generator.plugin.utils;

import org.changelog.generator.plugin.apis.GitLabAPI;
import org.changelog.generator.plugin.apis.gitlab.dtos.GitLabTagDTO;
import org.changelog.generator.plugin.clients.GitLabClient;
import org.changelog.generator.plugin.daos.Tag;

import java.util.List;

public class GitLabUtil {

    private final GitLabAPI client = GitLabClient.getInstance();

    public Tag getLattestTag(){
        List<GitLabTagDTO> tags = client.getTags(Configuration.getInstance().getRepositoryId());
        GitLabTagDTO lattestTag = tags != null && !tags.isEmpty() ? tags.get(0): null;
        if (lattestTag != null) {
            Tag tag = new Tag();
            tag.setDate(lattestTag.getCommit().getCommitedDate());
            tag.setName(lattestTag.getName());
            return tag;
        }
        return  null;
    }


}
