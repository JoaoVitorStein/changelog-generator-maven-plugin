package org.changelog.generator.plugin.daos;

import lombok.Data;
import org.changelog.generator.plugin.enums.CommitType;

@Data
public class Commit {

    private String id;

    private String message;

    private String title;

    private CommitType commitType;

    private String scope;
}
