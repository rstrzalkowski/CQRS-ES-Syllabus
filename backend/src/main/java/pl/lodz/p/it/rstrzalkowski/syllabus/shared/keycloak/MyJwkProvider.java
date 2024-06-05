package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.SigningKeyNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyJwkProvider implements JwkProvider {
    final URL url;
    private final Integer connectTimeout;
    private final Integer readTimeout;
    private final ObjectReader reader;

    public MyJwkProvider(URL url) {
        this(url, (Integer) null, (Integer) null);
    }

    public MyJwkProvider(URL url, Integer connectTimeout, Integer readTimeout) {
        Preconditions.checkArgument(url != null, "A non-null url is required");
        Preconditions.checkArgument(connectTimeout == null || connectTimeout >= 0, "Invalid connect timeout value '" + connectTimeout + "'. Must be a non-negative integer.");
        Preconditions.checkArgument(readTimeout == null || readTimeout >= 0, "Invalid read timeout value '" + readTimeout + "'. Must be a non-negative integer.");
        this.url = url;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.reader = (new ObjectMapper()).readerFor(Map.class);
    }

    public MyJwkProvider(String domain) {
        this(urlForDomain(domain));
    }

    static URL urlForDomain(String domain) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(domain), "A domain is required");
        if (!domain.startsWith("http")) {
            domain = "https://" + domain;
        }

        try {
            URI uri = (new URI(domain)).normalize();
            return uri.toURL();
        } catch (URISyntaxException | MalformedURLException var2) {
            throw new IllegalArgumentException("Invalid jwks uri", var2);
        }
    }

    private Map<String, Object> getJwks() throws SigningKeyNotFoundException {
        try {
            URLConnection c = this.url.openConnection();
            if (this.connectTimeout != null) {
                c.setConnectTimeout(this.connectTimeout);
            }

            if (this.readTimeout != null) {
                c.setReadTimeout(this.readTimeout);
            }

            c.setRequestProperty("Accept", "application/json");
            InputStream inputStream = c.getInputStream();
            Throwable var3 = null;

            Map var4;
            try {
                var4 = (Map) this.reader.readValue(inputStream);
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (inputStream != null) {
                    if (var3 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }

            return var4;
        } catch (IOException var16) {
            throw new SigningKeyNotFoundException("Cannot obtain jwks from url " + this.url.toString(), var16);
        }
    }

    public List<Jwk> getAll() throws SigningKeyNotFoundException {
        List<Jwk> jwks = Lists.newArrayList();
        List<Map<String, Object>> keys = (List) this.getJwks().get("keys");
        if (keys != null && !keys.isEmpty()) {
            try {
                Iterator var3 = keys.iterator();

                while (var3.hasNext()) {
                    Map<String, Object> values = (Map) var3.next();
                    jwks.add(Jwk.fromValues(values));
                }

                return jwks;
            } catch (IllegalArgumentException var5) {
                throw new SigningKeyNotFoundException("Failed to parse jwk from json", var5);
            }
        } else {
            throw new SigningKeyNotFoundException("No keys found in " + this.url.toString(), (Throwable) null);
        }
    }

    public Jwk get(String keyId) throws JwkException {
        List<Jwk> jwks = this.getAll();
        if (keyId == null && jwks.size() == 1) {
            return (Jwk) jwks.get(0);
        } else {
            if (keyId != null) {
                Iterator var3 = jwks.iterator();

                while (var3.hasNext()) {
                    Jwk jwk = (Jwk) var3.next();
                    if (keyId.equals(jwk.getId())) {
                        return jwk;
                    }
                }
            }

            throw new SigningKeyNotFoundException("No key found in " + this.url.toString() + " with kid " + keyId, (Throwable) null);
        }
    }
}

