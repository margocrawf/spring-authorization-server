/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.keys.KeyManager;
import org.springframework.security.crypto.keys.StaticKeyGeneratingKeyManager;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import sample.ApplicationSettings;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {
	private ApplicationSettings applicationSettings;

	@Autowired
	public void setApplicationSettings(ApplicationSettings applicationSettings) {
		this.applicationSettings = applicationSettings;
	}

	// @formatter:off
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		List<RegisteredClient> registeredClients = applicationSettings.getClients().stream().map(
				clientConfig -> {
					return RegisteredClient
							.withId(UUID.randomUUID().toString())
							.clientId(clientConfig.getClientId())
							.clientSecret(clientConfig.getClientSecret())
							.clientAuthenticationMethod(new ClientAuthenticationMethod(clientConfig.getAuthenticationMethod()))
							.authorizationGrantTypes(authorizationGrantTypes -> {
								clientConfig.getGrantTypes()
										.forEach(it -> authorizationGrantTypes.add(new AuthorizationGrantType(it)));
							})
							.redirectUri(clientConfig.getRedirectUri())
							.scopes(scopes -> {
								clientConfig.getScopes()
										.forEach(scopes::add);
							})
							.build();
				}).collect(Collectors.toList());

		return new InMemoryRegisteredClientRepository(registeredClients);
	}
	// @formatter:on

	@Bean
	public KeyManager keyManager() {
		return new StaticKeyGeneratingKeyManager();
	}

	// @formatter:off
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user1")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	// @formatter:on
}
