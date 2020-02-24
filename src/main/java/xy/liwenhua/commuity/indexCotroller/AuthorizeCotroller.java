package xy.liwenhua.commuity.indexCotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xy.liwenhua.commuity.dto.AccesstokenDTO;
import xy.liwenhua.commuity.dto.GithubUser;
import xy.liwenhua.commuity.provider.GithubProvider;

import java.io.IOException;

@Controller
public class AuthorizeCotroller {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state) throws IOException {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setClient_id("ee2e2f529996d02d4d9b");
        accesstokenDTO.setClient_secret("61101ed4d9eede2a3d764ffab2c392f4a8e36dde");
        accesstokenDTO.setState(state);
        accesstokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String accessToken=githubProvider.getAccessToken(accesstokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getId());
        return "index";
    }
}
