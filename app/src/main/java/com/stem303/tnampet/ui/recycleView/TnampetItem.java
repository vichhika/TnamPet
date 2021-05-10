package com.stem303.tnampet.ui.recycleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TnampetItem implements Serializable {

    private int id;
    private String title;
    private String intro_content;
    private String side_effect;
    private String side_effect_content;
    private String warning;
    private String warning_content;
    private String usage;
    private String usage_content;

    public TnampetItem(int id,String title,String intro_content,String side_effect,String side_effect_content,String warning,String warning_content,String usage,String usage_content) {
        this.id = id;
        this.title = title;
        this.intro_content = intro_content;
        this.side_effect = side_effect;
        this.side_effect_content = side_effect_content;
        this.warning = warning;
        this.warning_content = warning_content;
        this.usage = usage;
        this.usage_content = usage_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro_content() {
        return intro_content;
    }

    public void setIntro_content(String intro_content) {
        this.intro_content = intro_content;
    }

    public String getSide_effect() {
        return side_effect;
    }

    public void setSide_effect(String side_effect) {
        this.side_effect = side_effect;
    }

    public String getSide_effect_content() {
        return side_effect_content;
    }

    public void setSide_effect_content(String side_effect_content) {
        this.side_effect_content = side_effect_content;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getWarning_content() {
        return warning_content;
    }

    public void setWarning_content(String warning_content) {
        this.warning_content = warning_content;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage_content() {
        return usage_content;
    }

    public void setUsage_content(String usage_content) {
        this.usage_content = usage_content;
    }
}
