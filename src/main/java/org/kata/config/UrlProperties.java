package org.kata.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("url-properties")
@Getter
@Setter
public class UrlProperties {

    private String profileLoaderBaseUrl;

    private String profileLoaderGetIndividual;
    private String profileLoaderGetDocuments;
    private String profileLoaderGetContactMedium;
    private String profileLoaderGetAvatar;
    private String profileLoaderGetAddress;
    private String profileLoaderGetWalletByMobileAndCurrency;
    private String profileLoaderPatchWallet;

    private String profileLoaderPostIndividual;
    private String profileLoaderPostDocuments;
    private String profileLoaderPostContactMedium;
    private String profileLoaderPostAvatar;
    private String profileLoaderPostAddress;
}
