package com.ddokddak.auth.service;

import com.ddokddak.member.entity.Oauth2Member;
import com.ddokddak.member.entity.enums.AuthProviderType;
import com.ddokddak.member.mapper.Oauth2MemberMapper;
import com.ddokddak.member.repository.Oauth2MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final Oauth2MemberRepository oauth2MemberRepository;

//           JdbcOAuth2AuthorizedClientService;
//        InMemoryOAuth2AuthorizedClientService;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {


        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");

        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        Oauth2Member oauth2Member = registration == null ? null : this.getOauth2Member(principalName, clientRegistrationId);
        var oauth2AuthorizedClient = Oauth2MemberMapper.toOauth2AuthorizedClient(oauth2Member, registration);

        return (T) oauth2AuthorizedClient;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");

        var clientRegistrationId = authorizedClient.getClientRegistration() == null ? null : authorizedClient.getClientRegistration().getRegistrationId();
        var principalName = principal.getName();

        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");

        var oauth2Member = this.getOauth2Member(principalName, clientRegistrationId);
        boolean existsOauth2Member = null != oauth2Member;


        if (existsOauth2Member) {
            this.updateOauth2Member(oauth2Member, authorizedClient, principal);
        }
        else {
            try {
                oauth2Member = Oauth2MemberMapper.fromAuthorizedClientAndPrincipal(authorizedClient, principal);
                this.insertOauth2Member(oauth2Member);
            }
            catch (DuplicateKeyException ex) {
                this.updateOauth2Member(oauth2Member, authorizedClient, principal);
            }
        }
    }

    @Transactional
    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        var oauth2Member = this.getOauth2Member(clientRegistrationId, principalName);
        oauth2Member.modifyForDeletingAuthentication();
    }

    private Oauth2Member getOauth2Member(String principalName, String clientRegistrationId) {

        return this.oauth2MemberRepository
                .findByOauth2IdAndAuthProviderAndIsDeletedFalse(principalName, AuthProviderType.getByCode(clientRegistrationId))
                .orElse(null);
    }

    @Transactional
    public void insertOauth2Member(Oauth2Member oauth2Member) {
        oauth2MemberRepository.save(oauth2Member);
    }

    @Transactional
    public void updateOauth2Member(Oauth2Member oauth2Member, OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        oauth2Member.modifyByOAuth2AuthorizedClientAndAuthentication(authorizedClient, principal);
    }
}
