package the26.blinders.hoopytaskbysanjeev.models; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUploadImage {

    @SerializedName("urls")
    @Expose
    private List<String> urls = null;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public class Metadata {

        @SerializedName("response_code")
        @Expose
        private Integer responseCode;
        @SerializedName("response_text")
        @Expose
        private String responseText;

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseText() {
            return responseText;
        }

        public void setResponseText(String responseText) {
            this.responseText = responseText;
        }

    }

}