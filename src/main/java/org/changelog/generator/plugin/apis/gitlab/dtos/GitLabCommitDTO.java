package org.changelog.generator.plugin.apis.gitlab.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class GitLabCommitDTO {
    @SerializedName("id")
    private String id;

    @SerializedName("short_id")
    private String shortId;

    @SerializedName("title")
    private String title;

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("author_email")
    private String authorEmail;

    @SerializedName("authored_date")
    private String authoredDate;

    @SerializedName("committer_name")
    private String commiterName;

    @SerializedName("committer_email")
    private String commiterEmail;

    @SerializedName("committed_date")
    private String commitedDate;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("message")
    private String message;

    @SerializedName("parent_ids")
    private List<String> parentIds;
}
