package xyz.pangosoft.encinalbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import xyz.pangosoft.encinalbackend.models.User;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
    @Autowired
    private IUserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User usuario = userService.singleUser(authentication.getName());
        Map<String, Object> info = new HashMap<>();
        info.put("info_adicional", "Hola que tal!: ".concat(authentication.getName()));


        info.put("idUsuario", usuario.getUserId().toString());
        info.put("primerNombre", usuario.getFirstName());
        info.put("segundoNombre", usuario.getMiddleName());
        info.put("apellido", usuario.getLastName());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}
