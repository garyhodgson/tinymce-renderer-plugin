package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.core.util.HTMLUtils;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.issue.fields.renderer.wiki.AtlassianWikiRenderer;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;
import com.atlassian.jira.util.JiraKeyUtils;
import com.opensymphony.module.propertyset.PropertyException;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.util.TextUtils;

public class TinyMCERendererPlugin implements JiraRendererPlugin {

    public static final String TYPE = "tinymce-renderer";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private JiraRendererModuleDescriptor jiraRendererModuleDescriptor;
    private AtlassianWikiRenderer atlassianWikiRenderer;
    private PropertySet properties;

    public TinyMCERendererPlugin(EventPublisher eventPublisher) {
        this.atlassianWikiRenderer = new AtlassianWikiRenderer(eventPublisher);
        PropertiesManager propertiesManager = ComponentManager.getComponent(PropertiesManager.class);
        this.properties = propertiesManager.getPropertySet();
    }

    public void init(JiraRendererModuleDescriptor jiraRendererModuleDescriptor) {
        this.jiraRendererModuleDescriptor = jiraRendererModuleDescriptor;
    }

    public JiraRendererModuleDescriptor getDescriptor() {
        return jiraRendererModuleDescriptor;
    }

    public String getRendererType() {
        return TYPE;
    }

    public String render(String s, IssueRenderContext issueRenderContext) {

        if (s == null) {
            return "";
        }

        if (renderWikiText() && !s.startsWith("<")) {
            s = atlassianWikiRenderer.render(s, issueRenderContext);
        }
        s = transformLineBreaks(s);

        StringBuilder text = new StringBuilder();
        text.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        text.append(JiraKeyUtils.linkBugKeys(TextUtils.hyperlink(s)));

        return text.toString();
    }

    public String renderAsText(String s, IssueRenderContext issueRenderContext) {
        return JiraKeyUtils.linkBugKeys(TextUtils.hyperlink(HTMLUtils.stripTags(s)));
    }

    public Object transformForEdit(Object obj) {
        if (obj == null) {
            return obj;
        }

        String s = (String) obj;

        if (renderWikiText() && !s.startsWith("<")) {
            s = atlassianWikiRenderer.render(s, null);

        }
        s = transformLineBreaks(s);
        return s;
    }

    public Object transformFromEdit(Object obj) {
        return obj;
    }

    private boolean renderWikiText() throws PropertyException {
        return properties.getBoolean(TinyMCERendererAdminAction.RENDER_WIKI_TEXT_PROPERTY);
    }

    private String transformLineBreaks(String s) {
        if (!s.startsWith("<")) {
            s = s.replaceAll("\n", "<br>");
        }
        return s;
    }
}
