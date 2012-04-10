# tinymce-renderer-plugin

###IMPORTANT! A bug has recently been found in the plugin when used with Jira 5.0 which makes this plugin unusable.  This, together with the fact I am finding it increasingly hard to find time to provide the minimum support necessary means that this plugin is no longer supported.  Please do not use this for any version of Jira as any upgrade or system change may result in the plugin not working.

For existing users of the plugin who wish to update their Jira instance: I recommend first testing the plugin in the new version (excluding version 5), and if this fails to work either disable the plugin and allow your users to edit the html in the existing fields by hand, or use the Reverse Renderer to remove the html tags.

## Description

A Jira plugin that provides a wysiwyg textarea renderer using the [TinyMCE Editor](http://tinymce.moxiecode.com).

* [Homepage](http://garyhodgson.github.com/tinymce-renderer-plugin)
* [Atlassian Plugin Page](https://plugins.atlassian.com/plugin/details/30365)

## Installation and Configuration

* Copy the tinymce-renderer-plugin-xx.jar file to {JIRA_HOME}/plugins/installed-plugins
* Restart jira
* Ensure the html renderer option is enabled. As Administrator go to plugins, choose "Wiki Renderer Macros Plugin", and select "Enable" for the html option.
* Modify the renderer for the field you wish to use TinyMCE in. Example: As Administrator go to "Field Configurations", select "Configure", next to a text field select "Renderers", choose "TinyMCE Renderer"


## Limitations

Because this plugin scratches my itch only a few of the TinyMCE plugins and themes are available:

* The only theme used is advanced-default
* The following plugins are used:
    * contextmenu
    * fullscreen
    * inlinepopups
    * paste
    * preview
    * table
* The configuration options are modifiable but lie deep in the jar file structure: tinymce-renderer-plugin.jar\templates\plugins\renderers\tinymce\tinymce-renderer-edit.vm
* Buttons available:
    * bold, italic, underline, strikethrough
    * justifyleft, justifycenter, justifyright, justifyfull
    * bullist, numlist
    * outdent, indent, blockquote
    * undo, redo
    * link, unlink, image
    * formatselect, fontselect, fontsizeselect
    * forecolor, backcolor
    * tablecontrols
    * hr, removeformat, visualaid
    * pastetext, pasteword, selectall
    * sub, sup, charmap
    * fullscreen, preview, cleanup, code


## Support

This plugin is unsupported, and I am releasing it in case it is useful for people.
If the plugin is not working whatsoever I will try and find time to help resolve the issue.
I do not currently plan on implementing more themes or mce-plugins, but will help as much as I can if you want to extened the plugin for your environment. 

## License

[BSD](http://www.opensource.org/licenses/bsd-license.php)


## Modifications to the TinyMCE Library

* tiny_mce_popup.js - modified to set the tinymce object which seemed to be not set when called within jira.  Identified with comment: "Modified from the original by Gary Hodgson for Jira Plugin"
* themes/advanced/skins/default/dialog.css - prefixed all declarations with "tinymce_dialog"
* themes/advanced/skins/default/content.css - prefixed all declarations with "mceEditor"
* themes/advanced/*.htm - added explicit class attributes to body tags for theme css declarations (otherwise the jira css overrides the styles, for example: tabs and buttons) Perhaps there is a TinyMCE configuration item for this, but I couldn't find one that worked.
* plugins/table/*htm - as above.
