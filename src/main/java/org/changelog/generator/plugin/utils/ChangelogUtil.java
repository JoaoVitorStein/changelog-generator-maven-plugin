package org.changelog.generator.plugin.utils;

import org.changelog.generator.plugin.daos.Commit;
import org.changelog.generator.plugin.enums.CommitType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChangelogUtil {

    private final String path;

    public ChangelogUtil() {
        this.path = Configuration.getInstance().getChangelogPath();
    }

    public void generateChangeLog(Map<CommitType, List<Commit>> commitsByType) throws IOException {
        String currentData = this.getCurrentFileData();

        String versionHeader = this.createVersionHeader();
        String fixSectionData = this.createFixSection(commitsByType.get(CommitType.FIX));
        String featSectionData = this.createFeatSection(commitsByType.get(CommitType.FEAT));
        String breakingChangeSectionData = this.createBreakingChangeSection(commitsByType.get(CommitType.BREAKING_CHANGE));
        FileUtils.writeToTextFile(path, versionHeader + featSectionData+ breakingChangeSectionData + fixSectionData + currentData);
    }

    private String getCurrentFileData() throws IOException {
        return FileUtils.readTextFile(path);
    }

    private String createFixSection(List<Commit> fixCommits) {
        StringBuilder sb = new StringBuilder(Configuration.getInstance().getFixSectionTitle());
        fixCommits.forEach(commit -> {
            String commitBeginsWith = commit.getScope() == null ? "fix:" : "fix" + commit.getScope() + ":";
            String commitMessage = commit.getTitle().toLowerCase().substring(commitBeginsWith.length());
            sb.append("*");
            sb.append(" ");
            sb.append(commitMessage);
            sb.append("\n");
        });
        sb.append("\n");
        return sb.toString();
    }
    private String createVersionHeader(){
        if (Configuration.getInstance().getPutVersionHeader()){
            return Configuration.getInstance().getVersionHeader();
        }
        return "\n";
    }


    private String createFeatSection(List<Commit> fixCommits) {
        StringBuilder sb = new StringBuilder(Configuration.getInstance().getFeatureSectionTitle());
        fixCommits.forEach(commit -> {
            String commitBeginsWith = commit.getScope() == null ? "feat:" : "feat" + commit.getScope() + ":";
            String commitMessage = commit.getTitle().toLowerCase().substring(commitBeginsWith.length());
            sb.append("*");
            sb.append(" ");
            sb.append(commitMessage);
            sb.append("\n");
        });
        sb.append("\n");
        return sb.toString();
    }

    private String createBreakingChangeSection(List<Commit> fixCommits) {
        StringBuilder sb = new StringBuilder(Configuration.getInstance().getBreakingChangeSectionTitle());
        fixCommits.forEach(commit -> {
            String commitBeginsWith = commit.getScope() == null ? "breaking change:" : "breaking change" + commit.getScope() + ":";
            String commitMessage = commit.getTitle().toLowerCase().substring(commitBeginsWith.length());
            sb.append("*");
            sb.append(" ");
            sb.append(commitMessage);
            sb.append("\n");
        });
        sb.append("\n");
        return sb.toString();
    }
}
