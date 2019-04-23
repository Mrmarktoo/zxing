package com.google.zxing;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Map;

/**
 * author : alex
 * className : QrFormatHelper
 * desc : 二维码生成和解码
 * date : 2019/4/23 11:44
 * company : marktoo
 * leader : alex
 */
public class QrFormatHelper implements Writer, Reader {
    @Override
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    @Override
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        Writer writer = new QRCodeWriter();
        return writer.encode(contents, format, width, height, hints);
    }

    Reader myQrReader = null;
    Map<DecodeHintType, ?> hints = null;

    @Override
    public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
        setHints(null);
        return decodeInternal(image);
    }

    @Override
    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
        setHints(hints);
        return decodeInternal(image);
    }

    public void setHints(Map<DecodeHintType, ?> hints) {
        this.hints = hints;
        myQrReader = new QRCodeReader();
    }

    private Result decodeInternal(BinaryBitmap image) throws NotFoundException {
        if (myQrReader != null) {
            try {
                return myQrReader.decode(image, hints);
            } catch (ChecksumException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        if (myQrReader != null) {
            myQrReader.reset();
        }
    }
}
