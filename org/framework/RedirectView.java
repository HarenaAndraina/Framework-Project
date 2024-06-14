package org.framework.view;

public class RedirectView {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedirectView(String url) {
        this.url = url;
    }
}
