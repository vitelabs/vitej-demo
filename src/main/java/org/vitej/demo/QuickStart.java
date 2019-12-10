package org.vitej.demo;

import com.alibaba.fastjson.JSON;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.response.SnapshotChainHeightResponse;

import java.io.IOException;

public class QuickStart {
    public static void main(String[] args) {
        try {
            // Initialize Vitej client
            Vitej vitej = new Vitej(new HttpService());
            // Get latest snapshot block height
            SnapshotChainHeightResponse response = vitej.getSnapshotChainHeight().send();
            System.out.println(JSON.toJSONString(response));
            Long latestSnapshotHeight = response.getHeight();
            System.out.println(latestSnapshotHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
