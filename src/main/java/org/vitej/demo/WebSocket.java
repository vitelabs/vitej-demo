package org.vitej.demo;

import com.alibaba.fastjson.JSON;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.SnapshotBlockResponse;
import org.vitej.core.protocol.websocket.WebSocketService;

import java.io.IOException;

public class WebSocket {
    public static void main(String[] args) throws IOException {
        WebSocketService ws = new WebSocketService();
        ws.connect();
        Vitej vitej = new Vitej(ws);

        // Call RPC method using WebSocket service
        SnapshotBlockResponse response = vitej.getLatestSnapshotBlock().send();

        // Subscribe to new blocks event using WebSocket service
        vitej.snapshotBlockFlowable().subscribe(msg -> {
            System.out.println("snapshotBlock: " + JSON.toJSONString(msg));
        });

        vitej.accountBlockFlowable().subscribe(msg -> {
            System.out.println("accountBlock: " + JSON.toJSONString(msg));
        });

        vitej.accountBlockByAddressFlowable(new Address("vite_000000000000000000000000000000000000000595292d996d")).subscribe(msg -> {
            System.out.println("accountBlockByAddress: " + JSON.toJSONString(msg));
        });

        vitej.unreceivedBlockFlowable(new Address("vite_000000000000000000000000000000000000000595292d996d")).subscribe(msg -> {
            System.out.println("unreceivedBlock: " + JSON.toJSONString(msg));
        });

        vitej.vmlogFlowable(new VmLogFilter(new Address("vite_000000000000000000000000000000000000000595292d996d"))).subscribe(msg -> {
            System.out.println("vmlog: " + JSON.toJSONString(msg));
        });
    }
}
