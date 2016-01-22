package com.karaokeCopacabana;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.sound.sampled.*;
import java.net.InetSocketAddress;

/**
 * Created by on 18.12.15.
 */
public class SocketServer extends WebSocketServer {
    private WebSocket socket;
    private SourceDataLine soundLine;
    public SocketServer(InetSocketAddress address) throws LineUnavailableException {
        super(address);
        Mixer.Info[] mixer = AudioSystem.getMixerInfo();
        System.out.println(mixer[0]);
        //final AudioFormat audioFormat = new AudioFormat(44100, 16, 1, false, false);
        //final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, 1);
        //this.soundLine = (SourceDataLine) AudioSystem.getLine(info);
        //this.soundLine.open(audioFormat, 1024);
        //this.soundLine.start();


    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("open");
        this.socket =conn;

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("close");
        System.out.println(reason);

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.print("message: " + message);

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(ex);

    }

    public void onFragment( WebSocket conn, Framedata fragment ) {
        this.soundLine.write(fragment.getPayloadData().array(),0,1024);
    }

    public WebSocket getSocket() {
        return this.socket;
    }
}
