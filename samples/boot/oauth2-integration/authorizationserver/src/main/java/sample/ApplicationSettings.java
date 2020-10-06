package sample;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "app")
public class ApplicationSettings {
	private List<Client> clients;

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public static class Client {
		private String clientId;
		private String clientSecret;
		private String authenticationMethod;
		private List<String> grantTypes;
		private String redirectUri;
		private List<String> scopes;

		public String getClientId() {
			return clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String getAuthenticationMethod() {
			return authenticationMethod;
		}

		public void setAuthenticationMethod(String authenticationMethod) {
			this.authenticationMethod = authenticationMethod;
		}

		public List<String> getGrantTypes() {
			return grantTypes;
		}

		public void setGrantTypes(List<String> grantTypes) {
			this.grantTypes = grantTypes;
		}

		public String getRedirectUri() {
			return redirectUri;
		}

		public void setRedirectUri(String redirectUri) {
			this.redirectUri = redirectUri;
		}

		public List<String> getScopes() {
			return scopes;
		}

		public void setScopes(List<String> scopes) {
			this.scopes = scopes;
		}
	}
}
