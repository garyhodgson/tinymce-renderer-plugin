package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.opensymphony.module.propertyset.PropertySet;
import java.util.Map;
import webwork.action.ActionContext;

public class TinyMCERendererAdminAction extends JiraWebActionSupport {

    public static final String RENDER_WIKI_TEXT_PROPERTY = "com.garyhodgson.jira.tinymce-renderer-plugin.renderWikiText";
    public static final String STRIP_ALL_TAGS_PROPERTY = "com.garyhodgson.jira.tinymce-renderer-plugin.stripAllTags";
    public static final String STRIP_SOME_TAGS_PROPERTY = "com.garyhodgson.jira.tinymce-renderer-plugin.stripSomeTags";
    public static final String STRIP_NO_TAGS_PROPERTY = "com.garyhodgson.jira.tinymce-renderer-plugin.stripNoTags";
    private PropertySet properties;

    public TinyMCERendererAdminAction() {
        properties = PropertiesManager.getInstance().getPropertySet();
    }

    @RequiresXsrfCheck
    @Override
    public String doDefault() {
        return SUCCESS;
    }

    @RequiresXsrfCheck
    public String doSaveConfiguration() {
        final Map actionParams = ActionContext.getParameters();
        boolean isRenderWikiText = false;
        boolean isStripAllTags = false;
        boolean isStripSomeTags = false;
        boolean isStripNoTags = false;

        if (actionParams.containsKey("renderWikiText")) {
            String[] values = (String[]) actionParams.get("renderWikiText");

            if (values[0] != null && values[0].equals("on")) {
                isRenderWikiText = true;
            }
        }
        properties.setBoolean(RENDER_WIKI_TEXT_PROPERTY, isRenderWikiText);

        if (actionParams.containsKey("stripTags")) {
            String[] values = (String[]) actionParams.get("stripTags");

            if (values[0] != null) {
                isStripAllTags = values[0].equals("All");
                isStripSomeTags = values[0].equals("Some");
                isStripNoTags = values[0].equals("None");
            }
        }
        properties.setBoolean(STRIP_ALL_TAGS_PROPERTY, isStripAllTags);
        properties.setBoolean(STRIP_SOME_TAGS_PROPERTY, isStripSomeTags);
        properties.setBoolean(STRIP_NO_TAGS_PROPERTY, isStripNoTags);

        return SUCCESS;
    }

    public boolean getRenderWikiText() {
        return properties.getBoolean(RENDER_WIKI_TEXT_PROPERTY);
    }

    public boolean getStripAllTags() {
        return properties.getBoolean(STRIP_ALL_TAGS_PROPERTY);
    }

    public boolean getStripSomeTags() {
        return properties.getBoolean(STRIP_SOME_TAGS_PROPERTY);
    }

    public boolean getStripNoTags() {
        return properties.getBoolean(STRIP_NO_TAGS_PROPERTY);
    }
}
