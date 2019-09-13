package org.changelog.generator.plugin.utils;

import lombok.Data;
import org.changelog.generator.plugin.enums.GitClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class Configuration {
    private static Configuration instance;
    private  String repositoryId;
    private String gitUrl;
    private GitClient gitClient;
    private String changelogPath;
    private Boolean putVersionHeader;
    private String versionHeader;
    private String breakingChangeSectionTitle;
    private String featureSectionTitle;
    private String fixSectionTitle;
    private String privateToken;

    private Configuration(){

    }

    public static Configuration getInstance(){
        return instance;
    }

    public static void createInstance(String configPath){
        try (InputStream input = new FileInputStream(configPath)) {
            Configuration configuration = new Configuration();
            Properties prop = new Properties();
            prop.load(input);
            configuration.repositoryId = prop.getProperty("git.repository.id");
            configuration.gitUrl = prop.getProperty("git.url");
            configuration.gitClient = GitClient.valueOf(prop.getProperty("git.client"));
            configuration.changelogPath = prop.getProperty("changelog.path");
            configuration.putVersionHeader = Boolean.parseBoolean(prop.getProperty("changelog.layout.put-version.header"));
            configuration.versionHeader = prop.getProperty("changelog.layout.version-header");
            configuration.breakingChangeSectionTitle = prop.getProperty("changelog.layout.breaking-change-section-title");
            configuration.featureSectionTitle = prop.getProperty("changelog.layout.feature-change-section-title");
            configuration.fixSectionTitle = prop.getProperty("changelog.layout.fix-change-section-title");
            configuration.privateToken = prop.getProperty("git.private-token");
            instance = configuration;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
