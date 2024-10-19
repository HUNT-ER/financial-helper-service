package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.exception.QrCodeReadingException;
import com.boldyrev.financialhelper.service.QrCodeService;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link QrCodeService} with ZXing library.
 *
 * @author Alexandr Boldyrev
 */
@Service
public class ZXingQrCodeServiceImpl implements QrCodeService {

    @Override
    public Mono<String> readQrCode(byte[] bytes) {
        return Mono.fromCallable(() -> {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                BufferedImage bufferedImage = ImageIO.read(bais);
                BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);

                return result.getText();
            }
        }).onErrorMap(ex -> new QrCodeReadingException(ex.getMessage(), ex));
    }
}
