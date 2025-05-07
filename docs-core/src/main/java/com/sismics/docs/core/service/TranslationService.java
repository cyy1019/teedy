package com.sismics.docs.core.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;
import com.sismics.docs.core.util.ConfigUtil;
import com.sismics.docs.core.constant.ConfigType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Translation service using Tencent Cloud API.
 */
public class TranslationService {
    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(
        "zh", "en", "ja", "ko", "fr", "de", "es", "ru"
    );

    // Lazy-loaded credentials and client
    private TmtClient client;

    /**
     * Lazily initializes the TmtClient.
     */
    private void initClientIfNeeded() {
        if (client == null) {
            String secretId = "AKIDW7lEWcdaMzfEe8WjmdM2bzCE944vxyuh";
            String secretKey = "e6MVVKxPkvHUWM0pY6Rerwrb9VfdQ7sZ";

            if (secretId == null || secretKey == null) {
                throw new IllegalStateException("Tencent Cloud credentials are not configured properly.");
            }

            Credential cred = new Credential(secretId, secretKey);
            client = new TmtClient(cred, "ap-guangzhou");
        }
    }

    /**
     * Translate text to target language.
     *
     * @param text           Text to translate
     * @param targetLanguage Target language code (e.g., "zh" for Chinese)
     * @return Translated text
     * @throws IllegalArgumentException if target language is not supported
     */
    public String translateText(String text, String targetLanguage) {
        // Validate target language
        if (!SUPPORTED_LANGUAGES.contains(targetLanguage)) {
            throw new IllegalArgumentException("Unsupported target language: " + targetLanguage);
        }

        initClientIfNeeded();

        try {
            TextTranslateRequest req = new TextTranslateRequest();
            req.setSourceText(text);
            req.setSource("auto");
            req.setTarget(targetLanguage);
            req.setProjectId(0L);

            TextTranslateResponse resp = client.TextTranslate(req);
            return resp.getTargetText();
        } catch (TencentCloudSDKException e) {
            log.error("Error translating text", e);
            throw new RuntimeException("Translation failed", e);
        }
    }
}
