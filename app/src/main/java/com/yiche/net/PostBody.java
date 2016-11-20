package com.yiche.net;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 单独的请求体，设置的话会忽略Netparams
 */

public abstract class PostBody {

    public abstract String getMediaType();

    public abstract InputStream getIputStream() ;

    public static PostBody create(final String mediaType, final String content) {
        Charset charset = Charset.forName("UTF-8");
        byte[] bytes = content.getBytes(charset);
        return create(mediaType,bytes);
    }

    public static PostBody create(final String mediaType, final byte[] content) {
        return new PostBody() {
            @Override
            public String getMediaType() {
                return mediaType;
            }

            @Override
            public InputStream getIputStream() {
                return new ByteArrayInputStream(content);
            }
        };
    }


    public static PostBody create(final String mediaType, final File file) {
        if (file == null) throw new NullPointerException("content == null");
        return new PostBody() {
            @Override
            public String getMediaType() {
                return mediaType;
            }

            @Override
            public InputStream getIputStream() {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
