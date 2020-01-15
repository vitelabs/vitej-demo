package org.vitej.demo;

import com.google.common.base.Preconditions;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.response.AccountBlockResponse;

import java.io.IOException;

public class GetSendBlockDataByReceiveBlock {
    public static void main(String[] args) throws IOException {
        Hash h = new Hash("8b24348a290749c4bce7c80a3de35c3d0d3dfbc545b474379225c40c06d085aa");
        byte[] sendBlockData = findSendBlockDataByReceiveBlockHash(h);
        byte[] originalSendBlockData = findSendBlockDataByReceiveBlockHashRecursive(h);
    }

    private static byte[] findSendBlockDataByReceiveBlockHash(Hash receiveHash) throws IOException {
        Vitej vitej = new Vitej(new HttpService());
        AccountBlockResponse receiveBlock = vitej.getAccountBlockByHash(receiveHash).send();
        Preconditions.checkNotNull(receiveBlock.getResult());
        Preconditions.checkNotNull(receiveBlock.getResult().getSendBlockHash());
        Hash sendBlockHash = receiveBlock.getResult().getSendBlockHash();
        AccountBlockResponse sendBlock = vitej.getAccountBlockByHash(sendBlockHash).send();
        Preconditions.checkNotNull(sendBlock.getResult());
        return sendBlock.getResult().getData();
    }

    private static byte[] findSendBlockDataByReceiveBlockHashRecursive(Hash receiveHash) throws IOException {
        Vitej vitej = new Vitej(new HttpService());
        AccountBlockResponse receiveBlock = vitej.getAccountBlockByHash(receiveHash).send();
        while (true) {
            Preconditions.checkNotNull(receiveBlock.getResult());
            Preconditions.checkNotNull(receiveBlock.getResult().getSendBlockHash());
            Hash sendBlockHash = receiveBlock.getResult().getSendBlockHash();
            AccountBlockResponse fromBlock = vitej.getCompleteAccountBlockByHash(sendBlockHash).send();
            Preconditions.checkNotNull(fromBlock.getResult());
            if (fromBlock.getResult().isSendBlock()) {
                return fromBlock.getResult().getData();
            } else {
                receiveBlock = fromBlock;
            }
        }
    }
}
