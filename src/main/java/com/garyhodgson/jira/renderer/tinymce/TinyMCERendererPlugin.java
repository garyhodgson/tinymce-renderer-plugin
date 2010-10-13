package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;
import com.atlassian.jira.util.JiraKeyUtils;

public class TinyMCERendererPlugin implements JiraRendererPlugin {

    public static final String TYPE = "tinymce-renderer";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private JiraRendererModuleDescriptor jiraRendererModuleDescriptor;

    public JiraRendererModuleDescriptor getDescriptor() {
        return jiraRendererModuleDescriptor;
    }

    public String getRendererType() {
        return TYPE;
    }

    public void init(JiraRendererModuleDescriptor jiraRendererModuleDescriptor) {
        this.jiraRendererModuleDescriptor = jiraRendererModuleDescriptor;
    }

    public String render(String s, IssueRenderContext issueRenderContext) {
        StringBuilder text = new StringBuilder();
        text.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        text.append(JiraKeyUtils.linkBugKeys(s));

        return text.toString();
    }

    public String renderAsText(String s, IssueRenderContext issueRenderContext) {
        return s;
    }

    public Object transformForEdit(Object obj) {
        return obj;
    }

    public Object transformFromEdit(Object obj) {
        return obj;
    }
}
