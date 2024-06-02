package api;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static java.util.UUID.randomUUID;

public class TempMail {
    private OkHttpClient client = new OkHttpClient();
    private String baseURL = "https://privatix-temp-mail-v1.p.rapidapi.com/request/";
    private String host    = "privatix-temp-mail-v1.p.rapidapi.com";

    private String key;

    private String emailAddress;
    private String emailAddressHash;
    private String username;

    public TempMail(String tempMailAccessKey) throws NoSuchAlgorithmException {
        this.key = tempMailAccessKey;

        String domain = "@steveix.com"; // this.getDomains().get(0);
        this.username = randomUUID().toString();
        this.emailAddress = this.username + domain;

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(this.emailAddress.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        this.emailAddressHash = hashtext;
    }

    private Response request(String target) throws IOException {
        Request request = new Request.Builder()
            .url(this.baseURL + target)
            .get()
            .addHeader("X-RapidAPI-Key", this.key)
            .addHeader("X-RapidAPI-Host", this.host)
            .build();
        return this.client.newCall(request).execute();
    }

    public List<String> getDomains() {
        try {
            Response response = this.request("domains/");
            Gson gson = new Gson();
            return gson.fromJson(
                response.body().string(),
                new TypeToken<List<String>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getUsername() {
        return this.username;
    }

    public String waitAndGetConfirmationLink(int timeout) {
        try {
            Thread.sleep(timeout * 1000);
            Response response = this.request("mail/id/" + this.emailAddressHash);
            String rawJson = response.body().string();
            String confirmationLink = "http" + rawJson.split("http",2)[1].split("A hivatkoz")[0];
            return confirmationLink.substring(0, confirmationLink.length() - 4);
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "InterruptedException";
        }
    }
}
