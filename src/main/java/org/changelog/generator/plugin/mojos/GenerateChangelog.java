package org.changelog.generator.plugin.mojos;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.changelog.generator.plugin.apis.gitlab.dtos.GitLabCommitDTO;
import org.changelog.generator.plugin.clients.GitLabClient;
import org.changelog.generator.plugin.daos.Commit;
import org.changelog.generator.plugin.daos.Tag;
import org.changelog.generator.plugin.enums.CommitType;
import org.changelog.generator.plugin.utils.ChangelogUtil;
import org.changelog.generator.plugin.utils.CommitConverter;
import org.changelog.generator.plugin.utils.Configuration;
import org.changelog.generator.plugin.utils.GitLabUtil;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Mojo(name = "generate-changelog")
public class GenerateChangelog extends AbstractMojo {

    private final CommitConverter commitConverter = new CommitConverter();

    @Parameter(property = "config-file-path")
    private String configFilePath;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Configuration.createInstance(configFilePath);
        generateChangelogForGitLab();
    }

    public void generateChangelogForGitLab(){
        GitLabUtil gitLabUtil = new GitLabUtil();
        getLog().info("Getting lattest tag from gitlab");
        Tag tag = gitLabUtil.getLattestTag();
        String commitsSince = this.commitsSinceWhen(tag);
        getLog().info(String.format("Requesting commits to gitlab since %s", commitsSince));
        List<GitLabCommitDTO> gitLabCommits= GitLabClient.getInstance().getCommits(Configuration.getInstance().getRepositoryId(), commitsSince, "master");
        getLog().info(String.format("Found %s commits in the repository", gitLabCommits.size()));
        List<Commit> commits = commitConverter.toCommits(gitLabCommits);
        Map<CommitType, List<Commit>> commitsByType = commitConverter.getCommitsByType(commits);
        try {
            new ChangelogUtil().generateChangeLog(commitsByType);
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }
    }


    private String commitsSinceWhen(Tag tag) {
        return tag != null ? tag.getDate() : Instant.EPOCH.plusSeconds(1).toString();
    }
}
