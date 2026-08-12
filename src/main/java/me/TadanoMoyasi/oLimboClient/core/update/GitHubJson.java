package me.TadanoMoyasi.oLimboClient.core.update;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GitHubJson {
	@SerializedName("tag_name")
    public String tagName;

    @SerializedName("html_url")
    public String htmlUrl;

    @SerializedName("assets")
    public List<Asset> assets;

    public static class Asset {
        @SerializedName("name")
        public String name;

        @SerializedName("browser_download_url")
        public String browserDownloadUrl;
    }
}
