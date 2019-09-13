package org.changelog.generator.plugin.apis.gitlab.dtos;

import lombok.Data;

@Data
public class GitLabTagDTO {

    private String name;

    private String target;

    private String message;

    private GitLabCommitDTO commit;
}
