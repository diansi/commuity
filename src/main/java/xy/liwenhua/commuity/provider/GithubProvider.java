package xy.liwenhua.commuity.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import xy.liwenhua.commuity.dto.AccesstokenDTO;
import xy.liwenhua.commuity.dto.GithubUser;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body=RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String token=string.split("\\&")[0].split("\\=")[0];
            return token;
        } catch (Exception e) {
            System.out.println(e);

        }
        return null;

    }

    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?accessToken="+accessToken)
                .build();
        Response response = client.newCall(request).execute();
        String string=response.body().string();
        GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
        return githubUser;
    }






}
