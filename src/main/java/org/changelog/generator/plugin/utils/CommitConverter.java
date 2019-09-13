package org.changelog.generator.plugin.utils;

import org.changelog.generator.plugin.apis.gitlab.dtos.GitLabCommitDTO;
import org.changelog.generator.plugin.daos.Commit;
import org.changelog.generator.plugin.enums.CommitType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommitConverter {

    public List<Commit> toCommits(List<GitLabCommitDTO> commits) {
        return commits.stream().map(gitLabCommit -> {
            Commit commit = new Commit();
            try{
                commit.setId(gitLabCommit.getId());
                commit.setMessage(gitLabCommit.getMessage());
                commit.setTitle(gitLabCommit.getTitle());
                commit.setCommitType(this.getCommitType(gitLabCommit.getMessage()));
                commit.setScope(this.getCommitScope(gitLabCommit.getMessage()));
                return commit;
            }
            catch (Exception e){
                return null;
            }

        }).collect(Collectors.toList());
    }

    public Map<CommitType, List<Commit>> getCommitsByType(List<Commit> commits) {
        Map<CommitType, List<Commit>> commitByType = new HashMap<>();
        commitByType.put(CommitType.FIX, commits.stream().filter(x -> x.getCommitType() == CommitType.FIX).collect(Collectors.toList()));
        commitByType.put(CommitType.FEAT, commits.stream().filter(x -> x.getCommitType() == CommitType.FEAT).collect(Collectors.toList()));
        commitByType.put(CommitType.BREAKING_CHANGE, commits.stream().filter(x -> x.getCommitType() == CommitType.BREAKING_CHANGE).collect(Collectors.toList()));
        return commitByType;
    }


    private CommitType getCommitType(String commitMessage) {
        if (commitMessage.toLowerCase().startsWith("fix")) {
            return CommitType.FIX;
        } else if (commitMessage.toLowerCase().startsWith("feat")) {
            return CommitType.FEAT;
        } else if (commitMessage.toLowerCase().startsWith("breaking change")) {
            return CommitType.BREAKING_CHANGE;
        }
        return null;
    }

    private String getCommitScope(String commitMessage) {
        String regex = "^fix([^\\)]+)\\):|feat([^\\)]+)\\):|breaking change([^\\)]+)\\):";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(commitMessage);
        if (m.find()) {
            int firtParentheses = commitMessage.indexOf("(");
            int secondParentheses = commitMessage.indexOf(")") + 1;
            return commitMessage.substring(firtParentheses, secondParentheses);
        }
        return null;
    }
}
