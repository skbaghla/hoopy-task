package the26.blinders.hoopytaskbysanjeev.models; ;

import java.math.BigInteger;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetch {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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
    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("contact")
        @Expose
        private BigInteger contact;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public BigInteger getContact() {
            return contact;
        }

        public void setContact(BigInteger contact) {
            this.contact = contact;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }
}