package com.garyhodgson.jira.renderer.tinymce;

import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.opensymphony.module.propertyset.PropertySet;
import java.util.Map;
import webwork.action.ActionContext;

public class TinyMCERendererAdminAction extends JiraWebActionSupport {

    public static final String RENDER_WIKI_TEXT_PROPERTY = "com.garyhodgson.jira.tinymce-renderer-plugin.renderWikiText";
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

        if (actionParams.containsKey("renderWikiText")) {
            String[] values = (String[]) actionParams.get("renderWikiText");

            if (values[0] != null && values[0].equals("on")) {
                isRenderWikiText = true;
            }
        }
        properties.setBoolean(RENDER_WIKI_TEXT_PROPERTY, isRenderWikiText);

        return SUCCESS;
    }

    public boolean getRenderWikiText() {
        return properties.getBoolean(RENDER_WIKI_TEXT_PROPERTY);
    }
}
