package com.community.web.resolver;

import com.community.web.annotation.SocialUser;
import com.community.web.domain.User;
import com.community.web.domain.enums.SocialType;
import com.community.web.domain.enums.UserType;
import com.community.web.dto.UserDto;
import com.community.web.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.community.web.domain.enums.SocialType.GOOGLE;
import static com.community.web.domain.enums.SocialType.KAKAO;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType().equals(UserDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        return getUser(userDto, session);
    }

    private UserDto getUser(UserDto userDto, HttpSession session){
        if(userDto==null){
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String,Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(),map);
                User user = userRepository.findByEmail(convertUser.getEmail());
                if(user==null){
                    user = userRepository.save(convertUser);
                }
                userDto = new UserDto(user);
                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user",userDto);
            } catch (ClassCastException e){
                return userDto;
            }
        }
        return userDto;
    }

    private User convertUser(String authority, Map<String, Object> map){
        if(GOOGLE.isEquals(authority)) return getModernUser(GOOGLE, map);
        else if(KAKAO.isEquals(authority)) return getKakaoUser(map);
        return null;
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map){
        return User.builder().name(String.valueOf(map.get("name"))).email(String.valueOf(map.get("email"))).principal(String.valueOf(map.get("id")))
                .userType(UserType.commonuser).socialType(socialType).build();
    }

    private User getKakaoUser(Map<String, Object> map){
        Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        Map<String, String> accountMap = (HashMap<String, String>) map.get("kakao_account");
        return User.builder().name(propertyMap.get("nickname")).email(String.valueOf(accountMap.get("email"))).principal(String.valueOf(map.get("id")))
                .userType(UserType.commonuser).socialType(KAKAO).build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map){
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
        }
    }
}
