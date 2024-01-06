import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;

public class ServerHandler {

    public static void handleAccept(ServerSocketChannel mySocket,
    SelectionKey key, HashSet<String> users, Selector selector) throws IOException {

        System.out.println("Connexio Acceptada...");

        SocketChannel client = mySocket.accept();
        client.configureBlocking(false);
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

        ByteBuffer bufferNick = ByteBuffer.allocate(1024);
        client.read(bufferNick);
        String nick = BufferHandler.readBuffer(bufferNick);

        while(!(nick.length() > 0)){
            client.read(bufferNick);
            nick = BufferHandler.readBuffer(bufferNick);
        } 

        users.add(nick);
        clientKey.attach(nick);
        System.out.println("Nou client: " + clientKey.attachment());

        bufferNick.clear();
        String str = "Un now usuari s'ha unit: " + nick;
        BufferHandler.writeBuffer(bufferNick, str);


        for(SelectionKey selectionKey: selector.keys()){

            if((selectionKey.attachment() != clientKey.attachment()) & (selectionKey.attachment() != null)){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                socketChannel.write(bufferNick);
                bufferNick.flip();
            }
        }
        UsersHandler.sendUsers(users, selector);
    }


    public static void handleRead(SelectionKey selectionKey, HashSet<String> users, Selector selector)
    throws IOException {

        System.out.println("Llegin...");

        SocketChannel client = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        buffer.flip();
        String data = new String(buffer.array()).trim();

        if (data.length() > 0) {

            if (data.equalsIgnoreCase("exit")) {
            users.remove(selectionKey.attachment());

            buffer.clear();
            data = "L'usuari" + selectionKey.attachment() + " s'ha anat";
            BufferHandler.writeBuffer(buffer, data);

            for(SelectionKey sk: selector.keys()){

                if((sk.attachment() != selectionKey.attachment()) & (sk.attachment() != null)){
                SocketChannel socketChannel = (SocketChannel) sk.channel();

                socketChannel.write(buffer);
                buffer.flip();

                }
            }
            UsersHandler.sendUsers(users, selector);

            client.close();

            System.out.println("Connexio tancada...");

            }else{
                data = ((String)selectionKey.attachment()) + ": " +  data;
                buffer.clear();
                BufferHandler.writeBuffer(buffer, data);
                for(SelectionKey sk: selector.keys()){

                    if((sk.attachment() != selectionKey.attachment()) & (sk.attachment() != null)){
                        SocketChannel socketChannel = (SocketChannel) sk.channel();
                        socketChannel.write(buffer);
                        buffer.flip();
                    }
                }
            }
        }

    }

}
