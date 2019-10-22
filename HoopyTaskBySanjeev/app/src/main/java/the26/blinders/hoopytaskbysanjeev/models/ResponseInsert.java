package the26.blinders.hoopytaskbysanjeev.models; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInsert {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}