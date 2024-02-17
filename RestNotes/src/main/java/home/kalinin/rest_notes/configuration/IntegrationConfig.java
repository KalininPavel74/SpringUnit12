package home.kalinin.rest_notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Конвейер.
 * №1 Шлюз - источник данных.
 *       @MessagingGateway(defaultRequestChannel = "textInputChanel")
 *       public interface FileGateway {
 *           void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
 *       }
 * №2 Канал (DirectChannel) от шлюза до преобразователя данных (Трансформер)
 * №3 Преобразователь данных (Трансформер) GenericTransformer<String, String>
 * №4 Канал (DirectChannel) от преобразователя данных (Трансформер) до
 * №5 Активатор службы (Адаптер выходного канала для записи в файл FileWritingMessageHandler)
 */
//@Configuration
public class IntegrationConfig {
//
//    @Bean
//    public MessageChannel textInputChanel() {
//        return new DirectChannel();
//    }
//    @Bean
//    public MessageChannel fileWriterChanel() {
//        return new DirectChannel();
//    }
//    @Bean
//    @Transformer(inputChannel = "textInputChanel", outputChannel = "fileWriterChanel")
//    public GenericTransformer<String, String> mainTransformer() {
//        return text -> (new SimpleDateFormat("dd.MM.yyyy HH:mm"))
//                .format(new Date()) + " " + text;
//    }
//
//    /**
//     * Работает в своем потоке.
//     * Информация в папке может появится с большой задержкой.
//     * @return
//     */
//    @Bean
//    @ServiceActivator(inputChannel = "fileWriterChanel")
//    public FileWritingMessageHandler messageHandler() {
//        FileWritingMessageHandler handler =
//                new FileWritingMessageHandler(
//                        new File("integration_files"));
//        handler.setExpectReply(false);
//        handler.setFileExistsMode(FileExistsMode.APPEND);
//        handler.setAppendNewLine(true);
//        handler.setAutoCreateDirectory(true);
//        handler.setCharset("UTF-8");
//        handler.setExpectReply(false); // не нужно перенаправлять в другой канал
//        return handler;
//    }
}
