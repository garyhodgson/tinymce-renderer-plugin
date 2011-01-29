package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;
import com.atlassian.jira.util.JiraKeyUtils;
import com.opensymphony.module.propertyset.PropertySet;

public class TinyMCERendererPlugin implements JiraRendererPlugin {

    public static final String TYPE = "tinymce-renderer";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private JiraRendererModuleDescriptor jiraRendererModuleDescriptor;
    private AtlassianWikiRenderer atlassianWikiRenderer;

    public JiraRendererModuleDescriptor getDescriptor() {
        return jiraRendererModuleDescriptor;
    }

    public String getRendererType() {
        return TYPE;
    }

    public void init(JiraRendererModuleDescriptor jiraRendererModuleDescriptor) {
        this.jiraRendererModuleDescriptor = jiraRendererModuleDescriptor;
        this.atlassianWikiRenderer = new AtlassianWikiRenderer();

    }

    public String render(String s, IssueRenderContext issueRenderContext) {
        PropertySet properties = PropertiesManager.getInstance().getPropertySet();
        boolean renderWikiText = properties.getBoolean(TinyMCERendererAdminAction.RENDER_WIKI_TEXT_PROPERTY);

        if (renderWikiText) {
            if (!s.startsWith("<")) {
                s = atlassianWikiRenderer.render(s, issueRenderContext);
            }
        }
        StringBuilder text = new StringBuilder();
        text.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        text.append(JiraKeyUtils.linkBugKeys(s));

        return text.toString();
    }

    public String renderAsText(String s, IssueRenderContext issueRenderContext) {
        return s;
    }

    public Object transformForEdit(Object obj) {
        String s = (String) obj;
        PropertySet properties = PropertiesManager.getInstance().getPropertySet();
        boolean renderWikiText = properties.getBoolean(TinyMCERendererAdminAction.RENDER_WIKI_TEXT_PROPERTY);

        if (renderWikiText) {
            if (!s.startsWith("<")) {
                s = atlassianWikiRenderer.render(s, null);
            }
        }

        return s;
    }

    public Object transformFromEdit(Object obj) {
        return obj;
    }
}
