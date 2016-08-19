package zone.kaz.alight_midi_monitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Networking {

    private int port;
    private Thread thread;
    private char[] buffer = null;

    public Networking(int port) {
        this.port = port;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                ServerSocketChannel listener = ServerSocketChannel.open();
                listener.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                listener.bind(new InetSocketAddress(port));
                ByteBuffer headerBuffer = ByteBuffer.allocate(4);
                while (true) {
                    SocketChannel channel = listener.accept();
                    boolean isHeader = true;
                    byte chNum = 0, command = 0;
                    short length = 0;
                    while (true) {
                        if (!channel.isConnected()) {
                            break;
                        }
                        if (isHeader) {
                            headerBuffer.clear();
                            channel.read(headerBuffer);
                            headerBuffer.rewind();
                            chNum = headerBuffer.get();
                            command = headerBuffer.get();
                            length = headerBuffer.getShort();
                            isHeader = false;
                        } else {
                            ByteBuffer dataBuffer = ByteBuffer.allocate(length);
                            channel.read(dataBuffer);
                            dataBuffer.rewind();
                            int limit = dataBuffer.limit();
                            char[] buffer = new char[length];
                            for (int i = 0; i < limit; i++) {
                                buffer[i] = (char) (((char) dataBuffer.get(i)) & 0xFF);
                            }
                            this.buffer = buffer;
                            isHeader = true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public char[] getBuffer() {
        return buffer;
    }

}
