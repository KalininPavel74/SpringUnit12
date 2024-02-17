package home.kalinin.rest_notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DSL - domain-specific language
 */
@Configuration
public class IntegrationDSLConfig {
    @Bean
    public IntegrationFlow feedFlow() throws MalformedURLException {
        return IntegrationFlow
                .from(MessageChannels.direct("textInputChanel"))
                .transform(mainTransformer())
                .handle(messageHandler())
                .get();
    }
    @Bean
    public GenericTransformer<String, String> mainTransformer() {
        return text -> (new SimpleDateFormat("dd.MM.yyyy HH:mm"))
                .format(new Date()) + " " + text;
    }
    @Bean
    public FileWritingMessageHandler messageHandler() {
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(
                        new File("integration_files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        handler.setAutoCreateDirectory(true);
        handler.setCharset("UTF-8");
        handler.setExpectReply(false); // не нужно перенаправлять в другой канал
        return handler;
    }
}
