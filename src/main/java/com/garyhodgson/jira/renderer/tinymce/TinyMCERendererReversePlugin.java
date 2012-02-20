package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.core.util.HTMLUtils;
import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;
import com.opensymphony.module.propertyset.PropertyException;
import com.opensymphony.module.propertyset.PropertySet;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class TinyMCERendererReversePlugin implements JiraRendererPlugin {

    public static final String TYPE = "tinymce-renderer-reverse";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private JiraRendererModuleDescriptor jiraRendererModuleDescriptor;
    private PropertySet properties;
    private Whitelist whitelist;

    public TinyMCERendererReversePlugin() {
        this.properties = PropertiesManager.getInstance().getPropertySet();
        this.whitelist = Whitelist.none();
        this.whitelist.addTags("table", "thead", "tbody", "td", "tr", "img");
        this.whitelist.addAttributes("img", "src", "height", "width");
        this.whitelist.addAttributes("table", "cellpadding", "cellspacing", "border");
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
        
        if (s == null){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String[] lines = s.split("\n");
        for (String line : lines) {
            if (!line.trim().startsWith("<")) {
                sb.append("<br/>");
            }
            sb.append(line);
        }

        return sb.toString();
    }

    public String renderAsText(String s, IssueRenderContext issueRenderContext) {
        if (s == null){
            return "";
        }
        return HTMLUtils.stripTags(s);
    }

    public Object transformForEdit(Object obj) {
        if (obj == null) {
            return obj;
        }

        if (stripNoTags()) {
            return obj;
        }

        String s = (String) obj;
        s = s.replaceAll("&nbsp;", "tinymce-renderer-reverse_sp");

        if (stripAllTags()) {
            s = s.replaceAll("</tr>", "tinymce-renderer-reverse_br");
        }

        s = s.replaceAll("<p>&nbsp;</p>", "tinymce-renderer-reverse_br");
        s = s.replaceAll("</li>", "tinymce-renderer-reverse_br");
        s = s.replaceAll("<br />", "tinymce-renderer-reverse_br");
        s = s.replaceAll("</p>", "tinymce-renderer-reverse_br");
        s = s.replaceAll("</ul>", "tinymce-renderer-reverse_br");
        s = s.replaceAll("</ol>", "tinymce-renderer-reverse_br");
        s = s.replaceAll("</blockquote>", "tinymce-renderer-reverse_br");

        Whitelist wl = stripAllTags() ? Whitelist.none() : this.whitelist;

        String safe = Jsoup.clean(s, wl);
        safe = safe.replaceAll("tinymce-renderer-reverse_br", "\n");
        safe = safe.replaceAll("tinymce-renderer-reverse_sp", " ");

        return safe;

    }

    private boolean stripAllTags() throws PropertyException {
        return properties.getBoolean(TinyMCERendererAdminAction.STRIP_ALL_TAGS_PROPERTY);
    }

    private boolean stripSomeTags() throws PropertyException {
        return properties.getBoolean(TinyMCERendererAdminAction.STRIP_SOME_TAGS_PROPERTY);
    }

    private boolean stripNoTags() throws PropertyException {
        return properties.getBoolean(TinyMCERendererAdminAction.STRIP_NO_TAGS_PROPERTY);
    }

    public Object transformFromEdit(Object obj) {
        return obj;
    }
}