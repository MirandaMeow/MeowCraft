package cn.miranda.MeowCraft.Utils;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class IO {
    public static String encodeData(Object object) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        return encoder.encode(outputStream.toByteArray());
    }

    public static Object decodeData(String cacheData) throws IOException, ClassNotFoundException {
        BASE64Decoder decoder = new BASE64Decoder();
        if (cacheData == null) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decoder.decodeBuffer(cacheData));
        BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);
        return objectInputStream.readObject();
    }
}
